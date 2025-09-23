CC = gcc
CFLAGS = -Wall -g -std=c99

all: gocompiler

gocompiler: lex.yy.c
	$(CC) $(CFLAGS) -o gocompiler lex.yy.c -lfl

lex.yy.c: gocompiler.l
	flex gocompiler.l

clean:
	rm -f lex.yy.c gocompiler

test: gocompiler
	./test_all.sh

.PHONY: all clean test

.PHONY: all clean test