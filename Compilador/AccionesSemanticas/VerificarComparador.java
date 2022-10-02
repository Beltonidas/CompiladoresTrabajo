package Compilador.AccionesSemanticas;

public class VerificarComparador extends AccionSemantica {

	public VerificarComparador() {
	}

	@Override
	public int ejecutar(Character caracter) {
		switch (caracter) {
			case '+':
				//Segun el caso hacer un token y devolver 0
				AccionSemantica.tokenActual.setId("Comparador");
				AccionSemantica.tokenActual.appendLexema(caracter);
				return 1;
			case '-':
				AccionSemantica.tokenActual.setId("Comparador");
				AccionSemantica.tokenActual.appendLexema(caracter);
				return 1;
			case '*':
				AccionSemantica.tokenActual.setId("Comparador");
				AccionSemantica.tokenActual.appendLexema(caracter);
				return 1;
			case '/':
				AccionSemantica.tokenActual.setId("Comparador");
				AccionSemantica.tokenActual.appendLexema(caracter);
				return 1;
			case '{':
				AccionSemantica.tokenActual.setId("Separador");
				AccionSemantica.tokenActual.appendLexema(caracter);
				return 1;
			case '}':
				AccionSemantica.tokenActual.setId("Separador");
				AccionSemantica.tokenActual.appendLexema(caracter);
				return 1;
			case '[':
				AccionSemantica.tokenActual.setId("Separador");
				AccionSemantica.tokenActual.appendLexema(caracter);
				return 1;
			case ']':
				AccionSemantica.tokenActual.setId("Separador");
				AccionSemantica.tokenActual.appendLexema(caracter);
				return 1;
			case '(':
				AccionSemantica.tokenActual.setId("Separador");
				AccionSemantica.tokenActual.appendLexema(caracter);
				return 1;
			case ')':
				AccionSemantica.tokenActual.setId("Separador");
				AccionSemantica.tokenActual.appendLexema(caracter);
				return 1;
			case ',':
				AccionSemantica.tokenActual.setId("Separador");
				AccionSemantica.tokenActual.appendLexema(caracter);
				return 1;
			case ';':
				AccionSemantica.tokenActual.setId("Separador");
				AccionSemantica.tokenActual.appendLexema(caracter);
				return 1;
			case ':':
				AccionSemantica.tokenActual.setId("Comparador");
				AccionSemantica.tokenActual.appendLexema(caracter);
				return 1;
			case '=':
				AccionSemantica.tokenActual.setId("Comparador");
				if(AccionSemantica.tokenActual.getLexema().toString().equals("="))
				{
					return 0;
				}
				AccionSemantica.tokenActual.appendLexema(caracter);
				return 1;
			case '!':
				AccionSemantica.tokenActual.setId("Comparador");
				AccionSemantica.tokenActual.appendLexema(caracter);
				return 1;
			default:
				return 0;
		}
	}

}
