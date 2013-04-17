package br.unesp.lbbc.model;

import org.jzy3d.plot3d.builder.Mapper;


public class MapperCustom extends Mapper {

	double[][] matrix;
	
	

	public MapperCustom(double[][] matrix) {
		this.matrix=matrix;
		
	}

	public double f(double x, double y) {		
		
		Functions f = new Functions();
		double z= f.splineFunction(matrix,x,y);
		return z;
		
	}
	
	
}