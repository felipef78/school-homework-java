/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ft045_mst;

/**
 *
 * @author Felipe
 */
class Edge implements Comparable<Edge>{
    private Node no1;
    private Node no2;
    private double dist;

    public Edge(Node no1, Node no2, double dist) {
        this.no1 = no1;
        this.no2 = no2;
        this.dist = dist;
    }

    public Node getNo1() {
        return no1;
    }

    public void setNo1(Node no1) {
        this.no1 = no1;
    }

    public Node getNo2() {
        return no2;
    }

    public void setNo2(Node no2) {
        this.no2 = no2;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    @Override
    public int compareTo(Edge o) {
        if(this.dist == o.getDist()){
           return 0;
       }
       if(this.dist < o.getDist()){
           return -1;
       }else{
           return 1;
       }
    }
    
    
}
