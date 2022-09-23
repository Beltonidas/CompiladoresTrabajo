package Compilador;

public class TokenLexema {
	
	private Integer id;
	private StringBuilder lexema;
	
	public TokenLexema(Integer id) {
		this.id=id;
		this.lexema = new StringBuilder();
	}
	
	public TokenLexema(Integer id, String lexema) {
		this.id=id;
		this.lexema= new StringBuilder(lexema);
	}

	public TokenLexema(String lexema) {
		this.id = null;
		this.lexema = new StringBuilder(lexema);
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id=id;
	}
	
	public void setLexema(String lexema) {
		this.lexema.delete(0, this.lexema.length());
		this.lexema.append(lexema);
	}

	public void appendLexema(Character caracter) {
		this.lexema.append(caracter);
	}
	
	public StringBuilder getLexema() {
		//Debe retornar segun el tipo que sea
		return lexema;
	}
	
	
	@Override
	public boolean equals(Object o) {
		try {
			TokenLexema x = (TokenLexema)o;
			if (x.getId()==this.id && x.getLexema()==this.lexema)
				return true;
		} catch (Exception e) {
		}
		return false;
	}

}
