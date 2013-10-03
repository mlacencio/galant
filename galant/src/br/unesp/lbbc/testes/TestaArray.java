package br.unesp.lbbc.testes;

import br.unesp.lbbc.util.Util;

public class TestaArray {

	public TestaArray() {
		float[][][] mesh = { { { 0, 0, 0 }, { 0, 1, 0 } },
				{ { 1, 0, 0 }, { 10, 10, 10 } } };

		
		float[][][] nmesh = Util.normalizeFloat(mesh);
		
		int i = 0;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestaArray t = new TestaArray();
	}

}
