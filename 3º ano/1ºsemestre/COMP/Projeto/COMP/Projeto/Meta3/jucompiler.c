#include "jucompiler.h"

node create_node(char* type, char* info) {
	if ((type == NULL) || (info == NULL))
		return NULL;

	node new_node = malloc(sizeof(no));

	new_node->type = (char *)malloc((strlen(type) * sizeof(char)) + 1);
	new_node->type = type;

	new_node->info = (char *)malloc((strlen(info) * sizeof(char)) + 1);
	new_node->info = info;

	new_node->brother = NULL;
	new_node->child = NULL;
	new_node->parent = NULL;
	new_node->n_children = 0;

    new_node->anot = NULL;
    new_node->print = 1;
    new_node->call = 0;

	return new_node;
}

void add_node(node parent, node to_be_added) {
	if (to_be_added == NULL)
		return;

	parent->child = to_be_added;
	to_be_added->parent = parent;
}

void add_brother(node n, node brother) {
	if ((n == NULL) || (brother == NULL))
		return;

	node aux = n;

	while (aux->brother != NULL)
		aux = aux->brother;

	aux->brother = brother;

	if (n->parent != NULL){
		brother->parent = n->parent;
		n->parent->n_children++;
	}
}

int count_sons(node root) {
	int count = 0;
	node aux = root;

	while (aux != NULL){
		aux = aux->child;
		count++;
	}

	return count;
}

int count_brothers(node root) {
	int count = 0;
	node aux = root;

	while (aux != NULL){
		aux = aux->brother;
		count++;
	}

	return count;
}

void print_tree(node root, int size) {
	if (root == NULL)
		return;

	int i = 0;
	node aux;

	if (strcmp(root->type, "Program") == 0)
		printf("%s\n", root->type);
	else
	{
		while (i < size){
			printf("..");
			i++;
		}
		if (strcmp(root->info, "") != 0)
			printf("%s(%s)\n", root->type, root->info);
		else
			printf("%s\n", root->type);
	}

	aux = root->child;

	while (aux != NULL){
		node free_n = aux;
		print_tree(aux, size + 1);
		aux = aux->brother;

		free(free_n);
	}
}

char* type(char* exp) {
    char* str = NULL;

    if (strcmp(exp, "StringArray") == 0) {
        str = (char *)malloc((strlen("String[]") * sizeof(char)) + 1);
        strcpy(str, "String[]");
    } else if (strcmp(exp, "Bool") == 0) {
        str = (char *)malloc((strlen("boolean") * sizeof(char)) + 1);
        strcpy(str, "boolean");
    } else if (strcmp(exp, "ParseArgs") == 0 || strcmp(exp, "Int") == 0) {
        str = (char *)malloc((strlen("int") * sizeof(char)) + 1);
        strcpy(str, "int");
    } else if (strcmp(exp, "Double") == 0) {
        str = (char *)malloc((strlen("double") * sizeof(char)) + 1);
        strcpy(str, "double");
    } else if (strcmp(exp, "Void") == 0) {
        str = (char *)malloc((strlen("void") * sizeof(char)) + 1);
        strcpy(str, "void");
    }

    return str;
}

tab init_table(char* name){
    if(name == NULL)
        strcpy(name,"");
    
    tab new_table = malloc(sizeof(table));
    new_table->name = (char*)malloc((strlen(name)*sizeof(char))+1);
    strcpy(new_table->name, name);
    new_table->symbols=NULL;
    return new_table;
}

node_tab init_method(){
    node_tab method = malloc(sizeof(no_tab));
    method->vars=NULL;
    method->method=NULL;
    method->brother = NULL;
    method->is_method=1;
    method->is_params=1;
    method->name=NULL;
    method->type=NULL;

    return method;
}

tab add_symb_tab(tab dad, node_tab brother){
    if(brother == NULL || dad == NULL)
        return NULL;

    node_tab aux = dad->symbols;
    if(aux!=NULL){
        while(aux->brother!=NULL){
            aux=aux->brother;
        }
        aux->brother=brother;
    } else{
        dad->symbols=brother;
    }

    return dad;
}

