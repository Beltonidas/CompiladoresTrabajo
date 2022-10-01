//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
package compilador;

import java.io.*;
//#line 21 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short id=257;
public final static short cte=258;
public final static short cadena=259;
public final static short if=260;
public final static short then=261;
public final static short else=262;
public final static short end_if=263;
public final static short out=264;
public final static short fun=265;
public final static short return=266;
public final static short break=267;
public final static short ui8=268;
public final static short f64=269;
public final static short SIMB_ASIGNACION=270;
public final static short SIMB_MAY_IGUAL=271;
public final static short SIMB_MEN_IGUAL=272;
public final static short SIMB_DISTINTO=273;
public final static short SIMB_COM_I=274;
public final static short SIMB_COM_F=275;
public final static short discard=276;
public final static short for=277;
public final static short continue=278;
public final static short defer=279;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    2,    2,    4,    4,    5,    5,
    7,    7,    8,    8,    6,    6,    6,    9,   10,   11,
   12,    3,    3,    3,    3,   14,   14,   14,   14,   14,
   15,   15,   13,   13,   13,   20,   20,   20,   21,   21,
   21,   21,   21,   16,   16,   23,   23,   24,   24,   22,
   27,   28,   28,   28,   28,   28,   28,   17,   18,   19,
   19,   19,   19,   29,   29,   25,   25,   26,   26,   26,
   26,
};
final static short yylen[] = {                            2,
    2,    1,    4,    4,    3,    3,    1,    1,    4,    3,
    1,    1,    1,    3,    2,    3,    5,    3,    6,    2,
    6,    2,    1,    3,    2,    2,    2,    2,    2,    2,
    3,    5,    3,    3,    1,    3,    3,    1,    1,    1,
    3,    4,    6,    5,    6,    4,    2,    3,    1,    3,
    3,    1,    1,    1,    1,    1,    1,    4,    4,   16,
   14,   18,   16,    1,    1,    2,    1,    1,    2,    4,
    2,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   11,   12,    0,    0,    0,    0,    0,    0,    8,    0,
    0,   23,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   25,    0,    5,   22,
    6,    0,   13,    0,    0,    0,   15,    0,   26,   27,
   28,   29,   30,    0,    0,    0,   40,    0,    0,    0,
   38,    0,    0,    0,    0,    0,    0,   18,    0,    0,
   24,    0,   10,    0,    0,   20,    0,   16,    3,    4,
    0,    0,    0,    0,    0,    0,    0,   53,   54,   52,
   55,   56,   57,    0,   50,    0,    0,    0,   68,   47,
    0,    0,    0,   49,   58,   59,    0,    9,   14,    0,
    0,   41,    0,   32,    0,    0,   36,   37,    0,    0,
   69,    0,   71,    0,   67,   44,    0,    0,    0,    0,
   17,    0,   42,    0,    0,   46,   66,   48,   45,    0,
    0,    0,    0,    0,   70,    0,    0,   19,   43,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   64,
   65,    0,   21,    0,    0,    0,    0,    0,    0,   61,
    0,    0,    0,   63,   60,    0,   62,
};
final static short yydgoto[] = {                          2,
    3,    5,   16,   17,   18,   19,   20,   44,   21,   47,
   48,  142,   59,   99,   23,   24,   25,   26,   27,   60,
   61,   32,   66,  103,  124,  125,   64,   94,  162,
};
final static short yysindex[] = {                      -219,
    0,    0,  -79, -150,  -64, -196,   23,   58,   88, -173,
    0,    0,   91,  104, -125,   -3,   12, -157,    0, -112,
  -30,    0,   87,   89,   96,  101,  102, -150,  -32, -121,
 -141,  -99,  -96,  124, -141,  -87,    0, -125,    0,    0,
    0, -112,    0,  -11,  116,  -82,    0,   24,    0,    0,
    0,    0,    0,   11,   59,  143,    0, -141,   61,   52,
    0,  146,   -6,  151,  -88, -110,  154,    0,  155,  -67,
    0,    5,    0,  -52, -157,    0, -157,    0,    0,    0,
  -24,   60, -141, -141, -141, -141,  -51,    0,    0,    0,
    0,    0,    0, -141,    0,   66,  149, -181,    0,    0,
  150, -181,  -50,    0,    0,    0,  -44,    0,    0,   92,
  178,    0,   44,    0,   52,   52,    0,    0,  -49,   61,
    0,  -39,    0,   34,    0,    0,   40,  163,  164,  -79,
    0, -141,    0,  -34,  168,    0,    0,    0,    0,  -29,
 -108,  118,  188,  185,    0,  -20,  205,    0,    0,  -10,
 -141, -141,  -20,    2,   97, -141,   98,  187,   32,    0,
    0,   -8,    0,   98,  207,    1,  -66,  208, -181,    0,
  -60,   62, -181,    0,    0,   77,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  255,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  133,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -41,    0,    0,  201,  -36,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -31,   -9,    0,    0,    0,  228,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  142,  249,  250,    0,    0,   25,  237,    0,  -21,
  203,    0,   29,   54,    0,    0,    0,    0,    0,   37,
  -19,    0,    0,    0,  -80,    6,    0,  -73,  117,
};
final static int YYTABLESIZE=355;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         39,
   39,   39,   39,   39,   35,   39,   35,   58,   35,   33,
   45,   33,  102,   33,   28,   69,  112,   39,   39,   39,
   39,  127,   35,   35,   35,   35,   78,   33,   33,   33,
   33,   34,   74,   34,   98,   34,   83,    1,   84,   92,
   91,   93,   42,    4,   83,   46,   84,   73,   74,   34,
   34,   34,   34,   92,   91,   93,  169,   22,   28,   63,
  157,  113,  173,  108,   45,  117,  118,   77,   37,   40,
  100,  104,  151,   29,   83,    6,   84,    7,    8,  156,
   30,   22,    9,   34,  133,   96,   82,  132,  172,  131,
  164,   71,  176,   85,   13,   14,   97,   31,   86,  110,
  114,   46,   83,   83,   84,   84,    6,   40,    7,    8,
   11,   12,  143,    9,   10,   56,   57,   11,   12,  115,
  116,   39,  120,  122,  121,   13,   14,   33,   15,  137,
   35,    6,  137,    7,    8,   79,   41,  158,    9,   83,
  160,   84,  161,   36,   43,   49,    6,   50,    7,    8,
   13,   14,  101,    9,   51,   62,   96,  147,  136,   52,
   53,   65,   67,   68,  138,   13,   14,   97,    6,   70,
    7,    8,  170,   75,   76,    9,  174,  137,   96,  154,
  155,  137,   81,   80,  159,   87,  175,   13,   14,   97,
    6,   95,    7,    8,  105,  106,    6,    9,    7,    8,
   96,  177,  107,    9,  109,  119,   96,  123,  126,   13,
   14,   97,  128,  129,  130,   13,   14,   97,   45,  135,
  134,  139,  140,  144,   56,   57,  145,  146,  149,   39,
   39,   39,   56,   57,   35,   35,   35,   11,   12,   33,
   33,   33,  148,  150,  152,  163,  153,  167,  171,  165,
   88,   89,   90,    6,    1,    7,    8,    7,  168,   31,
    9,   34,   34,   34,   88,   89,   90,    6,   51,    7,
    8,  141,   13,   14,    9,   38,   54,   55,   72,  111,
  166,    0,    0,    0,    0,    0,   13,   14,    0,   38,
    6,    0,    7,    8,    0,    0,    6,    9,    7,    8,
   96,    0,    0,    9,    0,    0,   96,    0,    0,   13,
   14,   97,    0,    0,    0,   13,   14,   97,    6,    0,
    7,    8,    0,    0,    0,    9,    0,    0,   96,    0,
    0,    0,    0,    6,    0,    7,    8,   13,   14,   97,
    9,    0,    0,   96,    0,    0,    0,    0,    0,    0,
    0,    0,   13,   14,   97,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   41,   47,   43,   40,   45,   41,
   41,   43,  123,   45,  123,   35,   41,   59,   60,   61,
   62,  102,   59,   60,   61,   62,   48,   59,   60,   61,
   62,   41,   44,   43,  123,   45,   43,  257,   45,   60,
   61,   62,   18,  123,   43,   21,   45,   59,   44,   59,
   60,   61,   62,   60,   61,   62,  123,    4,  123,   31,
   59,   81,  123,   59,   41,   85,   86,   44,   15,   16,
   65,   66,  146,  270,   43,  257,   45,  259,  260,  153,
   58,   28,  264,  257,   41,  267,   58,   44,  169,  111,
   59,   38,  173,   42,  276,  277,  278,   40,   47,   75,
   41,   77,   43,   43,   45,   45,  257,   54,  259,  260,
  268,  269,  132,  264,  265,  257,  258,  268,  269,   83,
   84,  125,   94,   58,   59,  276,  277,   40,  279,  124,
   40,  257,  127,  259,  260,  125,  125,   41,  264,   43,
   43,   45,   45,   40,  257,   59,  257,   59,  259,  260,
  276,  277,  263,  264,   59,  277,  267,  266,  125,   59,
   59,  261,  259,   40,  125,  276,  277,  278,  257,  257,
  259,  260,  167,   58,  257,  264,  171,  172,  267,  151,
  152,  176,   40,  125,  156,   40,  125,  276,  277,  278,
  257,   41,  259,  260,   41,   41,  257,  264,  259,  260,
  267,  125,  270,  264,  257,  257,  267,   59,   59,  276,
  277,  278,  263,  258,  123,  276,  277,  278,   41,  259,
  270,   59,   59,  258,  257,  258,   59,  257,   41,  271,
  272,  273,  257,  258,  271,  272,  273,  268,  269,  271,
  272,  273,  125,   59,   40,   59,  257,   41,   41,  258,
  271,  272,  273,  257,    0,  259,  260,  125,  258,   59,
  264,  271,  272,  273,  271,  272,  273,  257,   41,  259,
  260,  130,  276,  277,  264,  279,   28,   28,   42,   77,
  164,   -1,   -1,   -1,   -1,   -1,  276,  277,   -1,  279,
  257,   -1,  259,  260,   -1,   -1,  257,  264,  259,  260,
  267,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,  276,
  277,  278,   -1,   -1,   -1,  276,  277,  278,  257,   -1,
  259,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,
   -1,   -1,   -1,  257,   -1,  259,  260,  276,  277,  278,
  264,   -1,   -1,  267,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  276,  277,  278,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=279;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"id","cte","cadena","if","then","else",
