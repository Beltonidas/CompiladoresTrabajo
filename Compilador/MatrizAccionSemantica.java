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



    //-----------------CONSTRUCTOR------------------------
    public MatrizAccionSemantica() {
        this.cargarMatrizSemantica();
<<<<<<< HEAD
        //this.inicializarAccionesSemanticas();
=======
        this.inicializarAccionesSemanticas();
    }
    
    private boolean correspondeEntregarToken(Integer accion) {
    	switch (accion) {
    	case 6,0,1,-2,-1:
    		return false;
    	default:
    		return true;
    	}
>>>>>>> 6d842864811a39c8e5e9e86f7f919911112e2ecc
    }

    //----------------- GENERAR ESTRUCTURA ------------------------
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

    //----------------- ACCIONES ------------------------
    public int dispararAccionSemantica(int indexFila,int indexCol, Character caracterArchivo){
        int accion = matrizSemantica[indexFila][indexCol];
        int respuesta=0;
        if (accion >= 0){
            respuesta = accionesSemanticas.get(accion).ejecutar(caracterArchivo);
        }
        if (respuesta != 0){
            System.out.println(respuesta);
        }
<<<<<<< HEAD
        return 1;
=======
        if (correspondeEntregarToken(accion)){
        	aLex.entregarToken(AccionSemantica.getToken().getLexema().toString());
        }
        if (respuesta == 1 || respuesta == -1 || accion == -2) {
    		aLex.avanzarLectura();
    	}
>>>>>>> 6d842864811a39c8e5e9e86f7f919911112e2ecc
    }

    public void imprimirMatrizSemantica (){
        for (int i=0; i<FILAS_SEM; i++) {
            for (int j=0; j<COLUMNAS_SEM; j++) {
                System.out.print(matrizSemantica[i][j]+" ");
            }
            System.out.println(" ");
        }
    }
}