node_tab add_symb_node_tab(node_tab method, node_tab var){
    if(method == NULL || var == NULL)
        return NULL;
    
    node_tab aux = malloc(sizeof(node_tab));
    aux=method->vars;
    var->method = method;
    if(aux!=NULL){
        while(aux->brother!=NULL)
            aux=aux->brother;

		aux->brother = var;
    } else
        method->vars=var;

    return method;
}

tab create_table(node root) {
    node no = malloc(sizeof(no));
    no=root->child;
    tab new_tab = init_table(no->info);
    node_tab method, method_aux;
    no=no->brother;
    node aux = no;
    while(aux!=NULL){
        node aux2  = aux, aux3 = aux;
        if(strcmp("FieldDecl", aux->type)==0){
            method = init_method();
            aux2=aux->child;
            method->type=strdup(type(aux2->type));
            aux2=aux2->brother;
            method->name=strdup(aux2->info);
            method->is_method=0; // nao cria tabela
            add_symb_tab(new_tab, method);
        }
        else if(strcmp("MethodDecl", aux->type)==0){
            aux2=aux->child; // MethodHeader
            method = init_method();
            while (aux2 != NULL){
                if(strcmp(aux2->type, "MethodHeader")==0) {
                    method->type=strdup(type(aux2->child->type));
                    method->name=strdup(aux2->child->brother->info);
                    aux3=aux2->child->brother->brother->child;
                    while(aux3!=NULL){
                        method_aux=init_method();
                        method_aux->type=strdup(type(aux3->child->type));
                        method_aux->name=strdup(aux3->child->brother->info);
                        method_aux->is_method=0;
                        method_aux->is_params=1;
                        method=add_symb_node_tab(method, method_aux);
                        aux3=aux3->brother;
                    }
                } else if (strcmp(aux2->type, "MethodBody") == 0){
                    aux3 = aux2->child;
                    while (aux3 != NULL){
                        if(strcmp(aux3->type, "VarDecl")==0) {
                            method_aux=init_method();
                            method_aux->type=strdup(type(aux3->child->type));
                            method_aux->name=strdup(aux3->child->brother->info);
                            method_aux->is_method=0;
                            method_aux->is_params=0;
                            method = add_symb_node_tab(method, method_aux);
                        }
                        aux3=aux3->brother;
                    }
                }
                aux2=aux2->brother;
            }
            new_tab=add_symb_tab(new_tab, method);
        }
        aux=aux->brother;
    }
    return new_tab;
}

void print_table(tab no, int size){
     if(no == NULL)
        return;
        
    tab aux = no;
    node_tab symbol_aux, param_aux;
    node aux_node, aux_node2, aux_node3;
    
	printf("===== Class %s Symbol Table =====\n", aux->name);
	symbol_aux=aux->symbols;
	while(symbol_aux != NULL){
		printf("%s\t",symbol_aux->name);
		if(symbol_aux->is_method){
			printf("(");
			param_aux = symbol_aux->vars;
			while(param_aux != NULL){
				if (param_aux->brother == NULL || param_aux->brother->is_params == 0){
					if(param_aux->is_params) 
                        printf("%s",param_aux->type);
				} else {
					if(param_aux->is_params) 
                        printf("%s,",param_aux->type);
				}
				param_aux = param_aux->brother;    
			}      
			printf(")");
		}
		printf("\t%s\n",symbol_aux->type);
		symbol_aux=symbol_aux->brother;
	}
	printf("\n");

	symbol_aux = aux->symbols;
	while(symbol_aux != NULL){
		if(symbol_aux->is_method){
            param_aux = symbol_aux->vars;
            printf("===== Method %s(", symbol_aux->name);
            while(param_aux != NULL){
                if(param_aux->is_params) {
                    if(param_aux->brother == NULL || param_aux->brother->is_params == 0)
                        printf("%s",param_aux->type);
                    else
                        printf("%s,",param_aux->type);
                }
                param_aux = param_aux->brother;
            }
            printf(") Symbol Table =====\n");
            printf("return\t\t%s\n", symbol_aux->type);
            param_aux = symbol_aux->vars;
            while(param_aux != NULL){
                if (param_aux->is_params == 1)
                    printf("%s\t\t%s\tparam\n",param_aux->name,param_aux->type);
                else
                    printf("%s\t\t%s\n",param_aux->name,param_aux->type);
                param_aux = param_aux->brother;
            }  
		    printf("\n");
		}
		symbol_aux = symbol_aux->brother;
	}
}

