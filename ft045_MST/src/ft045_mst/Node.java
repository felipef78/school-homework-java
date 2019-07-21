/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ft045_mst;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Felipe
 */
class Node {
    private int id;
    private String cidade;
    private String estado;
    private String sigla;
    private double lat;
    private double lon;
    private List<Node> vizinhos;
    private List<Edge> arestas;
    private boolean visitado;

    public Node(int id, String cidade, String estado, String sigla, double lat, double lon) {
        this.id = id;
        this.cidade = cidade;
        this.estado = estado;
        this.sigla = sigla;
        this.lat = lat;
        this.lon = lon;
        this.arestas = new ArrayList();
        this.vizinhos = new ArrayList();
        this.visitado = false;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public List<Edge> getArestas() {
        return arestas;
    }

    public void setArestas(List<Edge> arestas) {
        this.arestas = arestas;
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
