package Compilador;

public class TokenLexema {
	
	private Integer id;
	private String lexema;
	
	public TokenLexema(Integer id) {
		this.id=id;
		this.lexema=null;
	}
	
	public TokenLexema(Integer id, String lexema) {
		this.id=id;
		this.lexema=lexema;
	}

	public Integer getId() {
		return id;
	}

	public String getLexema() {
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
