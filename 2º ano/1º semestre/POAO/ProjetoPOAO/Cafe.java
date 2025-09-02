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
public final class Cafe extends Restauracao{
    private int numMedioCafesDiario;
    private double valMedioFaturacaoAnual;
    
    /**
     * Construtor por omissão responsável por criar um objeto do tipo Cafe.
     * 
     */
    public Cafe() {
    }

    /**
     * Costrutor responsável por criar um objeto do tipo Cafe.
     * 
     * @param numMedioClientesDiario
     * @param numMedioCafesDiario
     * @param valMedioFaturacaoAnual
     * @param custoEmpregados
     * @param numEmpregados
     * @param salarioMedioAnual
     * @param categoriaEmpresa
     * @param nomeEmpresa
     * @param distritoEmpresa
     * @param coordenadasGps 
     */
    public Cafe(int numMedioClientesDiario, int numMedioCafesDiario, double valMedioFaturacaoAnual, double custoEmpregados, int numEmpregados, double salarioMedioAnual, String categoriaEmpresa, String nomeEmpresa, String distritoEmpresa, Gps coordenadasGps) {
        super(custoEmpregados, numEmpregados, salarioMedioAnual, categoriaEmpresa, nomeEmpresa, distritoEmpresa, coordenadasGps,numMedioClientesDiario);
        this.numMedioCafesDiario = numMedioCafesDiario;
        this.valMedioFaturacaoAnual = valMedioFaturacaoAnual;
    }
    
    /**
     * Gets número médio de cafés diário.
     * 
     * @return 
     */
    public int getNumMedioCafesDiario() {
        return numMedioCafesDiario;
    }
    
    /**
     * Sets número médio de cafés diário.
     * 
     * @param numMedioCafesDiario 
     */
    public void setNumMedioCafesDiario(int numMedioCafesDiario) {
        this.numMedioCafesDiario = numMedioCafesDiario;
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
        double receitaAnualTotal = getNumMedioCafesDiario() * getValMedioFaturacaoAnual();
                
        return receitaAnualTotal;
    }
    
    @Override
    public int maiorCapacidade() {
        return 0;
    }
    
    /**
     * Método responsável por imprimir a informação de um Cafe.
     * 
     */
    public void informacaoEmpresa() {
        System.out.printf("----------Café----------");
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
                + "9)Número médio de cafés vendidos diariamente.\n"
                + "10)Valor médio de faturação anual.\n");
    }
    
    @Override
    public String toString() {
        return  super.toString()
                
                + "\nNúmero Médio de Cafés Vendidos Diáriamente: " + numMedioCafesDiario
                + "\nValor Médio de Faturação Anual Por Café: " + valMedioFaturacaoAnual
                + "\n";
    }
}
