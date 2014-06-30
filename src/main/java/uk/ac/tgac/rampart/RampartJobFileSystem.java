/*
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
 */

package uk.ac.tgac.rampart;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: maplesod
 * Date: 29/10/13
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
public class RampartJobFileSystem {

    // **** Autogenerated variables ****

    // Directories
    private File meqcDir;
    private File analyseReadsDir;
    private File massDir;
    private File analyseMassDir;
    private File massStatsDir;
    private File ampDir;
    private File ampAssembliesDir;
    private File analyseAmpDir;
    private File reportDir;
    private File reportImagesDir;
    private File finalDir;

    // Important Files
    private File qtLogFile;
    private File massPlotsFile;
    private File massStatsFile;
    private File massLogFile;
    private File selectedAssemblyFile;
    private File selectedBubbleFile;
    private File ampPlotsFile;
    private File ampStatsFile;
    private File ampLogFile;
    private File reportTemplateFile;
    private File reportMergedFile;

    public RampartJobFileSystem(File jobDir) {
        this.setupFileStructure(jobDir);
    }

    protected final void setupFileStructure(File jobDir) {

        // Record all important directories and make sure they exist
        this.meqcDir = new File(jobDir, "mecq");
        this.analyseReadsDir = new File(jobDir, "reads-analyses");
        this.massDir = new File(jobDir, "mass");
        this.analyseMassDir = new File(jobDir, "mass-analyses");
        this.massStatsDir = new File(massDir, "stats");
        this.ampDir = new File(jobDir, "amp");
        this.ampAssembliesDir = new File(ampDir, "assemblies");
        this.analyseAmpDir = new File(jobDir, "amp-analyses");
        this.reportDir = new File(jobDir, "report");
        this.reportImagesDir = new File(reportDir, "images");
        this.finalDir = new File(jobDir, "final");

        this.qtLogFile = new File(this.meqcDir + "/qt.log");
        this.massPlotsFile = new File(this.massStatsDir.getPath() + "/plots.pdf");
        this.massStatsFile = new File(this.massStatsDir.getPath() + "/score.tab");
        this.massLogFile = new File(this.massDir.getPath() + "/mass.settings");
        this.selectedAssemblyFile = new File(this.analyseMassDir.getPath() + "/best.fa");
        this.selectedBubbleFile = new File(this.analyseMassDir.getPath() + "/best_bubbles.fa");
        this.ampPlotsFile = new File(this.ampAssembliesDir.getPath() + "/analyser.pdf");
        this.ampStatsFile = new File(this.ampAssembliesDir.getPath() + "/analyser.txt");
        this.ampLogFile = new File(this.ampDir.getPath() + "/amp.log");
        this.reportTemplateFile = new File(this.reportDir.getPath() + "/template.tex");
        this.reportMergedFile = new File(this.reportDir.getPath() + "/report.tex");
    }


    // **** File Structure ****


    // ****** Dirs ******

    public File getMeqcDir() {
        return meqcDir;
    }

    public File getAnalyseReadsDir() {
        return analyseReadsDir;
    }

    public File getMassDir() {
        return massDir;
    }

    public File getAnalyseMassDir() {
        return analyseMassDir;
    }

    public File getMassStatsDir() {
        return massStatsDir;
    }

    public File getAmpDir() {
        return ampDir;
    }

    public File getAnalyseAmpDir() {
        return analyseAmpDir;
    }

    public File getReportDir() {
        return reportDir;
    }

    public File getReportImagesDir() {
        return reportImagesDir;
    }

    public File getFinalDir() {
        return finalDir;
    }


    // ****** Files ******

    public File getQtLogFile() {
        return qtLogFile;
    }

    public File getMassPlotsFile() {
        return massPlotsFile;
    }

    public File getMassStatsFile() {
        return massStatsFile;
    }

    public File getMassLogFile() {
        return massLogFile;
    }

    public File getSelectedAssemblyFile() {
        return selectedAssemblyFile;
    }

    public File getSelectedBubbleFile() {
        return selectedBubbleFile;
    }

    public File getReportTemplateFile() {
        return reportTemplateFile;
    }

    public File getReportMergedFile() {
        return reportMergedFile;
    }

    public File getAmpPlotsFile() {
        return ampPlotsFile;
    }

    public File getAmpAssembliesDir() {
        return ampAssembliesDir;
    }

    public File getAmpStatsFile() {
        return ampStatsFile;
    }

    public File getAmpLogFile() {
        return ampLogFile;
    }

}
