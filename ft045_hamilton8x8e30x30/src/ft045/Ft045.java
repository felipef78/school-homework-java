/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ft045;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe F. Muller
 */
public class Ft045 {
    private int[][] matAd;
    private List<ArrayList<Integer>> listAd;
    //private int[] grau;
    private List<Node> nodes;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Ft045 ft = new Ft045();
        //lê o txt com o grafo
        Scanner txt = null;
        try {
            //txt = new Scanner(new BufferedReader(new FileReader("grafos/05_Grafo2.txt")));
            txt = new Scanner(new BufferedReader(new FileReader("grafos/Grafo3.txt")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ft045.class.getName()).log(Level.SEVERE, null, ex);
        }
        int n = txt.nextInt();
        System.out.println(n);
        ft.loadGraph(txt, n);
        //ft.printMat(n);
        //ft.printMat2(n);
        ft.hamilton(n);
    }

    private void loadGraph(Scanner txt, int n) {
        //cria matriz de adjacencia
        matAd = new int[n][n];
        listAd = new ArrayList<>();
        nodes = new ArrayList<>();
        //grau = new int[n];
        
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                matAd[i][j] = 0;
                //System.out.println(matAd[i][j]);
            }
        }
        
        while(txt.hasNext()){
            int linha = txt.nextInt();
            int coluna = txt.nextInt();
            //System.out.println(linha + " " + coluna);
            matAd[linha][coluna] = 1;
            matAd[coluna][linha] = 1;
        }
        for(int i = 0; i < n; i++){
            ArrayList<Integer> vizinhos = new ArrayList<Integer>();
            for(int j = 0; j < n; j++){
                if(matAd[i][j] == 1 || matAd[j][i] == 1){
                    vizinhos.add(j);
                }
            }
            //System.out.println(vizinhos.size());
            listAd.add(i, vizinhos);
        }
        for(int i = 0; i < n; i++){
            Node no = new Node(i, false);
            nodes.add(no);
        }
        for(int i = 0; i < n; i++){
            List<Node> vizinhos = new ArrayList<Node>();
            for(int j = 0; j < listAd.get(i).size(); j++){
                vizinhos.add(nodes.get(listAd.get(i).get(j)));
            }
            nodes.get(i).setVizinhos(vizinhos);
        }
    }

    private void printMat(int n) {
        System.out.print("   ");
        for(int i = 0; i <n; i++){
            System.out.print(i+" ");
        }
        System.out.print("\n");
        for(int i = 0; i <n; i++){
            System.out.print("___");
        }
        System.out.print("\n");
        for(int i = 0; i < n; i++){
            System.out.print(i+" |");
            for(int j = 0; j < n; j++){
                System.out.print(matAd[i][j]+" ");
            }
            System.out.print("\n");
        }
    }
    
    private void printMat2(int n) {
        System.out.println("{");
        for(int i = 0; i < n; i++){
            System.out.print("{");
            for(int j = 0; j < n; j++){
                System.out.print(matAd[i][j]+", ");
            }
            System.out.println("},");
            System.out.print("\n");
        }
        System.out.println("}");
    }
    
    private void hamilton(int n){
        int[] caminho = new int[n];
        
        for(int i = 0; i < n; i++){
            caminho[i] = -1;
        }
        
        caminho[0] = 0;
        nodes.get(0).setVisitado(true);
        //procura um ciclo hamiltoniano
        if(backtracking(caminho, 0, n, 1)==false){
            System.out.println("Ciclo nao encontrado");
        }else{
            printC(caminho, n);
        }
    }
    //verifica se o vertice ja foi vizitado
    private boolean podeAdd(Node no){
        if(no.isVisitado()){
            return false;
        }
        return true;
    }
    
    private boolean backtracking(int[] caminho, int pos, int n, int contador){
        //condição de parada: todos os vertices visitados sendo o ultimo vizinho do primeiro
        if(contador == n){
            if(nodes.get(caminho[contador-1]).getVizinhos().contains(nodes.get(caminho[0]))){
                return true;
            }
            else{
                return false;
            }
        }
        //constrói caminhos
        for(Node no : nodes.get(pos).getVizinhos()){
            if(podeAdd(no)){
                caminho[contador] = no.getId();
                contador++;
                no.setVisitado(true);
                if(backtracking(caminho, no.getId(), n, contador) == true){
                    return true;
                }
                contador--;
                caminho[contador] = -1;
                no.setVisitado(false);
            }
        }
        return false;
    }

    private void printC(int[] caminho, int n) {
        System.out.println("Ciclo encontrado");
        System.out.print("Rota: ");
        for(int i = 0; i<n; i++){
            System.out.print(caminho[i] + " ");
        }
        System.out.print(caminho[0] + " ");
        System.out.print("\n");
    }
}
