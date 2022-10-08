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

boolean yydebug= true;        //do I want debug output?
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
    9,    7,    7,    7,   10,   11,   12,   12,   13,   13,
   13,   13,   13,   13,    5,    5,   15,   15,   15,   15,
   15,   16,   14,   14,   14,   21,   21,   21,   22,   22,
   22,   22,   23,   23,   23,   24,   24,   24,   17,   17,
   26,   26,   27,   27,   25,   30,   31,   31,   31,   31,
   31,   31,   18,   18,   18,   19,   19,   20,   20,   20,
   20,   32,   32,   28,   28,   29,   29,   29,   29,
};
final static short yylen[] = {                            2,
    2,    1,    1,    1,    4,    3,    2,    2,    1,    1,
    2,    2,    1,    1,    3,    2,    1,    1,    1,    1,
    3,    2,    3,    5,    3,    6,    2,    1,    6,    5,
    4,    5,    3,    5,    1,    2,    2,    2,    2,    2,
    1,    3,    3,    3,    1,    3,    3,    1,    1,    1,
    2,    1,    3,    4,    6,    1,    1,    2,    4,    5,
    4,    2,    4,    2,    3,    3,    1,    1,    1,    1,
    1,    1,    4,    3,    3,    2,    1,   17,   14,   19,
   16,    1,    1,    2,    1,    1,    2,    4,    2,
};
final static short yydefred[] = {                         0,
    2,    3,    4,    0,    0,    0,    0,    0,    0,    0,
   17,   18,    0,    0,    0,    0,    0,    0,    9,   10,
   13,   14,    0,    0,    0,   35,    0,    0,    0,    0,
   41,   77,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   76,    0,    0,   36,    0,    0,    8,   11,   12,
   20,    0,   16,    0,   19,    0,    0,   22,    0,   37,
   38,   39,   40,    0,   50,    0,    0,    0,   48,   52,
   56,   57,    0,   53,    0,    0,    0,    0,    0,    0,
   74,    0,   25,    0,    6,    0,   15,   21,    0,   27,
    0,   23,   51,    0,    0,    0,    0,   58,    0,   54,
    0,   68,   69,   67,   70,   71,   72,    0,   65,    0,
    0,    0,   86,   62,    0,   59,    0,   73,    0,    5,
    0,    0,    0,    0,   46,   47,    0,    0,    0,   87,
    0,   89,    0,   85,    0,   64,   60,    0,    0,   24,
   55,    0,    0,   61,   84,    0,    0,    0,    0,    0,
   88,   63,    0,    0,   26,    0,    0,    0,    0,    0,
    0,    0,   31,    0,    0,    0,   30,    0,   32,    0,
   82,   83,    0,   29,    0,    0,    0,    0,    0,    0,
   79,    0,    0,    0,   81,    0,    0,   78,    0,   80,
};
final static short yydgoto[] = {                          4,
    5,   17,   18,   19,   20,   21,   22,   23,   24,   25,
   58,   59,  149,   67,  113,   27,   28,   29,   30,   31,
   68,   69,   32,   75,   37,   80,  117,  133,  134,   78,
  108,  173,
};
final static short yysindex[] = {                      -133,
    0,    0,    0,    0, -110,  -37,   28,   19,  -32, -161,
    0,    0, -148,   77,  155,  140,   12,  -47,    0,    0,
    0,    0, -123,    4,  -26,    0,   78,   80,   83,   87,
    0,    0,  -10,    2, -135,  -10, -118,  103, -111,  111,
  112,    0, -104,  -37,    0,   15,  140,    0,    0,    0,
    0,   21,    0, -101,    0,  104,  -93,    0,   27,    0,
    0,    0,    0,  112,    0,  -92,   57,   40,    0,    0,
    0,    0,  -85,    0,   44,  128,   -6,  124,  -90, -172,
    0,  134,    0,  -98,    0,   32,    0,    0, -195,    0,
 -195,    0,    0,  -10,  -10,  -10,  -10,    0,   -7,    0,
  -79,    0,    0,    0,    0,    0,    0,  -10,    0,   61,
  120, -149,    0,    0,  -74,    0,  -44,    0,  -69,    0,
   97,  189,   40,   40,    0,    0,  191,  -25,   57,    0,
  -21,    0,   47,    0, -149,    0,    0,  193,  140,    0,
    0,   -5,  195,    0,    0,   63,    5,  125,  136,  204,
    0,    0,  -20,  -29,    0,    7,   18,  -10,   34,  -20,
  214,   54,    0,  218,   20,   69,    0,  222,    0,  223,
    0,    0,   29,    0,   69,  249,   35,  -65,  253, -149,
    0,  -56,   84, -149,    0,  236,  100,    0,  239,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    1,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  299,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   22,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   60,    0,    0,    0,
    0,    0,    0,  -41,    0,    0,  250,  -36,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  251,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -31,   -9,    0,    0,    0,    0,  267,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  187,    0,
    0,    0,    0,    0,    0,    0,    0,  188,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   45,   52,   85,    0,    0,   -8,  292,    0,
  -15,  225,    0,  229,  331,    0,    0,    0,    0,    0,
   11,   36,   33,  227,    0,    0,    0, -108,  246,    0,
  -96,  146,
};
final static int YYTABLESIZE=479;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         49,
   49,   49,   34,   49,   45,   49,   45,   39,   45,   43,
  158,   43,   16,   43,   56,   66,   57,   49,   49,   49,
   49,    7,   45,   45,   45,   45,  146,   43,   43,   43,
   43,   44,  112,   44,   66,   44,   94,   73,   95,  106,
  105,  107,   74,   92,   20,   42,   73,   54,  135,   44,
   44,   44,   44,  106,  105,  107,  157,  180,   36,   20,
   46,   55,   53,  165,   54,   70,  184,   56,   70,   49,
   91,  183,   11,   12,  164,  187,   94,   48,   95,   87,
  121,   96,   57,   49,  100,   35,   97,   99,   45,  115,
  116,   86,  163,   43,  168,   40,   94,   49,   95,   94,
   28,   95,   50,   28,  123,  124,  140,   44,   41,    7,
    8,  171,  167,  172,    9,   44,   43,  110,  131,  130,
   13,   14,  111,    1,    2,    3,   70,   70,   70,   70,
   50,  125,  126,   51,   47,   76,   60,   49,   61,   85,
   70,   62,   79,   81,    7,   63,    6,   82,    7,    8,
   83,   34,   84,    9,   10,   88,  120,   11,   12,   13,
   14,   89,   15,   90,  109,   93,   44,  101,    7,    8,
   50,  144,   98,    9,  118,  119,  110,  128,  132,   13,
   14,  111,   44,  148,    7,    8,   70,  152,  138,    9,
   70,   44,  110,    7,    8,   13,   14,  111,    9,   49,
   44,  110,    7,    8,   13,   14,  111,    9,  186,    6,
  110,    7,    8,   13,   14,  111,    9,   10,  137,  139,
   11,   12,   13,   14,  189,   15,   38,   64,   65,   56,
   55,  141,   50,   49,   49,   49,   33,  143,   45,   45,
   45,   11,   12,   43,   43,   43,   64,   65,  142,   71,
   72,  147,  150,  151,  102,  103,  104,   19,   71,   72,
  155,  153,  156,  160,   77,   44,   44,   44,  102,  103,
  104,    6,  166,    7,    8,  161,  169,  170,    9,   10,
  174,  175,   11,   12,   13,   14,  176,   15,    6,  178,
    7,    8,  179,  182,  188,    9,   10,  190,    1,   11,
   12,   13,   14,   44,   15,    7,    8,   66,   42,   75,
    9,   33,   34,  110,   52,  122,   13,   14,  111,   44,
  177,    7,    8,    0,  114,  127,    9,    0,    0,  110,
    0,    0,   13,   14,  111,   26,  129,    0,    0,    0,
   44,    0,    7,    8,    0,   45,   26,    9,   26,    0,
  110,    0,    0,   13,   14,  111,   44,    0,    7,    8,
  136,    0,    0,    9,    0,    0,  110,    0,    0,   13,
   14,  111,    0,    0,    0,    0,   26,   26,  145,    0,
    0,    6,  159,    7,    8,    0,  162,    0,    9,   10,
  154,  145,   11,   12,   13,   14,    6,   15,    7,    8,
    0,    0,    0,    9,   10,    0,    0,   11,   12,   13,
   14,   44,   15,    7,    8,    0,   26,    0,    9,    0,
    0,    0,    0,  181,   13,   14,    0,  185,  145,    0,
    0,    0,  145,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   26,
    0,    0,    0,    0,    0,    0,    0,    0,   26,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   40,   45,   41,   47,   43,   40,   45,   41,
   40,   43,  123,   45,   41,   45,   25,   59,   60,   61,
   62,    0,   59,   60,   61,   62,  135,   59,   60,   61,
   62,   41,  123,   43,   45,   45,   43,   45,   45,   60,
   61,   62,   41,   59,   44,   13,   45,   44,  123,   59,
   60,   61,   62,   60,   61,   62,  153,  123,   40,   59,
   16,  257,   59,  160,   44,   33,  123,   41,   36,   18,
   44,  180,  268,  269,   41,  184,   43,  125,   45,   59,
   89,   42,   91,  125,   41,   58,   47,   44,  125,  262,
  263,   47,   59,  125,   41,  257,   43,   46,   45,   43,
   41,   45,   18,   44,   94,   95,  122,  257,  257,  259,
  260,   43,   59,   45,  264,  125,   40,  267,   58,   59,
  270,  271,  272,  257,  258,  259,   94,   95,   96,   97,
   46,   96,   97,  257,  123,  271,   59,   86,   59,  125,
  108,   59,  261,   41,  123,   59,  257,  259,  259,  260,
   40,   40,  257,  264,  265,  257,  125,  268,  269,  270,
  271,   58,  273,  257,   41,  258,  257,   40,  259,  260,
   86,  125,  258,  264,   41,  274,  267,  257,   59,  270,
  271,  272,  257,  139,  259,  260,  154,  125,  258,  264,
  158,  257,  267,  259,  260,  270,  271,  272,  264,  148,
  257,  267,  259,  260,  270,  271,  272,  264,  125,  257,
  267,  259,  260,  270,  271,  272,  264,  265,  263,  123,
  268,  269,  270,  271,  125,  273,  259,  257,  258,   41,
  257,   41,  148,  275,  276,  277,  274,  259,  275,  276,
  277,  268,  269,  275,  276,  277,  257,  258,  274,  257,
  258,   59,  258,   59,  275,  276,  277,  257,  257,  258,
  125,  257,   59,  257,   36,  275,  276,  277,  275,  276,
  277,  257,   59,  259,  260,  258,   59,  258,  264,  265,
   59,   59,  268,  269,  270,  271,  258,  273,  257,   41,
  259,  260,  258,   41,   59,  264,  265,   59,    0,  268,
  269,  270,  271,  257,  273,  259,  260,   41,   59,   59,
  264,  125,  125,  267,   23,   91,  270,  271,  272,  257,
  175,  259,  260,   -1,   79,   99,  264,   -1,   -1,  267,
   -1,   -1,  270,  271,  272,    5,  108,   -1,   -1,   -1,
  257,   -1,  259,  260,   -1,   15,   16,  264,   18,   -1,
  267,   -1,   -1,  270,  271,  272,  257,   -1,  259,  260,
  115,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,  270,
  271,  272,   -1,   -1,   -1,   -1,   46,   47,  133,   -1,
   -1,  257,  154,  259,  260,   -1,  158,   -1,  264,  265,
  266,  146,  268,  269,  270,  271,  257,  273,  259,  260,
   -1,   -1,   -1,  264,  265,   -1,   -1,  268,  269,  270,
  271,  257,  273,  259,  260,   -1,   86,   -1,  264,   -1,
   -1,   -1,   -1,  178,  270,  271,   -1,  182,  183,   -1,
   -1,   -1,  187,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  139,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  148,
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
"dec_funcion : header_funcion cola_funcion",
"dec_funcion : header_funcion parametro cola_funcion",
"dec_funcion : header_funcion parametro ',' parametro cola_funcion",
"header_funcion : fun id '('",
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

