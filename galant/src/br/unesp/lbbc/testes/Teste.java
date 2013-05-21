package br.unesp.lbbc.testes;

import java.util.HashMap;

import br.unesp.lbbc.controller.Smooth;

public class Teste {

	public Teste(){
		
		HashMap<String,double[]> map = new HashMap<String, double[]>();
		double[] a = {0,0,0.5};
		map.put("A",a);
		double[] b = {0,.5,0.6};
		map.put("B",b);
		double[] c = {0.5,0,0.8};
		map.put("C",c);

		Smooth sm = new Smooth();
		double[][] mz = sm.custom(2, map, 2, false);
		
		String s = "da um tempo";
		
	}
	
	
	public static void main(String[] args) {
		Teste t = new Teste();

	}

}
