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
import java.util.HashMap;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import GeneracionTercetos.*;
//#line 25 "Parser.java"




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
    9,    7,   13,    7,   14,    7,   10,   10,   11,   11,
   12,   12,   15,   15,   15,   15,   15,   15,    5,   18,
    5,   17,   17,   17,   17,   17,   19,   19,   19,   16,
   16,   16,   24,   24,   24,   25,   25,   25,   25,   26,
   26,   26,   27,   27,   27,   20,   29,   29,   30,   30,
   31,   31,   28,   34,   35,   35,   35,   35,   35,   35,
   21,   21,   21,   22,   22,   23,   23,   40,   36,   37,
   38,   38,   39,   39,   41,   41,   32,   32,   33,   33,
   33,   33,
};
final static short yylen[] = {                            2,
    2,    1,    1,    1,    3,    2,    2,    1,    1,    2,
    2,    1,    1,    3,    2,    1,    1,    1,    1,    3,
    1,    2,    0,    4,    0,    6,    3,    2,    6,    1,
    2,    1,    6,    5,    4,    5,    3,    5,    1,    0,
    3,    2,    2,    2,    2,    1,    3,    2,    4,    3,
    3,    1,    3,    3,    1,    1,    1,    2,    1,    3,
    4,    6,    1,    1,    2,    3,    4,    2,    4,    2,
    3,    1,    3,    3,    1,    1,    1,    1,    1,    1,
    4,    3,    3,    2,    1,    9,   10,    2,    3,    3,
    2,    1,    4,    1,    1,    1,    2,    1,    1,    2,
    4,    2,
};
final static short yydefred[] = {                         0,
    2,    3,    4,    0,    0,   21,    0,    0,    0,    0,
   16,   17,    0,    0,   40,    0,    1,    0,    8,    9,
   12,   13,    0,    0,    0,   39,    0,    0,    0,    0,
   46,   85,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   28,    0,   84,    0,    0,    0,    7,   10,   11,
   19,    0,   15,    0,   30,   18,    0,    0,   22,    0,
   42,   43,   44,   45,    0,    0,   57,    0,    0,    0,
   55,   59,   63,   64,   60,    0,    0,    0,    0,    0,
    0,   66,    0,   82,    0,   27,    0,    0,    0,   41,
    5,   14,   20,    0,   31,    0,    0,    0,   58,    0,
    0,    0,    0,   65,    0,   61,    0,   76,   77,   75,
   78,   79,   80,    0,   73,    0,    0,    0,   99,   70,
    0,   68,   81,    0,    0,    0,   25,   24,    0,    0,
    0,   53,   54,    0,    0,  100,    0,  102,    0,   98,
    0,    0,   72,   89,    0,    0,    0,    0,    0,   62,
    0,   69,   97,    0,   67,    0,    0,    0,    0,   26,
    0,  101,   71,    0,   92,   95,   96,    0,    0,    0,
   29,    0,    0,   91,    0,    0,    0,    0,   94,   86,
    0,   35,    0,    0,    0,   34,    0,   36,   87,    0,
   33,   93,
};
final static short yydgoto[] = {                          4,
    5,   17,   18,   19,   20,   21,   22,   23,   24,   25,
   59,   60,   97,  148,  159,   69,  119,   46,   27,   28,
   29,   30,   31,   70,   71,   32,   77,   38,   82,   83,
  142,  139,  140,   80,  114,   88,  146,  168,  180,   33,
  169,
};
final static short yysindex[] = {                      -119,
    0,    0,    0,    0, -110,    0,  -25,   52,  -32,   -5,
    0,    0, -176,   56,    0,  115,    0,   37,    0,    0,
    0,    0, -151,   23,  -38,    0,   43,   64,   71,   85,
    0,    0, -181,   -7,    2,   92,   -7, -150,  116,  -95,
  125,    0,  126,    0,  -88, -145,   55,    0,    0,    0,
    0,   36,    0,  -87,    0,    0,  113,  -85,    0,  129,
    0,    0,    0,    0,  134,  126,    0,  -83,   29,   61,
    0,    0,    0,    0,    0,  -82,   69,   -7,   -6,  136,
  -78,    0, -127,    0,  137,    0,  -93,  124,  -25,    0,
    0,    0,    0, -171,    0, -171,  -24,  -88,    0,   -7,
   -7,   -7,   -7,    0,   18,    0,   29,    0,    0,    0,
    0,    0,    0,   -7,    0,   83,  131, -143,    0,    0,
  -50,    0,    0,  -74,  -72,   65,    0,    0,  132,   61,
   61,    0,    0,  155,   29,    0,  -59,    0,   70,    0,
 -143,  -60,    0,    0,  -20,  146,  115,  -24,  -72,    0,
  149,    0,    0,   79,    0,   -7,    3,   97,   84,    0,
  153,    0,    0,   29,    0,    0,    0,  174,  -42,  -29,
    0,    3,   20,    0,   -7,   34,  182, -143,    0,    0,
   42,    0,  165,   20,   88,    0,  166,    0,    0,  167,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,   17,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  179,    0,  -28,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  233,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   16,    0,  -19,
    0,    0,    0,    0,    0,  -41,    0,    0,  183,  -36,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  188,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  189,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -31,
   -9,    0,    0,    0,  212,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  199,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  138,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  139,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   50,   41,   44,    0,    0,   24,  239,    0,
  -70,  169,    0,    0,    0,  217,   53,    0,    0,    0,
    0,    0,    0,   48,   49,   31,  173,    0,    0,    0,
    0,  -71,  -17,    0,  140,  181,  133,  109,   99,    0,
    0,
};
final static int YYTABLESIZE=392;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         56,
   56,   56,   57,   56,   52,   56,   52,   40,   52,   50,
  175,   50,   16,   50,   35,   68,   57,   56,   56,   56,
   56,   23,   52,   52,   52,   52,  128,   50,   50,   50,
   50,   51,   36,   51,   42,   51,  100,   68,  101,  112,
  111,  113,   75,   44,  118,  166,   76,  167,   58,   51,
   51,   51,   51,  112,  111,  113,   32,   26,   49,   32,
   19,   50,   76,  120,   72,   47,   54,   72,   26,  154,
   26,  100,  141,  101,  183,   19,  100,  160,  101,   54,
   43,   53,  187,   56,  100,   56,  101,   49,   52,   65,
   50,   37,  182,   50,   92,   45,   11,   12,   90,   26,
  186,   61,  102,  143,    6,   51,  185,  103,   72,  106,
   81,   89,  105,   89,    8,   51,    8,  126,    9,   58,
    9,  153,   62,  116,   13,   14,   13,   14,  117,   63,
   72,   72,   72,   72,  121,  122,  153,    1,    2,    3,
  137,  136,  178,   64,   72,    6,    7,  130,  131,    8,
  132,  133,   78,    9,   10,  179,   84,   11,   12,   13,
   14,   48,   15,   85,   86,   35,  179,  153,   87,   93,
   94,   95,   96,   98,   99,  104,  115,  123,   89,   91,
  124,    8,  125,  144,  145,    9,   72,  147,  116,  138,
  149,   13,   14,  117,  152,  150,  158,  151,   49,   26,
   72,   50,  155,  163,  157,   72,   89,  162,  171,    8,
   26,  172,  190,    9,  173,  174,  116,   55,   56,   13,
   14,  117,  184,  188,  191,  192,   39,   66,   67,   11,
   12,   55,    6,   56,   56,   56,   23,   48,   52,   52,
   52,   47,   88,   50,   50,   50,   83,   49,   34,   66,
   67,   41,   74,   79,  108,  109,  110,   90,   73,   74,
  165,   52,   37,   38,  127,   51,   51,   51,  108,  109,
  110,   32,   18,   18,   73,   74,   89,  134,  129,    8,
  177,  161,  189,    9,  156,    0,  116,    0,    0,   13,
   14,  117,    6,    7,  107,    0,    8,    0,    0,    0,
    9,   10,    0,    0,   11,   12,   13,   14,    0,   15,
    6,    7,    0,    0,    8,    0,    0,    0,    9,   10,
    0,    0,   11,   12,   13,   14,   89,   15,    0,    8,
  135,    0,    0,    9,    0,   89,  116,    0,    8,   13,
   14,  117,    9,    0,   89,  116,    0,    8,   13,   14,
  117,    9,    6,    7,  116,    0,    8,   13,   14,  117,
    9,   10,  170,    0,   11,   12,   13,   14,    0,   15,
    6,    7,  164,    0,    8,    0,    0,    0,    9,   10,
    0,    0,   11,   12,   13,   14,  176,   15,    0,    0,
    0,  181,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   41,   45,   41,   47,   43,   40,   45,   41,
   40,   43,  123,   45,   40,   45,   41,   59,   60,   61,
   62,   41,   59,   60,   61,   62,   97,   59,   60,   61,
   62,   41,   58,   43,   40,   45,   43,   45,   45,   60,
   61,   62,   41,   13,  123,   43,   45,   45,   25,   59,
   60,   61,   62,   60,   61,   62,   41,    5,   18,   44,
   44,   18,   45,   81,   34,   16,   44,   37,   16,  141,
   18,   43,  123,   45,   41,   59,   43,  148,   45,   44,
  257,   59,   41,  125,   43,  257,   45,   47,  125,  271,
   47,   40,   59,  125,   59,   40,  268,  269,   46,   47,
   59,   59,   42,  121,  256,  257,  178,   47,   78,   41,
  261,  257,   44,  257,  260,  125,  260,   94,  264,   96,
  264,  139,   59,  267,  270,  271,  270,  271,  272,   59,
  100,  101,  102,  103,  262,  263,  154,  257,  258,  259,
   58,   59,  123,   59,  114,  256,  257,  100,  101,  260,
  102,  103,   61,  264,  265,  173,   41,  268,  269,  270,
  271,  125,  273,  259,   40,   40,  184,  185,  257,  257,
   58,  257,   44,   40,  258,  258,   41,   41,  257,  125,
  274,  260,   59,  258,  257,  264,  156,  123,  267,   59,
   59,  270,  271,  272,  125,   41,  147,  257,  158,  147,
  170,  158,  263,  125,   59,  175,  257,   59,  125,  260,
  158,   59,  125,  264,   41,  258,  267,  256,  257,  270,
  271,  272,   41,   59,   59,   59,  259,  257,  258,  268,
  269,  256,    0,  275,  276,  277,  256,   59,  275,  276,
  277,   59,  271,  275,  276,  277,   59,   59,  274,  257,
  258,  257,   41,   37,  275,  276,  277,   59,  257,  258,
  258,   23,  125,  125,   96,  275,  276,  277,  275,  276,
  277,  256,  256,  257,  257,  258,  257,  105,   98,  260,
  172,  149,  184,  264,  145,   -1,  267,   -1,   -1,  270,
  271,  272,  256,  257,   78,   -1,  260,   -1,   -1,   -1,
  264,  265,   -1,   -1,  268,  269,  270,  271,   -1,  273,
  256,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,  265,
   -1,   -1,  268,  269,  270,  271,  257,  273,   -1,  260,
  114,   -1,   -1,  264,   -1,  257,  267,   -1,  260,  270,
  271,  272,  264,   -1,  257,  267,   -1,  260,  270,  271,
  272,  264,  256,  257,  267,   -1,  260,  270,  271,  272,
  264,  265,  266,   -1,  268,  269,  270,  271,   -1,  273,
  256,  257,  156,   -1,  260,   -1,   -1,   -1,  264,  265,
   -1,   -1,  268,  269,  270,  271,  170,  273,   -1,   -1,
   -1,  175,
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
"$$1 :",
"dec_funcion : header_funcion parametro $$1 cola_funcion",
"$$2 :",
"dec_funcion : header_funcion parametro ',' parametro $$2 cola_funcion",
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
"$$3 :",
"ejecutable : defer $$3 inst_ejecutable",
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
"for_continue : etiqueta For '(' for_inic ';' for_cond ';' for_act ')' for_cuerpo",
"etiqueta : id ':'",
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

