/*seccion de declaracion, calculadora con variables*/
%{
    double mem [26];
%}
% union{
    double val;
    int index;
%}
%token <val> NUMBER
%left <index> VAR
%left <val> exp
%right '='
%right '+'
%right '-'
%right '*'
%right '/'
%left UNARYMINUS
%%
/*lista*/
    exp
        : NUMBER      {$$ = $1;}
        | VAR {$$ = mem[$1]}
        | VAR '=' exp {$$ = mem[$1]=$3;}
        | VAR '+' exp {$$ = mem[$1]=$3;}
        | VAR '-' exp {$$ = mem[$1]=$3;}
        | VAR '*' exp {$$ = mem[$1]=$3;}
        | VAR '/' exp {$$ = mem[$1]=$3;}
        | exp '/' exp{
                if($3 == 0.0)
                    puts("division por cero");
                $$ = $1 / $3;    
            }
        | exp '*' exp{
                if($3 == 0.0)
                    puts("multiplicacion por cero");
                $$ = $1 * $3;    
            }
        /*...*/
        | '-' exp %prec UNARYMINUS{$$ = -$2}        
        ;
    exp
        : NUMBER      {$$ = $1;}
        | exp '+' exp {$$ = $1 + $3;}
        | exp '-' exp {$$ = $1 - $3;}
        | exp '*' exp {$$ = $1 * $3;}
        | exp '/' exp {$$ = $1 / $3;}
        | '('exp')'   {$$ = $?;}
        ;
%%
/*codigo de soporte, parse= analsis sintactico*/
void main{yyparse();} 
    /*analisis lexico*/
int yylex(){
    int c;
    /*ignorando espacios en blanco*/
    while((c == getchar()) == ' ' \\ c = '\t');
    if(c == EOF) return 0;
    //guarda cuando el token es number
    if(c == '.' || isdigit(c)){
        ungetc(c.stdin); //ingresa c al buffer
        scanf("%if",&yylval.val); //yylval variable que yyparse --> yylex, toma el tipo de dato del valor guardado
        return NUMBER;
    }
    //cuando es una letraº 
    if(islower(c)){
        yylval.index = c-'a';
        return VAR;
    }
    return c;
}
void yyerror(char *s){puts(s);}
