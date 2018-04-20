package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class Grafo {

	private String aGrafo[][];
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

				//Linha que possua somente 1 caracter, obtem a dimenssão da matriz e a instancia
				if(aCaract.length == 1){
					nDimens   = Integer.parseInt(cLinha);
					aGrafo    = new String[nDimens][nDimens];
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
					//Busca vertices quando não for * ou .
					for (int nInd = 0; nInd < aVertAux.length; nInd++) {
						if(!aVertAux[nInd].equals("*") && !aVertAux[nInd].equals(".")){
							aVertices[nInd] = aVertAux[nInd];
						}else if(nLinha == nInd){
							aVertices[nInd] = aVertAux[nInd];
						}
					}
					
					int nCol=0;
					//Monta a matriz tirando os espaços
					for(String cVal:aCaract){
						aGrafo[nLinh][nCol] = cVal;
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
				
		for(int nLin=0;nLin<nDimens;nLin++){
			aListVert.add(oGraph.addVertex(aVertices[nLin]));
		}
		
		for(int nLin=0;nLin<nDimens;nLin++){
			for(int nCol=0;nCol<nDimens;nCol++){
				if(aGrafo[nLin][nCol] == "."){
					aListVert.get(nLin).addEdge(aListVert.get(nCol));
				}
			}
		}
		
		Vertex<String> oOrig = null, oDestin = null;
		for(Vertex<String> oVert : aListVert){
			if(oVert.getValue().equals(aCaminho[0])){
				oOrig = oVert;
			}
			if(oVert.getValue().equals(aCaminho[1])){
				oDestin = oVert;
			}
		}
		
		if(oGraph.hasPath(oOrig, oDestin)){
			System.out.print("*");
		}else{
			System.out.print("!");
		}
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
