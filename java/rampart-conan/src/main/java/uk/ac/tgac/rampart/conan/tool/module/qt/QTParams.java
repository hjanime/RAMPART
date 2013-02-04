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

import uk.ac.ebi.fgpt.conan.model.ConanParameter;
import uk.ac.tgac.rampart.conan.conanx.parameter.DefaultConanParameter;
import uk.ac.tgac.rampart.conan.conanx.parameter.PathParameter;
import uk.ac.tgac.rampart.conan.conanx.parameter.ToolParams;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * User: maplesod
 * Date: 16/01/13
 * Time: 13:37
 */
public class QTParams implements ToolParams {

    private ConanParameter qualityTrimmer;
    private ConanParameter rampartConfig;
    private ConanParameter outputDir;

    public QTParams() {
        this.qualityTrimmer = new DefaultConanParameter(
                "qt",
                "The Quality Trimming program (complete with arguments)",
                false,
                true,
                false);

        this.rampartConfig = new PathParameter(
                "config",
                "The rampart configuration file describing the libraries to quality trim",
                false);

        this.outputDir = new PathParameter(
                "output",
                "The directory to place the quality trimmed libraries",
                false);
    }

    public ConanParameter getQualityTrimmer() {
        return qualityTrimmer;
    }

    public ConanParameter getRampartConfig() {
        return rampartConfig;
    }

    public ConanParameter getOutputDir() {
        return outputDir;
    }

    @Override
    public Set<ConanParameter> getConanParameters() {
        return new HashSet<ConanParameter>(Arrays.asList(
                new ConanParameter[]{
                        this.qualityTrimmer,
                        this.rampartConfig,
                        this.outputDir
                }));
    }
}
