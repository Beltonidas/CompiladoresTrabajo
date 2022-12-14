%{
package Compilador;
import java.util.HashMap;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import GeneracionTercetos.*;
%}

//Declaracion de tokens:
%token id cte cadena If then Else end_if out fun Return Break ui8 f64 discard For Continue defer SIMB_ASIGNACION SIMB_MAY_IGUAL
 SIMB_MEN_IGUAL SIMB_DISTINTO SIMB_COM_I SIMB_COM_F

%start programa

%% //Especificacion de la gramatica 

programa: nombre_programa bloque_sentencias {programaListo();}
;

nombre_programa: id {setearUso($1.sval,"Nombre Programa");
					Ambito.addAmbito("_");
					TablaSimbolos.cambiarNombreKey($1.sval);
					Ambito.removeAmbito();
					Ambito.addAmbito("Main");}
		| cte {errorEnXY("Nombre del programa invalido. Identificador esperado, constante recibida en cambio");}
		| cadena {errorEnXY("Nombre del programa invalido. Identificador esperado, cadena recibida en cambio");}
;

bloque_sentencias: '{' sentencia '}' {Ambito.removeAmbito();}
		| '{' sentencia {errorEnXY("Esperando final de bloque");}
		| sentencia '}' {errorEnXY("Esperando comienzo de bloque");}

sentencia: declarativa
		| ejecutable
		| sentencia declarativa
		| sentencia ejecutable
;

//REGLAS PARA LAS SENTENCIAS DECLARATIVAS
declarativa: dec_variables {imprimirMSGEstructura("Declaracion de variable/s");}
		| dec_funcion {Terceto.GetTerceto(llamadasFunciones.get($1.sval).getTercetoInv()).setCarg(true);
						imprimirMSGEstructura("Declaracion de funcion");}
;

dec_variables: tipo list_variables ';'
		| list_variables ';' {errorEnXY("Tipo de la/s variable/s esperado al comienzo de la sentencia");}
;

tipo: ui8 {tipoAux=$1.sval;}
		| f64 {tipoAux=$1.sval;}
		| id {errorEnXY("Tipo de la/s variable/s invalido");}
;

list_variables: id {setearTipo($1.sval);setearUso($1.sval,"Variable");$1.sval=TablaSimbolos.cambiarNombreKey($1.sval);}
		| list_variables ',' id {setearTipo($3.sval);setearUso($3.sval,"Variable");$3.sval=TablaSimbolos.cambiarNombreKey($3.sval);}
		| error {errorEnXY("Se esperaba un identificador o una lista de identificadores separados por ,");}
;

dec_funcion: header_funcion cola_funcion
		| header_funcion parametro {llamadasFunciones.get($1.sval).setPar1($2.sval);} cola_funcion 
		| header_funcion parametro ',' parametro {
													InvocacionFuncion f = llamadasFunciones.get($1.sval);
													f.setPar1($2.sval); 
													f.setPar2($4.sval);
												} 
												cola_funcion 
;

header_funcion: fun id '(' {setearUso($2.sval,"Nombre de Funcion");
							String aux=TablaSimbolos.cambiarNombreKey($2.sval);
							Ambito.addAmbito($2.sval);
							$2.sval=aux;
							tokens.push(TablaSimbolos.getSimbolo($2.sval));
							Terceto terAux = new Terceto("BI","","_");
							tercetosAux.push(terAux);
							ListaTercetos.addTerceto(terAux);
							InvocacionFuncion f = new InvocacionFuncion();
							f.setTercetoInv("["+(Terceto.GetIndice())+"]");
							llamadasFunciones.put($2.sval,f);
							terAux = new Terceto("MOV","EBX",String.valueOf(Terceto.GetIndice()));
							ListaTercetos.addTerceto(terAux);
							ListaTercetos.addTerceto(new Terceto("POP","DX","_"));
							$$.sval=$2.sval;}
		| fun '(' {errorEnXY("La declaracion de la funcion necesita un nombre");}
;

