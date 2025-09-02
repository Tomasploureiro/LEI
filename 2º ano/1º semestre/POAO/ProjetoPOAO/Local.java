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
public final class Local extends Restaurante {
    private int numMesasInteriores;
    private int numMesasEsplanada;
    private double valLicencaAnualMesaEsplanada;
    private double valMedioFaturacaoDiario;
    
    /**
     * Construtor por omissão responsável por criar um objeto do tipo Local.
     * 
     */
    public Local() {
    }
    
    /**
     * Construtor responsável por criar um objeto do tipo Local.
     * 
     * @param numMesasInteriores
     * @param numMesasEsplanada
     * @param valLicencaAnualMesaEsplanada
     * @param valMedioFaturacaoDiario
     * @param numMedioClientesDiario
     * @param numDiasFuncionamentoAnual
     * @param custoEmpregados
     * @param numEmpregados
     * @param salarioMedioAnual
     * @param categoriaEmpresa
     * @param nomeEmpresa
     * @param distritoEmpresa
     * @param coordenadasGps 
     */
    public Local(int numMesasInteriores, int numMesasEsplanada, double valLicencaAnualMesaEsplanada, double valMedioFaturacaoDiario, int numMedioClientesDiario, int numDiasFuncionamentoAnual, double custoEmpregados, int numEmpregados, double salarioMedioAnual, String categoriaEmpresa, String nomeEmpresa, String distritoEmpresa, Gps coordenadasGps) {
        super(numMedioClientesDiario, numDiasFuncionamentoAnual, custoEmpregados, numEmpregados, salarioMedioAnual, categoriaEmpresa, nomeEmpresa, distritoEmpresa, coordenadasGps);
        this.numMesasInteriores = numMesasInteriores;
        this.numMesasEsplanada = numMesasEsplanada;
        this.valLicencaAnualMesaEsplanada = valLicencaAnualMesaEsplanada;
        this.valMedioFaturacaoDiario = valMedioFaturacaoDiario;
    }
    
    /**
     * Gets número de mesas interiores.
     * 
     * @return 
     */
    public int getNumMesasInteriores() {
        return numMesasInteriores;
    }
    
    /**
     * Sets número de mesas interiores.
     * 
     * @param numMesasInteriores 
     */
    public void setNumMesasInteriores(int numMesasInteriores) {
        this.numMesasInteriores = numMesasInteriores;
    }
    
    /**
     * Gets número de mesas na esplanada.
     * 
     * @return 
     */
    public int getNumMesasEsplanada() {
        return numMesasEsplanada;
    }
    
    /**
     * Sets número de mesas na esplanada.
     * 
     * @param numMesasEsplanada 
     */
    public void setNumMesasEsplanada(int numMesasEsplanada) {
        this.numMesasEsplanada = numMesasEsplanada;
    }
    
    /**
     * Gets valor licença anual por mesa na esplanada.
     * 
     * @return 
     */
    public double getValLicencaAnualMesaEsplanada() {
        return valLicencaAnualMesaEsplanada;
    }
    
    /**
     * Sets valor licença anual por mesas na esplanada.
     * 
     * @param valLicencaAnualMesaEsplanada 
     */
    public void setValLicencaAnualMesaEsplanada(double valLicencaAnualMesaEsplanada) {
        this.valLicencaAnualMesaEsplanada = valLicencaAnualMesaEsplanada;
    }
    
    /**
     * Gets valor médio faturação diário.
     * 
     * @return 
     */
    public double getValMedioFaturacaoDiario() {
        return valMedioFaturacaoDiario;
    }
    
    /**
     * Sets valor médio faturação diário.
     * 
     * @param valMedioFaturacaoDiario 
     */
    public void setValMedioFaturacaoDiario(double valMedioFaturacaoDiario) {
        this.valMedioFaturacaoDiario = valMedioFaturacaoDiario;
    }
    
    @Override
    public double despesaAnual() {
        double despesaAnualTotal = (getNumEmpregados() * getSalarioMedioAnual()) + (getNumMesasEsplanada() * getValLicencaAnualMesaEsplanada());
                
        return despesaAnualTotal;
    }
    
