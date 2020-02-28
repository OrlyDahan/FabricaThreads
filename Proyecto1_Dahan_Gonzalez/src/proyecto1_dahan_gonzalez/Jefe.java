/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1_dahan_gonzalez;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amanda Gonzalez
 */
public class Jefe extends Thread{
    
   //Atributos
    //Recibe la logica
    private LogicaThread logica;
    private Semaphore SEM; //Semáforo de exclusión mutua con el Gerente

    public Jefe(LogicaThread logica, Semaphore SEM) {
        this.logica = logica;
        this.SEM = SEM;
    }
   
    /**
     * Metodo que reduce los dias para el despacho
     */
    
    public void DiasDespacho(){
        // Si el tiempo de despacho es mayor que 0 entonces reducimos el tiempo
        if(this.logica.getTiempoDespacho()>0){
            this.logica.setTiempoDespacho(this.logica.getTiempoDespacho()-1);
            this.logica.getInterfaz().getDiasDespacho().setText(Integer.toString(this.logica.getTiempoDespacho()));
        }
    }
    
    //Metodo para iniciar el thread
    @Override
    public void run(){
        while(true){
            
            try {
                this.logica.getInterfaz().getStatusJefe().setText("Durmiendo");
                /*
                * /Al jefe le toma 1.5 horas hacer su funcion asi que el jefe se duerme
                * hasta que falten 1,5 horas para terminar un dia
                */
                long tiempoDormir = (long) ((22.5 * this.logica.getTiempoDia())/24); 
                this.sleep((1000*tiempoDormir/10));
                this.logica.getInterfaz().getStatusJefe().setText("Trabajando");
                this.sleep(1000);
                this.SEM.acquire();
                this.sleep((long)(1000*0.15));
                this.DiasDespacho();
                this.SEM.release();
            } catch (InterruptedException ex) {
                Logger.getLogger(Jefe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public LogicaThread getLogica() {
        return logica;
    }

    public void setLogica(LogicaThread logica) {
        this.logica = logica;
    }

    public Semaphore getSEM() {
        return SEM;
    }

    public void setSEM(Semaphore SEM) {
        this.SEM = SEM;
    }
    
    
}