//#line 428 "gramatica.y"

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
		aux1=ListaTercetos.getTerceto(aux1).getSarg();
	}
	String aux2=arg2;
	while (aux2.startsWith("[")){
		aux2=ListaTercetos.getTerceto(aux2).getSarg();
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
//#line 635 "Parser.java"
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
//#line 19 "gramatica.y"
{programaListo();}
break;
case 2:
//#line 22 "gramatica.y"
{setearUso(val_peek(0).sval,"Nombre Programa");
					Ambito.addAmbito("_");
					TablaSimbolos.cambiarNombreKey(val_peek(0).sval);
					Ambito.removeAmbito();
					Ambito.addAmbito("Main");}
break;
case 3:
//#line 27 "gramatica.y"
{errorEnXY("Nombre del programa invalido. Identificador esperado, constante recibida en cambio");}
break;
case 4:
//#line 28 "gramatica.y"
{errorEnXY("Nombre del programa invalido. Identificador esperado, cadena recibida en cambio");}
break;
case 5:
//#line 31 "gramatica.y"
{Ambito.removeAmbito();}
break;
case 6:
//#line 32 "gramatica.y"
{errorEnXY("Esperando final de bloque");}
break;
case 7:
//#line 33 "gramatica.y"
{errorEnXY("Esperando comienzo de bloque");}
break;
case 12:
//#line 42 "gramatica.y"
{imprimirMSGEstructura("Declaracion de variable/s");}
break;
case 13:
//#line 43 "gramatica.y"
{ListaTercetos.getTerceto(llamadasFunciones.get(val_peek(0).sval).getTercetoInv()).setCarg(true);
						imprimirMSGEstructura("Declaracion de funcion");}
break;
case 15:
//#line 48 "gramatica.y"
{errorEnXY("Tipo de la/s variable/s esperado al comienzo de la sentencia");}
break;
case 16:
//#line 51 "gramatica.y"
{tipoAux=val_peek(0).sval;yyval.sval=val_peek(0).sval;}
break;
case 17:
//#line 52 "gramatica.y"
{tipoAux=val_peek(0).sval;yyval.sval=val_peek(0).sval;}
break;
case 18:
//#line 53 "gramatica.y"
{errorEnXY("Tipo de la/s variable/s invalido");}
break;
case 19:
//#line 56 "gramatica.y"
{setearTipo(val_peek(0).sval);setearUso(val_peek(0).sval,"Variable");val_peek(0).sval=TablaSimbolos.cambiarNombreKey(val_peek(0).sval);}
break;
case 20:
//#line 57 "gramatica.y"
{setearTipo(val_peek(0).sval);setearUso(val_peek(0).sval,"Variable");val_peek(0).sval=TablaSimbolos.cambiarNombreKey(val_peek(0).sval);}
break;
case 21:
//#line 58 "gramatica.y"
{errorEnXY("Se esperaba un identificador o una lista de identificadores separados por ,");}
break;
case 23:
//#line 63 "gramatica.y"
{llamadasFunciones.get(val_peek(1).sval).setPar1(val_peek(0).sval);}
break;
case 25:
//#line 64 "gramatica.y"
{InvocacionFuncion f = llamadasFunciones.get(val_peek(3).sval);
																f.setPar1(val_peek(2).sval);
																f.setPar2(val_peek(0).sval);}
break;
case 27:
//#line 69 "gramatica.y"
{setearUso(val_peek(1).sval,"Nombre de Funcion");
							String aux=TablaSimbolos.cambiarNombreKey(val_peek(1).sval);
							Ambito.addAmbito(val_peek(1).sval);
							val_peek(1).sval=aux;
							tokens.push(TablaSimbolos.getSimbolo(val_peek(1).sval));
							Terceto terAux = new Terceto("BI","","_");
							tercetosAux.push(terAux);
							ListaTercetos.addTerceto(terAux);
							InvocacionFuncion f = new InvocacionFuncion();
							f.setTercetoInv("["+ListaTercetos.getIndice()+"]");
							llamadasFunciones.put(val_peek(1).sval,f);
							terAux = new Terceto("MOV","EBX",String.valueOf(ListaTercetos.getIndice()));
							ListaTercetos.addTerceto(terAux);
							yyval.sval=val_peek(1).sval;}
break;
case 28:
//#line 83 "gramatica.y"
{errorEnXY("La declaracion de la funcion necesita un nombre");}
break;
case 29:
//#line 86 "gramatica.y"
{TokenLexema tokenAux=tokens.pop();
												if(tokenAux!=null){
													Ambito.removeAmbito();
													tokenAux.setTipo(val_peek(3).sval);
													verificarTipos(tokenAux.getLexema().toString(),val_peek(1).sval);
													Terceto terAux=new Terceto("Push",val_peek(1).sval,"_");
													ListaTercetos.addTerceto(terAux);
													/*Ambito.removeAmbito();*/
													terAux=new Terceto("RET","_","_");
													ListaTercetos.addTerceto(terAux);
													terAux=tercetosAux.pop();
													terAux.setSarg("["+ListaTercetos.getIndice()+"]");
												}}
break;
case 30:
//#line 99 "gramatica.y"
{errorEnXY("En la declaracion de la funcion falta: ),:,{ o }");}
break;
case 31:
//#line 102 "gramatica.y"
{setearTipo(val_peek(0).sval,val_peek(1).sval);
					setearUso(val_peek(0).sval,"Nombre de Parametro");
					yyval.sval=TablaSimbolos.cambiarNombreKey(val_peek(0).sval);
					variablesInicializadas.add(yyval.sval);
					ListaTercetos.addTerceto(new Terceto("Pop",yyval.sval,"_"));}
break;
case 32:
//#line 107 "gramatica.y"
{errorEnXY("Identificador del parametro esperado  en la declaracion de funcion");}
break;
case 33:
//#line 110 "gramatica.y"
{yyval.sval=val_peek(2).sval; }
break;
case 34:
//#line 111 "gramatica.y"
{yyval.sval=val_peek(1).sval;errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 35:
//#line 112 "gramatica.y"
{yyval.sval=val_peek(1).sval;errorEnXY("Parentesis esperados al comienzo y final de la expresion");}
break;
case 36:
//#line 113 "gramatica.y"
{yyval.sval=val_peek(2).sval;errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 37:
//#line 114 "gramatica.y"
{yyval.sval=val_peek(0).sval;errorEnXY("Parentesis esperados al comienzo y final de la expresion y ; al final de linea");}
break;
case 38:
//#line 115 "gramatica.y"
{yyval.sval=val_peek(1).sval;errorEnXY("; esperados al final de linea");}
break;
case 40:
//#line 120 "gramatica.y"
{ListaTercetos.setDefer(true);diferido=true;}
break;
case 41:
//#line 120 "gramatica.y"
{ListaTercetos.setDefer(false);
																diferido=false;
																imprimirMSGEstructura("Defer de instruccion ejecutable");}
break;
case 42:
//#line 125 "gramatica.y"
{imprimirMSGEstructura("Asignacion");}
break;
case 43:
//#line 126 "gramatica.y"
{imprimirMSGEstructura("Seleccion If");}
break;
case 44:
//#line 127 "gramatica.y"
{imprimirMSGEstructura("Impresion a Consola");}
break;
case 45:
//#line 128 "gramatica.y"
{imprimirMSGEstructura("Invocacion de Funcion");}
break;
case 46:
//#line 129 "gramatica.y"
{imprimirMSGEstructura("Loop For");}
break;
case 47:
//#line 132 "gramatica.y"
{comprobarBinding(val_peek(2).sval,"Variable "+val_peek(2).sval+" no declarada");
											val_peek(2).sval=Ambito.getAmbito(val_peek(2).sval);
											if(val_peek(2).sval!=null){
												if (!diferido){
													if (Ambito.getNaming().equals(Ambito.getAmbitoDeVariable(val_peek(2).sval))){
														variablesInicializadas.add(val_peek(2).sval);
													}
												}else{
													if (!variablesInicializadas.contains(val_peek(2).sval)){
														errorEnXY("Para asignar a una variable en una instruccion diferida primero inicialicela");
													}
												}
												verificarTipos(val_peek(2).sval,val_peek(0).sval);
												if (TablaSimbolos.getSimbolo(val_peek(2).sval).getUso().equals("Nombre de Parametro")){
													TablaSimbolos.getSimbolo(Ambito.getNombreAmbito()).setEsp(true);
												}
												ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));
											}}
break;
case 48:
//#line 150 "gramatica.y"
{errorEnXY("Expresion esperada despues de la asignacion");}
break;
case 49:
//#line 151 "gramatica.y"
{errorEnXY("Operador de asignacion incorrecto, se esperaba -> =:");}
break;
case 50:
//#line 154 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
									yyval.sval="["+ListaTercetos.getIndice()+"]";
									ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 51:
//#line 157 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
									yyval.sval="["+ListaTercetos.getIndice()+"]";
									ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 53:
//#line 163 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
							yyval.sval="["+ListaTercetos.getIndice()+"]";
							ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 54:
//#line 166 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
							yyval.sval="["+ListaTercetos.getIndice()+"]";
							ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 56:
//#line 172 "gramatica.y"
{comprobarBinding(val_peek(0).sval,"Variable "+val_peek(0).sval+" no declarada");
			val_peek(0).sval=Ambito.getAmbito(val_peek(0).sval);
			if(!TablaSimbolos.getSimbolo(val_peek(0).sval).getUso().equals("Nombre de Parametro"))
				comprobarInicializada(val_peek(0).sval,Ambito.getNaming());
			yyval.sval=val_peek(0).sval;}
break;
case 58:
//#line 178 "gramatica.y"
{verificarRangoDoubleNegativo();
					val_peek(0).sval="-"+val_peek(0).sval;
					TablaSimbolos.addSimbolo(new TokenLexema(258, val_peek(0).sval,"f64"));
					yyval.sval=val_peek(0).sval;}
break;
case 60:
//#line 185 "gramatica.y"
{comprobarBinding(val_peek(2).sval,"Funcion "+val_peek(2).sval+" no declarada");
								if(Ambito.getAmbito(val_peek(2).sval)!=null){
									comprobarParametrosFuncion(val_peek(2).sval,0);
									val_peek(2).sval=Ambito.getAmbito(val_peek(2).sval);
									yyval.sval=Ambito.getAmbito(val_peek(2).sval);
									String tercetoLlamado = llamadasFunciones.get(val_peek(2).sval).getTercetoInv();
									Terceto terAux=new Terceto("Push","EBX","_");
									ListaTercetos.addTerceto(terAux);
									terAux=new Terceto("CMP","EBX",tercetoLlamado.substring(1,tercetoLlamado.length()-1));
									ListaTercetos.addTerceto(terAux);
									terAux=new Terceto("JE","ErrRec","_");
									ListaTercetos.addTerceto(terAux);
									terAux=new Terceto("CALL",tercetoLlamado,"_");
									ListaTercetos.addTerceto(terAux);
									terAux=new Terceto("Pop",val_peek(2).sval,"_");
									ListaTercetos.addTerceto(terAux);
									terAux=new Terceto("Pop","EBX","_");
									ListaTercetos.addTerceto(terAux);
								}}
break;
case 61:
//#line 204 "gramatica.y"
{comprobarBinding(val_peek(3).sval,"Funcion "+val_peek(3).sval+" no declarada");
									if(Ambito.getAmbito(val_peek(3).sval)!=null){
										comprobarParametrosFuncion(val_peek(3).sval,1);
										val_peek(3).sval=Ambito.getAmbito(val_peek(3).sval);
										String par= Ambito.getAmbito(val_peek(1).sval);
										if(par==null)
											par =val_peek(1).sval;
										verificarTipos(llamadasFunciones.get(val_peek(3).sval).getPar1(),par);
										String tercetoLlamado = llamadasFunciones.get(val_peek(3).sval).getTercetoInv();
										Terceto terAux=new Terceto("Push","EBX","_");
										ListaTercetos.addTerceto(terAux);
										terAux=new Terceto("CMP","EBX",tercetoLlamado.substring(1,tercetoLlamado.length()-1));
										ListaTercetos.addTerceto(terAux);
										ListaTercetos.addTerceto(new Terceto("JE","ErrRec","_"));
										ListaTercetos.addTerceto(new Terceto("Push",par,"_"));
										ListaTercetos.addTerceto(new Terceto("CALL",tercetoLlamado,"_"));
										ListaTercetos.addTerceto(new Terceto("Pop",val_peek(3).sval,"_"));
										terAux=new Terceto("Pop","EBX","_");
										ListaTercetos.addTerceto(terAux);
										yyval.sval=val_peek(3).sval;
									}}
break;
case 62:
//#line 225 "gramatica.y"
{comprobarBinding(val_peek(5).sval,"Funcion "+val_peek(5).sval+" no declarada");
														if(Ambito.getAmbito(val_peek(5).sval)!=null){
															comprobarParametrosFuncion(val_peek(5).sval,2);
															val_peek(5).sval=Ambito.getAmbito(val_peek(5).sval);
															String par1= Ambito.getAmbito(val_peek(3).sval);
															if(par1==null)
																par1 =val_peek(3).sval;
															verificarTipos(llamadasFunciones.get(val_peek(5).sval).getPar1(),par1);
															String par2= Ambito.getAmbito(val_peek(1).sval);
															if(par2==null)
																par2 =val_peek(1).sval;
															verificarTipos(llamadasFunciones.get(val_peek(5).sval).getPar2(),par2);
															String tercetoLlamado = llamadasFunciones.get(val_peek(5).sval).getTercetoInv();
															Terceto terAux=new Terceto("Push","EBX","_");
															ListaTercetos.addTerceto(terAux);
															terAux = new Terceto("CMP","EBX",tercetoLlamado.substring(1,tercetoLlamado.length()-1));
															ListaTercetos.addTerceto(terAux);
															ListaTercetos.addTerceto(new Terceto("JE","ErrRec","_"));
															ListaTercetos.addTerceto(new Terceto("Push",par1,"_"));
															ListaTercetos.addTerceto(new Terceto("Push",par2,"_"));
															ListaTercetos.addTerceto(new Terceto("CALL",tercetoLlamado,"_"));
															ListaTercetos.addTerceto(new Terceto("Pop",val_peek(5).sval,"_"));
															terAux=new Terceto("Pop","EBX","_");
															ListaTercetos.addTerceto(terAux);
															yyval.sval=Ambito.getAmbito(val_peek(5).sval);
														}}
break;
case 63:
//#line 253 "gramatica.y"
{comprobarBinding(val_peek(0).sval,"No se encontro el parametro "+val_peek(0).sval);
					if(Ambito.getAmbito(val_peek(0).sval)!=null)
						comprobarInicializada(Ambito.getAmbito(val_peek(0).sval),Ambito.getNaming());}
break;
case 65:
//#line 257 "gramatica.y"
{verificarRangoDoubleNegativo();val_peek(0).sval="-"+val_peek(0).sval;TablaSimbolos.addSimbolo(new TokenLexema(258, val_peek(0).sval,"f64"));yyval.sval=val_peek(0).sval;}
break;
case 66:
//#line 260 "gramatica.y"
{ListaTercetos.add_seleccion_final();}
break;
case 69:
//#line 267 "gramatica.y"
{ListaTercetos.add_seleccion_then();}
break;
case 70:
//#line 268 "gramatica.y"
{ListaTercetos.add_seleccion_then();}
break;
case 73:
//#line 275 "gramatica.y"
{ListaTercetos.add_seleccion_cond();}
break;
case 74:
//#line 278 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
													ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 81:
//#line 290 "gramatica.y"
{ListaTercetos.addTerceto(new Terceto(val_peek(3).sval,val_peek(1).sval,"_"));}
break;
case 82:
//#line 291 "gramatica.y"
{errorEnXY("Falta de parentesis al comienzo de la cadena del out");}
break;
case 83:
//#line 292 "gramatica.y"
{errorEnXY("Falta de parentesis al final de la cadena del out");}
break;
case 85:
//#line 296 "gramatica.y"
{errorEnXY("Funcion invocada sin discard del retorno");}
break;
case 86:
//#line 299 "gramatica.y"
{Ambito.removeAmbito();
																		if(val_peek(6).sval!=null){
																			verificarIdIguales(val_peek(6).sval,val_peek(4).sval);
																			verificarTipos(val_peek(6).sval,val_peek(4).sval);
																			verificarTipos(val_peek(6).sval,val_peek(2).sval);
																		}}
break;
case 87:
//#line 305 "gramatica.y"
{Ambito.removeAmbito();
																			if(val_peek(6).sval!=null){
																				verificarIdIguales(val_peek(6).sval,val_peek(4).sval);
																				verificarTipos(val_peek(6).sval,val_peek(4).sval);
																				verificarTipos(val_peek(6).sval,val_peek(2).sval);
																				if (tercetosBreakET.containsKey(val_peek(9).sval)){
																					List<Terceto> aux = tercetosBreakET.get(val_peek(9).sval);
																					tercetosBreakET.remove(val_peek(9).sval);
																					for (int i = 0; i<aux.size();i++){
																						aux.get(i).setSarg("["+ListaTercetos.getIndice()+"]");
																					}
																				}
																			}}
break;
case 88:
//#line 320 "gramatica.y"
{setearUso(val_peek(1).sval,"Etiqueta");
					yyval.sval=TablaSimbolos.cambiarNombreKey(val_peek(1).sval);}
break;
case 89:
//#line 324 "gramatica.y"
{val_peek(2).sval=Ambito.getAmbito(val_peek(2).sval);
									if (!variablesInicializadas.contains(val_peek(2).sval))
										variablesInicializadas.add(val_peek(2).sval);
									verificarEntero(val_peek(2).sval);
									verificarTipos(val_peek(2).sval,val_peek(0).sval);
									ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));
									id_for_act.push(val_peek(2).sval);
									yyval.sval=val_peek(2).sval;}
