#include <stdlib.h>
#include <stdio.h>
#include "ast.h"
#include <string.h>

struct node *new_node(enum category category, char *token) {
    struct node *new = malloc(sizeof(struct node));
    new->category = category;
    new->token = token;
    new->children = malloc(sizeof(struct node_list));
    new->children->node = NULL;
    new->children->next = NULL;
    return new;
}

void add_child(struct node *parent, struct node *child) {
    if(parent == NULL || child == NULL){
        return;
    }

    struct node_list *new = malloc(sizeof(struct node_list));
    new->node = child;
    new->next = NULL;
    struct node_list *children = parent->children;
    while(children->next != NULL)
        children = children->next;
    children->next = new;
}

void remove_first_child(struct node *parent) {
    struct node_list *children = parent->children;
    if(children->next == NULL)
        return;
    struct node_list *new = children->next;
    parent->children = new;
    free(children);
}


const char *category_name[] = names;

void dfs(struct node *cur_node, int depth){
    if(cur_node == NULL){
        return;
    }

    for(int i = 0; i < depth; i++){
        printf("..");
    }    
    
    if(cur_node->token == NULL){
        printf("%s\n", category_name[cur_node->category]);
    }
    else{
        if(cur_node->token != NULL && strlen(cur_node->token) > 0) {
            printf("%s(%s)\n", category_name[cur_node->category], cur_node->token);
        } else {
            printf("%s()\n", category_name[cur_node->category]);
        }
    }
    
    if(cur_node->children == NULL){
        return;
    }
    
    struct node_list *child = cur_node->children;
    while((child = child->next) != NULL){
        dfs(child->node, depth+1);
    }
}

void free_ast (struct node * cur_node){
    if(cur_node == NULL){
        return;
    }

    struct node_list *child = cur_node->children;
    while(child != NULL){
        struct node_list *next = child->next;
        
        if(child->node != NULL){
            free_ast(child->node);
        }
        free(child);
        child = next;
    }
    if(cur_node->token != NULL){
        free(cur_node->token);
        cur_node->token = NULL;
    }
    free(cur_node);
}

void deallocate(struct node *node) {
    if(node != NULL) {
        struct node_list *child = node->children;
        while(child != NULL) {
            deallocate(child->node);
            struct node_list *tmp = child;
            child = child->next;
            free(tmp);
        }
        if(node->token != NULL)
            free(node->token);
        free(node);
    }
}


int block_elements(struct node* cur_node){
    int count = 0;
    if(cur_node == NULL){
        return count;
    }

    struct node_list *child = cur_node->children;
    while((child = child->next) != NULL){
        if(child->node->category != AUX){
            count++;
        }
        else{
            count += block_elements(child->node);
        }
    }
    return count;
}

void remove_aux(struct node *parent) {
    if (parent == NULL || parent->children == NULL) {
        return;
    }

    struct node_list *prev = parent->children;
    struct node_list *current = prev->next;

    while (current != NULL) {
        if (current->node->category == AUX) {
            struct node_list *aux_children = current->node->children->next;

            if (aux_children != NULL) {
                struct node_list *last_aux_child = aux_children;
                while (last_aux_child->next != NULL) {
                    last_aux_child = last_aux_child->next;
                }

                prev->next = aux_children;

                last_aux_child->next = current->next;
            } 
            else {
                prev->next = current->next;
            }

            free(current->node->children);
            free(current->node);


            struct node_list *temp = current;
            current = prev->next;
            free(temp);

        } 
        else {
            remove_aux(current->node);
            prev = current;
            current = current->next;
        }
    }
}