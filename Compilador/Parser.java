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
    9,    7,   13,    7,   14,    7,   10,   10,   15,   11,
   11,   12,   12,   16,   16,   16,   16,   16,   16,    5,
   19,    5,   18,   18,   18,   18,   18,   20,   20,   20,
   17,   17,   17,   25,   25,   25,   26,   26,   26,   26,
   27,   27,   27,   28,   28,   28,   21,   30,   30,   31,
   31,   32,   32,   29,   35,   36,   36,   36,   36,   36,
   36,   22,   22,   22,   23,   23,   24,   24,   41,   37,
   38,   39,   39,   40,   40,   42,   42,   33,   33,   34,
   34,   34,   34,
};
final static short yylen[] = {                            2,
    2,    1,    1,    1,    3,    2,    2,    1,    1,    2,
    2,    1,    1,    3,    2,    1,    1,    1,    1,    3,
    1,    2,    0,    4,    0,    6,    3,    2,    0,    7,
    1,    2,    1,    6,    5,    4,    5,    3,    5,    1,
    0,    3,    2,    2,    2,    2,    1,    3,    2,    4,
    3,    3,    1,    3,    3,    1,    1,    1,    2,    1,
    3,    4,    6,    1,    1,    2,    3,    4,    2,    4,
    2,    3,    1,    3,    3,    1,    1,    1,    1,    1,
    1,    4,    3,    3,    2,    1,    9,   10,    2,    3,
    3,    2,    1,    4,    1,    1,    1,    2,    1,    1,
    2,    4,    2,
};
final static short yydefred[] = {                         0,
    2,    3,    4,    0,    0,   21,    0,    0,    0,    0,
   16,   17,    0,    0,   41,    0,    1,    0,    8,    9,
   12,   13,    0,    0,    0,   40,    0,    0,    0,    0,
   47,   86,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   28,    0,   85,    0,    0,    0,    7,   10,   11,
   19,    0,   15,    0,   31,   18,   29,    0,   22,    0,
   43,   44,   45,   46,    0,    0,   58,    0,    0,    0,
   56,   60,   64,   65,   61,    0,    0,    0,    0,    0,
    0,   67,    0,   83,    0,   27,    0,    0,    0,   42,
    5,   14,   20,    0,   32,    0,    0,    0,   59,    0,
    0,    0,    0,   66,    0,   62,    0,   77,   78,   76,
   79,   80,   81,    0,   74,    0,    0,    0,  100,   71,
    0,   69,   82,    0,    0,    0,   25,   24,    0,    0,
    0,   54,   55,    0,    0,  101,    0,  103,    0,   99,
    0,    0,   73,   90,    0,    0,    0,    0,    0,   63,
    0,   70,   98,    0,   68,    0,    0,    0,   26,    0,
  102,   72,    0,   93,   96,   97,    0,    0,    0,    0,
    0,    0,   92,    0,   30,    0,    0,   95,   87,    0,
    0,    0,    0,    0,   36,    0,   88,    0,   35,    0,
   37,   94,   34,
};
final static short yydgoto[] = {                          4,
    5,   17,   18,   19,   20,   21,   22,   23,   24,   25,
   59,   60,   97,  148,   94,  170,   69,  119,   46,   27,
   28,   29,   30,   31,   70,   71,   32,   77,   38,   82,
   83,  142,  139,  140,   80,  114,   88,  146,  167,  179,
   33,  168,
};
final static short yysindex[] = {                      -135,
    0,    0,    0,    0, -110,    0,  -25,   50,  -32,   -5,
    0,    0, -188,   58,    0,  122,    0,  -76,    0,    0,
    0,    0, -171,   49,  -38,    0,   22,   45,   51,   60,
    0,    0, -145,   -7,    2,   69,   -7, -123,   99, -117,
  111,    0,  113,    0, -100, -143,   46,    0,    0,    0,
    0,   53,    0,  -93,    0,    0,    0,  -89,    0,  126,
    0,    0,    0,    0,  134,  113,    0,  -81,   96,   41,
    0,    0,    0,    0,    0,  -75,   74,   -7,   -6,  141,
  -57,    0, -114,    0,  144,    0,  -84,  132,  -25,    0,
    0,    0,    0,  143,    0, -132,  -24, -100,    0,   -7,
   -7,   -7,   -7,    0,   18,    0,   96,    0,    0,    0,
    0,    0,    0,   -7,    0,  108,  140, -165,    0,    0,
   20,    0,    0,  -54,  -51, -132,    0,    0,  149,   41,
   41,    0,    0,  176,   96,    0,  -33,    0,   61,    0,
 -165,  -42,    0,    0,  -20,  166,  103,  -24,  -51,    0,
  174,    0,    0,   77,    0,   -7,    3,  122,    0,  179,
    0,    0,   96,    0,    0,    0,  201,  -15,  104,  123,
    3,   29,    0,  -29,    0,  206, -165,    0,    0,   -7,
   32,   29,   95,   37,    0,  194,    0,  195,    0,  199,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,   17,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  203,    0,   -8,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  264,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   16,    0,  -19,
    0,    0,    0,    0,    0,  -41,    0,    0,  219,  -36,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  220,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  222,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -31,
   -9,    0,    0,    0,  224,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  223,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  158,    0,    0,    0,    0,    0,    0,    0,    0,  160,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   11,   27,   40,    0,    0,   39,  265,    0,
  -35,  198,    0,    0,    0,    0,   42,   54,    0,    0,
    0,    0,    0,    0,   75,   76,   31,  190,    0,    0,
    0,    0,  -74,  -10,    0,  152,  200,  155,  136,  127,
    0,    0,
};
final static int YYTABLESIZE=395;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         57,
   57,   57,   57,   57,   53,   57,   53,   40,   53,   51,
  180,   51,   16,   51,   35,   68,   57,   57,   57,   57,
   57,   23,   53,   53,   53,   53,   47,   51,   51,   51,
   51,   52,   36,   52,   42,   52,  100,   68,  101,  112,
  111,  113,   75,   44,   49,  165,   76,  166,   48,   52,
   52,   52,   52,  112,  111,  113,   33,   50,   26,   33,
   19,  128,   76,   58,   72,  118,  154,   72,   43,   26,
  120,   26,  186,   49,  100,   19,  101,  190,   79,  100,
   61,  101,  102,   57,    6,   51,   50,  103,   53,   37,
  185,   89,   54,   51,    8,  189,   54,   45,    9,   90,
   26,  116,  183,   62,   13,   14,  117,   53,   72,   63,
  143,   92,  159,   89,  106,   52,    8,  105,   64,  107,
    9,    1,    2,    3,   56,   65,   13,   14,  153,   78,
   72,   72,   72,   72,   58,   11,   12,   81,  100,   84,
  101,   85,  141,  153,   72,    6,    7,  121,  122,    8,
   86,  177,   35,    9,   10,  135,   87,   11,   12,   13,
   14,  178,   15,   93,  147,  137,  136,   95,  169,   96,
   91,  178,  153,   98,  130,  131,   99,  132,  133,    6,
    7,  115,  104,    8,  123,  152,   72,    9,   10,  124,
  125,   11,   12,   13,   14,   49,   15,  163,  138,   89,
  126,  162,    8,  144,   72,  145,    9,  149,   50,  116,
   72,   26,   13,   14,  117,  181,  150,   55,   56,  188,
  155,  184,   26,  151,  157,  158,   39,   66,   67,   11,
   12,   55,  161,   57,   57,   57,   23,  171,   53,   53,
   53,  172,  173,   51,   51,   51,  182,  175,   34,   66,
   67,   41,  191,  192,  108,  109,  110,  193,   73,   74,
  164,   49,   89,    6,   75,   52,   52,   52,  108,  109,
  110,   33,   18,   18,   73,   74,   89,   48,   84,    8,
   50,   91,   38,    9,   39,   89,  116,   52,    8,   13,
   14,  117,    9,  127,  134,  116,  156,  129,   13,   14,
  117,    6,    7,  160,    0,    8,  176,    0,  187,    9,
   10,    0,    0,   11,   12,   13,   14,   89,   15,    0,
    8,    0,    0,    0,    9,    0,    0,  116,    0,    0,
   13,   14,  117,   89,    0,    0,    8,    0,    0,    0,
    9,    0,    0,  116,    0,    0,   13,   14,  117,    0,
    0,   89,    0,    0,    8,    0,    0,    0,    9,    6,
    7,  116,    0,    8,   13,   14,  117,    9,   10,  174,
    0,   11,   12,   13,   14,    0,   15,    6,    7,    0,
    0,    8,    0,    0,    0,    9,   10,    0,    0,   11,
   12,   13,   14,    0,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   41,   45,   41,   47,   43,   40,   45,   41,
   40,   43,  123,   45,   40,   45,   41,   59,   60,   61,
   62,   41,   59,   60,   61,   62,   16,   59,   60,   61,
   62,   41,   58,   43,   40,   45,   43,   45,   45,   60,
   61,   62,   41,   13,   18,   43,   45,   45,  125,   59,
   60,   61,   62,   60,   61,   62,   41,   18,    5,   44,
   44,   97,   45,   25,   34,  123,  141,   37,  257,   16,
   81,   18,   41,   47,   43,   59,   45,   41,   37,   43,
   59,   45,   42,  125,  256,  257,   47,   47,  125,   40,
   59,  257,   44,  125,  260,   59,   44,   40,  264,   46,
   47,  267,  177,   59,  270,  271,  272,   59,   78,   59,
  121,   59,  148,  257,   41,  125,  260,   44,   59,   78,
  264,  257,  258,  259,  257,  271,  270,  271,  139,   61,
  100,  101,  102,  103,   96,  268,  269,  261,   43,   41,
   45,  259,  123,  154,  114,  256,  257,  262,  263,  260,
   40,  123,   40,  264,  265,  114,  257,  268,  269,  270,
  271,  172,  273,  257,  126,   58,   59,  257,  158,   44,
  125,  182,  183,   40,  100,  101,  258,  102,  103,  256,
  257,   41,  258,  260,   41,  125,  156,  264,  265,  274,
   59,  268,  269,  270,  271,  169,  273,  156,   59,  257,
   58,  125,  260,  258,  174,  257,  264,   59,  169,  267,
  180,  158,  270,  271,  272,  174,   41,  256,  257,  125,
  263,  180,  169,  257,   59,  123,  259,  257,  258,  268,
  269,  256,   59,  275,  276,  277,  256,   59,  275,  276,
  277,   41,  258,  275,  276,  277,   41,  125,  274,  257,
  258,  257,   59,   59,  275,  276,  277,   59,  257,  258,
  258,   59,  271,    0,   41,  275,  276,  277,  275,  276,
  277,  256,  256,  257,  257,  258,  257,   59,   59,  260,
   59,   59,  125,  264,  125,  257,  267,   23,  260,  270,
  271,  272,  264,   96,  105,  267,  145,   98,  270,  271,
  272,  256,  257,  149,   -1,  260,  171,   -1,  182,  264,
  265,   -1,   -1,  268,  269,  270,  271,  257,  273,   -1,
  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,
  270,  271,  272,  257,   -1,   -1,  260,   -1,   -1,   -1,
  264,   -1,   -1,  267,   -1,   -1,  270,  271,  272,   -1,
   -1,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,  256,
  257,  267,   -1,  260,  270,  271,  272,  264,  265,  266,
   -1,  268,  269,  270,  271,   -1,  273,  256,  257,   -1,
   -1,  260,   -1,   -1,   -1,  264,  265,   -1,   -1,  268,
  269,  270,  271,   -1,  273,
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
"$$3 :",
"cola_funcion : ')' $$3 ':' tipo '{' cuerpo_fun '}'",
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
"$$4 :",
"ejecutable : defer $$4 inst_ejecutable",
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

