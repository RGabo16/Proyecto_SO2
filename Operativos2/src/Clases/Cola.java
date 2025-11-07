/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Usuario
 */
public class Cola {
       //Atributos 
    String nombre;
    Nodo cabeza;
    Nodo cola;
    int tamano;

    /*listo, new, bloqueado, suspendido listo,suspendido bloqueado,largo,corto,mediano plazo,terminados*/
    //Metodos
//cola de bloqueados ejecuta sus bloqueados
    public Cola(String nombre) {
        this.nombre = nombre;
        this.cabeza = null;
        this.cola = null;
        this.tamano = 0;
    }

    public void addAtTheStart(Nodo newNodo) {
        if (estaVacia()) {
            setCabeza(newNodo);
            setCola(newNodo);
        } else {
            newNodo.setSiguiente(getCabeza());
            getCabeza().setAnterior(newNodo);
            setCabeza(newNodo);
        }
        tamano++;
    }
    
    public boolean estaVacia() {
        return cabeza == null;
    }
    
    public void desencolar() {
        if (estaVacia()) {
            // Manejar error o devolver un valor de indicador
            throw new IllegalStateException("La cola está vacía");
        } else {
            
            this.setCabeza(this.getCabeza().getSiguiente());
            tamano--;
            if (this.getCabeza() == null) {
                this.setCola(null);
            }
            
        }

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Nodo getCabeza() {
        return cabeza;
    }

    public void setCabeza(Nodo cabeza) {
        this.cabeza = cabeza;
    }

    public Nodo getCola() {
        return cola;
    }

    public void setCola(Nodo cola) {
        this.cola = cola;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }
    
}
