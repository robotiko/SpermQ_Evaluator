package spqEval.pdf;

/** 
===============================================================================
* SpermQEvaluator_.java Version 1.0.5
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation
* (http://www.gnu.org/licenses/gpl.txt )
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public
* License along with this program.  If not, see
* <http://www.gnu.org/licenses/gpl-3.0.html>.
*
* Copyright (C) 2018: Jan N. Hansen and Sebastian Raßmann;
*         research group Biophysical Imaging, Institute of Innate 
Immunity, Bonn, Germany
*         (http://www.iii.uni-bonn.de/en/wachten_lab/).
*
* Funding: DFG priority program SPP 1726 “Microswimmers"
*
* For any questions please feel free to contact me (jan.hansen@uni-bonn.de).
*
* 
=============================================================================== 
*/

import java.awt.Color;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.data.xy.XYSeries;

import spqEval.Result;
import spqEval.plot.Plot;

public class KAPlot extends PDFPlot {
	
	final int nOfPlots = 2;
	int plotWidth, plotHeight;
	XYSeries average, range;
	
	static final Color [] rangeColors = {Color.RED};
	static final Color [] averageColors = {Color.BLUE};
	
	int xMaxRa = 0, xMaxAv = 0, xMinRa, xMinAv;
	double yMinAv = 0, yMinRa = 0, yMaxAv = Float.NEGATIVE_INFINITY, yMaxRa = Float.NEGATIVE_INFINITY;
	int highestUndefined;
	int xBaseValue = 50;
	double yBaseValueAv, yBaseValueRa;
	double arcLengthMicron;
	
	static final String nameOfAveragePlot = "KA_average_plot.png";
	static final String nameOfRangePlot = "KA_range_plot.png";
	String rangePlotPath, averagePlotPath;
		
	int rX0, rY0, rW, rH;
	int aX0, aY0, aW, aH;

	int spaceLeftDesc;
	
	double xMax;
	int xMin = 0;

	public KAPlot(int posX, int posY, int width, int height) {
		
		super("FFT_plot");
		plotWidth = width;
		plotHeight = height;
		x = posX;
		y = posY;
		rangePlotPath = targetPath + nameOfAveragePlot;
		averagePlotPath = targetPath + nameOfRangePlot;
		
		desc = "upper panel: local bending amplitude, bottom panel: local bending asymmetry";
		
		getData();

		setXCalibration();

		renderPlot();
		addPlot();

	}
	
	private void addPlot() {
		
		spaceLeftDesc = PDFTools.calculateMaxWidth(pdt.subDescSize, "0000") + pdt.space;
		rW = plotWidth;
		rH = plotHeight / 2 - pdt.space*2;
		rX0 = x + spaceLeftDesc + pdt.subDescSize;
		rY0 = y - pdt.header - rH;
		
		aW = rW;
		aH = plotHeight / 2 - pdt.space*2;
		aX0 = rX0;
		aY0 = rY0 -aH - pdt.space*2;
		
		try {			
			addDesc();
			PDImageXObject rangePlot = PDImageXObject.createFromFile(rangePlotPath, doc);
			PDImageXObject averagePlot = PDImageXObject.createFromFile(averagePlotPath, doc);
			
			cts.drawImage(rangePlot, rX0, rY0, rW, rH);	
			cts.drawImage(averagePlot, aX0, aY0, aW, aH);
					
			addDescRange();
			addDescAverage();
			addLowerDesc();
			
		}
		catch(Exception e){
			System.out.println("error! - caught Exception !");
			System.out.println(e.getCause());
			String out = "";
			for(int err = 0; err < e.getStackTrace().length; err++){
				out += " \n " + e.getStackTrace()[err].toString();
			}
			System.out.println(out);
		}
	}
	
	protected void renderPlot() {
		
		Plot plot1 = new Plot(plotWidth*pdt.resolution/nOfPlots, plotHeight*pdt.resolution/nOfPlots, rangePlotPath, nameOfRangePlot, range);
		plot1.setLineColor(rangeColors);
		plot1.setRanges(xMin, yMinRa, xMax, yMaxRa);
		plot1.renderPlot(false, true, pdt.plotLineWidthFactor*pdt.resolution/nOfPlots);
		
		Plot plot2 = new Plot(plotWidth*pdt.resolution/nOfPlots, plotHeight*pdt.resolution/nOfPlots, averagePlotPath,  nameOfAveragePlot , average);
		plot2.setLineColor(averageColors);
		plot2.setRanges(xMin, yMinAv, xMax, yMaxAv);
		plot2.renderPlot(false, true, pdt.plotLineWidthFactor*pdt.resolution/nOfPlots);
	}
	
