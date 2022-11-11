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
    9,    7,    7,    7,   10,   10,   11,   11,   12,   12,
   13,   13,   13,   13,   13,   13,    5,   16,    5,   15,
   15,   15,   15,   15,   17,   17,   17,   14,   14,   14,
   22,   22,   22,   23,   23,   23,   23,   24,   24,   24,
   25,   25,   25,   18,   27,   27,   28,   28,   29,   29,
   26,   32,   33,   33,   33,   33,   33,   33,   19,   19,
   19,   20,   20,   21,   21,   38,   34,   35,   36,   36,
   37,   37,   39,   39,   30,   30,   31,   31,   31,   31,
};
final static short yylen[] = {                            2,
    2,    1,    1,    1,    3,    2,    2,    1,    1,    2,
    2,    1,    1,    3,    2,    1,    1,    1,    1,    3,
    1,    2,    3,    5,    3,    2,    6,    1,    2,    1,
    6,    5,    4,    5,    3,    5,    1,    0,    3,    2,
    2,    2,    2,    1,    3,    2,    4,    3,    3,    1,
    3,    3,    1,    1,    1,    2,    1,    3,    4,    6,
    1,    1,    2,    3,    4,    2,    4,    2,    3,    1,
    3,    3,    1,    1,    1,    1,    1,    1,    4,    3,
    3,    2,    1,    9,   10,    2,    3,    3,    2,    1,
    4,    1,    1,    1,    2,    1,    1,    2,    4,    2,
};
final static short yydefred[] = {                         0,
    2,    3,    4,    0,    0,   21,    0,    0,    0,    0,
   16,   17,    0,    0,   38,    0,    1,    0,    8,    9,
   12,   13,    0,    0,    0,   37,    0,    0,    0,    0,
   44,   83,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   26,    0,   82,    0,    0,    0,    7,   10,   11,
   19,    0,   15,    0,   28,   18,    0,    0,   22,    0,
   40,   41,   42,   43,    0,    0,   55,    0,    0,    0,
   53,   57,   61,   62,   58,    0,    0,    0,    0,    0,
    0,   64,    0,   80,    0,   25,    0,    0,    0,   39,
    5,   14,   20,    0,   29,    0,   23,    0,   56,    0,
    0,    0,    0,   63,    0,   59,    0,   74,   75,   73,
   76,   77,   78,    0,   71,    0,    0,    0,   97,   68,
    0,   66,   79,    0,    0,    0,    0,    0,    0,    0,
   51,   52,    0,    0,   98,    0,  100,    0,   96,    0,
    0,   70,   87,    0,    0,    0,   24,    0,   60,    0,
   67,   95,    0,   65,    0,    0,    0,    0,    0,   99,
   69,    0,   90,   93,   94,    0,    0,    0,   27,    0,
    0,   89,    0,    0,    0,    0,   92,   84,    0,   33,
    0,    0,    0,   32,    0,   34,   85,    0,   31,   91,
};
final static short yydgoto[] = {                          4,
    5,   17,   18,   19,   20,   21,   22,   23,   24,   25,
   59,   60,  158,   69,  119,   46,   27,   28,   29,   30,
   31,   70,   71,   32,   77,   38,   82,   83,  141,  138,
  139,   80,  114,   88,  145,  166,  178,   33,  167,
};
final static short yysindex[] = {                      -154,
    0,    0,    0,    0, -110,    0,  -25,  -13,  -32,   -5,
    0,    0, -167,   59,    0,  109,    0,   39,    0,    0,
    0,    0, -138,   32,  -38,    0,   47,   52,   56,   61,
    0,    0, -142,   -7,    2,   82,   -7, -108,  110, -103,
  122,    0,  131,    0,  -85,  -90,   58,    0,    0,    0,
    0,   41,    0,  -84,    0,    0,  117,  -81,    0,    5,
    0,    0,    0,    0,  137,  131,    0,  -80,   67,   46,
    0,    0,    0,    0,    0,  -79,   54,   -7,   -6,  146,
  -75,    0, -127,    0,  147,    0,  -83,  134,  -25,    0,
    0,    0,    0, -182,    0, -182,    0,  -85,    0,   -7,
   -7,   -7,   -7,    0,   19,    0,   67,    0,    0,    0,
    0,    0,    0,   -7,    0,   83,  135,  124,    0,    0,
  -57,    0,    0,  -53,  -67,   85,  -24,  150,   46,   46,
    0,    0,  165,   67,    0,  -46,    0,   73,    0,  124,
  -43,    0,    0,  -20,  158,  109,    0,  -67,    0,  162,
    0,    0,   91,    0,   -7,   14, -143,   97,  167,    0,
    0,   67,    0,    0,    0,  183,  -21,  -29,    0,   14,
   21,    0,   -7,   37,  192,  124,    0,    0,   38,    0,
  179,   21,  100,    0,  184,    0,    0,  188,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,   18,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  189,    0,  -18,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  242,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   17,    0,    0,
    0,    0,    0,    0,    0,  -41,    0,    0,  195,  -36,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  199,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  203,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -31,   -9,
    0,    0,    0,  222,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  206,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  154,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  155,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    6,   27,   45,    0,    0,   44,  259,    0,
   10,  187,    0,  186,   55,    0,    0,    0,    0,    0,
    0,   48,   63,   31,  181,    0,    0,    0,    0,  -68,
  -14,    0,  140,  191,  139,  120,  112,    0,    0,
};
final static int YYTABLESIZE=396;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         54,
   54,   54,   57,   54,   50,   54,   50,   40,   50,   48,
  173,   48,   16,   48,   35,   68,   57,   54,   54,   54,
   54,   47,   50,   50,   50,   50,   37,   48,   48,   48,
   48,   49,   36,   49,   42,   49,  100,   68,  101,  112,
  111,  113,   75,   44,   49,   57,   76,  118,   96,   49,
   49,   49,   49,  112,  111,  113,  164,   30,  165,   26,
   30,   19,   50,   76,   72,  140,  120,   72,   58,   97,
   26,  153,   26,   49,   56,   54,   19,  181,  185,  100,
  100,  101,  101,   54,   54,   11,   12,  102,   50,   43,
   53,   50,  103,   48,  106,  180,  184,  105,   45,   92,
   90,   26,    1,    2,    3,   61,  142,  183,   72,  100,
   62,  101,    6,    7,   63,   49,    8,    6,   51,   64,
    9,   10,  168,  152,   11,   12,   13,   14,   65,   15,
   72,   72,   72,   72,  121,  122,  147,  126,  152,   58,
  136,  135,   78,  176,   72,    6,    7,  129,  130,    8,
   84,  157,   81,    9,   10,   85,  177,   11,   12,   13,
   14,   86,   15,   48,  131,  132,   89,  177,  152,    8,
   35,   87,   93,    9,   94,   95,   98,   99,  104,   13,
   14,   89,   91,   49,    8,   72,  115,  123,    9,  144,
  124,  116,  125,  137,   13,   14,  117,  151,   72,   89,
   26,   50,    8,   72,  143,  149,    9,  146,  148,  116,
  150,   26,   13,   14,  117,  161,  156,   55,   56,  154,
  160,  169,   79,  171,  188,  170,   39,   66,   67,   11,
   12,   55,  182,   54,   54,   54,  172,  186,   50,   50,
   50,    6,  189,   48,   48,   48,  190,   46,   34,   66,
   67,   41,   86,   45,  108,  109,  110,   81,   73,   74,
   55,   47,   72,  107,   88,   49,   49,   49,  108,  109,
  110,  163,   30,   18,   18,   73,   74,   89,   35,   36,
    8,   52,  127,  155,    9,  133,  159,  116,  128,  175,
   13,   14,  117,  187,    6,    7,    0,    0,    8,  134,
    0,    0,    9,   10,    0,    0,   11,   12,   13,   14,
    0,   15,    0,    6,    7,    0,    0,    8,    0,    0,
    0,    9,   10,    0,    0,   11,   12,   13,   14,   89,
   15,    0,    8,    0,    0,    0,    9,    0,    0,  116,
  162,    0,   13,   14,  117,    0,    0,   89,    0,    0,
    8,    0,    0,  174,    9,    0,   89,  116,  179,    8,
   13,   14,  117,    9,    6,    7,  116,    0,    8,   13,
   14,  117,    9,   10,    0,    0,   11,   12,   13,   14,
   89,   15,    0,    8,    0,    0,    0,    9,    0,    0,
  116,    0,    0,   13,   14,  117,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   41,   45,   41,   47,   43,   40,   45,   41,
   40,   43,  123,   45,   40,   45,   41,   59,   60,   61,
   62,   16,   59,   60,   61,   62,   40,   59,   60,   61,
   62,   41,   58,   43,   40,   45,   43,   45,   45,   60,
   61,   62,   41,   13,   18,   41,   45,  123,   44,   59,
   60,   61,   62,   60,   61,   62,   43,   41,   45,    5,
   44,   44,   18,   45,   34,  123,   81,   37,   25,   60,
   16,  140,   18,   47,  257,   44,   59,   41,   41,   43,
   43,   45,   45,  125,   44,  268,  269,   42,  125,  257,
   59,   47,   47,  125,   41,   59,   59,   44,   40,   59,
   46,   47,  257,  258,  259,   59,  121,  176,   78,   43,
   59,   45,  256,  257,   59,  125,  260,  256,  257,   59,
  264,  265,  266,  138,  268,  269,  270,  271,  271,  273,
  100,  101,  102,  103,  262,  263,  127,   94,  153,   96,
   58,   59,   61,  123,  114,  256,  257,  100,  101,  260,
   41,  146,  261,  264,  265,  259,  171,  268,  269,  270,
  271,   40,  273,  125,  102,  103,  257,  182,  183,  260,
   40,  257,  257,  264,   58,  257,   40,  258,  258,  270,
  271,  257,  125,  157,  260,  155,   41,   41,  264,  257,
  274,  267,   59,   59,  270,  271,  272,  125,  168,  257,
  146,  157,  260,  173,  258,   41,  264,  123,   59,  267,
  257,  157,  270,  271,  272,  125,   59,  256,  257,  263,
   59,  125,   37,   41,  125,   59,  259,  257,  258,  268,
  269,  256,   41,  275,  276,  277,  258,   59,  275,  276,
  277,    0,   59,  275,  276,  277,   59,   59,  274,  257,
  258,  257,  271,   59,  275,  276,  277,   59,  257,  258,
  256,   59,   41,   78,   59,  275,  276,  277,  275,  276,
  277,  258,  256,  256,  257,  257,  258,  257,  125,  125,
  260,   23,   96,  144,  264,  105,  148,  267,   98,  170,
  270,  271,  272,  182,  256,  257,   -1,   -1,  260,  114,
   -1,   -1,  264,  265,   -1,   -1,  268,  269,  270,  271,
   -1,  273,   -1,  256,  257,   -1,   -1,  260,   -1,   -1,
   -1,  264,  265,   -1,   -1,  268,  269,  270,  271,  257,
  273,   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,
  155,   -1,  270,  271,  272,   -1,   -1,  257,   -1,   -1,
  260,   -1,   -1,  168,  264,   -1,  257,  267,  173,  260,
  270,  271,  272,  264,  256,  257,  267,   -1,  260,  270,
  271,  272,  264,  265,   -1,   -1,  268,  269,  270,  271,
  257,  273,   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,
  267,   -1,   -1,  270,  271,  272,
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
"$$1 :",
"ejecutable : defer $$1 inst_ejecutable",
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

