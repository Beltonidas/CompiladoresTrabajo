package Compilador;

public class TokenLexema {
	
	private int id;
	private StringBuilder lexema;
	
	public TokenLexema(int id, String lexema) {
		this.id=id;
		this.lexema= new StringBuilder(lexema);
	}

	public TokenLexema(String lexema) {
		this.id = 0;
		this.lexema = new StringBuilder(lexema);
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
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
	
	
	@Override
	public boolean equals(Object o) {
		try {
			TokenLexema x = (TokenLexema)o;
			if (x.id ==this.id && x.lexema.equals(this.lexema))
				return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	@Override
	public String toString() {
        return "Id: "+this.id+" | Lexema: "+this.lexema.toString();
	}

}
