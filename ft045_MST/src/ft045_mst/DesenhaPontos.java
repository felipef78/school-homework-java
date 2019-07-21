package ft045_mst;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.processing.Filer;

/**
 *
 * @author augustomeira, Felipe
 */
public class DesenhaPontos {

    int[][][] figura;
    int xmax = Integer.MIN_VALUE;
            int ymax = Integer.MIN_VALUE;
            int xmin = Integer.MAX_VALUE;
            int ymin = Integer.MAX_VALUE;
    List<Node> nodes = new ArrayList();
    List<Edge> edges = new ArrayList();
    List<Edge> arvore = new ArrayList();
    static final File dir = new File("grafos/coordenadas.txt");

    public void setPreto(int i, int j) {
        figura[i][j][0] = 0;
        figura[i][j][1] = 0;
        figura[i][j][2] = 0;
    }
    
    public void setAzul(int i, int j) {
        figura[i][j][0] = 0;
        figura[i][j][1] = 0;
        figura[i][j][2] = 255;
    }

    public void setBranco(int i, int j) {
        figura[i][j][0] = 255;
        figura[i][j][1] = 255;
        figura[i][j][2] = 255;
    }

    public void imprimePontos() {
        try {
            

            //primeira vez encontra os limites xmax, xmin, ymax, ymin
            //segunda vez imprime o arquivo ppm

            for (int vez = 0; vez < 2; vez++) {
                int dx = 0;
                int dy = 0;
                BufferedWriter out = null;
                if (vez == 1) {
                    dx = xmax - xmin + 1;
                    dy = ymax - ymin + 1;
                    //cada posicao da matrix eh uma coordenada x1,y1 com tres
                    //dimensoes [x1][y1][0] [x1][y1][1] [x1][y1][2] para R-G-B
                    figura = new int[dx][dy][3];
                    out = new BufferedWriter(new FileWriter(new File( "grafos/img.ppm")));
                    //cabecalho da figura ppm
                    //http://en.wikipedia.org/wiki/Netpbm_format#PPM_example
                    out.write("P3\n");
                    out.write(dy + " " + dx + "\n");
                    out.write("255\n");

                    //inicializa matriz quadriculada
                    for (int i = 0; i < dx; i++) {
                        for (int j = 0; j < dy; j++) {
                            if (i % 100 == 9 || j % 100 == 9) {
                                setPreto(i, j);
                            } else {
                                setBranco(i, j);
                            }

                        }
                    }

                }


                //coordenadas.
                BufferedReader b =
                        new BufferedReader(
                        new FileReader(
                        new File("grafos/coordenadas.txt")));
                //leitura
                String linha = b.readLine();
                while (linha != null && linha.length() > 0) {
                    String[] lista = linha.split(";");

                    double a1 = Double.parseDouble(lista[3]);
                    double a2 = Double.parseDouble(lista[4]);

                    //converte em inteiro com um fator de escala 30.
                    int x = (int) (a1 * 30);
                    int y = (int) (a2 * 30);

                    
                    if (vez == 0) {
                        //procura min  e max
                        if (x < xmin) {
                            xmin = x;
                        }
                        if (x > xmax) {
                            xmax = x;
                        }
                        if (y < ymin) {
                            ymin = y;
                        }
                        if (y > ymax) {
                            ymax = y;
                        }
                    } else {
                        //imprime latitude e longitude
                        setPreto(x - xmin, y - ymin);
                    }
                    assert (ymin <= ymax);

                    linha = b.readLine();
                }
                if (vez == 1) {
                    imprimeArestas();
                    //imprime o arquivo.
                    for (int i = dx - 1; i >= 0; i--) {
                        for (int j = 0; j < dy; j++) {
                            out.write(figura[i][j][0] + " " +
                                    figura[i][j][1] + " " +
                                    figura[i][j][2] + " ");
                        }
                        out.write("\n");
                    }

                    out.write("\n");
                    out.close();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DesenhaPontos.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    //leitura de pontos. Exemplo.
    public void le(){
        int contador = 0;
        File dir =
            new File("");
        BufferedReader b = null;
        try{
            b = new BufferedReader(
                    new FileReader(
                    new File("grafos/coordenadas.txt")));
            String linha = b.readLine();
            while (linha != null && linha.length() > 0) {
                String[] lista = linha.split(";");
                for(int i=0;i<lista.length;i++){
                    //System.out.println(lista[i]+" ");
                }
                
                String cid = lista[0];
                String est = lista[1];
                String sig = lista[2];
                double lati = Double.parseDouble(lista[3]);
                double longi = Double.parseDouble(lista[4]);
                
                Node no = new Node(contador, cid, est, sig, lati, longi);
                nodes.add(no);
                contador++;
                linha = b.readLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }       
    }
    
    public double distancia(double lat1, double lat2, double lon1, double lon2){
        double r = 6371.0;
        double dLat = (lat2 - lat1) * Math.PI/180.0;
        double dLon = (lon2 - lon1) * Math.PI/180.0;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(lat1*Math.PI/180) * Math.cos(lat2*Math.PI/180)
                * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = r*c;
        return d;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DesenhaPontos m = new DesenhaPontos();
        m.le();
//        Node n1 = null;
//        Node n2 = null;
//        for(Node n : m.nodes){
//            if(n.getCidade().compareTo("Vila dos Remedios") == 0){
//                n1 = n;
//                System.out.println("oi");
//            }
//            if(n.getCidade().compareTo("Natal") == 0){
//                System.out.println("oi");
//                n2 = n;
//            }
//        }
//        double d = m.distancia(n1.getLat(), n1.getLon(), n2.getLat(), n2.getLon());
//        int dis = (int) d * 30;
//        System.out.println(d + " " + dis);
        m.criaArestas();
        m.mst();
        m.imprimePontos();
        m.imprimeArvore();
        
    }

    private void criaArestas() {
        int con = 0;
        for(Node no : nodes){
            for(int i = no.getId()+1; i < nodes.size(); i++){
                Node noh = nodes.get(i);
                double dist = distancia(no.getLat(), no.getLon(), noh.getLat(), noh.getLon());
                if(dist < 3500.0){
                    con++;
                    //System.out.println(con);
                    Edge ed = new Edge(no, noh, dist);
                    edges.add(ed);
                }
            }
        }
    }

    private void mst() {
        Collections.sort(edges);
        
        int n = nodes.size();
        
        UnionFind uf = new UnionFind(n);
        
        int contador = 0;
        
        for(Edge e : edges){
            if(contador < n){
                Node v1 = e.getNo1();
                Node v2 = e.getNo2();
                
                if(uf.find(v1.getId()) != uf.find(v2.getId())){
                    arvore.add(e);
                    
                    uf.union(v1.getId(), v2.getId());
                    contador++;
                }
            }
            else{
                break;
            }
            
        }
    }

    private void imprimeArvore() {
        double custo = 0;
        System.out.println(arvore.size());
        for(Edge e : arvore){
            System.out.println(e.getNo1().getCidade()+", "+e.getNo1().getSigla()+" - "+e.getNo2().getCidade()+", "+e.getNo2().getSigla());
            custo += e.getDist();
        }
        System.out.println(custo);
    }
    
    public void bresenham(int x1, int y1, int x2, int y2){
        int d = 0;
 
        int dy = Math.abs(y2 - y1);
        int dx = Math.abs(x2 - x1);
 
        int dy2 = (dy << 1); // slope scaling factors to avoid floating
        int dx2 = (dx << 1); // point
 
        int ix = x1 < x2 ? 1 : -1; // increment direction
        int iy = y1 < y2 ? 1 : -1;
 
        if (dy <= dx) {
            for (;;) {
                setAzul(x1, y1);
                if (x1 == x2)
                    break;
                x1 += ix;
                d += dy2;
                if (d > dx) {
                    y1 += iy;
                    d -= dx2;
                }
            }
        } else {
            for (;;) {
                setAzul(x1, y1);
                if (y1 == y2)
                    break;
                y1 += iy;
                d += dx2;
                if (d > dy) {
                    x1 += ix;
                    d -= dy2;
                }
            }
        }
    }

    private void imprimeArestas() {
        for(Edge e : edges){
            double a1 = e.getNo1().getLat();
            double a2 = e.getNo1().getLon();
            double a3 = e.getNo2().getLat();
            double a4 = e.getNo2().getLon();

            //converte em inteiro com um fator de escala 30.
            int x1 = (int) (a1 * 30);
            int y1 = (int) (a2 * 30);
            int x2 = (int) (a3 * 30);
            int y2 = (int) (a4 * 30);
            
            bresenham(x1-xmin, y1-ymin, x2-xmin, y2-ymin);
        }
    }
}