cola_funcion: ')' {ListaTercetos.addTerceto(new Terceto("PUSH","DX","_"));} ':' tipo '{' cuerpo_fun '}' {TokenLexema tokenAux=tokens.pop();
												if(tokenAux!=null){
													Ambito.removeAmbito();
													tokenAux.setTipo($4.sval);
													verificarTipos(tokenAux.getLexema().toString(),$6.sval);
													ListaTercetos.addTerceto(new Terceto("POP","DX","_"));
													Terceto terAux=new Terceto("Push",$6.sval,"_");
													ListaTercetos.addTerceto(terAux);
													ListaTercetos.addTerceto(new Terceto("PUSH","DX","_"));
													//Ambito.removeAmbito();
													terAux=new Terceto("RET","_","_");
													ListaTercetos.addTerceto(terAux);
													terAux=tercetosAux.pop();
													terAux.setSarg("["+Terceto.GetIndice()+"]");
												}}
		| error {errorEnXY("En la declaracion de la funcion falta: ),:,{ o }");}
;

parametro: tipo id {setearTipo($2.sval,$1.sval);
					setearUso($2.sval,"Nombre de Parametro");
					$$.sval=TablaSimbolos.cambiarNombreKey($2.sval);
					variablesInicializadas.add($$.sval);
					ListaTercetos.addTerceto(new Terceto("Pop",$$.sval,"_"));}
		| tipo {errorEnXY("Identificador del parametro esperado  en la declaracion de funcion");}
;

cuerpo_fun: sentencia Return '(' expresion ')' ';' {$$.sval=$4.sval; }
		| sentencia Return '(' expresion ';' {$$.sval=$4.sval;errorEnXY("Parentesis esperados al final de la expresion");}
		| sentencia Return expresion ';' {$$.sval=$3.sval;errorEnXY("Parentesis esperados al comienzo y final de la expresion");}
		| sentencia Return expresion ')' ';' {$$.sval=$3.sval;errorEnXY("Parentesis esperados al final de la expresion");}
		| sentencia Return expresion {$$.sval=$3.sval;errorEnXY("Parentesis esperados al comienzo y final de la expresion y ; al final de linea");}
		| sentencia Return '(' expresion ')'  {$$.sval=$4.sval;errorEnXY("; esperados al final de linea");}
;

//REGLAS PARA LAS SENTENCIAS EJECUTABLES
ejecutable: inst_ejecutable
		| defer {ListaTercetos.setDefer(true); diferido=true;} inst_ejecutable {ListaTercetos.setDefer(false); diferido=false; imprimirMSGEstructura("Defer de instruccion ejecutable");}
; 

inst_ejecutable: asignacion ';' {imprimirMSGEstructura("Asignacion");}
		| seleccion ';' {imprimirMSGEstructura("Seleccion If");}
		| impresion ';' {imprimirMSGEstructura("Impresion a Consola");}
		| invocar_fun ';' {imprimirMSGEstructura("Invocacion de Funcion");}
		| for_continue {imprimirMSGEstructura("Loop For");}
;

asignacion: id SIMB_ASIGNACION expresion {comprobarBinding($1.sval,"Variable "+$1.sval+" no declarada");
											$1.sval=Ambito.getAmbito($1.sval);
											if($1.sval!=null){
												if (!diferido){
													if (Ambito.getNaming().equals(Ambito.getAmbitoDeVariable($1.sval))){
														variablesInicializadas.add($1.sval);
													}
												}else{
													if (!variablesInicializadas.contains($1.sval)){
														errorEnXY("Para asignar a una variable en una instruccion diferida primero inicialicela");
													}
												}
												verificarTipos($1.sval,$3.sval);
												if (TablaSimbolos.getSimbolo($1.sval).getUso().equals("Nombre de Parametro")){
													TablaSimbolos.getSimbolo(Ambito.getNombreAmbito()).setEsp(true);
												}
												ListaTercetos.addTerceto(new Terceto($2.sval,$1.sval,$3.sval));
											}}
		| id SIMB_ASIGNACION {errorEnXY("Expresion esperada despues de la asignacion");}
		| id ':' '=' expresion {errorEnXY("Operador de asignacion incorrecto, se esperaba -> =:");}
