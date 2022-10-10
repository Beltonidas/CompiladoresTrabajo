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
   24,   24,   24,   17,   17,   26,   26,   27,   27,   25,
   30,   31,   31,   31,   31,   31,   31,   18,   18,   18,
   19,   19,   20,   20,   20,   20,   32,   32,   28,   28,
   29,   29,   29,   29,
};
final static short yylen[] = {                            2,
    2,    1,    1,    1,    4,    3,    2,    2,    1,    1,
    2,    2,    1,    1,    3,    2,    1,    1,    1,    1,
    3,    1,    2,    3,    5,    3,    2,    6,    1,    2,
    1,    6,    5,    4,    5,    3,    5,    1,    2,    2,
    2,    2,    2,    1,    3,    2,    4,    3,    3,    1,
    3,    3,    1,    1,    1,    2,    1,    3,    4,    6,
    1,    1,    2,    4,    5,    4,    2,    4,    2,    3,
    3,    1,    1,    1,    1,    1,    1,    4,    3,    3,
    2,    1,   17,   14,   19,   16,    1,    1,    2,    1,
    1,    2,    4,    2,
};
final static short yydefred[] = {                         0,
    2,    3,    4,    0,    0,   22,    0,    0,    0,    0,
    0,   17,   18,    0,    0,    0,    0,    0,    0,    9,
   10,   13,   14,    0,    0,    0,   38,    0,    0,    0,
    0,   44,   82,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   27,    0,   81,    0,    0,   39,    0,    0,
    8,   11,   12,   20,    0,   16,    0,   29,   19,    0,
    0,   23,    0,   40,   41,   42,   43,    0,   55,    0,
    0,    0,   53,   57,   61,   62,    0,   58,    0,    0,
    0,    0,    0,    0,    0,   79,    0,   26,    0,    6,
    0,   15,   21,    0,   30,    0,   24,   56,    0,    0,
    0,    0,   63,    0,   59,    0,    0,   73,   74,   72,
   75,   76,   77,    0,   70,    0,    0,    0,   91,   67,
    0,   64,    0,   78,    0,    5,    0,    0,    0,    0,
   51,   52,    0,    0,    0,   92,    0,   94,    0,   90,
    0,   69,   65,    0,    0,   25,   60,    0,    0,   66,
   89,    0,    0,    0,    0,    0,   93,   68,    0,    0,
   28,    0,    0,    0,    0,    0,    0,    0,   34,    0,
    0,    0,   33,    0,   35,    0,   87,   88,    0,   32,
    0,    0,    0,    0,    0,    0,   84,    0,    0,    0,
   86,    0,    0,   83,    0,   85,
};
final static short yydgoto[] = {                          4,
    5,   18,   19,   20,   21,   22,   23,   24,   25,   26,
   62,   63,  155,   71,  119,   28,   29,   30,   31,   32,
   72,   73,   33,   79,   39,   85,  123,  139,  140,   83,
  114,  179,
};
final static short yysindex[] = {                      -136,
    0,    0,    0,    0, -110,    0,  -25,   14,   56,  -32,
   -5,    0,    0, -152,   78, -157,  -92,   19,   46,    0,
    0,    0,    0, -123,   33,  -38,    0,   80,   89,   92,
  103,    0,    0,   -7,    2,   64, -105,   -7,  -86,  142,
  -72,  148,    0,  158,    0,  -64,  -25,    0,   66,  -92,
    0,    0,    0,    0,   49,    0,  -57,    0,    0,  145,
  -52,    0,    5,    0,    0,    0,    0,  158,    0,  -50,
   37,   24,    0,    0,    0,    0,  -47,    0,   65,   -7,
  175,   -6,  176,  -75, -127,    0,  180,    0,  -51,    0,
   85,    0,    0, -195,    0, -195,    0,    0,   -7,   -7,
   -7,   -7,    0,   18,    0,   37,  -35,    0,    0,    0,
    0,    0,    0,   -7,    0,   79,  167,  185,    0,    0,
  -58,    0,  -30,    0,  -21,    0,  115,  -24,   24,   24,
    0,    0,  202,  -27,   37,    0,  -11,    0,  100,    0,
  185,    0,    0,  194,  -92,    0,    0,    4,  195,    0,
    0,  117,    6,  170,  139,  206,    0,    0,  -20,  -29,
    0,   25,   23,   -7,   42,  -20,  224,   45,    0,  226,
   36,   83,    0,  236,    0,  238,    0,    0,   40,    0,
   83,  263,   51,   20,  266,  185,    0,   29,  133,  185,
    0,  249,  153,    0,  253,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,   17,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  313,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  259,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   22,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   16,    0,    0,    0,    0,    0,    0,  -41,    0,    0,
  261,  -36,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  262,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  265,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -31,   -9,
    0,    0,    0,    0,  286,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  204,    0,    0,    0,    0,    0,
    0,    0,    0,  207,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   41,   26,   50,    0,    0,    1,  309,    0,
   -4,  242,    0,   60,   62,    0,    0,    0,    0,    0,
   57,   68,   30,  239,    0,    0,    0,  -71,  244,    0,
  -39,  159,
};
final static int YYTABLESIZE=457;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         54,
   54,   54,   60,   54,   50,   54,   50,   41,   50,   48,
  164,   48,   17,   48,   35,   70,   60,   54,   54,   54,
   54,    7,   50,   50,   50,   50,   61,   48,   48,   48,
   48,   49,   36,   49,   43,   49,   99,   70,  100,  112,
  111,  113,   78,   45,   52,   60,   77,  118,   96,   49,
   49,   49,   49,  112,  111,  113,   31,   49,   97,   31,
   20,   59,   77,   74,  141,  101,   27,   74,   53,  152,
  102,   37,   12,   13,   52,   20,   57,   48,   27,   99,
   27,  100,  170,   54,   99,  174,  100,   99,   50,  100,
   91,   56,   57,   48,  127,   38,   61,   82,   53,   47,
  169,    8,    9,  173,   44,  105,   10,   92,  104,   74,
   27,   27,   14,   15,  189,   49,   52,   46,  193,  163,
    1,    2,    3,  146,   80,  177,  171,  178,   74,   74,
   74,   74,    6,   54,  121,  122,  137,  136,   64,  106,
   53,   50,  186,   74,    7,    6,    7,   65,    8,    9,
   66,  190,   27,   10,   11,  129,  130,   12,   13,   14,
   15,   67,   16,    6,    7,   81,    8,    9,  131,  132,
   51,   10,   11,  135,   84,   12,   13,   14,   15,   52,
   16,   47,   86,    8,    9,  154,   87,   88,   10,   74,
   90,  116,   89,   74,   14,   15,  117,   35,   47,   93,
    8,    9,   94,   53,   95,   10,   27,   98,  116,  126,
  103,   14,   15,  117,  107,   27,  115,   58,   59,  165,
  124,  134,  125,  168,  150,  138,   40,   68,   69,   12,
   13,   58,  143,   54,   54,   54,  144,  145,   50,   50,
   50,  158,  147,   48,   48,   48,  148,  149,   34,   68,
   69,   42,  153,  157,  108,  109,  110,  192,   75,   76,
   58,  156,  159,  161,  162,   49,   49,   49,  108,  109,
  110,   31,   19,   19,   75,   76,   47,  195,    8,    9,
  167,  166,  172,   10,  175,   47,  116,    8,    9,   14,
   15,  117,   10,  176,  180,  116,  181,  182,   14,   15,
  117,    6,    7,  184,    8,    9,  188,  194,  185,   10,
   11,  196,    1,   12,   13,   14,   15,   46,   16,   45,
   80,    6,    7,   47,    8,    9,   71,  120,   36,   10,
   11,   37,   55,   12,   13,   14,   15,  128,   16,  183,
    6,    7,  133,    8,    9,    0,    0,    0,   10,   11,
    0,    0,   12,   13,   14,   15,   47,   16,    8,    9,
    0,    0,    0,   10,  142,    0,  116,    0,    0,   14,
   15,  117,    0,   47,    0,    8,    9,    0,    0,    0,
   10,    0,  151,  116,    0,    0,   14,   15,  117,   47,
    0,    8,    9,    0,    0,  151,   10,    0,    0,  116,
    0,    0,   14,   15,  117,    0,    0,    0,    0,   47,
    0,    8,    9,    0,    0,    0,   10,    0,    0,  116,
    0,    0,   14,   15,  117,    6,    7,  187,    8,    9,
    0,  191,  151,   10,   11,  160,  151,   12,   13,   14,
   15,   47,   16,    8,    9,    0,    0,    0,   10,    0,
    0,  116,    0,    0,   14,   15,  117,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   41,   45,   41,   47,   43,   40,   45,   41,
   40,   43,  123,   45,   40,   45,   41,   59,   60,   61,
   62,    0,   59,   60,   61,   62,   26,   59,   60,   61,
   62,   41,   58,   43,   40,   45,   43,   45,   45,   60,
   61,   62,   41,   14,   19,   41,   45,  123,   44,   59,
   60,   61,   62,   60,   61,   62,   41,   17,   63,   44,
   44,  257,   45,   34,  123,   42,    5,   38,   19,  141,
   47,   58,  268,  269,   49,   59,   44,   16,   17,   43,
   19,   45,   41,  125,   43,   41,   45,   43,  125,   45,
   50,   59,   44,  125,   94,   40,   96,   38,   49,  257,
   59,  259,  260,   59,  257,   41,  264,   59,   44,   80,
   49,   50,  270,  271,  186,  125,   91,   40,  190,  159,
  257,  258,  259,  128,   61,   43,  166,   45,   99,  100,
  101,  102,  256,  257,  262,  263,   58,   59,   59,   80,
   91,  123,  123,  114,  123,  256,  257,   59,  259,  260,
   59,  123,   91,  264,  265,   99,  100,  268,  269,  270,
  271,   59,  273,  256,  257,  271,  259,  260,  101,  102,
  125,  264,  265,  114,  261,  268,  269,  270,  271,  154,
  273,  257,   41,  259,  260,  145,  259,   40,  264,  160,
  125,  267,  257,  164,  270,  271,  272,   40,  257,  257,
  259,  260,   58,  154,  257,  264,  145,  258,  267,  125,
  258,  270,  271,  272,   40,  154,   41,  256,  257,  160,
   41,  257,  274,  164,  125,   59,  259,  257,  258,  268,
  269,  256,  263,  275,  276,  277,  258,  123,  275,  276,
  277,  125,   41,  275,  276,  277,  274,  259,  274,  257,
  258,  257,   59,   59,  275,  276,  277,  125,  257,  258,
  256,  258,  257,  125,   59,  275,  276,  277,  275,  276,
  277,  256,  256,  257,  257,  258,  257,  125,  259,  260,
  258,  257,   59,  264,   59,  257,  267,  259,  260,  270,
  271,  272,  264,  258,   59,  267,   59,  258,  270,  271,
  272,  256,  257,   41,  259,  260,   41,   59,  258,  264,
  265,   59,    0,  268,  269,  270,  271,   59,  273,   59,
   59,  256,  257,   59,  259,  260,   41,   84,  125,  264,
  265,  125,   24,  268,  269,  270,  271,   96,  273,  181,
  256,  257,  104,  259,  260,   -1,   -1,   -1,  264,  265,
   -1,   -1,  268,  269,  270,  271,  257,  273,  259,  260,
   -1,   -1,   -1,  264,  121,   -1,  267,   -1,   -1,  270,
  271,  272,   -1,  257,   -1,  259,  260,   -1,   -1,   -1,
  264,   -1,  139,  267,   -1,   -1,  270,  271,  272,  257,
   -1,  259,  260,   -1,   -1,  152,  264,   -1,   -1,  267,
   -1,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,  257,
   -1,  259,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,
   -1,   -1,  270,  271,  272,  256,  257,  184,  259,  260,
   -1,  188,  189,  264,  265,  266,  193,  268,  269,  270,
  271,  257,  273,  259,  260,   -1,   -1,   -1,  264,   -1,
   -1,  267,   -1,   -1,  270,  271,  272,
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

