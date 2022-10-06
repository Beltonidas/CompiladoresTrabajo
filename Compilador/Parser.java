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
    9,    7,    7,    7,   10,   11,   12,   12,   13,   13,
   13,   13,   13,   13,    5,    5,   15,   15,   15,   15,
   15,   16,   16,   16,   16,   14,   14,   14,   21,   21,
   21,   22,   22,   22,   22,   22,   22,   17,   17,   24,
   24,   25,   25,   23,   28,   29,   29,   29,   29,   29,
   29,   18,   18,   18,   19,   19,   20,   20,   20,   20,
   30,   30,   26,   26,   27,   27,   27,   27,
};
final static short yylen[] = {                            2,
    2,    1,    1,    1,    4,    3,    2,    2,    1,    1,
    2,    2,    1,    1,    3,    2,    1,    1,    1,    1,
    3,    2,    3,    5,    3,    6,    2,    1,    6,    5,
    4,    5,    3,    5,    1,    2,    2,    2,    2,    2,
    2,    3,    5,    4,    4,    3,    3,    1,    3,    3,
    1,    1,    1,    2,    3,    4,    6,    5,    6,    4,
    2,    4,    2,    3,    3,    1,    1,    1,    1,    1,
    1,    4,    3,    3,    4,    3,   16,   14,   18,   16,
    1,    1,    2,    1,    1,    2,    4,    2,
};
final static short yydefred[] = {                         0,
    2,    3,    4,    0,    0,    0,    0,    0,    0,    0,
   17,   18,    0,    0,    0,    0,    0,    0,    0,    9,
   10,   13,   14,    0,    0,    0,   35,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   36,    0,    0,   53,    0,    0,    0,    8,
   11,   12,   20,    0,   16,    0,   19,    0,    0,   22,
    0,   37,   38,   39,   40,   41,    0,    0,    0,   51,
    0,    0,    0,    0,    0,   73,    0,   25,    0,    0,
    6,    0,   54,   76,    0,   15,   21,    0,   27,    0,
   23,    0,    0,    0,   45,    0,    0,    0,   67,   68,
   66,   69,   70,   71,    0,   64,    0,    0,    0,   85,
   61,    0,    0,    0,   72,   75,    0,   55,    0,    5,
    0,    0,   43,    0,    0,   49,   50,    0,    0,   86,
    0,   88,    0,   84,    0,   63,   58,    0,    0,    0,
   56,    0,   24,    0,    0,   60,   83,    0,   59,    0,
    0,    0,    0,    0,   87,   62,    0,   57,    0,   26,
    0,    0,    0,    0,    0,    0,    0,   31,    0,    0,
    0,   30,    0,   32,    0,   81,   82,    0,   29,    0,
    0,    0,    0,    0,    0,   78,    0,    0,    0,   80,
   77,    0,   79,
};
final static short yydgoto[] = {                          4,
    5,   18,   19,   20,   21,   22,   23,   24,   25,   26,
   60,   61,  153,   68,  110,   28,   29,   30,   31,   32,
   69,   70,   36,   75,  114,  133,  134,   73,  105,  178,
};
final static short yysindex[] = {                      -116,
    0,    0,    0,    0,   17, -257,   18,  -13,  -32, -195,
    0,    0,   48,   67,  116,   92,  -34,   10,   32,    0,
    0,    0,    0, -137,   36,  -26,    0,  104,  106,  108,
  115,  119,   -2, -144,  -34,  -86,  135,  -80,  140,  -34,
  -75, -257,    0,   47,  143,    0,  -74,  144,   92,    0,
    0,    0,    0,   46,    0,  -71,    0,  130,  -67,    0,
   90,    0,    0,    0,    0,    0,  -34,  105,   68,    0,
  151,   -6,  152,  107, -171,    0,  153,    0,  156,  -82,
    0,   26,    0,    0,   62,    0,    0, -172,    0, -172,
    0,  110,  -34,  -34,    0,  -34,  -34,  -59,    0,    0,
    0,    0,    0,    0,  -34,    0,   55,  142,  179,    0,
    0,  124,  146,  -61,    0,    0,  -55,    0,  117,    0,
   84,  167,    0,   68,   68,    0,    0,  -65,   23,    0,
  -48,    0,   -7,    0,  179,    0,    0,  154,  155,  -34,
    0,   92,    0,  -46,  157,    0,    0,  133,    0,  -42,
  176,   77,   93,  161,    0,    0,    3,    0,    4,    0,
  -35,  -34,  -34,   63,    3,   34,   85,    0,  162,  -34,
  109,    0,  166,    0,   76,    0,    0,  -29,    0,  109,
  185,  -25,  149,  191,  179,    0,  170,  188,  179,    0,
    0,  214,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    2,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  237,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   13,  -41,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  118,    0,
    0,    0,    0,    0,    0,    0,    0,  189,  -36,    0,
    0,    0,    0,    0,    0,    0,  190,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  192,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -31,   -9,    0,    0,    0,  197,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  148,    0,    0,    0,    0,    0,    0,
    0,    0,  150,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   29,   -3,   16,    0,    0,   21,  270,    0,
  -39,  205,    0,    7,   54,    0,    0,    0,    0,    0,
    6,   41,    0,    0,    0,  -60,   12,    0, -117,  128,
};
final static int YYTABLESIZE=486;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         52,
   52,   52,   52,   52,   48,   52,   48,   38,   48,   46,
   47,   46,    7,   46,   58,   51,   33,   52,   52,   52,
   52,   91,   48,   48,   48,   48,   35,   46,   46,   46,
   46,   47,   17,   47,   52,   47,   93,   67,   94,  162,
   51,   72,   47,  163,   44,   20,   59,  170,   47,   47,
   47,   47,   47,  103,  102,  104,   17,   48,   27,   52,
   20,   39,  103,  102,  104,   93,  118,   94,   43,   27,
   47,   17,   27,   92,  148,   34,   93,   85,   94,   56,
   79,   51,  143,   52,   57,  111,   17,   40,   48,   56,
  112,  113,  171,   46,   55,   11,   12,   27,  124,  125,
   52,   17,   27,  169,   86,   93,   41,   94,  121,   96,
   59,  129,  131,  130,   97,   47,   17,  146,   93,   53,
   94,  168,  119,  136,  188,  173,   71,   93,  192,   94,
   58,   17,   49,   90,  180,    7,  126,  127,   27,   16,
    1,    2,    3,  172,  147,   95,   17,   93,   51,   94,
  123,  176,   93,  177,   94,   17,   50,  141,   28,  147,
  140,   28,   62,   17,   63,  164,   64,   52,  166,  167,
  152,   81,   17,   65,   74,   76,  175,   66,   77,   78,
  151,   80,   82,   83,   84,   87,  120,   88,   17,   89,
   98,  117,  106,  115,  186,   27,  116,  128,  190,  147,
  132,  138,  139,  147,  137,   27,  142,   58,  144,   17,
  145,  154,  149,  150,  157,  155,  158,  160,   17,  161,
  174,  165,   45,   46,  179,  183,   37,   17,  181,  109,
   57,  187,  184,   52,   52,   52,    1,   65,   48,   48,
   48,   11,   12,   46,   46,   46,  135,   42,   74,   42,
   44,    7,    8,   17,   45,   46,    9,  156,   19,  107,
   45,   46,   13,   14,  108,   47,   47,   47,   99,  100,
  101,  185,   33,    6,   34,    7,    8,   99,  100,  101,
    9,   10,   45,   46,   11,   12,   13,   14,    6,   15,
    7,    8,  189,   54,  122,    9,   10,    0,    0,   11,
   12,   13,   14,    6,   15,    7,    8,  182,    0,    0,
    9,   10,  191,    0,   11,   12,   13,   14,    6,   15,
    7,    8,    0,    0,    0,    9,   10,    0,    0,   11,
   12,   13,   14,    6,   15,    7,    8,    0,  193,    0,
    9,   10,  159,    0,   11,   12,   13,   14,    6,   15,
    7,    8,    0,    0,    0,    9,   10,    0,    0,   11,
   12,   13,   14,   42,   15,    7,    8,    0,    0,    0,
    9,    0,   42,  107,    7,    8,   13,   14,  108,    9,
   42,    0,    7,    8,    0,   13,   14,    9,    0,   42,
  107,    7,    8,   13,   14,  108,    9,    0,    0,  107,
    0,    0,   13,   14,  108,   42,    0,    7,    8,    0,
    0,    0,    9,    0,    0,  107,    0,    0,   13,   14,
  108,    0,    0,    0,    0,    0,   42,    0,    7,    8,
    0,    0,    0,    9,    0,   42,  107,    7,    8,   13,
   14,  108,    9,    0,   42,  107,    7,    8,   13,   14,
  108,    9,    0,    0,  107,    0,    0,   13,   14,  108,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   42,    0,    7,    8,    0,    0,    0,    9,    0,    0,
  107,    0,    0,   13,   14,  108,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   41,   47,   43,   40,   45,   41,
   45,   43,    0,   45,   41,   19,  274,   59,   60,   61,
   62,   61,   59,   60,   61,   62,   40,   59,   60,   61,
   62,   41,   40,   43,   19,   45,   43,   40,   45,  157,
   44,   35,   45,   40,   16,   44,   26,  165,   45,   59,
   60,   61,   62,   60,   61,   62,   40,   17,    5,   44,
   59,  257,   60,   61,   62,   43,   41,   45,   15,   16,
   45,   40,   19,   67,  135,   58,   43,   49,   45,   44,
   40,   85,  122,  125,  257,   74,   40,   40,  125,   44,
  262,  263,   59,  125,   59,  268,  269,   44,   93,   94,
   85,   40,   49,   41,   59,   43,   40,   45,   88,   42,
   90,  105,   58,   59,   47,  125,   40,  125,   43,  257,
   45,   59,   82,  112,  185,   41,  271,   43,  189,   45,
   41,   40,  123,   44,   59,  123,   96,   97,   85,  123,
  257,  258,  259,   59,  133,   41,   40,   43,  152,   45,
   41,   43,   43,   45,   45,   40,  125,   41,   41,  148,
   44,   44,   59,   40,   59,  159,   59,  152,  162,  163,
  142,  125,   40,   59,  261,   41,  170,   59,  259,   40,
  140,  257,   40,  258,   41,  257,  125,   58,   40,  257,
   40,  274,   41,   41,  183,  142,   41,  257,  187,  188,
   59,  263,  258,  192,   59,  152,  123,   41,  274,   40,
  259,  258,   59,   59,  257,   59,   41,  125,   40,   59,
   59,  257,  257,  258,   59,   41,  259,   40,  258,  123,
  257,   41,  258,  275,  276,  277,    0,   41,  275,  276,
  277,  268,  269,  275,  276,  277,  123,   59,   59,  257,
   59,  259,  260,   40,  257,  258,  264,  125,  257,  267,
  257,  258,  270,  271,  272,  275,  276,  277,  275,  276,
  277,  123,  125,  257,  125,  259,  260,  275,  276,  277,
  264,  265,  257,  258,  268,  269,  270,  271,  257,  273,
  259,  260,  123,   24,   90,  264,  265,   -1,   -1,  268,
  269,  270,  271,  257,  273,  259,  260,  180,   -1,   -1,
  264,  265,  125,   -1,  268,  269,  270,  271,  257,  273,
  259,  260,   -1,   -1,   -1,  264,  265,   -1,   -1,  268,
  269,  270,  271,  257,  273,  259,  260,   -1,  125,   -1,
  264,  265,  266,   -1,  268,  269,  270,  271,  257,  273,
  259,  260,   -1,   -1,   -1,  264,  265,   -1,   -1,  268,
  269,  270,  271,  257,  273,  259,  260,   -1,   -1,   -1,
  264,   -1,  257,  267,  259,  260,  270,  271,  272,  264,
  257,   -1,  259,  260,   -1,  270,  271,  264,   -1,  257,
  267,  259,  260,  270,  271,  272,  264,   -1,   -1,  267,
   -1,   -1,  270,  271,  272,  257,   -1,  259,  260,   -1,
   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,  270,  271,
  272,   -1,   -1,   -1,   -1,   -1,  257,   -1,  259,  260,
   -1,   -1,   -1,  264,   -1,  257,  267,  259,  260,  270,
  271,  272,  264,   -1,  257,  267,  259,  260,  270,  271,
  272,  264,   -1,   -1,  267,   -1,   -1,  270,  271,  272,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,   -1,  259,  260,   -1,   -1,   -1,  264,   -1,   -1,
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
"inst_ejecutable : for_continue ';'",
"asignacion : id SIMB_ASIGNACION expresion",
"asignacion : id SIMB_ASIGNACION '(' expresion ')'",
"asignacion : id SIMB_ASIGNACION '(' expresion",
"asignacion : id SIMB_ASIGNACION expresion ')'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : id",
"factor : cte",
"factor : '-' cte",
"factor : id '(' ')'",
"factor : id '(' factor ')'",
"factor : id '(' factor ',' factor ')'",
"seleccion : If condicion_if then_selec end_if ';'",
"seleccion : If condicion_if then_selec else_selecc end_if ';'",
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
"invocar_fun : discard '(' factor ')'",
"invocar_fun : '(' factor ')'",
"for_continue : For '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' '{' ejecutable_for '}'",
"for_continue : For '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' inst_ejecutable_for",
"for_continue : cadena ':' For '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' '{' ejecutable_for '}'",
"for_continue : cadena ':' For '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' inst_ejecutable_for",
"mas_o_menos : '+'",
"mas_o_menos : '-'",
"ejecutable_for : ejecutable_for inst_ejecutable_for",
"ejecutable_for : inst_ejecutable_for",
"inst_ejecutable_for : inst_ejecutable",
"inst_ejecutable_for : Break ';'",
"inst_ejecutable_for : Break ':' cadena ';'",
"inst_ejecutable_for : Continue ';'",
};