;

expresion: expresion '+' termino {verificarTipos($1.sval,$3.sval);
									$$.sval="["+Terceto.GetIndice()+"]";
									ListaTercetos.addTerceto(new Terceto($2.sval,$1.sval,$3.sval));}
		| expresion '-' termino {verificarTipos($1.sval,$3.sval);
									$$.sval="["+Terceto.GetIndice()+"]";
									ListaTercetos.addTerceto(new Terceto($2.sval,$1.sval,$3.sval));}
		| termino
;

termino: termino '*' factor {verificarTipos($1.sval,$3.sval);
							$$.sval="["+Terceto.GetIndice()+"]";
							ListaTercetos.addTerceto(new Terceto($2.sval,$1.sval,$3.sval));}
		| termino '/' factor {verificarTipos($1.sval,$3.sval);
							$$.sval="["+Terceto.GetIndice()+"]";
							ListaTercetos.addTerceto(new Terceto($2.sval,$1.sval,$3.sval));}
		| factor
;

factor: id {comprobarBinding($1.sval,"Variable "+$1.sval+" no declarada");
			$1.sval=Ambito.getAmbito($1.sval);
			if(!TablaSimbolos.getSimbolo($1.sval).getUso().equals("Nombre de Parametro"))
				comprobarInicializada($1.sval,Ambito.getNaming());
			$$.sval=$1.sval;}
		| cte
		| '-' cte {verificarRangoDoubleNegativo();
					$2.sval="-"+$2.sval;
					TablaSimbolos.addSimbolo(new TokenLexema(258, $2.sval,"f64"));
					$$.sval=$2.sval;}
		| retorno_funcion
;

retorno_funcion: id '(' ')' {comprobarBinding($1.sval,"Funcion "+$1.sval+" no declarada");
								if(Ambito.getAmbito($1.sval)!=null){
									comprobarParametrosFuncion($1.sval,0);
									$1.sval=Ambito.getAmbito($1.sval);
									$$.sval=Ambito.getAmbito($1.sval);
									Terceto terAux=new Terceto("Push","EBX","_");
									ListaTercetos.addTerceto(terAux);
									String tercetoLlamado = llamadasFunciones.get($1.sval).getTercetoInv();
									terAux=new Terceto("CMP","EBX",tercetoLlamado.substring(1,tercetoLlamado.length()-1));
									ListaTercetos.addTerceto(terAux);
									terAux=new Terceto("JE","ErrRec","_");
									ListaTercetos.addTerceto(terAux);
									terAux=new Terceto("CALL",tercetoLlamado,"_");
									ListaTercetos.addTerceto(terAux);
									terAux=new Terceto("Pop",$1.sval,"_");
									ListaTercetos.addTerceto(terAux);
									terAux=new Terceto("Pop","EBX","_");
									ListaTercetos.addTerceto(terAux);
								}}
		| id '(' parametro_real ')' {comprobarBinding($1.sval,"Funcion "+$1.sval+" no declarada");
									if(Ambito.getAmbito($1.sval)!=null){
										comprobarParametrosFuncion($1.sval,1);
										$1.sval=Ambito.getAmbito($1.sval);
										String par= Ambito.getAmbito($3.sval);
										if(par==null)
											par =$3.sval;
										verificarTipos(llamadasFunciones.get($1.sval).getPar1(),par);
										String tercetoLlamado = llamadasFunciones.get($1.sval).getTercetoInv();
										Terceto terAux=new Terceto("Push","EBX","_");
										ListaTercetos.addTerceto(terAux);
										terAux=new Terceto("CMP","EBX",tercetoLlamado.substring(1,tercetoLlamado.length()-1));
										ListaTercetos.addTerceto(terAux);
										ListaTercetos.addTerceto(new Terceto("JE","ErrRec","_"));
										ListaTercetos.addTerceto(new Terceto("Push",par,"_"));
										ListaTercetos.addTerceto(new Terceto("CALL",tercetoLlamado,"_"));
										ListaTercetos.addTerceto(new Terceto("Pop",$1.sval,"_"));
										terAux=new Terceto("Pop","EBX","_");
										ListaTercetos.addTerceto(terAux);
										$$.sval=$1.sval;
									}}
		| id '(' parametro_real ',' parametro_real ')' {comprobarBinding($1.sval,"Funcion "+$1.sval+" no declarada");
														if(Ambito.getAmbito($1.sval)!=null){
															comprobarParametrosFuncion($1.sval,2);
															$1.sval=Ambito.getAmbito($1.sval);
															String par1= Ambito.getAmbito($3.sval);
															if(par1==null)
																par1 =$3.sval;
															verificarTipos(llamadasFunciones.get($1.sval).getPar1(),par1);
															String par2= Ambito.getAmbito($5.sval);
															if(par2==null)
																par2 =$5.sval;
															verificarTipos(llamadasFunciones.get($1.sval).getPar2(),par2);
															String tercetoLlamado = llamadasFunciones.get($1.sval).getTercetoInv();
															Terceto terAux=new Terceto("Push","EBX","_");
															ListaTercetos.addTerceto(terAux);
															terAux = new Terceto("CMP","EBX",tercetoLlamado.substring(1,tercetoLlamado.length()-1));
															ListaTercetos.addTerceto(terAux);
															ListaTercetos.addTerceto(new Terceto("JE","ErrRec","_"));
															ListaTercetos.addTerceto(new Terceto("Push",par1,"_"));
															ListaTercetos.addTerceto(new Terceto("Push",par2,"_"));
															ListaTercetos.addTerceto(new Terceto("CALL",tercetoLlamado,"_"));
															ListaTercetos.addTerceto(new Terceto("Pop",$1.sval,"_"));
															terAux=new Terceto("Pop","EBX","_");
															ListaTercetos.addTerceto(terAux);
															$$.sval=Ambito.getAmbito($1.sval);
														}}
