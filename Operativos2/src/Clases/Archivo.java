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
    
    String creador;
    String privacidad;

    public Archivo(String nombre, int cantidad_bloq, String priv) {
        this.nombre = nombre;
        this.cantidad_bloq = cantidad_bloq;
        this.bloq_inicial = bloq_inicial;
        this.creador = creador;
        this.privacidad = priv;
    }

    

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

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getPrivacidad() {
        return privacidad;
    }

    public void setPrivacidad(String privacidad) {
        this.privacidad = privacidad;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }
    
}