break;
case 90:
//#line 334 "gramatica.y"
{val_peek(2).sval=Ambito.getAmbito(val_peek(2).sval);
									verificarTipos(val_peek(2).sval,val_peek(0).sval);
									ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));
									ListaTercetos.add_seleccion_cond();
									yyval.sval=val_peek(2).sval;}
break;
case 91:
//#line 341 "gramatica.y"
{yyval.sval= val_peek(0).sval;
						ListaTercetos.add_for_act(id_for_act.pop(),val_peek(1).sval,val_peek(0).sval);
						Ambito.addAmbito("for");}
break;
case 92:
//#line 344 "gramatica.y"
{errorEnXY("Falta +/- para actualizar for");
				ListaTercetos.add_for_act(id_for_act.pop(),"+",val_peek(0).sval);
				Ambito.addAmbito("for");}
break;
case 93:
//#line 349 "gramatica.y"
{ListaTercetos.add_for_cpo();
										if (tercetosContinue.containsKey(Ambito.getNaming())){
											List<Terceto> aux = tercetosContinue.get(Ambito.getNaming());
											tercetosContinue.remove(Ambito.getNaming());
											for (int i = 0; i<aux.size();i++){
												aux.get(i).setSarg("["+(ListaTercetos.getIndice()-3)+"]");
											}
										}
										if (tercetosBreak.containsKey(Ambito.getNaming())){
											List<Terceto> aux = tercetosBreak.get(Ambito.getNaming());
											tercetosBreak.remove(Ambito.getNaming());
											for (int i = 0; i<aux.size();i++){
												aux.get(i).setSarg("["+ListaTercetos.getIndice()+"]");
											}
										}}
