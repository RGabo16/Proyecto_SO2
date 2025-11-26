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

    public Simulacion_Disco(Cola lista_bloques, int tamano) {
        this.lista_bloques = lista_bloques;
        this.espacio_disponible = 100;
        this.tamano = tamano;
    }
    
    public void asignarBloques(Archivo a){
        int i;
        int cantidad = a.getCantidad_bloq();
        if(this.getEspacio_disponible()-a.cantidad_bloq>=0){
            System.out.println("NO HAY BLOQUES DE DISCO DISPONIBLES");
        }else{
            for (i=0;i<cantidad;i++){
            
            this.getLista_bloques().getCabezaB().setArchi(a);
            this.getLista_bloques().getCabezaB().setEstado("Ocupado");
            this.getLista_bloques().setCabezaB(this.getLista_bloques().getCabezaB().getSiguiente());
        }
        }
    }
    public void liberarBloques(int idInicial){
        int u,i;
        for (u=0;u<100;u++){
            if (this.getLista_bloques().getCabezaB().getId()!=idInicial){
                this.getLista_bloques().setCabezaB(this.getLista_bloques().getCabezaB().getSiguiente());
                
            }else{
                break;
            }
            
        }
        int cantidad= this.getLista_bloques().getCabezaB().getArchi().getCantidad_bloq();
        for (i=0;i<cantidad;i++){
            
            this.getLista_bloques().getCabezaB().setArchi(null);
            this.getLista_bloques().getCabezaB().setEstado("Disponible");
            this.getLista_bloques().setCabezaB(this.getLista_bloques().getCabezaB().getSiguiente());
        }
    }
    public Cola getLista_bloques() {
        return lista_bloques;
    }
    public void calcularEspacioLibre(){
        int i, cont=0;
        for (i=0;i<101;i++){
            if(this.getLista_bloques().getCabezaB().getEstado()=="Disponible"){
                cont++;
                this.getLista_bloques().setCabezaB(this.getLista_bloques().getCabezaB().getSiguiente());
            }else{
                this.getLista_bloques().setCabezaB(this.getLista_bloques().getCabezaB().getSiguiente());
            }
        }
        this.setEspacio_disponible(cont);
    }
    public void llenar_bloques(){
        int i;
        for (i=0;i<100;i++){
            this.getLista_bloques().add_bloque();
        }
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
