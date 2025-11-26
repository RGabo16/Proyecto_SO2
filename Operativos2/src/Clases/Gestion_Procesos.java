
package Clases;

/**
 *
 * @author Usuario
 */
public class Gestion_Procesos {
    Cola cola_procesos = new Cola("Cola de Solicitudes");
    Cola cola_terminados = new Cola("Cola de Solicitudes Terminadas");
    long tiempo;

    public Gestion_Procesos() {
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
            case "Fifo" -> {
                
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
            }else {
            t1.start();
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
            this.cola_procesos.desencolar();
           

        }

        }
    }

