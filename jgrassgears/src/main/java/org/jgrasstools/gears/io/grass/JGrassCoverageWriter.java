/*
 * JGrass - Free Open Source Java GIS http://www.jgrass.org 
 * (C) HydroloGIS - www.hydrologis.com 
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Library General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Library General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Library General Public License
 * along with this library; if not, write to the Free Foundation, Inc., 59
 * Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.jgrasstools.gears.io.grass;

import java.io.File;

import oms3.annotations.Author;
import oms3.annotations.Description;
import oms3.annotations.Execute;
import oms3.annotations.In;
import oms3.annotations.Keywords;
import oms3.annotations.License;
import oms3.annotations.Out;
import oms3.annotations.Status;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.gce.grassraster.JGrassMapEnvironment;
import org.geotools.gce.grassraster.JGrassRegion;
import org.geotools.gce.grassraster.format.GrassCoverageFormatFactory;
import org.jgrasstools.gears.libs.modules.JGTModel;
import org.jgrasstools.gears.libs.monitor.DummyProgressMonitor;
import org.jgrasstools.gears.libs.monitor.IJGTProgressMonitor;
import org.jgrasstools.gears.utils.coverage.CoverageUtilities;
import org.opengis.coverage.grid.GridCoverageWriter;
import org.opengis.parameter.GeneralParameterValue;

@Description("Utility class for writing geotools coverages to grass rasters.")
@Author(name = "Andrea Antonello", contact = "www.hydrologis.com")
@Keywords("IO, Grass, Coverage, Raster, Writing")
@Status(Status.DRAFT)
@License("http://www.gnu.org/licenses/gpl-3.0.html")
public class JGrassCoverageWriter extends JGTModel {
    @Description("The coverage map that needs to be written.")
    @In
    public GridCoverage2D geodata = null;

    @Description("The progress monitor.")
    @In
    public IJGTProgressMonitor pm = new DummyProgressMonitor();

    @Description("Flag that defines if the map should be written on the whole (false) or"
            + " only on the active region (true and default).")
    @In
    public boolean doActive = true;

    @Description("The file to the map to be written (the cell file).")
    @Out
    public String file = null;

    private boolean hasWritten = false;

    @Execute
    public void writeCoverage() throws Exception {
        if (!concatOr(!hasWritten, doReset)) {
            return;
        }
        JGrassMapEnvironment mapEnvironment = new JGrassMapEnvironment(new File(file));

        AbstractGridFormat format = (AbstractGridFormat) new GrassCoverageFormatFactory()
                .createFormat();
        GridCoverageWriter writer = format.getWriter(mapEnvironment.getCELL());

        GeneralParameterValue[] readParams = null;
        if (doActive) {
            JGrassRegion activeRegion = mapEnvironment.getActiveRegion();
            readParams = CoverageUtilities.createGridGeometryGeneralParameter(activeRegion
                    .getCols(), activeRegion.getRows(), activeRegion.getWest(), activeRegion
                    .getEast(), activeRegion.getSouth(), activeRegion.getNorth(), mapEnvironment
                    .getCoordinateReferenceSystem());
        }

        writer.write(geodata, readParams);
        hasWritten = true;
    }
}