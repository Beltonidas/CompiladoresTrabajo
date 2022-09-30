package Compilador;

import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		AnalizadorLexico aLex = new AnalizadorLexico();
		System.out.println("Ruta del archivo que desea ejecutar: ");
		Scanner consola = new Scanner(System.in);
        String ruta = consola.nextLine();
        consola.close();
        System.out.println(ruta);
		aLex.ejecutar(ruta);
	}

}
