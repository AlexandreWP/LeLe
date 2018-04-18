package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class Grafo {

	private char aGrafo[][];
	private int nDimens=0;
	private String aVertices[];
	private String aVertAux[];


	public void carregaGrafo(String arquivo){
		int nlinh = 0;
		
		try {
			Scanner oRead = new Scanner(
					new File(arquivo));
							//ClassLoader.getSystemResource().getFile()));
				
			while(oRead.hasNext()){
				String cLinha = oRead.nextLine();
				String cLinEspaco = cLinha.replace(" ", "");
				String aCaminho[] = new String[2];

				//Linha que possua somente 1 caracter, obtem a dimenssão da matriz e a instancia
				if(cLinEspaco.length() == 1){
					nDimens = Integer.parseInt(cLinha);
					aGrafo = new char[nDimens][nDimens];
					aVertAux = new String[nDimens];
					aVertices = new String[nDimens];
					nlinh = 0;
				}
				
				//Linha com mais de 2 caracters busca vertices e monta a matriz
				if(cLinEspaco.length() > 2){
					aVertAux = cLinha.split(" ");
					
					//Busca vertices quando não for * ou .
					for (int nInd = 0; nInd < aVertAux.length; nInd++) {
						if(!aVertAux[nInd].equals("*") && !aVertAux[nInd].equals(".")){
							aVertices[nInd] = aVertAux[nInd];
						}
					}
					
					int nCol=0;
					//Monta a matriz tirando os espaços
					for(char cVal:cLinha.toCharArray()){
						if(cVal != ' '){
							aGrafo[nlinh][nCol] = cVal;
							nCol++;
						}
					}
					nlinh++;
					
		
				}
				
				//Linha com exatamente 2 caracters gera o grafo e respostas
				if(cLinEspaco.length() == 2){
					
					aCaminho = cLinha.split(" ");
					
					montaGrafo(aCaminho);
						
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void montaGrafo(String[] aCaminho){
		Graph<String> oGraph = new Graph<String>();
		
		ArrayList<Vertex> aListVert = new ArrayList<Vertex>();
		
		for(int nLin=0;nLin<nDimens;nLin++){
			aListVert.add(oGraph.addVertex(aVertices[nLin]));
		}
		
		
		for(int nLin=0;nLin<nDimens;nLin++){
			for(int nCol=0;nCol<nDimens;nCol++){
				if(aGrafo[nLin][nCol] == '.'){
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