package br.unesp.lbbc.controller;

import java.util.HashMap;

import br.unesp.lbbc.util.Util;

public class ExpControl {

	Mapping mapping = new Mapping();

	
	/**
	 * It takes the z-exp and the z-control, calculate alpha = (z-exp) / z-exp  -  z-control).
	 *  Returns a HashMap
	 * */
	public HashMap<String,double[]> calcule(String exp, String control,boolean isLog) {
		HashMap<String, double[]> mapExp = mapping.getCompleteHash(exp,isLog);
		HashMap<String, double[]> mapControl = mapping.getCompleteHash(control,isLog);
		
		HashMap<String, double[]> newMap = new HashMap<String, double[]>();

		for (String key : mapExp.keySet()) {
			double atributeCalculado = (mapExp.get(key)[2]) / (mapExp.get(key)[2]+mapControl.get(key)[2]);
			
		/*	if (atributeCalculado.isNaN()){  // ******************* nao lembro porque fiz isso
				atributeCalculado = 0.0000;
			}*/
				
			double[] valores = { mapExp.get(key)[0], mapExp.get(key)[1],atributeCalculado};
			
			//System.out.println(mapExp.get(key)[2]+"\t"+mapControl.get(key)[2]+"\t"+atributeCalculado);//alter choks
			
			newMap.put(key,valores);
			
			 
		}
		return newMap;
	}
	

	

}