//#line 433 "gramatica.y"

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
//#line 636 "Parser.java"
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
{Terceto.GetTerceto(llamadasFunciones.get(val_peek(0).sval).getTercetoInv()).setCarg(true);
						imprimirMSGEstructura("Declaracion de funcion");}
break;
case 15:
//#line 48 "gramatica.y"
{errorEnXY("Tipo de la/s variable/s esperado al comienzo de la sentencia");}
break;
case 16:
//#line 51 "gramatica.y"
{tipoAux=val_peek(0).sval;}
break;
case 17:
//#line 52 "gramatica.y"
{tipoAux=val_peek(0).sval;}
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
//#line 62 "gramatica.y"
{llamadasFunciones.get(val_peek(1).sval).setPar1(val_peek(0).sval);}
break;
case 25:
//#line 63 "gramatica.y"
{
													InvocacionFuncion f = llamadasFunciones.get(val_peek(3).sval);
													f.setPar1(val_peek(2).sval); 
													f.setPar2(val_peek(0).sval);
												}
break;
case 27:
//#line 71 "gramatica.y"
{setearUso(val_peek(1).sval,"Nombre de Funcion");
							String aux=TablaSimbolos.cambiarNombreKey(val_peek(1).sval);
							Ambito.addAmbito(val_peek(1).sval);
							val_peek(1).sval=aux;
							tokens.push(TablaSimbolos.getSimbolo(val_peek(1).sval));
							Terceto terAux = new Terceto("BI","","_");
							tercetosAux.push(terAux);
							ListaTercetos.addTerceto(terAux);
							InvocacionFuncion f = new InvocacionFuncion();
							f.setTercetoInv("["+(Terceto.GetIndice())+"]");
							llamadasFunciones.put(val_peek(1).sval,f);
							terAux = new Terceto("MOV","EBX",String.valueOf(Terceto.GetIndice()));
							ListaTercetos.addTerceto(terAux);
							ListaTercetos.addTerceto(new Terceto("POP","DX","_"));
							yyval.sval=val_peek(1).sval;}
