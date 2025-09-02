%{
/*
	Ana Rita Martins Oliveira 2020213684
	Hugo Sobral de Barros     2020234332
*/

	#include "jucompiler.h"
	extern int flag_lex;
	int flag_sint = 0;
	
	node root;
	node aux;
	node aux1;
	node aux2;

	int yylex(void);
	void yyerror(const char * s);
%}

%union {
	char* string;
	struct no* node;
}

%token AND ASSIGN STAR COMMA DIV EQ GE GT LBRACE LE LPAR LSQ LT MINUS MOD NE NOT OR PLUS RBRACE RPAR RSQ SEMICOLON ARROW LSHIFT RSHIFT XOR BOOL CLASS DOTLENGTH DOUBLE ELSE IF INT PRINT PARSEINT PUBLIC RETURN STATIC STRING VOID WHILE RESERVED

%token <string> ID
%token <string> INTLIT
%token <string> REALLIT
%token <string> BOOLLIT
%token <string> STRLIT

%type <node> Program Program1 MethodDecl FieldDecl FieldDecl1 Type MethodHeader FormalParams FormalParams1 MethodBody MethodBody1 VarDecl Statement Statement1 Statement2 Statement3 Statement4 MethodInvocation MethodInvocation1 MethodInvocation2 Assignment ParseArgs Expr Expr1

/*Precedences here ------------*/

%right ASSIGN
%left OR
%left AND
%left XOR
%left NE EQ
%left LE LT GE GT
%left RSHIFT LSHIFT
%left MINUS PLUS
%left MOD DIV STAR
%right NOT UNARY
%left LPAR RPAR LSQ RSQ
%nonassoc ELSE IF

/*Rules here ------------*/

%%

Program:            CLASS ID LBRACE Program1 RBRACE								{root = create_node("Program", "");
																				aux = create_node("Id", $2);
																				add_node(root, aux);
																				add_brother(aux, $4);
																				$$ = root;}
				|	CLASS ID LBRACE Program1 RBRACE error						{$$ = NULL; flag_sint = 1;}
				;

Program1:																		{$$ = NULL;}
				|   MethodDecl Program1                           		        {$$ = $1; add_brother($$, $2);}
				|   FieldDecl Program1                                  		{$$ = $1; add_brother($$, $2);}
				|   SEMICOLON Program1                                 		    {$$ = $2;}
				;

MethodDecl:         PUBLIC STATIC MethodHeader MethodBody             		    {$$ = create_node("MethodDecl", "");
																				add_node($$, $3);
																				add_brother($3, $4);}
																				
				;

FieldDecl:          PUBLIC STATIC Type ID FieldDecl1 SEMICOLON      		    {$$ = create_node("FieldDecl", "");
																				add_node($$, $3);
																				add_brother($3, create_node("Id", $4));
																				if($5 != NULL){
																					aux = $5;
																					while(aux != NULL) {
																						node aux1 = create_node("FieldDecl", "");
																						node aux2 = create_node($3->type, $3->info);
																						add_node(aux1, aux2);
																						add_brother(aux2, create_node("Id", aux->info));
																						add_brother($$, aux1);
																						aux = aux->brother;
																					}
																					free(aux);
																				}}
				|	error SEMICOLON												{$$ = NULL; flag_sint = 1;}
				;

FieldDecl1:                                                           		    {$$ = NULL;}
				|   COMMA ID FieldDecl1                                 		{$$ = create_node("Id", $2);
																				add_brother($$, $3);}
				;

Type:               BOOL                                                		{$$ = create_node("Bool", "");}
				|   INT                                                 		{$$ = create_node("Int", "");}
				|   DOUBLE                                              		{$$ = create_node("Double", "");}
				;

MethodHeader    :   Type ID LPAR FormalParams RPAR      						{$$ = create_node("MethodHeader", "");
																				add_node($$, $1);
																				add_brother($1, create_node("Id", $2));
																				aux = create_node("MethodParams", "");
																				add_brother($1, aux);
																				add_node(aux, $4);}
				|   VOID ID LPAR FormalParams RPAR      						{$$ = create_node("MethodHeader", "");
																				aux = create_node("Void", "");
																				add_node($$, aux);
																				add_brother(aux, create_node("Id", $2));
																				aux1 = create_node("MethodParams", "");
																				add_brother(aux, aux1);
																				add_node(aux1, $4);}
				|   Type ID LPAR RPAR                   						{$$ = create_node("MethodHeader", "");
																				add_node($$, $1);
																				add_brother($1, create_node("Id", $2));
																				aux1 = create_node("MethodParams", "");
																				add_brother($1, aux1);}
				|   VOID ID LPAR RPAR                							{$$ = create_node("MethodHeader", "");
																				aux = create_node("Void", "");
																				add_node($$, aux);
																				add_brother(aux, create_node("Id", $2));
																				aux1 = create_node("MethodParams", "");
																				add_brother(aux, aux1);}
				;          
       

