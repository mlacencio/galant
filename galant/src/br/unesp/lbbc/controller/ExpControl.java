package br.unesp.lbbc.controller;

import java.util.HashMap;

public class ExpControl {

	Mapping mapping = new Mapping();

	/**
	 * It takes the z-exp and the z-control, calculate alpha = (z-exp) / z-exp  -  z-control).
	 *  Returns a HashMap
	 * */
	public HashMap<String,double[]> calcule(String exp, String control) {
		HashMap<String, double[]> mapExp = mapping.getCompleteHash(exp);
		HashMap<String, double[]> mapControl = mapping.getCompleteHash(control);
		
		HashMap<String, double[]> newMap = new HashMap<String, double[]>();

		for (String key : mapExp.keySet()) {
				
			Double atributeCalculado = (mapExp.get(key)[2]) / (mapExp.get(key)[2]+mapControl.get(key)[2]);
			double[] valores = { mapExp.get(key)[0], mapExp.get(key)[1],atributeCalculado };
			newMap.put(key,valores);
			
		}
		return newMap;
	}
	
	public HashMap<String,double[]> calculeLog(String exp, String control) {
		HashMap<String, double[]> mapExp = mapping.getCompleteHash(exp);
		HashMap<String, double[]> mapControl = mapping.getCompleteHash(control);

		HashMap<String, double[]> newMap = new HashMap<String, double[]>();

		for (String key : mapExp.keySet()) {
				
			Double atributeCalculado = (mapExp.get(key)[2]-mapControl.get(key)[2]);
			double[] valores = { mapExp.get(key)[0], mapExp.get(key)[1],atributeCalculado };
			newMap.put(key,valores);
			
		}
		return newMap;
	}
	

}
