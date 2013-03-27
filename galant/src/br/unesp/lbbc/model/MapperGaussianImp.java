package br.unesp.lbbc.model;

import java.util.HashMap;
import org.jzy3d.plot3d.builder.Mapper;


public class MapperGaussianImp extends Mapper {

	String resolution;
	HashMap<String, double[]> mapA;

	public MapperGaussianImp(HashMap<String, double[]> mapB,String res) {
		this.resolution = res;
		mapA = mapB;

	}

	public double f(double x, double y) {
		Functions f = new Functions();
		double function = f.getGaussianFunction(mapA, (float)x, (float)y,resolution);
		return (function);
	}
	
}