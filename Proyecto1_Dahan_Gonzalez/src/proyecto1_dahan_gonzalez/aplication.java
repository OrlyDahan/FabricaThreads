/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1_dahan_gonzalez;

import java.io.FileNotFoundException;

/**
 *
 * @author Orly
 */
public class aplication {

   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        aplication app = new aplication();
        app.Correr();
    }
     
    public void Correr () throws FileNotFoundException{
        LogicaThread logica = new LogicaThread();
        Interfaz interfaz = new Interfaz (logica);
        logica.setInterfaz(interfaz);
        logica.Leer(); //Leemos los datos 
        logica.CrearArrays(); //Creamos los arrays que guardaran a los productores y ensambladores
        logica.CrearAlmacenes(); //Creamos los almacenes
        logica.CrearSemaforos(); //Creamos los semaforos
        logica.proveedoresIniciales(); //Creamos los productores iniciales
        logica.ensambladorInicial(); //Creamos los ensambladores iniciales
        //Les asignamos la logica a los threads
        logica.LogicaProveedores(logica);
        logica.LogicaEnsambladores(logica);
        logica.getInterfaz().setVisible(true);
        logica.Start(); //Iniciamos el simulador
        
     
    }
    
    

}
