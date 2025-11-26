
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
    Directorio directorio;
    int ciclos;

    public Proceso(String tipo_solicitud, Archivo archivo) {
        this.tipo_solicitud = tipo_solicitud;
        this.estado = "Nuevo";
        this.archivo = archivo;
        this.directorio = null;
        this.ciclos = this.archivo.getCantidad_bloq();
    }
    
    public Proceso(String tipo_solicitud, Directorio directorio) {
        this.tipo_solicitud = tipo_solicitud;
        this.estado = "Nuevo";
        this.archivo = null;
        this.directorio = directorio;
        this.ciclos = 1;
    }
    
    public String printProceso(){
        if (archivo != null){
            return this.tipo_solicitud + ": Archivo " + this.archivo.getNombre();
        } else {
            return this.tipo_solicitud + ": Directorio " + this.directorio.getNombre();
        }
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
