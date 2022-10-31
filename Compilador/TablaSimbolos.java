package Compilador;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;



public class TablaSimbolos {
	
	private static HashMap<String, TokenLexema> tabla = new HashMap<String,TokenLexema>();
	private static HashMap<String,Integer> palabrasReservadas;
	private static String ARCHIVO_RESERVADAS="./testFiles/palabras_reservadas.txt";
	public TablaSimbolos() {
	}
	
	public static void inic() {
	    palabrasReservadas = new HashMap<>();
        try {
            StringBuilder simbolo;
            int index=0;
            Scanner scanner = new Scanner(new File(ARCHIVO_RESERVADAS));
            while (scanner.hasNext()){
                simbolo = new StringBuilder(scanner.nextLine());
                int simEspacio=simbolo.indexOf(" ")+1;
                index=Integer.parseInt(simbolo.substring(simEspacio,simbolo.length()));
                //System.out.println("Palabra reservada: "+simbolo.substring(0,simEspacio-1)+" | Codigo: "+index);
                palabrasReservadas.put(simbolo.substring(0,simEspacio-1), index);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("No se ha podido leer el archivo localizado en: " + ARCHIVO_RESERVADAS);
            e.printStackTrace();          
        }
	}
	
	public static int addSimbolo(TokenLexema tok) {
	    //System.out.println(tok.toString());
		if (palabrasReservadas.containsKey(tok.getLexema().toString())){
		    //System.out.println("Token Previo Palabra Reservada");
		    tok.setId(palabrasReservadas.get(tok.getLexema().toString()));
		    return tok.getId();
		}
		if (tabla.containsKey(tok.getLexema().toString())) {
		    //System.out.println("Token Previo Existente");
		    return tok.getId();
		}
        //System.out.println("Token Previo Nuevo");
		tabla.put(tok.getLexema().toString(), tok);
		return tok.getId();
	}
	
	public static TokenLexema getSimbolo(String key) {
		return tabla.get(key);
	}
	
	public static void imprimirTabla() {
	    System.out.println(Parser.ANSI_CYAN+"Imprimiendo tabla de simbolos resultante..."+Parser.ANSI_RESET);
	    tabla.forEach((key, value)-> System.out.println(Parser.ANSI_CYAN+value+Parser.ANSI_RESET));
	}
}
