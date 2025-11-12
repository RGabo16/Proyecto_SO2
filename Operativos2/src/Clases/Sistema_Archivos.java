/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Usuario
 */
public class Sistema_Archivos {
    Directorio raiz;
    Cola cola_procesos;

    public Sistema_Archivos() {
        this.raiz = new Directorio("Raiz");
        this.cola_procesos = new Cola("Sistema de Archivos");
    }
    

    public Directorio getRaiz() {
        return raiz;
    }

    public void setRaiz(Directorio raiz) {
        this.raiz = raiz;
    }

    public Cola getCola_procesos() {
        return cola_procesos;
    }

    public void setCola_procesos(Cola cola_procesos) {
        this.cola_procesos = cola_procesos;
    }
    
    
}
