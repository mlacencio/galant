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


/**
 * To draw the surface it's needed to know what attribute and function
 * */

public class SurfacePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static HeatMap HM;


	public SurfacePanel() {
		
		setLayout(new BorderLayout());
		
		
	}
	
	/**
	 * if there is only one attribute, the control (at2) is passed as null and the boolean log is true, but 
	 * it won't be used.
	 * */
	
	public JPanel drawGaussianEC(String at1,String at2,boolean isSelected2D,int res,double sigma,boolean log) {
		Mapping map = new Mapping();
		HashMap<String, double[]> hash;
		Smooth sp = new Smooth();
		
		if (at2==null){
			hash = map.getCompleteHash(at1,log);
			}
		else{
			ExpControl expControl = new ExpControl();
			hash = expControl.calcule(at1, at2);
			
		}
		
		double[][] matrix = sp.gaussian(res, hash, sigma,log);
		
		HM = new HeatMap(matrix, true,Gradient.GRADIENT_RAINBOW2);
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
	
	public JPanel drawCustomEC(String at1,String at2,boolean isSelected2D,int res,double smooth,boolean log) {
		Mapping map = new Mapping();
		HashMap<String, double[]> hash;
		Smooth sp = new Smooth();
		
		if (at2==null){
			hash = map.getCompleteHash(at1,log);
			}
		else{
			ExpControl expControl = new ExpControl();
			hash = expControl.calcule(at1, at2);
			
		}
		
		double[][] matrix = sp.custom(res, hash, smooth,log);
		
		HM = new HeatMap(matrix, true,Gradient.GRADIENT_RAINBOW2);
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
	
	//Choks
	public JPanel drawGaussian2(String at1,String at2, boolean teste,int res, double sigma,boolean log) {
		
		Mapping map = new Mapping();
		HashMap<String,double[]> hash = map.getCompleteHash(at1, log);
		Smooth sp = new Smooth();
		
		double[][] matrix = sp.gaussian(res, hash, sigma,log);
		HM = new HeatMap(matrix, true,Gradient.GRADIENT_RAINBOW2);
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

		
	public JPanel drawCustom2(String at1,String at2, boolean isSelected2D,int res,double smooth,boolean log) {
	
		
		Mapping map = new Mapping();
		HashMap<String,double[]> hash = map.getCompleteHash(at1, log);
		Smooth sp = new Smooth();
		
		double[][] matrix = sp.custom(res, hash,smooth,log);
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
	
	//Choks
	
	public static void screenshot(String filename) throws IOException{
		File output = new File(filename);
		
		if(!output.getParentFile().exists())
			output.mkdirs();
		ImageIO.write(HM.screenshot() , "png", output);
		JOptionPane.showMessageDialog(null,"Successfully saved");
	}

	
	
}