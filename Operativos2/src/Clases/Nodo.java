/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Usuario
 */
public class Nodo {
    int num;
    Object elemento;
    Proceso p1;
    Nodo anterior, siguiente;
    
    public Nodo(Object elem ) {
        this.num = num;
        this.elemento=elem;
        this.p1=null;
        this.anterior = null;
        this.siguiente = null;
    }

    public Nodo(Proceso elemento) {
        this.num = num;
        this.p1= elemento;
        this.elemento = null;
        this.anterior = null;
        this.siguiente = null;
    }
    
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Nodo getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public Object getElemento() {
        return elemento;
    }

    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }

    public Proceso getProceso() {
        return p1;
    }

    public void setProceso(Proceso p1) {
        this.p1 = p1;
    }
    
}
