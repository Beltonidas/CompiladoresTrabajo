%{
package Compilador;
%}

//Declaracion de tokens:
%token id cte cadena If then Else end_if out fun Return Break ui8 f64 discard For Continue defer SIMB_ASIGNACION SIMB_MAY_IGUAL
 SIMB_MEN_IGUAL SIMB_DISTINTO SIMB_COM_I SIMB_COM_F

%left '+' '-'
%left '*' '/'

%start programa

%% //Especificacion de la gramatica 

programa: nombre_programa bloque_sentencias {programaListo();}
;

nombre_programa: id
		| cte {errorEnXY("Nombre del programa invalido. Identificador esperado, constante recibida en cambio");}
		| cadena {errorEnXY("Nombre del programa invalido. Identificador esperado, cadena recibida en cambio");}
;

bloque_sentencias: bloque_sentencias '{' sentencia '}'
		| '{' sentencia '}'
		| '{' sentencia {errorEnXY("Esperando final de bloque");}
		| sentencia '}' {errorEnXY("Esperando comienzo de bloque");}

sentencia: declarativa
		| ejecutable
		| sentencia declarativa
		| sentencia ejecutable
;

//REGLAS PARA LAS SENTENCIAS DECLARATIVAS
declarativa: dec_variables {imprimirMSGEstructura("Declaracion de variable/s");}
		| dec_funcion {imprimirMSGEstructura("Declaracion de funcion");}
;

dec_variables: tipo list_variables ';'
		| list_variables ';' {errorEnXY("Tipo de la/s variable/s esperado al comienzo de la sentencia");}
;

tipo: ui8
		| f64 
		| id {errorEnXY("Tipo de la/s variable/s invalido");}
;

list_variables: id
		| list_variables ',' id
		| error {errorEnXY("Se esperaba un identificador o una lista de identificadores separados por ,");}
;

dec_funcion: header_funcion cola_funcion
		| header_funcion parametro cola_funcion
		| header_funcion parametro ',' parametro cola_funcion
;

header_funcion: fun id '('
		| fun '(' {errorEnXY("La declaracion de la funcion necesita un nombre");}
;

cola_funcion: ')' ':' tipo '{' cuerpo_fun '}'
		| error {errorEnXY("En la declaracion de la funcion falta: ),:,{ o }");}
;

parametro: tipo id
		| tipo {errorEnXY("Identificador del parametro esperado  en la declaracion de funcion");}
;

cuerpo_fun: sentencia Return '(' expresion ')' ';'
		| sentencia Return '(' expresion ';' {errorEnXY("Parentesis esperados al final de la expresion");}
		| sentencia Return expresion ';' {errorEnXY("Parentesis esperados al comienzo y final de la expresion");}
		| sentencia Return expresion ')' ';' {errorEnXY("Parentesis esperados al final de la expresion");}
		| sentencia Return expresion {errorEnXY("Parentesis esperados al comienzo y final de la expresion y ; al final de linea");}
		| sentencia Return '(' expresion ')'  {errorEnXY("; esperados al final de linea");}
;

//REGLAS PARA LAS SENTENCIAS EJECUTABLES
ejecutable: inst_ejecutable
		| defer inst_ejecutable {imprimirMSGEstructura("Defer de instruccion ejecutable");}
; 

inst_ejecutable: asignacion ';' {imprimirMSGEstructura("Asignacion");}
		| seleccion ';' {imprimirMSGEstructura("Seleccion If");}
		| impresion ';' {imprimirMSGEstructura("Impresion a Consola");}
		| invocar_fun ';' {imprimirMSGEstructura("Invocacion de Funcion");}
		| for_continue {imprimirMSGEstructura("Loop For");}
;

asignacion: id SIMB_ASIGNACION expresion
		| id SIMB_ASIGNACION {errorEnXY("Expresion esperada despues de la asignacion");}
		| id ':' '=' expresion {errorEnXY("Operador de asignacion incorrecto, se esperaba -> =:");}
;

expresion: expresion '+' termino
		| expresion '-' termino
		| termino
;

termino: termino '*' factor
		| termino '/' factor
		| factor
;

factor: id
		| cte
		| '-' cte {verificarRangoDoubleNegativo();}
		| retorno_funcion
;

retorno_funcion: id '(' ')'
		| id '(' parametro_real ')'
		| id '(' parametro_real ',' parametro_real ')'
;

parametro_real: id 
		| cte
		| '-' cte {verificarRangoDoubleNegativo();}
;

seleccion: If condicion_if then_selec end_if
		| If condicion_if then_selec else_selecc end_if
;

then_selec: then '{' ejecutable_for '}'
		| then inst_ejecutable_for
;

else_selecc: Else '{' ejecutable_for '}'
		| Else inst_ejecutable_for
;

condicion_if: '(' expresion_booleana ')'
;

expresion_booleana: expresion comparador expresion
;

comparador: SIMB_DISTINTO
		| SIMB_MAY_IGUAL
		| SIMB_MEN_IGUAL
		| '='
		| '<'
		| '>'
;

