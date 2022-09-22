package Compilador;

import java.util.List;

public class MatrizAccionSemantica{
    
    private int[][] matriz;
    private Character ultimoCaracter;
    private MatrizTransicion matrizTransicion;

    public MatrizAccionSemantica() {

    }
    public Character getUltimoCaracter(){
        return ultimoCaracter;
    }

    public void setMatizTransicion(MatrizTransicion matrizTransicion){
        this.matrizTransicion = matrizTransicion;
    }

    public int dispararAccionSemantica(int fila, int columna, List<Character> lista){
        if (fila < 0){ //Estado final o puede ser un error
            matrizTransicion.setIndexFila(0);
            matrizTransicion.setIndexColumna(0);
            System.out.println("el token es: " + lista);
            if(lista.size()==1){
                lista.clear();
                return 1;
            }
            lista.clear();
            return 0;
        }
        //System.out.println(lista);
        return 1;
    }

    //AS1 Crear cadena agregar caracter a la cadena
    //AS2 Agregar caracter a la cadena
}
