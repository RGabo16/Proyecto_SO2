/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Usuario
 */
public class Bloque {
    String instruccion;
    int id;
    String estado ;//libre u ocupado
    Archivo archi;//el archivo que esta usando este bloque en disco
    Bloque siguiente,anterior;

    public Bloque() {
        this.archi = null;
        this.siguiente = null;
        this.anterior=null;
    }
    
        
    
    
    public Bloque getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Bloque siguiente) {
        this.siguiente = siguiente;
    }

    public Bloque getAnterior() {
        return anterior;
    }

    public void setAnterior(Bloque anterior) {
        this.anterior = anterior;
    }

    
    public String getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(String instruccion) {
        this.instruccion = instruccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Archivo getArchi() {
        return archi;
    }

    public void setArchi(Archivo archi) {
        this.archi = archi;
    }
    
}
