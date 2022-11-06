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
package Compilador;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import GeneracionTercetos.*;
//#line 24 "Parser.java"




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
public final static short If=260;
public final static short then=261;
public final static short Else=262;
public final static short end_if=263;
public final static short out=264;
public final static short fun=265;
public final static short Return=266;
public final static short Break=267;
public final static short ui8=268;
public final static short f64=269;
public final static short discard=270;
public final static short For=271;
public final static short Continue=272;
public final static short defer=273;
public final static short SIMB_ASIGNACION=274;
public final static short SIMB_MAY_IGUAL=275;
public final static short SIMB_MEN_IGUAL=276;
public final static short SIMB_DISTINTO=277;
public final static short SIMB_COM_I=278;
public final static short SIMB_COM_F=279;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    2,    2,    2,    3,    3,    3,
    3,    4,    4,    6,    6,    8,    8,    8,    9,    9,
    9,    7,    7,    7,   10,   10,   11,   11,   12,   12,
   13,   13,   13,   13,   13,   13,    5,    5,   15,   15,
   15,   15,   15,   16,   16,   16,   14,   14,   14,   21,
   21,   21,   22,   22,   22,   22,   23,   23,   23,   24,
   24,   24,   17,   26,   26,   27,   27,   28,   28,   25,
   31,   32,   32,   32,   32,   32,   32,   18,   18,   18,
   19,   19,   20,   20,   33,   34,   35,   35,   36,   36,
   37,   37,   29,   29,   30,   30,   30,   30,
};
final static short yylen[] = {                            2,
    2,    1,    1,    1,    3,    2,    2,    1,    1,    2,
    2,    1,    1,    3,    2,    1,    1,    1,    1,    3,
    1,    2,    3,    5,    3,    2,    6,    1,    2,    1,
    6,    5,    4,    5,    3,    5,    1,    2,    2,    2,
    2,    2,    1,    3,    2,    4,    3,    3,    1,    3,
    3,    1,    1,    1,    2,    1,    3,    4,    6,    1,
    1,    2,    3,    4,    2,    4,    2,    3,    1,    3,
    3,    1,    1,    1,    1,    1,    1,    4,    3,    3,
    2,    1,    9,   11,    3,    3,    2,    1,    4,    1,
    1,    1,    2,    1,    1,    2,    4,    2,
};
final static short yydefred[] = {                         0,
    2,    3,    4,    0,    0,   21,    0,    0,    0,    0,
   16,   17,    0,    0,    0,    0,    1,    0,    8,    9,
   12,   13,    0,    0,    0,   37,    0,    0,    0,    0,
   43,   82,    0,    0,    0,    0,    0,    0,    0,    0,
   26,    0,   81,    0,    0,   38,    0,    7,   10,   11,
   19,    0,   15,    0,   28,   18,    0,    0,   22,    0,
   39,   40,   41,   42,    0,   54,    0,    0,    0,   52,
   56,   60,   61,   57,    0,    0,    0,    0,    0,    0,
    0,   63,    0,   79,    0,   25,    0,    0,    5,   14,
   20,    0,   29,    0,   23,   55,    0,    0,    0,    0,
   62,    0,   58,    0,    0,   73,   74,   72,   75,   76,
   77,    0,   70,    0,    0,    0,   95,   67,    0,   65,
   78,    0,    0,    0,    0,    0,    0,   50,   51,    0,
    0,    0,   96,    0,   98,    0,   94,    0,    0,   69,
   85,    0,    0,    0,   24,   59,    0,    0,   66,   93,
    0,   64,    0,    0,    0,    0,    0,   97,   68,    0,
   88,   91,   92,    0,    0,    0,   27,    0,    0,   87,
    0,    0,    0,    0,   90,   83,    0,   33,    0,    0,
    0,   32,    0,   34,   84,    0,   31,   89,
};
final static short yydgoto[] = {                          4,
    5,   17,   18,   19,   20,   21,   22,   23,   24,   25,
   59,   60,  156,   68,  117,   27,   28,   29,   30,   31,
   69,   70,   32,   76,   37,   82,   83,  139,  136,  137,
   80,  112,   88,  143,  164,  176,  165,
};
final static short yysindex[] = {                      -166,
    0,    0,    0,    0, -101,    0,  -25,  -13,  -32,   -5,
    0,    0, -162,   59, -118,  105,    0,  -81,    0,    0,
    0,    0, -136,   41,  -38,    0,   48,   74,   78,   79,
    0,    0,   -7,    2,  -48,   -7, -148,   99, -108,  120,
    0,  121,    0,  -95,  -25,    0,   40,    0,    0,    0,
    0,   46,    0,  -91,    0,    0,  113,  -84,    0,    5,
    0,    0,    0,    0,  121,    0,  -80,   63,   62,    0,
    0,    0,    0,    0,  -78,   73,  134,   -7,   -6,  136,
  -56,    0, -139,    0,  140,    0,  -88,  137,    0,    0,
    0, -181,    0, -181,    0,    0,   -7,   -7,   -7,   -7,
    0,   19,    0,  -95,   63,    0,    0,    0,    0,    0,
    0,   -7,    0,   76,  138, -145,    0,    0,   22,    0,
    0,  -65,  -62,   82,  -24,   62,   62,    0,    0,  161,
  148,   63,    0,  -45,    0,   57,    0, -145,  -50,    0,
    0,  -20,  158,  105,    0,    0,  -62,  162,    0,    0,
   66,    0,   -7,   14,   84,   95,  163,    0,    0,   63,
    0,    0,    0,  183,  -21,  -29,    0,   14,   31,    0,
   -7,   37,  184, -145,    0,    0,   38,    0,  167,   31,
   75,    0,  174,    0,    0,  179,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,   18,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  188,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  243,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   17,    0,    0,
    0,    0,    0,    0,  -41,    0,    0,  189,  -36,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  194,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  195,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -31,   -9,    0,    0,    0,
    0,  217,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  203,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  139,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  153,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   50,   51,   54,    0,    0,   49,  240,    0,
    3,  171,    0,  206,   55,    0,    0,    0,    0,    0,
   52,   58,   32,  178,    0,    0,    0,    0,  -63,  -33,
    0,  141,  177,  143,  117,  107,    0,
};
final static int YYTABLESIZE=378;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         53,
   53,   53,   57,   53,   49,   53,   49,   39,   49,   47,
  171,   47,   78,   47,   34,   67,   57,   53,   53,   53,
   53,   16,   49,   49,   49,   49,   36,   47,   47,   47,
   47,   48,   35,   48,   41,   48,   97,   67,   98,  110,
  109,  111,   74,   48,   43,   57,   75,  118,   94,   48,
   48,   48,   48,  110,  109,  111,  162,   30,  163,   26,
   30,   19,   95,   75,   71,   47,  116,   71,   49,   46,
   26,   50,   26,   58,  151,   56,   19,  179,  183,   97,
   97,   98,   98,   53,   54,  140,   11,   12,   49,   54,
    1,    2,    3,   47,   42,  178,  182,   49,   44,   53,
   50,   26,  150,   99,   90,   97,   61,   98,  100,   71,
  181,   45,   81,  103,    8,   48,  102,  150,    9,    6,
   51,  114,  119,  120,   13,   14,  115,  145,   71,   71,
   71,   71,   62,  134,  133,  175,   63,   64,   45,   84,
  124,    8,   58,   71,  138,    9,  175,  150,  126,  127,
   85,   13,   14,  174,    6,    7,  128,  129,    8,   86,
   34,   87,    9,   10,   89,   91,   11,   12,   13,   14,
   92,   15,   93,  104,    6,    7,  113,   96,    8,  101,
  121,  149,    9,   10,   71,  122,   11,   12,   13,   14,
  159,   15,  141,  155,  142,  123,  135,   71,   26,  186,
   45,  146,   71,    8,  144,   49,  147,    9,   50,   26,
  114,  148,  152,   13,   14,  115,  154,   55,   56,  167,
  158,  168,   77,  169,  180,  184,   38,   65,   66,   11,
   12,   55,  187,   53,   53,   53,  170,  188,   49,   49,
   49,   79,    6,   47,   47,   47,   45,   44,   33,   65,
   66,   40,   80,   46,  106,  107,  108,   71,   72,   73,
   55,   86,   52,   35,  125,   48,   48,   48,  106,  107,
  108,  161,   30,   18,   18,   72,   73,   36,   45,  130,
  131,    8,  153,  105,  173,    9,  185,   45,  114,  157,
    8,   13,   14,  115,    9,    6,    7,  114,    0,    8,
   13,   14,  115,    9,   10,    0,    0,   11,   12,   13,
   14,    0,   15,   45,    0,    0,    8,  132,    0,    0,
    9,    0,   45,  114,    0,    8,   13,   14,  115,    9,
    0,   45,  114,    0,    8,   13,   14,  115,    9,    6,
    7,  114,    0,    8,   13,   14,  115,    9,   10,  166,
    0,   11,   12,   13,   14,    0,   15,    0,  160,    0,
    6,    7,    0,    0,    8,    0,    0,    0,    9,   10,
    0,  172,   11,   12,   13,   14,  177,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   41,   45,   41,   47,   43,   40,   45,   41,
   40,   43,   61,   45,   40,   45,   41,   59,   60,   61,
   62,  123,   59,   60,   61,   62,   40,   59,   60,   61,
   62,   41,   58,   43,   40,   45,   43,   45,   45,   60,
   61,   62,   41,  125,   13,   41,   45,   81,   44,   59,
   60,   61,   62,   60,   61,   62,   43,   41,   45,    5,
   44,   44,   60,   45,   33,   16,  123,   36,   18,   15,
   16,   18,   18,   25,  138,  257,   59,   41,   41,   43,
   43,   45,   45,  125,   44,  119,  268,  269,  125,   44,
  257,  258,  259,  125,  257,   59,   59,   47,   40,   59,
   47,   47,  136,   42,   59,   43,   59,   45,   47,   78,
  174,  257,  261,   41,  260,  125,   44,  151,  264,  256,
  257,  267,  262,  263,  270,  271,  272,  125,   97,   98,
   99,  100,   59,   58,   59,  169,   59,   59,  257,   41,
   92,  260,   94,  112,  123,  264,  180,  181,   97,   98,
  259,  270,  271,  123,  256,  257,   99,  100,  260,   40,
   40,  257,  264,  265,  125,  257,  268,  269,  270,  271,
   58,  273,  257,   40,  256,  257,   41,  258,  260,  258,
   41,  125,  264,  265,  153,  274,  268,  269,  270,  271,
  125,  273,  258,  144,  257,   59,   59,  166,  144,  125,
  257,   41,  171,  260,  123,  155,   59,  264,  155,  155,
  267,  257,  263,  270,  271,  272,   59,  256,  257,  125,
   59,   59,  271,   41,   41,   59,  259,  257,  258,  268,
  269,  256,   59,  275,  276,  277,  258,   59,  275,  276,
  277,   36,    0,  275,  276,  277,   59,   59,  274,  257,
  258,  257,   59,   59,  275,  276,  277,   41,  257,  258,
  256,   59,   23,  125,   94,  275,  276,  277,  275,  276,
  277,  258,  256,  256,  257,  257,  258,  125,  257,  102,
  104,  260,  142,   78,  168,  264,  180,  257,  267,  147,
  260,  270,  271,  272,  264,  256,  257,  267,   -1,  260,
  270,  271,  272,  264,  265,   -1,   -1,  268,  269,  270,
  271,   -1,  273,  257,   -1,   -1,  260,  112,   -1,   -1,
  264,   -1,  257,  267,   -1,  260,  270,  271,  272,  264,
   -1,  257,  267,   -1,  260,  270,  271,  272,  264,  256,
  257,  267,   -1,  260,  270,  271,  272,  264,  265,  266,
   -1,  268,  269,  270,  271,   -1,  273,   -1,  153,   -1,
  256,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,  265,
   -1,  166,  268,  269,  270,  271,  171,  273,
};
}
final static short YYFINAL=4;
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
null,null,null,null,null,null,null,"id","cte","cadena","If","then","Else",
"end_if","out","fun","Return","Break","ui8","f64","discard","For","Continue",
"defer","SIMB_ASIGNACION","SIMB_MAY_IGUAL","SIMB_MEN_IGUAL","SIMB_DISTINTO",
"SIMB_COM_I","SIMB_COM_F",
};
final static String yyrule[] = {
"$accept : programa",
"programa : nombre_programa bloque_sentencias",
"nombre_programa : id",
"nombre_programa : cte",
"nombre_programa : cadena",
"bloque_sentencias : '{' sentencia '}'",
"bloque_sentencias : '{' sentencia",
"bloque_sentencias : sentencia '}'",
"sentencia : declarativa",
"sentencia : ejecutable",
"sentencia : sentencia declarativa",
"sentencia : sentencia ejecutable",
"declarativa : dec_variables",
"declarativa : dec_funcion",
"dec_variables : tipo list_variables ';'",
"dec_variables : list_variables ';'",
"tipo : ui8",
"tipo : f64",
"tipo : id",
"list_variables : id",
"list_variables : list_variables ',' id",
"list_variables : error",
"dec_funcion : header_funcion cola_funcion",
"dec_funcion : header_funcion parametro cola_funcion",
"dec_funcion : header_funcion parametro ',' parametro cola_funcion",
"header_funcion : fun id '('",
"header_funcion : fun '('",
"cola_funcion : ')' ':' tipo '{' cuerpo_fun '}'",
"cola_funcion : error",
"parametro : tipo id",
"parametro : tipo",
"cuerpo_fun : sentencia Return '(' expresion ')' ';'",
"cuerpo_fun : sentencia Return '(' expresion ';'",
"cuerpo_fun : sentencia Return expresion ';'",
"cuerpo_fun : sentencia Return expresion ')' ';'",
"cuerpo_fun : sentencia Return expresion",
"cuerpo_fun : sentencia Return '(' expresion ')'",
"ejecutable : inst_ejecutable",
"ejecutable : defer inst_ejecutable",
"inst_ejecutable : asignacion ';'",
"inst_ejecutable : seleccion ';'",
"inst_ejecutable : impresion ';'",
"inst_ejecutable : invocar_fun ';'",
"inst_ejecutable : for_continue",
"asignacion : id SIMB_ASIGNACION expresion",
"asignacion : id SIMB_ASIGNACION",
"asignacion : id ':' '=' expresion",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : id",
"factor : cte",
"factor : '-' cte",
"factor : retorno_funcion",
"retorno_funcion : id '(' ')'",
"retorno_funcion : id '(' parametro_real ')'",
"retorno_funcion : id '(' parametro_real ',' parametro_real ')'",
"parametro_real : id",
"parametro_real : cte",
"parametro_real : '-' cte",
"seleccion : If condicion_if cuerpo_if",
"cuerpo_if : then_selec Else else_selecc end_if",
"cuerpo_if : then_selec end_if",
"then_selec : then '{' ejecutable_for '}'",
"then_selec : then inst_ejecutable_for",
"else_selecc : '{' ejecutable_for '}'",
"else_selecc : inst_ejecutable_for",
"condicion_if : '(' expresion_booleana ')'",
"expresion_booleana : expresion comparador expresion",
"comparador : SIMB_DISTINTO",
"comparador : SIMB_MAY_IGUAL",
"comparador : SIMB_MEN_IGUAL",
"comparador : '='",
"comparador : '<'",
"comparador : '>'",
"impresion : out '(' cadena ')'",
"impresion : out cadena ')'",
"impresion : out '(' cadena",
"invocar_fun : discard retorno_funcion",
"invocar_fun : retorno_funcion",
"for_continue : For '(' for_inic ';' for_cond ';' for_act ')' for_cuerpo",
"for_continue : id ':' For '(' for_inic ';' for_cond ';' for_act ')' for_cuerpo",
"for_inic : id SIMB_ASIGNACION cte",
"for_cond : id comparador expresion",
"for_act : mas_o_menos cte",
"for_act : cte",
"for_cuerpo : '{' ejecutable_for '}' ';'",
"for_cuerpo : inst_ejecutable_for",
"mas_o_menos : '+'",
"mas_o_menos : '-'",
"ejecutable_for : ejecutable_for inst_ejecutable_for",
"ejecutable_for : inst_ejecutable_for",
"inst_ejecutable_for : inst_ejecutable",
"inst_ejecutable_for : Break ';'",
"inst_ejecutable_for : Break ':' id ';'",
"inst_ejecutable_for : Continue ';'",
};

