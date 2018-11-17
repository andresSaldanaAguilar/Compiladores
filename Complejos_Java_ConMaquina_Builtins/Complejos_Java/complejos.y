%{
  import java.io.*;
%}

%token VAR
%token CNUMBER
%token BLTIN
%token DIG

%right '='
%left '-' '+'
%left '*' '/'
%left '^'

%%
list:	/* nada */
			| list '\n'
      | list asgn '\n'{ maq.code("STOP"); 
                        maq.execute(flag); 
                        flag = true;
                      }
			| list exp '\n' { maq.code("imprimeComplejo");
                        maq.code("STOP");
                        maq.execute(flag); 
                        flag = true;
                      }
      | list stmt '\n'{ maq.code("STOP");
                        maq.execute(flag); 
                        flag = true;
                      }
			;
asgn: VAR '=' exp   { Cadena c = (Cadena)$1.obj;
                      maq.code("varpush");
                      maq.code(c);
                      maq.code("setvar");
                    }
      ;
exp: CNUMBER  { Complejo c = (Complejo)$1.obj;
                maq.code("cnumber"); //guardamos cadena en RAM
                maq.code(c); //guardamos variable en RAM
              }
   | VAR      { Cadena c = (Cadena)$1.obj;
                maq.code("varpush");
                maq.code(c);
                maq.code("getvar"); //conseguimos el valor de la variable para imprimirlo
              }  
   | asgn
   | BLTIN '(' exp ')'{ Cadena c = (Cadena)$1.obj; 
                        maq.code("stringpush");
                        maq.code(c);
                        maq.code("bltin");
                      }
   | exp '+' exp {maq.code("add");}
   | exp '-' exp {maq.code("sub");}
   | exp '*' exp {maq.code("mul");}
   | exp '/' exp {maq.code("div");}
   | exp '^' exp {maq.code("pow");}
   | '(' exp ')'  { $$= $2;}
   ;
%%

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

    maq.initcode();

    yyparser.yyparse();

  }
