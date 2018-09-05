/*seccion de declaracion*/
%{
    #include <stdio.h>
    #define YYSTYPE double
%}
%token NUMBER
%left '+' '-'
%left '*' '/'
/*seccion reglas*/
%%
    list
        :/*nada*/
        | list '\n'
        | list exp {printf("\t%.8g\n",$2)}
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
    if(c == '.' || isdigit(c)){
        ungetc(c.stdin); //ingresa c al buffer
        scanf("%if",&yylval); //yylval variable que yyparse --> yylex, toma el tipo de dato del valor guardado
        return NUMBER;
    }
    return c;
}
void yyerror(char *s){puts(s);}
