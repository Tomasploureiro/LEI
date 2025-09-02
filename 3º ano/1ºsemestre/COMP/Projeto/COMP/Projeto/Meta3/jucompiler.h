#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdarg.h>

typedef struct no* node;
typedef struct no {

	node brother;
	node child;
	node parent;

	char* info;
	char* type;

	int n_children;

    char* anot;
    int print;
    int call;
    
} no;

typedef struct no_tab* node_tab;
typedef struct no_tab{

    char* name;
    char* type;
    int is_method;
    int is_params;
    node_tab brother;
    node_tab vars;
    node_tab method; 

}no_tab;

typedef struct table* tab;
typedef struct table{

    char* name;
    node_tab symbols;
    tab next;

}table;

typedef struct no_list{

    char* type;
    char* name;
    struct no_list *next;

}no_list;

typedef struct list{

    no_list *root;

}list;


node create_node(char* type, char* info);
void add_node(node parent, node to_be_added);
void add_brother(node n, node brother);
int count_sons(node root);
int count_brothers(node root);
void print_tree(node root, int size);

tab create_table(node root);
void print_table(tab no, int size);
void search_in_tree(node root, list *l);
void print_tree_tab(node root, int size, list *l);
void free_list(list* l);