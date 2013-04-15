package br.unesp.lbbc.testes;

import br.unesp.lbbc.controller.Spline;

public class TesteInterpola2 {

	
	public TesteInterpola2(){
		Spline sp = new Spline();	
		sp.interpolate(0, 0.5, 1, 1, 3);
		}
	
	
	
	public static void main(String[] args) {
		TesteInterpola2 t = new TesteInterpola2();
	}

}
