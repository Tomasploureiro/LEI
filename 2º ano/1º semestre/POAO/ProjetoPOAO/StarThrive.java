/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorstarthrive;

import java.util.ArrayList;
import java.io.Serializable;

/**
 *
 * @author filip
 */
public final class StarThrive implements Serializable {
    private ArrayList<Empresa> empresas;
    
    /**
     * Construtor por omissão responsável por criar um objeto StarThrive.
     * 
     */
    public StarThrive() {
    }
    
    /**
     * Construtor responsável por criar um objeto StarThrive.
     * 
     * @param empresas 
     */
    public StarThrive(ArrayList<Empresa> empresas) {
        this.empresas = empresas;
    }
    
    /**
     * Gets empresas.
     * 
     * @return 
     */
    public ArrayList<Empresa> getEmpresas() {
        return empresas;
    }
    
    /**
     * Sets empresas.
     * 
     * @param empresas 
     */
    public void setEmpresas(ArrayList<Empresa> empresas) {
        this.empresas = empresas;
    }
    
    /**
     * Método responsável por imprimir todas as empresas.
     * 
     */
    public void imprimirEmpresas() {
        System.out.printf("Empresas Geridas Pela StarThrive:\n\n");
        
        for (Empresa empresa : empresas) {
            empresa.informacaoEmpresa();
        }
    }
    
    /**
     * Método responsável por determinar o índice da empresa do tipo restauração com maior valor de receita anual.
     * 
     * @return 
     */
    public int maiorReceitaRestauracao() {
        double maiorReceita = empresas.get(0).receitaAnual();
        int indexEmpresa = 0;
        
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).receitaAnual() > maiorReceita  && empresas.get(i).getCategoriaEmpresa().equals("Restauração") == true) {
                maiorReceita = empresas.get(i).receitaAnual();
                indexEmpresa = i;
            }
        }
        
        return indexEmpresa;
    }
    
    /**
     * Método responsável por determinar o índice da empresa do tipo restauração com menor valor de despesa anual.
     * 
     * @return 
     */
    public int menorDespesaRestauracao() {
        double menorDespesa = empresas.get(0).despesaAnual();
        int indexEmpresa = 0;
        
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).despesaAnual() < menorDespesa  && empresas.get(i).getCategoriaEmpresa().equals("Restauração") == true) {
                menorDespesa = empresas.get(i).despesaAnual();
                indexEmpresa = i;
            }
        }
        
        return indexEmpresa;
    }
    
    /**
     * Método responsável por determinar o índice da empresa do tipo restauração com maior valor de lucro anual.
     * 
     * @return 
     */
    public int maiorLucroRestauracao() {
        double maiorLucro = empresas.get(0).receitaAnual();
        int indexEmpresa = 0;
        
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).lucroAnual() > maiorLucro  && empresas.get(i).getCategoriaEmpresa().equals("Restauração") == true) {
                maiorLucro = empresas.get(i).receitaAnual();
                indexEmpresa = i;
            }
        }
        
        return indexEmpresa;
    }
    
    /**
     * Método responsável por determinar o índica da empresa do tipo mercearia com maior valor de receita anual.
     * 
     * @return 
     */
    public int maiorReceitaMercearia() {
        double maiorReceita = empresas.get(0).receitaAnual();
        int indexEmpresa = 0;
        
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).receitaAnual() > maiorReceita  && empresas.get(i).getCategoriaEmpresa().equals("Mercearia") == true) {
                maiorReceita = empresas.get(i).receitaAnual();
                indexEmpresa = i;
            }
        }
        
        return indexEmpresa;
    }
    
    /**
     * Método responsável por determinar o índice da empresa do tipo mercearia com menor valor de despesa anual.
     * 
     * @return 
     */
    public int menorDespesaMercearia() {
        double menorDespesa = empresas.get(0).despesaAnual();
        int indexEmpresa = 0;
        
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).despesaAnual() < menorDespesa  && empresas.get(i).getCategoriaEmpresa().equals("Mercearia") == true) {
                menorDespesa = empresas.get(i).despesaAnual();
                indexEmpresa = i;
            }
        }
        
        return indexEmpresa;
    }
    
    /**
     * Método responsável por determinar o índice da empresa do tipo mercearia com maior valor de lucro anual.
     * 
     * @return 
     */
    public int maiorLucroMercearia() {
        double maiorLucro = empresas.get(0).receitaAnual();
        int indexEmpresa = 0;
        
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).lucroAnual() > maiorLucro  && empresas.get(i).getCategoriaEmpresa().equals("Mercearia") == true) {
                maiorLucro = empresas.get(i).receitaAnual();
                indexEmpresa = i;
            }
        }
        
        return indexEmpresa;
    }
    
    /**
     * Método responsável por imprimir a informação das empresas com maiores receitas anuais, menores despesas anuais e maior lucro anual (Para cada tipo de empresa).
     * 
     */
    public void imprimirEmpresasCondicoes() {
        int maiorReceita = maiorReceitaRestauracao();   //Variáveis que contêm o índice das empresas.
        int menorDespesa = menorDespesaRestauracao();
        int maiorLucro = maiorLucroRestauracao();
        
        empresas.get(maiorReceita).maiorReceita();
        empresas.get(menorDespesa).menorDespesa();
        empresas.get(maiorLucro).maiorLucro();
        
        maiorReceita = maiorReceitaMercearia();
        menorDespesa = menorDespesaMercearia();
        maiorLucro = maiorLucroMercearia();
        
        empresas.get(maiorReceita).maiorReceita();
        empresas.get(menorDespesa).menorDespesa();
        empresas.get(maiorLucro).maiorLucro();
    }
    
    /**
     * Método responsável por imprimir as duas empresas do tipo restauração com maior capacidade máxima de clientes.
     * 
     */
    public void imprimirEmpresasMaiorCapacidade() {
        int maiorCapacidade = empresas.get(0).maiorCapacidade();
        int index0 = 0;
        int index1 = 0;
        
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).maiorCapacidade() > maiorCapacidade) {
                maiorCapacidade = empresas.get(i).maiorCapacidade();
                index0 = i;   //A variável index0 guarda o valor do índice do objeto no ArrayList empresas com maior número de capacidade máxima de clientes.
            }
        }
        
        maiorCapacidade = empresas.get(0).maiorCapacidade();
        
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).maiorCapacidade() > maiorCapacidade  && i != index0) {   //A condição i != index0 permite obter o segundo valor mais elevado de capacidade máxima de clientes.
                maiorCapacidade = empresas.get(i).maiorCapacidade();
                index1 = i;
            }
        }
        
        System.out.printf("Empresas do tipo restauração com maior capacidade de clientes:\n\n");
        
        empresas.get(index0).informacaoEmpresa();
        empresas.get(index1).informacaoEmpresa();
    }
    
    /**
     * Método responsável por procurar uma empresa pelo seu nome.
     * 
     * @param nomeEmpresa
     * @return 
     */
    public Empresa procurarEmpresa(String nomeEmpresa) {
        Empresa empresaAux = null;
        
        for (Empresa empresa : empresas) {
            if (nomeEmpresa.equals(empresa.getNomeEmpresa())) {
                empresaAux = empresa;
            }
        }
        
        return empresaAux;
    }
    
    @Override
    public String toString() {
        return "StarThrive{" + "empresas=" + empresas + '}';
    }
}
