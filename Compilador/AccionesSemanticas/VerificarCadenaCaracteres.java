package Compilador.AccionesSemanticas;


public class VerificarCadenaCaracteres extends AccionSemantica {

	public VerificarCadenaCaracteres(){
	}

	@Override
	public int ejecutar(Character caracter) {
		if (caracter=='\n'){
			System.err.println("Error las cadenas de caracteres no puede tener un salto de linea");
			AccionSemantica.tokenActual.setId(259);
			return -1;
		} else {
		    AccionSemantica.tokenActual.appendLexema(caracter);
			AccionSemantica.tokenActual.setId(259);
			return 1;
		}
    }

}
