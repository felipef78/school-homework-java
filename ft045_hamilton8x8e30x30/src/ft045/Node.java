/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ft045;

import java.util.List;

/**
 *
 * @author Felipe
 */
class Node {
    private int id;
    private List<Node> vizinhos;
    private boolean visitado;

    public Node(int id, boolean visitado) {
        this.id = id;
        this.visitado = visitado;
    }

    public List<Node> getVizinhos() {
        return vizinhos;
    }

    public int getId() {
        return id;
    }

    public void setVizinhos(List<Node> vizinhos) {
        this.vizinhos = vizinhos;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }
}
