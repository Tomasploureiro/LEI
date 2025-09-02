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
public abstract class Mercearia extends Empresa {
    
    /**
     * Construtor por omissão responsável por criar um objeto Mercearia.
     * 
     */
    public Mercearia() {
    }
    
    /**
     * Construtor responsável por criar um obejto do tipo Mercearia.
     * 
     * @param categoriaEmpresa
     * @param nomeEmpresa
     * @param distritoEmpresa
     * @param coordenadasGps 
     */
    public Mercearia(String categoriaEmpresa, String nomeEmpresa, String distritoEmpresa, Gps coordenadasGps) {
        super(categoriaEmpresa, nomeEmpresa, distritoEmpresa, coordenadasGps);
    }
    
    /**
     * Método responsável por determinar o valor de despesa anual.
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
     * Método responsável por imprimir informação de uma Mercearia.
     * 
     */
    public void informacaoEditarEmpresa() {   //Método responsável por imprimir os atributos possíveis de editar desta classe.
        super.informacaoEditarEmpresa();
    }
    
    /**
     * Método responsável por imprimir os atributos possíveis de editar desta classe.
     * 
     * @param selecionaOpcao 
     */
    public void executarEditarEmpresa(int selecionaOpcao) {
        super.executarEditarEmpresa(selecionaOpcao);
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
}
