
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
        long tiempo= 1;
        Gestion_Procesos gp = new Gestion_Procesos(tiempo);
        Archivo a = new Archivo("",2,"", "Modo Usuario");
        Archivo b = new Archivo("",3,"", "Modo Usuario");
        Archivo c = new Archivo("",4,"", "Modo Usuario");
        Proceso p1= new Proceso("",a);
        int u;
            for (u=0;u<101;u++){
            gp.getCola_procesos().add_nodo("DISPONIBLE");
        }
        gp.getCola_procesos().getCabeza().setProceso(p1);
        while (true) {
            System.out.println("HOLA");
            this.politica_planificacion("FIFO");
        }
    }

    public Gestion_Procesos(){
        
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
                int i;
                //espacio del disco 100
                for (i=0;i<101;i++){
                    
                    //desde el inicio si existe proceso lo ejecuta sino siguiente al terminar da la vuelta 
                    
                }
            }
            case "C-SCAN"-> {
                int i;
                //espacio del disco 100
                for (i=0;i<101;i++){
                    //desde el inicio si existe proceso lo ejecuta sino siguiente al terminar vuelve al inicio
                }
            }
            case "SSTF"-> {
                //BUSCA IZQ Y DERECHA Y CUA
                
            }
            case "LOOK"-> {
                //Busca hasta que llega a la ultima solicitud y se devuelve
                
            }
            case "C-LOOK"-> {
                //Busca hasta que llega a la ultima solicitud y se devuelve al inicio
                
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
            
            t1.start();
            
            if(this.getCola_procesos().getCabeza().getProceso()==null){
                this.getCola_procesos().setCabeza(this.getCola_procesos().getCabeza().getSiguiente());
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
            //this.agregar_listo(this.getBloq().getCabeza().getProceso());
            this.getCola_procesos().getCabeza().setProceso(null);
            }
            
           

        }
        }
    
    }