//#line 246 "gramatica.y"

public static final String ANSI_RESET ="\u001B[0m";
public static final String ANSI_RED = "\u001B[31m";
public static final String ANSI_GREEN = "\u001B[32m";
public static final String ANSI_YELLOW = "\u001B[33m";
public static final String ANSI_BLUE = "\u001B[34m";
public static final String ANSI_PURPLE ="\u001B[35m";
public static final String ANSI_CYAN = "\u001B[36m";

public Vector<String> for_ids = new Vector<String>();
public List<Terceto> tercetos = new ArrayList<Terceto>();
public String tipoAux="";
public Stack<TokenLexema> tokens= new Stack<TokenLexema>();
public TokenLexema tokenAux;
public Boolean verb=AnalizadorLexico.getVerbose();
public Stack<String> id_for_act = new Stack<String>();
public Boolean parametroConstante = false;


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
		//System.out.println("Verificando entero");
		errorEnXY("Variable "+arg+" no declarada.");
		return;
	}
	if (TablaSimbolos.getSimbolo(arg).getTipo().equals("ui8")){
		return;
	}
	errorEnXY("Se requiere que sea de un tipo entero");
}

public void setearUso(String arg, String arg2){
	TokenLexema x = TablaSimbolos.getSimbolo(arg);
	x.setUso(arg2);
}

