package Compilador.AccionesSemanticas;

public class VerificarIdentificador extends AccionSemantica {

	public VerificarIdentificador() {
	}

	@Override
	public int ejecutar(Character caracter) {
		AccionSemantica.tokenActual.setId(257);
		if (AccionSemantica.tokenActual.getLexema().length()>25) {
		    String ant = AccionSemantica.tokenActual.getLexema().toString();
		    String neu = AccionSemantica.tokenActual.getLexema().delete(25, AccionSemantica.tokenActual.getLexema().length()).toString();
		    System.err.println("|/|/|/| Warning: Identificador: "+ant+"\n|/|/|/| Warning: Truncado a: "+neu+", por superar longitud de 25 caracteres.");
        }
		return 0;
	}

	
	
}
