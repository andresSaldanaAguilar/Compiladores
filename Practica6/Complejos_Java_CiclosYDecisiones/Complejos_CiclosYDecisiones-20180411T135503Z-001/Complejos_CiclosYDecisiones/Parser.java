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






//#line 2 "complejos.y"
  import java.io.*;  
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
public final static short BLTIN=257;
public final static short IF=258;
public final static short ELSE=259;
public final static short WHILE=260;
public final static short EQ=261;
public final static short NEQ=262;
public final static short DIG=263;
public final static short VAR=264;
public final static short CNUMBER=265;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    1,    2,    2,    5,    4,
    6,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,
};
final static short yylen[] = {                            2,
    0,    2,    3,    3,    3,    3,    1,    4,    3,    1,
    0,    1,    1,    1,    4,    3,    3,    3,    3,    3,
    3,    3,    3,
};
final static short yydefred[] = {                         1,
    0,    0,   10,    0,   12,    2,    0,    0,    0,    0,
    0,    0,    0,   14,    0,    3,    4,    0,    0,    0,
    0,    0,    0,    5,    0,    0,    0,    0,    0,   23,
    0,    0,    0,    0,    0,    0,   20,    0,   11,    0,
   15,    9,    8,
};
final static short yydgoto[] = {                          1,
   14,    9,   10,   11,   27,   43,
};
final static short yysindex[] = {                         0,
  -10,  -35,    0,  -53,    0,    0,  -36,   -1,   18,   -3,
   -5,  -36,  -36,    0,  -31,    0,    0,  -36,  -36,  -36,
  -36,  -36,  -36,    0, -250,  -36,  -38,  -24,    3,    0,
    3,    3,  -27,  -27,  -91,  -91,    0,  -16,    0,    3,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,   -9,    0,    0,    0,    9,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   -4,    0,
   12,   14,   31,   55,   39,   47,    0,    0,    0,   33,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
   46,   32,   92,    0,    0,    0,
};
final static int YYTABLESIZE=271;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          6,
   13,    7,   25,    7,   12,    6,   24,   13,   16,   30,
   22,   21,   37,   20,   22,   23,   41,   22,   21,   23,
   20,   21,   23,   22,   42,   22,   21,   17,   20,    7,
   23,   13,   13,   13,   26,   13,    6,   13,   22,   21,
   17,   20,    7,   23,   22,   21,    8,   20,   18,   23,
   14,   14,   21,   14,   22,   14,   19,    0,   39,    0,
    0,    0,   25,    0,   16,    0,   25,    0,    0,   25,
    0,   17,    0,   17,    0,   17,    0,   25,    0,   18,
   18,   18,    0,   18,   13,   18,    0,   19,   19,   19,
   25,   19,    0,   19,    0,   16,   25,   16,   15,   16,
    0,    0,   14,   28,   29,    0,    0,    0,    0,   31,
   32,   33,   34,   35,   36,    0,    0,   38,   40,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   18,
   19,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    2,    0,
    2,    3,    0,    0,    0,    4,    5,    4,    5,   18,
   19,    0,    0,   18,   19,    0,   18,   19,    0,    0,
    0,    0,    0,    0,   18,   19,    2,    0,    0,    3,
    0,   13,   13,    4,    5,    0,    0,   18,   19,    0,
    0,    0,    0,   18,   19,    0,    0,    0,    0,   14,
   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         10,
   10,   40,   94,   40,   40,   10,   10,   61,   10,   41,
   42,   43,  263,   45,   42,   47,   41,   42,   43,   47,
   45,   10,   47,   10,   41,   42,   43,   10,   45,   40,
   47,   41,   42,   43,   40,   45,   41,   47,   42,   43,
   10,   45,   10,   47,   42,   43,    1,   45,   10,   47,
   42,   43,   41,   45,   41,   47,   10,   -1,   27,   -1,
   -1,   -1,   94,   -1,   10,   -1,   94,   -1,   -1,   94,
   -1,   41,   -1,   43,   -1,   45,   -1,   94,   -1,   41,
   42,   43,   -1,   45,   94,   47,   -1,   41,   42,   43,
   94,   45,   -1,   47,   -1,   41,   94,   43,    7,   45,
   -1,   -1,   94,   12,   13,   -1,   -1,   -1,   -1,   18,
   19,   20,   21,   22,   23,   -1,   -1,   26,   27,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  261,
  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,
  257,  260,   -1,   -1,   -1,  264,  265,  264,  265,  261,
  262,   -1,   -1,  261,  262,   -1,  261,  262,   -1,   -1,
   -1,   -1,   -1,   -1,  261,  262,  257,   -1,   -1,  260,
   -1,  261,  262,  264,  265,   -1,   -1,  261,  262,   -1,
   -1,   -1,   -1,  261,  262,   -1,   -1,   -1,   -1,  261,
  262,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=265;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,"'\\n'",null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'",
