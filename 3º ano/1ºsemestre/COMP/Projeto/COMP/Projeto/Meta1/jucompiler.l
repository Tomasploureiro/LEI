/*
    Ana Rita Martins Oliveira 2020213684
    Hugo Sobral de Barros     2020234332
*/

%{
    #include <stdio.h>
    #include <stdlib.h>
    #include <string.h>

    int line = 1;
    int column = 1;
    int line_col[2];
    int flag = 0;
    int detect = 0;
    char strlit[1024];
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
bool        boolean
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

%x COMMENT MULTILINE STRLIT

%%

{boolit}                        {if(flag==1)printf("BOOLLIT(%s)\n",yytext);column+=yyleng;}
{and}                           {if(flag==1)printf("AND\n");column+=yyleng;}
{assign}                        {if(flag==1)printf("ASSIGN\n");column+=yyleng;}
{star}                          {if(flag==1)printf("STAR\n");column+=yyleng;}
{comma}                         {if(flag==1)printf("COMMA\n");column+=yyleng;}
{div}                           {if(flag==1)printf("DIV\n");column+=yyleng;}
{eq}                            {if(flag==1)printf("EQ\n");column+=yyleng;}
{ge}                            {if(flag==1)printf("GE\n");column+=yyleng;}
{gt}                            {if(flag==1)printf("GT\n");column+=yyleng;}
{lbrace}                        {if(flag==1)printf("LBRACE\n");column+=yyleng;}
{le}                            {if(flag==1)printf("LE\n");column+=yyleng;}
{lpar}                          {if(flag==1)printf("LPAR\n");column+=yyleng;}
{lsq}                           {if(flag==1)printf("LSQ\n");column+=yyleng;}
{lt}                            {if(flag==1)printf("LT\n");column+=yyleng;}
{minus}                         {if(flag==1)printf("MINUS\n");column+=yyleng;}
{mod}                           {if(flag==1)printf("MOD\n");column+=yyleng;}
{ne}                            {if(flag==1)printf("NE\n");column+=yyleng;}
{not}                           {if(flag==1)printf("NOT\n");column+=yyleng;}
{or}                            {if(flag==1)printf("OR\n");column+=yyleng;}
{plus}                          {if(flag==1)printf("PLUS\n");column+=yyleng;}
{rbrace}                        {if(flag==1)printf("RBRACE\n");column+=yyleng;}
{rpar}                          {if(flag==1)printf("RPAR\n");column+=yyleng;}
{rsq}                           {if(flag==1)printf("RSQ\n");column+=yyleng;}
{semicolon}                     {if(flag==1)printf("SEMICOLON\n");column+=yyleng;}
{arrow}                         {if(flag==1)printf("ARROW\n");column+=yyleng;}
{lshift}                        {if(flag==1)printf("LSHIFT\n");column+=yyleng;}
{rshift}                        {if(flag==1)printf("RSHIFT\n");column+=yyleng;}
{xor}                           {if(flag==1)printf("XOR\n");column+=yyleng;}
{bool}                          {if(flag==1)printf("BOOL\n");column+=yyleng;}
{class}                         {if(flag==1)printf("CLASS\n");column+=yyleng;}
{dotlength}                     {if(flag==1)printf("DOTLENGTH\n");column+=yyleng;}
{double}                        {if(flag==1)printf("DOUBLE\n");column+=yyleng;}
{else}                          {if(flag==1)printf("ELSE\n");column+=yyleng;}
{if}                            {if(flag==1)printf("IF\n");column+=yyleng;}
{int}                           {if(flag==1)printf("INT\n");column+=yyleng;}
{print}                         {if(flag==1)printf("PRINT\n");column+=yyleng;}
{parseint}                      {if(flag==1)printf("PARSEINT\n");column+=yyleng;}
{public}                        {if(flag==1)printf("PUBLIC\n");column+=yyleng;}
{return}                        {if(flag==1)printf("RETURN\n");column+=yyleng;}
{static}                        {if(flag==1)printf("STATIC\n");column+=yyleng;}
{string}                        {if(flag==1)printf("STRING\n");column+=yyleng;}
{void}                          {if(flag==1)printf("VOID\n");column+=yyleng;}
{while}                         {if(flag==1)printf("WHILE\n");column+=yyleng;}
{reserved}                      {if(flag==1)printf("RESERVED(%s)\n",yytext);column+=yyleng;}
{intlit}                        {if(flag==1)printf("INTLIT(%s)\n",yytext);column+=yyleng;}
{reallit}                       {if(flag==1)printf("REALLIT(%s)\n",yytext);column+=yyleng;}
{id}                            {if(flag==1)printf("ID(%s)\n",yytext);column+=yyleng;}


"//"                            {BEGIN COMMENT;column+=yyleng;}
<COMMENT>{newline}              {BEGIN 0;line++;column=1;}
<COMMENT>.                      {column+=yyleng;}

"/*"				            {BEGIN MULTILINE;line_col[0]=line;line_col[1]=column;column+=yyleng;}
<MULTILINE>"*/"		            {BEGIN 0;column+=yyleng;}
<MULTILINE>{newline}            {line++;column=1;}
<MULTILINE>.		            {column+=yyleng;}
<MULTILINE><<EOF>>	            {printf("Line %d, col %d: unterminated comment\n",line_col[0],line_col[1]);column+=yyleng;BEGIN 0;}

\"					            {BEGIN STRLIT;detect=0;line_col[0]=line;line_col[1]=column;strcat(strlit,yytext); column+=yyleng;}
<STRLIT>{strlit}	            {column+=yyleng;strcat(strlit,yytext);}
<STRLIT>\\(.?)		            {printf("Line %d, col %d: invalid escape sequence (%s)\n",line,column,yytext);column+=yyleng;detect=1;strcpy(strlit,"\0");}
<STRLIT>{newline}               {line++;column=1;printf("Line %d, col %d: unterminated string literal\n",line_col[0],line_col[1]);strcpy(strlit,"\0");BEGIN 0;}
<STRLIT>\"			            {if(flag==1){if(detect==0){strcat(strlit,yytext);printf("STRLIT(%s)\n",strlit);}}column+=yyleng;strcpy(strlit,"\0");BEGIN 0;}
<STRLIT><<EOF>>		            {column=1;printf("Line %d, col %d: unterminated string literal\n",line_col[0],line_col[1]);column+=yyleng;strcpy(strlit,"\0");BEGIN 0;return 0;}

{newline}                       {line++;column=1;}
{whitespace}                    {column+=yyleng;}
" "                             {column+=1;}

.                               {printf("Line %d, col %d: illegal character (%s)\n",line,column,yytext);column+=yyleng;}



%%

int main(int argc, char *argv[])
{
    if (argc > 1) {
        if (strcmp(argv[1], "-l") == 0) {
            flag = 1;
        }
        else if (strcmp(argv[1], "-e1") == 0) {
            flag = 0;
        }
    }
    else if (argc == 1) {
        flag = 1;
    } 
    yylex();
return 0;
}

int yywrap()
{
return 1;
}