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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;;
//#line 27 "Parser.java"




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
public final static short if=260;
public final static short then=261;
public final static short else=262;
public final static short end_if=263;
public final static short out=264;
public final static short fun=265;
public final static short return=266;
public final static short break=267;
public final static short ui8=268;
public final static short f64=269;
public final static short discard=270;
public final static short for=271;
public final static short continue=272;
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
    3,    3,    4,    4,    6,    6,    6,    8,    8,    8,
    9,    9,    7,    7,    7,   10,   11,   12,   12,   13,
   13,   13,   13,   13,   13,    5,    5,   15,   15,   15,
   15,   15,   16,   16,   16,   16,   14,   14,   14,   21,
   21,   21,   22,   22,   22,   22,   22,   22,   17,   17,
   24,   24,   25,   25,   23,   28,   29,   29,   29,   29,
   29,   29,   18,   18,   18,   19,   19,   20,   20,   20,
   20,   30,   30,   26,   26,   27,   27,   27,   27,
};
final static short yylen[] = {                            2,
    2,    1,    1,    1,    4,    3,    2,    2,    1,    1,
    2,    2,    1,    1,    3,    2,    2,    1,    1,    1,
    1,    3,    2,    3,    5,    3,    6,    2,    1,    6,
    5,    4,    5,    3,    5,    1,    2,    2,    2,    2,
    2,    2,    3,    5,    4,    4,    3,    3,    1,    3,
    3,    1,    1,    1,    2,    3,    4,    6,    5,    6,
    4,    2,    3,    1,    3,    3,    1,    1,    1,    1,
    1,    1,    4,    3,    3,    4,    3,   16,   14,   18,
   16,    1,    1,    2,    1,    1,    2,    4,    2,
};
final static short yydefred[] = {                         0,
    2,    3,    4,    0,    0,    0,    0,    0,    0,    0,
   18,   19,    0,    0,    0,    0,    0,    0,    0,    9,
   10,   13,   14,    0,    0,    0,   36,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   37,    0,    0,   54,    0,    0,    0,    8,
   11,   12,   21,    0,   17,    0,   20,    0,    0,   23,
    0,   38,   39,   40,   41,   42,    0,    0,    0,   52,
    0,    0,    0,    0,    0,   74,    0,   26,    0,    0,
    6,    0,   55,   77,    0,   15,   22,    0,   28,    0,
   24,    0,    0,    0,   46,    0,    0,    0,   68,   69,
   67,   70,   71,   72,    0,   65,    0,    0,    0,   86,
   62,    0,    0,    0,   64,   73,   76,    0,   56,    0,
    5,    0,    0,   44,    0,    0,   50,   51,    0,    0,
   87,    0,   89,    0,   85,   59,    0,    0,    0,    0,
   57,    0,   25,    0,    0,   61,   84,   63,   60,    0,
    0,    0,    0,    0,   88,    0,   58,    0,   27,    0,
    0,    0,    0,    0,    0,    0,   32,    0,    0,    0,
   31,    0,   33,    0,   82,   83,    0,   30,    0,    0,
    0,    0,    0,    0,   79,    0,    0,    0,   81,   78,
    0,   80,
};
final static short yydgoto[] = {                          4,
    5,   18,   19,   20,   21,   22,   23,   24,   25,   26,
   60,   61,  153,   68,  110,   28,   29,   30,   31,   32,
   69,   70,   36,   75,  114,  134,  135,   73,  105,  177,
};
final static short yysindex[] = {                       -89,
    0,    0,    0,    0,  -40, -232,   -5,   26,  -25, -163,
    0,    0,   73,   85,  224,   78,  -35,  -19,   16,    0,
    0,    0,    0, -128,   15,  -12,    0,   75,   89,   98,
  100,  103,   35, -119,  -35, -107,  122,  -93,  131,  -35,
  -83, -232,    0,   31,  135,    0,  -82,  137,   78,    0,
    0,    0,    0,   28,    0,  -78,    0,  124,  -77,    0,
   38,    0,    0,    0,    0,    0,  -35,   94,   43,    0,
  144,  -26,  145,  109,   93,    0,  146,    0,  147,  -81,
    0,    5,    0,    0,   48,    0,    0, -126,    0, -126,
    0,  110,  -35,  -35,    0,  -35,  -35,  -72,    0,    0,
    0,    0,    0,    0,  -35,    0,   41,  133,  118,    0,
    0,  136,  118,  -69,    0,    0,    0,  -62,    0,   54,
    0,   74,  157,    0,   43,   43,    0,    0,  -74,   76,
    0,  -58,    0,  127,    0,    0,  143,  148,  149,  -35,
    0,   78,    0,  -56,  150,    0,    0,    0,    0,  -54,
  164,   63,   81,  151,    0,  129,    0,   52,    0,  -46,
  -35,  -35,   64,  129,    6,   87,    0,  153,  -35,   95,
    0,  154,    0,    9,    0,    0,  -37,    0,   95,  177,
  -32,  159,  186,  118,    0,  175,  199,  118,    0,    0,
  215,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,   -4,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  235,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   13,  -39,    0,    0,    0,    0,    0,
    0,    0,    0,    1,    0,    0,    0,    0,   71,    0,
    0,    0,    0,    0,    0,    0,    0,  181,  -34,    0,
    0,    0,    0,    0,    0,    0,  185,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  195,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -29,    2,    0,    0,    0,  218,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  158,    0,    0,    0,    0,    0,    0,    0,
    0,  169,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    8,   25,   29,    0,    0,   32,  273,    0,
  -22,  213,    0,    3,   62,    0,    0,    0,    0,    0,
   51,   20,    0,    0,    0,  -95,  318,    0,  -80,  132,
};
final static int YYTABLESIZE=509;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         17,
   16,   53,   53,   53,   53,   53,   49,   53,   49,   47,
   49,   47,    7,   47,   38,   47,   93,  137,   94,   53,
   53,   53,   53,   44,   49,   49,   49,   49,   58,   47,
   47,   47,   47,  103,  102,  104,   48,   72,   91,   21,
   16,   33,   48,   51,   48,  119,   48,   52,   93,   47,
   94,   93,   34,   94,   21,   17,   85,   59,   56,   79,
   48,   48,   48,   48,  170,   35,   27,  179,   51,   92,
   17,   56,   52,   55,   67,  161,   43,   27,   58,   47,
   27,   90,   16,  169,   96,   53,   86,   17,  187,   97,
   49,  162,  191,   39,  141,   47,   47,  140,  132,  131,
  143,  120,   17,   49,  168,   27,   93,  130,   94,   51,
   27,   29,   40,   52,   29,  127,  128,   17,   93,  122,
   94,   59,  167,   16,   41,   16,   48,  172,   53,   93,
   57,   94,   17,   62,   95,    7,   93,  175,   94,  176,
   50,   11,   12,  125,  126,  171,   27,   63,   17,  152,
  124,   71,   93,   74,   94,   81,   64,   17,   65,  151,
  163,   66,   76,  165,  166,   77,   17,    1,    2,    3,
   78,  174,  121,   80,   82,   83,   51,   84,   87,   89,
   52,   88,   17,   98,  129,  106,  116,  117,  103,  102,
  104,  133,  118,  138,  136,  139,  142,   58,   17,  144,
  145,  154,  156,   27,  157,  159,  149,  150,  155,  160,
  164,  173,  178,   27,   17,  113,    6,  182,    7,    8,
  180,   45,   46,    9,   10,  183,  186,   11,   12,   13,
   14,  109,   15,   37,    1,   53,   53,   53,   17,   43,
   49,   49,   49,   75,   57,   47,   47,   47,   99,  100,
  101,  146,   20,   45,   17,   11,   12,   16,   66,   16,
   16,   45,   46,   17,   16,   16,   16,  148,   16,   16,
   16,   16,    6,   16,    7,    8,   48,   48,   48,    9,
   10,  184,   34,   11,   12,   13,   14,    6,   15,    7,
    8,   45,   46,   35,    9,   10,   54,  188,   11,   12,
   13,   14,  123,   15,    6,    0,    7,    8,   45,   46,
  181,    9,   10,    0,    0,   11,   12,   13,   14,    6,
   15,    7,    8,  190,    0,    0,    9,   10,  158,    0,
   11,   12,   13,   14,    6,   15,    7,    8,    0,  192,
    0,    9,   10,    0,    0,   11,   12,   13,   14,   42,
   15,    7,    8,    0,    0,  112,    9,    0,    0,  107,
    0,    0,   13,   14,  108,   42,    0,    7,    8,    0,
    0,    0,    9,    0,   42,  107,    7,    8,   13,   14,
  108,    9,    0,   42,  107,    7,    8,   13,   14,  108,
    9,  111,  115,  107,    0,    0,   13,   14,  108,   42,
    0,    7,    8,   99,  100,  101,    9,    0,    0,  107,
    0,    0,   13,   14,  108,   42,    0,    7,    8,    0,
    0,    0,    9,    0,    0,  107,    0,    0,   13,   14,
  108,   42,    0,    7,    8,    0,    0,    0,    9,    0,
    0,  107,    0,    0,   13,   14,  108,    0,    0,    0,
    0,  147,    0,    0,  147,   42,    0,    7,    8,    0,
    0,    0,    9,    0,    0,  107,    0,    0,   13,   14,
  108,   42,    0,    7,    8,    0,    0,    0,    9,    0,
   42,  107,    7,    8,   13,   14,  108,    9,    0,    0,
    0,    0,    0,   13,   14,    0,    0,    0,    0,  185,
    0,    0,    0,  189,  147,    0,    0,    0,  147,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   41,   42,   43,   44,   45,   41,   47,   43,   45,
   45,   41,    0,   43,   40,   45,   43,  113,   45,   59,
   60,   61,   62,   16,   59,   60,   61,   62,   41,   59,
   60,   61,   62,   60,   61,   62,   17,   35,   61,   44,
   40,  274,   41,   19,   43,   41,   45,   19,   43,   45,
   45,   43,   58,   45,   59,   40,   49,   26,   44,   40,
   59,   60,   61,   62,   59,   40,    5,   59,   44,   67,
   40,   44,   44,   59,   40,  156,   15,   16,   41,   45,
   19,   44,  123,  164,   42,  125,   59,   40,  184,   47,
  125,   40,  188,  257,   41,  125,   45,   44,   58,   59,
  123,   82,   40,  123,   41,   44,   43,  105,   45,   85,
   49,   41,   40,   85,   44,   96,   97,   40,   43,   88,
   45,   90,   59,  123,   40,  125,  125,   41,  257,   43,
  257,   45,   40,   59,   41,  123,   43,   43,   45,   45,
  125,  268,  269,   93,   94,   59,   85,   59,   40,  142,
   41,  271,   43,  261,   45,  125,   59,   40,   59,  140,
  158,   59,   41,  161,  162,  259,   40,  257,  258,  259,
   40,  169,  125,  257,   40,  258,  152,   41,  257,  257,
  152,   58,   40,   40,  257,   41,   41,   41,   60,   61,
   62,   59,  274,  263,   59,  258,  123,   41,   40,  274,
  259,  258,  257,  142,   41,  125,   59,   59,   59,   59,
  257,   59,   59,  152,   40,  123,  257,   41,  259,  260,
  258,  257,  258,  264,  265,  258,   41,  268,  269,  270,
  271,  123,  273,  259,    0,  275,  276,  277,   40,   59,
  275,  276,  277,   59,  257,  275,  276,  277,  275,  276,
  277,  125,  257,   59,   40,  268,  269,  257,   41,  259,
  260,  257,  258,   40,  264,  265,  266,  125,  268,  269,
  270,  271,  257,  273,  259,  260,  275,  276,  277,  264,
  265,  123,  125,  268,  269,  270,  271,  257,  273,  259,
  260,  257,  258,  125,  264,  265,   24,  123,  268,  269,
  270,  271,   90,  273,  257,   -1,  259,  260,  257,  258,
  179,  264,  265,   -1,   -1,  268,  269,  270,  271,  257,
  273,  259,  260,  125,   -1,   -1,  264,  265,  266,   -1,
  268,  269,  270,  271,  257,  273,  259,  260,   -1,  125,
   -1,  264,  265,   -1,   -1,  268,  269,  270,  271,  257,
  273,  259,  260,   -1,   -1,  263,  264,   -1,   -1,  267,
   -1,   -1,  270,  271,  272,  257,   -1,  259,  260,   -1,
   -1,   -1,  264,   -1,  257,  267,  259,  260,  270,  271,
  272,  264,   -1,  257,  267,  259,  260,  270,  271,  272,
  264,   74,   75,  267,   -1,   -1,  270,  271,  272,  257,
   -1,  259,  260,  275,  276,  277,  264,   -1,   -1,  267,
   -1,   -1,  270,  271,  272,  257,   -1,  259,  260,   -1,
   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,  270,  271,
  272,  257,   -1,  259,  260,   -1,   -1,   -1,  264,   -1,
   -1,  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,
   -1,  134,   -1,   -1,  137,  257,   -1,  259,  260,   -1,
   -1,   -1,  264,   -1,   -1,  267,   -1,   -1,  270,  271,
  272,  257,   -1,  259,  260,   -1,   -1,   -1,  264,   -1,
  257,  267,  259,  260,  270,  271,  272,  264,   -1,   -1,
   -1,   -1,   -1,  270,  271,   -1,   -1,   -1,   -1,  182,
   -1,   -1,   -1,  186,  187,   -1,   -1,   -1,  191,
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
null,null,null,null,null,null,null,"id","cte","cadena","if","then","else",
"end_if","out","fun","return","break","ui8","f64","discard","for","continue",
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
"dec_variables : tipo list_variables",
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
"cuerpo_fun : sentencia return '(' expresion ')' ';'",
"cuerpo_fun : sentencia return '(' expresion ';'",
"cuerpo_fun : sentencia return expresion ';'",
"cuerpo_fun : sentencia return expresion ')' ';'",
"cuerpo_fun : sentencia return expresion",
"cuerpo_fun : sentencia return '(' expresion ')'",
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
"seleccion : if condicion_if then_selec end_if ';'",
"seleccion : if condicion_if then_selec else_selecc end_if ';'",
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
"invocar_fun : discard '(' factor ')'",
"invocar_fun : '(' factor ')'",
"for_continue : for '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' '{' ejecutable_for '}'",
"for_continue : for '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' inst_ejecutable_for",
"for_continue : cadena ':' for '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' '{' ejecutable_for '}'",
"for_continue : cadena ':' for '(' id SIMB_ASIGNACION cte ';' id comparador expresion ';' mas_o_menos cte ')' inst_ejecutable_for",
"mas_o_menos : '+'",
"mas_o_menos : '-'",
"ejecutable_for : ejecutable_for inst_ejecutable_for",
"ejecutable_for : inst_ejecutable_for",
"inst_ejecutable_for : inst_ejecutable",
"inst_ejecutable_for : break ';'",
"inst_ejecutable_for : break ':' cadena ';'",
"inst_ejecutable_for : continue ';'",
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
//#line 507 "Parser.java"
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
case 3:
//#line 28 "gramatica.y"
{errorEnXY("Nombre del programa invalido. Identificador esperado, constante recibida en cambio")}
break;
case 4:
//#line 29 "gramatica.y"
{errorEnXY("Nombre del programa invalido. Identificador esperado, cadena recibida en cambio")}
break;
case 7:
//#line 34 "gramatica.y"
{errorEnXY("Esperando final de bloque")}
break;
case 8:
//#line 35 "gramatica.y"
{errorEnXY("Esperando comienzo de bloque")}
break;
case 16:
//#line 49 "gramatica.y"
{errorEnXY("; esperado al final de la sentencia")}
break;
case 17:
//#line 50 "gramatica.y"
{errorEnXY("Tipo de la/s variable/s esperado al comienzo de la sentencia")}
break;
case 20:
//#line 55 "gramatica.y"
{System.err.println("Tipo de la/s variable/s invalido"}
break;
case 29:
//#line 74 "gramatica.y"
{errorEnXY("Identificador del parametro esperado  en la declaracion de funcion")}
break;
case 31:
//#line 78 "gramatica.y"
{errorEnXY("Parentesis esperados al final de la expresion")}
break;
case 32:
//#line 79 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo y final de la expresion")}
break;
case 33:
//#line 80 "gramatica.y"
{errorEnXY("Parentesis esperados al final de la expresion")}
break;
case 34:
//#line 81 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo y final de la expresion y ; al final de linea")}
break;
case 35:
//#line 82 "gramatica.y"
{errorEnXY("; esperados al final de linea")}
break;
case 45:
//#line 99 "gramatica.y"
{errorEnXY("Parentesis esperados al final de expresion")}
break;
case 46:
//#line 100 "gramatica.y"
{errorEnXY("Parentesis esperados al comienzo de expresion")}
break;
case 55:
//#line 115 "gramatica.y"
{verificarRangoDoubleNegativo();}
break;
case 74:
//#line 148 "gramatica.y"
{errorEnXY("Falta de parentesis al comienzo de la cadena del out")}
break;
case 75:
//#line 149 "gramatica.y"
{errorEnXY("Falta de parentesis al final de la cadena del out")}
break;
case 77:
//#line 153 "gramatica.y"
{errorEnXY("Funcion invocada sin discard del retorno")}
break;
case 78:
//#line 156 "gramatica.y"
{verificarIdIguales(val_peek(13).sval, val_peek(9).sval)}
break;
case 79:
//#line 157 "gramatica.y"
{verificarIdIguales(val_peek(11).sval, val_peek(7).sval)}
break;
case 80:
//#line 158 "gramatica.y"
{verificarIdIguales(val_peek(13).sval, val_peek(9).sval)}
break;
case 81:
//#line 159 "gramatica.y"
{verificarIdIguales(val_peek(11).sval, val_peek(7).sval)}
break;
//#line 748 "Parser.java"
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
