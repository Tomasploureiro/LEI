%{
    /*
        Ana Rita Martins Oliveira 2020213684
        Hugo Sobral de Barros     2020234332
    */
    
    #include "jucompiler.h"
    #include "y.tab.h"

    int line = 1;
    int column = 1;
    int line_col[2];
    int flag_lex = 0;
    int flag_tree = 0;
    extern int flag_sint;
    extern node root;

    int detect = 0;
    char str[1024];


%}


boolit      true|false
id          ([a-z]|[A-Z]|"_"|"$")([a-z]|[A-Z]|[0-9]|"_"|"$")*
intlit      0|[1-9][0-9]*|([1-9]([0-9]|"_")*[0-9])
strlit      (\\f|\\n|\\r|\\t|\\\"|\\\\|[^\n\r\\"])*
real        [0-9]|([0-9]+("_"+[0-9]+)+)
exp         [eE][+-]?[0-9]+("_"+[0-9]+)*
reallit     ({real}+"."({real}+{exp}?|({real}|{exp})))|{real}+"."|"."({real}+{exp}?|{real})|({real}+{exp})
and         "&&"
assign      "="
star        "*"
comma       ","
div         "/"
eq          "=="
ge          ">="
gt          ">"
lbrace      "{"
le          "<="
lpar        "("
lsq         "["
lt          "<"
minus       "-"
mod         "%"
ne          "!="
not         "!"
or          "||"
plus        "+"
rbrace      "}"
rpar        ")"
rsq         "]"
semicolon   ";"
arrow       "->"
lshift      "<<"
rshift      ">>"
xor         "^"
bool        "boolean"
class       "class"
dotlength   ".length"
double      "double"
else        "else"
if          "if"
int         "int"
print       "System.out.print"
parseint    "Integer.parseInt"
public      "public"
return      "return"
static      "static"
string      "String"
void        "void"
while       "while"
reserved    "++"|"--"|"null"|"Integer"|"System"|"abstract"|"continue"|"for"|"new"|"switch"|"assert"|"default"|"package"|"synchronized"|"do"|"goto"|"private"|"this"|"break"|"implements"|"protected"|"throw"|"byte"|"import"|"throws"|"case"|"enum"|"instanceof"|"transient"|"catch"|"extends"|"short"|"try"|"char"|"final"|"interface"|"finally"|"long"|"strictfp"|"volatile"|"const"|"float"|"native"|"super"
whitespace  \t|\f
newline     \n|(\r\n)|\r

%x COMMENT MULTILINE STR

%%

