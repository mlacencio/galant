package br.unesp.lbbc.testes;

public class TestaNey {

	public TestaNey(){
		
		int size = 2;
		int arg = 1/2;
		
		double[][] mesh = new double[size][3];
		
		//Preenche matriz
		for (int i=0;i<size;i++){     //itera em cada ponto
			mesh[i][0]=i*arg;
			mesh[i][1]=i*arg;
		}
		
		int n = 2;  //number of original points
		double[][] originalPoints = new double[n][3]; //2 pontos com 3 coordenadas cada
		// Preenche original points
		originalPoints[0][0]=0.5; //Ponto 1 -----x
		originalPoints[0][1]=0.5; //	    -----y
		originalPoints[0][2]=0.1; //	    -----z
		originalPoints[1][0]=0.6; //Ponto2  -----x
		originalPoints[1][1]=0.8; //	    -----y
		originalPoints[1][2]=0.9; //	    -----z
								
		//vetor com as distancias de todos os pontos originais a cada celula. Neste caso matriz sera size X 2	
		double[][] dist = new double[size][n];
		
		
		for (int i=0;i<size;i++){     //itera em cada ponto da malha
			for (int j=0;j<n;i++){     //itera em cada ponto original
			//	dist[i][j] = (xmesh - xponto)
			}
		}
		
		
	}
	
	
	public static void main(String[] args) {
		TestaNey t = new TestaNey();
	}

}
