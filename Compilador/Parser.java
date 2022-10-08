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
//#line 19 "Parser.java"




public class Parser
{

boolean yydebug=true;        //do I want debug output?
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
    9,    9,    7,    7,    7,   10,   10,   11,   12,   12,
   13,   13,   13,   13,   13,   13,    5,    5,   15,   15,
   15,   15,   15,   16,   16,   16,   14,   14,   14,   21,
   21,   21,   22,   22,   22,   22,   23,   23,   23,   24,
   24,   24,   17,   17,   26,   26,   27,   27,   25,   30,
   31,   31,   31,   31,   31,   31,   18,   18,   18,   19,
   19,   20,   20,   20,   20,   32,   32,   28,   28,   29,
   29,   29,   29,
};
final static short yylen[] = {                            2,
    2,    1,    1,    1,    4,    3,    2,    2,    1,    1,
    2,    2,    1,    1,    3,    2,    1,    1,    1,    1,
    3,    1,    2,    3,    5,    3,    2,    6,    2,    1,
    6,    5,    4,    5,    3,    5,    1,    2,    2,    2,
    2,    2,    1,    3,    2,    4,    3,    3,    1,    3,
    3,    1,    1,    1,    2,    1,    3,    4,    6,    1,
    1,    2,    4,    5,    4,    2,    4,    2,    3,    3,
    1,    1,    1,    1,    1,    1,    4,    3,    3,    2,
    1,   17,   14,   19,   16,    1,    1,    2,    1,    1,
    2,    4,    2,
};
final static short yydefred[] = {                         0,
    2,    3,    4,    0,    0,   22,    0,    0,    0,    0,
    0,   17,   18,    0,    0,    0,    0,    0,    0,    9,
   10,   13,   14,    0,    0,    0,   37,    0,    0,    0,
    0,   43,   81,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   27,    0,   80,    0,    0,   38,    0,    0,
    8,   11,   12,   20,    0,   16,    0,   19,    0,    0,
   23,    0,   39,   40,   41,   42,    0,   54,    0,    0,
    0,   52,   56,   60,   61,    0,   57,    0,    0,    0,
    0,    0,    0,    0,   78,    0,   26,    0,    6,    0,
   15,   21,    0,   29,    0,   24,   55,    0,    0,    0,
    0,   62,    0,   58,    0,    0,   72,   73,   71,   74,
   75,   76,    0,   69,    0,    0,    0,   90,   66,    0,
   63,    0,   77,    0,    5,    0,    0,    0,    0,   50,
   51,    0,    0,    0,   91,    0,   93,    0,   89,    0,
   68,   64,    0,    0,   25,   59,    0,    0,   65,   88,
    0,    0,    0,    0,    0,   92,   67,    0,    0,   28,
    0,    0,    0,    0,    0,    0,    0,   33,    0,    0,
    0,   32,    0,   34,    0,   86,   87,    0,   31,    0,
    0,    0,    0,    0,    0,   83,    0,    0,    0,   85,
    0,    0,   82,    0,   84,
};
final static short yydgoto[] = {                          4,
    5,   18,   19,   20,   21,   22,   23,   24,   25,   26,
   61,   62,  154,   70,  118,   28,   29,   30,   31,   32,
   71,   72,   33,   78,   39,   84,  122,  138,  139,   82,
  113,  178,
};
final static short yysindex[] = {                      -123,
    0,    0,    0,    0, -110,    0,  -25,    9,  -23,  -32,
   -5,    0,    0, -176,   45,  104,  155,  -17,   26,    0,
    0,    0,    0, -115,   27,  -38,    0,   52,   65,   68,
   80,    0,    0,   -7,    2,   42, -134,   -7, -118,  107,
 -107,  122,    0,  126,    0,  -89,  -25,    0,   44,  155,
    0,    0,    0,    0,   58,    0,  -74,    0,  131,  -71,
    0,   60,    0,    0,    0,    0,  126,    0,  -59,   54,
   41,    0,    0,    0,    0,  -58,    0,   64,   -7,  161,
   -6,  162,  -79, -106,    0,  165,    0,  -57,    0,   62,
    0,    0, -194,    0, -194,    0,    0,   -7,   -7,   -7,
   -7,    0,   15,    0,   54,  -45,    0,    0,    0,    0,
    0,    0,   -7,    0,  106,  156,  170,    0,    0,  -62,
    0,  -40,    0,  -26,    0,   99,  192,   41,   41,    0,
    0,  197,  -27,   54,    0,  -16,    0,   79,    0,  170,
    0,    0,  183,  155,    0,    0,  -10,  195,    0,    0,
   95,    1,  137,  138,  205,    0,    0,  -20,  -29,    0,
   18,    7,   -7,   50,  -20,  219,   55,    0,  220,   34,
   77,    0,  221,    0,  234,    0,    0,   47,    0,   77,
  257,   48,  -46,  261,  170,    0,   17,  112,  170,    0,
  248,  128,    0,  251,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    5,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  311,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  264,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   22,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   74,
    0,    0,    0,    0,    0,    0,  -41,    0,    0,  265,
  -36,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  266,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  269,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -31,   -9,    0,
    0,    0,    0,  275,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  204,    0,    0,    0,    0,    0,    0,
    0,    0,  209,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   40,   29,   43,    0,    0,   33,  296,    0,
   -4,  242,    0,    8,   63,    0,    0,    0,    0,    0,
   78,   72,   31,  237,    0,    0,    0, -113,  -13,    0,
  -92,  164,
};
final static int YYTABLESIZE=442;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         53,
   53,   53,   59,   53,   49,   53,   49,   41,   49,   47,
  163,   47,   17,   47,   35,   69,   38,   53,   53,   53,
   53,    7,   49,   49,   49,   49,  151,   47,   47,   47,
   47,   48,   36,   48,   43,   48,   98,   69,   99,  111,
  110,  112,   77,  117,   45,   81,   76,   52,   20,   48,
   48,   48,   48,  111,  110,  112,   49,   96,   60,   76,
  140,   53,   58,   20,   73,  162,   37,   27,   73,  119,
   57,  188,  170,   12,   13,  192,  185,   52,   48,   27,
   44,   27,  100,   53,   46,   56,  105,  101,   49,   90,
  169,   53,   98,   47,   99,  173,   98,   98,   99,   99,
   59,   57,   79,   95,  104,   50,  141,  103,  168,   73,
   63,   27,   27,  172,   30,   48,   91,   30,   52,  176,
  134,  177,  145,   64,  150,  126,   65,   60,   73,   73,
   73,   73,   53,    1,    2,    3,   80,  150,   66,  189,
    6,   54,   83,   73,    7,    6,    7,   85,    8,    9,
   51,   86,   27,   10,   11,  120,  121,   12,   13,   14,
   15,   87,   16,  136,  135,   35,  164,   88,   89,  186,
  167,  130,  131,  190,  150,  128,  129,   47,  150,    8,
    9,   52,   92,  153,   10,   94,  125,  115,   93,   73,
   14,   15,  116,   73,   47,   53,    8,    9,   97,  102,
  106,   10,  114,  149,  115,  123,   27,   14,   15,  116,
   47,  133,    8,    9,  137,   27,  124,   10,   58,  157,
  115,  144,  142,   14,   15,  116,   40,   67,   68,   12,
   13,  143,   59,   53,   53,   53,  191,  146,   49,   49,
   49,  152,  148,   47,   47,   47,  147,  155,   34,   67,
   68,   42,  194,  156,  107,  108,  109,  158,   74,   75,
   19,   19,  160,  161,  166,   48,   48,   48,  107,  108,
  109,   74,   75,   47,  165,    8,    9,  171,  174,  179,
   10,    6,    7,  115,    8,    9,   14,   15,  116,   10,
   11,  175,  180,   12,   13,   14,   15,  183,   16,    6,
    7,  187,    8,    9,  181,  184,  193,   10,   11,  195,
    1,   12,   13,   14,   15,   70,   16,    6,    7,   55,
    8,    9,   45,   44,   79,   10,   11,   46,   35,   12,
   13,   14,   15,   36,   16,   47,  127,    8,    9,  132,
    0,    0,   10,  182,    0,  115,    0,    0,   14,   15,
  116,   47,    0,    8,    9,    0,    0,    0,   10,    0,
   47,  115,    8,    9,   14,   15,  116,   10,   47,    0,
    8,    9,    0,   14,   15,   10,    0,    0,  115,    0,
    0,   14,   15,  116,   47,    0,    8,    9,    0,    0,
    0,   10,    6,    7,  115,    8,    9,   14,   15,  116,
   10,   11,  159,    0,   12,   13,   14,   15,    0,   16,
    6,    7,    0,    8,    9,    0,    0,    0,   10,   11,
    0,    0,   12,   13,   14,   15,   47,   16,    8,    9,
    0,    0,    0,   10,    0,    0,  115,    0,    0,   14,
   15,  116,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   41,   45,   41,   47,   43,   40,   45,   41,
   40,   43,  123,   45,   40,   45,   40,   59,   60,   61,
   62,    0,   59,   60,   61,   62,  140,   59,   60,   61,
   62,   41,   58,   43,   40,   45,   43,   45,   45,   60,
   61,   62,   41,  123,   14,   38,   45,   19,   44,   59,
   60,   61,   62,   60,   61,   62,   17,   62,   26,   45,
  123,   19,  257,   59,   34,  158,   58,    5,   38,   83,
   44,  185,  165,  268,  269,  189,  123,   49,   16,   17,
  257,   19,   42,  125,   40,   59,   79,   47,  125,   50,
   41,   49,   43,  125,   45,   41,   43,   43,   45,   45,
   41,   44,   61,   44,   41,  123,  120,   44,   59,   79,
   59,   49,   50,   59,   41,  125,   59,   44,   90,   43,
  113,   45,  127,   59,  138,   93,   59,   95,   98,   99,
  100,  101,   90,  257,  258,  259,  271,  151,   59,  123,
  256,  257,  261,  113,  123,  256,  257,   41,  259,  260,
  125,  259,   90,  264,  265,  262,  263,  268,  269,  270,
  271,   40,  273,   58,   59,   40,  159,  257,  125,  183,
  163,  100,  101,  187,  188,   98,   99,  257,  192,  259,
  260,  153,  257,  144,  264,  257,  125,  267,   58,  159,
  270,  271,  272,  163,  257,  153,  259,  260,  258,  258,
   40,  264,   41,  125,  267,   41,  144,  270,  271,  272,
  257,  257,  259,  260,   59,  153,  274,  264,  257,  125,
  267,  123,  263,  270,  271,  272,  259,  257,  258,  268,
  269,  258,   41,  275,  276,  277,  125,   41,  275,  276,
  277,   59,  259,  275,  276,  277,  274,  258,  274,  257,
  258,  257,  125,   59,  275,  276,  277,  257,  257,  258,
  256,  257,  125,   59,  258,  275,  276,  277,  275,  276,
  277,  257,  258,  257,  257,  259,  260,   59,   59,   59,
  264,  256,  257,  267,  259,  260,  270,  271,  272,  264,
  265,  258,   59,  268,  269,  270,  271,   41,  273,  256,
  257,   41,  259,  260,  258,  258,   59,  264,  265,   59,
    0,  268,  269,  270,  271,   41,  273,  256,  257,   24,
  259,  260,   59,   59,   59,  264,  265,   59,  125,  268,
  269,  270,  271,  125,  273,  257,   95,  259,  260,  103,
   -1,   -1,  264,  180,   -1,  267,   -1,   -1,  270,  271,
  272,  257,   -1,  259,  260,   -1,   -1,   -1,  264,   -1,
  257,  267,  259,  260,  270,  271,  272,  264,  257,   -1,
  259,  260,   -1,  270,  271,  264,   -1,   -1,  267,   -1,
   -1,  270,  271,  272,  257,   -1,  259,  260,   -1,   -1,
   -1,  264,  256,  257,  267,  259,  260,  270,  271,  272,
  264,  265,  266,   -1,  268,  269,  270,  271,   -1,  273,
  256,  257,   -1,  259,  260,   -1,   -1,   -1,  264,  265,
   -1,   -1,  268,  269,  270,  271,  257,  273,  259,  260,
   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,  270,
  271,  272,
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
"seleccion : If condicion_if then_selec end_if",
"seleccion : If condicion_if then_selec else_selecc end_if",
"then_selec : then '{' ejecutable_for '}'",
"then_selec : then inst_ejecutable_for",
"else_selecc : Else '{' ejecutable_for '}'",
"else_selecc : Else inst_ejecutable_for",
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
"for_continue : For '(' id SIMB_ASIGNACION cte ';' id comparador cte ';' mas_o_menos cte ')' '{' ejecutable_for '}' ';'",
"for_continue : For '(' id SIMB_ASIGNACION cte ';' id comparador cte ';' mas_o_menos cte ')' inst_ejecutable_for",
"for_continue : cadena ':' For '(' id SIMB_ASIGNACION cte ';' id comparador cte ';' mas_o_menos cte ')' '{' ejecutable_for '}' ';'",
"for_continue : cadena ':' For '(' id SIMB_ASIGNACION cte ';' id comparador cte ';' mas_o_menos cte ')' inst_ejecutable_for",
"mas_o_menos : '+'",
"mas_o_menos : '-'",
"ejecutable_for : ejecutable_for inst_ejecutable_for",
"ejecutable_for : inst_ejecutable_for",
"inst_ejecutable_for : inst_ejecutable",
"inst_ejecutable_for : Break ';'",
"inst_ejecutable_for : Break ':' cadena ';'",
"inst_ejecutable_for : Continue ';'",
};

