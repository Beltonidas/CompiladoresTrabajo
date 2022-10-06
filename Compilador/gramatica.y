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

programa: nombre_programa bloque_sentencias {System.out.println("Programa listo");}
;

nombre_programa: id
		| cte {errorEnXY("Nombre del programa invalido. Identificador esperado, constante recibida en cambio");}
		| cadena {errorEnXY("Nombre del programa invalido. Identificador esperado, cadena recibida en cambio");}
;

bloque_sentencias: bloque_sentencias '{' sentencia '}'
		| '{' sentencia '}'
		| '{' sentencia {errorEnXY("Esperando final de bloque");}
		| sentencia '}' {errorEnXY("Esperando comienzo de bloque");}

sentencia: declarativa {System.out.println("Sentencia 1");}
		| ejecutable {System.out.println("Sentencia 2");}
		| sentencia declarativa {System.out.println("Sentencia 3");}
		| sentencia ejecutable {System.out.println("Sentencia 4");}
;

//REGLAS PARA LAS SENTENCIAS DECLARATIVAS
declarativa: dec_variables {System.out.println("Declarativa 1");}
		| dec_funcion {System.out.println("Declarativa 2");}
;

dec_variables: tipo list_variables ';' {System.out.println("Declarativa var 1");}
		| list_variables ';' {errorEnXY("Tipo de la/s variable/s esperado al comienzo de la sentencia");System.out.println("Declarativa var 2");}
;

tipo: ui8
		| f64 
		| id {errorEnXY("Tipo de la/s variable/s invalido");}
;

list_variables: id
		| list_variables ',' id
;

dec_funcion: header_funcion cola_funcion
		| header_funcion parametro cola_funcion
		| header_funcion parametro ',' parametro cola_funcion
;

header_funcion: fun id '('
;

cola_funcion: ')' ':' tipo '{' cuerpo_fun '}'
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
ejecutable: inst_ejecutable {System.out.println("ejecutable 1era regla");}
		| defer inst_ejecutable {System.out.println("ejecutable 2era regla");}
; 

inst_ejecutable: asignacion ';' {System.out.println("inst_ejecutable 1era regla");}
		| seleccion ';' {System.out.println("inst_ejecutable 2era regla");}
		| impresion ';' {System.out.println("inst_ejecutable 3era regla");}
		| invocar_fun ';' {System.out.println("inst_ejecutable 4era regla");}
		| for_continue ';' {System.out.println("inst_ejecutable 5era regla");}
;

asignacion: id SIMB_ASIGNACION expresion {System.out.println("Asignacion 1era regla");}
		| id SIMB_ASIGNACION '(' expresion ')' {System.out.println("Asignacion 2era regla");}
		| id SIMB_ASIGNACION '(' expresion {errorEnXY("Parentesis esperados al final de expresion");System.out.println("Asignacion 3era regla");}
		| id SIMB_ASIGNACION expresion ')' {errorEnXY("Parentesis esperados al comienzo de expresion");System.out.println("Asignacion 4era regla");}
;

expresion: expresion '+' termino {System.out.println("Expresion 1era regla");}
		| expresion '-' termino {System.out.println("Expresion 2era regla");}
		| termino {System.out.println("Expresion 3era regla");}
;

termino: termino '*' factor {System.out.println("Termino 1era regla");}
		| termino '/' factor {System.out.println("Termino 2era regla");}
		| factor {System.out.println("Termino 3era regla");}
;

factor: id {System.out.println("Factor 1era regla");}
		| cte {System.out.println("Factor 2era regla");}
		| '-' cte {verificarRangoDoubleNegativo();System.out.println("Factor 3era regla");}
		| id '(' ')' {System.out.println("Factor 4era regla");}
		| id '(' factor ')' {System.out.println("Factor 5era regla");}
		| id '(' factor ',' factor ')' {System.out.println("Factor 6era regla");}
;

