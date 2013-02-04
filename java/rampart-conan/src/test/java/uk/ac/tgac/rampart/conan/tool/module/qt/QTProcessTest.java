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
package uk.ac.tgac.rampart.conan.tool.module.qt;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import uk.ac.ebi.fgpt.conan.service.exception.ProcessExecutionException;
import uk.ac.ebi.fgpt.conan.utils.CommandExecutionException;
import uk.ac.tgac.rampart.conan.conanx.env.DefaultEnvironment;
import uk.ac.tgac.rampart.conan.conanx.parameter.FilePair;
import uk.ac.tgac.rampart.conan.tool.module.util.RampartConfig;
import uk.ac.tgac.rampart.conan.tool.process.qt.QualityTrimmer;
import uk.ac.tgac.rampart.conan.tool.process.qt.sickle.SicklePeV11Args;
import uk.ac.tgac.rampart.conan.tool.process.qt.sickle.SickleV11Process;
import uk.ac.tgac.rampart.conan.util.PETestLibrary;

import java.io.File;
import java.io.IOException;

/**
 * User: maplesod
 * Date: 24/01/13
 * Time: 11:53
 */
public class QTProcessTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();


    @Test
    public void localQTTest() throws InterruptedException, ProcessExecutionException, IOException, CommandExecutionException {

        File outputDir = temp.newFolder("qtTest");

        QTArgs qtArgs = new QTArgs();
        qtArgs.setOutputDir(outputDir);
        qtArgs.setConfig(new RampartConfig(new File("tools/test_rampart.cfg")));

        QTProcess simpleQTProcess = new QTProcess(qtArgs);

        simpleQTProcess.execute(new DefaultEnvironment());
    }

}