;

parametro_real: id {comprobarBinding($1.sval,"No se encontro el parametro "+$1.sval);
					if(Ambito.getAmbito($1.sval)!=null)
						comprobarInicializada(Ambito.getAmbito($1.sval),Ambito.getNaming());}
		| cte
		| '-' cte {verificarRangoDoubleNegativo();
					$2.sval="-"+$2.sval;TablaSimbolos.addSimbolo(new TokenLexema(258, $2.sval,"f64"));
					$$.sval=$2.sval;}
;

seleccion: If condicion_if cuerpo_if {ListaTercetos.add_seleccion_final();}
;

cuerpo_if: then_selec Else else_selecc end_if
		| then_selec end_if
;

then_selec: then '{' ejecutable_for '}' {ListaTercetos.add_seleccion_then();}
		| then inst_ejecutable_for {ListaTercetos.add_seleccion_then();}
;

else_selecc: '{' ejecutable_for '}'
		| inst_ejecutable_for
;

condicion_if: '(' expresion_booleana ')' {ListaTercetos.add_seleccion_cond();}
;

expresion_booleana: expresion comparador expresion {verificarTipos($1.sval,$3.sval);
													ListaTercetos.addTerceto(new Terceto($2.sval,$1.sval,$3.sval));}
;

comparador: SIMB_DISTINTO
		| SIMB_MAY_IGUAL
		| SIMB_MEN_IGUAL
		| '='
		| '<'
		| '>'
;

impresion: out '(' cadena ')' {ListaTercetos.addTerceto(new Terceto($1.sval,$3.sval,"_"));}
		| out cadena ')' {errorEnXY("Falta de parentesis al comienzo de la cadena del out");}
		| out '(' cadena {errorEnXY("Falta de parentesis al final de la cadena del out");}
;

invocar_fun: discard retorno_funcion
		| retorno_funcion {errorEnXY("Funcion invocada sin discard del retorno");}
;

