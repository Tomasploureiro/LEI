#!/bin/bash

# Script para testar o analisador léxico gocompiler
# Compara a saída do programa com os ficheiros .out esperados

echo "========================================="
echo "     TESTE DO ANALISADOR LÉXICO"
echo "========================================="
echo

# Primeiro compila o programa
echo "Compilando gocompiler..."
flex gocompiler.l
if [ $? -ne 0 ]; then
    echo "ERRO: Falha ao executar flex"
    exit 1
fi

gcc -o gocompiler lex.yy.c -lfl
if [ $? -ne 0 ]; then
    echo "ERRO: Falha ao compilar com gcc"
    exit 1
fi

echo "Compilação bem-sucedida!"
echo

# Contadores
total_tests=0
passed_tests=0
failed_tests=0

# Função para executar um teste
run_test() {
    local test_file="$1"
    local expected_file="$2"
    local test_name=$(basename "$test_file" .dgo)
    
    echo -n "Testando $test_name... "
    
    # Executa o programa e guarda a saída
    ./gocompiler -l < "$test_file" > temp_output.txt 2>&1
    
    # Compara com o output esperado
    if diff -q temp_output.txt "$expected_file" > /dev/null 2>&1; then
        echo "✅ PASSOU"
        ((passed_tests++))
    else
        echo "❌ FALHOU"
        ((failed_tests++))
        
        # Mostra as diferenças
        echo "   Diferenças encontradas:"
        echo "   Esperado vs Obtido:"
        diff "$expected_file" temp_output.txt | head -10
        echo
    fi
    
    ((total_tests++))
}

# Testa todos os ficheiros .dgo que têm um .out correspondente
echo "Executando testes..."
echo "--------------------"

for dgo_file in meta1/*.dgo; do
    if [ -f "$dgo_file" ]; then
        # Verifica se existe o ficheiro .out correspondente
        out_file="${dgo_file%.dgo}.out"
        if [ -f "$out_file" ]; then
            run_test "$dgo_file" "$out_file"
        fi
    fi
done

# Limpa ficheiros temporários
rm -f temp_output.txt

# Mostra resumo
echo
echo "========================================="
echo "              RESUMO"
echo "========================================="
echo "Total de testes: $total_tests"
echo "Testes que passaram: $passed_tests"
echo "Testes que falharam: $failed_tests"
echo

if [ $failed_tests -eq 0 ]; then
    echo "🎉 TODOS OS TESTES PASSARAM!"
    exit 0
else
    echo "⚠️  Alguns testes falharam. Verifique a implementação."
    exit 1
fi