	private void getData() {
		
		range = new XYSeries ("range_plot");
		average = new XYSeries ("average_plot");

		Result r = new Result(PDFPage.sourcePath, coverageThreshold);
		double [][] rawData = r.getFlagellarParameterResult("cAng", (int)slicesPerCycle);

		int arcL;
		for (arcL = 0; arcL < rawData.length; arcL++) {
			if(rawData[arcL][0] > Double.NEGATIVE_INFINITY && rawData[arcL][1] > Double.NEGATIVE_INFINITY 
					&& !Double.isNaN(rawData[arcL][0]) && !Double.isNaN(rawData[arcL][1])) {
				range.add(arcL, rawData[arcL][1] - rawData[arcL][0]);
			}
			if(rawData[arcL][3] > Double.NEGATIVE_INFINITY && !Double.isNaN(rawData[arcL][3])) {
//				System.out.println(arcL + ": " + rawData[arcL][3]);
				average.add(arcL, rawData[arcL][3]);
			}
		}
		xMaxRa = (int) (range.getMaxX());
		xMinRa = (int) (range.getMinX());
		yMaxRa = range.getMaxY();
		yMinRa = range.getMinY();
		xMinAv = (int) (average.getMinX());
		xMaxAv = (int) (average.getMaxX());
		yMinAv = average.getMinY();
		yMaxAv = average.getMaxY();
//		System.out.println("ranges Ra : " + xMaxRa + "|" + xMinRa + "|" + yMaxRa + "|" + yMinRa);
//		System.out.println("ranges Av : " + xMaxAv + "|" + xMinAv + "|" + yMaxAv + "|" + yMinAv);
	}

	private void setXCalibration() {
		
		if(xMaxRa < xMaxAv) {
			xMax = (int) xMaxAv;
		}
		else {
			xMax = (int) xMaxRa;
		}
				
		double xInUM = xMax * xyCalibration;
		xBaseValue = (int) PDFTools.getBaseValue(xInUM - 0, 7, 0.25,1,2,5);
		double absoluteArcLenght = xInUM;
		xInUM = PDFTools.getNextMultipleOf(xBaseValue, xInUM);		
		xMax = (xMax/(absoluteArcLenght / xInUM));
		this.arcLengthMicron = xInUM;
		
		yBaseValueAv = PDFTools.getBaseValue(yMaxAv - yMinAv, 4, 0.25,1,2,5);
		yMaxAv = PDFTools.getNextMultipleOf(yBaseValueAv, yMaxAv);
		yMinAv = PDFTools.getNextMultipleOf(yBaseValueAv, yMinAv) - yBaseValueAv;
		yBaseValueRa = PDFTools.getBaseValue(yMaxRa - yMinRa, 4, 0.25,1,2,5);
		yMaxRa = PDFTools.getNextMultipleOf(yBaseValueRa, yMaxRa);
		yMinRa = PDFTools.getNextMultipleOf(yBaseValueRa, yMinRa) - yBaseValueRa;		
	}
		
