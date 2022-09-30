package Compilador;
import java.util.Scanner;

import Compilador.AccionesSemanticas.AccionSemantica;
import Compilador.AccionesSemanticas.AddCaracterToken;
import Compilador.AccionesSemanticas.ComienzoComentario;
import Compilador.AccionesSemanticas.FinComentario;
import Compilador.AccionesSemanticas.GenerarNuevoToken;
import Compilador.AccionesSemanticas.VerificarCadenaCaracteres;
import Compilador.AccionesSemanticas.VerificarComparador;
import Compilador.AccionesSemanticas.VerificarIdentificador;
import Compilador.AccionesSemanticas.VerificarRangoDouble;
import Compilador.AccionesSemanticas.VerificarRangoEntero;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class MatrizAccionSemantica{
    
	private AnalizadorLexico aLex;
    private int[][] matrizSemantica;
    private HashMap<Integer,AccionSemantica> accionesSemanticas = new HashMap<Integer,AccionSemantica>();
    private final String MATRIZ_SEM = "./testFiles/TablaSemantica.txt";
    private final int FILAS_SEM = 14;
    private final int COLUMNAS_SEM = 28;

    public MatrizAccionSemantica(AnalizadorLexico al) {
    	this.aLex=al;
        this.cargarMatrizSemantica();
        this.inicializarAccionesSemanticas();
    }
    
    private boolean correspondeEntregarToken(Integer accion) {
    	switch (accion) {
    	case 6,0,1:
    		return false;
    	default:
    		return true;
    	}
    }

    private void inicializarAccionesSemanticas(){
    	this.accionesSemanticas.put(0, new GenerarNuevoToken());
    	this.accionesSemanticas.put(1, new AddCaracterToken());
    	this.accionesSemanticas.put(2, new VerificarIdentificador());
    	this.accionesSemanticas.put(3, new VerificarRangoEntero());
    	this.accionesSemanticas.put(4, new VerificarRangoDouble());
    	this.accionesSemanticas.put(5, new VerificarComparador());
    	this.accionesSemanticas.put(6, new ComienzoComentario());
    	this.accionesSemanticas.put(7, new FinComentario());
    	this.accionesSemanticas.put(8, new VerificarCadenaCaracteres());
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

    public void dispararAccionSemantica(int indexFila,int indexCol, Character caracterArchivo){
    	
        int accion = matrizSemantica[indexFila][indexCol];
        int respuesta=0;
        System.out.println("La accion semantica a tomar es: "+accion);
        if (accion >= 0){
            respuesta = accionesSemanticas.get(accion).ejecutar(caracterArchivo);
        }
        if (respuesta == -1){
            System.err.println(AccionSemantica.getToken().getLexema());
        }
        if (correspondeEntregarToken(accion)){
        	aLex.entregarToken(AccionSemantica.getToken().getLexema().toString());
        }
        if (respuesta == 1 || respuesta == -1) {
    		aLex.avanzarLectura();
    	}
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
