package Compilador;

import java.io.IOException;
import java.util.Scanner;

import GeneracionTercetos.GestorAssembler;

public class Main {

	public static void main(String[] args) throws IOException {
	    Boolean com=false;
	    System.out.println("Ruta del archivo que desea ejecutar: ");
	    Scanner consola = new Scanner(System.in);
	    String ruta = consola.nextLine();
        System.out.println("�Desea que se ejecute en modo verboso? Y/N");
        String verb = consola.nextLine();
        if(verb.contains("Y")||verb.contains("y")) {
            com=true;
        }
        consola.close();
        AnalizadorLexico.inic(ruta,com);
		AnalizadorLexico.inic("./testFiles/test.txt",com);
		Parser paruser = new Parser();
		AnalizadorLexico.paruser=paruser;
		paruser.run();
		GestorAssembler.generarASM(null);
	}
}