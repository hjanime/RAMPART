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
package uk.ac.tgac.rampart.conan.tool.process.qt.sickle;

import uk.ac.ebi.fgpt.conan.model.ConanParameter;
import uk.ac.ebi.fgpt.conan.service.exception.ProcessExecutionException;
import uk.ac.tgac.rampart.conan.conanx.process.DefaultExtendedConanProcess;
import uk.ac.tgac.rampart.conan.tool.process.qt.QualityTrimmer;
import uk.ac.tgac.rampart.conan.tool.process.qt.QualityTrimmerArgs;

import java.util.Collection;
import java.util.Map;

/**
 * User: maplesod
 * Date: 23/01/13
 * Time: 14:36
 */
public class SickleV11Process extends DefaultExtendedConanProcess implements QualityTrimmer {

    @Override
    public void qualityTrim() throws InterruptedException, ProcessExecutionException {
        this.execute(this.args.getArgMap());
    }

    public enum JobType {

        SINGLE_END {
            @Override
            public Collection<ConanParameter> getParameters() {
                return new SickleSeV11Params().getConanParameters();
            }

            @Override
            public String getExe() {
                return "sickle se";
            }
        },
        PAIRED_END {
            @Override
            public Collection<ConanParameter> getParameters() {
                return new SicklePeV11Params().getConanParameters();
            }

            @Override
            public String getExe() {
                return "sickle pe";
            }
        };

        public abstract String getExe();
        public abstract Collection<ConanParameter> getParameters();
    }

    private JobType jobType;
    private QualityTrimmerArgs args;


    public SickleV11Process() {
        this(JobType.PAIRED_END, new SicklePeV11Args());
    }

    public SickleV11Process(JobType jobType, QualityTrimmerArgs args) {
        super(jobType.getExe());
        this.jobType = jobType;
        this.args = args;
    }

    @Override
    public QualityTrimmerArgs getArgs() {
        return args;
    }


    @Override
    public String getCommand() {
        return this.getCommandLineBuilder().getFullCommand(this.args, false);
    }

    @Override
    public boolean execute(Map<ConanParameter, String> parameters) throws ProcessExecutionException, IllegalArgumentException, InterruptedException {
          return false;
    }

    @Override
    public String getName() {
        return "Sickle_V1.1";
    }

    @Override
    public Collection<ConanParameter> getParameters() {
        return this.jobType.getParameters();
    }

}
