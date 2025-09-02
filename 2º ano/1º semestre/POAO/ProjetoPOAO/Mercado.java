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
public final class Mercado extends Mercearia {
    private String tipoMercado;
    private double areaCorredores;
    private double valMedioFaturacaoAnual;
    private double valAnualLimpezaEstabelecimento;
    
    /**
     * Construtor por omissão responsável por criar um objeto do tipo Mercado.
     * 
     */
    public Mercado() {
    }
    
    /**
     * Construtor responsável por criar um objeto do tipo Mercado.
     * 
     * @param tipoMercado
     * @param areaCorredores
     * @param valMedioFaturacaoAnual
     * @param valAnualLimpezaEstabelecimento
     * @param categoriaEmpresa
     * @param nomeEmpresa
     * @param distritoEmpresa
     * @param coordenadasGps 
     */
    public Mercado(String tipoMercado, double areaCorredores, double valMedioFaturacaoAnual, double valAnualLimpezaEstabelecimento, String categoriaEmpresa, String nomeEmpresa, String distritoEmpresa, Gps coordenadasGps) {
        super(categoriaEmpresa, nomeEmpresa, distritoEmpresa, coordenadasGps);
        this.tipoMercado = tipoMercado;
        this.areaCorredores = areaCorredores;
        this.valMedioFaturacaoAnual = valMedioFaturacaoAnual;
        this.valAnualLimpezaEstabelecimento = valAnualLimpezaEstabelecimento;
    }

    public String getTipoMercado() {
        return tipoMercado;
    }

    public void setTipoMercado(String tipoMercado) {
        this.tipoMercado = tipoMercado;
    }

    public double getAreaCorredores() {
        return areaCorredores;
    }

    public void setAreaCorredores(double areaCorredores) {
        this.areaCorredores = areaCorredores;
    }

    public double getValMedioFaturacaoAnual() {
        return valMedioFaturacaoAnual;
    }

    public void setValMedioFaturacaoAnual(double valMedioFaturacaoAnual) {
        this.valMedioFaturacaoAnual = valMedioFaturacaoAnual;
    }

    public double getValAnualLimpezaEstabelecimento() {
        return valAnualLimpezaEstabelecimento;
    }

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
        double receitaAnualTotal = getAreaCorredores() * getValMedioFaturacaoAnual();
        
        return receitaAnualTotal;
    }
    
    @Override
    public int maiorCapacidade(){
        return 0;
    }
    
    /**
     * Método responsável por imprimir informação de um Mercado.
     * 
     */
    public void informacaoEmpresa() {
        System.out.printf("----------Mercado----------");
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
        System.out.printf("5)Tipo de mercado.\n"
                + "6)Área total de corredores.\n"
                + "7)Valor médio de faturação anual.\n"
                + "8)Valor anual de limpeza de estabelecimento.\n");
    }
    
    /**
     * Método responsável pela edição dos atributos desta classe.
     * 
     * @param selecionaOpcao 
     */
    public void executarEditarEmpresa(int selecionaOpcao) {
        while (selecionaOpcao < 0 || selecionaOpcao > 8) {
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
            String novoTipoMercado;
            
            System.out.printf("Introduza o novo tipo de mercado: ");
            Scanner sc = new Scanner(System.in);
            novoTipoMercado = sc.nextLine();
            
            while (novoTipoMercado.equals("Mini") == false && novoTipoMercado.equals("Super") == false && novoTipoMercado.equals("Hiper") == false) {
                System.out.printf("Novo tipo de mercado inválido! Por favor tente novamente: ");
                Scanner sc1 = new Scanner(System.in);
                novoTipoMercado = sc1.nextLine();
            }
            
            setTipoMercado(novoTipoMercado);
        }
        
        else if (selecionaOpcao == 6) {
            double novaAreaCorredores;
            
            System.out.printf("Introduza o novo valor da área total de corredores: ");
            Scanner sc = new Scanner(System.in);
            
            while (!sc.hasNextDouble()) {
                System.out.printf("Novo valor da área total de corredores inválido! Por favor tente novamente: ");
                sc = new Scanner(System.in);
            }
            
            novaAreaCorredores = sc.nextDouble();
            
            setAreaCorredores(novaAreaCorredores);
        }
        
        else if (selecionaOpcao == 7) {
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
        
        else if (selecionaOpcao == 8) {
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
        return  "\nTipo de Mercado: " + tipoMercado
                + "\nÁrea Total de Corredores (m^2): " + areaCorredores
                + "\nValor Médio de Faturação Anual: " + valMedioFaturacaoAnual
                + "\nValor Anual de Limpeza do Estabelecimento: " + valAnualLimpezaEstabelecimento
                + "\n";
    }
}
