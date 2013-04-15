package br.unesp.lbbc.testes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRBG;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.factories.AxeFactory;
import org.jzy3d.factories.JzyFactories;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Polygon;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.primitives.axes.ContourAxeBox;
import org.jzy3d.plot3d.primitives.axes.IAxe;
import org.jzy3d.plot3d.rendering.legends.colorbars.ColorbarLegend;
import org.jzy3d.plot3d.rendering.view.View;
import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;


public class TestaJzy extends JPanel {

	JFrame frame = new JFrame();
	private static Chart chart;
	
	
	public TestaJzy(){
		setLayout(new BorderLayout());
		frame.add(this);
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chart = new Chart("awt");
		
		// Creates the 3d object
		
		double data[][] = new double[10][10]; //dimensao x e y 
		
/*		List<Coord3d> coord = new ArrayList<Coord3d>();
		for (int l=0;l<data.length;l++){
			for (int k=0;k<data.length;k++){
				int rand = (int)(Math.random()*10);
				System.out.println(l+" "+k+" "+rand);
				Coord3d c = new Coord3d(l, k, rand); 
				coord.add(c);
				data[l][k] = rand;
				
			}
		}*/
		
		
		double distDataProp [][] = data;
		
		
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
			
			
			

			
	        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
			surface.setWireframeDisplayed(true);
			surface.setWireframeColor(org.jzy3d.colors.Color.BLACK);
			surface.setLegend(new ColorbarLegend(surface,chart.getView().getAxe().getLayout().getZTickProvider(),chart.getView().getAxe().getLayout().getZTickRenderer()));
			surface.setLegendDisplayed(true); 
		
	
			chart.getScene().getGraph().add(surface);

			this.add((Component) chart.getCanvas(), BorderLayout.CENTER);
			
			surface.setWireframeDisplayed(true);
			
			chart.setViewMode(ViewPositionMode.TOP);
			
			frame.setVisible(true);
		
		
	}
	
	
	public static void main(String[] args) {
		TestaJzy t = new TestaJzy();
	}

}
