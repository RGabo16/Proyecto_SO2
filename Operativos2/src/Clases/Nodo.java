
package Clases;

/**
 *
 * @author Usuario
 */
public class Nodo {
    int num;
    Object elemento;
    Proceso p1;
    Archivo archivo;
    Directorio directorio;
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
    
    public Nodo(Archivo archivo) {
        this.archivo = archivo;
    }
    
    public Nodo(Directorio directorio) {
        this.directorio = directorio;
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

    public Archivo getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

    public Directorio getDirectorio() {
        return directorio;
    }

    public void setDirectorio(Directorio directorio) {
        this.directorio = directorio;
    }
    
    
    
}