public void verificarTipos(String arg1,String arg2){
	//System.out.println("Verificando tipos "+arg1+";"+arg2);
	if (TablaSimbolos.getSimbolo(arg1).getTipo().equals(TablaSimbolos.getSimbolo(arg2).getTipo()))
		return;
	errorEnXY("No se puede realizar una operacion entre "+arg1+" y "+arg2);
}

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
	if (verb){
		int linea=0;
		linea = AnalizadorLexico.getLinea();
		System.out.println(ANSI_BLUE+"/|/|/|/: "+msg+" termina de reconocerse en la linea: "+linea+".\n"+ANSI_RESET);
	}
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
//#line 572 "Parser.java"
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
case 1:
//#line 18 "gramatica.y"
{programaListo();}
break;
case 2:
//#line 21 "gramatica.y"
{setearUso(val_peek(0).sval,"Nombre Programa");Ambito.addAmbito("_");TablaSimbolos.cambiarNombreKey(val_peek(0).sval);Ambito.removeAmbito();Ambito.addAmbito("Main");}
break;
case 3:
//#line 22 "gramatica.y"
{errorEnXY("Nombre del programa invalido. Identificador esperado, constante recibida en cambio");}
break;
case 4:
//#line 23 "gramatica.y"
{errorEnXY("Nombre del programa invalido. Identificador esperado, cadena recibida en cambio");}
break;
case 6:
//#line 27 "gramatica.y"
{errorEnXY("Esperando final de bloque");}
break;
case 7:
//#line 28 "gramatica.y"
{errorEnXY("Esperando comienzo de bloque");}
break;
case 12:
//#line 37 "gramatica.y"
{imprimirMSGEstructura("Declaracion de variable/s");}
break;
case 13:
//#line 38 "gramatica.y"
{imprimirMSGEstructura("Declaracion de funcion");}
break;
case 15:
//#line 42 "gramatica.y"
{errorEnXY("Tipo de la/s variable/s esperado al comienzo de la sentencia");}
break;
case 16:
//#line 45 "gramatica.y"
{tipoAux=val_peek(0).sval;yyval.sval=val_peek(0).sval;}
break;
case 17:
//#line 46 "gramatica.y"
{tipoAux=val_peek(0).sval;yyval.sval=val_peek(0).sval;}
break;
case 18:
//#line 47 "gramatica.y"
{errorEnXY("Tipo de la/s variable/s invalido");}
break;
case 19:
//#line 50 "gramatica.y"
{setearTipo(val_peek(0).sval);setearUso(val_peek(0).sval,"Variable");val_peek(0).sval=TablaSimbolos.cambiarNombreKey(val_peek(0).sval);}
break;
case 20:
//#line 51 "gramatica.y"
{setearTipo(val_peek(0).sval);setearUso(val_peek(0).sval,"Variable");val_peek(0).sval=TablaSimbolos.cambiarNombreKey(val_peek(0).sval);}
break;
case 21:
//#line 52 "gramatica.y"
{errorEnXY("Se esperaba un identificador o una lista de identificadores separados por ,");}
break;
case 25:
//#line 60 "gramatica.y"
{setearUso(val_peek(1).sval,"Nombre de Funcion");
							String aux=TablaSimbolos.cambiarNombreKey(val_peek(1).sval);
							Ambito.addAmbito(val_peek(1).sval);
							val_peek(1).sval=aux;
							tokens.push(TablaSimbolos.getSimbolo(val_peek(1).sval));}
