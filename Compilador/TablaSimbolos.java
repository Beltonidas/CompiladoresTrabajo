package Compilador;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class TablaSimbolos {
	
	private static HashMap<String, TokenLexema> tabla = new HashMap<String,TokenLexema>();
	private static HashMap<String,Integer> palabrasReservadas;
	private String ARCHIVO_RESERVADAS="./testFiles/palabras_reservadas.txt";
    
	public TablaSimbolos() {
		palabrasReservadas = new HashMap<>();
		try {
            StringBuilder simbolo;
            int index=0;
            Scanner scanner = new Scanner(new File(ARCHIVO_RESERVADAS));
            while (scanner.hasNext()){
                simbolo = new StringBuilder(scanner.nextLine());
                int simEspacio=simbolo.indexOf(" ")+1;
                index=Integer.parseInt(simbolo.substring(simEspacio,simbolo.length()));
                System.out.println("Palabra reservada: "+simbolo.substring(0,simEspacio-1)+" | Codigo: "+index);
                palabrasReservadas.put(simbolo.substring(0,simEspacio-1), index);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("No se ha podido leer el archivo localizado en: " + ARCHIVO_RESERVADAS);
            e.printStackTrace();          
        }
	}
	
	public static String addSimbolo(TokenLexema tok) {
	    System.out.println(tok.toString());
		if (palabrasReservadas.containsKey(tok.getLexema().toString())){
		    System.out.println("Token Previo Palabra Reservada");
		    return tok.getLexema().toString();
		}
		if (tabla.containsKey(tok.getLexema().toString())) {
		    System.out.println("Token Previo Existente");
			return tok.getLexema().toString();
		}
        System.out.println("Token Previo Nuevo: "+tok.getLexema().toString());
		tabla.put(tok.getLexema().toString(), tok);
		return tok.getLexema().toString();
	}
	
	public static TokenLexema getSimbolo(String key) {
		return tabla.get(key);
	}
	
	public static void imprimirTabla() {
	    System.out.println("////////////////////////////");
	    /*var setOfKeys=tabla.keySet();
	    for (String key : setOfKeys) {
	        System.out.println(tabla.get(key).toString()+" || "+key);
	    }*/
	    tabla.forEach((key, value)-> System.out.println(key + " =>>> " + value));
	}
}
