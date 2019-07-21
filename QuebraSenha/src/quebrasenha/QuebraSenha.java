/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quebrasenha;

import java.awt.BorderLayout;
import java.awt.Container;
import java.lang.Runnable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

/**
 *
 * @author Felipe
 */
public class QuebraSenha {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String numerao = "640084161814599381273369059525873437";
        
        BigInteger k = new BigInteger(numerao);
        BigInteger x = BigInteger.valueOf(2);
        BigInteger y = BigInteger.valueOf(2);
        BigInteger d = BigInteger.ONE;
        BigInteger aux;
        while(d.compareTo(BigInteger.ONE) == 0){
            aux = x.multiply(x);
            aux = aux.add(BigInteger.ONE);
            x = aux.mod(k);
            
            aux = y.multiply(y);
            aux = aux.add(BigInteger.ONE);
            aux = aux.mod(k);
            aux = aux.multiply(aux);
            aux = aux.add(BigInteger.ONE);
            y = aux.mod(k);
            
            aux = x.subtract(y);
            d = aux.gcd(k);
        }
        if(d.compareTo(k) == 0){
            System.out.println("Falha");
        }
        else{
            System.out.println("Possivel senha1: "+d);
            System.out.println("Possivel senha2: "+k.divide(d));
        }
        
    }
        
    
    
}