	@SuppressWarnings("deprecation")
	private void addLowerDesc() {
		
		try {
			cts.drawLine(aX0, aY0, aX0 + aW + pdt.lineWidth, aY0);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		int y0 = aY0 - pdt.space;
		float x;
		float numberOfIndicators = (int) ( arcLengthMicron/ xBaseValue);
		double desValue;
		
		for(int z = 0; z <= numberOfIndicators; z++) {
			try {
				x = aX0 + aW * z/numberOfIndicators;
				cts.drawLine(x, y0, x, aY0);
				desValue = (z/numberOfIndicators * arcLengthMicron);
				PDFTools.insertTextBoxXCentered(cts, x, y0 - pdt.space, Long.toString(Math.round(desValue)) , pdt.subDescSize);
			} catch (IOException e) {
				System.out.println("KAPlot exception in addSideDesc");
				System.out.println(e.getCause());
				String out = "";
				for(int err = 0; err < e.getStackTrace().length; err++){
					out += " \n " + e.getStackTrace()[err].toString();
				}
				System.out.println(out);
			}
		}
		PDFTools.insertTextBoxXCentered(cts, aX0 + aW/2, aY0 - pdt.space*2 - pdt.descSize, "arc length (µm)", pdt.subDescSize);
		
	}
	
	@SuppressWarnings("deprecation")
	private void addDescRange() {
		float y = 0;
		int x1 = rX0 - pdt.space;
		int x2 = rX0 - pdt.space*2;
		float yCorrectionNumbers = pdt.subDescSize/2;
		
		int numberOfIndicators = (int) ((yMaxRa - yMinRa) / yBaseValueRa);
		
		for(int z = 0; z <= numberOfIndicators; z++) {
			try {
				y = rY0 + rH * (numberOfIndicators-z)/numberOfIndicators;
				cts.drawLine(x1, y, rX0, y);
			} catch (IOException e) {
				System.out.println("KAPlot exception in addSideDesc");
				System.out.println(e.getCause());
				String out = "";
				for(int err = 0; err < e.getStackTrace().length; err++){
					out += " \n " + e.getStackTrace()[err].toString();
				}
				System.out.println(out);
			}
		}
		PDFTools.insertTextBoxToRightBoundYCentrated(cts, x2, rY0 + yCorrectionNumbers, Integer.toString((int) (yMinRa)), pdt.subDescSize);
		PDFTools.insertTextBoxToRightBoundYCentrated(cts, x2, rY0 + rH + yCorrectionNumbers, Integer.toString((int) (yMaxRa)), pdt.subDescSize);
		try {
			cts.drawLine(rX0, rY0 - pdt.lineWidth, rX0, rY0 + rH + pdt.lineWidth);
		} catch (IOException e) {
			System.out.println("KAPlot exception in addSideDesc 2");
			System.out.println(e.getCause());
			String out = "";
			for(int err = 0; err < e.getStackTrace().length; err++){
				out += " \n " + e.getStackTrace()[err].toString();
			}
			System.out.println(out);
		}
		PDFTools.insertTextBoxRotatedUpward(cts, x, rY0+rH/2, "range of curvature angle", pdt.subDescSize);
		PDFTools.insertTextBoxRotatedUpward(cts, x + pdt.space+ pdt.subDescSize, rY0+rH/2, "(°)", pdt.subDescSize);
	}
	
	@SuppressWarnings("deprecation")
	private void addDescAverage() {
		float y = 0;
		int x1 = aX0 - pdt.space;
		int x2 = aX0 - pdt.space*2;
		float yCorrectionNumbers = pdt.subDescSize/2;
		
		int numberOfIndicators = (int) ((yMaxAv-yMinAv) / yBaseValueAv);
//		System.out.println("xMaxAv" + yMaxAv);
//		System.out.println("xBaseValueAv" + yBaseValueAv);
		for(int z = 0; z <= numberOfIndicators; z++) {
			try {
				y = aY0 + aH * (numberOfIndicators - z)/numberOfIndicators;
				cts.drawLine(x1, y, aX0, y);
			} catch (IOException e) {
				System.out.println("KAPlot exception in addDescAvg");
				System.out.println(e.getCause());
				String out = "";
				for(int err = 0; err < e.getStackTrace().length; err++){
					out += " \n " + e.getStackTrace()[err].toString();
				}
				System.out.println(out);
			}
		}
		PDFTools.insertTextBoxToRightBoundYCentrated(cts, x2, aY0 + yCorrectionNumbers, Integer.toString((int) (yMinAv)), pdt.subDescSize);
		PDFTools.insertTextBoxToRightBound(cts, x2, aY0 + aH + yCorrectionNumbers, Integer.toString((int) (yMaxAv)), pdt.subDescSize);
		try {
			cts.drawLine(aX0, aY0, aX0, aY0 + aH + pdt.lineWidth);
		} catch (IOException e) {
			System.out.println("KAPlot exception in addDescAvg 2");
			System.out.println(e.getCause());
			String out = "";
			for(int err = 0; err < e.getStackTrace().length; err++){
				out += " \n " + e.getStackTrace()[err].toString();
			}
			System.out.println(out);
		}
		PDFTools.insertTextBoxRotatedUpward(cts, x, aY0+aH/2, "average curvature angle", pdt.subDescSize);
		PDFTools.insertTextBoxRotatedUpward(cts, x + pdt.space+ pdt.subDescSize, aY0+aH/2, "(°)", pdt.subDescSize);
	}	
}
