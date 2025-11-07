/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Usuario
 */
public class Simulacion_Disco {
    Cola lista_bloques;
    int espacio_disponible;
    int tamano;
    
    
    public void agregar_archivo(Archivo a){
        if(this.getEspacio_disponible()>a.getCantidad_bloq()){
            this.setEspacio_disponible(espacio_disponible- a.getCantidad_bloq());
            int i;
            for(i=0; i<a.getCantidad_bloq();i++){
                this.getLista_bloques().add_bloque(a.getBloq_inicial());
                
            }
        }
    }
    public void quitar_archivo(Archivo a){
        
            this.setEspacio_disponible(espacio_disponible+ a.getCantidad_bloq());
            int i;
            for(i=0; i<a.getCantidad_bloq();i++){
                this.getLista_bloques().desencolarB();
                //desencolar
            }
        
    }

    public Cola getLista_bloques() {
        return lista_bloques;
    }

    public void setLista_bloques(Cola lista_bloques) {
        this.lista_bloques = lista_bloques;
    }

   

    public int getEspacio_disponible() {
        return espacio_disponible;
    }

    public void setEspacio_disponible(int espacio_disponible) {
        this.espacio_disponible = espacio_disponible;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }
    
}