impresion: out '(' cadena ')'
		| out cadena ')' {errorEnXY("Falta de parentesis al comienzo de la cadena del out");}
		| out '(' cadena {errorEnXY("Falta de parentesis al final de la cadena del out");}
;

invocar_fun: discard retorno_funcion
		| retorno_funcion {errorEnXY("Funcion invocada sin discard del retorno");}
;

for_continue: For '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' '{' ejecutable_for '}' ';' {verificarIdIguales($3.sval, $7.sval);verificarConstanteEntera($5.sval);verificarConstanteEntera($12.sval);}
		| For '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' inst_ejecutable_for {verificarIdIguales($3.sval, $7.sval);verificarConstanteEntera($5.sval);verificarConstanteEntera($12.sval);}
		| cadena ':' For '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' '{' ejecutable_for '}' ';' {verificarIdIguales($5.sval, $9.sval);verificarConstanteEntera($7.sval);verificarConstanteEntera($14.sval);}
		| cadena ':' For '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' inst_ejecutable_for {verificarIdIguales($5.sval, $9.sval);verificarConstanteEntera($7.sval);verificarConstanteEntera($14.sval);}
;

mas_o_menos: '+'
		| '-'
;

ejecutable_for: ejecutable_for inst_ejecutable_for
		| inst_ejecutable_for
; 

inst_ejecutable_for: inst_ejecutable
		| Break ';'
		| Break ':' cadena ';'
		| Continue ';'
;

%%

public static final String ANSI_RESET ="\u001B[0m";
public static final String ANSI_RED = "\u001B[31m";
public static final String ANSI_GREEN = "\u001B[32m";
public static final String ANSI_YELLOW = "\u001B[33m";
public static final String ANSI_BLUE = "\u001B[34m";
public static final String ANSI_PURPLE ="\u001B[35m";
public static final String ANSI_CYAN = "\u001B[36m";

public void errorEnXY(String msg){
	int linea,caracter=0;
	linea = AnalizadorLexico.getLinea();
	caracter = AnalizadorLexico.getCaracter();
	yynerrs++;
	System.out.println(ANSI_RED+"!|!|!|! Error en linea: "+linea+", caracter: "+caracter+". Errores hasta ahora: "+yynerrs+"\n"+msg+"\n"+ANSI_RESET);
}

public void verificarIdIguales(String id_1, String id_2){
	if (!id_1.equals(id_2)){
		errorEnXY("Identificadores no coincidentes");
	}
}

public void verificarConstanteEntera(String lexema){
	if (lexema.contains(".")){
		errorEnXY("La constante "+lexema+" debe ser de tipo entero");
	}
}

public void verificarRangoDoubleNegativo(){
	StringBuilder lexema = new StringBuilder(AnalizadorLexico.anteriorToken.getLexema().toString());
    int indexPunto = lexema.indexOf(".");
    if (indexPunto== -1)
        return;
    int indexExponente = lexema.indexOf("D");
    if (indexExponente!=-1) {
        lexema.deleteCharAt(indexExponente);
        lexema.insert(indexExponente, 'E');
    }
    Double parteDecimal=Double.parseDouble("-"+lexema.toString());
    if (parteDecimal==0.0 || (Double.parseDouble("-2.2250738585072014E-308") > parteDecimal && parteDecimal > Double.parseDouble("-1.7976931348623157E308"))) {
        return;
    }
    errorEnXY("Valor de la constante double fuera de rango");
}

private void yyerror(String msg){
	errorEnXY(msg);
	yynerrs--;
	//Esto es porque en parser se aumenta el numero de errores una vez finalizada la llamada al metodo yyerror y 
	//veniamos modificandolo de tal manera que lo haga antes pero decidimos hacer esto por si alguna vez se nos olvidaba
}

public void warningEnXY(String msg){
	int linea,caracter=0;
	linea = AnalizadorLexico.getLinea();
	caracter = AnalizadorLexico.getCaracter();
	System.out.println(ANSI_YELLOW+"!|/|/|! Warning en linea: "+linea+", caracter: "+caracter+"\n"+msg+"\n"+ANSI_RESET);
}

private void imprimirMSGEstructura(String msg){
	int linea=0;
	linea = AnalizadorLexico.getLinea();
	System.out.println(ANSI_BLUE+"/|/|/|/: "+msg+" termina de reconocerse en la linea: "+linea+".\n"+ANSI_RESET);
}

private void programaListo(){
	if (yynerrs!=0){
		System.out.println(ANSI_RED+"!|!|!|!: El programa encontro "+yynerrs+" errores al compilarse."+ANSI_RESET);
		return;
	}
	System.out.println(ANSI_GREEN+"%|%|%|%: El programa compilo sin errores."+ANSI_RESET);
}

private int yylex(){
	int rta=0;
    rta=AnalizadorLexico.siguienteToken();
    //System.out.println("Siguiente token: "+rta+", valor: "+AnalizadorLexico.anteriorToken.getId());
    if (rta!=-1) {
        if (AnalizadorLexico.anteriorToken.getLexema() != null){
            yylval = new ParserVal(AnalizadorLexico.anteriorToken.getLexema().toString());
        } else {
            yylval = new ParserVal();
        }
        return rta;
    }
    return 0;
}