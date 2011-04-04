package org.jgrasstools.gears.modules;

import java.awt.image.RenderedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.geotools.coverage.grid.GridCoverage2D;
import org.jgrasstools.gears.modules.r.mapcalc.Mapcalc;
import org.jgrasstools.gears.utils.HMTestCase;
import org.jgrasstools.gears.utils.HMTestMaps;
import org.jgrasstools.gears.utils.coverage.CoverageUtilities;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

@SuppressWarnings("nls")
public class TestMapcalc extends HMTestCase {
    public void testMapcalc() throws Exception {

        double[][] elevationData = HMTestMaps.pitData;
        HashMap<String, Double> envelopeParams = HMTestMaps.envelopeParams;
        CoordinateReferenceSystem crs = HMTestMaps.crs;
        GridCoverage2D elevationCoverage = CoverageUtilities.buildCoverage("ele", elevationData, envelopeParams, crs, true);

        List<GridCoverage2D> maps = Arrays.asList(elevationCoverage);

        Mapcalc mapcalc = new Mapcalc();
        mapcalc.inMaps = maps;
        mapcalc.pFunction = "images{ele=read; dest=write;} dest=ele*2-ele + sqrt(ele)^2-exp(log(ele));";
        mapcalc.process();

        GridCoverage2D outMap = mapcalc.outMap;

        RenderedImage renderedImage = outMap.getRenderedImage();
        printImage(renderedImage);
        checkMatrixEqual(renderedImage, HMTestMaps.pitData, 0.000000001);
    }

    public void testMapcalc2() throws Exception {

        double[][] elevationData = HMTestMaps.flowData;
        HashMap<String, Double> envelopeParams = HMTestMaps.envelopeParams;
        CoordinateReferenceSystem crs = HMTestMaps.crs;
        GridCoverage2D elevationCoverage = CoverageUtilities.buildCoverage("flow", elevationData, envelopeParams, crs, true);

        List<GridCoverage2D> maps = Arrays.asList(elevationCoverage);

        Mapcalc mapcalc = new Mapcalc();
        mapcalc.inMaps = maps;
        mapcalc.pFunction = "images{flow=read; dest=write;} dest = (flow+flow)/2;";

        mapcalc.process();

        GridCoverage2D outMap = mapcalc.outMap;
        RenderedImage renderedImage = outMap.getRenderedImage();
        // printImage(renderedImage);
        checkMatrixEqual(renderedImage, HMTestMaps.flowData, 0.000000001);
    }

    public void testMapcalc3() throws Exception {
        double[][] elevationData = HMTestMaps.pitData;
        HashMap<String, Double> envelopeParams = HMTestMaps.envelopeParams;
        CoordinateReferenceSystem crs = HMTestMaps.crs;
        GridCoverage2D elevationCoverage = CoverageUtilities.buildCoverage("ele", elevationData, envelopeParams, crs, true);

        List<GridCoverage2D> maps = Arrays.asList(elevationCoverage);

        Mapcalc mapcalc = new Mapcalc();
        mapcalc.inMaps = maps;
        mapcalc.pFunction = "images{ele=read; dest=write;} dest = xstep()*ystep();";
        mapcalc.process();

        GridCoverage2D outMap = mapcalc.outMap;

        RenderedImage renderedImage = outMap.getRenderedImage();
        printImage(renderedImage);

        double[][] data = new double[][]{//
        {900, 900, 900, 900, 900, 900, 900, 900}, //
                {900, 900, 900, 900, 900, 900, 900, 900}, //
                {900, 900, 900, 900, 900, 900, 900, 900}, //
                {900, 900, 900, 900, 900, 900, 900, 900}, //
                {900, 900, 900, 900, 900, 900, 900, 900}, //
                {900, 900, 900, 900, 900, 900, 900, 900}, //
                {900, 900, 900, 900, 900, 900, 900, 900}, //
                {900, 900, 900, 900, 900, 900, 900, 900}};
        checkMatrixEqual(renderedImage, data, 0.000000001);
    }

    public static void main( String[] args ) throws Exception {
        new TestMapcalc().testMapcalc();
    }
}
