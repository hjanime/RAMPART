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
package uk.ac.tgac.rampart.tool.process.stats;

import uk.ac.ebi.fgpt.conan.service.exception.ProcessExecutionException;
import uk.ac.ebi.fgpt.conan.utils.CommandExecutionException;
import uk.ac.tgac.conan.core.data.Organism;
import uk.ac.tgac.rampart.tool.RampartExecutor;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * User: maplesod
 * Date: 23/08/13
 * Time: 11:18
 */
public interface StatsExecutor extends RampartExecutor {

    List<Integer> dispatchAnalyserJobs(List<StatsLevel> statsLevels, File inputDir, List<File> readKmerCounts,
                                       int threadsPerProcess, int memoryPerProcess, Organism organism,
                                       boolean scaffolds, boolean runParallel, String waitCondition, String jobName)
            throws InterruptedException, ProcessExecutionException, IOException, CommandExecutionException;

}
