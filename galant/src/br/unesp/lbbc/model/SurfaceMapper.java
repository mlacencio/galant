package br.unesp.lbbc.model;

import java.util.HashMap;
import java.util.List;

import org.jzy3d.maths.Coord2d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.builder.Mapper;

public class SurfaceMapper extends Mapper {

	List<Coord3d> list;
	
	public SurfaceMapper(List<Coord3d> list){ 
		
		this.list = list;
		
	}
	
	@Override
	public double f(double x, double y) {
		
			for (Coord3d coord: list){
				double xr = coord.x;
				double yr = coord.y;
				if (x==xr && y==yr){return coord.z;}
			}
		
		
		return 0.0;
	}

}
