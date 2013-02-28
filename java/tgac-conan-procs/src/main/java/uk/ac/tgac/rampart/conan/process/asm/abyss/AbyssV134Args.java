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
package uk.ac.tgac.rampart.conan.process.asm.abyss;

import uk.ac.ebi.fgpt.conan.model.param.ConanParameter;
import uk.ac.tgac.rampart.conan.process.asm.AssemblerArgs;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AbyssV134Args extends AssemblerArgs {

    private AbyssV134Params params = new AbyssV134Params();

    private int nbContigPairs;
    private String name;

    public AbyssV134Args() {
        super();
        this.nbContigPairs = 10;
        this.name = null;
    }

    public int getNbContigPairs() {
        return nbContigPairs;
    }

    public void setNbContigPairs(int nbContigPairs) {
        this.nbContigPairs = nbContigPairs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Map<ConanParameter, String> getArgMap() {

        Map<ConanParameter, String> pvp = new LinkedHashMap<ConanParameter, String>();

        if (this.name != null)
            pvp.put(params.getName(), params.getName().getName() + "=" + this.name);

        if (this.nbContigPairs != 10)
            pvp.put(params.getNbContigPairs(), params.getNbContigPairs().getName() + "=" + Integer.toString(this.nbContigPairs));

        pvp.put(params.getKmer(), params.getKmer().getName() + "=" + Integer.toString(this.getKmer()));

        if (this.getThreads() > 1)
            pvp.put(params.getThreads(), params.getThreads().getName() + "=" + Integer.toString(this.getThreads()));

        if (this.getLibraries() != null && !this.getLibraries().isEmpty())
            pvp.put(params.getLibs(), new AbyssV134InputLibsArg(this.getLibraries()).toString());

        return pvp;
    }

    @Override
    public void setFromArgMap(Map<ConanParameter, String> pvp) {
        for (Map.Entry<ConanParameter, String> entry : pvp.entrySet()) {

            if (!entry.getKey().validateParameterValue(entry.getValue())) {
                throw new IllegalArgumentException("Parameter invalid: " + entry.getKey() + " : " + entry.getValue());
            }

            String param = entry.getKey().getName();
            String val = entry.getValue();

            if (param.equals(this.params.getName().getName())) {
                this.name = val;
            }
            else if (param.equals(this.params.getKmer().getName())) {
                this.setKmer(Integer.parseInt(val));
            }
            else if (param.equals(this.params.getNbContigPairs().getName())) {
                this.nbContigPairs = Integer.parseInt(val);
            }
            else if (param.equals(this.params.getThreads().getName())) {
                this.setThreads(Integer.parseInt(val));
            }
            else if (param.equals(this.params.getLibs().getName())) {
                this.setLibraries(null); // TODO should implement parse method
            }
            else {
                throw new IllegalArgumentException("Unknown param found: " + param);
            }
        }
    }

    @Override
    public AssemblerArgs copy() {

        AbyssV134Args copy = new AbyssV134Args();
        copy.setName(this.getName());
        copy.setKmer(this.getKmer());
        copy.setThreads(this.getThreads());
        copy.setNbContigPairs(this.getNbContigPairs());
        copy.setLibraries(this.getLibraries());  // Not really copying this!!

        return copy;
    }

}
