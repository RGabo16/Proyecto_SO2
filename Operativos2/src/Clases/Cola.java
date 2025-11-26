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
    Bloque cabezaB;
    Bloque colaB;
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
            //this.getCabeza().getSiguiente().setAnterior(null);
            this.setCabeza(this.getCabeza().getSiguiente());
            tamano--;
            if (this.getCabeza() == null) {
                this.setCola(null);
            }
            
        }

    }
    public void add_nodo(Proceso elem) {
        Nodo nodo = new Nodo(elem);
        if (estaVacia()) {
            setCabeza(nodo);
            setCola(nodo);
        } else {
            nodo.setAnterior(this.getCola());
            getCola().setSiguiente(nodo);
            setCola(nodo);
            
        }
        tamano++;
    }
    public void add_bloque(Bloque nodo) {
        
        
        if (estaVacia()) {
            setCabezaB(nodo);
            setColaB(nodo);
        } else {
            getColaB().setSiguiente(nodo);
            setColaB(nodo);
        }
        tamano++;
        nodo.setEstado("Ocupado");
    }
    public void desencolarB() {
        if (estaVacia()) {
            // Manejar error o devolver un valor de indicador
            throw new IllegalStateException("La cola está vacía");
        } else {
            this.getCabezaB().setEstado("Libre");
            this.setCabezaB(this.getCabezaB().getSiguiente());
            tamano--;
            if (this.getCabezaB() == null) {
                this.setColaB(null);
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

    public Bloque getCabezaB() {
        return cabezaB;
    }

    public void setCabezaB(Bloque cabezaB) {
        this.cabezaB = cabezaB;
    }

    public Bloque getColaB() {
        return colaB;
    }

    public void setColaB(Bloque colaB) {
        this.colaB = colaB;
    }
    
}
