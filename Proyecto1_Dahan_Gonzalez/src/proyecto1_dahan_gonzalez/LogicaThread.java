/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1_dahan_gonzalez;

import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Integer.parseInt;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

/**
 *
 * @author Amanda
 */
public class LogicaThread {
    
    /*
    * Lógica que deben de seguir los threads en el programa
    */

    //Atributos
    private Interfaz interfaz;
    private Proveedores pRuedas[], pParabrisa[], pMotor[]; //Hay tres tipos de proveedores
    private Ensamblador ensamblador[];//El esamblador maneja los 3 productos de los proveedores
    private int tiempoDia, tiempoDespacho; //tiempo que dura un día en el programa
    //Tamaño de los almacenes
    private int almRuedas, almParabrisas, almMotor;
    //Almacenes 
    private Almacen alamacenRuedas, almacenParabrisa, alamacenMotor;
    //Cantidad inicial de proveedores y ensambladores
    private int provInicialR, provInicialP, provInicialM;
    private int ensInicial;
    //Cantidad máxima de proveedores y ensambladores
    private int maxProvR, maxProvP, maxProvM;
    private int maxEns;
    //Apuntadores a las siguientes posiciones del almacen
    private int apuntadorProvR=0, apuntadorProvP=0, apuntadorProvM=0;
    //Contador de la cantidad de producto en el almacén
    private int cantR=0, cantP=0, cantM=0;
    //Apuntador de donde va a consumir el ensamblador
    private int apuntadorConsR=0, apuntadorConsP=0, apuntadorConsM=0;
    //Contador de carros ensamblados
    private int carrosEnsamblados =0;
    //Semaforos del prgrama
    //Semáforos SEM= exclusión mutua, SP= productores, SC= consumidores
    private Semaphore SPR,SCR,SEMR; //Semaforos del almacen de ruedas
    private Semaphore SPP,SCP,SEMP; //Semaforos del almacen de parabrisas
    private Semaphore SPM,SCM,SEMM; //Semaforos del almacen de motores
    private Semaphore SEMD; //Semaforo del deposito de carros
    private Semaphore SEM; //Semafor de entre jefe y gerente
    
    private Gerente gerente;
    private Jefe jefe;
  
    
    private LogicaThread fabrica;
    
    //Constructor

    public LogicaThread() {
        this.fabrica = this;
    }
   
    //Metodos
    
    /*
    * Metodo para leer los datos del archivo de texto
    */
    public void Leer() throws FileNotFoundException{
        Scanner d = new Scanner(new File("C:\\Users\\Amanda\\Desktop\\Proyecto1_Dahan_Gonzalez\\Proyecto1_Dahan_Gonzalez\\src\\proyecto1_dahan_gonzalez\\DatosIniciales.txt"));
        String line=d.nextLine();
    
        this.tiempoDia= parseInt(line.substring(20,25).trim());
        System.out.println(this.tiempoDia);
        line=d.nextLine();
        if(this.tiempoDia<0){
            JOptionPane.showMessageDialog(null, "Dato invalido");  
        }
        
        this.tiempoDespacho= parseInt(line.substring(20,25).trim());
        System.out.println(this.tiempoDespacho);
        line=d.nextLine();
        if(this.tiempoDespacho<0){
            JOptionPane.showMessageDialog(null, "Dato invalido");  
        }
      
        this.almRuedas= parseInt(line.substring(20,25).trim());
        System.out.println(this.almRuedas);
        line=d.nextLine();
        if(this.almRuedas<0){
            JOptionPane.showMessageDialog(null, "Dato invalido");
        }

         this.almParabrisas= parseInt(line.substring(20,25).trim());
        System.out.println(this.almParabrisas);
        line=d.nextLine();
        if(this.almParabrisas<0){
            JOptionPane.showMessageDialog(null, "Dato invalido");
        }
        
        this.almMotor= parseInt(line.substring(20,25).trim());
        System.out.println(this.almMotor);
        line=d.nextLine();
        if(this.almMotor<0){
            JOptionPane.showMessageDialog(null, "Dato invalido");
        }
        
        this.provInicialR= parseInt(line.substring(20,25).trim());
        System.out.println(this.provInicialR);
        line=d.nextLine();
        if(this.provInicialR<0){
            JOptionPane.showMessageDialog(null, "Dato invalido");
        }
        
        this.provInicialP= parseInt(line.substring(20,25).trim());
        System.out.println(this.provInicialP);
        line=d.nextLine();
        if(this.provInicialP<0){
            JOptionPane.showMessageDialog(null, "Dato invalido");
        }
        
        this.provInicialM= parseInt(line.substring(20,25).trim());
        System.out.println(this.provInicialM);
        line=d.nextLine();
        if(this.provInicialM<0){
            JOptionPane.showMessageDialog(null, "Dato invalido");
        }
        
        this.maxProvR= parseInt(line.substring(20,25).trim());
        System.out.println(this.maxProvR);
        line=d.nextLine();
        if(this.maxProvR<0){
            JOptionPane.showMessageDialog(null, "Dato invalido");
        }
        
        this.maxProvP= parseInt(line.substring(20,25).trim());
        System.out.println(this.maxProvP);
        line=d.nextLine();
        if(this.maxProvP<0){
            JOptionPane.showMessageDialog(null, "Dato invalido");
        }
        
        this.maxProvM= parseInt(line.substring(20,25).trim());
        System.out.println(this.maxProvM);
        line=d.nextLine();
        if(this.maxProvM<0){
            JOptionPane.showMessageDialog(null, "Dato invalido");
        }
        
        this.ensInicial= parseInt(line.substring(20,25).trim());
        System.out.println(this.ensInicial);
        line=d.nextLine();
        if(this.ensInicial<0){
            JOptionPane.showMessageDialog(null, "Dato invalido");
        }
        
        this.maxEns= parseInt(line.substring(20,25).trim());
        System.out.println(this.maxEns);
        if(this.maxEns<0){
            JOptionPane.showMessageDialog(null, "Dato invalido");
        }
    }
    
