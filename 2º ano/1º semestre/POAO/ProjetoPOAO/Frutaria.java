/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorstarthrive;

import java.util.Scanner;

/**
 *
 * @author filip
 */
public final class Frutaria extends Mercearia {
    private int numProdutos;
    private double valMedioFaturacaoAnual;
    private double valAnualLimpezaEstabelecimento;
    
    /**
     * Construtor por omissão responsável por criar um objeto do tipo Frutaria.
     * 
     */
    public Frutaria() {
    }

    /**
     * Construtor responsável por criar um objeto do tipo Frutaria.
     * 
     * @param numProdutos
     * @param valMedioFaturacaoAnual
     * @param valAnualLimpezaEstabelecimento
     * @param categoriaEmpresa
     * @param nomeEmpresa
     * @param distritoEmpresa
     * @param coordenadasGps 
     */
    public Frutaria(int numProdutos, double valMedioFaturacaoAnual, double valAnualLimpezaEstabelecimento, String categoriaEmpresa, String nomeEmpresa, String distritoEmpresa, Gps coordenadasGps) {
        super(categoriaEmpresa, nomeEmpresa, distritoEmpresa, coordenadasGps);
        this.numProdutos = numProdutos;
        this.valMedioFaturacaoAnual = valMedioFaturacaoAnual;
        this.valAnualLimpezaEstabelecimento = valAnualLimpezaEstabelecimento;
    }
    
    /**
     * Gets número de produtos.
     * 
     * @return 
     */
    public int getNumProdutos() {
        return numProdutos;
    }
    
    /**
     * Sets número de produtos.
     * 
     * @param numProdutos 
     */
    public void setNumProdutos(int numProdutos) {
        this.numProdutos = numProdutos;
    }
    
    /**
     * Gets valor médio de faturação anual.
     * 
     * @return 
     */
    public double getValMedioFaturacaoAnual() {
        return valMedioFaturacaoAnual;
    }
    
    /**
     * Sets valor médio de faturação anual.
     * 
     * @param valMedioFaturacaoAnual 
     */
    public void setValMedioFaturacaoAnual(double valMedioFaturacaoAnual) {
        this.valMedioFaturacaoAnual = valMedioFaturacaoAnual;
    }
    
    /**
     * Gets valor anual limpeza estabelecimento.
     * 
     * @return 
     */
    public double getValAnualLimpezaEstabelecimento() {
        return valAnualLimpezaEstabelecimento;
    }
    
    /**
     * Sets valor anual limpeza estabelecimento.
     * 
     * @param valAnualLimpezaEstabelecimento 
     */
    public void setValAnualLimpezaEstabelecimento(double valAnualLimpezaEstabelecimento) {
        this.valAnualLimpezaEstabelecimento = valAnualLimpezaEstabelecimento;
    }

    @Override
    public double despesaAnual() {
        double despesaAnualTotal = getValAnualLimpezaEstabelecimento();
        
        return despesaAnualTotal;
    }
    
    @Override
    public double receitaAnual() {
        double receitaAnualTotal = getNumProdutos() * getValMedioFaturacaoAnual();
                
        return receitaAnualTotal;
    }
    
    @Override
    public int maiorCapacidade() {
        return 0;
    }
    
    /**
     * Método responsável por imprimir a informação de uma Frutaria.
     * 
     */
    public void informacaoEmpresa() {
        System.out.printf("----------Frutaria----------");
        super.informacaoEmpresa();
        System.out.printf("\nDespesa Anual: " + despesaAnual()
            + "\nReceitaAnual: " + receitaAnual());
        
        if (receitaAnual() > despesaAnual()) {
            System.out.printf("\nLucro: Sim\n\n");
        }
        else {
            System.out.printf("\nLucro: Não\n\n");
        }
    }
    
    /**
     * Método responsável por imprimir os atributos possíveis de editar desta classe.
     * 
     */
    public void informacaoEditarEmpresa() {
        super.informacaoEditarEmpresa();
        System.out.printf("5)Número de produtos.\n"
                + "6)Valor médio de faturação anual.\n"
                + "7)Valor anual de limpeza de estabelecimento.\n");
    }
    
    /**
     * Método responsável pela edição dos atributos desta classe.
     * 
     * @param selecionaOpcao 
     */
    public void executarEditarEmpresa(int selecionaOpcao) {
        while (selecionaOpcao < 0 || selecionaOpcao > 7) {
            System.out.printf("Opção inválida! Por favor tente novamente: ");
            Scanner sc = new Scanner(System.in);
            
            while (!sc.hasNextInt()) {
                System.out.printf("Opção inválida! Por favor tente novamente: ");
                sc = new Scanner(System.in);
            }
            
            selecionaOpcao = sc.nextInt();
        }
        
        super.executarEditarEmpresa(selecionaOpcao);
        
        if (selecionaOpcao == 5) {
            int novoNumProdutos;
            
            System.out.printf("Introduza o novo número de produtos: ");
            Scanner sc = new Scanner(System.in);
            
            while (!sc.hasNextInt()) {
                System.out.printf("Novo número de produtos inválido! Por favor tente novamente: ");
                sc = new Scanner(System.in);
            }
            
            novoNumProdutos = sc.nextInt();
            
            setNumProdutos(novoNumProdutos);
        }
        
        else if (selecionaOpcao == 6) {
            double novoValMedioFaturacaoAnual;
            
            System.out.printf("Introduza o novo valor médio de faturação anual: ");
            Scanner sc = new Scanner(System.in);
            
            while (!sc.hasNextDouble()) {
                System.out.printf("Novo valor médio de faturação anual inválido! Por favor tente novamente: ");
                sc = new Scanner(System.in);
            }
            
            novoValMedioFaturacaoAnual = sc.nextDouble();
            
            setValMedioFaturacaoAnual(novoValMedioFaturacaoAnual);
        }
        
        else if (selecionaOpcao == 7) {
            double novoValAnualLimpezaEstabelecimento;
            
            System.out.printf("Introduza o novo valor anual de limpeza do estabelecimento: ");
            Scanner sc = new Scanner(System.in);
            
            while (!sc.hasNextDouble()) {
                System.out.printf("Novo valor anula de limpeza do eatabelecimento inválido! Por favor tente novamente: ");
                sc = new Scanner(System.in);
            }
            
            novoValAnualLimpezaEstabelecimento = sc.nextDouble();
            
            setValAnualLimpezaEstabelecimento(novoValAnualLimpezaEstabelecimento);
        }
    }
    
    @Override
    public String toString() {
        return  "\nNúmero de Produtos: " + numProdutos
                + "\nValor Médio de Faturação Anual: " + valMedioFaturacaoAnual
                + "\nValor Anual de Limpeza do Estabelecimento: " + valAnualLimpezaEstabelecimento
                + "\n";
    }
}