void add_to_list(list *l, char* type, char* name){
    no_list *node, *new_node = (no_list*)malloc(sizeof(no_list));

	new_node->type = (char *)malloc((strlen(type) * sizeof(char)) + 1);
	new_node->type = type;

	new_node->name = (char *)malloc((strlen(name) * sizeof(char)) + 1);
	new_node->name = name;
    
    new_node->next = NULL;
    if(l->root == NULL){
        l->root = new_node;
    }
    else{
        node = l->root;
        while(node->next != NULL){
            node = node -> next;
        }
        node->next = new_node;
    }
}

char* search_in_list(list* l, char* name){
    no_list *inicio = l->root;
    while(inicio != NULL){
        if(strcmp(name, inicio->name) == 0) {
            return inicio->type;
        }
        else if (strcmp(name, "args") == 0) {
            return "String[]";
        }
        inicio = inicio->next;
    }
    return NULL;
}

void substitute_in_list(list *l, char* type, char* name) {
    no_list *start = l->root;
    while(start != NULL){
        if(strcmp(name, start->name) == 0) {
            start->type = type;
        }
        start = start->next;
    }
}

void search_in_tree(node root, list *l) {    
    if (root == NULL)
        return;

	node aux;

	if (strcmp(root->type, "MethodHeader") == 0) {
        add_to_list(l, type(root->child->type), root->child->brother->info);
    }

	aux = root->child;

	while (aux != NULL){
		search_in_tree(aux, l);
		aux = aux->brother;	}
}

int search(char* type) {
    if (strcmp(type, "DecLit")==0 || strcmp(type, "ParseArgs")==0 || strcmp(type, "Length")==0)
        return 1;
    else if (strcmp(type, "RealLit")==0)
        return 1;
    else if (strcmp(type, "StrLit")==0)
        return 1;
    else if (strcmp(type, "Eq")==0 || strcmp(type, "Ne")==0 || strcmp(type, "Gt")==0 || strcmp(type, "Ge")==0 || strcmp(type, "Lt")==0 || strcmp(type, "Le")==0 || strcmp(type, "And")==0 || strcmp(type, "Or")==0 || strcmp(type, "Not")==0 || strcmp(type, "BoolLit")==0)
        return 1;
    else 
        return 0;
}

char* search_type(char* type) {
    if (strcmp(type, "DecLit")==0 || strcmp(type, "ParseArgs")==0 || strcmp(type, "Length")==0)
        return "int";
    else if (strcmp(type, "RealLit")==0)
        return "double";
    else if (strcmp(type, "StrLit")==0)
        return "string";
    else if (strcmp(type, "Eq")==0 || strcmp(type, "Ne")==0 || strcmp(type, "Gt")==0 || strcmp(type, "Ge")==0 || strcmp(type, "Lt")==0 || strcmp(type, "Le")==0 || strcmp(type, "And")==0 || strcmp(type, "Or")==0 || strcmp(type, "Not")==0 || strcmp(type, "BoolLit")==0)
        return "boolean";
}