    /*
    * Metodo que crea los ensambladores y proveedores iniciales
    */
    
    public void proveedoresIniciales(){
        for (int i = 0; i < this.provInicialR; i++) {
            this.pRuedas[i] = new Proveedores (this.SEMR, this.SPR, this.SCR, this.alamacenRuedas,1, this.fabrica);
            System.out.println("Proveedor de ruedas "+ i);
            System.out.println(pRuedas[i]);
        }
        for (int i = 0; i < this.provInicialP; i++) {
            
            this.pParabrisa[i] = new Proveedores (this.SEMP, this.SPP, this.SCP, this.almacenParabrisa,2, this.fabrica);
            System.out.println("Proveedor de parabrisas "+ i);
            System.out.println(pParabrisa[i]);
        }
        for (int i = 0; i < this.provInicialM; i++) {
    
            this.pMotor[i] = new Proveedores (this.SEMM, this.SPM, this.SCM, this.alamacenMotor,3, this.fabrica);
            System.out.println("Proveedor de motor "+ i);
            System.out.println(pMotor[i]);
        }
    }
    
    public void ensambladorInicial(){
        for (int i = 0; i < this.ensInicial; i++) {
            System.out.println("Ensambladores Iniciales: "+ this.ensInicial);
            System.out.println("i" + i);
            this.ensamblador[i]= new Ensamblador (this.SEMR, this.SPR, this.SCR, this.SEMP, this.SPP, this.SCP,this.SEMM, this.SPM, this.SCM, this.fabrica, this.SEMD);
        }
    }
    /*
    * Metodo para crear semaforos
    */
     public void CrearSemaforos(){
        //Semaforos del alamacen de ruedas 
        this.SPR = new Semaphore(this.alamacenRuedas.getTamaño());
        this.SEMR = new Semaphore(1);
        this.SCR = new Semaphore(0);
        //Semaforos del almacen de parabrisas 
        this.SPP = new Semaphore(this.almacenParabrisa.getTamaño());
        this.SEMP = new Semaphore(1);
        this.SCP = new Semaphore(0);
        //Semaforos del almacen de motores
        this.SPM = new Semaphore(this.alamacenMotor.getTamaño());
        this.SEMM = new Semaphore(1);
        this.SCM = new Semaphore(0);
        //Semaforos de jefe y gerente
        this.SEMD = new Semaphore(1);
        this.SEM  = new Semaphore(1);
   
    }
     /*
    * Metodo para el gerente y el jefe
    */
      public void CrearJefe(){
          this.jefe = new Jefe (this.fabrica, this.SEM);
          jefe.start();
      }
      
       public void CrearGerente(){
           this.gerente = new Gerente (this.fabrica, this.SEM, this.SEMD, this.tiempoDespacho);
           gerente.start();
        
    }
    
    /*
    * Metodo para crear los almacenes
    */
    public void CrearAlmacenes(){
        this.alamacenRuedas = new Almacen (this.almRuedas);
        this.almacenParabrisa = new Almacen (this.almParabrisas);
        this.alamacenMotor  = new Almacen (this.almMotor);
    }
    
    /*
    * Metodo que crea los arrays donde estaran los proveedores y los ensambladores
    */
    
    public void CrearArrays (){
        System.out.println("Creado array de ruedas");
        this.pRuedas = new Proveedores [this.maxProvR];
        System.out.println("Creado array de Parabrisas");
        this.pParabrisa = new Proveedores [this.maxProvP];
        System.out.println("Creado array de Motores");
        this.pMotor = new Proveedores [this.maxProvM];
        System.out.println("Creado array de Ensambladores");
        this.ensamblador = new Ensamblador [this.maxEns];
    }
    
