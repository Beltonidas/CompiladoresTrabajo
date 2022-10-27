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
   19,   19,   20,   20,   20,   20,   32,   32,   32,   28,
   28,   29,   29,   29,   29,
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
    2,    1,   17,   14,   19,   16,    1,    1,    1,    2,
    1,    1,    2,    4,    2,
};
final static short yydefred[] = {                         0,
    2,    3,    4,    0,    0,   22,    0,    0,    0,    0,
   17,   18,    0,    0,    0,    0,    0,    0,    9,   10,
   13,   14,    0,    0,    0,   38,    0,    0,    0,    0,
   44,   82,    0,    0,    0,    0,    0,    0,    0,    0,
   27,    0,   81,    0,    0,   39,    0,    0,    8,   11,
   12,   20,    0,   16,    0,   29,   19,    0,    0,   23,
    0,   40,   41,   42,   43,    0,   55,    0,    0,    0,
   53,   57,   61,   62,   58,    0,    0,    0,    0,    0,
    0,    0,    0,   79,    0,   26,    0,    6,    0,   15,
   21,    0,   30,    0,   24,   56,    0,    0,    0,    0,
   63,    0,   59,    0,    0,   73,   74,   72,   75,   76,
   77,    0,   70,    0,    0,    0,   92,   67,    0,   64,
    0,   78,    0,    5,    0,    0,    0,    0,   51,   52,
    0,    0,    0,   93,    0,   95,    0,   91,    0,   69,
   65,    0,    0,   25,   60,    0,    0,   66,   90,    0,
    0,    0,    0,    0,   94,   68,    0,    0,   28,    0,
    0,    0,    0,    0,    0,    0,   34,    0,    0,    0,
   33,    0,   35,    0,   89,   87,   88,    0,   32,    0,
    0,    0,    0,    0,    0,   84,    0,    0,    0,   86,
    0,    0,   83,    0,   85,
};
final static short yydgoto[] = {                          4,
    5,   17,   18,   19,   20,   21,   22,   23,   24,   25,
   60,   61,  153,   69,  117,   27,   28,   29,   30,   31,
   70,   71,   32,   77,   37,   83,  121,  137,  138,   81,
  112,  178,
};
final static short yysindex[] = {                       -84,
    0,    0,    0,    0, -101,    0,  -25,   35,  -32,   -5,
    0,    0, -165,   66, -147, -122,   -1,   40,    0,    0,
    0,    0, -185,   41,  -38,    0,   67,   78,  101,  103,
    0,    0,   -7,    2,  -48,   -7,  -95,  130,  -81,  141,
    0,  144,    0,  -70,  -25,    0,   58, -122,    0,    0,
    0,    0,   42,    0,  -69,    0,    0,  140,  -66,    0,
    5,    0,    0,    0,    0,  144,    0,  -59,   50,   86,
    0,    0,    0,    0,    0,  -58,   95,  165,   -7,   -6,
  167,  -75, -110,    0,  168,    0,  -68,    0,   77,    0,
    0, -154,    0, -154,    0,    0,   -7,   -7,   -7,   -7,
    0,   18,    0,  -46,   50,    0,    0,    0,    0,    0,
    0,   -7,    0,   99,  154,  201,    0,    0,  -50,    0,
  -51,    0,  -42,    0,  110,  -24,   86,   86,    0,    0,
  185,  -37,   50,    0,  -19,    0,  100,    0,  201,    0,
    0,  184, -122,    0,    0,  -11,  189,    0,    0,  117,
   -4,  186,  129,  203,    0,    0,  -20,  -29,    0,    6,
   -7,   -7,   37,  -20,   59,   38,    0,  205,   -7,   21,
    0,  206,    0,   62,    0,    0,    0,   23,    0,   21,
  239,   25,   22,  243,  201,    0,   31,  133,  201,    0,
  226,  153,    0,  228,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,   17,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  290,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  240,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   27,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   16,    0,
    0,    0,    0,    0,    0,  -41,    0,    0,  248,  -36,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  253,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  258,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -31,   -9,    0,    0,
    0,    0,  275,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  194,    0,    0,    0,    0,    0,    0,    0,
    0,  195,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   43,   51,   52,    0,    0,   33,  298,    0,
  -17,  230,    0,  270,   72,    0,    0,    0,    0,    0,
   79,   80,   32,  223,    0,    0,    0,  -77,  257,    0,
  -90,  150,
};
final static int YYTABLESIZE=473;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         54,
   54,   54,   58,   54,   50,   54,   50,   39,   50,   48,
  162,   48,   79,   48,   34,   68,   58,   54,   54,   54,
   54,   16,   50,   50,   50,   50,    7,   48,   48,   48,
   48,   49,   35,   49,   41,   49,   97,   68,   98,  110,
  109,  111,   75,   95,   43,   58,   76,  116,   94,   49,
   49,   49,   49,  110,  109,  111,   31,   59,   47,   31,
   20,  150,   76,  176,   72,  177,  161,   72,   50,   51,
    6,   52,  139,  169,   36,   20,   26,  168,  172,   97,
   97,   98,   98,   54,   55,   55,   46,   26,   50,   26,
   89,   42,   97,   48,   98,  167,  171,   50,   51,   54,
   90,   97,   57,   98,   97,   44,   98,  188,  144,   45,
   72,  192,    8,   11,   12,   49,    9,  170,   26,   26,
  180,   48,   13,   14,  125,   62,   59,   99,   72,   72,
   72,   72,  100,    6,    7,  103,   63,    8,  102,   50,
   51,    9,   10,   72,  185,   11,   12,   13,   14,    7,
   15,  119,  120,  189,    6,    7,  135,  134,    8,   64,
   26,   65,    9,   10,   49,   82,   11,   12,   13,   14,
   84,   15,    1,    2,    3,  127,  128,   85,  129,  130,
   86,   45,   88,   34,    8,  152,   87,   91,    9,   72,
   93,  114,   72,   72,   13,   14,  115,   92,   96,  101,
   72,  124,   50,   51,  104,  123,   45,  113,  122,    8,
  132,  141,  136,    9,   26,  142,  114,   56,   57,   13,
   14,  115,   78,   26,  148,  145,   38,   66,   67,   11,
   12,   56,  143,   54,   54,   54,  146,  147,   50,   50,
   50,  156,  151,   48,   48,   48,  154,  155,   33,   66,
   67,   40,  157,  159,  106,  107,  108,  191,   73,   74,
   56,  160,  164,  173,  179,   49,   49,   49,  106,  107,
  108,   31,   19,   19,   73,   74,  175,  194,   45,  183,
  181,    8,  184,  187,  193,    9,  195,   45,  114,    1,
    8,   13,   14,  115,    9,    6,    7,  114,   46,    8,
   13,   14,  115,    9,   10,   80,   45,   11,   12,   13,
   14,   80,   15,    6,    7,   71,   47,    8,   36,   37,
   53,    9,   10,  126,  131,   11,   12,   13,   14,  182,
   15,    0,    6,    7,    0,    0,    8,    0,  118,    0,
    9,   10,    0,    0,   11,   12,   13,   14,  105,   15,
    0,    0,    0,    0,    0,    0,   45,    0,    0,    8,
    0,    0,    0,    9,    0,    0,  114,    0,    0,   13,
   14,  115,    0,   45,    0,  140,    8,    0,    0,    0,
    9,  133,    0,  114,    0,    0,   13,   14,  115,   45,
    0,    0,    8,  149,    0,    0,    9,    0,    0,  114,
    0,    0,   13,   14,  115,    0,  149,    0,    0,   45,
    0,    0,    8,    0,    0,    0,    9,    0,    0,  114,
    0,    0,   13,   14,  115,    0,    0,  163,    0,    0,
  165,  166,    0,    0,    0,    0,    0,    0,  174,  186,
    0,    6,    7,  190,  149,    8,    0,    0,  149,    9,
   10,  158,    0,   11,   12,   13,   14,   45,   15,    0,
    8,    0,    0,    0,    9,    0,    0,  114,    0,    0,
   13,   14,  115,
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
   61,   62,   41,   61,   13,   41,   45,  123,   44,   59,
   60,   61,   62,   60,   61,   62,   41,   25,   16,   44,
   44,  139,   45,   43,   33,   45,  157,   36,   18,   18,
  256,  257,  123,  164,   40,   59,    5,   41,   41,   43,
   43,   45,   45,  125,   44,   44,   15,   16,  125,   18,
   48,  257,   43,  125,   45,   59,   59,   47,   47,   59,
   59,   43,  257,   45,   43,   40,   45,  185,  126,  257,
   79,  189,  260,  268,  269,  125,  264,   59,   47,   48,
   59,  123,  270,  271,   92,   59,   94,   42,   97,   98,
   99,  100,   47,  256,  257,   41,   59,  260,   44,   89,
   89,  264,  265,  112,  123,  268,  269,  270,  271,  123,
  273,  262,  263,  123,  256,  257,   58,   59,  260,   59,
   89,   59,  264,  265,  125,  261,  268,  269,  270,  271,
   41,  273,  257,  258,  259,   97,   98,  259,   99,  100,
   40,  257,  125,   40,  260,  143,  257,  257,  264,  158,
  257,  267,  161,  162,  270,  271,  272,   58,  258,  258,
  169,  125,  152,  152,   40,  274,  257,   41,   41,  260,
  257,  263,   59,  264,  143,  258,  267,  256,  257,  270,
  271,  272,  271,  152,  125,   41,  259,  257,  258,  268,
  269,  256,  123,  275,  276,  277,  274,  257,  275,  276,
  277,  125,   59,  275,  276,  277,  258,   59,  274,  257,
  258,  257,  257,  125,  275,  276,  277,  125,  257,  258,
  256,   59,  257,   59,   59,  275,  276,  277,  275,  276,
  277,  256,  256,  257,  257,  258,  256,  125,  257,   41,
  258,  260,  258,   41,   59,  264,   59,  257,  267,    0,
  260,  270,  271,  272,  264,  256,  257,  267,   59,  260,
  270,  271,  272,  264,  265,   36,   59,  268,  269,  270,
  271,   59,  273,  256,  257,   41,   59,  260,  125,  125,
   23,  264,  265,   94,  102,  268,  269,  270,  271,  180,
  273,   -1,  256,  257,   -1,   -1,  260,   -1,   82,   -1,
  264,  265,   -1,   -1,  268,  269,  270,  271,   79,  273,
   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,  260,
   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,  270,
  271,  272,   -1,  257,   -1,  119,  260,   -1,   -1,   -1,
  264,  112,   -1,  267,   -1,   -1,  270,  271,  272,  257,
   -1,   -1,  260,  137,   -1,   -1,  264,   -1,   -1,  267,
   -1,   -1,  270,  271,  272,   -1,  150,   -1,   -1,  257,
   -1,   -1,  260,   -1,   -1,   -1,  264,   -1,   -1,  267,
   -1,   -1,  270,  271,  272,   -1,   -1,  158,   -1,   -1,
  161,  162,   -1,   -1,   -1,   -1,   -1,   -1,  169,  183,
   -1,  256,  257,  187,  188,  260,   -1,   -1,  192,  264,
  265,  266,   -1,  268,  269,  270,  271,  257,  273,   -1,
  260,   -1,   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,
  270,  271,  272,
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
"for_continue : For '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' '{' ejecutable_for '}' ';'",
"for_continue : For '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' inst_ejecutable_for",
"for_continue : id ':' For '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' '{' ejecutable_for '}' ';'",
"for_continue : id ':' For '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' inst_ejecutable_for",
"mas_o_menos : '+'",
"mas_o_menos : '-'",
"mas_o_menos : error",
"ejecutable_for : ejecutable_for inst_ejecutable_for",
"ejecutable_for : inst_ejecutable_for",
"inst_ejecutable_for : inst_ejecutable",
"inst_ejecutable_for : Break ';'",
"inst_ejecutable_for : Break ':' id ';'",
"inst_ejecutable_for : Continue ';'",
};

