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
public final class Pastelaria extends Restauracao {
    private int numMedioBolosDiario;
    private double valMedioFaturacaoAnual;
    
    /**
     * Construtor por omissão responsável por criar um objeto do tipo Pastelaria.
     * 
     */
    public Pastelaria() {
    }
    
    /**
     * Construtor responsável por criar um objeto do tipo Pastelaria.
     * 
     * @param numMedioClientesDiario
     * @param numMedioBolosDiario
     * @param valMedioFaturacaoAnual
     * @param custoEmpregados
     * @param numEmpregados
     * @param salarioMedioAnual
     * @param categoriaEmpresa
     * @param nomeEmpresa
     * @param distritoEmpresa
     * @param coordenadasGps 
     */
    public Pastelaria(int numMedioClientesDiario, int numMedioBolosDiario, double valMedioFaturacaoAnual, double custoEmpregados, int numEmpregados, double salarioMedioAnual, String categoriaEmpresa, String nomeEmpresa, String distritoEmpresa, Gps coordenadasGps) {
        super(custoEmpregados, numEmpregados, salarioMedioAnual, categoriaEmpresa, nomeEmpresa, distritoEmpresa, coordenadasGps,numMedioClientesDiario);
        this.numMedioBolosDiario = numMedioBolosDiario;
        this.valMedioFaturacaoAnual = valMedioFaturacaoAnual;
    }
    
    /**
     * Gets número médio de bolos diário.
     * 
     * @return 
     */
    public int getNumMedioBolosDiario() {
        return numMedioBolosDiario;
    }
    
    /**
     * Sets número médio de bolos diário.
     * 
     * @param numMedioBolosDiario 
     */
    public void setNumMedioBolosDiario(int numMedioBolosDiario) {
        this.numMedioBolosDiario = numMedioBolosDiario;
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
    
    @Override
    public double receitaAnual() {
        double receitaAnualTotal = getNumMedioBolosDiario() * getValMedioFaturacaoAnual();
                
        return receitaAnualTotal;
    }
    
    @Override
    public int maiorCapacidade() {
        return 0;
    }
    
    /**
     * Método responsável por imprimir a informação de uma Pastelaria.
     * 
     */
    public void informacaoEmpresa() {
        System.out.printf("----------Pastelaria----------");
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
        System.out.printf("8)Número médio de clientes diário.\n"
                + "9)Número médio de bolos vendidos diariamente.\n"
                + "10)Valor médio de faturação anual.\n");
    }
    
    @Override
    public String toString() {
        return  super.toString()
                + "\nNúmero Médio de Bolos Vendidos Diariamente: " + numMedioBolosDiario
                + "\nValor Médio de Faturação Anual Por Bolo: " + valMedioFaturacaoAnual
                + "\n";
    }
}