{boolit}                        {if(flag_lex==1)printf("BOOLLIT(%s)\n",yytext);column+=yyleng;
                                if(flag_lex != 1) {
                                    yylval.string = strdup(yytext);
                                    return BOOLLIT;
                                }}                               
{and}                           {if(flag_lex==1)printf("AND\n");column+=yyleng;
                                if(flag_lex != 1)return AND;}
{assign}                        {if(flag_lex==1)printf("ASSIGN\n");column+=yyleng;
                                if(flag_lex != 1)return ASSIGN;}
{star}                          {if(flag_lex==1)printf("STAR\n");column+=yyleng;
                                if(flag_lex != 1)return STAR;}
{comma}                         {if(flag_lex==1)printf("COMMA\n");column+=yyleng;
                                if(flag_lex != 1)return COMMA;}
{div}                           {if(flag_lex==1)printf("DIV\n");column+=yyleng;
                                if(flag_lex != 1)return DIV;}
{eq}                            {if(flag_lex==1)printf("EQ\n");column+=yyleng;
                                if(flag_lex != 1)return EQ;}
{ge}                            {if(flag_lex==1)printf("GE\n");column+=yyleng;
                                if(flag_lex != 1)return GE;}
{gt}                            {if(flag_lex==1)printf("GT\n");column+=yyleng;
                                if(flag_lex != 1)return GT;}
{lbrace}                        {if(flag_lex==1)printf("LBRACE\n");column+=yyleng;
                                if(flag_lex != 1)return LBRACE;}
{le}                            {if(flag_lex==1)printf("LE\n");column+=yyleng;
                                if(flag_lex != 1)return LE;}
{lpar}                          {if(flag_lex==1)printf("LPAR\n");column+=yyleng;
                                if(flag_lex != 1)return LPAR;}
{lsq}                           {if(flag_lex==1)printf("LSQ\n");column+=yyleng;
                                if(flag_lex != 1)return LSQ;}
{lt}                            {if(flag_lex==1)printf("LT\n");column+=yyleng;
                                if(flag_lex != 1)return LT;}
{minus}                         {if(flag_lex==1)printf("MINUS\n");column+=yyleng;
                                if(flag_lex != 1)return MINUS;}
{mod}                           {if(flag_lex==1)printf("MOD\n");column+=yyleng;
                                if(flag_lex != 1)return MOD;}
{ne}                            {if(flag_lex==1)printf("NE\n");column+=yyleng;
                                if(flag_lex != 1)return NE;}
{not}                           {if(flag_lex==1)printf("NOT\n");column+=yyleng;
                                if(flag_lex != 1)return NOT;}
{or}                            {if(flag_lex==1)printf("OR\n");column+=yyleng;
                                if(flag_lex != 1)return OR;}
{plus}                          {if(flag_lex==1)printf("PLUS\n");column+=yyleng;
                                if(flag_lex != 1)return PLUS;}
{rbrace}                        {if(flag_lex==1)printf("RBRACE\n");column+=yyleng;
                                if(flag_lex != 1)return RBRACE;}
{rpar}                          {if(flag_lex==1)printf("RPAR\n");column+=yyleng;
                                if(flag_lex != 1)return RPAR;}
{rsq}                           {if(flag_lex==1)printf("RSQ\n");column+=yyleng;
                                if(flag_lex != 1)return RSQ;}
{semicolon}                     {if(flag_lex==1)printf("SEMICOLON\n");column+=yyleng;
                                if(flag_lex != 1)return SEMICOLON;}
{arrow}                         {if(flag_lex==1)printf("ARROW\n");column+=yyleng;
                                if(flag_lex != 1)return ARROW;}
{lshift}                        {if(flag_lex==1)printf("LSHIFT\n");column+=yyleng;
                                if(flag_lex != 1)return LSHIFT;}
{rshift}                        {if(flag_lex==1)printf("RSHIFT\n");column+=yyleng;
                                if(flag_lex != 1)return RSHIFT;}
{xor}                           {if(flag_lex==1)printf("XOR\n");column+=yyleng;
                                if(flag_lex != 1)return XOR;}
{bool}                          {if(flag_lex==1)printf("BOOL\n");column+=yyleng;
                                if(flag_lex != 1)return BOOL;}
{class}                         {if(flag_lex==1)printf("CLASS\n");column+=yyleng;
                                if(flag_lex != 1)return CLASS;}
{dotlength}                     {if(flag_lex==1)printf("DOTLENGTH\n");column+=yyleng;
                                if(flag_lex != 1)return DOTLENGTH;}
{double}                        {if(flag_lex==1)printf("DOUBLE\n");column+=yyleng;
                                if(flag_lex != 1)return DOUBLE;}
{else}                          {if(flag_lex==1)printf("ELSE\n");column+=yyleng;
                                if(flag_lex != 1)return ELSE;}
{if}                            {if(flag_lex==1)printf("IF\n");column+=yyleng;
                                if(flag_lex != 1)return IF;}
{int}                           {if(flag_lex==1)printf("INT\n");column+=yyleng;
                                if(flag_lex != 1)return INT;}
{print}                         {if(flag_lex==1)printf("PRINT\n");column+=yyleng;
                                if(flag_lex != 1)return PRINT;}
{parseint}                      {if(flag_lex==1)printf("PARSEINT\n");column+=yyleng;
                                if(flag_lex != 1)return PARSEINT;}
{public}                        {if(flag_lex==1)printf("PUBLIC\n");column+=yyleng;
                                if(flag_lex != 1)return PUBLIC;}
{return}                        {if(flag_lex==1)printf("RETURN\n");column+=yyleng;
                                if(flag_lex != 1)return RETURN;}
{static}                        {if(flag_lex==1)printf("STATIC\n");column+=yyleng;
                                if(flag_lex != 1)return STATIC;}
{string}                        {if(flag_lex==1)printf("STRING\n");column+=yyleng;
                                if(flag_lex != 1)return STRING;}
{void}                          {if(flag_lex==1)printf("VOID\n");column+=yyleng;
                                if(flag_lex != 1)return VOID;}
{while}                         {if(flag_lex==1)printf("WHILE\n");column+=yyleng;
                                if(flag_lex != 1)return WHILE;}
{reserved}                      {if(flag_lex==1)printf("RESERVED(%s)\n",yytext);column+=yyleng;
                                if(flag_lex != 1) {
                                    yylval.string = strdup(yytext);
                                    return RESERVED;
                                }}
{intlit}                        {if(flag_lex==1)printf("INTLIT(%s)\n",yytext);column+=yyleng;
                                if(flag_lex != 1) {
                                    yylval.string = strdup(yytext);
                                    return INTLIT;
                                }}
{reallit}                       {if(flag_lex==1)printf("REALLIT(%s)\n",yytext);column+=yyleng;
                                if(flag_lex != 1) {
                                    yylval.string = strdup(yytext);
                                    return REALLIT;
                                }}
{id}                            {if(flag_lex==1)printf("ID(%s)\n",yytext);column+=yyleng;
                                if(flag_lex != 1) {
                                    yylval.string = strdup(yytext);
                                    return ID;
                                }}