for_continue: For '(' for_inic ';' for_cond ';' for_act ')' for_cuerpo {Ambito.removeAmbito();
																		if($3.sval!=null){
																			verificarIdIguales($3.sval,$5.sval);
																			verificarTipos($3.sval,$5.sval);
																			verificarTipos($3.sval,$7.sval);
																		}}
		| etiqueta For '(' for_inic ';' for_cond ';' for_act ')' for_cuerpo {Ambito.removeAmbito();
																			if($4.sval!=null){
																				verificarIdIguales($4.sval,$6.sval);
																				verificarTipos($4.sval,$6.sval);
																				verificarTipos($4.sval,$8.sval);
																				if (tercetosBreakET.containsKey($1.sval)){
																					List<Terceto> aux = tercetosBreakET.get($1.sval);
																					tercetosBreakET.remove($1.sval);
																					for (int i = 0; i<aux.size();i++){
																						aux.get(i).setSarg("["+Terceto.GetIndice()+"]");
																					}
																				}
																			}}
;

etiqueta: id ':' {setearUso($1.sval,"Etiqueta");
					$$.sval=TablaSimbolos.cambiarNombreKey($1.sval);}
;

for_inic: id SIMB_ASIGNACION cte {$1.sval=Ambito.getAmbito($1.sval);
									if (!variablesInicializadas.contains($1.sval))
										variablesInicializadas.add($1.sval);
									verificarEntero($1.sval);
									verificarTipos($1.sval,$3.sval);
									ListaTercetos.addTerceto(new Terceto($2.sval,$1.sval,$3.sval));
									id_for_act.push($1.sval);
									$$.sval=$1.sval;}
;

for_cond: id comparador expresion {$1.sval=Ambito.getAmbito($1.sval);
									verificarTipos($1.sval,$3.sval);
									ListaTercetos.addTerceto(new Terceto($2.sval,$1.sval,$3.sval));
									ListaTercetos.add_seleccion_cond();
									$$.sval=$1.sval;}
;

for_act: mas_o_menos cte {$$.sval= $2.sval;
						ListaTercetos.add_for_act(id_for_act.pop(),$1.sval,$2.sval);
						Ambito.addAmbito("for");}
		| cte {errorEnXY("Falta +/- para actualizar for");
				ListaTercetos.add_for_act(id_for_act.pop(),"+",$1.sval);
				Ambito.addAmbito("for");}
;

for_cuerpo: '{' ejecutable_for '}' ';' {ListaTercetos.add_for_cpo();
										if (tercetosContinue.containsKey(Ambito.getNaming())){
											List<Terceto> aux = tercetosContinue.get(Ambito.getNaming());
											tercetosContinue.remove(Ambito.getNaming());
											for (int i = 0; i<aux.size();i++){
												aux.get(i).setSarg("["+(Terceto.GetIndice()-3)+"]");
											}
										}
										if (tercetosBreak.containsKey(Ambito.getNaming())){
											List<Terceto> aux = tercetosBreak.get(Ambito.getNaming());
											tercetosBreak.remove(Ambito.getNaming());
											for (int i = 0; i<aux.size();i++){
												aux.get(i).setSarg("["+Terceto.GetIndice()+"]");
											}
										}}
		| inst_ejecutable_for {ListaTercetos.add_for_cpo();
								if (tercetosContinue.containsKey(Ambito.getNaming())){
									List<Terceto> aux = tercetosContinue.get(Ambito.getNaming());
									tercetosContinue.remove(Ambito.getNaming());
									for (int i = 0; i<aux.size();i++){
										aux.get(i).setSarg("["+(Terceto.GetIndice()-3)+"]");
									}
								}
								if (tercetosBreak.containsKey(Ambito.getNaming())){
									List<Terceto> aux = tercetosBreak.get(Ambito.getNaming());
									tercetosBreak.remove(Ambito.getNaming());
									for (int i = 0; i<aux.size();i++){
										aux.get(i).setSarg("["+Terceto.GetIndice()+"]");
									}
								}}
;

mas_o_menos: '+'
		| '-'
;

