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
public final class FastFood extends Restaurante {
    private int numMesasInteriores;
    private double valMedioFaturacaoDiario;
    private int numMedioClientesDiarioDrive;
    private double valMedioFaturacaoDiarioDrive;
    
    /**
     * Construtor por omissão responsável por criar um objeto do tipo FastFood.
     * 
     */
    public FastFood() {
    }
    
    /**
     * Construtor responsável por criar um objeto do tipo Fast-Food.
     * 
     * @param numMesasInteriores
     * @param valMedioFaturacaoDiario
     * @param numMedioClientesDiarioDrive
     * @param valMedioFaturacaoDiarioDrive
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
    public FastFood(int numMesasInteriores, double valMedioFaturacaoDiario, int numMedioClientesDiarioDrive, double valMedioFaturacaoDiarioDrive, int numMedioClientesDiario, int numDiasFuncionamentoAnual, double custoEmpregados, int numEmpregados, double salarioMedioAnual, String categoriaEmpresa, String nomeEmpresa, String distritoEmpresa, Gps coordenadasGps) {
        super(numMedioClientesDiario, numDiasFuncionamentoAnual, custoEmpregados, numEmpregados, salarioMedioAnual, categoriaEmpresa, nomeEmpresa, distritoEmpresa, coordenadasGps);
        this.numMesasInteriores = numMesasInteriores;
        this.valMedioFaturacaoDiario = valMedioFaturacaoDiario;
        this.numMedioClientesDiarioDrive = numMedioClientesDiarioDrive;
        this.valMedioFaturacaoDiarioDrive = valMedioFaturacaoDiarioDrive;
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
     * Gets valor médio de faturação diário.
     * 
     * @return 
     */
    public double getValMedioFaturacaoDiario() {
        return valMedioFaturacaoDiario;
    }
    
    /**
     * Sets valor médio de faturação diário.
     * 
     * @param valMedioFaturacaoDiario 
     */
    public void setValMedioFaturacaoDiario(double valMedioFaturacaoDiario) {
        this.valMedioFaturacaoDiario = valMedioFaturacaoDiario;
    }
    
    /**
     * Gets número médio de clientes diário no drive-thru.
     * 
     * @return 
     */
    public int getNumMedioClientesDiarioDrive() {
        return numMedioClientesDiarioDrive;
    }
    
    /**
     * Sets número médio de clientes diário no drive-thru.
     * 
     * @param numMedioClientesDiarioDrive 
     */
    public void setNumMedioClientesDiarioDrive(int numMedioClientesDiarioDrive) {
        this.numMedioClientesDiarioDrive = numMedioClientesDiarioDrive;
    }
    
    /**
     * Gets valor médio de faturação diário no drive-thru.
     * 
     * @return 
     */
    public double getValMedioFaturacaoDiarioDrive() {
        return valMedioFaturacaoDiarioDrive;
    }
    
    /**
     * Sets valor médio de faturação diário no drive-thru.
     * 
     * @param valMedioFaturacaoDiarioDrive 
     */
    public void setValMedioFaturacaoDiarioDrive(double valMedioFaturacaoDiarioDrive) {
        this.valMedioFaturacaoDiarioDrive = valMedioFaturacaoDiarioDrive;
    }
    
    @Override
    public double despesaAnual() {
        double despesaAnualTotal = getNumEmpregados() * getSalarioMedioAnual();
        
        return despesaAnualTotal;
    }
    
    @Override
    public double receitaAnual() {
        double receitaAnualTotal = ((getNumMesasInteriores() * getValMedioFaturacaoDiario()) + (getNumMedioClientesDiarioDrive() * getValMedioFaturacaoDiarioDrive())) * getNumDiasFuncionamentoAnual(); 
                
        return receitaAnualTotal;
    }
    
    @Override
    public int maiorCapacidade() {
        return numMesasInteriores;
    }
    
    /**
     * Método responsável por imprimir informação de um restaurante Fast-Food.
     * 
     */
    public void informacaoEmpresa() {
        System.out.printf("----------Restaurante Fast-Food----------");
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
                + "11)Valor médio de faturação diário.\n"
                + "12)Número médio de clientes diários no Drive-Thru.\n"
                + "13)Valor médio de faturação diário no Drive-Thru.\n");
    }
    
    /**
     * Método responsável pela edição dos atributos desta classe.
     * 
     * @param selecionaOpcao 
     */
    public void executarEditarEmpresa(int selecionaOpcao) {
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
        
        else if (selecionaOpcao == 12) {
            int novoNumMedioClientesDiarioDrive;
            
            System.out.printf("Introduza o novo número médio de clientes diários no Drive-Thru: ");
            Scanner sc = new Scanner(System.in);
            
            while (!sc.hasNextInt()) {
                System.out.printf("Novo número médio de clientes diários no Drive-Thru inválido! Por favor tente novamente: ");
                sc = new Scanner(System.in);
            }
            
            novoNumMedioClientesDiarioDrive = sc.nextInt();
            
            setNumMedioClientesDiarioDrive(novoNumMedioClientesDiarioDrive);
        }
        
        else if (selecionaOpcao == 13) {
            double novoValMedioFaturacaoDiarioDrive;
            
            System.out.printf("Introduza o novo valor médio de faturação diário no Drive-Thru: ");
            Scanner sc = new Scanner(System.in);
            
            while (!sc.hasNextDouble()) {
                System.out.printf("Novo valor médio de faturação diário no Drive-Thru inválido! Por favor tente novamente: ");
                sc = new Scanner(System.in);
            }
            
            novoValMedioFaturacaoDiarioDrive = sc.nextDouble();
            
            setValMedioFaturacaoDiarioDrive(novoValMedioFaturacaoDiarioDrive);
        }
    }
    
    @Override
    public String toString() {
        return  super.toString()
                +"\nNúmero de Mesas Interiores: " + numMesasInteriores
                + "\nValor Médio de Faturação Diário: " + valMedioFaturacaoDiario
                + "\nNúmero Médio de Clientes Diário no Drive-Thru: " + numMedioClientesDiarioDrive
                + "\nValor Médio de Faturação Diário no Drive-Thru: " + valMedioFaturacaoDiarioDrive
                + "\n";
    }
}
