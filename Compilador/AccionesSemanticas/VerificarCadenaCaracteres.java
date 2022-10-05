package Compilador.AccionesSemanticas;


public class VerificarCadenaCaracteres extends AccionSemantica {

	public VerificarCadenaCaracteres(){
	}

	@Override
	public int ejecutar(Character caracter) {
		if (caracter=='\n'){
			AccionSemantica.tokenActual.setLexema("Error las cadenas de caracteres no puede tener un salto de linea");
			return -1;
		} else {
			AccionSemantica.tokenActual.setId("259");
			return 1;
		}
    }

}
