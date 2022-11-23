package Compilador;

import java.io.IOException;
import java.util.Scanner;

import GeneracionTercetos.GestorAssembler;

public class Main {

	public static void main(String[] args) throws IOException {
	    System.out.println("Ruta del archivo que desea ejecutar: ");
	    //Scanner consola = new Scanner(System.in);
	    //String ruta = consola.nextLine();
        //consola.close();
        //AnalizadorLexico.inic(ruta);
	    AnalizadorLexico.inic("./testFiles/prueba_3.txt",false);
		Parser paruser = new Parser();
		AnalizadorLexico.paruser=paruser;
		paruser.run();
		GestorAssembler.generarASM(null);
		// for (HashMap.Entry<Integer,String> entry: GestorAssembler.getVariablesAux().entrySet()){
		// 	//lista de variables
		// 	System.out.println(entry);
		// }
		TablaSimbolos.imprimirTabla();	
	}
}