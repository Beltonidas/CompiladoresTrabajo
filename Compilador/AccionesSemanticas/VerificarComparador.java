package Compilador.AccionesSemanticas;

public class VerificarComparador extends AccionSemantica {

	public VerificarComparador() {
	}

	@Override
	public int ejecutar(Character caracter) {
		AccionSemantica.tokenActual.appendLexema(caracter);
		switch (caracter) {
			case '+':
				//Segun el caso hacer un token y devolver 0
				AccionSemantica.tokenActual.setId("Comparador");
				return 1;
			case '-':
				AccionSemantica.tokenActual.setId("Comparador");
				return 1;
			case '*':
				AccionSemantica.tokenActual.setId("Comparador");
				return 1;
			case '/':
				AccionSemantica.tokenActual.setId("Comparador");
				return 1;
			case '{':
				AccionSemantica.tokenActual.setId("Separador");
				return 1;
			case '}':
				AccionSemantica.tokenActual.setId("Separador");
				return 1;
			case '[':
				AccionSemantica.tokenActual.setId("Separador");
				return 1;
			case ']':
				AccionSemantica.tokenActual.setId("Separador");
				return 1;
			case '(':
				AccionSemantica.tokenActual.setId("Separador");
				return 1;
			case ')':
				AccionSemantica.tokenActual.setId("Separador");
				return 1;
			case ',':
				AccionSemantica.tokenActual.setId("Separador");
				return 1;
			case ';':
				AccionSemantica.tokenActual.setId("Separador");
				return 1;
			case ':':
				AccionSemantica.tokenActual.setId("Comparador");
				return 1;
			default:
				return 0;
		}
	}

}