break;
case 94:
//#line 364 "gramatica.y"
{ListaTercetos.add_for_cpo();
								if (tercetosContinue.containsKey(Ambito.getNaming())){
									List<Terceto> aux = tercetosContinue.get(Ambito.getNaming());
									tercetosContinue.remove(Ambito.getNaming());
									for (int i = 0; i<aux.size();i++){
										aux.get(i).setSarg("["+(ListaTercetos.getIndice()-3)+"]");
									}
								}
								if (tercetosBreak.containsKey(Ambito.getNaming())){
									List<Terceto> aux = tercetosBreak.get(Ambito.getNaming());
									tercetosBreak.remove(Ambito.getNaming());
									for (int i = 0; i<aux.size();i++){
										aux.get(i).setSarg("["+ListaTercetos.getIndice()+"]");
									}
								}}
break;
case 100:
//#line 390 "gramatica.y"
{Terceto brk=new Terceto("BI","_","_");
					if (tercetosBreak.containsKey(Ambito.getNaming())){
						tercetosBreak.get(Ambito.getNaming()).add(brk);
						ListaTercetos.addTerceto(brk);
					}else{
						List<Terceto> aux = new ArrayList<Terceto>();
						aux.add(brk);
						tercetosBreak.put(Ambito.getNaming(),aux);
						ListaTercetos.addTerceto(brk);
					}}
break;
case 101:
//#line 400 "gramatica.y"
{String amb = Ambito.getAmbito(val_peek(1).sval);
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
								errorEnXY("Etiqueta "+val_peek(1).sval+" no declarada");
							}}
break;
case 102:
//#line 415 "gramatica.y"
{Terceto cont=new Terceto("BI","_","_");
						if (tercetosContinue.containsKey(Ambito.getNaming())){
							tercetosContinue.get(Ambito.getNaming()).add(cont);
							ListaTercetos.addTerceto(cont);
						}else{
							List<Terceto> aux =new ArrayList<Terceto>();
							aux.add(cont);
							tercetosContinue.put(Ambito.getNaming(),aux);
							ListaTercetos.addTerceto(cont);
						}}
break;
//#line 1301 "Parser.java"
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
