# Compilador Go - Meta 2

Este projeto implementa um analisador sintático (parser) para uma linguagem simplificada baseada em Go.

## Componentes

- **gocompiler_meta2.l**: Analisador léxico (lexer) usando Flex
- **gocompiler.y**: Analisador sintático (parser) usando Yacc/Bison  
- **ast.h/ast.c**: Implementação da Árvore Sintática Abstrata (AST)
- **Makefile**: Automação do processo de compilação

## Compilação

```bash
make clean && make
```

## Execução

```bash
./gocompiler_meta2 < arquivo.dgo
```

## Testes

```bash
./test_meta2.sh
```

O projeto passa em todos os 21 testes de sintaxe fornecidos.

## Funcionalidades Suportadas

- Declarações de variáveis (simples e múltiplas)
- Declarações de funções com parâmetros
- Estruturas de controle (if/else, for)
- Expressões aritméticas e lógicas
- Chamadas de função
- Tratamento de erros sintáticos