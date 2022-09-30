package Compilador.AccionesSemanticas;

public class VerificarIdentificador extends AccionSemantica {

	public VerificarIdentificador() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int ejecutar(Character caracter) {
		AccionSemantica.tokenActual.setId("Identificador");
		return 0;
	}

	
	
}
