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
package uk.ac.tgac.rampart.pipeline.tool.pipeline.amp;

import org.springframework.stereotype.Component;
import uk.ac.ebi.fgpt.conan.model.ConanPipeline;
import uk.ac.ebi.fgpt.conan.model.ConanProcess;
import uk.ac.ebi.fgpt.conan.model.ConanUser;
import uk.ac.ebi.fgpt.conan.model.param.ConanParameter;
import uk.ac.tgac.rampart.conan.process.AbstractIOProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: maplesod
 * Date: 07/01/13
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AmpPipeline implements ConanPipeline {

    private AmpParams params = new AmpParams();
    private AmpArgs args;

    public AmpPipeline() {
        this(new AmpArgs());
    }

    public AmpPipeline(AmpArgs ampArgs) {
        this.args = ampArgs;
    }

    @Override
    public String getName() {
        return "Assembly iMProver (AMP)";
    }

    @Override
    public ConanUser getCreator() {
        return null;
    }

    @Override
    public boolean isPrivate() {
        return false;
    }

    @Override
    public boolean isDaemonized() {
        return false;
    }

    @Override
    public List<ConanProcess> getProcesses() {

        List<ConanProcess> cProcs= new ArrayList<ConanProcess>();

        for(AbstractIOProcess abstractIOProcess : this.args.getProcesses()) {
            cProcs.add(abstractIOProcess);
        }

        return cProcs;
    }

    @Override
    public List<ConanParameter> getAllRequiredParameters() {
        return this.params.getConanParameters();
    }
}
