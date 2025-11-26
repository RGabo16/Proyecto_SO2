/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Usuario
 */
public class Sistema_Archivos {
    Directorio raiz;
    Cola cola_archivos;
    int espacio_disponible =100;

    public Sistema_Archivos() {
        this.raiz = new Directorio("Raiz");
        this.cola_archivos = new Cola("Sistema de Archivos");
    }
    public void agregar_archivo(Archivo a, Directorio dir){
        if(this.getEspacio_disponible()>a.getCantidad_bloq()){
            this.setEspacio_disponible(espacio_disponible- a.getCantidad_bloq());
            int i;
            for (i=0;i<this.getCola_archivos().getTamano();i++){
                if(this.getCola_archivos().getCabeza().getElemento().getClass()=dir){
                    this.getCola_archivos().getCabeza().getElemento().//al directorio se le agrega archivo
                            
                }
            }
        }
    }
    public void eliminar_archivo(Archivo a){
        
            this.setEspacio_disponible(espacio_disponible+ a.getCantidad_bloq());
            int i;
            //SE QUITAN LOS COLORES DEL ARCHIVO EN DISCO
           /* for(i=0; i<a.getCantidad_bloq();i++){
                this.getLista_bloques().desencolarB();
                //desencolar
            }*/
        
    }
    public void crear_directorio(String name){
        this.getCola_archivos().getCabeza().
        
    }
    
    public void eliminar_directorio(Directorio dir){
        
    }

    public Directorio getRaiz() {
        return raiz;
    }

    public void setRaiz(Directorio raiz) {
        this.raiz = raiz;
    }

    public Cola getCola_archivos() {
        return cola_archivos;
    }

    public void setCola_archivos(Cola cola_archivos) {
        this.cola_archivos = cola_archivos;
    }

    public int getEspacio_disponible() {
        return espacio_disponible;
    }

    public void setEspacio_disponible(int espacio_disponible) {
        this.espacio_disponible = espacio_disponible;
    }

    
    
}
