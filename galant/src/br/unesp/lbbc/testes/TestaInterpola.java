package br.unesp.lbbc.testes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.math.ArgumentOutsideDomainException;
import org.apache.commons.math.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.factories.AxeFactory;
import org.jzy3d.factories.JzyFactories;
import org.jzy3d.global.Settings;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Coord2d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.primitives.axes.ContourAxeBox;
import org.jzy3d.plot3d.primitives.axes.IAxe;
import org.jzy3d.plot3d.rendering.legends.colorbars.ColorbarLegend;
import org.jzy3d.plot3d.rendering.view.View;
import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;

import br.unesp.lbbc.controller.Mapping;
import br.unesp.lbbc.controller.Spline;
import br.unesp.lbbc.model.SurfaceMapper;


public class TestaInterpola extends JPanel {

	
	private static Chart chart;
	
	public TestaInterpola(){
		
		setLayout(new BorderLayout());
		chart = new Chart("awt");
		HashMap<String,double[]> originalPoints = new HashMap<String, double[]>();
		double[] a = {0.1,0.1,0.8};
		originalPoints.put("A",a);
		double[] b = {0.4,0.4,0.6};
		originalPoints.put("B",b);
		double[] c = {0.7,0.7,0.3};
		originalPoints.put("C",c);
		
		
		Spline sp = new Spline();
		List<Coord3d> list;// = sp.generateSpline(originalPoints,2, 1);
	
		
		/*List<Coord3d> list = new ArrayList<Coord3d>();
		Coord3d c1 = new Coord3d(0, 0, 0);
		Coord3d c2 = new Coord3d(0, 1, 0.2);
		Coord3d c3 = new Coord3d(0, 2, 0.9);
		Coord3d c4 = new Coord3d(1, 0, 0);
		Coord3d c5 = new Coord3d(1, 1, 0.5);
		Coord3d c6 = new Coord3d(1, 2, 0);
		Coord3d c7 = new Coord3d(2, 0, 0.1);
		Coord3d c8 = new Coord3d(2, 1, 0.3);
		Coord3d c9 = new Coord3d(2, 2, 0);*/
		
		//list.add(c1);list.add(c2);list.add(c3);list.add(c4);list.add(c5);list.add(c6);list.add(c7);list.add(c8);list.add(c9);
		
		
		Range xyrange = new Range(0, 1);
		//steps = res*(smooth+1)
		int xysteps = (int) (Math.sqrt(list.size())+1);
		OrthonormalGrid grid = new OrthonormalGrid(xyrange, xysteps);
		Mapper mapper = new SurfaceMapper(list);
		Shape surface = Builder.buildOrthonormal(grid, mapper);
		
		
		
		surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface
				.getBounds().getZmin(), surface.getBounds().getZmax(),
				new org.jzy3d.colors.Color(1, 1, 1, 1f)));
		surface.setWireframeDisplayed(true);
		surface.setLegend(new ColorbarLegend(surface,chart.getView().getAxe().getLayout().getZTickProvider(),chart.getView().getAxe().getLayout().getZTickRenderer()));
		surface.setLegendDisplayed(true); 
	
		JzyFactories.axe = new AxeFactory() {
			
			@Override
			public IAxe getInstance(BoundingBox3d box, View view) {
	
				ContourAxeBox axe = new ContourAxeBox(box);
				axe.setView(view);
				return axe;
			}
		};
		chart.getScene().getGraph().add(surface);

		this.add((Component) chart.getCanvas(), BorderLayout.CENTER);
		
		
		chart.setViewMode(ViewPositionMode.TOP);
		
		
		
		
	}
	
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new TestaInterpola());
		frame.setVisible(true);
		frame.setExtendedState(6);

	}

}