break;
case 26:
//#line 65 "gramatica.y"
{errorEnXY("La declaracion de la funcion necesita un nombre");}
break;
case 27:
//#line 68 "gramatica.y"
{tokenAux=tokens.pop();
												tokenAux.setTipo(val_peek(3).sval);
												verificarTipos(tokenAux.getLexema().toString(),val_peek(1).sval);
												Ambito.removeAmbito();}
break;
case 28:
//#line 72 "gramatica.y"
{errorEnXY("En la declaracion de la funcion falta: ),:,{ o }");}
break;
case 29:
//#line 75 "gramatica.y"
{setearTipo(val_peek(0).sval,val_peek(1).sval);
					setearUso(val_peek(0).sval,"Nombre de Parametro");
					val_peek(0).sval=TablaSimbolos.cambiarNombreKey(val_peek(0).sval);}
break;
case 30:
//#line 78 "gramatica.y"
{errorEnXY("Identificador del parametro esperado  en la declaracion de funcion");}
break;
case 31:
//#line 81 "gramatica.y"
{yyval.sval=val_peek(2).sval;}
break;
case 32:
//#line 82 "gramatica.y"
{errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 33:
//#line 83 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo y final de la expresion");}
break;
case 34:
//#line 84 "gramatica.y"
{errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 35:
//#line 85 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo y final de la expresion y ; al final de linea");}
break;
case 36:
//#line 86 "gramatica.y"
{errorEnXY("; esperados al final de linea");}
break;
case 38:
//#line 91 "gramatica.y"
{imprimirMSGEstructura("Defer de instruccion ejecutable");}
break;
case 39:
//#line 94 "gramatica.y"
{imprimirMSGEstructura("Asignacion");}
break;
case 40:
//#line 95 "gramatica.y"
{imprimirMSGEstructura("Seleccion If");}
break;
case 41:
//#line 96 "gramatica.y"
{imprimirMSGEstructura("Impresion a Consola");}
break;
case 42:
//#line 97 "gramatica.y"
{imprimirMSGEstructura("Invocacion de Funcion");}
break;
case 43:
//#line 98 "gramatica.y"
{imprimirMSGEstructura("Loop For");}
break;
case 44:
//#line 101 "gramatica.y"
{comprobarBinding(val_peek(2).sval,"Variable "+val_peek(2).sval+" no declarada");
											val_peek(2).sval=val_peek(2).sval+Ambito.getNaming();
											verificarTipos(val_peek(2).sval,val_peek(0).sval);
											if (TablaSimbolos.getSimbolo(val_peek(2).sval).getUso().equals("Nombre de Parametro")){
												TablaSimbolos.getSimbolo(Ambito.getNombreAmbito()).setEsp(true);
											}
											ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 45:
//#line 108 "gramatica.y"
{errorEnXY("Expresion esperada despues de la asignacion");}
break;
case 46:
//#line 109 "gramatica.y"
{errorEnXY("Operador de asignacion incorrecto, se esperaba -> =:");}
break;
case 47:
//#line 112 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
									ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 48:
//#line 114 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
									ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 50:
//#line 119 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
							ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 51:
//#line 121 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
								ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 53:
//#line 126 "gramatica.y"
{comprobarBinding(val_peek(0).sval,"Variable "+val_peek(0).sval+" no declarada");
			val_peek(0).sval=Ambito.getAmbito(val_peek(0).sval);
			yyval.sval=val_peek(0).sval;}
break;
case 55:
//#line 130 "gramatica.y"
{verificarRangoDoubleNegativo();
					val_peek(0).sval="-"+val_peek(0).sval;
					TablaSimbolos.addSimbolo(new TokenLexema(258, val_peek(0).sval,"f64"));
					yyval.sval=val_peek(0).sval;}
break;
case 57:
//#line 137 "gramatica.y"
{comprobarBinding(val_peek(2).sval,"Funcion "+val_peek(2).sval+" no declarada");}
break;
case 58:
//#line 138 "gramatica.y"
{comprobarBinding(val_peek(3).sval,"Funcion "+val_peek(3).sval+" no declarada");
									val_peek(3).sval=Ambito.getAmbito(val_peek(3).sval);
									System.out.println(val_peek(3).sval);
									if (TablaSimbolos.getSimbolo(val_peek(3).sval).getEsp()&&parametroConstante){
										errorEnXY("El parametro "+val_peek(1).sval+", no puede ser constante debido a que hay asignaciones en la funcion "+val_peek(3).sval);
										parametroConstante=false;
									}}
break;
case 59:
//#line 145 "gramatica.y"
{comprobarBinding(val_peek(5).sval,"Funcion "+val_peek(5).sval+" no declarada");
														System.out.println(val_peek(5).sval);
														if (TablaSimbolos.getSimbolo(val_peek(5).sval).getEsp()&&parametroConstante){
															errorEnXY("Los parametros no pueden ser constantes debido a que hay asignaciones en la funcion "+val_peek(5).sval);
															parametroConstante=false;
														}}
break;
case 60:
//#line 153 "gramatica.y"
{comprobarBinding(val_peek(0).sval,"No se encontro el parametro "+val_peek(0).sval);}
break;
case 61:
//#line 154 "gramatica.y"
{parametroConstante=true;}
break;
case 62:
//#line 155 "gramatica.y"
{verificarRangoDoubleNegativo();val_peek(0).sval="-"+val_peek(0).sval;TablaSimbolos.addSimbolo(new TokenLexema(258, val_peek(0).sval,"f64"));yyval.sval=val_peek(0).sval;}
break;
case 63:
//#line 158 "gramatica.y"
{ListaTercetos.add_seleccion_final();}
break;
case 66:
//#line 165 "gramatica.y"
{ListaTercetos.add_seleccion_then();}
break;
case 67:
//#line 166 "gramatica.y"
{ListaTercetos.add_seleccion_then();}
break;
case 70:
//#line 173 "gramatica.y"
{ListaTercetos.add_seleccion_cond();}
break;
case 71:
//#line 176 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
													ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 78:
//#line 188 "gramatica.y"
{ListaTercetos.addTerceto(new Terceto(val_peek(3).sval,val_peek(1).sval,"-"));}
break;
case 79:
//#line 189 "gramatica.y"
{errorEnXY("Falta de parentesis al comienzo de la cadena del out");}
break;
case 80:
//#line 190 "gramatica.y"
{errorEnXY("Falta de parentesis al final de la cadena del out");}
break;
case 82:
//#line 194 "gramatica.y"
{errorEnXY("Funcion invocada sin discard del retorno");}
break;
case 83:
//#line 197 "gramatica.y"
{verificarIdIguales(val_peek(6).sval,val_peek(4).sval);
																		verificarTipos(val_peek(6).sval+Ambito.getNaming(),val_peek(4).sval+Ambito.getNaming());
																		verificarTipos(val_peek(6).sval+Ambito.getNaming(),val_peek(2).sval);}
break;
case 84:
//#line 200 "gramatica.y"
{verificarIdIguales(val_peek(6).sval,val_peek(4).sval);
																		verificarTipos(val_peek(6).sval+Ambito.getNaming(),val_peek(4).sval+Ambito.getNaming());
																		verificarTipos(val_peek(6).sval+Ambito.getNaming(),val_peek(2).sval);
																		TablaSimbolos.cambiarNombreKey(val_peek(10).sval);
																		setearUso(val_peek(10).sval+Ambito.getNaming(),"Etiqueta");}
break;
case 85:
//#line 207 "gramatica.y"
{TablaSimbolos.removeSimbolo(val_peek(2).sval);
									val_peek(2).sval=val_peek(2).sval+Ambito.getNaming();
									verificarEntero(val_peek(2).sval);
									verificarTipos(val_peek(2).sval,val_peek(0).sval);
									ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));
									id_for_act.push(val_peek(2).sval);}
break;
case 86:
//#line 215 "gramatica.y"
{TablaSimbolos.removeSimbolo(val_peek(2).sval);
									val_peek(2).sval=val_peek(2).sval+Ambito.getNaming();
									verificarTipos(val_peek(2).sval,val_peek(0).sval);
									ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));
									ListaTercetos.add_seleccion_cond();}
break;
case 87:
//#line 222 "gramatica.y"
{yyval.sval=val_peek(0).sval;
						ListaTercetos.add_for_act(id_for_act.pop(),val_peek(1).sval,val_peek(0).sval);}
break;
case 88:
//#line 224 "gramatica.y"
{errorEnXY("Falta +/- para actualizar for");}
break;
case 89:
//#line 227 "gramatica.y"
{ListaTercetos.add_for_cpo();}
break;
case 90:
//#line 228 "gramatica.y"
{ListaTercetos.add_for_cpo();}
break;
//#line 1033 "Parser.java"
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
