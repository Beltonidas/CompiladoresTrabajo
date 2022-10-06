package Compilador.AccionesSemanticas;

public class VerificarRangoEntero extends AccionSemantica {

	public VerificarRangoEntero() {
	}
	
	@Override
	public int ejecutar(Character caracter) {
		Integer valor = Integer.parseInt(AccionSemantica.tokenActual.getLexema().toString());
		AccionSemantica.tokenActual.setId(258);
		if (valor>255) {
			System.err.println("Constante entera fuera de rango. Rango Permitido 0 <= x <= 255");
			AccionSemantica.tokenActual.setLexema("255");
		}
		return 0;
	}

}