ejecutable_for: ejecutable_for inst_ejecutable_for
		| inst_ejecutable_for
;

inst_ejecutable_for: inst_ejecutable
		| Break ';' {Terceto brk=new Terceto("BI","_","_");
					if (tercetosBreak.containsKey(Ambito.getNaming())){
						tercetosBreak.get(Ambito.getNaming()).add(brk);
						ListaTercetos.addTerceto(brk);
					}else{
						List<Terceto> aux = new ArrayList<Terceto>();
						aux.add(brk);
						tercetosBreak.put(Ambito.getNaming(),aux);
						ListaTercetos.addTerceto(brk);
					}}
		| Break ':' id ';' {String amb = Ambito.getAmbito($3.sval);
							if (amb!=null){
								Terceto brk=new Terceto("BI","_","_");
								if (tercetosBreakET.containsKey(amb)){
									tercetosBreakET.get(amb).add(brk);
									ListaTercetos.addTerceto(brk);
								}else{
									List<Terceto> aux = new ArrayList<Terceto>();
									aux.add(brk);
									tercetosBreakET.put(amb,aux);
									ListaTercetos.addTerceto(brk);
								}
							} else {
								errorEnXY("Etiqueta "+$3.sval+" no declarada");
							}}
		| Continue ';' {Terceto cont=new Terceto("BI","_","_");
						if (tercetosContinue.containsKey(Ambito.getNaming())){
							tercetosContinue.get(Ambito.getNaming()).add(cont);
							ListaTercetos.addTerceto(cont);
						}else{
							List<Terceto> aux =new ArrayList<Terceto>();
							aux.add(cont);
							tercetosContinue.put(Ambito.getNaming(),aux);
							ListaTercetos.addTerceto(cont);
						}}
;

%%

public static final String ANSI_RESET ="\u001B[0m";
public static final String ANSI_RED = "\u001B[31m";
public static final String ANSI_GREEN = "\u001B[32m";
public static final String ANSI_YELLOW = "\u001B[33m";
public static final String ANSI_BLUE = "\u001B[34m";
public static final String ANSI_PURPLE ="\u001B[35m";
public static final String ANSI_CYAN = "\u001B[36m";

public Vector<String> for_ids = new Vector<String>();
public String tipoAux="";
public Stack<TokenLexema> tokens= new Stack<TokenLexema>();
public Stack<Terceto> tercetosAux = new Stack<Terceto>();
public Boolean verb=AnalizadorLexico.getVerbose();
public Stack<String> id_for_act = new Stack<String>();
public HashMap<String, InvocacionFuncion> llamadasFunciones = new HashMap<String,InvocacionFuncion>();
public HashMap<String,List<Terceto>> tercetosContinue = new HashMap<String,List<Terceto>>();
public HashMap<String,List<Terceto>> tercetosBreak = new HashMap<String,List<Terceto>>();
public HashMap<String,List<Terceto>> tercetosBreakET = new HashMap<String,List<Terceto>>();
public List<String> erroresDump=new ArrayList<String>();
public List<String> warningsDump=new ArrayList<String>();
public List<String> variablesInicializadas=new ArrayList<String>();
public Boolean diferido=false;

public void comprobarInicializada(String arg,String ambito){
    ambito=ambito.replace("for:", "");
    String aux = Ambito.getAmbitoDeVariable(arg).substring(1);
	if (aux.equals(ambito.substring(1)) && !variablesInicializadas.contains(arg))
		errorEnXY("La variable "+arg+" falta ser inicializada");
}

public void comprobarParametrosFuncion(String nomFuncionInvocada,Integer x){
    InvocacionFuncion aux = llamadasFunciones.get(Ambito.getAmbito(nomFuncionInvocada));
    if (aux.getPar1().equals("") && x==0)
        return;
    else if (!aux.getPar1().equals("") && aux.getPar2().equals("") && x==1)
        return;
    else if (!aux.getPar1().equals("") && !aux.getPar2().equals("") && x==2)
        return;
    errorEnXY("La funcion "+nomFuncionInvocada+" fue invocada con un numero de parametros incorrecto");
}

