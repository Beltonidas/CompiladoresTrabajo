package Compilador;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import Compilador.AccionesSemanticas.AccionSemantica; 

public class MatrizTransicion {

    /*
    * En el archivo de la matriz de transicion utilizamos la siguiente convencion:
    * Estado final, F = -1
    * Estado error, e = -2
    */
    private final String MATRIZ_TRANS = "./testFiles/TablaTransicion.txt";
    private final int FILAS_TRANS = 14;
    private final int COLUMNAS_TRANS = 28;
    private static final String ARCHIVO_SIMBOLO = "./testFiles/TablaDistintosSimbolos.txt";

    //Posicion actual en la tabla
    public int indexFila;
    public int indexCol;

    //Matriz de transicion de estados
    private int matrizEstado [][];

    private MatrizAccionSemantica matrizAccionSemantica;

    //Indice de simbolos
    private HashMap<String, Integer> indexSimbolo;

    //Caracteres acumulados
    public List<Character> listaCaracteresAcumulados = new ArrayList<>();

    public MatrizTransicion(AnalizadorLexico al) {
        this.cargarMatrizTrans();
        this.cargarSimbolos();
        this.indexCol = 0;
        this.indexFila = 0;
        this.matrizAccionSemantica = new MatrizAccionSemantica(al);
    }

    public void cargarMatrizTrans() {
        this.matrizEstado = new int[FILAS_TRANS][COLUMNAS_TRANS];
        try {
            Scanner s = new Scanner(new File(MATRIZ_TRANS));
            
            for (int i=0; i<FILAS_TRANS; i++) {
                    for (int j=0; j<COLUMNAS_TRANS; j++) {
                        this.matrizEstado[i][j] = Integer.parseInt(s.nextLine());
                    }
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.err.println("No se ha podido leer el archivo localizado en: " + MATRIZ_TRANS);
            e.printStackTrace();
        }
    }
    
    public void cargarSimbolos(){
        indexSimbolo = new HashMap<String, Integer>();
        try {
            String simbolo;
            int index;
            Scanner scanner = new Scanner(new File(ARCHIVO_SIMBOLO));
            while (scanner.hasNext()){
                simbolo = scanner.nextLine();
                index = Integer.parseInt(scanner.nextLine());
                indexSimbolo.put(simbolo, index);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("No se ha podido leer el archivo localizado en: " + ARCHIVO_SIMBOLO);
            e.printStackTrace();
        }
    }
    
    public HashMap<String, Integer> getSimbolos(){
        return indexSimbolo;
    }
    
    public void setIndexFila(int value){
        indexFila = value;
    }

    public void setIndexColumna(int value){
        indexCol = value;
    }
//--------- METODOS --------------------

    public String transicionCaracter(char caracterArchivo, Boolean unico){
    	String codigoToken="";
    	int estadoAnterior = indexFila;
        indexCol = identificarCaracter(caracterArchivo);
        indexFila = matrizEstado[indexFila][indexCol];
        System.out.println("El tipo de caracter recibido nos hace pasar al estado: "+indexFila);
        matrizAccionSemantica.dispararAccionSemantica(estadoAnterior, indexCol, caracterArchivo);
        if (indexFila<0) {
        	indexCol=0;
        	indexFila=0;
        	AccionSemantica.getToken().resetLexema();
        }
        return  codigoToken;
    }

    public int identificarCaracter(Character character){
        //Si no es ninguno de los anteriores entonces, buscar en la tabla
        String aux = String.valueOf(character);
        if (indexSimbolo.containsKey(aux))
            return indexSimbolo.get(aux);
        //Numero
        if (aux.matches("[+-]?\\d*(\\.\\d+)?"))
            return 5;
        //Cadena vacia blanco
        if (aux.isEmpty())
            return 0;
        //Mayuscula
        if (aux.equals(aux.toLowerCase()))
            return 3;
        //Minuscula
        if (aux.equals(aux.toUpperCase()))
            return 4;
        return -1;
    }
}
