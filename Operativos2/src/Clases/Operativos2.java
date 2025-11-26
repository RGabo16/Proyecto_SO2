/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Clases;

import interfaces.Controlador;
import interfaces.View;

/**
 *
 * @author Usuario
 */
public class Operativos2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       
        
        Controlador controlador = new Controlador();
//        View v = new View();
//        v.setVisible(true);      
       long tiempo= 1;
       Gestion_Procesos gp = new Gestion_Procesos(tiempo);
       Simulacion_Disco sm = new Simulacion_Disco();
       
      /*  int u;
            for (u=0;u<100;u++){
            gp.getCola_procesos().add_nodo(p1);
        }
       gp.getCola_procesos().getCabeza().setProceso(p1);*/
       Thread GP = new Thread(gp);
       GP.start();
       Thread SM = new Thread(sm);
       SM.start();
       
        
    }
    
}