void verify(node root, list *l) {
    char* first, *second;
    if (strcmp(root->child->type, "Id") == 0 && strcmp(root->child->brother->type, "Id") == 0) {            
        if (search_in_list(l, root->child->info) != NULL) {
            first = (char *)malloc((strlen(search_in_list(l, root->child->info)) * sizeof(char)) + 1);
            strcpy(first,search_in_list(l, root->child->info));

            if (search_in_list(l, root->child->brother->info) != NULL) {
            second = (char *)malloc((strlen(search_in_list(l, root->child->brother->info)) * sizeof(char)) + 1);
            strcpy(second,search_in_list(l, root->child->brother->info));


            if (strcmp(first, second) == 0) {
                if (strcmp(first, "double") == 0 || strcmp(second, "int") == 0 )
                    root->anot=first; 
                else 
                    root->anot="undef"; 
                }
                else {
                    if ((strcmp(first,"double") == 0 && strcmp(second, "int") == 0) || (strcmp(first,"int") == 0 && strcmp(second, "double") == 0))
                        root->anot="double";
                    else
                        root->anot="undef";
            } 
            }
            else {
                root->anot="undef";
            }

        }
        else {
            root->anot="undef";
        } 
    }
    else if (strcmp(root->child->type, "Id") == 0 && search(root->child->brother->type) == 1) {
        if (search_in_list(l, root->child->info) != NULL) {
            first = (char *)malloc((strlen(search_in_list(l, root->child->info)) * sizeof(char)) + 1);
            strcpy(first,search_in_list(l, root->child->info));

            second = (char *)malloc((strlen(search_type(root->child->brother->type)) * sizeof(char)) + 1);
            strcpy(second,search_type(root->child->brother->type));


            if (strcmp(first, second) == 0) {
                if (strcmp(first, "double") == 0 || strcmp(second, "int") == 0 )
                    root->anot=first; 
                else 
                    root->anot="undef"; 
                }
                else {
                    if ((strcmp(first,"double") == 0 && strcmp(second, "int") == 0) || (strcmp(first,"int") == 0 && strcmp(second, "double") == 0))
                        root->anot="double";
                    else
                        root->anot="undef";
            } 
        }
        else {
            root->anot="undef";
        } 
    }
    else if (search(root->child->type) == 1 && strcmp(root->child->brother->type, "Id") == 0) {
        if (search_in_list(l, root->child->brother->info) != NULL) {
            first = (char *)malloc((strlen(search_type(root->child->type)) * sizeof(char)) + 1);
            strcpy(first,search_type(root->child->type));

            second = (char *)malloc((strlen(search_in_list(l, root->child->brother->info)) * sizeof(char)) + 1);
            strcpy(second,search_in_list(l, root->child->brother->info));

            if (strcmp(first, second) == 0) {
                if (strcmp(first, "double") == 0 || strcmp(second, "int") == 0 )
                    root->anot=first; 
                else 
                    root->anot="undef"; 
                }
                else {
                    if ((strcmp(first,"double") == 0 && strcmp(second, "int") == 0) || (strcmp(first,"int") == 0 && strcmp(second, "double") == 0))
                        root->anot="double";
                    else
                        root->anot="undef";
            } 
        }
        else {
            root->anot="undef";
        } 
    }
    else if (search(root->child->type) == 1 && search(root->child->brother->type) == 1) {
        if (strcmp(search_type(root->child->type), search_type(root->child->brother->type)) == 0) {
            if (strcmp(search_type(root->child->type), "double") == 0 || strcmp(search_type(root->child->type), "int") == 0 )
                root->anot=search_type(root->child->type); 
            else 
                root->anot="undef"; 
        }
        else {
            if ((strcmp(search_type(root->child->type),"double") == 0 && strcmp(search_type(root->child->brother->type), "int") == 0) || (strcmp(search_type(root->child->type),"int") == 0 && strcmp(search_type(root->child->brother->type), "double") == 0))
                root->anot="double";
            else
                root->anot="undef";
        }
    }
    else if (strcmp(root->child->type, "Lshift") == 0 || strcmp(root->child->type, "Rshift") == 0) {
        root->anot="undef";
        root->child->print=0;
        root->child->child->print=0;
        root->child->child->brother->print=0;             
    }
    else if (strcmp(root->child->brother->type, "Lshift") == 0 || strcmp(root->child->brother->type, "Rshift") == 0) {
        root->anot="undef";
        root->child->brother->print=0;
        root->child->brother->child->print=0;
        root->child->brother->child->brother->print=0;             
    }
    else if (strcmp(root->child->brother->type, "Call") == 0) {
        if (strcmp(root->child->type, "Id") == 0) {
            if (search_in_list(l, root->child->info) != NULL) {
                if (strcmp(search_in_list(l, root->child->brother->child->info), search_in_list(l, root->child->info)) == 0) {
                    if (strcmp(search_in_list(l, root->child->brother->child->info), "double") == 0 || strcmp(search_in_list(l, root->child->info), "int") == 0 )
                        root->anot=search_in_list(l, root->child->brother->child->info); 
                    else 
                        root->anot="undef"; 
                }
                else {
                    if ((strcmp(search_in_list(l, root->child->brother->child->info),"double") == 0 && strcmp(search_in_list(l, root->child->info), "int") == 0) || (strcmp(search_in_list(l, root->child->brother->child->info),"int") == 0 && strcmp(search_in_list(l, root->child->info), "double") == 0))
                        root->anot="double";
                    else
                        root->anot="undef";
                }   
            }
            else
                root->anot="undef";
        }
    }
    /* else if (search(root->child->type) == 1) {
        if (strcmp(root->child->brother->type, "Add") == 0 || strcmp(root->child->brother->type, "Sub") == 0 || strcmp(root->child->brother->type, "Mul") == 0 || strcmp(root->child->brother->type, "Div") == 0 || strcmp(root->child->brother->type, "Mod") == 0) {
            verify(root->child->brother, l);
            if (strcmp(search_type(root->child->type), root->child->brother->anot) == 0) {
                if (strcmp(search_type(root->child->type), "double") == 0 || strcmp(search_type(root->child->type), "int") == 0 )
                    root->anot=search_type(root->child->type); 
                else 
                    root->anot="undef"; 
            }
            else {
                if ((strcmp(search_type(root->child->type),"double") == 0 && strcmp(root->child->brother->anot, "int") == 0) || (strcmp(search_type(root->child->type),"int") == 0 && strcmp(root->child->brother->anot, "double") == 0))
                    root->anot="double";
                else
                    root->anot="undef";
            }
        }
    } */
}

