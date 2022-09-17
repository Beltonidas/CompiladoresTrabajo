package Compilador;

import java.util.ArrayList;
import java.util.List;

public class TablaSimbolo {
	private List<TokenLexema> tabla;
    
	public TablaSimbolo() {
		this.tabla = new ArrayList<TokenLexema>();
	}
	
	public Integer addSimbolo(TokenLexema tok) {
		if (tabla.contains(tok)) {
			return null;
		} else {
			tabla.add(tok);
			return tabla.size()-1;
		}				
	}
	
	public TokenLexema getSimbolo(Integer i) {
		if (i>0 && i<tabla.size()-1) {
			return tabla.get(i);
		}
		return null;
	}
	
}