break;
case 28:
//#line 86 "gramatica.y"
{errorEnXY("La declaracion de la funcion necesita un nombre");}
break;
case 29:
//#line 89 "gramatica.y"
{ListaTercetos.addTerceto(new Terceto("PUSH","DX","_"));}
break;
case 30:
//#line 89 "gramatica.y"
{TokenLexema tokenAux=tokens.pop();
												if(tokenAux!=null){
													Ambito.removeAmbito();
													tokenAux.setTipo(val_peek(3).sval);
													verificarTipos(tokenAux.getLexema().toString(),val_peek(1).sval);
													ListaTercetos.addTerceto(new Terceto("POP","DX","_"));
													Terceto terAux=new Terceto("Push",val_peek(1).sval,"_");
													ListaTercetos.addTerceto(terAux);
													ListaTercetos.addTerceto(new Terceto("PUSH","DX","_"));
													/*Ambito.removeAmbito();*/
													terAux=new Terceto("RET","_","_");
													ListaTercetos.addTerceto(terAux);
													terAux=tercetosAux.pop();
													terAux.setSarg("["+Terceto.GetIndice()+"]");
												}}
break;
case 31:
//#line 104 "gramatica.y"
{errorEnXY("En la declaracion de la funcion falta: ),:,{ o }");}
break;
case 32:
//#line 107 "gramatica.y"
{setearTipo(val_peek(0).sval,val_peek(1).sval);
					setearUso(val_peek(0).sval,"Nombre de Parametro");
					yyval.sval=TablaSimbolos.cambiarNombreKey(val_peek(0).sval);
					variablesInicializadas.add(yyval.sval);
					ListaTercetos.addTerceto(new Terceto("Pop",yyval.sval,"_"));}
