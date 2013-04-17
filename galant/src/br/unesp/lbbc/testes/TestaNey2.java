package br.unesp.lbbc.testes;

import java.util.HashMap;

import br.unesp.lbbc.controller.Spline;

public class TestaNey2 {

	
	public TestaNey2(){
		
		Spline sp = new Spline();
		double[] x1 = {0.5,0.5,1};
		double[] x2 = {0.6,0.8,1};
		double[] x3 = {0.8,0.6,1};
		
		double data [][] = {x1,x2,x3};
		
		double[][] result = sp.geraMatrizNey(4, data);
		
		System.out.println("da um tempo");
		
	}
	
	
	
	public static void main(String[] args) {
		TestaNey2 t = new TestaNey2();
	}

	
}