//#line 178 "gramatica.y"

public static final String ANSI_RESET ="\u001B[0m";
public static final String ANSI_RED = "\u001B[31m";
public static final String ANSI_GREEN = "\u001B[32m";
public static final String ANSI_YELLOW = "\u001B[33m";
public static final String ANSI_BLUE = "\u001B[34m";

public void errorEnXY(String msg){
	int linea,caracter=0;
	linea = AnalizadorLexico.getLinea();
	caracter = AnalizadorLexico.getCaracter();
	yynerrs++;
	System.out.println(ANSI_RED+"!|!|!|! Error en linea: "+linea+", caracter: "+caracter+". Errores hasta ahora: "+yynerrs+"\n"+msg+ANSI_RESET);
}

public void verificarIdIguales(String id_1, String id_2){
	if (!id_1.equals(id_2)){
		errorEnXY("Identificadores no coincidentes");
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
	System.out.println(ANSI_YELLOW+"!|/|/|! Warning en linea: "+linea+", caracter: "+caracter+"\n"+msg+ANSI_RESET);
}

private void imprimirMSGEstructura(String msg){
	int linea=0;
	linea = AnalizadorLexico.getLinea();
	System.out.println(ANSI_BLUE+"/|/|/|/: "+msg+" termina de reconocerse en la linea: "+linea+"."+ANSI_RESET);
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
//#line 522 "Parser.java"
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
{programaListo();}
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
{imprimirMSGEstructura("Declaracion de variable/s");}
break;
case 14:
//#line 37 "gramatica.y"
{imprimirMSGEstructura("Declaracion de funcion");}
break;
case 16:
//#line 41 "gramatica.y"
{errorEnXY("Tipo de la/s variable/s esperado al comienzo de la sentencia");}
break;
case 19:
//#line 46 "gramatica.y"
{errorEnXY("Tipo de la/s variable/s invalido");}
break;
case 22:
//#line 51 "gramatica.y"
{errorEnXY("Se esperaba un identificador o una lista de identificadores separados por ,");}
break;
case 27:
//#line 60 "gramatica.y"
{errorEnXY("La declaracion de la funcion necesita un nombre");}
break;
case 29:
//#line 64 "gramatica.y"
{errorEnXY("En la declaracion de la funcion falta: ),:,{ o }");}
break;
case 31:
//#line 68 "gramatica.y"
{errorEnXY("Identificador del parametro esperado  en la declaracion de funcion");}
break;
case 33:
//#line 72 "gramatica.y"
{errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 34:
//#line 73 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo y final de la expresion");}
break;
case 35:
//#line 74 "gramatica.y"
{errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 36:
//#line 75 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo y final de la expresion y ; al final de linea");}
break;
case 37:
//#line 76 "gramatica.y"
{errorEnXY("; esperados al final de linea");}
break;
case 39:
//#line 81 "gramatica.y"
{imprimirMSGEstructura("Defer de instruccion ejecutable");}
break;
case 40:
//#line 84 "gramatica.y"
{imprimirMSGEstructura("Asignacion");}
break;
case 41:
//#line 85 "gramatica.y"
{imprimirMSGEstructura("Seleccion If");}
break;
case 42:
//#line 86 "gramatica.y"
{imprimirMSGEstructura("Impresion a Consola");}
break;
case 43:
//#line 87 "gramatica.y"
{imprimirMSGEstructura("Invocacion de Funcion");}
break;
case 44:
//#line 88 "gramatica.y"
{imprimirMSGEstructura("Loop For");}
break;
case 46:
//#line 92 "gramatica.y"
{errorEnXY("Expresion esperada despues de la asignacion");}
break;
case 47:
//#line 93 "gramatica.y"
{errorEnXY("Operador de asignacion incorrecto, se esperaba -> =:");}
break;
case 56:
//#line 108 "gramatica.y"
{verificarRangoDoubleNegativo();}
break;
case 63:
//#line 119 "gramatica.y"
{verificarRangoDoubleNegativo();}
break;
case 79:
//#line 149 "gramatica.y"
{errorEnXY("Falta de parentesis al comienzo de la cadena del out");}
break;
case 80:
//#line 150 "gramatica.y"
{errorEnXY("Falta de parentesis al final de la cadena del out");}
break;
case 82:
//#line 154 "gramatica.y"
{errorEnXY("Funcion invocada sin discard del retorno");}
break;
case 83:
//#line 157 "gramatica.y"
{verificarIdIguales(val_peek(14).sval, val_peek(10).sval);}
break;
case 84:
//#line 158 "gramatica.y"
{verificarIdIguales(val_peek(11).sval, val_peek(7).sval);}
break;
case 85:
//#line 159 "gramatica.y"
{verificarIdIguales(val_peek(14).sval, val_peek(10).sval);}
break;
case 86:
//#line 160 "gramatica.y"
{verificarIdIguales(val_peek(11).sval, val_peek(7).sval);}
break;
//#line 811 "Parser.java"
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
