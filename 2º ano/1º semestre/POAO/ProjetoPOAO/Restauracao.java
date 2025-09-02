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
public abstract class Restauracao extends Empresa {
    private int numMedioClientesDiario;
    private double custoEmpregados;
    private int numEmpregados;
    private double salarioMedioAnual;
    
    /**
     * Construtor por omissão responsável por criar um objeto do tipo Restauracao.
     * 
     */
    public Restauracao() {
    }
    
    /**
     * Construtor responsável por criar um objeto do tipo Restauracao.
     * 
     * @param custoEmpregados
     * @param numEmpregados
     * @param salarioMedioAnual
     * @param categoriaEmpresa
     * @param nomeEmpresa
     * @param distritoEmpresa
     * @param coordenadasGps
     * @param numMedioClientesDiario 
     */
    public Restauracao(double custoEmpregados, int numEmpregados, double salarioMedioAnual, String categoriaEmpresa, String nomeEmpresa, String distritoEmpresa, Gps coordenadasGps,int numMedioClientesDiario) {
        super(categoriaEmpresa, nomeEmpresa, distritoEmpresa, coordenadasGps);
        this.numMedioClientesDiario = numMedioClientesDiario;
        this.custoEmpregados = custoEmpregados;
        this.numEmpregados = numEmpregados;
        this.salarioMedioAnual = salarioMedioAnual;
    }
    
    /**
     * Gets número médio de clientes diário.
     * 
     * @return 
     */
    public int getNumMedioClientesDiario() {
        return numMedioClientesDiario;
    }

    /**
     * Sets número médio de clientes diário.
     * 
     * @param numMedioClientesDiario 
     */
    public void setNumMedioClientesDiario(int numMedioClientesDiario) {
        this.numMedioClientesDiario = numMedioClientesDiario;
    }
    
    /**
     * Gets custo de empregados.
     * 
     * @return 
     */
    public double getCustoEmpregados() {
        return custoEmpregados;
    }
    
    /**
     * Sets custo de empregados.
     * 
     * @param custoEmpregados 
     */
    public void setCustoEmpregados(double custoEmpregados) {
        this.custoEmpregados = custoEmpregados;
    }
    
    /**
     * Gets número de empregados.
     * 
     * @return 
     */
    public int getNumEmpregados() {
        return numEmpregados;
    }
    
    /**
     * Sets número de empregados.
     * 
     * @param numEmpregados 
     */
    public void setNumEmpregados(int numEmpregados) {
        this.numEmpregados = numEmpregados;
    }
    
    /**
     * Gets salário médio anual.
     * 
     * @return 
     */
    public double getSalarioMedioAnual() {
        return salarioMedioAnual;
    }
    
    /**
     * Sets salário médio anual.
     * 
     * @param salarioMedioAnual 
     */
    public void setSalarioMedioAnual(double salarioMedioAnual) {
        this.salarioMedioAnual = salarioMedioAnual;
    }
    
    /**
     * Método responsável por determinar o valor da despesa anual.
     * 
     * @return 
     */
    public double despesaAnual() {
        double despesaAnualTotal = getNumEmpregados() * getSalarioMedioAnual();
        
        return despesaAnualTotal;
    }
    
    /**
     * Método responsável por determinar o valor da receita anual.
     * 
     * @return 
     */
    public abstract double receitaAnual();
    
    /**
     * Método responsável por imprimir os atributos possíveis de editar desta classe.
     * 
     */
    public void informacaoEditarEmpresa() {
        super.informacaoEditarEmpresa();
        System.out.printf("5)Custo dos empregados.\n"
                + "6)Número de empregados.\n"
                + "7)Salário médio anual.\n");
    }
    
    /**
     * Método responsável por editar os atributos desta classe.
     * 
     * @param selecionaOpcao 
     */
    public void executarEditarEmpresa(int selecionaOpcao) {
        super.executarEditarEmpresa(selecionaOpcao);
        
        if (selecionaOpcao == 5) {
            double novoCustoEmpregados;
            
            System.out.printf("Introduza o novo custo dos empregados: ");
            Scanner sc = new Scanner(System.in);
            
            while (!sc.hasNextDouble()) {
                System.out.printf("Novo valor de custo dos empregados inválido! Por favor tente novamente: ");
                sc = new Scanner(System.in);
            }
            
            novoCustoEmpregados = sc.nextDouble();
            
            setCustoEmpregados(novoCustoEmpregados);
        }
        
        else if (selecionaOpcao == 6) {
            int novoNumEmpregados;
            
            System.out.printf("Introduza o novo número de empregados: ");
            Scanner sc = new Scanner(System.in);
            
            while (!sc.hasNextInt()) {
                System.out.printf("Novo número de empregados inválido! Por favor tente novamente: ");
                sc = new Scanner(System.in);
            }
            
            novoNumEmpregados = sc.nextInt();
            
            setNumEmpregados(novoNumEmpregados);
        }
        
        else if (selecionaOpcao == 7) {
            double novoSalarioMedioAnual;
            
            System.out.printf("Introduza o novo valor de salário médio anual: ");
            Scanner sc = new Scanner(System.in);
            
            while (!sc.hasNextDouble()) {
                System.out.printf("Novo valor de salário médio anual inválido! Por favor tente novamente: ");
                sc = new Scanner(System.in);
            }
            
            novoSalarioMedioAnual = sc.nextDouble();
            
            setSalarioMedioAnual(novoSalarioMedioAnual);
        }
    }
    
    @Override
    public String toString() {
        return  super.toString()
                +"\nnumMedioClientes: " + numMedioClientesDiario
                +"\nCusto dos Empregados: " + custoEmpregados
                + "\nNúmero de Empregados: " + numEmpregados
                + "\nSalario Médio Anual: " + salarioMedioAnual
                + "\n";
    }
}
