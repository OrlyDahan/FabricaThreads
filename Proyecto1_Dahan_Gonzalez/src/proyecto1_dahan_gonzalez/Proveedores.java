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
 * @author Amanda
 */
public class Proveedores extends Thread {
    
    private Semaphore SEM,SP,SC; //Semáforos SEM= exclusión mutua, SP= productores, SC= consumidores
    private Almacen almacen;
    private int tipoP; //Nos indica el tipo de productor que es (ruedas, parabrisas o motor)
    private boolean activo; //Si un proveedor esta contratado es true
    private LogicaThread fabrica;

    public Proveedores(Semaphore SEM, Semaphore SP, Semaphore SC, Almacen almacen, int tipoP, LogicaThread fabrica) {
        this.SEM = SEM;
        this.SP = SP;
        this.SC = SC;
        this.almacen = almacen;
        this.tipoP = tipoP;
        this.fabrica = fabrica;
        this.activo = true;
    }
    
    /*
    * Método para producir 
    */
    public void Producir () throws InterruptedException{
        this.sleep(2000);
        //Si el tipo es 1 es un productor de ruedas
        if(this.tipoP==1){
            System.out.println("PRODUCTOR DE TIPO " + this.tipoP);
            //tiempo que tarda en producir una rueda= 1 día
            this.sleep(1000 * (this.fabrica.getTiempoDia()/10));
            
            //Crea una rueda y la coloca en el almacen 
            //En la posición que apunta colocamos una rueda que sera el tipo de thread
            System.out.println("Apunta a" + this.fabrica.getApuntadorProvR());
            this.fabrica.getAlamacenRuedas().setArray(this.fabrica.getApuntadorProvR(), this.tipoP);
            //Cambiamos el apuntador
            this.fabrica.setApuntadorProvR((this.fabrica.getApuntadorProvR()+1)%this.fabrica.getAlamacenRuedas().getTamaño());
            //Aumentamos el contador de ruedas
            this.fabrica.setCantR(this.fabrica.getCantR()+1);
            //Mostramos los cambios en la interfaz
            if(this.fabrica.getCantR()<= this.fabrica.getAlmRuedas()){
                 this.fabrica.getInterfaz().getIntCantR().setText(Integer.toString(this.fabrica.getCantR()));
            }
           
             
        } else if (this.tipoP==2){ //Si el tipo es 2 es un productor de parabrisas
            //tiempo que tarda en producir un parabrisas= 2 días
            this.sleep(1000 * ((this.fabrica.getTiempoDia()*2)/10));
            
            this.fabrica.getAlmacenParabrisa().setArray(this.fabrica.getApuntadorProvP(), this.tipoP);
            this.fabrica.setApuntadorProvP((this.fabrica.getApuntadorProvP()+1)%this.fabrica.getAlmacenParabrisa().getTamaño());
           
            this.fabrica.setCantP(this.fabrica.getCantP()+1);
            
            if(this.fabrica.getCantP()<= this.fabrica.getAlmParabrisas()){
                this.fabrica.getInterfaz().getIntCantP().setText(Integer.toString(this.fabrica.getCantP()));
            }
            
            
        } else{ //Sino es un proveedor de motores
             //tiempo que tarda en producir un motor= 3 días
            this.sleep(1000 * ((this.fabrica.getTiempoDia()*3)/10));
            this.fabrica.getAlamacenMotor().setArray(this.fabrica.getApuntadorProvM(), this.tipoP);
            this.fabrica.setApuntadorProvM((this.fabrica.getApuntadorProvM()+1)%this.fabrica.getAlamacenMotor().getTamaño());
           
            this.fabrica.setCantM(this.fabrica.getCantM()+1);
            
             if(this.fabrica.getCantM()<= this.fabrica.getAlmMotor()){
                  this.fabrica.getInterfaz().getIntCantM().setText(Integer.toString(this.fabrica.getCantM()));
            
             }
            
           
        }
        this.sleep(2000);
        
    }
    
    /*
    * Metodo que inicia al thread
    */
    @Override
    public void run(){
        //Mientras el thread este activo
        while(activo==true){
            try {
                this.SP.acquire();
                this.SEM.acquire();
                this.Producir();
                this.SEM.release();
                this.SC.release();
                this.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Proveedores.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    //Getters y Setters

    public Semaphore getSEM() {
        return SEM;
    }

    public void setSEM(Semaphore SEM) {
        this.SEM = SEM;
    }

    public Semaphore getSP() {
        return SP;
    }

    public void setSP(Semaphore SP) {
        this.SP = SP;
    }

    public Semaphore getSC() {
        return SC;
    }

    public void setSC(Semaphore SC) {
        this.SC = SC;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public int getTipoP() {
        return tipoP;
    }

    public void setTipoP(int tipoP) {
        this.tipoP = tipoP;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LogicaThread getLogica() {
        return fabrica;
    }

    public void setLogica(LogicaThread fabrica) {
        this.fabrica = fabrica;
    }
    
    
 

 
}
