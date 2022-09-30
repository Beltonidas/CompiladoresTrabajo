package Compilador;

public class TokenLexema {
	
	private String id;
	private StringBuilder lexema;
	
	public TokenLexema(String id, String lexema) {
		this.id=id;
		this.lexema= new StringBuilder(lexema);
	}

	public TokenLexema(String lexema) {
		this.id = null;
		this.lexema = new StringBuilder(lexema);
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id=id;
	}
	
	public void setLexema(String lexema) {
		this.lexema = new StringBuilder(lexema);
	}

	public void appendLexema(Character caracter) {
		this.lexema.append(caracter);
	}
	
	public StringBuilder getLexema() {
		return lexema;
	}
	
	public void resetLexema() {
		this.lexema.delete(0, this.lexema.length());
	}
	
	
	@Override
	public boolean equals(Object o) {
		try {
			TokenLexema x = (TokenLexema)o;
			if (x.id.equals(this.id) && x.lexema.equals(this.lexema))
				return true;
		} catch (Exception e) {
		}
		return false;
	}

}