void insert_params(node root, list *l) {
    if (strcmp(root->type, "DecLit")==0 || strcmp(root->type, "ParseArgs")==0 || strcmp(root->type, "Length")==0)
        root->anot="int";
    else if (strcmp(root->type, "RealLit")==0)
        root->anot="double";
    else if (strcmp(root->type, "StrLit")==0)
        root->anot="String";
    else if (strcmp(root->type, "Eq")==0 || strcmp(root->type, "Ne")==0 || strcmp(root->type, "Gt")==0 || strcmp(root->type, "Ge")==0 || strcmp(root->type, "Lt")==0 || strcmp(root->type, "Le")==0 || strcmp(root->type, "And")==0 || strcmp(root->type, "Or")==0 || strcmp(root->type, "Not")==0 || strcmp(root->type, "BoolLit")==0)
        root->anot="boolean";
    else if (strcmp(root->type, "VarDecl") == 0 || strcmp(root->type, "FieldDecl") == 0 || strcmp(root->type, "ParamDecl") == 0) {
        if (search_in_list(l, root->child->brother->info) != NULL)
            substitute_in_list(l, type(root->child->type), root->child->brother->info);
        else
            add_to_list(l, type(root->child->type), root->child->brother->info);
        node aux_vardecl = root->child ;
        while (aux_vardecl != NULL) {
            aux_vardecl->print = 0;
            aux_vardecl=aux_vardecl->brother;
        }
    }
    else if (strcmp(root->type, "Program") == 0 || strcmp(root->type, "FieldDecl") == 0) {
        node aux_vardecl = root->child ;
        while (aux_vardecl != NULL) {
            aux_vardecl->print = 0;
            aux_vardecl=aux_vardecl->brother;
        }
    }
    else if (strcmp(root->type, "MethodHeader") == 0) {
        node aux_vardecl = root->child ;
        while (aux_vardecl != NULL) {
            aux_vardecl->print = 0;
            aux_vardecl=aux_vardecl->brother;
        }
        if (strcmp(root->child->brother->info, "main") != 0) {
            add_to_list(l, type(root->child->type), root->child->brother->info);
        }
    }   
    else if (strcmp(root->type, "Id") == 0) {
        if (search_in_list(l, root->info) != NULL)
            root->anot = search_in_list(l, root->info);
        else
            root->anot="undef";
        if (strcmp(root->anot,"undef") != 0) {
            if (root->call == 1) {
                node aux = root->brother;
                if (aux == NULL) 
                    root->anot="";
                else if (strcmp(aux->type, "Call") == 0) {
                    root->anot=search_in_list(l, root->info);
                }
                else {
                    char types[1024] = "";
                    while (aux != NULL) {
                        if (strcmp(aux->type,"Id")==0) {
                            if (search_in_list(l, aux->info) != NULL && aux->brother != NULL) {
                                strcat(types, search_in_list(l, aux->info));
                                strcat(types, ",");
                            }
                            else if (search_in_list(l, aux->info) != NULL && aux->brother == NULL)
                                strcat(types, search_in_list(l, aux->info));                                 
                        } else if (search(aux->type) == 1) {
                            if (aux->brother != NULL) {
                                strcat(types, search_type(aux->type));
                                strcat(types, ",");
                            }
                            else 
                                strcat(types, search_type(aux->type));
                        }
                        aux=aux->brother;
                    }
                    root->anot=types;
                }
            }
        } 
    }
    else if (strcmp(root->type, "Assign") == 0 || strcmp(root->type, "Plus") == 0 || strcmp(root->type, "Minus") == 0) {
        if (strcmp(root->child->type, "Id") == 0) {
            root->anot = search_in_list(l, root->child->info);
        }
        else if (search(root->child->type) == 1) {
            root->anot = search_type(root->child->type);
        }
    }
    /* else if (strcmp(root->type, "Plus") == 0 || strcmp(root->type, "Minus") == 0) {
        if (root->child != NULL) {
            if (strcmp(root->child->type, "Id") == 0) {
                if (strcmp(search_in_list(l, root->child->info), "boolean") == 0)
                    root->anot="undef";
                else
                    root->anot = search_in_list(l, root->child->info);
            }
            else if (search(root->child->type) == 1) {
                if (strcmp(search_type(root->child->type), "boolean") == 0)
                    root->anot="undef";
                else
                    root->anot = search_type(root->child->type);
            }
        }
    } */
    else if (strcmp(root->type, "Add") == 0 || strcmp(root->type, "Sub") == 0 || strcmp(root->type, "Mul") == 0 || strcmp(root->type, "Div") == 0 || strcmp(root->type, "Mod") == 0) {
       verify(root, l);
    }
    else if (strcmp(root->type, "Xor") == 0) {
        node aux_xor = root->child;
        while(search(aux_xor->type) == 0) {
            aux_xor->child;
        }

        node aux_xor2 = root->child->brother;
        while(search(aux_xor2->type) == 0) {
            aux_xor2->child;
        }

        if (strcmp(search_type(aux_xor->type), "int") == 0 && strcmp(search_type(aux_xor2->type), "int") == 0)
            root->anot="int";
        else if (strcmp(search_type(aux_xor->type), "boolean") == 0 && strcmp(search_type(aux_xor2->type), "boolean") == 0)
            root->anot="boolean";
        else 
            root->anot="undef";
    }
    else if (strcmp(root->type, "Call") == 0) {
        root->child->call = 1;
        if (search_in_list(l, root->child->info) != NULL) {
            root->anot = search_in_list(l, root->child->info);
        }
        else 
            root->anot = "undef";
    }
    else if (strcmp(root->type, "Lshift") == 0 || strcmp(root->type, "Rshift") == 0) {
        char* first, *second;
        if (strcmp(root->child->type, "Id") == 0 && strcmp(root->child->brother->type, "Id") == 0) {            
            if (search_in_list(l, root->child->info) != NULL) {
                first = (char *)malloc((strlen(search_in_list(l, root->child->info)) * sizeof(char)) + 1);
                strcpy(first,search_in_list(l, root->child->info));

                if (search_in_list(l, root->child->brother->info) != NULL) {
                second = (char *)malloc((strlen(search_in_list(l, root->child->brother->info)) * sizeof(char)) + 1);
                strcpy(second,search_in_list(l, root->child->brother->info));


                    if (strcmp(first, second) == 0) {
                        if (strcmp(first, "int") == 0)
                            root->anot=first; 
                        else 
                            root->anot="undef"; 
                        }
                        else
                            root->anot="undef"; 
                }
                else {
                    root->anot="undef";
                }

            }
            else {
                root->anot="undef";
            } 
        } 
        else if (strcmp(root->child->type, "Id") == 0 && search(root->child->brother->type) == 1) {
            if (search_in_list(l, root->child->info) != NULL) {
                first = (char *)malloc((strlen(search_in_list(l, root->child->info)) * sizeof(char)) + 1);
                strcpy(first,search_in_list(l, root->child->info));

                second = (char *)malloc((strlen(search_type(root->child->brother->type)) * sizeof(char)) + 1);
                strcpy(second,search_type(root->child->brother->type));


                if (strcmp(first, second) == 0) {
                    if (strcmp(first, "int") == 0)
                        root->anot=first; 
                    else 
                        root->anot="undef"; 
                    }
                    else
                        root->anot="undef";
            }
            else {
                root->anot="undef";
            } 
        }
        else if (search(root->child->type) && strcmp(root->child->brother->type, "Id") == 0) {
        if (search_in_list(l, root->child->brother->type) != NULL) {
                first = (char *)malloc((strlen(search_type(root->child->type)) * sizeof(char)) + 1);
                strcpy(first,search_type(root->child->type));

                second = (char *)malloc((strlen(search_in_list(l, root->child->brother->type)) * sizeof(char)) + 1);
                strcpy(second,search_in_list(l, root->child->brother->type));

                if (strcmp(first, second) == 0) {
                    if (strcmp(first, "int") == 0)
                        root->anot=first; 
                    else 
                        root->anot="undef"; 
                    }
                    else
                        root->anot="undef"; 
            }
            else {
                root->anot="undef";
            } 
        }
        else if (search(root->child->type) == 1 && search(root->child->brother->type) == 1) {
            if (strcmp(search_type(root->child->type), search_type(root->child->brother->type)) == 0) {
                if (strcmp(search_type(root->child->type), "int") == 0 && strcmp(search_type(root->child->brother->type), "int") == 0 )
                    root->anot=search_type(root->child->type); 
                else 
                    root->anot="undef"; 
            }
            else 
                root->anot="undef";
        }
        else if (search(root->child->type) == 1 && (strcmp(root->child->brother->type, "Add") == 0 || strcmp(root->child->brother->type, "Sub") == 0 || strcmp(root->child->brother->type, "Mul") == 0 || strcmp(root->child->brother->type, "Div") == 0 || strcmp(root->child->brother->type, "Mod") == 0)) {
            verify(root->child->brother, l);
            if (strcmp(search_type(root->child->type), root->child->brother->anot) == 0) {
                if (strcmp(search_type(root->child->type), "int") == 0)
                    root->anot=search_type(root->child->type); 
                else 
                    root->anot="undef"; 
            }
            else 
                root->anot="undef";
        }
    }
}

