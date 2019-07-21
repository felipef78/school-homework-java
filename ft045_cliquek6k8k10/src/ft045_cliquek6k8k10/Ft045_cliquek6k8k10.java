/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ft045_cliquek6k8k10;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe
 */
public class Ft045_cliquek6k8k10 {
    private int[][] matAd;
    private List<ArrayList<Integer>> listAd;
    //private int[] grau;
    private List<Node> nodes;
    private final int kn = 10;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Ft045_cliquek6k8k10 ft = new Ft045_cliquek6k8k10();
        //lê o txt com o grafo
        Scanner txt = null;
        try {
            //txt = new Scanner(new BufferedReader(new FileReader("grafos/GrafoK6.txt")));
            //txt = new Scanner(new BufferedReader(new FileReader("grafos/GrafoK8.txt")));
            txt = new Scanner(new BufferedReader(new FileReader("grafos/GrafoK10.txt")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ft045_cliquek6k8k10.class.getName()).log(Level.SEVERE, null, ex);
        }
        int n = txt.nextInt();
        
        System.out.println(n);
        
        ft.loadGraph(txt, n); //cria lista de vertices
        ft.otimiza();
        ft.clique(n); //procura um clique e imprime-o
        String numerao = "1764209468357";
        //String numerao = "152";
        List<BigInteger> divisores = new ArrayList();        
        BigInteger k = new BigInteger(numerao).divide(new BigInteger("2"));
        BigInteger raiz = new BigInteger("20781");
        long r = k.multiply(raiz).longValueExact();
        //long startTime = System.nanoTime();
        long time = 0;
        int j = 0;
        for(long i = 1; i < r; i += 2){
            if( i%3 != 0 && i%5 != 0){
                long startTime = System.nanoTime();
                BigInteger aux = new BigInteger(Long.toString(i));
                BigInteger[] aux2 = k.divideAndRemainder(aux);
                if(aux2[1].longValueExact() == 0){
                    divisores.add(new BigInteger(Long.toString(i)));
                    divisores.add(aux2[0]);
                }
                long stopTime = System.nanoTime();
                time += (stopTime - startTime);
                j++;
                if(j==1000)
                    break;
            }
        }
        System.out.println(time/500);
        //long stopTime = System.nanoTime();
        //System.out.println(stopTime - startTime);
        System.out.println(k);
        System.out.println(r);
    }
public static BigInteger sqrt(BigInteger x) {
    BigInteger div = BigInteger.ZERO.setBit(x.bitLength()/2);
    BigInteger div2 = div;
    // Loop until we hit the same value twice in a row, or wind
    // up alternating.
    for(;;) {
        BigInteger y = div.add(x.divide(div)).shiftRight(1);
        if (y.equals(div) || y.equals(div2))
            return y;
        div2 = div;
        div = y;
        }
    }
    private void loadGraph(Scanner txt, int n) {
        //cria matriz de adjacencia
        matAd = new int[n][n];
        listAd = new ArrayList<>();
        nodes = new ArrayList<>();
        
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
        //
        
        //cria lista de adjacencias a partir da matriz
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
        //
        
        //cria lista de vertices a partir da lista de adj.
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
    
    private void clique(int n){
        List<Node> cliq = new ArrayList();
        //procura uma clique para cada vertice do grafo
        for(int i = 0; i < n; i++){
            cliq.add(nodes.get(i));
            nodes.get(i).setVisitado(true);
            //procura uma clique
            if(backtracking(cliq, i, 1)==false){
                //vertice i nao resulta em clique
                System.out.println("Clique " + i + " nao encontrado"); 
            }else{
                printC(cliq, kn); //mostra clique encontrada
                break;
            }
            cliq.remove(nodes.get(i));
            nodes.get(i).setVisitado(false);
        }
    }
    //verifica se o vertice ja foi vizitado e se eh vizinho de todos os outros
    //membros da clique
    private boolean podeAdd(Node no, List<Node> cliq){
        if(no.isVisitado()){
            return false;
        }
        for(Node n : cliq){
            if(no.getVizinhos().contains(n) == false){
                return false;
            }
        }
        return true;
    }
    
    private boolean backtracking(List<Node> cliq, int pos, int contador){
        //condição de parada: k vertices na clique
        if(contador == kn){
            return true;
        }
        //constrói clique com recursao
        for(Node no : nodes.get(pos).getVizinhos()){
            if(podeAdd(no, cliq)){ //checa condiçoes para entrar na clique
                cliq.add(no);
                contador++;
                no.setVisitado(true);
                if(backtracking(cliq, no.getId(), contador) == true){
                    return true;
                }
                //1System.out.println("max " + cliq.size());
                contador--;
                cliq.remove(no);
                no.setVisitado(false);
            }
        }
        return false;
    }

    private void printC(List<Node> cliq, int kn) {
        System.out.println("Clique encontrado");
        System.out.print("Vertices: ");
        for(int i = 0; i<kn; i++){
            System.out.print(cliq.get(i).getId() + " ");
        }
        System.out.print("\n");
    }

    private void otimiza() {
        for(Node n : nodes){
            if(n.getVizinhos().size() < kn){
                nodes.remove(n);
                for(Node n2 : n.getVizinhos()){
                    n2.getVizinhos().remove(n);
                }
            }
        }
    }
}
