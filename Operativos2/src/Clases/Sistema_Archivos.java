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
    Cola cola_procesos;

    public Sistema_Archivos() {
        this.raiz = new Directorio("Raiz");
        this.cola_procesos = new Cola("Sistema de Archivos");
    }
    public void crear_archivo(Archivo a){
        if(this.getEspacio_disponible()>a.getCantidad_bloq()){
            this.setEspacio_disponible(espacio_disponible- a.getCantidad_bloq());
            int i;
            /*for(i=0; i<a.getCantidad_bloq();i++){
                this.getLista_bloques().add_bloque(a.getBloq_inicial());
                AQUI CAMBIAN LOS COLORES DEL DISCO
            }*/
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
        
    }
    
    public void eliminar_directorio(Directorio dir){
        
    }

    public Directorio getRaiz() {
        return raiz;
    }

    public void setRaiz(Directorio raiz) {
        this.raiz = raiz;
    }

    public Cola getCola_procesos() {
        return cola_procesos;
    }

    public void setCola_procesos(Cola cola_procesos) {
        this.cola_procesos = cola_procesos;
    }
    
    
}
