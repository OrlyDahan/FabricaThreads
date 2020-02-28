/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1_dahan_gonzalez;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amanda Gonzalez
 */
public class Gerente extends Thread {
    
    //Atributos
    private LogicaThread logica;
    private Semaphore SEM, SEMD;
    private int tiempoDespacho;

    public Gerente(LogicaThread logica, Semaphore SEM, Semaphore SEMD, int tiempoDespacho) {
        this.logica = logica;
        this.SEM = SEM;
        this.SEMD = SEMD;
        this.tiempoDespacho = tiempoDespacho;
    }
    
    public void Despachar() throws InterruptedException{
        this.SEMD.acquire();
        this.logica.setCarrosEnsamblados(0);
        this.logica.getInterfaz().getCarrosTerm().setText(Integer.toString(this.logica.getCarrosEnsamblados()));
        this.SEMD.release();
    }
    
    public void Descansar () throws InterruptedException{
        
    }
    
    @Override
    public void run(){
        while(true){
            try {
                int aux = (int)(Math.random()*(18-6+1)+6);
                if(this.logica.getTiempoDespacho() != 0){
                    long dormir = aux* this.logica.getTiempoDia()/24;
                    this.logica.getInterfaz().getStatusGerente().setText("Durmiendo");
                    this.sleep(1000*dormir/10);
                } else {
                    this.SEM.acquire();
                    this.logica.getInterfaz().getStatusGerente().setText("Trabajando");
                    this.sleep(1000);
                    this.Despachar();
                    this.logica.setTiempoDespacho(this.tiempoDespacho);
                    this.logica.getInterfaz().getDiasDespacho().setText(Integer.toString(this.logica.getTiempoDespacho()));
                    this.SEM.release();
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Gerente.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
}
