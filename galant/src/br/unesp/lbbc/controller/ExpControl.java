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
			double expv = mapExp.get(key)[2];
			double contv = mapControl.get(key)[2];
			double atributeCalculado = 0.0;
			if (expv==0 && contv==0){
				double[] valores = { mapExp.get(key)[0], mapExp.get(key)[1],atributeCalculado};
				newMap.put(key,valores);
			}   // ******************* fiz isso por causa dos zero dividido por zero
			else{atributeCalculado = (expv) / (expv+contv);
				double[] valores = { mapExp.get(key)[0], mapExp.get(key)[1],atributeCalculado};
				newMap.put(key,valores);
			}
			
			
			
			//System.out.println(mapExp.get(key)[2]+"\t"+mapControl.get(key)[2]+"\t"+atributeCalculado);//alter choks
			
			
			
			 
		}
		return newMap;
	}
	

	

}
