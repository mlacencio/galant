package br.unesp.lbbc.testes;

import java.util.List;

import br.unesp.lbbc.model.Functions;

public class TestaTranspose {

	public TestaTranspose(){
		
		double[][] mat = {{0,1,5,6},{2,3,0,0},{2,1,0,0},{2,3,0,4}};
		//double[][] mat = {{0,1},{2,3},{2,1},{2,3}};
		
		Functions f = new Functions();
		
		List a = f.getInterpolatedArray(mat, 1);
		
		int i = 1;
		
	}
	
	
	
	public static void main(String[] args) {
		TestaTranspose t = new TestaTranspose();
	}

}
