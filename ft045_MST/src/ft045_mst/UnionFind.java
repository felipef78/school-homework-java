/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ft045_mst;

/**
 *
 * @author rodri
 */
public class UnionFind {
    
    private int[] parent;
    private int[] rank;
    
    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }
    
    public int find(int item) {
        
        if(item>=parent.length){
            System.out.print("");
        }
        
        if (parent[item] == item) {
            return item;
        } else {
            parent[item] = find(parent[item]);
            return parent[item];
        }
    }
    
    public void union(int item1, int item2) {
        int rootItem1 = find(item1);
        int rootItem2 = find(item2);
        if (rootItem1 == rootItem2) return;
        
        // make root of smaller rank point to root of higher rank
        if (rank[rootItem1] < rank[rootItem2]){
            parent[rootItem1] = rootItem2;
        }
        else{ 
            parent[rootItem2] = rootItem1;        
        }    
    }

}
