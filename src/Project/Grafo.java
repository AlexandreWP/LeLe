package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Grafo {

	private char grafo[][];
	private int dimensao=0;
	private String aVertices[];
	private String aVertAux[];


	public void carregaGrafo(String arquivo){
		int nlinh = 0;
		
		try {
			Scanner leitor = new Scanner(
					new File(
							ClassLoader.getSystemResource(arquivo).getFile()));
			
			Graph<String> graph = new Graph<String>();

				
			while(leitor.hasNext()){
				String linha = leitor.nextLine();
				String linhasempespaco = linha.replace(" ", "");
				String aCaminho[] = new String[2];

				if(linhasempespaco.length() == 1){
					dimensao = Integer.parseInt(linha);
					grafo = new char[dimensao][dimensao];
					aVertAux = new String[dimensao];
					aVertices = new String[dimensao];
					nlinh = 0;
				}
				if(linhasempespaco.length() > 2){
					aVertAux = linha.split(" ");
					
					for (int nInd = 0; nInd < aVertAux.length; nInd++) {
						if(!aVertAux[nInd].equals("*") && !aVertAux[nInd].equals(".")){
							aVertices[nInd] = aVertAux[nInd];
						}
					}
					
					int col=0;
					for(char celula:linha.toCharArray()){
						if(celula != ' '){
							grafo[nlinh][col] = celula;
							col++;
						}
					}
					nlinh++;
					
		
				}
				if(linhasempespaco.length() == 2){
					
					aCaminho = linha.split(" ");
					
					montaGrafo(aCaminho);
						
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void montaGrafo(String[] aCaminho){
		Graph<String> graph = new Graph<String>();
		
		ArrayList<Vertex> listavertices = new ArrayList<Vertex>();
		
		for(int lin=0;lin<dimensao;lin++){
			listavertices.add(graph.addVertex(aVertices[lin]));
		}
		
		
		for(int lin=0;lin<dimensao;lin++){
			for(int col=0;col<dimensao;col++){
				if(grafo[lin][col] == '.'){
					listavertices.get(lin).addEdge(listavertices.get(col));
				}
			}
			
		}
		
		Vertex<String> origem = null, destino = null;
		for(Vertex<String> v : listavertices){
			if(v.getValue().equals(aCaminho[0])){
				origem = v;
			}
			if(v.getValue().equals(aCaminho[1])){
				destino = v;
			}
		}
		
		if(graph.hasPath(origem, destino)){
			System.out.print("*");
		}else{
			System.out.print("!");
		}
	}
	
	public static void main(String[] args) {
		Grafo g = new Grafo();
		g.carregaGrafo("Project/teste2.txt");
		
	}
}