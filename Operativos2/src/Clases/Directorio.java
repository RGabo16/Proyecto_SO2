
package Clases;

/**
 *
 * @author Usuario
 */
public class Directorio {
    String nombre;
    Cola lista_archivos;

    public Directorio(String nombre) {
        this.nombre = nombre;
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
    
}
