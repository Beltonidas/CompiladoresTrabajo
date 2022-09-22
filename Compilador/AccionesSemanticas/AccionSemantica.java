package Compilador.AccionesSemanticas;

import java.util.List;

import Compilador.TokenLexema;

public class AccionSemantica {
	protected TokenLexema tokenActual;
	
	/*
	 * AS 0 Generar nuevo token sin tipo
	 * AS 1	Añadir caracter al token
	 * AS 2 Verificar palabras reservadas, truncar identificador de ser necesario y devolver el token
	 * AS 3	Crear nuevo token de double y cambiar tipo de token a double
	 * AS 4	Verificar si es un short unsigned int //entre 0 y 255 o un entero comun
	 * AS 5	Verificar rango de double y devolver token
	 * AS 6	Verificar tipo de comparador y devolver token correspondiente
	 * AS 7	Borrar lista caracteres acumulados y dejar de acumular
	 * AS 8	Volver a acumular caracteres e informar del comentario
	*/
    public int ejecutar(Character caracter) {
		return -1;
    }
}