null,"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,
null,null,"'='",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,"'^'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"BLTIN","IF","ELSE","WHILE","EQ","NEQ",
"DIG","VAR","CNUMBER",
};
final static String yyrule[] = {
"$accept : list",
"list :",
"list : list '\\n'",
"list : list asgn '\\n'",
"list : list stmt '\\n'",
"list : list exp '\\n'",
"asgn : VAR '=' exp",
"stmt : exp",
"stmt : while cond stmt end",
"cond : '(' exp ')'",
"while : WHILE",
"end :",
"exp : CNUMBER",
"exp : VAR",
"exp : asgn",
"exp : BLTIN '(' exp ')'",
"exp : exp '+' exp",
"exp : exp '-' exp",
"exp : exp '*' exp",
"exp : exp '/' exp",
"exp : exp '^' DIG",
"exp : exp EQ exp",
"exp : exp NEQ exp",
"exp : '(' exp ')'",
};

//#line 83 "complejos.y"

/** CÓDIGO DE SOPORTE **/
  static Maquina maq = new Maquina();
  private Yylex lexer;

  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new ParserVal(0);      
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }  

    return yyl_return;
  }


  public void yyerror (String error) {
    System.err.println ("Error: " + error);
  }


  public Parser(Reader r) {
    lexer = new Yylex(r, this);
  }
  
  boolean flag = false;
  
  public static void main(String args[]) throws IOException {    
    System.out.println(".:: Complex Number Calculator ::.");

    Parser yyparser;
    
    System.out.print("Expression: ");
    
	  yyparser = new Parser(new InputStreamReader(System.in));    

    maq.initCode();

    yyparser.yyparse();

  }
//#line 298 "Parser.java"
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
//#line 20 "complejos.y"
{ /*maq.code("printVar"); */maq.code("STOP"); 
                          maq.execute(flag, 0); flag = true; 
                        }
break;
case 4:
//#line 23 "complejos.y"
{maq.code("STOP"); maq.execute(flag, 0); flag = true;}
break;
case 5:
//#line 24 "complejos.y"
{  maq.code("printComplex"); maq.code("STOP"); 
                            maq.execute(flag, 0); flag = true;
                        }
break;
case 6:
//#line 28 "complejos.y"
{ int numI = maq.code("varPush");
                      maq.code((Cadena) val_peek(2).obj);
                      maq.code("asgVar"); 
                      System.out.println("ASGNNI: " + numI); 
                      yyval = new ParserVal(new Integer(numI) - 6);     
                    }
break;
case 7:
//#line 35 "complejos.y"
{}
break;
case 8:
//#line 36 "complejos.y"
{ maq.getProg().setElementAt(val_peek(1).obj, (int) val_peek(3).obj + 1);
                                maq.getProg().setElementAt(val_peek(0).obj, (int) val_peek(3).obj + 2); 
                                }
break;
case 9:
//#line 40 "complejos.y"
{ maq.code("STOP");
                    System.out.println("exp @: " + val_peek(1).obj);
                    yyval = new ParserVal(val_peek(1).obj);
                  }
break;
case 10:
//#line 45 "complejos.y"
{ int numI = maq.code("whileCode"); 
                               maq.code("STOP"); maq.code("STOP");
                               yyval = new ParserVal(new Integer(numI));
                             }
break;
case 11:
//#line 50 "complejos.y"
{ maq.code("STOP");
           yyval = new ParserVal(new Integer(maq.getProg().size())); 
         }
break;
case 12:
//#line 54 "complejos.y"
{ Complejo c = (Complejo) val_peek(0).obj; 
                                    int numI = maq.code("cNumber");                                   
                                    maq.code(c);
                                    yyval = new ParserVal(new Integer(numI));
                                  }
break;
case 13:
//#line 59 "complejos.y"
{ int numI = maq.code("varPush");
                                    maq.code((Cadena) val_peek(0).obj);
                                    maq.code("getVarValue");                                   
                                    yyval = new ParserVal(new Integer(numI));
                                  }
break;
case 15:
//#line 65 "complejos.y"
{ maq.code("bltinPush");
                                    maq.code((Cadena) val_peek(3).obj);
                                    maq.code("bltin");                                    
                                  }
break;
case 16:
//#line 69 "complejos.y"
{ maq.code("add"); }
break;
case 17:
//#line 70 "complejos.y"
{ maq.code("sub"); }
break;
case 18:
//#line 71 "complejos.y"
{ maq.code("mult"); }
break;
case 19:
//#line 72 "complejos.y"
{ maq.code("div"); }
break;
case 20:
//#line 73 "complejos.y"
{ maq.code("powN");
                                    maq.code((Cadena) val_peek(0).obj);
                                    maq.code("pow");                                    
                                  }
break;
case 21:
//#line 78 "complejos.y"
{ maq.code("eq");}
break;
case 22:
//#line 79 "complejos.y"
{ maq.code("neq");}
break;
case 23:
//#line 80 "complejos.y"
{ yyval = val_peek(1); }
break;
//#line 560 "Parser.java"
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
