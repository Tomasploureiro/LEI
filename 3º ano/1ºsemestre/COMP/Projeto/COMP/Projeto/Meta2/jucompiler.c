#include "jucompiler.h"

node create_node(char* type, char* info){
		if((type == NULL) || (info == NULL))
            return NULL;
		
		node new_node = malloc(sizeof(no));
		
		new_node->type = (char*)malloc((strlen(type)*sizeof(char))+1);
    	new_node->type = type;

		new_node->info = (char*)malloc((strlen(info)*sizeof(char))+1);
		new_node->info = info;

		new_node->brother = NULL;
		new_node->child = NULL;
		new_node->parent = NULL;
		new_node->n_children = 0;

		return new_node;
	}


	void add_node(node parent, node to_be_added){
		if(to_be_added == NULL)
			return;	
		
		parent->child = to_be_added;
		to_be_added->parent = parent;
	}

	void add_brother(node n, node brother) {
		if((n == NULL) || (brother == NULL)) 
			return;
		
		node aux = n;

		while(aux->brother != NULL)
			aux = aux->brother;

		aux->brother = brother;

		if(n->parent != NULL) {
			brother->parent = n->parent;
			n->parent->n_children++;
		}
	}

	int count_sons(node root) {
		int count = 0;
		node aux = root;

		while(aux != NULL) {
			aux = aux->child;
			count++;
		}

		return count;
	}
	
	int count_brothers(node root) {
		int count = 0;
		node aux = root;

		while(aux != NULL) {
			aux = aux->brother;
			count++;
		}
		
		return count;
	}

	void print_tree(node root, int size) {
		if(root == NULL){
        	return;
    	}

		int i = 0;
		node aux;

		if(strcmp(root->type, "Program") == 0)
			printf("%s\n", root->type);
		else {
			while(i < size) {
				printf("..");
				i++;
			}
			if(strcmp(root->info, "") != 0)
				printf("%s(%s)\n", root->type, root->info);
			else
				printf("%s\n", root->type);
		}

		aux = root->child;

		while(aux!= NULL){
			node free_n = aux;
			print_tree(aux, size+1);
			aux = aux->brother;
			
			free(free_n);
		}
	}