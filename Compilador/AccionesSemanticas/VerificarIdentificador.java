package Compilador.AccionesSemanticas;

public class VerificarIdentificador extends AccionSemantica {

	public VerificarIdentificador() {
	}

	@Override
	public int ejecutar(Character caracter) {
		AccionSemantica.tokenActual.setId("Identificador");
		if (AccionSemantica.tokenActual.getLexema().length()>25) {
            AccionSemantica.tokenActual.getLexema().delete(25, AccionSemantica.tokenActual.getLexema().length());
        }
		return 0;
	}

	
	
}
