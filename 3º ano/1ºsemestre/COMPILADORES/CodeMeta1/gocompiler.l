%{
    /*
        Author: Tomás Loureiro Nº2022234440
    */
    #include <stdio.h>
    #include <stdlib.h>
    #include <string.h>

    int line = 1;
    int column = 1;
    int line_col[2];
    char strlit[1024];
    int flag = 0;
    int flagSemicolon = 0;
    int detect = 0;

%}

digit   [0-9]
letter  [a-zA-Z_]
hex     [0-9a-fA-F]
strlit  (\\f|\\n|\\r|\\t|\\\"|\\\\|[^\n\r\\"])*
semicolon ";"
comma ","
blankid "_"
assign "="
star "*"
div "/"
minus "-"
plus "+"
eq "=="
ge ">="
gt ">"
lbrace "{"
le "<="
lpar "("
lsq "["
lt "<"
mod "%"
ne "!="
not "!"
and "&&"
or "||"
rbrace  "}"
rpar ")"
rsq "]"
package "package"
return "return"
else "else"
for "for"
if "if"
var "var"
int "int"
float32 "float32"
bool "bool"
string "string"
print "fmt.Println"
parseint "strconv.Atoi"
func "func"
cmdargs "os.Args"
reserved    "++"|"--"|"break"|"case"|"chan"|"const"|"continue"|"default"|"defer"|"fallthrough"|"go"|"goto"|"import"|"interface"|"map"|"range"|"select"|"struct"|"switch"|"type"
newline \n|(\r\n)|\r


%X STRLIT COMMENT1 COMMENT2

%%
" "                             { column+= yyleng;}
{newline}                       { if(flagSemicolon == 1 && flag == 1)printf("SEMICOLON\n"); line++; column = 1; flagSemicolon = 0; }
"\t"                            { column += 1; }
{semicolon}                     { if(flag == 1) printf("SEMICOLON\n"); column += yyleng; flagSemicolon = 0; }
{comma}                         { if(flag == 1) printf("COMMA\n"); column += yyleng; flagSemicolon = 0; }
{blankid}                       { if(flag == 1) printf("BLANKID\n"); column += yyleng; flagSemicolon = 0; }
{assign}                        { if(flag == 1) printf("ASSIGN\n"); column += yyleng; flagSemicolon =0; }
{star}                          { if(flag == 1) printf("STAR\n"); column += yyleng; flagSemicolon =0; }
{div}                           { if(flag == 1) printf("DIV\n"); column += yyleng; flagSemicolon =0; }
{minus}                         { if(flag == 1) printf("MINUS\n"); column += yyleng; flagSemicolon =0; }
{plus}                          { if(flag == 1) printf("PLUS\n"); column += yyleng; flagSemicolon =0; }
{eq}                            { if(flag == 1) printf("EQ\n"); column += yyleng; flagSemicolon =0; }
{ge}                            { if(flag == 1) printf("GE\n"); column += yyleng; flagSemicolon =0; }
{gt}                            { if(flag == 1) printf("GT\n"); column += yyleng; flagSemicolon =0; }
{lbrace}                        { if(flag == 1) printf("LBRACE\n"); column += yyleng; flagSemicolon =0; }
{le}                            { if(flag == 1) printf("LE\n"); column += yyleng; flagSemicolon =0; }
{lpar}                          { if(flag == 1) printf("LPAR\n"); column += yyleng; flagSemicolon =0; }
{lsq}                           { if(flag == 1) printf("LSQ\n"); column += yyleng; flagSemicolon =0; }
{lt}                            { if(flag == 1) printf("LT\n"); column += yyleng; flagSemicolon =0; }
{mod}                           { if(flag == 1) printf("MOD\n"); column += yyleng; flagSemicolon =0; }
{ne}                            { if(flag == 1) printf("NE\n"); column += yyleng; flagSemicolon =0; }
{not}                           { if(flag == 1) printf("NOT\n"); column += yyleng; flagSemicolon =0; }
{and}                           { if(flag == 1) printf("AND\n"); column += yyleng; flagSemicolon =0; }
{or}                            { if(flag == 1) printf("OR\n"); column += yyleng; flagSemicolon =0; }
{rbrace}                        { if(flag == 1) printf("RBRACE\n"); column += yyleng; flagSemicolon = 1; }
{rpar}                          { if(flag == 1) printf("RPAR\n"); column += yyleng; flagSemicolon = 1; }
{rsq}                           { if(flag == 1) printf("RSQ\n"); column += yyleng; flagSemicolon = 1; }
{package}                       { if(flag == 1) printf("PACKAGE\n"); column += yyleng; flagSemicolon =0; }
{return}                        { if(flag == 1) printf("RETURN\n"); column += yyleng; flagSemicolon = 1; }
{else}                          { if(flag == 1) printf("ELSE\n"); column += yyleng; flagSemicolon =0; }
{for}                           { if(flag == 1) printf("FOR\n"); column += yyleng; flagSemicolon =0; }
{if}                            { if(flag == 1) printf("IF\n"); column += yyleng; flagSemicolon =0; }
{var}                           { if(flag == 1) printf("VAR\n"); column += yyleng; flagSemicolon =0; }
{int}                           { if(flag == 1) printf("INT\n"); column += yyleng; flagSemicolon =0; }
{float32}                       { if(flag == 1) printf("FLOAT32\n"); column += yyleng; flagSemicolon =0; }
{bool}                          { if(flag == 1) printf("BOOL\n"); column += yyleng; flagSemicolon =0; }
{string}                        { if(flag == 1) printf("STRING\n"); column += yyleng; flagSemicolon =0; }
{print}                         { if(flag == 1) printf("PRINT\n"); column += yyleng; flagSemicolon =0; }
{parseint}                      { if(flag == 1) printf("PARSEINT\n"); column += yyleng; flagSemicolon =0; }
{func}                          { if(flag == 1) printf("FUNC\n"); column += yyleng; flagSemicolon =0; }
{cmdargs}                       { if(flag == 1) printf("CMDARGS\n"); column += yyleng; flagSemicolon =0; }
{reserved}                      { if(flag == 1) printf("RESERVED(%s)\n",yytext);column+=yyleng; flagSemicolon =0; }