//#line 177 "gramatica.y"

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
	if (indexPunto== -1)
		return;
	
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
//#line 517 "Parser.java"
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
//#line 16 "gramatica.y"
{System.out.println("////////////////////");System.out.println("Programa Listo");}
break;
case 3:
//#line 20 "gramatica.y"
{errorEnXY("Nombre del programa invalido. Identificador esperado, constante recibida en cambio");}
break;
case 4:
//#line 21 "gramatica.y"
{errorEnXY("Nombre del programa invalido. Identificador esperado, cadena recibida en cambio");}
break;
case 7:
//#line 26 "gramatica.y"
{errorEnXY("Esperando final de bloque");}
break;
case 8:
//#line 27 "gramatica.y"
{errorEnXY("Esperando comienzo de bloque");}
break;
case 13:
//#line 36 "gramatica.y"
{System.out.println("Declarativa 1");}
break;
case 14:
//#line 37 "gramatica.y"
{System.out.println("Declarativa 2");}
break;
case 15:
//#line 40 "gramatica.y"
{System.out.println("Declarativa var 1");}
break;
case 16:
//#line 41 "gramatica.y"
{errorEnXY("Tipo de la/s variable/s esperado al comienzo de la sentencia");System.out.println("Declarativa var 2");}
break;
case 19:
//#line 46 "gramatica.y"
{errorEnXY("Tipo de la/s variable/s invalido");}
break;
case 22:
//#line 51 "gramatica.y"
{errorEnXY("Se esperaba un identificador o una lista de Identificadores separados por ,");}
break;
case 27:
//#line 60 "gramatica.y"
{errorEnXY("La declaracion necesita un nombre de funcion");}
break;
case 30:
//#line 67 "gramatica.y"
{errorEnXY("Identificador del parametro esperado  en la declaracion de funcion");}
break;
case 32:
//#line 71 "gramatica.y"
{errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 33:
//#line 72 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo y final de la expresion");}
break;
case 34:
//#line 73 "gramatica.y"
{errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 35:
//#line 74 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo y final de la expresion y ; al final de linea");}
break;
case 36:
//#line 75 "gramatica.y"
{errorEnXY("; esperados al final de linea");}
break;
case 37:
//#line 79 "gramatica.y"
{System.out.println("ejecutable 1era regla");}
break;
case 38:
//#line 80 "gramatica.y"
{System.out.println("ejecutable 2era regla");}
break;
case 39:
//#line 83 "gramatica.y"
{System.out.println("inst_ejecutable 1era regla");}
break;
case 40:
//#line 84 "gramatica.y"
{System.out.println("inst_ejecutable 2era regla");}
break;
case 41:
//#line 85 "gramatica.y"
{System.out.println("inst_ejecutable 3era regla");}
break;
case 42:
//#line 86 "gramatica.y"
{System.out.println("inst_ejecutable 4era regla");}
break;
case 43:
//#line 87 "gramatica.y"
{System.out.println("inst_ejecutable 5era regla");}
break;
case 44:
//#line 90 "gramatica.y"
{System.out.println("Asignacion 1era regla");}
break;
case 45:
//#line 91 "gramatica.y"
{errorEnXY("Expresion esperada");}
break;
case 46:
//#line 92 "gramatica.y"
{errorEnXY("Operador de asignacion incorrecto, se esperaba -> =:");}
break;
case 47:
//#line 95 "gramatica.y"
{System.out.println("Expresion 1era regla");}
break;
case 48:
//#line 96 "gramatica.y"
{System.out.println("Expresion 2era regla");}
break;
case 49:
//#line 97 "gramatica.y"
{System.out.println("Expresion 3era regla");}
break;
case 50:
//#line 100 "gramatica.y"
{System.out.println("Termino 1era regla");}
break;
case 51:
//#line 101 "gramatica.y"
{System.out.println("Termino 2era regla");}
break;
case 52:
//#line 102 "gramatica.y"
{System.out.println("Termino 3era regla");}
break;
case 53:
//#line 105 "gramatica.y"
{System.out.println("Factor 1era regla");}
break;
case 54:
//#line 106 "gramatica.y"
{System.out.println("Factor 2era regla");}
break;
case 55:
//#line 107 "gramatica.y"
{verificarRangoDoubleNegativo();System.out.println("Factor 3era regla");}
break;
case 56:
//#line 108 "gramatica.y"
{System.out.println("Factor 4ta regla");}
break;
case 57:
//#line 111 "gramatica.y"
{System.out.println("Retorno funcion 1ra regla");}
break;
case 58:
//#line 112 "gramatica.y"
{System.out.println("Retorno funcion 2ra regla");}
break;
case 59:
//#line 113 "gramatica.y"
{System.out.println("Retorno funcion 3ra regla");}
break;
case 62:
//#line 118 "gramatica.y"
{verificarRangoDoubleNegativo();System.out.println("parametro real");}
break;
case 78:
//#line 148 "gramatica.y"
{errorEnXY("Falta de parentesis al comienzo de la cadena del out");}
break;
case 79:
//#line 149 "gramatica.y"
{errorEnXY("Falta de parentesis al final de la cadena del out");}
break;
case 81:
//#line 153 "gramatica.y"
{errorEnXY("Funcion invocada sin discard del retorno");}
break;
case 82:
//#line 156 "gramatica.y"
{verificarIdIguales(val_peek(14).sval, val_peek(10).sval);}
break;
case 83:
//#line 157 "gramatica.y"
{verificarIdIguales(val_peek(11).sval, val_peek(7).sval);}
break;
case 84:
//#line 158 "gramatica.y"
{verificarIdIguales(val_peek(14).sval, val_peek(10).sval);}
break;
case 85:
//#line 159 "gramatica.y"
{verificarIdIguales(val_peek(11).sval, val_peek(7).sval);}
break;
//#line 862 "Parser.java"
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