public void comprobarBinding(String arg, String text){
	String aux = Ambito.getAmbito(arg);
	if (aux == null)
		errorEnXY(text);
}

public void setearTipo(String arg){
	TokenLexema x = TablaSimbolos.getSimbolo(arg);
	x.setTipo(tipoAux);
}

public void setearTipo(String arg1, String arg2){
	TokenLexema x = TablaSimbolos.getSimbolo(arg1);
	x.setTipo(arg2);
}

public void verificarEntero(String arg){
	if (TablaSimbolos.getSimbolo(arg) == null){
		if(arg!=null)
			errorEnXY("Variable "+arg+" no declarada.");
		else
			errorEnXY("Variable no declarada.");
		return;
	}
	if (TablaSimbolos.getSimbolo(arg).getTipo().equals("ui8")){
		return;
	}
	errorEnXY("Se requiere que "+arg+" sea de un tipo entero");
}

public void setearUso(String arg, String arg2){
	TokenLexema x = TablaSimbolos.getSimbolo(arg);
	x.setUso(arg2);
}

public void verificarTipos(String arg1,String arg2){
	if (arg1==null||arg2==null||arg1==""||arg2=="") return;
	String aux1 = arg1;
	while (aux1.startsWith("[")){
		aux1=Terceto.GetTerceto(aux1).getSarg();
	}
	String aux2=arg2;
	while (aux2.startsWith("[")){
		aux2=Terceto.GetTerceto(aux2).getSarg();
	}
	if (TablaSimbolos.getSimbolo(aux1).getTipo().equals(TablaSimbolos.getSimbolo(aux2).getTipo()))
		return;
	errorEnXY("No se puede realizar una operacion entre "+arg1+" y "+arg2);
}

public void errorEnXY(String msg){
	int linea,caracter=0;
	linea = AnalizadorLexico.getLinea();
	caracter = AnalizadorLexico.getCaracter();
	yynerrs++;
	erroresDump.add(ANSI_RED+"!|!|!|! Error en linea: "+linea+", caracter: "+caracter+". Errores hasta ahora: "+yynerrs+"\n"+msg+"\n"+ANSI_RESET);
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
	if (verb){
		int linea,caracter=0;
		linea = AnalizadorLexico.getLinea();
		caracter = AnalizadorLexico.getCaracter();
		warningsDump.add(ANSI_YELLOW+"!|/|/|! Warning en linea: "+linea+", caracter: "+caracter+"\n"+msg+"\n"+ANSI_RESET);
	}
}

private void imprimirMSGEstructura(String msg){
	if (verb){
		int linea=0;
		linea = AnalizadorLexico.getLinea();
		System.out.println(ANSI_BLUE+"/|/|/|/: "+msg+" termina de reconocerse en la linea: "+linea+".\n"+ANSI_RESET);
	}
}

private void programaListo(){
	if (yynerrs!=0){
		System.out.println(ANSI_RED+"!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!\n"+ANSI_RESET);
		System.out.println(ANSI_RED+"!|!|!|!: El programa encontro "+yynerrs+" errores al compilarse.\n"+ANSI_RESET);
		System.out.println(ANSI_RED+"!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!/!\n\n"+ANSI_RESET);
		for (int i = 0; i<erroresDump.size();i++){
			System.out.println(erroresDump.get(i));
		}
		return;
	}
	System.out.println(ANSI_GREEN+"|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|\n"+ANSI_RESET);
	System.out.println(ANSI_GREEN+"%|%|%|%: El programa compilo sin errores.\n"+ANSI_RESET);
	System.out.println(ANSI_GREEN+"|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|%|\n\n"+ANSI_RESET);
	for (int i = 0; i<warningsDump.size();i++){
			System.out.println(warningsDump.get(i));
		}
	ListaTercetos.imprimir();
	GestorAssembler.procesarArchivo();
	TablaSimbolos.imprimirTabla();
}

private int yylex(){
	int rta=0;
    rta=AnalizadorLexico.siguienteToken();
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