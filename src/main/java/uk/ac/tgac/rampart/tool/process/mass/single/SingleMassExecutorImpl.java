/**
 * RAMPART - Robust Automatic MultiPle AssembleR Toolkit
 * Copyright (C) 2013  Daniel Mapleson - TGAC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/
package uk.ac.tgac.rampart.tool.process.mass.single;

import org.apache.commons.io.FileUtils;
import uk.ac.ebi.fgpt.conan.core.context.DefaultExecutionContext;
import uk.ac.ebi.fgpt.conan.model.context.ExecutionContext;
import uk.ac.ebi.fgpt.conan.model.context.ExecutionResult;
import uk.ac.ebi.fgpt.conan.model.context.SchedulerArgs;
import uk.ac.ebi.fgpt.conan.service.ConanProcessService;
import uk.ac.ebi.fgpt.conan.service.exception.ProcessExecutionException;
import uk.ac.ebi.fgpt.conan.util.StringJoiner;
import uk.ac.ebi.fgpt.conan.utils.CommandExecutionException;
import uk.ac.tgac.conan.process.asm.Assembler;
import uk.ac.tgac.conan.process.asm.AssemblerArgs;
import uk.ac.tgac.conan.process.subsampler.SubsamplerV1_0Args;
import uk.ac.tgac.conan.process.subsampler.SubsamplerV1_0Process;
import uk.ac.tgac.rampart.tool.RampartExecutorImpl;
import uk.ac.tgac.rampart.tool.process.mass.MassArgs;
import uk.ac.tgac.rampart.tool.process.stats.StatsExecutor;
import uk.ac.tgac.rampart.tool.process.stats.StatsExecutorImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: maplesod
 * Date: 23/08/13
 * Time: 11:18
 */
public class SingleMassExecutorImpl extends RampartExecutorImpl implements SingleMassExecutor {

    private List<Integer> jobIds;
    private StatsExecutor statsExecutor;

    public SingleMassExecutorImpl() {
        this.jobIds = new ArrayList<>();
        this.statsExecutor = new StatsExecutorImpl();
    }

    @Override
    public void initialise(ConanProcessService conanProcessService, ExecutionContext executionContext) {
        super.initialise(conanProcessService, executionContext);

        this.jobIds.clear();
        this.statsExecutor.initialise(conanProcessService, executionContext);
    }

    @Override
    public ExecutionResult executeAssembler(Assembler assembler, String jobName, boolean runParallel)
            throws ProcessExecutionException, InterruptedException, IOException {

        // Important that this happens after directory cleaning.
        assembler.initialise();

        // Create execution context
        ExecutionContext executionContextCopy = executionContext.copy();
        executionContextCopy.setContext(
                jobName,
                executionContextCopy.usingScheduler() ? !runParallel : true,
                new File(assembler.getArgs().getOutputDir(), jobName + ".log"));

        // Modify the scheduling settings if present
        if (executionContextCopy.usingScheduler()) {

            SchedulerArgs schArgs = executionContextCopy.getScheduler().getArgs();

            AssemblerArgs asmArgs = assembler.getArgs();

            schArgs.setThreads(asmArgs.getThreads());
            schArgs.setMemoryMB(asmArgs.getMemory());

            if (assembler.usesOpenMpi() && asmArgs.getThreads() > 1) {
                schArgs.setOpenmpi(true);
            }
        }

        // Create process
        return this.conanProcessService.execute(assembler, executionContextCopy);
    }

    @Override
    public void createAssemblyLinks(Assembler assembler, SingleMassArgs smArgs, String jobName)
            throws ProcessExecutionException, InterruptedException {

        ExecutionContext linkingExecutionContext = new DefaultExecutionContext(executionContext.getLocality(), null, null);

        StringJoiner compoundLinkCmdLine = new StringJoiner(";");

        if (assembler.makesUnitigs()) {
            compoundLinkCmdLine.add(
                            this.conanProcessService.makeLinkCommand(assembler.getUnitigsFile(),
                                    new File(smArgs.getUnitigsDir(), jobName + "-unitigs.fa")));
        }

        if (assembler.makesContigs()) {
            compoundLinkCmdLine.add(
                            this.conanProcessService.makeLinkCommand(assembler.getContigsFile(),
                                    new File(smArgs.getContigsDir(), jobName + "-contigs.fa")));
        }

        if (assembler.makesScaffolds()) {
            compoundLinkCmdLine.add(
                            this.conanProcessService.makeLinkCommand(assembler.getScaffoldsFile(),
                                    new File(smArgs.getScaffoldsDir(), jobName + "-scaffolds.fa")));
        }

        this.conanProcessService.execute(compoundLinkCmdLine.toString(), linkingExecutionContext);
    }