//#line 373 "gramatica.y"

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
public TokenLexema tokenAux;
public Stack<Terceto> tercetosAux = new Stack<Terceto>();
public Boolean verb=AnalizadorLexico.getVerbose();
public Stack<String> id_for_act = new Stack<String>();
public Boolean parametroConstante = false;
public HashMap<String, InvocacionFuncion> llamadasFunciones = new HashMap<String,InvocacionFuncion>();
public Stack<String> etiquetas = new Stack<String>();
public HashMap<String,List<Terceto>> tercetosContinue = new HashMap<String,List<Terceto>>();
public HashMap<String,List<Terceto>> tercetosBreak = new HashMap<String,List<Terceto>>();
public HashMap<String,List<Terceto>> tercetosBreakET = new HashMap<String,List<Terceto>>();

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
	String aux1 = arg1;
	while (aux1.startsWith("[")){
		aux1=ListaTercetos.getTerceto(aux1).getSarg();
	}
	String aux2=arg2;
	while (aux2.startsWith("[")){
		aux2=ListaTercetos.getTerceto(aux2).getSarg();
	}
	//System.out.println("Verificando tipos "+aux1+";"+aux2);
	if (TablaSimbolos.getSimbolo(aux1).getTipo().equals(TablaSimbolos.getSimbolo(aux2).getTipo()))
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
	if (verb){
		int linea,caracter=0;
		linea = AnalizadorLexico.getLinea();
		caracter = AnalizadorLexico.getCaracter();
		System.out.println(ANSI_YELLOW+"!|/|/|! Warning en linea: "+linea+", caracter: "+caracter+"\n"+msg+"\n"+ANSI_RESET);
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
		System.out.println(ANSI_RED+"!|!|!|!: El programa encontro "+yynerrs+" errores al compilarse."+ANSI_RESET);
		return;
	}
	System.out.println(ANSI_GREEN+"%|%|%|%: El programa compilo sin errores."+ANSI_RESET);
	TablaSimbolos.imprimirTabla();
	ListaTercetos.imprimir();
	GestorAssembler.procesarArchivo();
	GestorAssembler.imprimir();
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
//#line 599 "Parser.java"
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
{imprimirMSGEstructura("Declaracion de funcion");}
break;
case 15:
//#line 47 "gramatica.y"
{errorEnXY("Tipo de la/s variable/s esperado al comienzo de la sentencia");}
break;
case 16:
//#line 50 "gramatica.y"
{tipoAux=val_peek(0).sval;yyval.sval=val_peek(0).sval;}
break;
case 17:
//#line 51 "gramatica.y"
{tipoAux=val_peek(0).sval;yyval.sval=val_peek(0).sval;}
break;
case 18:
//#line 52 "gramatica.y"
{errorEnXY("Tipo de la/s variable/s invalido");}
break;
case 19:
//#line 55 "gramatica.y"
{setearTipo(val_peek(0).sval);setearUso(val_peek(0).sval,"Variable");val_peek(0).sval=TablaSimbolos.cambiarNombreKey(val_peek(0).sval);}
break;
case 20:
//#line 56 "gramatica.y"
{setearTipo(val_peek(0).sval);setearUso(val_peek(0).sval,"Variable");val_peek(0).sval=TablaSimbolos.cambiarNombreKey(val_peek(0).sval);}
break;
case 21:
//#line 57 "gramatica.y"
{errorEnXY("Se esperaba un identificador o una lista de identificadores separados por ,");}
break;
case 23:
//#line 61 "gramatica.y"
{llamadasFunciones.get(val_peek(2).sval).setPar1(val_peek(1).sval);}
break;
case 24:
//#line 62 "gramatica.y"
{InvocacionFuncion f = llamadasFunciones.get(val_peek(4).sval);
																f.setPar1(val_peek(3).sval);
																f.setPar2(val_peek(1).sval);}
