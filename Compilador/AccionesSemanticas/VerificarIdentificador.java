package Compilador.AccionesSemanticas;

import java.util.HashMap;

import Compilador.TokenLexema;
import Compilador.TablaSimbolo;

public class VerificarIdentificador extends AccionSemantica {

	public VerificarIdentificador() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int ejecutar(Character caracter) {
		
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		//Chequear palabras reservadas
		Integer id = map.get(AccionSemantica.tokenActual.getLexema().toString().toLowerCase());
		if (id == null) {
			AccionSemantica.tokenActual.setId(null);
			// queda completar esta linea, solo use para testear
			if (AccionSemantica.tokenActual.getLexema().length()>25) {
				AccionSemantica.tokenActual.getLexema().delete(25, AccionSemantica.tokenActual.getLexema().length());
			}
			TablaSimbolo.addSimbolo(AccionSemantica.tokenActual);
			return 0;
		} else {
			AccionSemantica.tokenActual = new TokenLexema(id);
			return 0;
		}
	}

	
	
}
