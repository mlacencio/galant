package br.unesp.lbbc.testes;

import java.util.HashMap;

import org.jzy3d.maths.Coord2d;
import org.jzy3d.maths.Coord3d;

public class StupidTest {

	public StupidTest(){
		
		HashMap<Coord2d,Double> array = new HashMap<Coord2d, Double>();
		
		Coord2d a = new Coord2d(0.0,0.0);
		array.put(a, 1.0);
		
		double x = 0.0;
		double y = 0.0;
		Coord2d b = new Coord2d(x,y);
		System.out.println(array.get(a));
		
		
		
	}
	
	
	public static void main(String[] args) {
		StupidTest t =new StupidTest();
	}

}


	
	
	
