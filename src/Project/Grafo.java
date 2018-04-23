package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class Grafo {

	private int[][] aGrafo;
	private int nDimens=0;
	private String aVertices[];
	private String aVertAux[];
	private ArrayList<Vertex> aListVert;


	public void carregaGrafo(String arquivo){
		int nLinh  = 0;
		int nLinha = 0;

		try {
			Scanner oRead = new Scanner(new File(arquivo));

			while(oRead.hasNext()){
				String cLinha = oRead.nextLine();
				String[] aCaract = cLinha.split(" ");

				//Linha que possua somente 1 caracter, obtem a dimenssÃ£o da matriz e a instancia
				if(aCaract.length == 1){
					nDimens   = Integer.parseInt(cLinha);
					aGrafo    = new int[nDimens][nDimens];
					aVertAux  = new String[nDimens];
					aVertices = new String[nDimens];
					aListVert = new ArrayList<Vertex>();
					nLinh = 0;
					nLinha = -1;
				}

				//Linha com mais de 2 caracters busca vertices e monta a matriz
				if(aCaract.length == nDimens){
					aVertAux = cLinha.split(" ");
					nLinha++;
					//Busca vertices quando nÃ£o for * ou .
					for (int nInd = 0; nInd < aVertAux.length; nInd++) {
						if(!aVertAux[nInd].equals("*") && !aVertAux[nInd].equals(".")){
							aVertices[nInd] = aVertAux[nInd];
						}else if(nLinha == nInd){
							aVertices[nInd] = aVertAux[nInd];
						}
					}

					int nCol=0;
					//Monta a matriz tirando os espaÃ§os
					for(String cVal:aCaract){
						if (cVal.equals(".")){
							aGrafo[nLinh][nCol] = 1;
						}else if(cVal.equals("*")){
							aGrafo[nLinh][nCol] = 0;
						}else{
							aGrafo[nLinh][nCol] = 0;
						}
						nCol++;
					}
					nLinh++;
				}


				//Linha com exatamente 2 caracters gera o grafo e respostas
				if(aCaract.length == 2){		
					montaGrafo(aCaract);	
				}

				if(aCaract.length == 1){
					System.out.println("");
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void montaGrafo(String[] aCaminho){
		Graph<String> oGraph = new Graph<String>();
		aListVert.clear();

		for(int nLin=0;nLin<nDimens;nLin++){
			aListVert.add(oGraph.addVertex(aVertices[nLin]));
		}

		for(int nLin=0;nLin<nDimens;nLin++){
			for(int nCol=0;nCol<nDimens;nCol++){
				if(aGrafo[nLin][nCol] == 1){
					aListVert.get(nLin).addEdge(aListVert.get(nCol));
				}
			}
		}

		int nOrig = 0;
		int nDestin = 0;
		int nInd = 0;
		for(Vertex<String> oVert : aListVert){
			if(oVert.getValue().equals(aCaminho[0])){
				nOrig = nInd;
			}
			
			if(oVert.getValue().equals(aCaminho[1])){
				nDestin = nInd;
			}
			nInd++;
		}		

		if(fechaTransit(nOrig, nDestin)){
			System.out.print("*");
		}else{
			System.out.print("!");
		}
	}

	public boolean fechaTransit(int orig, int dest){
		boolean lRet = false;
		int fecha[][] = aGrafo.clone();
		for(int k=0;k<nDimens;k++){
			for(int i=0;i<nDimens;i++){
				if(fecha[i][k] != 0){
					for(int j=0;j<nDimens;j++){
						fecha[i][j] = 
								(fecha[i][j]==1) || (fecha[k][j]==1)?1:0;
					}
				}
			}
		}
		
		if(fecha[orig][dest] == 1){
			lRet = true;
		}else{
			lRet = false;
		}
		
		return lRet;
	}

	public static void main(String[] args) {
		Grafo oGraf = new Grafo();
		JFileChooser fc = new JFileChooser();

		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			oGraf.carregaGrafo(file.getPath().replace("\\", "/"));
		}
	}
}
