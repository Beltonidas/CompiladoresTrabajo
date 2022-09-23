package Compilador.AccionesSemanticas;

import Compilador.TokenLexema;

public class VerificarComparador extends AccionSemantica {

	public VerificarComparador() {
	}

	@Override
	public int ejecutar(Character caracter) {
		switch (AccionSemantica.tokenActual.getLexema().toString()) {
			case "+":
				//Segun el caso hacer un token y devolver 0
				AccionSemantica.tokenActual.setId();
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
			case "-":
				AccionSemantica.tokenActual.setId(null);
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
			case "*":
				AccionSemantica.tokenActual.setId(null);
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
			case "/":
				AccionSemantica.tokenActual.setId(null);
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
			case "{":
				AccionSemantica.tokenActual.setId(null);
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
			case "}":
				AccionSemantica.tokenActual.setId(null);
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
			case "[":
				AccionSemantica.tokenActual.setId(null);
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
			case "]":
				AccionSemantica.tokenActual.setId(null);
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
			case "(":
				AccionSemantica.tokenActual.setId(null);
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
			case ")":
				AccionSemantica.tokenActual.setId(null);
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
			case ",":
				AccionSemantica.tokenActual.setId(null);
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
			case ";":
				AccionSemantica.tokenActual.setId(null);
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
			case ":":
				AccionSemantica.tokenActual.setId(null);
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
			case "=:":
				AccionSemantica.tokenActual.setId(null);
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
			case "<=":
				AccionSemantica.tokenActual.setId(null);
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
			case ">=":
				AccionSemantica.tokenActual.setId(null);
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
			case "==":
				AccionSemantica.tokenActual.setId(null);
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
			case "=!":
				AccionSemantica.tokenActual.setId(null);
				AccionSemantica.tokenActual.getLexema().delete(0, AccionSemantica.tokenActual.getLexema().length());
				break;
		}
		return 0;
	}

}
