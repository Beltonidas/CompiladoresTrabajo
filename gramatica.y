%{
package Compilador;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;;
%}

//Declaracion de tokens:
%token id cte cadena if then else end_if out fun return break ui8 f64 discard for continue defer SIMB_ASIGNACION SIMB_MAY_IGUAL
 SIMB_MEN_IGUAL SIMB_DISTINTO SIMB_COM_I SIMB_COM_F

%left '+' '-'
%left '*' '/'

%start programa

%% //Especificacion de la gramatica

programa: nombre_programa sentencia
;

nombre_programa: id
;

sentencia: sentencia '{' ejecutiva '}'
            | sentencia '{' declarativa '}'
            | '{' ejecutiva '}'
            | '{' declarativa '}'

//REGLAS PARA LAS SENTENCIAS DECLARATIVAS
declarativa: dec_variables
            | dec_funcion
;

dec_variables: dec_variables tipo list_variables ';'
            | tipo list_variables ';'
;

tipo: ui8
            | f64 
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
;

cuerpo_fun: sentencia return '(' expresion ')' ';'
;

//REGLAS PARA LAS SENTENCIAS EJECUTIVAS
ejecutiva: ejecutiva inst_ejecutiva
            | inst_ejecutiva
            | ejecutiva defer inst_ejecutiva
            | defer inst_ejecutiva
; 

inst_ejecutiva: asignacion ';'
            | seleccion ';'
            | impresion ';'
            | invocar_fun ';'
            | for_continue ';'
;//BORRAR COMENTARIO CUANDO SE ESTE SEGURO DE QUE ESTA REGLA ESTA BIEN

asignacion: id SIMB_ASIGNACION expresion
            | id SIMB_ASIGNACION '(' expresion ')'
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
            | '-' cte 
            | id '(' ')'
            | id '(' factor ')'
            | id '(' factor ',' factor ')'
;

seleccion: if condicion_if then_selec end_if ';'
            | if condicion_if then_selec else_selecc end_if ';'
;

then_selec: then '{' ejecutiva_for '}'
            | then inst_ejecutiva_for
;

else_selecc: '{' ejecutiva_for '}'
            | inst_ejecutiva_for
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
;

invocar_fun: discard '(' factor ')' 
            | '(' factor ')' //PONER ACCION QUE INFORME DE ERROR
;

for_continue: for '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' '{' ejecutiva_for '}'
            | for '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' inst_ejecutiva_for
            | cadena ':' for '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' '{' ejecutiva_for '}'
            | cadena ':' for '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' inst_ejecutiva_for
;

mas_o_menos: '+'
            | '-'
;

ejecutiva_for: ejecutiva_for inst_ejecutiva_for
            | inst_ejecutiva_for
; 

inst_ejecutiva_for: inst_ejecutiva
            | break ';'
            | break ':' cadena ';'
            | continue ';'
;

%%

//codigo loco