package br.unesp.lbbc.util.subdivision;


public class CCSurface {

	public CCSurface() {
		
	}

	public float[][][] divideCC(float[][][] mesh,int vezes){
		
		float[][][] novaMesh = divide(mesh);
		
		if (vezes>1) {
			for (int i = 0; i < vezes - 1; i++) {
				novaMesh = divide(novaMesh);
			}
		}
		return novaMesh;
	}
	
	private float[][][] divide(float[][][] mesh) {

		int res = mesh.length;
		int nres = 2 * res - 1;
		// a nova malha divMesh nao tem distancias regulares entre as celulas
		// entao ela
		// precisa ter 3 dimensoes (colunas,linhas,pontos -> res,res,3)

		float[][][] divMesh = new float[nres][nres][3];
		// preenche a nova malha com base na malha antiga
		for (int i = 0; i < nres; i = i + 2) {
			for (int j = 0; j < nres; j = j + 2) {
				divMesh[i][j] = mesh[i / 2][j / 2];

			}
		}

		// planta a velha malha na nova malha
		for (int i = 0; i < res - 1; i++) {
			for (int j = 0; j < res - 1; j++) {
				divMesh[2 * i + 1][2 * j + 1] = getMid(mesh[i][j],
						mesh[i + 1][j + 1]);
			}
		}

		// calcula os midpoints na horizontal
		for (int i = 0; i < nres - 1; i = i + 2) {
			for (int j = 0; j <= nres - 1; j = j + 2) {
				divMesh[i + 1][j] = getMid(divMesh[i][j], divMesh[i + 2][j]);
			}
		}
		// calcula os midpoints na vertical
		for (int j = 0; j < nres - 1; j = j + 2) {
			for (int i = 0; i <= nres - 1; i = i + 2) {
				divMesh[i][j + 1] = getMid(divMesh[i][j], divMesh[i][j + 2]);
			}
		}

		// adiciona os pontos de face
		for (int i = 0; i < nres - 1; i = i + 2) {
			for (int j = 0; j < nres - 1; j = j + 2) {
				float[][] pontos = { divMesh[i][j], divMesh[i][j + 2],
						divMesh[i + 2][j], divMesh[i + 2][j + 2] };
				divMesh[i + 1][j + 1] = getFace(pontos);
			}
		}

		// desloca (recalcula) os pontos de origem
		for (int i = 2; i < nres - 1; i = i + 2) {
			for (int j = 2; j < nres - 1; j = j + 2) {
				float[][] faces = { divMesh[i - 1][j - 1],
						divMesh[i - 1][j + 1], divMesh[i + 1][j - 1],
						divMesh[i + 1][j + 1] };
				float[][] mid = { divMesh[i][j - 1], divMesh[i][j + 1],
						divMesh[i + 1][j], divMesh[i][j + 1] };
				float[] original = divMesh[i][j];
				divMesh[i][j] = getVertex(faces, mid, original);
			}
		}

		return divMesh;
	}

	private float[] getFace(float[][] pontos) {
		float x = 0;
		float y = 0;
		float z = 0;

		for (int i = 0; i < pontos.length; i++) {
			x = x + pontos[i][0];
			y = y + pontos[i][1];
			z = z + pontos[i][2];
		}
		float[] cFace = { x / 4, y / 4, z / 4 };

		return cFace;
	}

	private float[] getMid(float[] p1, float[] p2) { // media entre dois pontos
		float[] mid = { (p2[0] + p1[0]) / 2, (p2[1] + p1[1]) / 2,
				(p2[2] + p1[2]) / 2 };
		return mid;

	}

	/*
	 * calcula a media ponderada das faces, mids e original e desloca o ponto
	 */
	private float[] getVertex(float[][] faces, float[][] mid, float[] original) {
		// 25% do peso para as faces, 50% do peso para os mids, 25% para o
		// original

		// soma os pontos da face
		float[] face = new float[3];
		for (int i = 0; i < faces.length; i++) {
			face[0] = face[0] + faces[i][0]; // em x
			face[1] = face[1] + faces[i][1]; // em x
			face[2] = face[1] + faces[i][1]; // em x
		}
		// soma os pontos das arestas
		float[] m = new float[3];
		for (int i = 0; i < mid.length; i++) {
			m[0] = m[0] + mid[i][0]; // em x
			m[1] = m[1] + mid[i][1]; // em x
			m[2] = m[1] + mid[i][1]; // em x
		}

		//somas todos de acordo com os pesos
		float[] vertex = {face[0]/4+m[0]/2+original[0]/4,face[1]/4+m[1]/2+original[1]/4,face[2]/4+m[2]/2+original[2]/4};
		return vertex;
	}

}
