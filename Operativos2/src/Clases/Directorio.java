
package Clases;

/**
 *
 * @author Usuario
 */
public class Directorio {
    String nombre;
    Cola lista_archivos = new Cola("Archivos");
    Cola lista_subdirectorios = new Cola("Subdirectorios");
    private String creador;

    public Directorio(String nombre, String creador) {
        this.nombre = nombre;
        this.creador = creador;
    }
    
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Cola getLista_archivos() {
        return lista_archivos;
    }

    public void setLista_archivos(Cola lista_archivos) {
        this.lista_archivos = lista_archivos;
    }

    public Cola getLista_subdirectorios() {
        return lista_subdirectorios;
    }

    public void setLista_subdirectorios(Cola lista_subdirectorios) {
        this.lista_subdirectorios = lista_subdirectorios;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }
    
}