    @Override
    public double receitaAnual() {
        double receitaAnualTotal = (getNumMesasInteriores() + getNumMesasEsplanada()) * getValMedioFaturacaoDiario() * getNumDiasFuncionamentoAnual();
        
        return receitaAnualTotal;
    }
    
    @Override
    public int maiorCapacidade() {
        return numMesasInteriores + numMesasEsplanada;
    }
    
    /**
     * Método responsável por imprimir informação de um restaurante local.
     * 
     */
    public void informacaoEmpresa() {
        System.out.printf("----------Restaurante Local----------");
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
        System.out.printf("10)Número de mesas interiores.\n"
                + "11)Número de mesas de esplanada.\n"
                + "12)Valor de licença anual por mesa de esplanada.\n"
                + "13)Valor médio de faturação diário.\n");
    }
    
    /**
     * Método responsável por editar os atributos desta classe.
     * 
     * @param selecionaOpcao 
     */
    public void executarEditarEmpresa(int selecionaOpcao) {   //Método responsável pela edição dos atributos desta classe.
        while (selecionaOpcao < 1 || selecionaOpcao > 13) {
            System.out.printf("Opção inválida! Por favor tente novamente: ");
            Scanner sc = new Scanner(System.in);
            
            while (!sc.hasNextInt()) {
                System.out.printf("Opção inválida! Por favor tente novamente: ");
                sc = new Scanner(System.in);
            }
            
            selecionaOpcao = sc.nextInt();
        }
        
        super.executarEditarEmpresa(selecionaOpcao);
        
        if (selecionaOpcao == 10) {
            int novoNumMesasInteriores;
            
            System.out.printf("Introduza o novo número de mesas interiores: ");
            Scanner sc = new Scanner(System.in);
            
            while (!sc.hasNextInt()) {
                System.out.printf("Novo número de mesas inteiores inválido! Por favor tente novamente: ");
                sc = new Scanner(System.in);
            }
            
            novoNumMesasInteriores = sc.nextInt();
            
            setNumMesasInteriores(novoNumMesasInteriores);
        }
        
        else if (selecionaOpcao == 11) {
            int novoNumMesasEsplanada;
            
            System.out.printf("Introduza o novo número de mesas na esplanada: ");
            Scanner sc = new Scanner(System.in);
            
            while (!sc.hasNextInt()) {
                System.out.printf("Novo número de mesas na esplanada inválido! Por favor tente novamente: ");
                sc = new Scanner(System.in);
            }
            
            novoNumMesasEsplanada = sc.nextInt();
            
            setNumMesasEsplanada(novoNumMesasEsplanada);
        }
        
        else if (selecionaOpcao == 12) {
            double novoValLicencaAnualMesaEsplanada;
            
            System.out.printf("Introduza o novo valor de licença anual por mesa na esplanada: ");
            Scanner sc = new Scanner(System.in);
            
            while (!sc.hasNextDouble()) {
                System.out.printf("Novo valor de licença anual por mesa na esplanada inválido! Por favor tente novamente: ");
                sc = new Scanner(System.in);
            }
            
            novoValLicencaAnualMesaEsplanada = sc.nextDouble();
            
            setValLicencaAnualMesaEsplanada(novoValLicencaAnualMesaEsplanada);
        }
        
        else if (selecionaOpcao == 13) {
            double novoValMedioFaturacaoDiario;
            
            System.out.printf("Introduza o novo valor médio de faturação diário: ");
            Scanner sc = new Scanner(System.in);
            
            while (!sc.hasNextDouble()) {
                System.out.printf("Novo valor médio de faturação diário inválido! Por favor tente novamente: ");
                sc = new Scanner(System.in);
            }
            
            novoValMedioFaturacaoDiario = sc.nextDouble();
            
            setValMedioFaturacaoDiario(novoValMedioFaturacaoDiario);
        }
    }
    
    @Override
    public String toString() {
        return  super.toString()
                +"\nNúmero de Mesas Interiores: " + numMesasInteriores
                + "\nNúmero de Mesas na Esplanada: " + numMesasEsplanada
                + "\nValor de Licença Anual Por Mesa de Esplanada: " + valLicencaAnualMesaEsplanada
                + "\nValor Médio de Faturação Diário: " + valMedioFaturacaoDiario
                + "\n";
    }
}