"//"                            {BEGIN COMMENT;column+=yyleng;}
<COMMENT>{newline}              {BEGIN 0;line++;column=1;}
<COMMENT>.                      {column+=yyleng;}

"/*"				            {BEGIN MULTILINE;line_col[0]=line;line_col[1]=column;column+=yyleng;}
<MULTILINE>"*/"		            {column+=yyleng;BEGIN 0;}
<MULTILINE>{newline}            {line++;column=1;}
<MULTILINE>.		            {column+=yyleng;}
<MULTILINE><<EOF>>	            {printf("Line %d, col %d: unterminated comment\n",line_col[0],line_col[1]);BEGIN 0;}

\"					            {BEGIN STR;detect=0;line_col[0]=line;line_col[1]=column;strcat(str,yytext); column+=yyleng;}
<STR>{strlit}	            {column+=yyleng;strcat(str,yytext);}
<STR>\\(.?)		            {printf("Line %d, col %d: invalid escape sequence (%s)\n",line,column,yytext);column+=yyleng;detect=1;strcpy(str,"\0");}
<STR>{newline}               {line++;column=1;printf("Line %d, col %d: unterminated string literal\n",line_col[0],line_col[1]);strcpy(str,"\0");BEGIN 0;}
<STR>\"			            {   BEGIN 0;
                                strcat(str,yytext);
                                if(flag_lex==1){
                                    if(detect==0){
                                        
                                        printf("STRLIT(%s)\n",str);
                                    }
                                }
                                column+=yyleng;                                
                                if(flag_lex!=1&&detect==0){
                                    yytext=strdup(str);
                                    yylval.string = (char*)strdup(str);
                                    strcpy(str,"\0");
                                    return STRLIT;
                                }
                            strcpy(str,"\0");}
<STR><<EOF>>		            {column=1;printf("Line %d, col %d: unterminated string literal\n",line_col[0],line_col[1]);column+=yyleng;strcpy(str,"\0");BEGIN 0;return 0;}

{newline}                       {line++;column=1;}
{whitespace}                    {column+=yyleng;}
" "                             {column+=1;}

.                               {printf("Line %d, col %d: illegal character (%s)\n",line,column,yytext);column+=yyleng;}


%%

void yyerror ( char *s) {
    printf ("Line %d, col %d: %s: %s\n", line, (int)(column-strlen(yytext)), s, yytext );
}

int yywrap(){
    return 1;
}


int main(int argc, char *argv[])
{
    if (argc > 1) {
        if (strcmp(argv[1], "-l") == 0) {
            flag_lex = 1;
            yylex();
        }
        else if (strcmp(argv[1], "-e1") == 0) {
            flag_lex = 0;
            yylex();
        }
        else if (strcmp(argv[1],"-t") == 0) {
			flag_lex = 2;
            flag_tree = 1;
            yyparse();
            if(flag_sint != 1)
                print_tree(root, 0);
		}
        else if (strcmp(argv[1], "-e2") == 0) {
            flag_lex = 0;
            flag_sint = 1;
            yyparse();
            yylex();
        }
    }
    else if (argc == 1) {
        flag_lex = 0;
        flag_sint = 1;
        yyparse();
        yylex();
    } 
    return 0;
}
