package Compilador;

import java.util.ArrayList;
import java.util.List;

public class TablaSimbolo {
	private static List<TokenLexema> tabla=new ArrayList<TokenLexema>();
    
	public TablaSimbolo() {
	}
	
	public static Integer addSimbolo(TokenLexema tok) {
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
