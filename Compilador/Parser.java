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
//#line 23 "Parser.java"




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
    0,    1,    1,    1,    2,    2,    2,    2,    3,    3,
    3,    3,    4,    4,    6,    6,    8,    8,    8,    9,
    9,    9,    7,    7,    7,   10,   10,   11,   11,   12,
   12,   13,   13,   13,   13,   13,   13,    5,    5,   15,
   15,   15,   15,   15,   16,   16,   16,   14,   14,   14,
   21,   21,   21,   22,   22,   22,   22,   23,   23,   23,
   24,   24,   24,   17,   26,   26,   27,   27,   28,   28,
   25,   31,   32,   32,   32,   32,   32,   32,   18,   18,
   18,   19,   19,   20,   20,   33,   34,   35,   35,   36,
   36,   37,   37,   29,   29,   30,   30,   30,   30,
};
final static short yylen[] = {                            2,
    2,    1,    1,    1,    4,    3,    2,    2,    1,    1,
    2,    2,    1,    1,    3,    2,    1,    1,    1,    1,
    3,    1,    2,    3,    5,    3,    2,    6,    1,    2,
    1,    6,    5,    4,    5,    3,    5,    1,    2,    2,
    2,    2,    2,    1,    3,    2,    4,    3,    3,    1,
    3,    3,    1,    1,    1,    2,    1,    3,    4,    6,
    1,    1,    2,    3,    4,    2,    4,    2,    3,    1,
    3,    3,    1,    1,    1,    1,    1,    1,    4,    3,
    3,    2,    1,    9,   11,    3,    3,    2,    1,    4,
    1,    1,    1,    2,    1,    1,    2,    4,    2,
};
final static short yydefred[] = {                         0,
    2,    3,    4,    0,    0,   22,    0,    0,    0,    0,
   17,   18,    0,    0,    0,    0,    0,    0,    9,   10,
   13,   14,    0,    0,    0,   38,    0,    0,    0,    0,
   44,   83,    0,    0,    0,    0,    0,    0,    0,    0,
   27,    0,   82,    0,    0,   39,    0,    0,    8,   11,
   12,   20,    0,   16,    0,   29,   19,    0,    0,   23,
    0,   40,   41,   42,   43,    0,   55,    0,    0,    0,
   53,   57,   61,   62,   58,    0,    0,    0,    0,    0,
    0,    0,   64,    0,   80,    0,   26,    0,    0,    6,
    0,   15,   21,    0,   30,    0,   24,   56,    0,    0,
    0,    0,   63,    0,   59,    0,    0,   74,   75,   73,
   76,   77,   78,    0,   71,    0,    0,    0,   96,   68,
    0,   66,   79,    0,    0,    5,    0,    0,    0,    0,
   51,   52,    0,    0,    0,   97,    0,   99,    0,   95,
    0,    0,   70,   86,    0,    0,    0,   25,   60,    0,
    0,   67,   94,    0,   65,    0,    0,    0,    0,    0,
   98,   69,    0,   89,   92,   93,    0,    0,    0,   28,
    0,    0,   88,    0,    0,    0,    0,   91,   84,    0,
   34,    0,    0,    0,   33,    0,   35,   85,    0,   32,
   90,
};
final static short yydgoto[] = {                          4,
    5,   17,   18,   19,   20,   21,   22,   23,   24,   25,
   60,   61,  159,   69,  119,   27,   28,   29,   30,   31,
   70,   71,   32,   77,   37,   83,   84,  142,  139,  140,
   81,  114,   89,  146,  167,  179,  168,
};
final static short yysindex[] = {                      -171,
    0,    0,    0,    0, -101,    0,  -25,    4,  -32,   -5,
    0,    0, -178,   68, -117,  157,  -19,   40,    0,    0,
    0,    0, -133,   23,  -38,    0,   33,   55,   61,  102,
    0,    0,   -7,    2,  -48,   -7, -162,  119,  -97,  126,
    0,  131,    0,  -84,  -25,    0,   59,  157,    0,    0,
    0,    0,   31,    0,  -83,    0,    0,  118,  -82,    0,
    5,    0,    0,    0,    0,  131,    0,  -81,   57,   36,
    0,    0,    0,    0,    0,  -80,   69,  139,   -7,   -6,
  140,  -75,    0, -136,    0,  142,    0,  -88,  128,    0,
   77,    0,    0, -172,    0, -172,    0,    0,   -7,   -7,
   -7,   -7,    0,   19,    0,  -84,   57,    0,    0,    0,
    0,    0,    0,   -7,    0,   78,  132, -142,    0,    0,
  -57,    0,    0,  -68,  -63,    0,   81,  -24,   36,   36,
    0,    0,  158,  149,   57,    0,  -37,    0,   92,    0,
 -142,  -42,    0,    0,  -20,  163,  157,    0,    0,  -63,
  165,    0,    0,  101,    0,   -7,   14,  136,  100,  174,
    0,    0,   57,    0,    0,    0,  196,  -15,  -29,    0,
   14,   21,    0,   -7,   50,  197, -142,    0,    0,   76,
    0,  188,   21,  117,    0,  189,    0,    0,  194,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,   18,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  254,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  199,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   27,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   17,    0,
    0,    0,    0,    0,    0,  -41,    0,    0,  203,  -36,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  204,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  205,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -31,   -9,
    0,    0,    0,    0,  224,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  220,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  155,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  159,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,   64,   51,   54,    0,    0,   45,  259,    0,
   20,  187,    0,   24,   58,    0,    0,    0,    0,    0,
   52,   56,   32,  182,    0,    0,    0,    0,  -70,  236,
    0,  144,  181,  145,  123,  107,    0,
};
final static int YYTABLESIZE=430;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         54,
   54,   54,   58,   54,   50,   54,   50,   39,   50,   48,
  174,   48,   79,   48,   34,   68,   58,   54,   54,   54,
   54,   16,   50,   50,   50,   50,    7,   48,   48,   48,
   48,   49,   35,   49,   41,   49,   99,   68,  100,  112,
  111,  113,   75,   36,   43,   58,   76,  118,   96,   49,
   49,   49,   49,  112,  111,  113,  165,   31,  166,   80,
   31,   20,   26,   76,   72,  141,   55,   72,   50,   59,
  154,   51,   46,   26,   55,   26,   20,  101,   42,   47,
   97,   54,  102,   54,   57,    1,    2,    3,   50,   92,
  182,   62,   99,   48,  100,   11,   12,   50,   82,   99,
   51,  100,  107,   48,   26,   26,  184,   44,  181,  105,
   72,   91,  104,   63,   45,   49,  186,    8,   99,   64,
  100,    9,    6,   52,  116,  121,  122,   13,   14,  117,
   72,   72,   72,   72,  185,  137,  136,  135,  127,   45,
   59,   50,    8,  177,   51,   72,    9,  148,   26,    7,
  129,  130,   13,   14,    6,    7,  131,  132,    8,   85,
   65,   86,    9,   10,   49,   87,   11,   12,   13,   14,
   34,   15,   88,   93,   95,   94,   98,  103,  106,  163,
  115,   45,  123,   90,    8,  124,  125,   72,    9,  144,
  138,  116,  175,  145,   13,   14,  117,  180,  149,   45,
   72,  126,    8,  147,   26,   72,    9,  150,   50,  116,
  158,   51,   13,   14,  117,   26,  152,   56,   57,  151,
  155,  157,   78,  161,  170,  162,   38,   66,   67,   11,
   12,   56,  171,   54,   54,   54,  172,  183,   50,   50,
   50,  189,  173,   48,   48,   48,  187,  190,   33,   66,
   67,   40,  191,    1,  108,  109,  110,   46,   73,   74,
   56,   45,   81,   47,   72,   49,   49,   49,  108,  109,
  110,  164,   31,   19,   19,   73,   74,   45,   87,   36,
    8,   53,  128,   37,    9,  133,  134,  116,  156,  188,
   13,   14,  117,  176,  160,    6,    7,    0,    0,    8,
    0,    0,    0,    9,   10,    0,    0,   11,   12,   13,
   14,    0,   15,    0,    6,    7,    0,  120,    8,    0,
    0,    0,    9,   10,    0,    0,   11,   12,   13,   14,
    0,   15,    6,    7,    0,    0,    8,    0,    0,    0,
    9,   10,    0,    0,   11,   12,   13,   14,   45,   15,
    0,    8,    0,    0,    0,    9,  143,   45,  116,    0,
    8,   13,   14,  117,    9,    0,    0,  116,    0,    0,
   13,   14,  117,   45,  153,    0,    8,    0,    0,    0,
    9,    0,    0,  116,    0,    0,   13,   14,  117,  153,
    0,    6,    7,    0,    0,    8,    0,    0,    0,    9,
   10,  169,    0,   11,   12,   13,   14,  178,   15,    0,
    0,    0,    6,    7,    0,    0,    8,    0,  178,  153,
    9,   10,    0,    0,   11,   12,   13,   14,    0,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   41,   45,   41,   47,   43,   40,   45,   41,
   40,   43,   61,   45,   40,   45,   41,   59,   60,   61,
   62,  123,   59,   60,   61,   62,    0,   59,   60,   61,
   62,   41,   58,   43,   40,   45,   43,   45,   45,   60,
   61,   62,   41,   40,   13,   41,   45,  123,   44,   59,
   60,   61,   62,   60,   61,   62,   43,   41,   45,   36,
   44,   44,    5,   45,   33,  123,   44,   36,   18,   25,
  141,   18,   15,   16,   44,   18,   59,   42,  257,   16,
   61,   59,   47,  125,  257,  257,  258,  259,  125,   59,
   41,   59,   43,  125,   45,  268,  269,   47,  261,   43,
   47,   45,   79,  123,   47,   48,  177,   40,   59,   41,
   79,   48,   44,   59,  257,  125,   41,  260,   43,   59,
   45,  264,  256,  257,  267,  262,  263,  270,  271,  272,
   99,  100,  101,  102,   59,   58,   59,  114,   94,  257,
   96,   91,  260,  123,   91,  114,  264,  128,   91,  123,
   99,  100,  270,  271,  256,  257,  101,  102,  260,   41,
   59,  259,  264,  265,  125,   40,  268,  269,  270,  271,
   40,  273,  257,  257,  257,   58,  258,  258,   40,  156,
   41,  257,   41,  125,  260,  274,   59,  156,  264,  258,
   59,  267,  169,  257,  270,  271,  272,  174,   41,  257,
  169,  125,  260,  123,  147,  174,  264,   59,  158,  267,
  147,  158,  270,  271,  272,  158,  125,  256,  257,  257,
  263,   59,  271,   59,  125,  125,  259,  257,  258,  268,
  269,  256,   59,  275,  276,  277,   41,   41,  275,  276,
  277,  125,  258,  275,  276,  277,   59,   59,  274,  257,
  258,  257,   59,    0,  275,  276,  277,   59,  257,  258,
  256,   59,   59,   59,   41,  275,  276,  277,  275,  276,
  277,  258,  256,  256,  257,  257,  258,  257,   59,  125,
  260,   23,   96,  125,  264,  104,  106,  267,  145,  183,
  270,  271,  272,  171,  150,  256,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,  265,   -1,   -1,  268,  269,  270,
  271,   -1,  273,   -1,  256,  257,   -1,   82,  260,   -1,
   -1,   -1,  264,  265,   -1,   -1,  268,  269,  270,  271,
   -1,  273,  256,  257,   -1,   -1,  260,   -1,   -1,   -1,
  264,  265,   -1,   -1,  268,  269,  270,  271,  257,  273,
   -1,  260,   -1,   -1,   -1,  264,  121,  257,  267,   -1,
  260,  270,  271,  272,  264,   -1,   -1,  267,   -1,   -1,
  270,  271,  272,  257,  139,   -1,  260,   -1,   -1,   -1,
  264,   -1,   -1,  267,   -1,   -1,  270,  271,  272,  154,
   -1,  256,  257,   -1,   -1,  260,   -1,   -1,   -1,  264,
  265,  266,   -1,  268,  269,  270,  271,  172,  273,   -1,
   -1,   -1,  256,  257,   -1,   -1,  260,   -1,  183,  184,
  264,  265,   -1,   -1,  268,  269,  270,  271,   -1,  273,
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
"bloque_sentencias : bloque_sentencias '{' sentencia '}'",
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

