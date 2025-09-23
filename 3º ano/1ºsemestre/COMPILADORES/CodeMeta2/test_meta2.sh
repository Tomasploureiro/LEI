#!/bin/bash

# Script para testar o analisador sintático (meta2)
# Compara a saída do programa com os ficheiros .out esperados

echo "========================================="
echo "   TESTE DO ANALISADOR SINTÁTICO (META2)"
echo "========================================="
echo

# Verifica se o compilador existe
if [ ! -f "./gocompiler" ]; then
    echo "ERRO: gocompiler não encontrado. Execute 'make meta2' primeiro."
    exit 1
fi

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
    ./gocompiler -t < "$test_file" > temp_output.txt 2>&1
    
    # Compara com o output esperado
    if diff -q temp_output.txt "$expected_file" > /dev/null 2>&1; then
        echo "✅ PASSOU"
        ((passed_tests++))
    else
        echo "❌ FALHOU"
        ((failed_tests++))
        
        # Mostra as diferenças (limitadas)
        echo "   Diferenças encontradas:"
        echo "   Esperado vs Obtido:"
        diff "$expected_file" temp_output.txt | head -10
        echo
    fi
    
    ((total_tests++))
}

# Função para testar erro sintático
run_error_test() {
    local test_file="$1"
    local expected_file="$2"
    local test_name=$(basename "$test_file" .dgo)
    
    echo -n "Testando $test_name (erros)... "
    
    # Executa o programa e guarda a saída
    ./gocompiler -t < "$test_file" > temp_output.txt 2>&1
    
    # Para testes de erro, verificamos se há mensagens de erro
    if [ -s temp_output.txt ]; then
        # Se há output (erros), compara com esperado se existir
        if [ -f "$expected_file" ]; then
            if diff -q temp_output.txt "$expected_file" > /dev/null 2>&1; then
                echo "✅ PASSOU (erro detectado corretamente)"
                ((passed_tests++))
            else
                echo "⚠️  ERRO DETECTADO MAS DIFERENTE DO ESPERADO"
                ((failed_tests++))
                echo "   Esperado vs Obtido:"
                diff "$expected_file" temp_output.txt | head -5
                echo
            fi
        else
            echo "✅ PASSOU (erro detectado)"
            ((passed_tests++))
        fi
    else
        echo "❌ FALHOU (erro não detectado)"
        ((failed_tests++))
    fi
    
    ((total_tests++))
}

# Testa todos os ficheiros .dgo que têm um .out correspondente na meta2
echo "Executando testes de sintaxe..."
echo "--------------------------------"

for dgo_file in meta2/*.dgo; do
    if [ -f "$dgo_file" ]; then
        out_file="${dgo_file%.dgo}.out"
        test_name=$(basename "$dgo_file" .dgo)
        
        if [ -f "$out_file" ]; then
            # Verifica se é um teste de erro
            if [[ "$test_name" == *"error"* ]] || [[ "$test_name" == *"Error"* ]] || [[ "$test_name" == *"testeErros"* ]]; then
                run_error_test "$dgo_file" "$out_file"
            else
                run_test "$dgo_file" "$out_file"
            fi
        else
            echo "⚠️  Arquivo $test_name.out não encontrado, pulando..."
        fi
    fi
done

# Limpa ficheiros temporários
rm -f temp_output.txt

# Mostra resumo
echo
echo "========================================="
echo "              RESUMO META2"
echo "========================================="
echo "Total de testes: $total_tests"
echo "Testes que passaram: $passed_tests"
echo "Testes que falharam: $failed_tests"
echo

if [ $failed_tests -eq 0 ]; then
    echo "🎉 TODOS OS TESTES PASSARAM!"
    exit 0
else
    echo "⚠️  Alguns testes falharam. Verifique a implementação da gramática."
    exit 1
fi