//#line 176 "gramatica.y"

public static final String ANSI_RESET ="\u001B[0m";
public static final String ANSI_RED = "\u001B[31m";
public static final String ANSI_GREEN = "\u001B[32m";
public static final String ANSI_YELLOW = "\u001B[33m";
public static final String ANSI_BLUE = "\u001B[34m";
public static final String ANSI_PURPLE ="\u001B[35m";
public static final String ANSI_CYAN = "\u001B[36m";

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
//#line 535 "Parser.java"
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
//#line 13 "gramatica.y"
{programaListo();}
break;
case 3:
//#line 17 "gramatica.y"
{errorEnXY("Nombre del programa invalido. Identificador esperado, constante recibida en cambio");}
break;
case 4:
//#line 18 "gramatica.y"
{errorEnXY("Nombre del programa invalido. Identificador esperado, cadena recibida en cambio");}
break;
case 7:
//#line 23 "gramatica.y"
{errorEnXY("Esperando final de bloque");}
break;
case 8:
//#line 24 "gramatica.y"
{errorEnXY("Esperando comienzo de bloque");}
break;
case 13:
//#line 33 "gramatica.y"
{imprimirMSGEstructura("Declaracion de variable/s");}
break;
case 14:
//#line 34 "gramatica.y"
{imprimirMSGEstructura("Declaracion de funcion");}
break;
case 16:
//#line 38 "gramatica.y"
{errorEnXY("Tipo de la/s variable/s esperado al comienzo de la sentencia");}
break;
case 19:
//#line 43 "gramatica.y"
{errorEnXY("Tipo de la/s variable/s invalido");}
break;
case 22:
//#line 48 "gramatica.y"
{errorEnXY("Se esperaba un identificador o una lista de identificadores separados por ,");}
break;
case 27:
//#line 57 "gramatica.y"
{errorEnXY("La declaracion de la funcion necesita un nombre");}
break;
case 29:
//#line 61 "gramatica.y"
{errorEnXY("En la declaracion de la funcion falta: ),:,{ o }");}
break;
case 31:
//#line 65 "gramatica.y"
{errorEnXY("Identificador del parametro esperado  en la declaracion de funcion");}
break;
case 33:
//#line 69 "gramatica.y"
{errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 34:
//#line 70 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo y final de la expresion");}
break;
case 35:
//#line 71 "gramatica.y"
{errorEnXY("Parentesis esperados al final de la expresion");}
break;
case 36:
//#line 72 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo y final de la expresion y ; al final de linea");}
break;
case 37:
//#line 73 "gramatica.y"
{errorEnXY("; esperados al final de linea");}
break;
case 39:
//#line 78 "gramatica.y"
{imprimirMSGEstructura("Defer de instruccion ejecutable");}
break;
case 40:
//#line 81 "gramatica.y"
{imprimirMSGEstructura("Asignacion");}
break;
case 41:
//#line 82 "gramatica.y"
{imprimirMSGEstructura("Seleccion If");}
break;
case 42:
//#line 83 "gramatica.y"
{imprimirMSGEstructura("Impresion a Consola");}
break;
case 43:
//#line 84 "gramatica.y"
{imprimirMSGEstructura("Invocacion de Funcion");}
break;
case 44:
//#line 85 "gramatica.y"
{imprimirMSGEstructura("Loop For");}
break;
case 46:
//#line 89 "gramatica.y"
{errorEnXY("Expresion esperada despues de la asignacion");}
break;
case 47:
//#line 90 "gramatica.y"
{errorEnXY("Operador de asignacion incorrecto, se esperaba -> =:");}
break;
case 56:
//#line 105 "gramatica.y"
{verificarRangoDoubleNegativo();}
break;
case 63:
//#line 116 "gramatica.y"
{verificarRangoDoubleNegativo();}
break;
case 79:
//#line 146 "gramatica.y"
{errorEnXY("Falta de parentesis al comienzo de la cadena del out");}
break;
case 80:
//#line 147 "gramatica.y"
{errorEnXY("Falta de parentesis al final de la cadena del out");}
break;
case 82:
//#line 151 "gramatica.y"
{errorEnXY("Funcion invocada sin discard del retorno");}
break;
case 83:
//#line 154 "gramatica.y"
{verificarIdIguales(val_peek(14).sval, val_peek(10).sval);verificarConstanteEntera(val_peek(12).sval);verificarConstanteEntera(val_peek(5).sval);}
break;
case 84:
//#line 155 "gramatica.y"
{verificarIdIguales(val_peek(11).sval, val_peek(7).sval);verificarConstanteEntera(val_peek(9).sval);verificarConstanteEntera(val_peek(2).sval);}
break;
case 85:
//#line 156 "gramatica.y"
{verificarIdIguales(val_peek(14).sval, val_peek(10).sval);verificarConstanteEntera(val_peek(12).sval);verificarConstanteEntera(val_peek(5).sval);}
break;
case 86:
//#line 157 "gramatica.y"
{verificarIdIguales(val_peek(11).sval, val_peek(7).sval);verificarConstanteEntera(val_peek(9).sval);verificarConstanteEntera(val_peek(2).sval);}
break;
case 89:
//#line 162 "gramatica.y"
{errorEnXY("Mal declarada la actualizacion de cte de control del for");}
break;
//#line 828 "Parser.java"
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