break;
case 33:
//#line 112 "gramatica.y"
{errorEnXY("Identificador del parametro esperado  en la declaracion de funcion");}
break;
case 34:
//#line 115 "gramatica.y"
{yyval.sval=val_peek(2).sval; }
break;
case 35:
//#line 116 "gramatica.y"
{yyval.sval=val_peek(1).sval;errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 36:
//#line 117 "gramatica.y"
{yyval.sval=val_peek(1).sval;errorEnXY("Parentesis esperados al comienzo y final de la expresion");}
break;
case 37:
//#line 118 "gramatica.y"
{yyval.sval=val_peek(2).sval;errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 38:
//#line 119 "gramatica.y"
{yyval.sval=val_peek(0).sval;errorEnXY("Parentesis esperados al comienzo y final de la expresion y ; al final de linea");}
break;
case 39:
//#line 120 "gramatica.y"
{yyval.sval=val_peek(1).sval;errorEnXY("; esperados al final de linea");}
break;
case 41:
//#line 125 "gramatica.y"
{ListaTercetos.setDefer(true); diferido=true;}
break;
case 42:
//#line 125 "gramatica.y"
{ListaTercetos.setDefer(false); diferido=false; imprimirMSGEstructura("Defer de instruccion ejecutable");}
break;
case 43:
//#line 128 "gramatica.y"
{imprimirMSGEstructura("Asignacion");}
break;
case 44:
//#line 129 "gramatica.y"
{imprimirMSGEstructura("Seleccion If");}
break;
case 45:
//#line 130 "gramatica.y"
{imprimirMSGEstructura("Impresion a Consola");}
break;
case 46:
//#line 131 "gramatica.y"
{imprimirMSGEstructura("Invocacion de Funcion");}
break;
case 47:
//#line 132 "gramatica.y"
{imprimirMSGEstructura("Loop For");}
break;
case 48:
//#line 135 "gramatica.y"
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
case 49:
//#line 153 "gramatica.y"
{errorEnXY("Expresion esperada despues de la asignacion");}
break;
case 50:
//#line 154 "gramatica.y"
{errorEnXY("Operador de asignacion incorrecto, se esperaba -> =:");}
break;
case 51:
//#line 157 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
									yyval.sval="["+Terceto.GetIndice()+"]";
									ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 52:
//#line 160 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
									yyval.sval="["+Terceto.GetIndice()+"]";
									ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 54:
//#line 166 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
							yyval.sval="["+Terceto.GetIndice()+"]";
							ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 55:
//#line 169 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
							yyval.sval="["+Terceto.GetIndice()+"]";
							ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 57:
//#line 175 "gramatica.y"
{comprobarBinding(val_peek(0).sval,"Variable "+val_peek(0).sval+" no declarada");
			val_peek(0).sval=Ambito.getAmbito(val_peek(0).sval);
			if(!TablaSimbolos.getSimbolo(val_peek(0).sval).getUso().equals("Nombre de Parametro"))
				comprobarInicializada(val_peek(0).sval,Ambito.getNaming());
			yyval.sval=val_peek(0).sval;}
break;
case 59:
//#line 181 "gramatica.y"
{verificarRangoDoubleNegativo();
					val_peek(0).sval="-"+val_peek(0).sval;
					TablaSimbolos.addSimbolo(new TokenLexema(258, val_peek(0).sval,"f64"));
					yyval.sval=val_peek(0).sval;}
break;
case 61:
//#line 188 "gramatica.y"
{comprobarBinding(val_peek(2).sval,"Funcion "+val_peek(2).sval+" no declarada");
								if(Ambito.getAmbito(val_peek(2).sval)!=null){
									comprobarParametrosFuncion(val_peek(2).sval,0);
									val_peek(2).sval=Ambito.getAmbito(val_peek(2).sval);
									yyval.sval=Ambito.getAmbito(val_peek(2).sval);
									Terceto terAux=new Terceto("Push","EBX","_");
									ListaTercetos.addTerceto(terAux);
									String tercetoLlamado = llamadasFunciones.get(val_peek(2).sval).getTercetoInv();
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
case 62:
//#line 207 "gramatica.y"
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
case 63:
//#line 228 "gramatica.y"
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
case 64:
//#line 256 "gramatica.y"
{comprobarBinding(val_peek(0).sval,"No se encontro el parametro "+val_peek(0).sval);
					if(Ambito.getAmbito(val_peek(0).sval)!=null)
						comprobarInicializada(Ambito.getAmbito(val_peek(0).sval),Ambito.getNaming());}
break;
case 66:
//#line 260 "gramatica.y"
{verificarRangoDoubleNegativo();
					val_peek(0).sval="-"+val_peek(0).sval;TablaSimbolos.addSimbolo(new TokenLexema(258, val_peek(0).sval,"f64"));
					yyval.sval=val_peek(0).sval;}
break;
case 67:
//#line 265 "gramatica.y"
{ListaTercetos.add_seleccion_final();}
break;
case 70:
//#line 272 "gramatica.y"
{ListaTercetos.add_seleccion_then();}
break;
case 71:
//#line 273 "gramatica.y"
{ListaTercetos.add_seleccion_then();}
break;
case 74:
//#line 280 "gramatica.y"
{ListaTercetos.add_seleccion_cond();}
break;
case 75:
//#line 283 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);
													ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 82:
//#line 295 "gramatica.y"
{ListaTercetos.addTerceto(new Terceto(val_peek(3).sval,val_peek(1).sval,"_"));}
break;
case 83:
//#line 296 "gramatica.y"
{errorEnXY("Falta de parentesis al comienzo de la cadena del out");}
break;
case 84:
//#line 297 "gramatica.y"
{errorEnXY("Falta de parentesis al final de la cadena del out");}
break;
case 86:
//#line 301 "gramatica.y"
{errorEnXY("Funcion invocada sin discard del retorno");}
break;
case 87:
//#line 304 "gramatica.y"
{Ambito.removeAmbito();
																		if(val_peek(6).sval!=null){
																			verificarIdIguales(val_peek(6).sval,val_peek(4).sval);
																			verificarTipos(val_peek(6).sval,val_peek(4).sval);
																			verificarTipos(val_peek(6).sval,val_peek(2).sval);
																		}}
break;
case 88:
//#line 310 "gramatica.y"
{Ambito.removeAmbito();
																			if(val_peek(6).sval!=null){
																				verificarIdIguales(val_peek(6).sval,val_peek(4).sval);
																				verificarTipos(val_peek(6).sval,val_peek(4).sval);
																				verificarTipos(val_peek(6).sval,val_peek(2).sval);
																				if (tercetosBreakET.containsKey(val_peek(9).sval)){
																					List<Terceto> aux = tercetosBreakET.get(val_peek(9).sval);
																					tercetosBreakET.remove(val_peek(9).sval);
																					for (int i = 0; i<aux.size();i++){
																						aux.get(i).setSarg("["+Terceto.GetIndice()+"]");
																					}
																				}
																			}}
break;
case 89:
//#line 325 "gramatica.y"
{setearUso(val_peek(1).sval,"Etiqueta");
					yyval.sval=TablaSimbolos.cambiarNombreKey(val_peek(1).sval);}
break;
case 90:
//#line 329 "gramatica.y"
{val_peek(2).sval=Ambito.getAmbito(val_peek(2).sval);
									if (!variablesInicializadas.contains(val_peek(2).sval))
										variablesInicializadas.add(val_peek(2).sval);
									verificarEntero(val_peek(2).sval);
									verificarTipos(val_peek(2).sval,val_peek(0).sval);
									ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));
									id_for_act.push(val_peek(2).sval);
									yyval.sval=val_peek(2).sval;}
break;
case 91:
//#line 339 "gramatica.y"
{val_peek(2).sval=Ambito.getAmbito(val_peek(2).sval);
									verificarTipos(val_peek(2).sval,val_peek(0).sval);
									ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));
									ListaTercetos.add_seleccion_cond();
									yyval.sval=val_peek(2).sval;}
break;
case 92:
//#line 346 "gramatica.y"
{yyval.sval= val_peek(0).sval;
						ListaTercetos.add_for_act(id_for_act.pop(),val_peek(1).sval,val_peek(0).sval);
						Ambito.addAmbito("for");}
break;
case 93:
//#line 349 "gramatica.y"
{errorEnXY("Falta +/- para actualizar for");
				ListaTercetos.add_for_act(id_for_act.pop(),"+",val_peek(0).sval);
				Ambito.addAmbito("for");}
break;
case 94:
//#line 354 "gramatica.y"
{ListaTercetos.add_for_cpo();
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
break;
case 95:
//#line 369 "gramatica.y"
{ListaTercetos.add_for_cpo();
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
break;
case 101:
//#line 395 "gramatica.y"
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
case 102:
//#line 405 "gramatica.y"
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
case 103:
//#line 420 "gramatica.y"
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
//#line 1311 "Parser.java"
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
