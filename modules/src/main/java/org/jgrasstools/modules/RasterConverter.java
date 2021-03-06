/*
 * This file is part of JGrasstools (http://www.jgrasstools.org)
 * (C) HydroloGIS - www.hydrologis.com 
 * 
 * JGrasstools is free software: you can redistribute it and/or modify
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
package org.jgrasstools.modules;

import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERCONVERTER_AUTHORCONTACTS;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERCONVERTER_AUTHORNAMES;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERCONVERTER_DESCRIPTION;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERCONVERTER_KEYWORDS;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERCONVERTER_LABEL;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERCONVERTER_LICENSE;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERCONVERTER_NAME;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERCONVERTER_STATUS;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERCONVERTER_inRaster_DESCRIPTION;
import static org.jgrasstools.gears.i18n.GearsMessages.OMSRASTERCONVERTER_outRaster_DESCRIPTION;
import oms3.annotations.Author;
import oms3.annotations.Description;
import oms3.annotations.Execute;
import oms3.annotations.In;
import oms3.annotations.Keywords;
import oms3.annotations.Label;
import oms3.annotations.License;
import oms3.annotations.Name;
import oms3.annotations.Status;
import oms3.annotations.UI;

import org.jgrasstools.gears.libs.modules.JGTConstants;
import org.jgrasstools.gears.libs.modules.JGTModel;
import org.jgrasstools.gears.modules.r.rasterconverter.OmsRasterConverter;

@Description(OMSRASTERCONVERTER_DESCRIPTION)
@Author(name = OMSRASTERCONVERTER_AUTHORNAMES, contact = OMSRASTERCONVERTER_AUTHORCONTACTS)
@Keywords(OMSRASTERCONVERTER_KEYWORDS)
@Label(OMSRASTERCONVERTER_LABEL)
@Name("_" + OMSRASTERCONVERTER_NAME)
@Status(OMSRASTERCONVERTER_STATUS)
@License(OMSRASTERCONVERTER_LICENSE)
public class RasterConverter extends JGTModel {

    @Description(OMSRASTERCONVERTER_inRaster_DESCRIPTION)
    @UI(JGTConstants.FILEIN_UI_HINT)
    @In
    public String inRaster;

    @Description(OMSRASTERCONVERTER_outRaster_DESCRIPTION)
    @UI(JGTConstants.FILEOUT_UI_HINT)
    @In
    public String outRaster;

    @Execute
    public void process() throws Exception {
        OmsRasterConverter rasterconverter = new OmsRasterConverter();
        rasterconverter.inRaster = getRaster(inRaster);
        rasterconverter.pm = pm;
        rasterconverter.doProcess = doProcess;
        rasterconverter.doReset = doReset;
        rasterconverter.process();
        dumpRaster(rasterconverter.outRaster, outRaster);
    }
}
