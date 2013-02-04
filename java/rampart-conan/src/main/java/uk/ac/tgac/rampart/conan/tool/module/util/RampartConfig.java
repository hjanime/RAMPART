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
package uk.ac.tgac.rampart.conan.tool.module.util;

import uk.ac.tgac.rampart.conan.tool.process.qt.QualityTrimmer;
import uk.ac.tgac.rampart.conan.tool.process.qt.QualityTrimmerArgs;
import uk.ac.tgac.rampart.conan.tool.process.qt.QualityTrimmerFactory;
import uk.ac.tgac.rampart.core.data.Library;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: maplesod
 * Date: 01/02/13
 * Time: 11:32
 */
public class RampartConfig {


    // File keys
    public static final String QT_MIN_LEN   = "minlen";
    public static final String QT_MIN_QUAL  = "minqual";



    private File configFile;

    private String name;
    private String qtTool;
    private Map<String, String> qtArgs;


    public RampartConfig() {
        this(null);
    }

    public RampartConfig(File configFile) {
        this.parseFile(configFile);
        this.configFile = configFile;
    }


    public File getConfigFile() {
        return configFile;
    }

    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Library> getLibs() {
        return null;
    }

    protected void parseFile(File configFile) {

        if (configFile != null && configFile.exists()) {
             //TODO Do stuff
        }
    }

    public static List<RampartConfig> parseList(String list) {

        List<RampartConfig> configs = new ArrayList<RampartConfig>();

        String[] parts = list.split(",");

        for(String part : parts) {
            configs.add(new RampartConfig(new File(part.trim())));
        }

        return configs;
    }

    public static List<RampartConfig> createList(List<File> configFiles) {

        List<RampartConfig> configs = new ArrayList<RampartConfig>();

        for(File configFile : configFiles) {
            configs.add(new RampartConfig(configFile));
        }

        return configs;
    }

    public String getQTTool() {
        return this.qtTool;
    }

    public Map<String, String> getQTArgs() {
        return this.qtArgs;
    }




    // ***** Construction methods *****

    public List<QualityTrimmer> createQualityTrimmers() {

        List<QualityTrimmer> qtList = new ArrayList<QualityTrimmer>();

        for(Library lib : this.getLibs()) {

            if (lib.testUsage(Library.Usage.QUALITY_TRIMMING)) {

                QualityTrimmer qt = QualityTrimmerFactory.create(this.getQTTool(), lib);

                qt.getArgs().setMinLength(Integer.parseInt(this.getQTArgs().get(QT_MIN_LEN)));
                qt.getArgs().setQualityThreshold(Integer.parseInt(this.getQTArgs().get(QT_MIN_QUAL)));

                qtList.add(qt);
            }
        }

        return qtList;
    }
}
