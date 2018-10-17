%{
  import java.io.*;
  import java.util.ArrayList;
%}

%token VAR
%token CNUMBER
%token BLTIN
%token DIG

%right '='
%left '-' '+'
%left '*' '/'

%%
list:	/* nada */
			| list '\n'
      | list asgn '\n' {maq.code("pop");maq.code("STOP");return 1;}
			| list exp '\n' {maq.code("print");maq.code("STOP");return 1;}
			;
asgn: VAR '=' exp   {
        $$=$3; maq.code("varpush"); 
        maq.code(((Algo)$1.obj).simb); 
        maq.code("assign");
      }
      ;
exp:  CNUMBER       {Complejo c = (Complejo) $1.obj;}
      | VAR         { Cadena c      = (Cadena) $1.obj;
                      Symbol s      = lookUpTable(c.getCadena());
                      if (s != null) {
                        Complejo data = (Complejo) s.getData();
                        $$ = new ParserVal(data);
                      } else {
                        yyerror("Variable no declarada");
                        System.exit(0);
                      }
                    }
     | asgn
     | BLTIN '(' exp ',' DIG ')'  { Cadena c1       = (Cadena) $1.obj;
                                    Symbol s        = lookUpTable(c1.getCadena());
                                    String fName    = s.getName();
                                    Complejo res    = new Complejo(0, 0);
                                    Cadena powN     = (Cadena) $5.obj;

                                    if (fName.compareTo("pow") == 0) {
                                      res = f.pow((Complejo) $3.obj, powN);
                                    }
                                    $$ = new ParserVal(res);

                                  }
     | BLTIN '(' exp ')'          { Cadena c        = (Cadena) $1.obj;
                                    Symbol s        = lookUpTable(c.getCadena());
                                    String fName    = s.getName();
                                    Complejo res    = new Complejo(0, 0);

                                    if (fName.compareTo("exp") == 0) {
                                      res = f.exp((Complejo) $3.obj);
                                    } else if (fName.compareTo("sin") == 0) {
                                      res = f.sinus((Complejo) $3.obj);
                                    } else if (fName.compareTo("cos") == 0) {
                                      res = f.cosine((Complejo) $3.obj);
                                    }
                                    $$ = new ParserVal(res);
                                  }
     | exp '+' exp                { Complejo c1   = (Complejo) $1.obj; Complejo c2 = (Complejo) $3.obj;
                                    Complejo res  = new Complejo(0,0);
                                    res.sumaComplejos(c1, c2);
                                    $$            = new ParserVal(res);
                                  }
     | exp '-' exp                { Complejo c1   = (Complejo) $1.obj; Complejo c2 = (Complejo) $3.obj;
                                    Complejo res  = new Complejo(0,0);
                                    res.restaComplejos(c1, c2);
                                    $$            = new ParserVal(res);
                                  }
     | exp '*' exp                { Complejo c1   = (Complejo) $1.obj; Complejo c2 = (Complejo) $3.obj;
                                    Complejo res  = new Complejo(0,0);
                                    res.multiplicaComplejos(c1, c2);
                                    $$            = new ParserVal(res);
                                  }
     | exp '/' exp                { Complejo c1   = (Complejo) $1.obj; Complejo c2 = (Complejo) $3.obj;
                                    Complejo res  = new Complejo(0,0);
                                    res.divideComplejos(c1, c2);
                                    $$            = new ParserVal(res);
                                  }
     | '(' exp ')'                {Complejo c = (Complejo) $2.obj; $$ = new ParserVal(c); }
    ;
%%

/** CÓDIGO DE SOPORTE **/

  boolean indef;
  static Tabla tabla;
  static Maquina maq;

  static String ins;
  static StringTokenizer st;
  static boolean newline;
  Functions f = new Functions();

  class Algo {
    Simbolo simb;
    int inst;

    public Algo(int i){
      inst=i;
    }
    public Algo(Simbolo s, int i){
      simb=s;
      inst=i;
    }
  }

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

  public Parser(Reader r) {
    lexer = new Yylex(r, this);
  }

  void yyerror(String s){
  System.out.println("parser:"+s);
  }

  public static void main(String args[]) throws IOException {
    System.out.println("Calculadora Números Complejos Cientifica");

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    Maquina maq = new Maquina();

    Tabla tabla = new Tabla();
    //tabla.install("sin",BLTIN,0,0);

    String[] funcNames = {"exp", "sin", "cos", "pow"};
    for (int i = 0; i < funcNames.length; i++) {
      tabla.install(funcNames[i], null, (short) 2);
    }

    Parser par = new Parser(false);
      try{
         ins = in.readLine();
      } catch (Exception e){}
      st = new StringTokenizer(ins);
      newline=false;
      for(maq.initcode(); par.yyparse ()!=0; maq.initcode()){
      maq.execute(maq.progbase);
      try{
         ins = in.readLine();
      } catch (Exception e){}
      st = new StringTokenizer(ins);  
      newline=false;
    }	

  }
