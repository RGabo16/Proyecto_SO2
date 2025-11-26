
package Clases;

/**
 *
 * @author Usuario
 */

public class Gestion_Procesos implements Runnable {
    Cola cola_procesos= new Cola("Cola de Procesos");
    Cola cola_terminados = new Cola("Cola de Solicitudes Terminadas");

    
    long tiempo;
 @Override
    public void run() {
        
        
        
        while (true) {
            
            this.politica_planificacion("LOOK");
        }
    }

    public Gestion_Procesos(long tiempo) {
        this.tiempo = tiempo;
    }
    
    public Cola getCola_procesos() {
        return cola_procesos;
    }

    public void setCola_procesos(Cola cola_procesos) {
        this.cola_procesos = cola_procesos;
    }

    public Cola getCola_terminados() {
        return cola_terminados;
    }

    public void setCola_terminados(Cola cola_terminados) {
        this.cola_terminados = cola_terminados;
    }
    
    
    public void agregar_proceso(Proceso p1){
        
        this.cola_procesos.add_nodo(p1);
    }
    public void politica_planificacion(String s){
        
        switch(s){
            
            case "FIFO" -> {
                this.manejo_procesos();
            }
            case "SCAN"-> {
                
                //espacio del disco 100
                this.manejo_procesos();
                this.manejo_procesos_inverso();
                    
                    //desde el inicio si existe proceso lo ejecuta sino siguiente al terminar da la vuelta 
                    
                
            }
            case "C-SCAN"-> {
                
                //espacio del disco 100
                this.manejo_procesos();
                   
            }
            case "LIFO"-> {
                //
                this.manejo_procesos_inverso();
            }
            case "C-LOOK"-> {
                //Busca hasta que llega a la ultima solicitud y se devuelve
                this.manejo_procesos();
                
                
                
//                Archivo a = new Archivo("",5,"");
//                Archivo b = new Archivo("",3,"");
//                Archivo c = new Archivo("",4,"");
//                Proceso p1= new Proceso("",b);
//                Proceso p2= new Proceso("",c);
//                Proceso p3= new Proceso("",a);
//                this.agregar_proceso(p1);
//                this.agregar_proceso(p2);
//                this.agregar_proceso(p3);
                this.manejo_procesos_inverso();
            }
            case "PA"-> {
                //Busca hasta que llega a la ultima solicitud y se devuelve al inicio
                this.manejo_procesos();
            }
        }
    }
    public void manejo_procesos(){
        Thread t1 =new Thread();
        if (this.getCola_procesos().getTamano() == 0) {

            System.out.println("no hay procesos");
            try {
                t1.sleep(tiempo * 1000);
            } catch (InterruptedException ex) {
                System.getLogger(Gestion_Procesos.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            }else{     
   
                System.out.println("Procesando solicitud "+ this.getCola_procesos().getCabeza().getProceso().getTipo_solicitud());
               int i;
            int v = this.getCola_procesos().getCabeza().getProceso().getArchivo().getCantidad_bloq();
            for (i = 0; i < v; i++) {
                this.getCola_procesos().getCabeza().getProceso().setCiclos(this.getCola_procesos().getCabeza().getProceso().getCiclos() - 1);
                
                try {
                    //aqui el hilo espera el tiempo del ciclo
                    t1.sleep(tiempo * 1000);
                } catch (InterruptedException ex) {
                    System.getLogger(Gestion_Procesos.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
                System.out.println("Procesando " + this.getCola_procesos().getCabeza().getProceso().getCiclos());
            }
            
            this.getCola_terminados().add_nodo(this.getCola_procesos().getCabeza().getProceso());
            this.getCola_procesos().desencolar();
            
            //this.agregar_listo(this.getBloq().getCabeza().getProceso());
           /* this.getCola_procesos().getCabeza().setProceso(null);
            this.getCola_procesos().setCabeza(this.getCola_procesos().getCabeza().getSiguiente());*/
            }
            
           

        
        }
    
            
           

        
        
    public void manejo_procesos_inverso(){
        Thread t1 =new Thread();
        if (this.getCola_procesos().getTamano() == 0) {

            System.out.println("no hay procesos");
            try {
                t1.sleep(tiempo * 1000);
            } catch (InterruptedException ex) {
                System.getLogger(Gestion_Procesos.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            }else{
            
            t1.start();
            
            if(this.getCola_procesos().getCola()==null){
                this.getCola_procesos().setCola(this.getCola_procesos().getCola().getAnterior());
            }else{
                System.out.println("Procesando solicitud "+ this.getCola_procesos().getCola().getProceso().getTipo_solicitud());
               int i;
            int v = this.getCola_procesos().getCola().getProceso().getArchivo().getCantidad_bloq();
            for (i = 0; i < v; i++) {
                this.getCola_procesos().getCola().getProceso().setCiclos(this.getCola_procesos().getCola().getProceso().getCiclos() - 1);
                
                try {
                    //aqui el hilo espera el tiempo del ciclo
                    t1.sleep(tiempo * 1000);
                } catch (InterruptedException ex) {
                    System.getLogger(Gestion_Procesos.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
                System.out.println("Procesando " + this.getCola_procesos().getCola().getProceso().getCiclos());
            }
            //this.agregar_listo(this.getBloq().getCabeza().getProceso());
            this.getCola_terminados().add_nodo(this.getCola_procesos().getCola().getProceso());
            this.getCola_procesos().setCola(this.getCola_procesos().getCola().getAnterior());
            this.getCola_procesos().desencolar();
            }
            
           

        }
        }
    }

