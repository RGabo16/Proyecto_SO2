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
    Nodo anterior, siguiente;
    
    public Nodo( ) {
        this.num = num;
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
    
}
