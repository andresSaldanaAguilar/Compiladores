%{
  import java.io.*;
  import java.util.ArrayList;
%}

%token VAR
%token CNUMBER

%right '='
%left '-' '+'
%left '*' '/'

%%
list:	/* nada */
			| list '\n'
      | list asgn '\n' {}
			| list exp '\n' {Complejo res = (Complejo) $2.obj; System.out.println("Real: " + res.getReal() + "Img: " + res.getImg());}
			;
asgn: VAR '=' exp   { Cadena c = (Cadena) $1.obj; 
                      Symbol s = lookUpTable(c.getCadena());
                      System.out.println("Is it in the table?: " + s);
                      if (s == null)
                        install(c.getCadena(), (Complejo) $3.obj);
                     }
      ;
exp:  CNUMBER      {Complejo c = (Complejo) $1.obj;}
     | VAR          { Cadena c = (Cadena) $1.obj;
                      Symbol s = lookUpTable(c.getCadena());
                      Complejo data = (Complejo) s.getData();
                      $$ = new ParserVal(data);
                    }
     | asgn
     | exp '+' exp {Complejo c1 = (Complejo) $1.obj; Complejo c2 = (Complejo) $3.obj;
                    Complejo res = sumaComplejos(c1, c2);
                    $$ = new ParserVal(res);
                   }
     | exp '-' exp {}
     | exp '*' exp {}
     | exp '/' exp {}
     | '(' exp ')' {Complejo c = (Complejo) $2.obj; $$ = new ParserVal(c); }
    ;
%%

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


  public Complejo sumaComplejos(Complejo c1, Complejo c2) {
    Complejo res = new Complejo(c1.getReal() + c2.getReal(),
                        c1.getImg() + c2.getImg());
    return res;
  }



/* Metodos de la tabla de símbolos */
ArrayList<Symbol> symbolTable = new ArrayList<>();

Symbol lookUpTable(String symbolName) {
  boolean flag = false;
  for (Symbol s: symbolTable) {
    if (s.getName().compareTo(symbolName) == 0) { // The symbol is already in the table !
      return s;
    }
  }

  return null;
}

void install(String name, Complejo data) {
  Symbol s = new Symbol(name, (short) 1, data);
  symbolTable.add(s);
}



  public static void main(String args[]) throws IOException {
    System.out.println("Calculadora Números Complejos");

    Parser yyparser;

    System.out.print("Expression: ");

	  yyparser = new Parser(new InputStreamReader(System.in));

    yyparser.yyparse();

  }
