/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorstarthrive;

/**
 *
 * @author filip
 */
public abstract class Restaurante extends Restauracao {
    private int numDiasFuncionamentoAnual;
    
    /**
     * Construtor por omissão responsável por criar um objeto do tipo Restaurante.
     * 
     */
    public Restaurante() {
    }

    /**
     * Construtor responsável por criar um objeto do tipo Restaurante.
     * 
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
    public Restaurante(int numMedioClientesDiario, int numDiasFuncionamentoAnual, double custoEmpregados, int numEmpregados, double salarioMedioAnual, String categoriaEmpresa, String nomeEmpresa, String distritoEmpresa, Gps coordenadasGps) {
        super(custoEmpregados, numEmpregados, salarioMedioAnual, categoriaEmpresa, nomeEmpresa, distritoEmpresa, coordenadasGps,numMedioClientesDiario);
        this.numDiasFuncionamentoAnual = numDiasFuncionamentoAnual;
    }
    
    /**
     * Gets número de dias de funcionamento anual.
     * 
     * @return 
     */
    public int getNumDiasFuncionamentoAnual() {
        return numDiasFuncionamentoAnual;
    }
    
    /**
     * Sets número de dias de funcionamento anual.
     * 
     * @param numDiasFuncionamentoAnual 
     */
    public void setNumDiasFuncionamentoAnual(int numDiasFuncionamentoAnual) {
        this.numDiasFuncionamentoAnual = numDiasFuncionamentoAnual;
    }
    
    /**
     * Método responsável por determinar o valor da despesa anual.
     * 
     * @return 
     */
    public abstract double despesaAnual();
    
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
        System.out.printf("8)Número médio de clientes diário.\n"
                + "9)Número de dias de funcionamento anual.\n");
    }
    
    @Override
    public String toString() {
        return  super.toString()
                + "\nNúmero de Dias de Funcionamento Anual: " + numDiasFuncionamentoAnual
                + "\n";
    }
}