//#line 168 "gramatica.y"

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
//#line 513 "Parser.java"
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
//#line 89 "gramatica.y"
{System.out.println("Asignacion 2era regla");}
break;
case 44:
//#line 90 "gramatica.y"
{errorEnXY("Parentesis esperados al final de expresion");System.out.println("Asignacion 3era regla");}
break;
case 45:
//#line 91 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo de expresion");System.out.println("Asignacion 4era regla");}
break;
case 46:
//#line 94 "gramatica.y"
{System.out.println("Expresion 1era regla");}
break;
case 47:
//#line 95 "gramatica.y"
{System.out.println("Expresion 2era regla");}
break;
case 48:
//#line 96 "gramatica.y"
{System.out.println("Expresion 3era regla");}
break;
case 49:
//#line 99 "gramatica.y"
{System.out.println("Termino 1era regla");}
break;
case 50:
//#line 100 "gramatica.y"
{System.out.println("Termino 2era regla");}
break;
case 51:
//#line 101 "gramatica.y"
{System.out.println("Termino 3era regla");}
break;
case 52:
//#line 104 "gramatica.y"
{System.out.println("Factor 1era regla");}
break;
case 53:
//#line 105 "gramatica.y"
{System.out.println("Factor 2era regla");}
break;
case 54:
//#line 106 "gramatica.y"
{verificarRangoDoubleNegativo();System.out.println("Factor 3era regla");}
break;
case 55:
//#line 107 "gramatica.y"
{System.out.println("Factor 4era regla");}
break;
case 56:
//#line 108 "gramatica.y"
{System.out.println("Factor 5era regla");}
break;
case 57:
//#line 109 "gramatica.y"
{System.out.println("Factor 6era regla");}
break;
case 73:
//#line 139 "gramatica.y"
{errorEnXY("Falta de parentesis al comienzo de la cadena del out");}
break;
case 74:
//#line 140 "gramatica.y"
{errorEnXY("Falta de parentesis al final de la cadena del out");}
break;
case 76:
//#line 144 "gramatica.y"
{errorEnXY("Funcion invocada sin discard del retorno");}
break;
case 77:
//#line 147 "gramatica.y"
{verificarIdIguales(val_peek(13).sval, val_peek(9).sval);}
break;
case 78:
//#line 148 "gramatica.y"
{verificarIdIguales(val_peek(11).sval, val_peek(7).sval);}
break;
case 79:
//#line 149 "gramatica.y"
{verificarIdIguales(val_peek(13).sval, val_peek(9).sval);}
break;
case 80:
//#line 150 "gramatica.y"
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