void print_tree_tab(node root, int size, list *l) {
    if (root == NULL)
        return;

	int i = 0;
	node aux;

    insert_params(root, l);

	if (strcmp(root->type, "Program") == 0)
		printf("%s\n", root->type);
	else {
		while (i < size){
			printf("..");
			i++;		
        }

        if (root->anot == NULL || root->print == 0) {
            if (strcmp(root->info, "") != 0)
                printf("%s(%s)\n", root->type, root->info);

            else if (strcmp(root->info, "") == 0)
                printf("%s\n", root->type);
        }
        else {
            if (root->call == 1) {
                if (strcmp(root->anot, "undef") == 0)
                    printf("%s(%s) - %s\n", root->type, root->info, root->anot);
                else
                    printf("%s(%s) - (%s)\n", root->type, root->info, root->anot);
            }

            else if (strcmp(root->info, "") != 0)
                printf("%s(%s) - %s\n", root->type, root->info, root->anot);

            else if (strcmp(root->info, "") == 0)
                printf("%s - %s\n", root->type, root->anot);
        }
	}

	aux = root->child;

	while (aux != NULL){
		node free_n = aux;
		print_tree_tab(aux, size + 1, l);
		aux = aux->brother;

		free(free_n);
	}
}

void free_list(list* l) {
    no_list *start = l->root, *temp;
    while(start != NULL){
        temp = start;
        start = start->next;
        free(temp);
    }
}
