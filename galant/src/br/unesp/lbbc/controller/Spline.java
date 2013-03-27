package br.unesp.lbbc.controller;

import java.util.HashMap;

/** Get one HasMap - calculate others points (spline) and then return a new map */

public class Spline {

	
	double[][] z ;
	
	public Spline() {

	}

	/**
	 * Este metodo precisa do tamanho da malha pra definir os pontos de base. Se
	 * houver dois pontos ou mais dentro de uma mesma celula eh feita a media. Ele
	 * retorna um double[][] com alguns pontos preenchidos e outros NaN
	 */

	public double[][] generateControlPoints(HashMap<String, double[]> originalPoints, int res) {

		
		z = new double[res+(int)0.2*res][res+(int)0.2*res];
		
		// calcular a media de todos os pontos que estao dentro de uma celula

		//fazer i comecar 10% depois e acabar 10% antes
		int inicio = (int) (res*0.1);
		int fim = z.length - (int) (res*0.1);
		
		
		for (int i = inicio; i < fim; i++) {
			for (int j = inicio; j < fim; j++) {
				int soma = 0;
				for (String key : originalPoints.keySet()) {

					int x = (int) (res * originalPoints.get(key)[0]);
					int y = (int) (res * originalPoints.get(key)[1]);
					if (i == x && j == y) {
						z[i][j] = (z[i][j] + originalPoints.get(key)[2]);
						soma = soma + 1;
					}
					
				}
				// calculo a media
				z[i][j] = z[i][j] / soma;
			}
		}
		
		//Todos os NaN devem ser zero tambem
		for (int i = 0; i < z.length; i++) {
			for (int j = 0; j < z.length; j++) {	
				if (z[i][j]>0){continue;}
				else {z[i][j]=0f;}
			}
		}
		
		//Preciso adicionar mais 10% de linhas e colunas = 0.1*res
		
	
		
			for (int i = 0; i < res; i++) {
			for (int j = 0; j < res; j++) {	
				//System.out.println(i+" "+j+"  ->  "+z[i][j]);
			}
		}	 

		return z;
	}


	
	
}