//#line 203 "gramatica.y"

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

public Stack<String> id_for_act = new Stack<String>();


public void comprobarBinding(String arg, String text){
	if (Ambito.getAmbito(arg) != null) {
		//ACA CREAR LOS TERCETOS
	} else
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
	if (TablaSimbolos.getSimbolo(arg1) == null || TablaSimbolos.getSimbolo(arg2) == null){
		return;
	}
	if (TablaSimbolos.getSimbolo(arg1).getTipo().equals(TablaSimbolos.getSimbolo(arg2).getTipo()))
		return;
	errorEnXY("No se puede realizar una operacion entre "+arg1+", "+arg2);
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
//#line 584 "Parser.java"
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
//#line 17 "gramatica.y"
{programaListo();}
break;
case 2:
//#line 20 "gramatica.y"
{setearUso(val_peek(0).sval,"Nombre Programa");Ambito.addAmbito("Main");}
break;
case 3:
//#line 21 "gramatica.y"
{errorEnXY("Nombre del programa invalido. Identificador esperado, constante recibida en cambio");}
break;
case 4:
//#line 22 "gramatica.y"
{errorEnXY("Nombre del programa invalido. Identificador esperado, cadena recibida en cambio");}
break;
case 7:
//#line 27 "gramatica.y"
{errorEnXY("Esperando final de bloque");}
break;
case 8:
//#line 28 "gramatica.y"
{errorEnXY("Esperando comienzo de bloque");}
break;
case 13:
//#line 37 "gramatica.y"
{imprimirMSGEstructura("Declaracion de variable/s");}
break;
case 14:
//#line 38 "gramatica.y"
{imprimirMSGEstructura("Declaracion de funcion");}
break;
case 16:
//#line 42 "gramatica.y"
{errorEnXY("Tipo de la/s variable/s esperado al comienzo de la sentencia");}
break;
case 17:
//#line 45 "gramatica.y"
{tipoAux=val_peek(0).sval;yyval.sval=val_peek(0).sval;}
break;
case 18:
//#line 46 "gramatica.y"
{tipoAux=val_peek(0).sval;yyval.sval=val_peek(0).sval;}
break;
case 19:
//#line 47 "gramatica.y"
{errorEnXY("Tipo de la/s variable/s invalido");}
break;
case 20:
//#line 50 "gramatica.y"
{setearTipo(val_peek(0).sval);setearUso(val_peek(0).sval,"Variable");TablaSimbolos.cambiarNombreKey(val_peek(0).sval, val_peek(0).sval+Ambito.getNaming());}
break;
case 21:
//#line 51 "gramatica.y"
{setearTipo(val_peek(0).sval);setearUso(val_peek(0).sval,"Variable");TablaSimbolos.cambiarNombreKey(val_peek(0).sval, val_peek(0).sval+Ambito.getNaming());}
break;
case 22:
//#line 52 "gramatica.y"
{errorEnXY("Se esperaba un identificador o una lista de identificadores separados por ,");}
break;
case 26:
//#line 60 "gramatica.y"
{setearUso(val_peek(1).sval,"Nombre de Funcion");
							TablaSimbolos.cambiarNombreKey(val_peek(1).sval, val_peek(1).sval+Ambito.getNaming());
							tokens.push(TablaSimbolos.getSimbolo(val_peek(1).sval+Ambito.getNaming()));
							Ambito.addAmbito(val_peek(1).sval);}
break;
case 27:
//#line 64 "gramatica.y"
{errorEnXY("La declaracion de la funcion necesita un nombre");}
break;
case 28:
//#line 67 "gramatica.y"
{tokenAux=tokens.pop();
												tokenAux.setTipo(val_peek(3).sval);
												/*verificarTipos(tokenAux.getLexema().toString(),$5.sval+Ambito.getNaming());*/
												verificarTipos(tokenAux.getLexema().toString(),Ambito.getAmbito(val_peek(1).sval));
												Ambito.removeAmbito();}
break;
case 29:
//#line 72 "gramatica.y"
{errorEnXY("En la declaracion de la funcion falta: ),:,{ o }");}
break;
case 30:
//#line 75 "gramatica.y"
{setearTipo(val_peek(0).sval,val_peek(1).sval);
					setearUso(val_peek(0).sval,"Nombre de Parametro");
					TablaSimbolos.cambiarNombreKey(val_peek(0).sval, val_peek(0).sval+Ambito.getNaming());}
break;
case 31:
//#line 78 "gramatica.y"
{errorEnXY("Identificador del parametro esperado  en la declaracion de funcion");}
break;
case 32:
//#line 81 "gramatica.y"
{yyval.sval=val_peek(2).sval;}
break;
case 33:
//#line 82 "gramatica.y"
{errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 34:
//#line 83 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo y final de la expresion");}
break;
case 35:
//#line 84 "gramatica.y"
{errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 36:
//#line 85 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo y final de la expresion y ; al final de linea");}
break;
case 37:
//#line 86 "gramatica.y"
{errorEnXY("; esperados al final de linea");}
break;
case 39:
//#line 91 "gramatica.y"
{imprimirMSGEstructura("Defer de instruccion ejecutable");}
break;
case 40:
//#line 94 "gramatica.y"
{imprimirMSGEstructura("Asignacion");}
break;
case 41:
//#line 95 "gramatica.y"
{imprimirMSGEstructura("Seleccion If");}
break;
case 42:
//#line 96 "gramatica.y"
{imprimirMSGEstructura("Impresion a Consola");}
break;
case 43:
//#line 97 "gramatica.y"
{imprimirMSGEstructura("Invocacion de Funcion");}
break;
case 44:
//#line 98 "gramatica.y"
{imprimirMSGEstructura("Loop For");}
break;
case 45:
//#line 101 "gramatica.y"
{verificarTipos(val_peek(2).sval+Ambito.getNaming(),val_peek(0).sval);comprobarBinding(val_peek(2).sval,"Variable no declarada");ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 46:
//#line 102 "gramatica.y"
{errorEnXY("Expresion esperada despues de la asignacion");}
break;
case 47:
//#line 103 "gramatica.y"
{errorEnXY("Operador de asignacion incorrecto, se esperaba -> =:");}
break;
case 48:
//#line 106 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 49:
//#line 107 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 51:
//#line 111 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 52:
//#line 112 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 54:
//#line 116 "gramatica.y"
{comprobarBinding(val_peek(0).sval,"Variable no declarada");}
break;
case 56:
//#line 118 "gramatica.y"
{verificarRangoDoubleNegativo();val_peek(0).sval="-"+val_peek(0).sval;TablaSimbolos.addSimbolo(new TokenLexema(258, val_peek(0).sval,"f64"));yyval.sval=val_peek(0).sval;}
break;
case 58:
//#line 122 "gramatica.y"
{comprobarBinding(val_peek(2).sval,"No se encontro la funcion en el ambito actual para los parametros dados");}
break;
case 59:
//#line 123 "gramatica.y"
{comprobarBinding(val_peek(3).sval,"No se encontro la funcion en el ambito actual para los parametros dados");}
break;
case 60:
//#line 124 "gramatica.y"
{comprobarBinding(val_peek(5).sval,"No se encontro la funcion en el ambito actual para los parametros dados");}
break;
case 61:
//#line 127 "gramatica.y"
{comprobarBinding(val_peek(0).sval,"No se encontro el parametro indicado");}
break;
case 63:
//#line 129 "gramatica.y"
{verificarRangoDoubleNegativo();val_peek(0).sval="-"+val_peek(0).sval;TablaSimbolos.addSimbolo(new TokenLexema(258, val_peek(0).sval,"f64"));yyval.sval=val_peek(0).sval;}
break;
case 64:
//#line 132 "gramatica.y"
{ListaTercetos.add_seleccion_final();}
break;
case 67:
//#line 139 "gramatica.y"
{ListaTercetos.add_seleccion_then();}
break;
case 68:
//#line 140 "gramatica.y"
{ListaTercetos.add_seleccion_then();}
break;
case 71:
//#line 147 "gramatica.y"
{ListaTercetos.add_seleccion_cond();}
break;
case 72:
//#line 150 "gramatica.y"
{verificarTipos(val_peek(2).sval,val_peek(0).sval);ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));}
break;
case 79:
//#line 161 "gramatica.y"
{ListaTercetos.addTerceto(new Terceto(val_peek(3).sval,val_peek(1).sval,"-"));}
break;
case 80:
//#line 162 "gramatica.y"
{errorEnXY("Falta de parentesis al comienzo de la cadena del out");}
break;
case 81:
//#line 163 "gramatica.y"
{errorEnXY("Falta de parentesis al final de la cadena del out");}
break;
case 83:
//#line 167 "gramatica.y"
{errorEnXY("Funcion invocada sin discard del retorno");}
break;
case 84:
//#line 170 "gramatica.y"
{verificarIdIguales(val_peek(6).sval,val_peek(4).sval);verificarTipos(val_peek(6).sval+Ambito.getNaming(),val_peek(4).sval+Ambito.getNaming());verificarTipos(val_peek(6).sval+Ambito.getNaming(),val_peek(2).sval);}
break;
case 85:
//#line 171 "gramatica.y"
{verificarIdIguales(val_peek(6).sval,val_peek(4).sval);verificarTipos(val_peek(6).sval+Ambito.getNaming(),val_peek(4).sval+Ambito.getNaming());verificarTipos(val_peek(6).sval+Ambito.getNaming(),val_peek(2).sval);TablaSimbolos.cambiarNombreKey(val_peek(10).sval, val_peek(10).sval+Ambito.getNaming());setearUso(val_peek(10).sval+Ambito.getNaming(),"Etiqueta");}
break;
case 86:
//#line 174 "gramatica.y"
{verificarEntero(val_peek(2).sval+Ambito.getNaming());verificarTipos(val_peek(2).sval+Ambito.getNaming(),val_peek(0).sval);ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));id_for_act.push(val_peek(2).sval);}
break;
case 87:
//#line 177 "gramatica.y"
{verificarTipos(val_peek(2).sval+Ambito.getNaming(),val_peek(0).sval);ListaTercetos.addTerceto(new Terceto(val_peek(1).sval,val_peek(2).sval,val_peek(0).sval));ListaTercetos.add_seleccion_cond();}
break;
case 88:
//#line 180 "gramatica.y"
{yyval.sval=val_peek(0).sval;ListaTercetos.add_for_act(id_for_act.pop(),val_peek(1).sval,val_peek(0).sval);}
break;
case 89:
//#line 181 "gramatica.y"
{errorEnXY("Falta +/- para actualizar for");}
break;
case 90:
//#line 184 "gramatica.y"
{ListaTercetos.add_for_cpo();}
break;
case 91:
//#line 185 "gramatica.y"
{ListaTercetos.add_for_cpo();}
break;
//#line 998 "Parser.java"
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
