package Compilador.AccionesSemanticas;

import java.util.HashMap;

import Compilador.TablaSimbolo;
import Compilador.TokenLexema;

public class ComienzoComentario extends AccionSemantica {

	public ComienzoComentario() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int ejecutar(Character caracter) {
		AccionSemantica.modoComentario=true;
		AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
		return 1;
	}

}
