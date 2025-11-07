package Clases;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
public class Archivo {
    
    String nombre;
    int cantidad_bloq;
    Bloque bloq_inicial;
    Cola lista_bloq;
    String creador;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad_bloq() {
        return cantidad_bloq;
    }

    public void setCantidad_bloq(int cantidad_bloq) {
        this.cantidad_bloq = cantidad_bloq;
    }

    public Bloque getBloq_inicial() {
        return bloq_inicial;
    }

    public void setBloq_inicial(Bloque bloq_inicial) {
        this.bloq_inicial = bloq_inicial;
    }

    public Cola getLista_bloq() {
        return lista_bloq;
    }

    public void setLista_bloq(Cola lista_bloq) {
        this.lista_bloq = lista_bloq;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }
    
}
