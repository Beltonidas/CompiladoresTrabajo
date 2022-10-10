package Compilador;
import java.util.Scanner;

import Compilador.AccionesSemanticas.AccionSemantica;
import Compilador.AccionesSemanticas.AddCaracterToken;
import Compilador.AccionesSemanticas.ComentarioConsumirCaracter;
import Compilador.AccionesSemanticas.VerificarCadenaCaracteres;
import Compilador.AccionesSemanticas.VerificarComparador;
import Compilador.AccionesSemanticas.VerificarIdentificador;
import Compilador.AccionesSemanticas.VerificarRangoDouble;
import Compilador.AccionesSemanticas.VerificarRangoEntero;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;


public class MatrizAccionSemantica{
    
    private int[][] matrizSemantica;
    private HashMap<Integer,AccionSemantica> accionesSemanticas = new HashMap<Integer,AccionSemantica>();
    private final String MATRIZ_SEM = "./testFiles/TablaSemantica.txt";
    private final int FILAS_SEM = 14;
    private final int COLUMNAS_SEM = 28;

    public MatrizAccionSemantica() {
        this.cargarMatrizSemantica();
        this.inicializarAccionesSemanticas();
    }
    
    private boolean correspondeEntregarToken(Integer accion) {
    	switch (accion) {
    	case 6,1,-2,-1:
    		return false;
    	default:
    		return true;
    	}
    }

    private void inicializarAccionesSemanticas(){
    	this.accionesSemanticas.put(1, new AddCaracterToken());
    	this.accionesSemanticas.put(2, new VerificarIdentificador());
    	this.accionesSemanticas.put(3, new VerificarRangoEntero());
    	this.accionesSemanticas.put(4, new VerificarRangoDouble());
    	this.accionesSemanticas.put(5, new VerificarComparador());
    	this.accionesSemanticas.put(6, new ComentarioConsumirCaracter());
    	this.accionesSemanticas.put(7, new VerificarCadenaCaracteres());
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
            System.out.println(Parser.ANSI_RED+"No se ha podido leer el archivo localizado en: " + MATRIZ_SEM + Parser.ANSI_RESET);
            e.printStackTrace();
        }
    }

    public void dispararAccionSemantica(int indexFila,int indexCol, Character caracterArchivo){
    	
        int accion = matrizSemantica[indexFila][indexCol];
        int respuesta=0;
        //System.out.println("La accion semantica a tomar es: "+accion);
        if (accion >= 0){
            respuesta = accionesSemanticas.get(accion).ejecutar(caracterArchivo);
        }
        if (respuesta < 0){
            AnalizadorLexico.paruser.errorEnXY("El token entregado puede estar incompleto debido al error");
            AnalizadorLexico.entregarToken(TablaSimbolos.addSimbolo(AccionSemantica.getToken()));
            AccionSemantica.getNewToken();
            AnalizadorLexico.avanzarLectura();
            return;
        }
        if (accion==-1) {
            switch (caracterArchivo) {
                case ' ':
                    AnalizadorLexico.paruser.errorEnXY("Caracter espacio no esperado.");
                    break;
                case '\t':
                    AnalizadorLexico.paruser.errorEnXY("Caracter tabulacion no esperada.");
                    break;
                case '\n':
                    AnalizadorLexico.paruser.errorEnXY("Caracter salto de linea no esperado.");
                    break;
                default:
                    AnalizadorLexico.paruser.errorEnXY("Caracter: "+caracterArchivo+" no esperado.");
                    break;
            }
            AnalizadorLexico.avanzarLectura();
            return;
        }
        if (correspondeEntregarToken(accion)){
            AnalizadorLexico.entregarToken(TablaSimbolos.addSimbolo(AccionSemantica.getToken()));
        	AccionSemantica.getNewToken();
        }
        if (respuesta == 1 || respuesta == -1 || accion == -2) {
            AnalizadorLexico.avanzarLectura();
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