break;
case 25:
//#line 67 "gramatica.y"
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
							yyval.sval=val_peek(1).sval;}
break;
case 26:
//#line 79 "gramatica.y"
{errorEnXY("La declaracion de la funcion necesita un nombre");}
break;
case 27:
//#line 82 "gramatica.y"
{tokenAux=tokens.pop();
												tokenAux.setTipo(val_peek(3).sval);
												verificarTipos(tokenAux.getLexema().toString(),val_peek(1).sval);
												Terceto terAux=new Terceto("Stack",val_peek(1).sval,"_");
												ListaTercetos.addTerceto(terAux);
												Ambito.removeAmbito();
												terAux=new Terceto("BI","TerRetFuncion:_","_");
												ListaTercetos.addTerceto(terAux);
												terAux=tercetosAux.pop();
												terAux.setSarg("["+ListaTercetos.getIndice()+"]");
												}
break;
case 28:
//#line 93 "gramatica.y"
{errorEnXY("En la declaracion de la funcion falta: ),:,{ o }");}
break;
case 29:
//#line 96 "gramatica.y"
{setearTipo(val_peek(0).sval,val_peek(1).sval);
					setearUso(val_peek(0).sval,"Nombre de Parametro");
					val_peek(0).sval=TablaSimbolos.cambiarNombreKey(val_peek(0).sval);}