//#line 173 "gramatica.y"

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
//#line 514 "Parser.java"
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
{System.out.println("Programa listo");}
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
case 9:
//#line 29 "gramatica.y"
{System.out.println("Sentencia 1");}
break;
case 10:
//#line 30 "gramatica.y"
{System.out.println("Sentencia 2");}
break;
case 11:
//#line 31 "gramatica.y"
{System.out.println("Sentencia 3");}
break;
case 12:
//#line 32 "gramatica.y"
{System.out.println("Sentencia 4");}
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
case 28:
//#line 65 "gramatica.y"
{errorEnXY("Identificador del parametro esperado  en la declaracion de funcion");}
break;
case 30:
//#line 69 "gramatica.y"
{errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 31:
//#line 70 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo y final de la expresion");}
break;
case 32:
//#line 71 "gramatica.y"
{errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 33:
//#line 72 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo y final de la expresion y ; al final de linea");}
break;
case 34:
//#line 73 "gramatica.y"
{errorEnXY("; esperados al final de linea");}
break;
case 35:
//#line 77 "gramatica.y"
{System.out.println("ejecutable 1era regla");}
break;
case 36:
//#line 78 "gramatica.y"
{System.out.println("ejecutable 2era regla");}
break;
case 37:
//#line 81 "gramatica.y"
{System.out.println("inst_ejecutable 1era regla");}
break;
case 38:
//#line 82 "gramatica.y"
{System.out.println("inst_ejecutable 2era regla");}
break;
case 39:
//#line 83 "gramatica.y"
{System.out.println("inst_ejecutable 3era regla");}
break;
case 40:
//#line 84 "gramatica.y"
{System.out.println("inst_ejecutable 4era regla");}
break;
case 41:
//#line 85 "gramatica.y"
{System.out.println("inst_ejecutable 5era regla");}
break;
case 42:
//#line 88 "gramatica.y"
{System.out.println("Asignacion 1era regla");}
break;
case 43:
//#line 91 "gramatica.y"
{System.out.println("Expresion 1era regla");}
break;
case 44:
//#line 92 "gramatica.y"
{System.out.println("Expresion 2era regla");}
break;
case 45:
//#line 93 "gramatica.y"
{System.out.println("Expresion 3era regla");}
break;
case 46:
//#line 96 "gramatica.y"
{System.out.println("Termino 1era regla");}
break;
case 47:
//#line 97 "gramatica.y"
{System.out.println("Termino 2era regla");}
break;
case 48:
//#line 98 "gramatica.y"
{System.out.println("Termino 3era regla");}
break;
case 49:
//#line 101 "gramatica.y"
{System.out.println("Factor 1era regla");}
break;
case 50:
//#line 102 "gramatica.y"
{System.out.println("Factor 2era regla");}
break;
case 51:
//#line 103 "gramatica.y"
{verificarRangoDoubleNegativo();System.out.println("Factor 3era regla");}
break;
case 52:
//#line 104 "gramatica.y"
{System.out.println("Factor 4ta regla");}
break;
case 53:
//#line 107 "gramatica.y"
{System.out.println("Retorno funcion 1ra regla");}
break;
case 54:
//#line 108 "gramatica.y"
{System.out.println("Retorno funcion 2ra regla");}
break;
case 55:
//#line 109 "gramatica.y"
{System.out.println("Retorno funcion 3ra regla");}
break;
case 58:
//#line 114 "gramatica.y"
{verificarRangoDoubleNegativo();System.out.println("parametro real");}
break;
case 74:
//#line 144 "gramatica.y"
{errorEnXY("Falta de parentesis al comienzo de la cadena del out");}
break;
case 75:
//#line 145 "gramatica.y"
{errorEnXY("Falta de parentesis al final de la cadena del out");}
break;
case 77:
//#line 149 "gramatica.y"
{errorEnXY("Funcion invocada sin discard del retorno");}
break;
case 78:
//#line 152 "gramatica.y"
{verificarIdIguales(val_peek(14).sval, val_peek(10).sval);}
break;
case 79:
//#line 153 "gramatica.y"
{verificarIdIguales(val_peek(11).sval, val_peek(7).sval);}
break;
case 80:
//#line 154 "gramatica.y"
{verificarIdIguales(val_peek(14).sval, val_peek(10).sval);}
break;
case 81:
//#line 155 "gramatica.y"
{verificarIdIguales(val_peek(11).sval, val_peek(7).sval);}
break;
//#line 859 "Parser.java"
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
