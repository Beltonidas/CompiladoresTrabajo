package Compilador;
import java.util.Scanner;

import Compilador.AccionesSemanticas.AccionSemantica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class MatrizAccionSemantica{
    
    private int[][] matrizSemantica;
    private HashMap<Integer,AccionSemantica> accionesSemanticas = new HashMap<Integer,AccionSemantica>();
    private Boolean modoComentario = false;
    private final String MATRIZ_SEM = "./testFiles/TablaSemantica.txt";
    private final int FILAS_SEM = 14;
    private final int COLUMNAS_SEM = 28;

    public MatrizAccionSemantica() {
        this.cargarMatrizSemantica();
        this.inicializarAccionesSemanticas();
    }

    private void inicializarAccionesSemanticas(){
    	this.accionesSemanticas.put(0, null);
    	this.accionesSemanticas.put(0, null);
    	this.accionesSemanticas.put(0, null);
    	this.accionesSemanticas.put(0, null);
    	this.accionesSemanticas.put(0, null);
    	this.accionesSemanticas.put(0, null);
    }

    private void cargarMatrizSemantica(){
        this.matrizSemantica = new int[FILAS_SEM][COLUMNAS_SEM];
        try {
            Scanner s = new Scanner(new File(MATRIZ_SEM));
            for (int i=0; i<FILAS_SEM; i++) {
                    for (int j=0; j<COLUMNAS_SEM; j++) {
                        this.matrizSemantica[i][j] = Integer.parseInt(s.nextLine());
                    }
                }
                s.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se ha podido leer el archivo localizado en: " + MATRIZ_SEM);
            e.printStackTrace();
        }
    }

    public int dispararAccionSemantica(int indexFila,int indexCol, Character caracterArchivo){
        int accion = matrizSemantica[indexFila][indexCol];
        int respuesta=0;
        if (accion >= 0){
            respuesta = accionesSemanticas.get(accion).ejecutar(caracterArchivo);
        }
        if (respuesta != 0){
            System.out.println(respuesta);
        }
        return 1;
    }
}
