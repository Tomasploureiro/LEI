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
	} no;

node create_node(char* type, char* info);
void add_node(node parent, node to_be_added);
void add_brother(node n, node brother);
int count_sons(node root);
int count_brothers(node root);
void print_tree(node root, int size);