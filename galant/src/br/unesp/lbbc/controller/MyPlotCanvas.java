package br.unesp.lbbc.controller;

import org.math.plot.canvas.PlotCanvas;

public class MyPlotCanvas extends PlotCanvas {

	@Override
	public void initDrawer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initBasenGrid(double[] min, double[] max) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initBasenGrid() {
		// TODO Auto-generated method stub

	}
	
	 public void setFixedBounds(double[] min, double[] max) {
	        base.setFixedBounds(min, max);
	        resetBase();
	        repaint();
	        revalidate();
	    }

}
