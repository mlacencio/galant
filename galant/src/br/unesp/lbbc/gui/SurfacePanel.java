package br.unesp.lbbc.gui;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.unesp.lbbc.controller.ExpControl;
import br.unesp.lbbc.controller.Mapping;
import br.unesp.lbbc.controller.Smooth;
import br.unesp.lbbc.util.Gradient;
import br.unesp.lbbc.util.HeatMap;
import br.unesp.lbbc.util.SurfSmooth;
import br.unesp.lbbc.util.Util;

/**
 * To draw the surface it's needed to know what attribute and function
 * */

//public class SurfacePanel extends JPanel {
public class SurfacePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static HeatMap HM;

	public SurfacePanel() {

	//setLayout(new BorderLayout());

	}

	/**
	 * if there is only one attribute, the control (at2) is passed as null and
	 * the boolean log is true, but it won't be used.
	 * */

	public JPanel drawGaussian(String at1, String at2,int res, double sigma, boolean log) {
		Mapping map = new Mapping();
		HashMap<String, double[]> hash;
		Smooth sp = new Smooth();

		//soh normaliza se nao for exp x controle
		
		if (at2 == null) {
			hash = map.getCompleteHash(at1, log);
			hash = Util.Normalize(hash);
		} else {
			ExpControl expControl = new ExpControl();
			hash = expControl.calcule(at1, at2,log);
		}

		
		
		hash = Util.Normalize(hash);
		
		double[][] matrix = sp.gaussian(res, hash, sigma);

		HM = new HeatMap(matrix, true, Gradient.GRADIENT_RAINBOW2);
		HM.setDrawLegend(true);

		HeatMapPanel HMD = null;
		try {
			HMD = new HeatMapPanel(HM);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JPanel sswp = new JPanel();
		sswp.setLayout(new BorderLayout());
		sswp.add(HM);
		sswp.add(HMD.testJPanel(), BorderLayout.SOUTH);
		return sswp;

	}

	
	/**
	 * do Ney - LEI DA POTENCIA
	 * */
	public JPanel drawCustom(String at1, String at2, int res, double smooth, boolean log) {
		Mapping map = new Mapping();
		HashMap<String, double[]> hash;
		Smooth sp = new Smooth();

		if (at2 == null) {
			hash = map.getCompleteHash(at1, log);
			
			
		} else {
			ExpControl expControl = new ExpControl();
			hash = expControl.calcule(at1, at2,log);

		}

		hash = Util.Normalize(hash);
		double[][] matrix = sp.custom(res, hash, smooth);

		//que tal normalizar a matriz antes de colocar aqui? nao precisa, deu na mesma
		//matrix = Util.normalizeDouble(matrix);
		System.out.println("nao ta mais normalizando");
		
		HM = new HeatMap(matrix, true, Gradient.GRADIENT_RAINBOW2);
		HM.setDrawLegend(true);

		HeatMapPanel HMD = null;
		try {
			HMD = new HeatMapPanel(HM);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JPanel sswp = new JPanel();
		sswp.setLayout(new BorderLayout());
		sswp.add(HM);
		sswp.add(HMD.testJPanel(), BorderLayout.SOUTH);
		return sswp;
	}

	
	
	
	public static void screenshot(String filename) throws IOException {
		File output = new File(filename);

		if (!output.getParentFile().exists())
			output.mkdirs();
		ImageIO.write(HM.screenshot(), "png", output);
		JOptionPane.showMessageDialog(null, "Successfully saved");
	}
	
	
	
	public JPanel drawCustomEC(String at1, String at2, int res, double smooth, boolean log) {
		Mapping map = new Mapping();
		HashMap<String, double[]> hash;
		Smooth sp = new Smooth();

		if (at2 == null) {
			hash = map.getCompleteHash(at1, log);
			
			
		} else {
			ExpControl expControl = new ExpControl();
			hash = expControl.calcule(at1, at2,log);

		}

		//girar o hashmap de novo
		HashMap<String, double[]> valores = new HashMap<String, double[]>();
		for(String key: hash.keySet()){
			double [] newvalue = {hash.get(key)[0],-1.0*hash.get(key)[1],hash.get(key)[2]};
			valores.put(key,newvalue);
			
		}
		hash = valores;
		
		hash = Util.Normalize(hash);
		
		double[][] matrix = sp.customEC(res, hash, smooth);

		//aplicar o flanagran
		
		SurfSmooth smoothness = new SurfSmooth(matrix);
		matrix = smoothness.movingAverage((int) smooth);
		
		
		
		HM = new HeatMap(matrix, true, Gradient.GRADIENT_RAINBOW2);
		HM.setDrawLegend(true);

		HeatMapPanel HMD = null;
		try {
			HMD = new HeatMapPanel(HM);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JPanel sswp = new JPanel();
		sswp.setLayout(new BorderLayout());
		sswp.add(HM);
		sswp.add(HMD.testJPanel(), BorderLayout.SOUTH);
		return sswp;
	}
}