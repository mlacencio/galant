package br.unesp.lbbc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.math.ArgumentOutsideDomainException;
import org.apache.commons.math.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math.analysis.polynomials.PolynomialSplineFunction;
import org.jzy3d.maths.Array;
import org.jzy3d.maths.Coord2d;
import org.jzy3d.maths.Coord3d;

import flanagan.interpolation.CubicSplineFast;

/** Get one HasMap - calculate others points (spline) and then return a new map */

public class Spline {

	
	double[][] z ;
	
	public Spline() {

	}

public double[][] geraMatrizNey(int res, double [][] original){
		
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
	
	public double[][] generateSpline(HashMap<String, double[]> originalPoints, int res, int smooth){
	//public List<Coord3d> generateSpline(HashMap<String, double[]> originalPoints, int res, int smooth){
			
		double cellSize = 1/(double)res;
		double[] x = new double[res];
		double[] y = new double[res];
		double[][]zmat = new double[res][res];
		
		//preenche x e y
		for (int i=0;i<res;i++){
			x[i]=i*cellSize;
			y[i]=i*cellSize;
		}
		
		//preenche zmat
		for (int i=0;i<res;i++){
			for (int j=0;j<res;j++){
				zmat[i][j]=getCellAverage(originalPoints,x[i],y[j],cellSize);
			}
		}		
		
		//adicionar um ponto em cada extremo sendo z igual a zero
		double[] xm1 = new double[res+2]; //adiciona um zero de cada lado + 2
		double[] ym1 = new double[res+2]; 
		//preenche novos x e y
				for (int i=0;i<res+2;i++){
					xm1[i]=i*cellSize/res;
					ym1[i]=i*cellSize/res;
				}
				
		//preenche novo zmat baseada na antiga - comeca 1 depois e para 1 antes - o resto fica zero
		double[][] nzmat = new double[res+2][res+2];
				for (int i=1;i<res+1;i++){
					for (int j=1;j<res+1;j++){
						nzmat[i][j]=zmat[i-1][j-1];
					}
				}		
		
		
		//dependendo do smooth interpola n pontos entre os pontos
		
		//terceira nova array em x e y - modificacao2 - m2
		int atualDim = res+2;	
		int novaDim = (smooth+1)*atualDim;
		double[] xm2 = new double[novaDim]; 
		double[] ym2 = new double[novaDim]; 
		double[][] zmatm2 = new double[novaDim][novaDim];
		
		
		//preenche x e y
		double newCellSize = cellSize/(2*(smooth+1));
		for (int i=0;i<novaDim;i++){
			xm2[i]=i*newCellSize;
			ym2[i]=i*newCellSize;
		}
		
		//preenche z mat com dados anteriores - PERFEITO - NAO MEXER
		for (int i=0;i<atualDim;i++){
			for (int j=0;j<atualDim;j++){
				zmatm2[(smooth+1)*i][(smooth+1)*j]=nzmat[i][j];
			}
		}		

		
		//interpola em xz  - PERFEITO - NAO MEXER
		for (int i=0;i<=novaDim/(smooth+1);i=i+smooth+1){ 
			for (int j=0;j<=novaDim/(smooth+1);j=j+smooth+1){				
				double inc =(zmatm2[i][j+smooth+1]-zmatm2[i][j])/(smooth+1);
				for (int k=1; k<=smooth;k++){
					zmatm2[i][j+k]=zmatm2[i][j]+k*inc;
				}
				
			}
		}		

		//interpola em yz   - PERFEITO - NAO MEXER
		for (int i=0;i<=novaDim/(smooth+1);i=i+smooth+1){ 
			for (int j=0;j<=novaDim/(smooth+1);j=j+smooth+1){				
				double inc = ((zmatm2[j+smooth+1][i]-zmatm2[j][i]))/(smooth+1);
				for (int k=1; k<=smooth;k++){
					zmatm2[j+k][i]=zmatm2[i][j]+k*inc;
				}
				
			}
		}	
	

		
		List<Coord3d> list = new ArrayList<Coord3d>();
		
		for (int i=0;i<novaDim;i++){ 
			for (int j=0;j<novaDim;j++){
				Coord3d coord = new Coord3d(xm2[i], ym2[j],zmatm2[i][j]);
				list.add(coord);
				
			}
		}
		
		//return list;
		return zmatm2;
		
	}
	
	private double getCellAverage(HashMap<String, double[]> originalPoints, double xi, double yi,double cellSize) {
		//o tamanho da celula eh 1
		double soma=0;
		int quociente = 0;
		for (double[] value:originalPoints.values()){
			double x = value[0];
			double y = value[1];
			double z = value[2];
			
			if (x>=xi && x<xi+cellSize && y>=yi && y<yi+cellSize){
				soma = soma+z;
				quociente = quociente +1;
			}		
		}
		if (quociente == 0) {return soma;} //para evitar divisao por zero
		else {return soma/quociente;}   //tem que dividir para ter a media
	}


	/**
	 * Given two points, it builds a line and take the number of points.
	 * Por exemplo: se pedir para encontrar tres pontos entre 0 e 1 ele retorna 0.25, 0.50 e 0.75
	 */
	
	//depois voltar para private
	public double[] interpolate(double x0, double z0, double x1, double z1, int points){
		
		double coef = (z1-z0)/(x1-x0); 					//coeficiente de inclinacao da reta
		double argX = (x1-x0)/((double)points+1);		//argumentos somados em X
				
		//array x
		double [] arrayZ = new double[points]; //numero de pontos intermediarios
		for (int i = 0; i<points; i++){
			double x = x0+(i+1)*argX; 
			arrayZ[i]=z0+coef*x;
		}
		
		return arrayZ;
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
