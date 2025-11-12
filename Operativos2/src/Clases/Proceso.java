/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Usuario
 */
public class Proceso {
    int id;
    String estado;//listo, nuevo, bloqueado,terminado, ejecutando
    String tipo_solicitud;//CRUD
    Archivo archivo;
    int ciclos;

    public Proceso(String tipo_solicitud, Archivo archivo) {
        this.tipo_solicitud = tipo_solicitud;
        this.estado = "Nuevo";
        this.archivo = archivo;
        this.ciclos=this.archivo.getCantidad_bloq();
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

    public String getTipo_solicitud() {
        return tipo_solicitud;
    }

    public void setTipo_solicitud(String tipo_solicitud) {
        this.tipo_solicitud = tipo_solicitud;
    }

    public Archivo getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

    public int getCiclos() {
        return ciclos;
    }

    public void setCiclos(int ciclos) {
        this.ciclos = ciclos;
    }
    
    
}