FormalParams    :   Type ID FormalParams1                               		{$$ = create_node("ParamDecl", "");
																				add_node($$, $1);
																				aux = create_node("Id", $2);
																				add_brother($1, aux);
																				add_brother($$, $3);}
				|   STRING LSQ RSQ ID                                   		{$$ = create_node("ParamDecl", "");
																				aux = create_node("StringArray", "");
																				add_node($$, aux);
																				add_brother(aux, create_node("Id", $4));}
				;          

FormalParams1:                                           	            		{$$ = NULL;}
				|   COMMA Type ID FormalParams1                         		{$$ = create_node("ParamDecl", "");
																				aux = create_node("Id", $3);
																				add_node($$, $2);
																				add_brother($2, aux);
																				add_brother($$, $4);}

MethodBody:         LBRACE MethodBody1 RBRACE                           		{$$ = create_node("MethodBody", "");
																				add_node($$, $2);}
				;          

MethodBody1:                                                            		{$$ = NULL;}
				|   Statement MethodBody1                               		{if($1 != NULL){
																					$$ = $1;
																					add_brother($$, $2);
																				} else 
																					$$ = $2;}
				|   VarDecl MethodBody1                                 		{$$ = $1; add_brother($$, $2);}
				;          		

VarDecl:            Type ID FieldDecl1 SEMICOLON                        		{$$ = create_node("VarDecl", "");
																				add_node($$, $1);
																				add_brother($1, create_node("Id", $2));
																				if($3 != NULL){
																					aux = $3;
																					while(aux != NULL){
																						node aux1 = create_node("VarDecl", "");
																						node aux2 = create_node($1->type, $1->info);
																						add_node(aux1, aux2);
																						add_brother(aux2, create_node("Id", aux->info));
																						add_brother($$, aux1);
																						aux = aux->brother;
																					}
																					free(aux);
																				}}
				;          		

Statement:          LBRACE Statement1 RBRACE                  		    		{if(count_brothers($2) > 1) {
																				aux = create_node("Block", "");
																				$$ = aux;
																				add_node(aux, $2);
																				} else 
																					$$ = $2;
																				}
				|   IF LPAR Expr1 RPAR Statement ELSE Statement 				{$$ = create_node("If", "");
																				add_node($$, $3);
																				aux = create_node("Block", "");
																				if(count_brothers($5)==1 && $5!=NULL) {
																					add_brother($3, $5);
																					if (count_brothers($7)==1 && $7!=NULL)
																						add_brother($5, $7);
																					else {
																						add_brother($5, aux);
																						add_node(aux, $7);
																					}
																				}else{
																					add_brother($3, aux);
																					add_node(aux, $5);
																					if(count_brothers($7)==1 && $7!=NULL)
																						add_brother(aux, $7);
																					else {
																						aux1 = create_node("Block", "");
																						add_brother(aux, aux1);
																						add_node(aux1, $7);
																					}
																				}}
				|   IF LPAR Expr1 RPAR Statement 								{$$ = create_node("If", "");
																				add_node($$, $3);
																				aux = create_node("Block", "");
																				if(count_brothers($5)==1 && $5!=NULL){
																					add_brother($3, $5);
																					add_brother($5, aux);
																				} else{
																					add_brother($3, aux);
																					add_node(aux, $5);
																					add_brother(aux, create_node("Block", ""));
																				}}
				|   WHILE LPAR Expr1 RPAR Statement       						{$$ = create_node("While", ""); 
																				add_node($$, $3);
																				if(count_brothers($5) == 1 && $5 != NULL) { 
																					add_brother($3, $5);
																				}
																				else{
																					aux = create_node("Block", "");
																					add_brother($3, aux);
																					add_node(aux, $5);
																				}}
				|   RETURN Statement4 SEMICOLON       				    		{$$ = create_node("Return", "");
																				add_node($$, $2);}
				|   Statement2 SEMICOLON               							{$$ = $1;}
				|   PRINT LPAR Statement3 RPAR SEMICOLON   			 			{$$ = create_node("Print", ""); add_node($$, $3);}
				|	error SEMICOLON												{$$ = NULL; flag_sint = 1;}
				;

Statement1:                                                 					{$$ = NULL;}
				|   Statement Statement1                    					{if($1 != NULL) {
																					$$ = $1;
																					add_brother($$, $2);
																				}
																				else {
																					$$ = $2;
																				}}
				;

Statement2:                               										{$$ = NULL;}
				|   MethodInvocation      										{$$ = $1;}
				|   Assignment     		  										{$$ = $1;}
				|   ParseArgs      		  										{$$ = $1;}
				;

Statement3:         Expr1        												{$$ = $1;}
				|   STRLIT       												{$$ = create_node("StrLit", $1);}
				;

Statement4:																		{$$ = NULL;}
				|	Expr1														{$$ = $1;}
				;

