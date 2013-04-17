package br.unesp.lbbc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.math.ArgumentOutsideDomainException;
import org.apache.commons.math.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction;
import org.jzy3d.maths.Coord3d;


public class Functions {

	
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

	
	public double splineFunction(double map[][], double x, double y){
		
		int res = map.length;
		
		for (int i=0; i<res;i++){
			double[] xarray = new double[res];
			double[] xzarray = map[i];
			SplineInterpolator spix = new SplineInterpolator();
			PolynomialSplineFunction psfx = spix.interpolate(xarray,xzarray); 
			for (int j=0;i<res;i++){
				double[] yarray = new double[res];
				double[] yzarray = map[j];
				SplineInterpolator spiy = new SplineInterpolator();
				PolynomialSplineFunction psfy = spiy.interpolate(yarray,yzarray); 
			}
		}
			
		return 0;
	}
	/**
	 * Enter a double map and obtain a set of coordinates, interpolated 
	 * @param map
	 * @param smooth
	 * @return
	 */
	//menor smooth possivel 2
	public List<Coord3d> getInterpolatedArray(double map[][], int smooth){
	
	int res = map.length;
	int nres = res*(smooth+1)-smooth; //nova resolucao
	double[] eixoArray = new double[res];
	double[] newEixoArray = new double[nres];
	double[][] newmat = new double[nres][nres];
	
	double[][] mapT = transpose(map);   //matrix transposta
	
	for (int i = 0; i < eixoArray.length; i++) {
		eixoArray[i] = i; //********************************NORMALIZAR AQUI DEPOIS
	}
	
	for (int i = 0; i < newEixoArray.length; i++) {
		newEixoArray[i] = i; //*****************************NORMALIZAR AQUI DEPOIS
	}
	
			
	List<Coord3d> coords = new ArrayList<Coord3d>();
	
	//aqui gera uma array que sera usa da interpolacao do y
	for (int i=0; i<res;i++){
		double[] xzarray = map[i];
		double[] newXZarray = new double[nres];
		
		SplineInterpolator spix = new SplineInterpolator();
		PolynomialSplineFunction psfx = spix.interpolate(eixoArray,xzarray);
		//for para calcular nova arrayx
		for (int j = 0; j < newEixoArray.length; j++) {
			try {
				newmat[i][j]=psfx.value(j);
			} catch (ArgumentOutsideDomainException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
		int daumtempo = 1;
		return null;
	
	}
	
	public double exp(double valor) {
		return Math.exp(valor);
	}

	public double pow(double valor, double potencia) {
		return Math.pow(valor, potencia);
	}

	
	public double[][] transpose(double[][] mat){
		
		double[][] tmat = new double[mat.length][mat.length];
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < tmat.length; j++) {
				tmat[i][j] = mat[j][i];
			}
		}
		
		return tmat;
	}
	
}