{letter}({letter}*{digit}*)*    { if(flag == 1) printf("IDENTIFIER(%s)\n",yytext); column+=yyleng; flagSemicolon = 1; }


\"					            { BEGIN (STRLIT); detect = 0; line_col[0] = line; line_col[1] = column; strcat(strlit,yytext); column += yyleng; }
<STRLIT>{strlit}	            { column += yyleng; strcat(strlit,yytext);}
<STRLIT>\\(.?)		            { printf("Line %d, column %d: invalid escape sequence (%s)\n",line,column,yytext); column += yyleng; detect = 1; strcpy(strlit,"\0"); }
<STRLIT>{newline}               { line++; column = 1 ;printf("Line %d, column %d: unterminated string literal\n",line_col[0],line_col[1]); strcpy(strlit,"\0"); BEGIN (INITIAL); }
<STRLIT>\"			            { if(detect == 0 && flag == 1) {strcat(strlit,yytext);printf("STRLIT(%s)\n",strlit); flagSemicolon = 1;} column+=yyleng; strcpy(strlit,"\0"); BEGIN(INITIAL); }
<STRLIT><<EOF>>		            { printf("Line %d, column %d: unterminated string literal\n",line_col[0],line_col[1]);column += yyleng; strcpy(strlit,"\0"); BEGIN (INITIAL); }


"//"                            { BEGIN(COMMENT1); column += yyleng;}
<COMMENT1>{newline}              { BEGIN(INITIAL); column = 1; line++; if(flagSemicolon == 1 && flag == 1) printf("SEMICOLON\n"); flagSemicolon = 0; }
<COMMENT1>.                      { column += yyleng; }


"/*"				            { BEGIN(COMMENT2); line_col[0]=line; line_col[1]=column; column+=yyleng; }
<COMMENT2>"*/"		            { BEGIN (INITIAL); column+=yyleng; }
<COMMENT2>{newline}             { line++; column=1; }
<COMMENT2>.		                { column += yyleng; }
<COMMENT2><<EOF>>	            { printf("Line %d, column %d: unterminated comment\n",line_col[0],line_col[1]); column+=yyleng; BEGIN (INITIAL); flagSemicolon = 0; }


0[xX]{hex}+                     { if(flag == 1) printf("NATURAL(%s)\n", yytext); column += yyleng; flagSemicolon = 1; }
0[0-7]+                         { if(flag == 1) printf("NATURAL(%s)\n", yytext); column += yyleng; flagSemicolon = 1; }
0[0-9]+                         { printf("Line %d, column %d: invalid octal constant (%s)\n", line, column, yytext); column += yyleng; }
{digit}+                        { if(flag == 1) printf("NATURAL(%s)\n", yytext); column += yyleng; flagSemicolon = 1;}

({digit}+("."{digit}*)?([Ee][+-]?{digit}+)?|"."{digit}+([Ee][+-]?{digit}+)?|{digit}+([Ee][+-]?{digit}+)) { if(flag == 1) printf("DECIMAL(%s)\n", yytext); column += yyleng; flagSemicolon = 1; }


.                               {printf("Line %d, column %d: illegal character (%s)\n",line,column,yytext); column+=1; }
<<EOF>>                         {if(flagSemicolon == 1 && flag == 1) printf("SEMICOLON\n"); exit (0);}
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
        flag = 0;
    } 
    yylex();
return 0;
}

int yywrap()
{
return 1;
}