    @Override
    public void dispatchAnalyserJobs(Assembler assembler, SingleMassArgs args, String waitCondition, String jobName)
            throws InterruptedException, ProcessExecutionException, IOException, CommandExecutionException {

        File inputDir = null;

        // We only do one level of Cegma jobs (the highest), technically there shouldn't be much / any different between different levels
        if (assembler.makesScaffolds()) {
            inputDir = args.getScaffoldsDir();
        }
        else if (assembler.makesContigs()) {
            inputDir = args.getContigsDir();
        }
        else if (assembler.makesUnitigs()) {
            inputDir = args.getUnitigsDir();
        }
        else {
            throw new IOException("Couldn't run stats jobs because assembler does not support any recognised output types: " + MassArgs.OutputLevel.getListAsString());
        }

        // Execute the jobs
        List<Integer> jobIds = this.statsExecutor.dispatchAnalyserJobs(
                args.getStatsLevels(),
                inputDir,
                args.getInputKmers(),
                args.getThreads(),
                args.getMemory(),
                args.getOrganism() == null ? null : args.getOrganism(),
                assembler.makesScaffolds(),
                args.isRunParallel(),
                waitCondition,
                jobName);

        // Add these jobs to the list of running jobs
        this.jobIds.addAll(jobIds);
    }

    @Override
    public void executeSubsampler(double probability, long timestamp, File input, File output, String jobName)
            throws ProcessExecutionException, InterruptedException, IOException {

        ExecutionContext executionContextCopy = executionContext.copy();
        executionContextCopy.setContext(jobName, true, new File(output.getParentFile(), jobName + ".log"));

        SubsamplerV1_0Args ssArgs = new SubsamplerV1_0Args();
        ssArgs.setInputFile(input);
        ssArgs.setOutputFile(output);
        ssArgs.setLogFile(new File(output.getParentFile(), output.getName() + ".log"));
        ssArgs.setSeed(timestamp);
        ssArgs.setProbability(probability);

        SubsamplerV1_0Process ssProc = new SubsamplerV1_0Process(ssArgs);

        // Create process
        this.conanProcessService.execute(ssProc, executionContextCopy);
    }

    @Override
    public long getNbEntries(File seqFile, File outputDir, String jobName) throws ProcessExecutionException, InterruptedException, IOException {

        return getCount(seqFile, outputDir, jobName, true);
    }

    @Override
    public long getNbBases(File seqFile, File outputDir, String jobName) throws IOException, ProcessExecutionException, InterruptedException {

        return getCount(seqFile, outputDir, jobName, false);
    }

    @Override
    public List<Integer> getJobIds() {
        return this.jobIds;
    }

    protected long getCount(File seqFile, File outputDir, String jobName, boolean linesOnly) throws ProcessExecutionException, InterruptedException, IOException {

        ExecutionContext executionContextCopy = this.executionContext.copy();
        executionContextCopy.setContext(jobName, true, new File(outputDir, jobName + ".log"));

        File outputFile = new File(outputDir, seqFile.getName() + ".nb_entries.out");

        String wcOption = linesOnly ? "-l" : "-m";
        String command = "awk '/^@/{getline; print}' " + seqFile.getAbsolutePath() + " | wc " + wcOption + " > " + outputFile.getAbsolutePath();

        this.conanProcessService.execute(command, executionContextCopy);

        List<String> lines = FileUtils.readLines(outputFile);

        if (lines == null || lines.isEmpty()) {
            throw new IOException("Failed to retrieve number of lines in file: " + seqFile.getAbsolutePath());
        }

        return Long.parseLong(lines.get(0).trim());
    }


}
