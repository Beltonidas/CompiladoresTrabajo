package Compilador.AccionesSemanticas;

import Compilador.TokenLexema;

public class AccionSemantica {
	protected static TokenLexema tokenActual;
	
	
	//---Done
	/*
	 * AS 0 Generar nuevo token sin tipo
	 * AS 1	A�adir caracter al token
	 * AS 2 Verificar palabras reservadas, truncar identificador de ser necesario y devolver el token
	 * AS 3	Verificar si es un short unsigned int //entre 0 y 255 o un entero comun
	 * AS 4	Verificar rango de double y devolver token
	 * AS 5	Verificar tipo de comparador y devolver token correspondiente
	 * AS 6	Borrar lista caracteres acumulados y dejar de acumular
	 * AS 7	Volver a acumular caracteres e informar del comentario
	*/
	
	//Return de 1 significa caracter consumido, 0 significa repetir el mismo, -1 error detallado en el token
	
    public int ejecutar(Character caracter) {
		return -1;
    }
    
    public static TokenLexema getToken() {
		return AccionSemantica.tokenActual;
    }

    public static void getNewToken() {
        tokenActual = new TokenLexema("");
    }
}
