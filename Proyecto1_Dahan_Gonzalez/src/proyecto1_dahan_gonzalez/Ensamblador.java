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
public class Ensamblador extends Thread{
    //Atributos
    
    //Semaforos del almacen de las ruedas
    private Semaphore SEMR, SPR, SCR;//Semáforos SEM= exclusión mutua, SP= productores, SC= consumidores
    //Semaforos del almacen de parabrisas
    private Semaphore SEMP, SPP, SCP;
    //Semaforos del almacen de motores
    private Semaphore SEMM, SPM, SCM;
    //Semaforo de EM con el gerente en el deposito
    private Semaphore SEMD;
    
    private LogicaThread fabrica;
    private boolean activo;

    public Ensamblador(Semaphore SEMR, Semaphore SPR, Semaphore SCR, Semaphore SEMP, Semaphore SPP, Semaphore SCP, Semaphore SEMM, Semaphore SPM, Semaphore SCM, LogicaThread fabrica, Semaphore SEMD) {
        this.SEMR = SEMR;
        this.SPR = SPR;
        this.SCR = SCR;
        this.SEMP = SEMP;
        this.SPP = SPP;
        this.SCP = SCP;
        this.SEMM = SEMM;
        this.SPM = SPM;
        this.SCM = SCM;
        this.fabrica = fabrica;
        this.SEMD = SEMD;
        this.activo = true;
    }
    
    
    
    //Metodos
    
    /*
    * Metodo para consumir ruedas
    */
    public void EnsamblarR(){
        System.out.println("ENSAMBLANDO RUEDAS");
        //Se coloca un 0 en la posicion del array  donde se consume
        this.fabrica.getAlamacenRuedas().setArray(this.fabrica.getApuntadorConsR(), 0);
        this.fabrica.setApuntadorConsR((this.fabrica.getApuntadorConsR()+1)%(this.fabrica.getAlamacenRuedas().getTamaño())); //Cambiamos el apuntador
        //Si hay ruedas, cambiamos la cantidad de ruedas a las consumidas por el ensamblador
        if(this.fabrica.getCantR()>0){
            this.fabrica.setCantR(this.fabrica.getCantR()-4);
            this.fabrica.getInterfaz().getIntCantR().setText(Integer.toString(this.fabrica.getCantR()));
        }
        
    }
    
    /*
    * Metodo para consumir parabrisas
    */
    public void EnsamblarP(){
        //Se coloca un 0 en la posicion del array  donde se consume
        System.out.println("ENSAMBLANDO PARABRISAS");
        this.fabrica.getAlmacenParabrisa().setArray(this.fabrica.getApuntadorConsP(), 0);
        this.fabrica.setApuntadorConsP((this.fabrica.getApuntadorConsP()+1)%(this.fabrica.getAlmacenParabrisa().getTamaño())); //Cambiamos el apuntador
        if(this.fabrica.getCantP()>0){
            this.fabrica.setCantP(this.fabrica.getCantP()-1);
            this.fabrica.getInterfaz().getIntCantP().setText(Integer.toString(this.fabrica.getCantP()));
        }
        
    }
    
    /*
    * Metodo para consumir motores
    */
    public void EnsamblarM(){
         System.out.println("ENSAMBLANDO MOTORES");
        //Se coloca un 0 en la posicion del array  donde se consume
        this.fabrica.getAlamacenMotor().setArray(this.fabrica.getApuntadorConsM(), 0);
        this.fabrica.setApuntadorConsM((this.fabrica.getApuntadorConsM()+1)%(this.fabrica.getAlamacenMotor().getTamaño())); //Cambiamos el apuntador
        //Si hay entradas, cambiamos la cantidad de entradas a las consumidas por el ensamblador
        if(this.fabrica.getCantM()>0){
            this.fabrica.setCantM(this.fabrica.getCantM()-1);
            this.fabrica.getInterfaz().getIntCantM().setText(Integer.toString(this.fabrica.getCantM()));
        }
        
    }
    
      /*
    * Metodo que inicia al thread
    */
    @Override
    public void run(){
        while(activo== true){
            try {
                //Semáforos SEM= exclusión mutua, SP= productores, SC= consumidores
                //Acceder al almacen de las ruedas y consumirlo
                this.SCR.acquire(4);
                this.SCP.acquire();
                this.SCM.acquire();
                this.SEMR.acquire();
                this.SEMP.acquire();
                this.SEMM.acquire();
                this.EnsamblarR();
                this.EnsamblarP();
                this.EnsamblarM();
                
                //Tiempo que tarda en ensamblar un carro = 2 dias
                this.sleep(1000*this.fabrica.getTiempoDia()/10);
                //this.sleep(1000*((this.fabrica.getTiempoDia()*2)/10));
                this.SEMM.release();
                this.SEMP.release();
                this.SEMR.release();
                this.SPM.release();
                this.SPP.release();
                this.SPR.release(4);
                
                
                
                //Mostrar cambios en la vista sobre carros ensamblados
                this.SEMD.acquire();
                this.fabrica.setCarrosEnsamblados(this.fabrica.getCarrosEnsamblados()+1); //Aumentamos el contador de carros completados
                this.fabrica.getInterfaz().getCarrosTerm().setText(Integer.toString(this.fabrica.getCarrosEnsamblados()));
                this.SEMD.release();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Ensamblador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
 //Getters y Setters
    public Semaphore getSEMR() {
        return SEMR;
    }

    public void setSEMR(Semaphore SEMR) {
        this.SEMR = SEMR;
    }

    public Semaphore getSPR() {
        return SPR;
    }

    public void setSPR(Semaphore SPR) {
        this.SPR = SPR;
    }

    public Semaphore getSCR() {
        return SCR;
    }

    public void setSCR(Semaphore SCR) {
        this.SCR = SCR;
    }

    public Semaphore getSEMP() {
        return SEMP;
    }

    public void setSEMP(Semaphore SEMP) {
        this.SEMP = SEMP;
    }

    public Semaphore getSPP() {
        return SPP;
    }

    public void setSPP(Semaphore SPP) {
        this.SPP = SPP;
    }

    public Semaphore getSCP() {
        return SCP;
    }

    public void setSCP(Semaphore SCP) {
        this.SCP = SCP;
    }

    public Semaphore getSEMM() {
        return SEMM;
    }

    public void setSEMM(Semaphore SEMM) {
        this.SEMM = SEMM;
    }

    public Semaphore getSPM() {
        return SPM;
    }

    public void setSPM(Semaphore SPM) {
        this.SPM = SPM;
    }

    public Semaphore getSCM() {
        return SCM;
    }

    public void setSCM(Semaphore SCM) {
        this.SCM = SCM;
    }

    public LogicaThread getLogica() {
        return fabrica;
    }

    public void setLogica(LogicaThread fabrica) {
        this.fabrica = fabrica;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
    
    
    
    
}
