package br.unesp.lbbc.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/** Get one HasMap - calculate others points (spline) and then return a new map */

public class Smooth {

	public double[][] custom(int res, HashMap<String, double[]> original,
			double smooth) {

		int nvertices = original.size(); // number of original points
		double[] cond = new double[nvertices]; // um vetor que indica a
												// expressao de cada nodo
		double[][] matrix = new double[res][res]; // a matrix que devera ser
													// impressa
		double[][] coordsXY = new double[nvertices][2]; // a posicao de cada um
														// dos vertices (deve
														// sair do cytoscape)
		double[] dist = new double[nvertices]; // tem de estar entre [0,1] x
												// [0,1]

		// iterate through HashMap values iterator
		Collection c = original.keySet();
		Iterator itr = c.iterator();

		int kk = 0;
		while (itr.hasNext()) {
			double[] value = original.get(itr.next());
			coordsXY[kk][0] = value[0];
			coordsXY[kk][1] = 1 - value[1];

			coordsXY[kk][0] = 0.1 + 0.8 * coordsXY[kk][0];
			coordsXY[kk][1] = 0.1 + 0.8 * coordsXY[kk][1];

			cond[kk] = value[2];
			kk++;
		}
		double sumdist = 0;
		for (int i = 0; i < res; i++) {
			for (int j = 0; j < res; j++) {
				// double sumdist = 0;
				for (int k = 0; k < nvertices; k++) {
					double a = coordsXY[k][0] * res - i;
					double b = coordsXY[k][1] * res - j;

					dist[k] = Math.pow(Math.pow((a), 2) + Math.pow((b), 2),
							smooth);
					// dist[k]=Math.pow((a),2)+Math.pow((b),2);
					if (dist[k] == 0) {
						dist[k] = 0.0001;
					} else {
						sumdist += 1 / dist[k]; // consertar
					}
				}

				for (int k = 0; k < nvertices; k++) {
					matrix[i][j] += cond[k] / dist[k];
				}
				// matrix[i][j]=matrix[i][j]/1;//sumdist;

			}

		}
		for (int i = 0; i < res; i++) {
			for (int j = 0; j < res; j++) {
				matrix[i][j] = matrix[i][j] / sumdist;
			}
		}

		return matrix;
		// return Util.normalizeDouble(matrix);
	}

	public double[][] gaussian(int res, HashMap<String, double[]> original,
			double sigma) {

		int nvertices = original.size(); // number of original points
		double[] cond = new double[nvertices]; // um vetor que indica a
												// expressao de cada nodo
		double[][] matrix = new double[res][res]; // a matrix que devera ser
													// impressa
		double[][] coords = new double[nvertices][2]; // a posicao de cada um
														// dos vertices (deve
														// sair do cytoscape)
		double[] dist = new double[nvertices]; // tem de estar entre [0,1] x
												// [0,1]
		double s = sigma;

		// iterate through HashMap values iterator
		Collection c = original.keySet();
		Iterator itr = c.iterator();

		int kk = 0;
		double mediacond = 0.0;
		while (itr.hasNext()) {
			double[] value = original.get(itr.next());
			coords[kk][0] = value[0];
			coords[kk][1] = 1 - value[1];
		//	coords[kk][0] = 0.1 + 0.8 * coords[kk][0];
		//	coords[kk][1] = 0.1 + 0.8 * coords[kk][1];
			/*
			 * if(boollog){ if(value[2]==0){ cond[kk]=0;
			 * //System.out.println(cond[kk]); }else{
			 * cond[kk]=Math.log(value[2])/Math.log(2);
			 * //System.out.println(cond[kk]); } }else{ cond[kk]=value[2]; }
			 */
			cond[kk] = value[2];
			mediacond = mediacond + cond[kk];
			kk++;
		}

		mediacond = mediacond / nvertices;
		for (int i = 0; i < res; i++) {
			for (int j = 0; j < res; j++) {
				matrix[i][j] = mediacond;
			}
		}
		s = s * res;

		for (int i = 0; i < res; i++) {
			for (int j = 0; j < res; j++) {
				double sumdist = 0;
				for (int k = 0; k < nvertices; k++) {
					double a = coords[k][0] * res - i;
					double b = coords[k][1] * res - j;

					dist[k] = Math.exp(-(Math.pow((a), 2) + Math.pow((b), 2))
							/ (s * s));
					// sumdist+=dist[k];
				}

				for (int k = 0; k < nvertices; k++) {
					matrix[i][j] += cond[k] * dist[k];
				}
				matrix[i][j] = matrix[i][j] / (s * s);
			}
		}

		return matrix;
	}

	public double[][] customEC(int res, HashMap<String, double[]> original,
			double smooth) {

		double[][] matrix = new double[res][res];

		for (int i = 0; i < res; i++) {
			for (int j = 0; j < res; j++) {
				double soma = 1.0;
				double z = matrix[i][j];
				for (double[] values : original.values()) {
					if (((values[0] * res) >= (i * 1.0) && (values[0] * res) <= (i * 1.0 + 1.0))
							&& ((values[1] * res) >= (j * 1.0) && (values[1] * res) <= (j * 1.0 + 1.0))) {
						z = z + values[2];
						soma = soma + 1;
					}
				}
				if (soma > 1) {
					matrix[i][j] = z / (soma - 1);
				} else {
					matrix[i][j] = z;
				}
			}
		}
		return matrix;
	}

}
