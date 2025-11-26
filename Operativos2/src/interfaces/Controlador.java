package interfaces;

import Clases.Cola;
import Clases.Directorio;
import Clases.Gestion_Procesos;
import Clases.Nodo;
import Clases.Tree;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultListModel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Luri
 */
public class Controlador extends Thread {

    private View view = new View(this);
    private volatile long segundos = 0L;
    private Gestion_Procesos gp = new Gestion_Procesos();
    private Tree arbol;

    public Controlador() {
        this.start();
        view.setVisible(true);
        view.getPlanPolicy().addActionListener(e -> {
            String seleccion = (String) view.getPlanPolicy().getSelectedItem();
            System.out.println("Política seleccionada: " + seleccion);

            // Aquí puedes actualizar la lógica de planificación, reiniciar métricas, etc.
            gp.politica_planificacion(seleccion);
        });
    }

    //Crear modelo para JList
    public DefaultListModel createModel(Cola cola) {
        DefaultListModel model = new DefaultListModel();
        Nodo actual = cola.getCabeza();
        while (actual != null) {
            model.addElement(actual.getProceso().printProceso());
            actual = actual.getSiguiente();
        }
        return model;
    }
    
    //Crear modelo para JTree
    public DefaultMutableTreeNode convertirDirectorio(Directorio dir){
        DefaultMutableTreeNode nodoDir = new DefaultMutableTreeNode(dir.getNombre());
        Cola listaArchivos = dir.getLista_archivos();
        Nodo actual  = listaArchivos.getCabeza();
        //Agregar archivos
        while (actual != null){
            nodoDir.add(new DefaultMutableTreeNode(actual.getArchivo().getNombre()));        
        }
        
        Cola listaSubdirectorios = dir.getLista_subdirectorios();
        Directorio actualSub = listaSubdirectorios.getCabeza().getDirectorio();
        //Agregar Subdirectorios
        while (actualSub != null){
            nodoDir.add(convertirDirectorio(actualSub));
        }
        
        return nodoDir;
    }

    @Override
    public void run() {

        //Reloj/Cronometro
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long inicio = System.currentTimeMillis(); // Marca de tiempo inicial
                while (true) {
                    try {
                        Thread.sleep(500);
                        long ahora = System.currentTimeMillis();
                        long transcurrido = ahora - inicio;

                        // Convertimos milisegundos a horas, minutos y segundos
                        segundos = (transcurrido / 1000);
                        long horas = (segundos / 3600);
                        long minutos = ((segundos % 3600) / 60);
                        long seg = (segundos % 60);

                        // Formateamos como HH:mm:ss
                        String tiempo = String.format("%02d:%02d:%02d", horas, minutos, seg);
                        getView().getClockLabel().setText(tiempo);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        while (true) {

            //Seleccion de la politica de planificacion
            gp.politica_planificacion((String) view.getPlanPolicy().getSelectedItem());

            //Cola de solicitudes
            if (createModel(gp.getCola_procesos()) != view.getColaSolicitudes().getModel()) {
                DefaultListModel modelSolicitudes = createModel(gp.getCola_procesos());
                view.getColaSolicitudes().setModel(modelSolicitudes);
            }

            //Cola de solicitudes terminadas
            if (createModel(gp.getCola_terminados()) != view.getColaSolicitudesTerminadas().getModel()) {
                DefaultListModel modelSolicitudesTerminadas = createModel(gp.getCola_terminados());
                view.getColaSolicitudesTerminadas().setModel(modelSolicitudesTerminadas);
            }
        }
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public long getSegundos() {
        return segundos;
    }

    public void setSegundos(long segundos) {
        this.segundos = segundos;
    }

    public Gestion_Procesos getGp() {
        return gp;
    }

    public void setGp(Gestion_Procesos gp) {
        this.gp = gp;
    }

    public Tree getArbol() {
        return arbol;
    }

    public void setArbol(Tree arbol) {
        this.arbol = arbol;
    }
    
    
}

