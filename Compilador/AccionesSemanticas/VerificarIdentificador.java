package Compilador.AccionesSemanticas;

public class VerificarIdentificador extends AccionSemantica {

	public VerificarIdentificador() {
	}

	@Override
	public int ejecutar(Character caracter) {
		AccionSemantica.tokenActual.setId("Identificador");
		return 0;
	}

	
	
}