MethodInvocation:   ID LPAR MethodInvocation1 RPAR  							{$$ = create_node("Call", "");
																				aux = create_node("Id", $1);
																				add_node($$, aux);
																				add_brother(aux, $3);}
				|	ID LPAR error RPAR											{$$ = NULL; 
																				flag_sint = 1;}
				;							

MethodInvocation1:                                  							{$$ = NULL;}
				|   Expr1 MethodInvocation2         							{$$ = $1;
																				add_brother($$, $2);}
				;


MethodInvocation2:                                   							{$$ = NULL;}
				|   COMMA Expr1 MethodInvocation2    							{if($2 != NULL) {
																					$$=$2;
																					add_brother($$,$3);
																				}
																				else {
																					$$=$2;
																				}}
				;							


Assignment:         ID ASSIGN Expr1                  							{$$ = create_node("Assign", "");
																				aux = create_node("Id", $1);
																				add_node($$, aux);
																				add_brother(aux, $3);}
				;


ParseArgs:          PARSEINT LPAR ID LSQ Expr1 RSQ RPAR  						{$$ = create_node("ParseArgs", "");
																				aux = create_node("Id", $3);
																				add_node($$, aux);
																				add_brother(aux, $5);}
				|	PARSEINT LPAR error RPAR			 						{$$ = NULL;
																				flag_sint = 1;}
				;       						
						
Expr:               Expr PLUS Expr                        						{$$ = create_node("Add", "");
																				add_node($$, $1);
																				add_brother($1, $3);}
				|   Expr MINUS Expr                      						{$$ = create_node("Sub", "");
																				add_node($$, $1);
																				add_brother($1, $3);}
				|   Expr STAR Expr                       						{$$ = create_node("Mul", "");
																				add_node($$, $1);
																				add_brother($1, $3);}
				|   Expr DIV Expr                        						{$$ = create_node("Div", "");
																				add_node($$, $1);
																				add_brother($1, $3);} 
				|   Expr MOD Expr                        						{$$ = create_node("Mod", "");
																				add_node($$, $1);
																				add_brother($1, $3);}
				|   Expr AND Expr                        						{$$ = create_node("And", "");
																				add_node($$, $1);
																				add_brother($1, $3);}
				|   Expr OR Expr                         						{$$ = create_node("Or", "");
																				add_node($$, $1);
																				add_brother($1, $3);}
				|   Expr XOR Expr                        						{$$ = create_node("Xor", "");
																				add_node($$, $1);
																				add_brother($1, $3);}
				|   Expr LSHIFT Expr                     						{$$ = create_node("Lshift", "");
																				add_node($$, $1);
																				add_brother($1, $3);} 
				|   Expr RSHIFT Expr                     						{$$ = create_node("Rshift", "");
																				add_node($$, $1);
																				add_brother($1, $3);}
				|   Expr EQ Expr                         						{$$ = create_node("Eq", "");
																				add_node($$, $1);
																				add_brother($1, $3);}
				|   Expr GE Expr                         						{$$ = create_node("Ge", "");
																				add_node($$, $1);
																				add_brother($1, $3);}
				|   Expr GT Expr                         						{$$ = create_node("Gt", "");
																				add_node($$, $1);
																				add_brother($1, $3);} 
				|   Expr LE Expr                         						{$$ = create_node("Le", "");
																				add_node($$, $1);
																				add_brother($1, $3);}
				|   Expr LT Expr                         						{$$ = create_node("Lt", "");
																				add_node($$, $1);
																				add_brother($1, $3);}
				|   Expr NE Expr                         						{$$ = create_node("Ne", "");
																				add_node($$, $1);
																				add_brother($1, $3);}
				|   MINUS Expr      %prec UNARY          						{$$ = create_node("Minus", "");
																				add_node($$, $2);}
				|   NOT Expr        %prec UNARY          						{$$ = create_node("Not", "");
																				add_node($$, $2);}
				|   PLUS Expr       %prec UNARY          						{$$ = create_node("Plus", "");
																				add_node($$, $2);}
				|   LPAR Expr1 RPAR                       						{$$ = $2;}
				|   MethodInvocation				     						{$$ = $1;}
				|	ParseArgs						     						{$$ = $1;}
				|   ID		                             						{$$ = create_node("Id", $1);}
				|	ID DOTLENGTH						 						{$$ = create_node("Length", "");
																				add_node($$, create_node("Id", $1));}
				|   INTLIT                               						{$$ = create_node("DecLit", $1);}
				|   REALLIT                              						{$$ = create_node("RealLit", $1);}   
				|   BOOLLIT                              						{$$ = create_node("BoolLit", $1);}
				|	LPAR error RPAR						 						{$$ = NULL; flag_sint = 1;}
				;					
						
Expr1:      	    Assignment                               					{$$ = $1;}
				|   Expr                                     					{$$ = $1;}
				;  
%%