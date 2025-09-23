CC = gcc
CFLAGS = -Wall -g -std=c99

all: gocompiler

gocompiler: lex.yy.c y.tab.c ast.c
	$(CC) $(CFLAGS) -o gocompiler lex.yy.c y.tab.c ast.c -lfl

lex.yy.c: gocompiler.l y.tab.h
	flex gocompiler.l

y.tab.c y.tab.h: gocompiler.y
	yacc -d gocompiler.y

clean:
	rm -f lex.yy.c y.tab.c y.tab.h gocompiler

test: gocompiler
	./test_meta2.sh

.PHONY: all clean test