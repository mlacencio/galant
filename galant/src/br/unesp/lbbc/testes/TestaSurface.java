package br.unesp.lbbc.testes;

import java.awt.Color;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

public class TestaSurface {

	public TestaSurface (){
		
		JFrame frame = new JFrame();
		frame.setSize(500,500);
		frame.setLocation(1000,100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Plot2DPanel plot = new Plot2DPanel();
		double [] a = {0,0,0};
		double [] b = {10,10,10};
		
		plot.getAxis(0).setOrigin(a);
		plot.repaint();
		
		double[] x1 = {0,0,3};
		double[] x2 = {0,.1,1};
		double[] x3 = {.2,.2,0};
		double[] x4 = {.3,.3,5};
		double[] x6 = {.6,.6,10};
		
		double[] x5 = {1,1,3}; //esse ponto fica fora
		
		double [][] data = {x3,x2,x1,x4,x5,x6};
		
		
		plot.addCloudPlot("cloud",data,10,10);
		
		
		
		
	
		frame.add(plot);
		frame.setVisible(true);
		
	}
	public static void main(String[] args) {
		TestaSurface t = new TestaSurface();
	}
	
	
	

}






