package br.unesp.lbbc.model;

import java.util.HashMap;

import javax.swing.JOptionPane;


public class Functions {

public Functions(){
	
}
	
	/*for each key we have one list of 3 doubles that are the x,y,z coordinates */
	public double getGaussianFunction(HashMap<String, double[]> map, float x, float y,String resolution) {

		String s = resolution;
		double SIGMA = Double.parseDouble(s);
		if (SIGMA==Double.NaN){JOptionPane.showMessageDialog(null,"Type other gauss precision");}
		double A = 1;
		double sumFunction = 0.0;
		double gaussian = 0.0;

		for (String key : map.keySet()) {
			double x0 = map.get(key)[0];
			double y0 = map.get(key)[1];
			double z0 = map.get(key)[2];
			//to find the sum of all z0
			sumFunction = sumFunction + z0;
			// piece of gaussian
			double pg = A * z0
					* exp((-pow(x - x0, 2) - pow(y - y0, 2)) / SIGMA);
			gaussian = gaussian + pg;
			
		}

		
		//normalize z-axis
		
		
		
		
		
		
		return gaussian-sumFunction;
	}

	
	

	public double exp(double valor) {
		return Math.exp(valor);
	}

	public double pow(double valor, double potencia) {
		return Math.pow(valor, potencia);
	}


}
