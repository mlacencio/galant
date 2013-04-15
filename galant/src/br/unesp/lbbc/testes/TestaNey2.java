package br.unesp.lbbc.testes;

public class TestaNey2 {

	
	public TestaNey2(){
		
		double[] x1 = {0.5,0.5,0.3};
		double[] x2 = {0.6,0.8,0.3};
		double[] x3 = {0.8,0.6,0.9};	
		double [][] data = {x1,x2,x3};
		
		double[][] result = geraMatriz(2, data);
		
		System.out.println("da um tempo");
	}
	
	/**
	 * 
	 * @param res - mesh resolution
	 * @param original - matriz bi-dimensiona com os pontos do cytoscape em cada condicao (n x 3)
	 * @return
	 */
	public double[][] geraMatriz(int res, double [][] original){
		
		int size=res;
		int nvertices = original.length;  //number of original points
		double[] cond = new double[nvertices]; // um vetor que indica a expressao de cada nodo
		double[][] matrix =new double[size][size]; // a matrix que devera ser impressa 
		double[][] coords = new double[nvertices][2]; // a posicao de cada um dos vertices (deve sair do cytoscape)
		double[] dist = new double[nvertices];           // tem de estar entre [0,1] x [0,1]
		
		//preenche as arrays
		for (int i=0;i<size;i++){
			coords[i][0]=original[i][0];
			coords[i][1]=original[i][1];
			cond[i]=original[i][2];
		}
		
		
		for (int i=0;i<size;i++){     
		        for (int j=0;i<size;i++){
		        	double sumdist = 0;
		             for (int k=0; k<size; k++){
		            	 	double a = coords[k][0]*size-i;
		            	 	double b = coords[k][1]*size-j;
		            	 
		            	 	dist[k]=Math.pow((a),2)+Math.pow((b),2);
		            	 	sumdist+=dist[k];
		             }
		            
		             for (int k=0; k<size; k++){
		            	 matrix[i][j]+=cond[k]/dist[k];
		             }
		             matrix[i][j]=matrix[i][j]/sumdist;
		        }
		}
		
		return matrix;
	}
	
	
	public static void main(String[] args) {
		TestaNey2 t = new TestaNey2();
		
	}

	
}
