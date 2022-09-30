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
            String simbolo;
            int index;
            Scanner scanner = new Scanner(new File(ARCHIVO_RESERVADAS));
            while (scanner.hasNext()){
                simbolo = scanner.nextLine();
                index = Integer.parseInt(scanner.nextLine());
                palabrasReservadas.put(simbolo, index);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("No se ha podido leer el archivo localizado en: " + ARCHIVO_RESERVADAS);
            e.printStackTrace();
        }
	}
	
	public static String addSimbolo(TokenLexema tok) {
		if (palabrasReservadas.containsKey(tok.getLexema().toString().toLowerCase())) {
			return tok.getLexema().toString().toLowerCase();
		}
		if (tabla.containsKey(tok.getLexema().toString())) {
			return tok.getLexema().toString();
		}
		tabla.put(tok.getLexema().toString(), tok);
		return tok.getLexema().toString();
	}
	
	public TokenLexema getSimbolo(String key) {
		return tabla.get(key.toLowerCase());
	}
	
}
