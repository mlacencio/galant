package br.unesp.lbbc.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.keyboard.ChartKeyController;
import org.jzy3d.chart.controllers.mouse.ChartMouseController;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.factories.AxeFactory;
import org.jzy3d.factories.JzyFactories;
import org.jzy3d.global.Settings;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Polygon;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.primitives.axes.ContourAxeBox;
import org.jzy3d.plot3d.primitives.axes.IAxe;
import org.jzy3d.plot3d.primitives.axes.layout.providers.ITickProvider;
import org.jzy3d.plot3d.primitives.axes.layout.renderers.ITickRenderer;
import org.jzy3d.plot3d.rendering.legends.colorbars.ColorbarLegend;
import org.jzy3d.plot3d.rendering.view.View;
import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;

import flanagan.analysis.SurfaceSmooth;

import br.unesp.lbbc.controller.ExpControl;
import br.unesp.lbbc.controller.Mapping;
import br.unesp.lbbc.controller.Spline;
import br.unesp.lbbc.model.MapperGaussianImp;
import br.unesp.lbbc.util.Util;
//import flanagan.analysis.SurfaceSmooth;

/**
 * To draw the surface it's needed to know what attribute and function
 * */

public class SurfacePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Mapping mapping;
	private static Chart chart;
	private static ChartMouseController mouseController;

	public static int TYPE_BOOLEAN = 1;
	public static int TYPE_FLOATING = 2;
	public static int TYPE_INTEGER = 3;
	public static int TYPE_STRING = 4;
	public static int TYPE_SIMPLE_LIST = -2;
	public static int TYPE_SIMPLE_MAP = -3;
	public static int TYPE_COMPLEX = -4;
	public static int TYPE_UNDEFINED = -1;

	public SurfacePanel() {
		
		Settings.getInstance().setHardwareAccelerated(true);
		setLayout(new BorderLayout());
		chart = new Chart("awt");
		chart.addController(new ChartKeyController());
		chart.addController(new ChartMouseController());
		mapping = new Mapping();
		
		
	}
	
	/**
	 * if there is only one attribute, the control (at2) is passed as null and the boolean log is true, but 
	 * it won't be used.
	 * */
	
	public void drawGaussian(String at1,String at2,boolean isSelected2D,String resolution,boolean log) {
		HashMap<String, double[]> map;
		
		if (at2==null){
			map = mapping.getCompleteHash(at1);
			}
		else{
			ExpControl expControl = new ExpControl();
			
			if (log) {
				map = expControl.calculeLog(at1, at2);
			}
			else {
				map = expControl.calcule(at1, at2);
			}
		}
		
		//normalize map: precisa normalizar pois eu defini abaixo o alcance do eixo x e y entre 0 e 1.
		map = Util.Normalize(map);
		
		Mapper gaussianMapper = new MapperGaussianImp(map,resolution);
		Range range = new Range(-0.1, 1.1);
		
		int steps = 80;
		
		

		//final Shape surface = (Shape) Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), gaussianMapper);
		
		
		final Shape surface = (Shape) Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), gaussianMapper);
		
		surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface
				.getBounds().getZmin(), surface.getBounds().getZmax(),
				new Color(1, 1, 1, .5f)));
		
		
		surface.setFaceDisplayed(true);
		
		surface.setWireframeDisplayed(false);	
		
		
		chart.getScene().getGraph().add(surface);
		
		
		ITickProvider provider = new ITickProvider() {
			
			@Override
			public int getDefaultSteps() {
				// TODO Auto-generated method stub
				return 3;
			}
			
			@Override
			public float[] generateTicks(float min, float max, int steps) {
				float value []={0,1,3} ;
				return value;
			}
			
			@Override
			public float[] generateTicks(float min, float max) {
				float value []={0,1,3} ;
				return value;
			}
		};
		
		ITickRenderer renderer = new ITickRenderer() {
			
			@Override
			public String format(float value) {
				// TODO Auto-generated method stub
				return String.valueOf(value);
			}
		};
		
		
		 surface.setLegend(new ColorbarLegend(surface,provider,renderer));
		 surface.setLegendDisplayed(true); // opens a colorbar on the right part of the display 
		
		 
		this.add((Component) chart.getCanvas(), BorderLayout.CENTER);
		if (isSelected2D == true){
			chart.setViewMode(ViewPositionMode.TOP);
		}
			
		
		
		
	}

	
	
	/**
	 * if there is only one attribute, the control (at2) is passed as null
	 * */
	
	public void drawCustom(String at1,String at2, boolean isSelected2D,float resolution,int smoothness,boolean log) {

		HashMap<String, double[]> map;
		
		if (at2==null){
			map = mapping.getCompleteHash(at1);}
		else{
			ExpControl expControl = new ExpControl();
			if (log) {
				map = expControl.calculeLog(at1, at2);
			}
			else {
				map = expControl.calcule(at1, at2);
			}
		}
		//normalize map
				map = Util.Normalize(map);
		
		Spline sp = new Spline();
		double[][] zmat;
		float res = resolution;
		zmat = sp.generateControlPoints(map, (int) res);

		
		double[][] distDataProp;
		
		if (at1==(at2)) {//se atributos sao iguais nao suaviza
			distDataProp = (zmat);
		}
		else{
			SurfaceSmooth smooth = new SurfaceSmooth(zmat);
			distDataProp = smooth.movingAverage(smoothness);
			distDataProp = Util.normalizeDouble(distDataProp);
		}
		
		List<Polygon> polygons = new ArrayList<Polygon>();
	
		for (int i = 0; i < distDataProp.length - 1; i++) {
			for (int j = 0; j < distDataProp[i].length - 1; j++) {
				Polygon polygon = new Polygon();
				polygon.add(new Point(new Coord3d(i, j, distDataProp[i][j])));
				polygon.add(new Point(new Coord3d(i, j + 1,
						distDataProp[i][j + 1])));
				polygon.add(new Point(new Coord3d(i + 1, j + 1,
						distDataProp[i + 1][j + 1])));
				polygon.add(new Point(new Coord3d(i + 1, j,
						distDataProp[i + 1][j])));
				polygons.add(polygon);
			}
		}
		// Creates the 3d object
	
		Shape surface = new Shape(polygons);
	
		surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface
				.getBounds().getZmin(), surface.getBounds().getZmax(),
				new org.jzy3d.colors.Color(1, 1, 1, 1f)));
		surface.setWireframeDisplayed(false);
		surface.setWireframeColor(org.jzy3d.colors.Color.BLACK);
		surface.setLegend(new ColorbarLegend(surface,chart.getView().getAxe().getLayout().getZTickProvider(),chart.getView().getAxe().getLayout().getZTickRenderer()));
		surface.setLegendDisplayed(true); 
	
		JzyFactories.axe = new AxeFactory() {
			
			@Override
			public IAxe getInstance(BoundingBox3d box, View view) {
	
				ContourAxeBox axe = new ContourAxeBox(box);
				axe.setView(view);
			//	box.setZmin(0);
			//	box.setZmax(10);
				return axe;
			}
		};
		chart.getScene().getGraph().add(surface);

		this.add((Component) chart.getCanvas(), BorderLayout.CENTER);
		
		
		if (isSelected2D == true){
		chart.setViewMode(ViewPositionMode.TOP);
	}

	}
	
	public static Chart getChart(){
		return chart;
	}
	
	

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		this.finalize();
		super.finalize();
		
		
	}
	
	public static void screenshot(String filename) throws IOException{
		File output = new File(filename);
		if(!output.getParentFile().exists())
			output.mkdirs();
		ImageIO.write(chart.screenshot(), "png", output);
		JOptionPane.showMessageDialog(null,"Successfully saved");
	}

	
	
}