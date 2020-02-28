/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1_dahan_gonzalez;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Amanda
 */
public class Almacen {
    
    //Atributos 
    private int [] almacen;
    private int tamaño;
    //private int p=0,c=0; //Posición del proximo consumidor y el productor
    
    //Constructor
    public Almacen(int tamaño){
        this.tamaño = tamaño;
        this.almacen = new int [tamaño];
    }
    
    //Metodo que imprime el array (para Debbuging)
    
    public void imprimirAlmacen (){
        for (int i = 0; i < this.tamaño ; i++) {
            System.out.print(this.almacen[i] + " ");
        }
        System.out.println("");
    }
    
    
    //Getters y setters
    
    public int getArray(int x, int valor){
        return this.almacen[x]=valor;
    }
    
    public void setArray(int x, int valor){
        this.almacen[x]=valor;
    }

    public int[] getAlmacen() {
        return almacen;
    }

    public void setAlmacen(int[] almacen) {
        this.almacen = almacen;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }
    
    
 
    
    
}