    /*
    * Metodo para asignar la logica a los threads de proveedores y ensambladores
    */
    public void LogicaProveedores(LogicaThread logica){
        for (int i = 0; i < this.maxProvR; i++) {
            //Si hay un proveedor le asignamos la logica
            if(this.pRuedas[i] != null){
                this.pRuedas[i].setLogica(logica);
            }
        }
        
        for (int i = 0; i < this.maxProvP; i++) {
            //Si hay un proveedor le asignamos la logica
            if(this.pParabrisa[i] != null){
                this.pParabrisa[i].setLogica(logica);
            }
        }
        
        for (int i = 0; i < this.maxProvM; i++) {
            //Si hay un proveedor le asignamos la logica
            if(this.pMotor[i] != null){
                this.pMotor[i].setLogica(logica);
            }
        }
    }
    
    public void LogicaEnsambladores(LogicaThread logica){
          for (int i = 0; i < this.maxEns; i++) {
            //Si hay un ensamblador le asignamos la logica
            if(this.ensamblador[i] != null){
                this.ensamblador[i].setLogica(logica);
            }
        }
    }
    
    /*
    * Metodo para iniciar el simulador
    */
    public void Start(){
        for (int i = 0; i < this.pRuedas.length; i++) {
            //Si hay un proveedor lo iniciamos
            if (pRuedas[i] != null) {
                pRuedas[i].start();
            }
        }
        
         for (int i = 0; i < this.pParabrisa.length; i++) {
            //Si hay un proveedor lo iniciamos
            if (pParabrisa[i] != null) {
                pParabrisa[i].start();
            }
        }
         
          for (int i = 0; i < this.pMotor.length; i++) {
            //Si hay un proveedor lo iniciamos
            if (pMotor[i] != null) {
                pMotor[i].start();
            }
        }
          
           for (int i = 0; i < this.ensamblador.length; i++) {
            //Si hay un proveedor lo iniciamos
            if (ensamblador[i] != null) {
                ensamblador[i].start();
            }
        }
       
        //Mostramos los cambios en la vista
        this.fabrica.getInterfaz().getProdRuedas().setText(Integer.toString(this.fabrica.provInicialR));
        this.fabrica.getInterfaz().getProdPara().setText(Integer.toString(this.fabrica.provInicialP));
        this.fabrica.getInterfaz().getProdMotor().setText(Integer.toString(this.fabrica.provInicialM));
        this.fabrica.getInterfaz().getCantEnsam().setText(Integer.toString(this.fabrica.ensInicial));
        //Iniciamos al jefes y al gerente
        this.CrearJefe();
        this.CrearGerente();
        this.fabrica.getInterfaz().getDiasDespacho().setText(Integer.toString(this.fabrica.getTiempoDespacho()));
        
    }
    
     
    
    //Getters y Setters

    public Proveedores[] getpRuedas() {
        return pRuedas;
    }

    public void setpRuedas(Proveedores[] pRuedas) {
        this.pRuedas = pRuedas;
    }

    public Proveedores[] getpParabrisa() {
        return pParabrisa;
    }

    public void setpParabrisa(Proveedores[] pParabrisa) {
        this.pParabrisa = pParabrisa;
    }

    public Proveedores[] getpMotor() {
        return pMotor;
    }

    public void setpMotor(Proveedores[] pMotor) {
        this.pMotor = pMotor;
    }

    public Ensamblador[] getEnsamblador() {
        return ensamblador;
    }

    public void setEnsamblador(Ensamblador[] ensamblador) {
        this.ensamblador = ensamblador;
    }

    public int getTiempoDia() {
        return tiempoDia;
    }

    public void setTiempoDia(int tiempoDia) {
        this.tiempoDia = tiempoDia;
    }

    public int getTiempoDespacho() {
        return tiempoDespacho;
    }

    public void setTiempoDespacho(int tiempoDespacho) {
        this.tiempoDespacho = tiempoDespacho;
    }

    public int getAlmRuedas() {
        return almRuedas;
    }

    public void setAlmRuedas(int almRuedas) {
        this.almRuedas = almRuedas;
    }

    public int getAlmParabrisas() {
        return almParabrisas;
    }

    public void setAlmParabrisas(int almParabrisas) {
        this.almParabrisas = almParabrisas;
    }

    public int getAlmMotor() {
        return almMotor;
    }

    public void setAlmMotor(int almMotor) {
        this.almMotor = almMotor;
    }

    public int getProvInicialR() {
        return provInicialR;
    }

    public void setProvInicialR(int provInicialR) {
        this.provInicialR = provInicialR;
    }

