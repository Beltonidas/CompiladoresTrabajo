package Compilador;

import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
	    //yylex()
	    System.out.println("Ruta del archivo que desea ejecutar: ");
        Scanner consola = new Scanner(System.in);
        String ruta = consola.nextLine();
        consola.close();
		AnalizadorLexico aLex = new AnalizadorLexico(ruta);
		int rta=0;
		while (rta!=-1) {
		    rta=aLex.siguienteToken();
		    System.out.println("Siguiente token: "+rta+", valor: "+aLex.anteriorToken);
		}
		
		//aLex.ejecutar(ruta);
		//aLex.getTablasSimbolos();
	}

}