seleccion: If condicion_if then_selec end_if ';'
		| If condicion_if then_selec else_selecc end_if ';'
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

invocar_fun: discard '(' factor ')' 
		| '(' factor ')' {errorEnXY("Funcion invocada sin discard del retorno");}
;

for_continue: For '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' '{' ejecutable_for '}' {verificarIdIguales($3.sval, $7.sval);}
		| For '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' inst_ejecutable_for {verificarIdIguales($3.sval, $7.sval);}
		| cadena ':' For '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' '{' ejecutable_for '}' {verificarIdIguales($5.sval, $9.sval);}
		| cadena ':' For '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' inst_ejecutable_for {verificarIdIguales($5.sval, $9.sval);}
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

public void errorEnXY(String mensaje){
	int linea,caracter=0;
	linea = AnalizadorLexico.getLinea();
	caracter = AnalizadorLexico.getCaracter();
	System.err.println("!|!|!|! Error en linea: "+linea+", caracter: "+caracter+". "+mensaje);
}

public void verificarIdIguales(String id_1, String id_2){
	int linea,caracter=0;
	linea = AnalizadorLexico.getLinea();
	caracter = AnalizadorLexico.getCaracter();
	if (id_1.equals(id_2)){
		System.err.println("!|!|!|! Error en linea: "+linea+", caracter: "+caracter+". Identificadores no coincidentes");
	}
}

public void verificarRangoDoubleNegativo(){
	int linea,caracter=0;
	linea = AnalizadorLexico.getLinea();
	caracter = AnalizadorLexico.getCaracter();
    Double parteDecimal=0.0;
    int parteExponente=0;
    StringBuilder lexema = new StringBuilder("-");
    lexema.append(AnalizadorLexico.anteriorToken.getLexema().toString());
    int indexPunto = lexema.indexOf(".");
    int indexExponente = lexema.indexOf("D");
    if (indexPunto==0) {
        if (indexExponente!=-1) {
            parteExponente = Integer.parseInt(lexema.substring(indexExponente+1,lexema.length()));
            if (parteExponente>=308||parteExponente<=-308) {
                System.err.println("!|!|!|! Error en linea: "+linea+", caracter: "+caracter+". Exponente de la constante double fuera de rango.");
            }
            parteDecimal = Double.parseDouble("-0"+lexema.substring(indexPunto, indexExponente)+"E"+lexema.substring(indexExponente+1,lexema.length()));
        }else {
            parteDecimal = Double.parseDouble("-0"+lexema.substring(indexPunto, lexema.length()));
        }
        if (Double.MIN_VALUE > parteDecimal || parteDecimal > Double.MAX_VALUE) {
            System.err.println("!|!|!|! Error en linea: "+linea+", caracter: "+caracter+". Valor de la constante double fuera de rango.");
            
        }
    } else {
        if (indexExponente!=-1) {
            parteExponente = Integer.parseInt(lexema.substring(indexExponente+1,lexema.length()));
            if (parteExponente>=308||parteExponente<=-308) {
                System.err.println("!|!|!|! Error en linea: "+linea+", caracter: "+caracter+". Exponente de la constante double fuera de rango.");
            }
            parteDecimal = Double.parseDouble("-"+lexema.substring(0, indexExponente)+"0E"+lexema.substring(indexExponente+1,lexema.length()));
        } else {
            parteDecimal = Double.parseDouble("-"+lexema.substring(0, lexema.length())+"0");
        }
        if (Double.MIN_VALUE > parteDecimal || parteDecimal > Double.MAX_VALUE) {
            System.err.println("!|!|!|! Error en linea: "+linea+", caracter: "+caracter+". Valor de la constante double fuera de rango.");
        }
    }
}

private void yyerror(String msg){
	errorEnXY(msg);
}

private int yylex(){
	int rta=0;
    rta=AnalizadorLexico.siguienteToken();
    System.out.println("Siguiente token: "+rta+", valor: "+AnalizadorLexico.anteriorToken.getId());
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