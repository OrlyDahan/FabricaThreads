/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1_dahan_gonzalez;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amanda Gonzalez
 */
public class Debugger extends Thread{
    
    //Clase para hacer debugg de que este funcionando bien la logica de los threads
    
    private LogicaThread logica;

    public Debugger(LogicaThread logica) {
        this.logica = logica;
    }
    
    
    @Override
    public void run(){
        while(true){
            try{
                System.out.println("Almacen de Ruedas");
                this.logica.getAlamacenRuedas().imprimirAlmacen();
                System.out.println("Almacen de Parabrisas");
                this.logica.getAlmacenParabrisa().imprimirAlmacen();
                System.out.println("Almacen de Motores");
                this.logica.getAlamacenMotor().imprimirAlmacen();
                this.sleep(1000);
            }catch(Exception e){
                Logger.getLogger(Debugger.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    
    
    
}