"end_if","out","fun","return","break","ui8","f64","SIMB_ASIGNACION",
"SIMB_MAY_IGUAL","SIMB_MEN_IGUAL","SIMB_DISTINTO","SIMB_COM_I","SIMB_COM_F",
"discard","for","continue","defer",
};
final static String yyrule[] = {
"$accept : programa",
"programa : nombre_programa sentencia",
"nombre_programa : id",
"sentencia : sentencia '{' ejecutiva '}'",
"sentencia : sentencia '{' declarativa '}'",
"sentencia : '{' ejecutiva '}'",
"sentencia : '{' declarativa '}'",
"declarativa : dec_variables",
"declarativa : dec_funcion",
"dec_variables : dec_variables tipo list_variables ';'",
"dec_variables : tipo list_variables ';'",
"tipo : ui8",
"tipo : f64",
"list_variables : id",
"list_variables : list_variables ',' id",
"dec_funcion : header_funcion cola_funcion",
"dec_funcion : header_funcion parametro cola_funcion",
"dec_funcion : header_funcion parametro ',' parametro cola_funcion",
"header_funcion : fun id '('",
"cola_funcion : ')' ':' tipo '{' cuerpo_fun '}'",
"parametro : tipo id",
"cuerpo_fun : sentencia return '(' expresion ')' ';'",
"ejecutiva : ejecutiva inst_ejecutiva",
"ejecutiva : inst_ejecutiva",
"ejecutiva : ejecutiva defer inst_ejecutiva",
"ejecutiva : defer inst_ejecutiva",
"inst_ejecutiva : asignacion ';'",
"inst_ejecutiva : seleccion ';'",
"inst_ejecutiva : impresion ';'",
"inst_ejecutiva : invocar_fun ';'",
"inst_ejecutiva : for_continue ';'",
"asignacion : id SIMB_ASIGNACION expresion",
"asignacion : id SIMB_ASIGNACION '(' expresion ')'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : id",
"factor : cte",
"factor : id '(' ')'",
"factor : id '(' factor ')'",
"factor : id '(' factor ',' factor ')'",
"seleccion : if condicion_if then_selec end_if ';'",
"seleccion : if condicion_if then_selec else_selecc end_if ';'",
"then_selec : then '{' ejecutiva_for '}'",
"then_selec : then inst_ejecutiva_for",
"else_selecc : '{' ejecutiva_for '}'",
"else_selecc : inst_ejecutiva_for",
"condicion_if : '(' expresion_booleana ')'",
"expresion_booleana : expresion comparador expresion",
"comparador : SIMB_DISTINTO",
"comparador : SIMB_MAY_IGUAL",
"comparador : SIMB_MEN_IGUAL",
"comparador : '='",
"comparador : '<'",
"comparador : '>'",
"impresion : out '(' cadena ')'",
"invocar_fun : discard '(' factor ')'",
"for_continue : for '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' '{' ejecutiva_for '}'",
"for_continue : for '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' inst_ejecutiva_for",
"for_continue : cadena ':' for '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' '{' ejecutiva_for '}'",
"for_continue : cadena ':' for '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' inst_ejecutiva_for",
"mas_o_menos : '+'",
"mas_o_menos : '-'",
"ejecutiva_for : ejecutiva_for inst_ejecutiva_for",
"ejecutiva_for : inst_ejecutiva_for",
"inst_ejecutiva_for : inst_ejecutiva",
"inst_ejecutiva_for : break ';'",
"inst_ejecutiva_for : break ':' cadena ';'",
"inst_ejecutiva_for : continue ';'",
};

//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