break;
case 30:
//#line 99 "gramatica.y"
{errorEnXY("Identificador del parametro esperado  en la declaracion de funcion");}
break;
case 31:
//#line 102 "gramatica.y"
{yyval.sval=val_peek(2).sval;}
break;
case 32:
//#line 103 "gramatica.y"
{errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 33:
//#line 104 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo y final de la expresion");}
break;
case 34:
//#line 105 "gramatica.y"
{errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 35:
//#line 106 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo y final de la expresion y ; al final de linea");}
break;
case 36:
//#line 107 "gramatica.y"
{errorEnXY("; esperados al final de linea");}
break;
case 38:
//#line 112 "gramatica.y"
{ListaTercetos.setDefer(true);}
break;
case 39:
//#line 112 "gramatica.y"
{ListaTercetos.setDefer(false);
																imprimirMSGEstructura("Defer de instruccion ejecutable");}
break;
case 40:
//#line 116 "gramatica.y"
{imprimirMSGEstructura("Asignacion");}
break;
case 41:
//#line 117 "gramatica.y"
{imprimirMSGEstructura("Seleccion If");}
break;
case 42:
//#line 118 "gramatica.y"
{imprimirMSGEstructura("Impresion a Consola");}
break;
case 43:
//#line 119 "gramatica.y"
{imprimirMSGEstructura("Invocacion de Funcion");}
break;
case 44:
//#line 120 "gramatica.y"
{imprimirMSGEstructura("Loop For");}
break;
case 45:
//#line 123 "gramatica.y"
{comprobarBinding(val_peek(2).sval,"Variable "+val_peek(2).sval+" no declarada");
											val_peek(2).sval=Ambito.getAmbito(val_peek(2).sval);
											verificarTipos(val_peek(2).sval,val_peek(0).sval);
											if (TablaSimbolos.getSimbolo(val_peek(2).sval).getUso().equals("Nombre de Parametro")){
												TablaSimbolos.getSimbolo(Ambito.getNombreAmbito()).setEsp(true);
											}
											ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 46:
//#line 130 "gramatica.y"
{errorEnXY("Expresion esperada despues de la asignacion");}
break;
case 47:
//#line 131 "gramatica.y"
{errorEnXY("Operador de asignacion incorrecto, se esperaba -> =:");}
break;
case 48:
//#line 134 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
									yyval.sval="["+ListaTercetos.getIndice()+"]";
									ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 49:
//#line 137 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
									yyval.sval="["+ListaTercetos.getIndice()+"]";
									ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 51:
//#line 143 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
							yyval.sval="["+ListaTercetos.getIndice()+"]";
							ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 52:
//#line 146 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
							yyval.sval="["+ListaTercetos.getIndice()+"]";
							ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 54:
//#line 152 "gramatica.y"
{comprobarBinding(val_peek(0).sval,"Variable "+val_peek(0).sval+" no declarada");
			val_peek(0).sval=Ambito.getAmbito(val_peek(0).sval);
			yyval.sval=val_peek(0).sval;}
break;
case 56:
//#line 156 "gramatica.y"
{verificarRangoDoubleNegativo();
					val_peek(0).sval="-"+val_peek(0).sval;
					TablaSimbolos.addSimbolo(new TokenLexema(258, val_peek(0).sval,"f64"));
					yyval.sval=val_peek(0).sval;}
break;
case 58:
//#line 163 "gramatica.y"
{comprobarBinding(val_peek(2).sval,"Funcion "+val_peek(2).sval+" no declarada");
								val_peek(2).sval=Ambito.getAmbito(val_peek(2).sval);
								yyval.sval=Ambito.getAmbito(val_peek(2).sval);
								Terceto terAux=new Terceto("Stack","TerRetFuncion:_","_");
								ListaTercetos.addTerceto(terAux);
								terAux=new Terceto("=:","TerRetFuncion:_","["+(ListaTercetos.getIndice()+2)+"]");
								ListaTercetos.addTerceto(terAux);
								terAux=new Terceto("BI",llamadasFunciones.get(val_peek(2).sval).getTercetoInv(),"_");
								ListaTercetos.addTerceto(terAux);
								terAux=new Terceto("Pop",val_peek(2).sval,"_");
								ListaTercetos.addTerceto(terAux);
								terAux=new Terceto("Pop","TerRetFuncion:_","_");
								ListaTercetos.addTerceto(terAux);}
break;
case 59:
//#line 176 "gramatica.y"
{comprobarBinding(val_peek(3).sval,"Funcion "+val_peek(3).sval+" no declarada");
									val_peek(3).sval=Ambito.getAmbito(val_peek(3).sval);
									if (TablaSimbolos.getSimbolo(val_peek(3).sval).getEsp()&&parametroConstante){
										errorEnXY("El parametro "+val_peek(1).sval+", no puede ser constante debido a que hay asignaciones en la funcion "+val_peek(3).sval);
										parametroConstante=false;
									}
									Terceto terAux=new Terceto("Stack","TerRetFuncion:_","_");
									ListaTercetos.addTerceto(terAux);
									terAux=new Terceto("=:","TerRetFuncion:_","["+(ListaTercetos.getIndice()+2)+"]");
									ListaTercetos.addTerceto(terAux);
									terAux=new Terceto("BI",llamadasFunciones.get(val_peek(3).sval).getTercetoInv(),"_");
									ListaTercetos.addTerceto(terAux);
									terAux=new Terceto("Pop",val_peek(3).sval,"_");
									ListaTercetos.addTerceto(terAux);
									terAux=new Terceto("Pop","TerRetFuncion:_","_");
									ListaTercetos.addTerceto(terAux);
									yyval.sval=val_peek(3).sval;}
break;
case 60:
//#line 193 "gramatica.y"
{comprobarBinding(val_peek(5).sval,"Funcion "+val_peek(5).sval+" no declarada");
														val_peek(5).sval=Ambito.getAmbito(val_peek(5).sval);
														if (TablaSimbolos.getSimbolo(val_peek(5).sval).getEsp()&&parametroConstante){
															errorEnXY("Los parametros no pueden ser constantes debido a que hay asignaciones en la funcion "+val_peek(5).sval);
															parametroConstante=false;
														}
														Terceto terAux=new Terceto("Stack","TerRetFuncion:_","_");
														ListaTercetos.addTerceto(terAux);
														terAux=new Terceto("=:","TerRetFuncion:_","["+(ListaTercetos.getIndice()+2)+"]");
														ListaTercetos.addTerceto(terAux);
														terAux=new Terceto("BI",llamadasFunciones.get(val_peek(5).sval).getTercetoInv(),"_");
														ListaTercetos.addTerceto(terAux);
														terAux=new Terceto("Pop",val_peek(5).sval,"_");
														ListaTercetos.addTerceto(terAux);
														terAux=new Terceto("Pop","TerRetFuncion:_","_");
														ListaTercetos.addTerceto(terAux);
														yyval.sval=Ambito.getAmbito(val_peek(5).sval);}
break;
case 61:
//#line 212 "gramatica.y"
{comprobarBinding(val_peek(0).sval,"No se encontro el parametro "+val_peek(0).sval);}
break;
case 62:
//#line 213 "gramatica.y"
{parametroConstante=true;}
break;
case 63:
//#line 214 "gramatica.y"
{verificarRangoDoubleNegativo();val_peek(0).sval="-"+val_peek(0).sval;TablaSimbolos.addSimbolo(new TokenLexema(258, val_peek(0).sval,"f64"));yyval.sval=val_peek(0).sval;}
break;
case 64:
//#line 217 "gramatica.y"
{ListaTercetos.add_seleccion_final();}
break;
case 67:
//#line 224 "gramatica.y"
{ListaTercetos.add_seleccion_then();}
break;
case 68:
//#line 225 "gramatica.y"
{ListaTercetos.add_seleccion_then();}
break;
case 71:
//#line 232 "gramatica.y"
{ListaTercetos.add_seleccion_cond();}
break;
case 72:
//#line 235 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
													ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 79:
//#line 247 "gramatica.y"
{ListaTercetos.addTerceto(new Terceto(val_peek(3).sval,val_peek(1).sval,"_"));}
break;
case 80:
//#line 248 "gramatica.y"
{errorEnXY("Falta de parentesis al comienzo de la cadena del out");}
break;
case 81:
//#line 249 "gramatica.y"
{errorEnXY("Falta de parentesis al final de la cadena del out");}
break;
case 82:
//#line 252 "gramatica.y"
{ListaTercetos.removeTerceto(ListaTercetos.getIndice()-2);}
break;
case 83:
//#line 253 "gramatica.y"
{errorEnXY("Funcion invocada sin discard del retorno");}
break;
case 84:
//#line 256 "gramatica.y"
{Ambito.removeAmbito();
																		verificarIdIguales(val_peek(6).sval,val_peek(4).sval);
																		verificarTipos(val_peek(6).sval,val_peek(4).sval);
																		verificarTipos(val_peek(6).sval,val_peek(2).sval);}
break;
case 85:
//#line 260 "gramatica.y"
{Ambito.removeAmbito();
																			verificarIdIguales(val_peek(6).sval,val_peek(4).sval);
																			verificarTipos(val_peek(6).sval,val_peek(4).sval);
																			verificarTipos(val_peek(6).sval,val_peek(2).sval);
																			if (tercetosBreakET.containsKey(val_peek(9).sval)){
																				List<Terceto> aux = tercetosBreakET.get(val_peek(9).sval);
																				tercetosBreakET.remove(val_peek(9).sval);
																				for (int i = 0; i<aux.size();i++){
																					aux.get(i).setSarg("["+ListaTercetos.getIndice()+"]");
																				}
																			}}
break;
case 86:
//#line 273 "gramatica.y"
{setearUso(val_peek(1).sval,"Etiqueta");
					yyval.sval=TablaSimbolos.cambiarNombreKey(val_peek(1).sval);}
break;
case 87:
//#line 277 "gramatica.y"
{val_peek(2).sval=Ambito.getAmbito(val_peek(2).sval);
									verificarEntero(val_peek(2).sval);
									verificarTipos(val_peek(2).sval,val_peek(0).sval);
									ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));
									id_for_act.push(val_peek(2).sval);
									yyval.sval=val_peek(2).sval;}
break;
case 88:
//#line 285 "gramatica.y"
{val_peek(2).sval=Ambito.getAmbito(val_peek(2).sval);
									verificarTipos(val_peek(2).sval,val_peek(0).sval);
									ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));
									ListaTercetos.add_seleccion_cond();
									yyval.sval=val_peek(2).sval;}
break;
case 89:
//#line 292 "gramatica.y"
{yyval.sval= val_peek(0).sval;
						ListaTercetos.add_for_act(id_for_act.pop(),val_peek(1).sval,val_peek(0).sval);
						Ambito.addAmbito("for");}
break;
case 90:
//#line 295 "gramatica.y"
{errorEnXY("Falta +/- para actualizar for");}
break;
case 91:
//#line 298 "gramatica.y"
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
case 92:
//#line 313 "gramatica.y"
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
case 98:
//#line 339 "gramatica.y"
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
case 99:
//#line 349 "gramatica.y"
{String amb = Ambito.getAmbito(val_peek(1).sval);
							Terceto brk=new Terceto("BI","_","_");
							if (tercetosBreakET.containsKey(amb)){
								tercetosBreakET.get(amb).add(brk);
								ListaTercetos.addTerceto(brk);
							}else{
								List<Terceto> aux = new ArrayList<Terceto>();
								aux.add(brk);
								tercetosBreakET.put(amb,aux);
								ListaTercetos.addTerceto(brk);
							}}
break;
case 100:
//#line 360 "gramatica.y"
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
//#line 1219 "Parser.java"
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
