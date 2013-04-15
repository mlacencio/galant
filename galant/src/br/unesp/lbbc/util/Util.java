package br.unesp.lbbc.util;

import java.util.HashMap;

public class Util {

	// This method normalize the values of one map with 3coord
	public static HashMap<String, double[]> Normalize(
			HashMap<String, double[]> map) {
		// Get the highest value for x and y

		// Pick any value to start comparison

		double xmax = (map.values().iterator().next()[0]);
		double xmin = xmax;
		double ymax = (map.values().iterator().next()[1]);
		double ymin = ymax;
		double zmax = (map.values().iterator().next()[2]);
		double zmin = zmax;

		for (double[] elem : map.values()) {
			if (elem[0] > xmax) {
				xmax = elem[0];
			}
			if (elem[1] > ymax) {
				ymax = elem[1];
			}
			if (elem[2] > zmax) {
				zmax = elem[2];
			}
			if (elem[0] < xmin) {
				xmin = elem[0];
			}
			if (elem[1] < ymin) {
				ymin = elem[1];
			}
			if (elem[2] < zmin) {
				zmin = elem[2];
			}

		}

		HashMap<String, double[]> newMap = new HashMap<String, double[]>();

		for (String key : map.keySet()) {
			double newX = ((map.get(key)[0]) - xmin) / (xmax - xmin);
			double newY = ((map.get(key)[1]) - ymin) / (ymax - ymin);
			double newZ = (((map.get(key)[2]) - zmin) / (zmax - zmin));
			double[] newCoord = { newX, newY, newZ };

			// use this line to NOT normalize the attribute
			//double[] newCoord = { newX, newY, map.get(key)[2] };

			newMap.put(key, newCoord);

		}
		return newMap;
	}

	public static double[][] normalizeDouble(double[][] data) {

		double max = data[0][0];
		double min = max;
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data.length; j++) {
				double novo = data[i][j];

				if (novo > max) {
					max = data[i][j];
				}

				if (novo < min) {
					min = data[i][j];
				}

			}
		}
		for (int k = 0; k < data.length; k++) {
			for (int l = 0; l < data.length; l++) {
				data[k][l] = (((data[k][l]) - min) / (max - min));
			}
		}

		return data;
	}

}