    public int getProvInicialP() {
        return provInicialP;
    }

    public void setProvInicialP(int provInicialP) {
        this.provInicialP = provInicialP;
    }

    public int getProvInicialM() {
        return provInicialM;
    }

    public void setProvInicialM(int provInicialM) {
        this.provInicialM = provInicialM;
    }

    public int getMaxProvR() {
        return maxProvR;
    }

    public void setMaxProvR(int maxProvR) {
        this.maxProvR = maxProvR;
    }

    public int getMaxProvP() {
        return maxProvP;
    }

    public void setMaxProvP(int maxProvP) {
        this.maxProvP = maxProvP;
    }

    public int getMaxProvM() {
        return maxProvM;
    }

    public void setMaxProvM(int maxProvM) {
        this.maxProvM = maxProvM;
    }

    public int getApuntadorProvR() {
        return apuntadorProvR;
    }

    public void setApuntadorProvR(int apuntadorProvR) {
        this.apuntadorProvR = apuntadorProvR;
    }

    public int getApuntadorProvP() {
        return apuntadorProvP;
    }

    public void setApuntadorProvP(int apuntadorProvP) {
        this.apuntadorProvP = apuntadorProvP;
    }

    public int getApuntadorProvM() {
        return apuntadorProvM;
    }

    public void setApuntadorProvM(int apuntadorProvM) {
        this.apuntadorProvM = apuntadorProvM;
    }

    public int getCantR() {
        return cantR;
    }

    public void setCantR(int cantR) {
        this.cantR = cantR;
    }

    public int getCantP() {
        return cantP;
    }

    public void setCantP(int cantP) {
        this.cantP = cantP;
    }

    public int getCantM() {
        return cantM;
    }

    public void setCantM(int cantM) {
        this.cantM = cantM;
    }

    public Almacen getAlamacenRuedas() {
        return alamacenRuedas;
    }

    public void setAlamacenRuedas(Almacen alamacenRuedas) {
        this.alamacenRuedas = alamacenRuedas;
    }

    public Almacen getAlmacenParabrisa() {
        return almacenParabrisa;
    }

    public void setAlmacenParabrisa(Almacen almacenParabrisa) {
        this.almacenParabrisa = almacenParabrisa;
    }

    public Almacen getAlamacenMotor() {
        return alamacenMotor;
    }

    public void setAlamacenMotor(Almacen alamacenMotor) {
        this.alamacenMotor = alamacenMotor;
    }

    public Interfaz getInterfaz() {
        return interfaz;
    }

    public void setInterfaz(Interfaz interfaz) {
        this.interfaz = interfaz;
    }

    public int getApuntadorConsR() {
        return apuntadorConsR;
    }

    public void setApuntadorConsR(int apuntadorConsR) {
        this.apuntadorConsR = apuntadorConsR;
    }

    public int getApuntadorConsP() {
        return apuntadorConsP;
    }

    public void setApuntadorConsP(int apuntadorConsP) {
        this.apuntadorConsP = apuntadorConsP;
    }

    public int getApuntadorConsM() {
        return apuntadorConsM;
    }

    public void setApuntadorConsM(int apuntadorConsM) {
        this.apuntadorConsM = apuntadorConsM;
    }

    public int getCarrosEnsamblados() {
        return carrosEnsamblados;
    }

    public void setCarrosEnsamblados(int carrosEnsamblados) {
        this.carrosEnsamblados = carrosEnsamblados;
    }

    public int getEnsInicial() {
        return ensInicial;
    }

    public void setEnsInicial(int ensInicial) {
        this.ensInicial = ensInicial;
    }

    public int getMaxEns() {
        return maxEns;
    }

    public void setMaxEns(int maxEns) {
        this.maxEns = maxEns;
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

    public Semaphore getSEMR() {
        return SEMR;
    }

    public void setSEMR(Semaphore SEMR) {
        this.SEMR = SEMR;
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

    public Semaphore getSEMP() {
        return SEMP;
    }

    public void setSEMP(Semaphore SEMP) {
        this.SEMP = SEMP;
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

    public Semaphore getSEMM() {
        return SEMM;
    }

    public void setSEMM(Semaphore SEMM) {
        this.SEMM = SEMM;
    }

    public LogicaThread getFabrica() {
        return fabrica;
    }

    public void setFabrica(LogicaThread fabrica) {
        this.fabrica = fabrica;
    }

    public Semaphore getSEMD() {
        return SEMD;
    }

    public void setSEMD(Semaphore SEMD) {
        this.SEMD = SEMD;
    }

    public Semaphore getSEM() {
        return SEM;
    }

    public void setSEM(Semaphore SEM) {
        this.SEM = SEM;
    }
    
    
    
    
    
    
}
