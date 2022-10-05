package Compilador.AccionesSemanticas;

public class VerificarComparador extends AccionSemantica {

	public VerificarComparador() {
	}

	@Override
	public int ejecutar(Character caracter) {
		switch (caracter) {
			case '+':
				//Segun el caso hacer un token y devolver 0
				AccionSemantica.tokenActual.setId("43");
				AccionSemantica.tokenActual.setLexema(caracter.toString());
				return 1;
			case '-':
				AccionSemantica.tokenActual.setId("45");
				AccionSemantica.tokenActual.setLexema(caracter.toString());
				return 1;
			case '*':
				AccionSemantica.tokenActual.setId("42");
				AccionSemantica.tokenActual.setLexema(caracter.toString());
				return 1;
			case '/':
				AccionSemantica.tokenActual.setId("47");
				AccionSemantica.tokenActual.setLexema(caracter.toString());
				return 1;
			case '{':
				AccionSemantica.tokenActual.setId("123");
				AccionSemantica.tokenActual.setLexema(caracter.toString());
				return 1;
			case '}':
				AccionSemantica.tokenActual.setId("125");
				AccionSemantica.tokenActual.setLexema(caracter.toString());
				return 1;
			case '[':
				AccionSemantica.tokenActual.setId("91");
				AccionSemantica.tokenActual.setLexema(caracter.toString());
				return 1;
			case ']':
				AccionSemantica.tokenActual.setId("93");
				AccionSemantica.tokenActual.setLexema(caracter.toString());
				return 1;
			case '(':
				AccionSemantica.tokenActual.setId("40");
				AccionSemantica.tokenActual.setLexema(caracter.toString());
				return 1;
			case ')':
				AccionSemantica.tokenActual.setId("41");
				AccionSemantica.tokenActual.setLexema(caracter.toString());
				return 1;
			case ',':
			    AccionSemantica.tokenActual.setId("44");
                AccionSemantica.tokenActual.setLexema(caracter.toString());
				return 1;
			case ';':
				AccionSemantica.tokenActual.setId("59");
				AccionSemantica.tokenActual.setLexema(caracter.toString());
				return 1;
			case ':':
			    if(AccionSemantica.tokenActual.getLexema().length()==1) {
			        AccionSemantica.tokenActual.setId("273");
			    } else {
			        AccionSemantica.tokenActual.setId("58");
			    }
				AccionSemantica.tokenActual.appendLexema(caracter);
				return 1;
			case '=':
				if(AccionSemantica.tokenActual.getLexema().toString().equals("="))
				{
				    AccionSemantica.tokenActual.setId("61");
					return 0;
				}else if (AccionSemantica.tokenActual.getLexema().toString().equals(">")) {
				    AccionSemantica.tokenActual.setId("274");
				}else{
                    AccionSemantica.tokenActual.setId("275");
                }
				AccionSemantica.tokenActual.appendLexema(caracter);
				return 1;
			case '!':
			    if(AccionSemantica.tokenActual.getLexema().length()==1) {
                    AccionSemantica.tokenActual.setId("276");
                } else {
                    AccionSemantica.tokenActual.setId("33");
                }
                AccionSemantica.tokenActual.appendLexema(caracter);
                return 1;
			default:
			    switch (AccionSemantica.tokenActual.getLexema().toString()) {
			        case "=":
			            AccionSemantica.tokenActual.setId("61");
			            break;
			        case "<":
			            AccionSemantica.tokenActual.setId("60");
                        break;
			        case ">":
			            AccionSemantica.tokenActual.setId("62");
                        break;
                    default:
                        break;
			    }
				return 0;
		}
	}

}
