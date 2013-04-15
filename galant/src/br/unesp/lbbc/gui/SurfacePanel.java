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
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.contour.DefaultContourColoringPolicy;
import org.jzy3d.contour.IContourGenerator;
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
import org.math.plot.Plot2DPanel;

import flanagan.analysis.SurfaceSmooth;

import br.unesp.lbbc.controller.ExpControl;
import br.unesp.lbbc.controller.Mapping;
import br.unesp.lbbc.controller.Spline;
import br.unesp.lbbc.model.MapperGaussianImp;
import br.unesp.lbbc.model.SurfaceMapper;
import br.unesp.lbbc.util.Util;


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


	public SurfacePanel() {
		
		Settings.getInstance().setHardwareAccelerated(true);
		setLayout(new BorderLayout());
		chart = new Chart("awt");
	//	chart.addController(new ChartKeyController());
	//	chart.addController(new ChartMouseController());
		mapping = new Mapping();
		
		
	}
	
	/**
	 * if there is only one attribute, the control (at2) is passed as null and the boolean log is true, but 
	 * it won't be used.
	 * */
	
	public void drawGaussian(String at1,String at2,boolean isSelected2D,String resolution,boolean log) {
		HashMap<String, double[]> map;
		
		if (at2==null){
			map = mapping.getCompleteHash(at1,log);
			}
		else{
			ExpControl expControl = new ExpControl();
			
			if (log) {
				map = expControl.calculeLog(at1, at2,log);
			}
			else {
				map = expControl.calcule(at1, at2);
			}
		}
		
		//normalize map: precisa normalizar pois eu defini abaixo o alcance do eixo x e y entre 0 e 1.
		//map = Util.Normalize(map);
		
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
	
	public void drawCustom(String at1,String at2, boolean isSelected2D,int res,int smooth,boolean log) {

		
		Mapping map = new Mapping();
		HashMap<String, double[]> originalPointsHash = map.getCompleteHash(at1, log);
		Spline sp = new Spline();
		double [][] originalPoints = new double[originalPointsHash.size()][3];  //[[0.5865384615384616, 1.0, 0.0], [0.0, 0.8135593220338984, 1.0], [1.0, 0.5536723163841808, 0.5], [0.1346153846153846, 0.536723163841808, 1.0], [0.2403846153846154, 0.0, 0.0]]
		int cont=0;
		for (String key: originalPointsHash.keySet()){
			originalPoints[cont][0] = originalPointsHash.get(key)[0];
			originalPoints[cont][1] = originalPointsHash.get(key)[1];
			originalPoints[cont][2] = originalPointsHash.get(key)[2];
			cont = cont+1;
		}
		
		
		
		double distDataProp[][] = sp.geraMatrizNey(res, originalPoints); //[[0.005569505418526472, 0.0, 0.0, 0.0], [0.006848548734301809, 0.0, 0.0, 0.0], [0.005887441429824172, 0.0, 0.0, 0.0], [0.0043630861887793675, 0.0, 0.0, 0.0]]
		
		List<Polygon> polygons = new ArrayList<Polygon>();
		
		for (int i = 0; i < distDataProp.length - 1; i++) {
			for (int j = 0; j < distDataProp[i].length - 1; j++) {
				Polygon polygon = new Polygon();
				polygon.add(new Point(new Coord3d(i, j, distDataProp[i][j])));
				polygon.add(new Point(new Coord3d(i, j + 1,	distDataProp[i][j + 1])));
				polygon.add(new Point(new Coord3d(i + 1, j + 1,	distDataProp[i + 1][j + 1])));
				polygon.add(new Point(new Coord3d(i + 1, j,	distDataProp[i + 1][j])));
				polygons.add(polygon);
			}
		}
		
		
		
			Shape surface = new Shape(polygons);
			
			
			

			
			surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), 0, 1));
			surface.setWireframeDisplayed(true);
			surface.setWireframeColor(org.jzy3d.colors.Color.BLACK);
			surface.setLegend(new ColorbarLegend(surface,chart.getView().getAxe().getLayout().getZTickProvider(),chart.getView().getAxe().getLayout().getZTickRenderer()));
			surface.setLegendDisplayed(true); 
		
	
			
			
			
			
			
			
			
			chart.getScene().getGraph().add(surface);

			this.add((Component) chart.getCanvas(), BorderLayout.CENTER);
			
			surface.setWireframeDisplayed(true);
			
			chart.setViewMode(ViewPositionMode.TOP);
		
		
		
		
		

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