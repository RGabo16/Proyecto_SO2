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
       
        
        Archivo a = new Archivo("",2,"", "Modo Usuario");
        Archivo b = new Archivo("",3,"", "Modo Usuario");
        Archivo c = new Archivo("",4,"", "Modo Usuario");
        Proceso p1= new Proceso("",a);
        Controlador controlador = new Controlador();
//        View v = new View();
//        v.setVisible(true);      
        
    }
    
}
