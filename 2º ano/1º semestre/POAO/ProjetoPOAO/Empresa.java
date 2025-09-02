/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorstarthrive;

import java.io.Serializable;
import java.util.Scanner;

/**
 *
 * @author filip
 */
public abstract class Empresa implements Serializable {
    private String categoriaEmpresa;
    private String nomeEmpresa;
    private String distritoEmpresa;
    private Gps coordenadasGps;
    
    /**
     * Construtor por omissão responsável por criar um objeto do tipo Empresa.
     * 
     */
    public Empresa() {
    }
    
    /**
     * Construtor responsável por criar um objeto do tipo Empresa.
     * 
     * @param categoriaEmpresa
     * @param nomeEmpresa
     * @param distritoEmpresa
     * @param coordenadasGps 
     */
    public Empresa(String categoriaEmpresa, String nomeEmpresa, String distritoEmpresa, Gps coordenadasGps) {
        this.categoriaEmpresa = categoriaEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.distritoEmpresa = distritoEmpresa;
        this.coordenadasGps = coordenadasGps;
    }
    
    /**
     * Gets categoria da empresa.
     * 
     * @return 
     */
    public String getCategoriaEmpresa() {
        return categoriaEmpresa;
    }
    
    /**
     * Sets categoria da empresa.
     * 
     * @param categoriaEmpresa 
     */
    public void setCategoriaEmpresa(String categoriaEmpresa) {
        this.categoriaEmpresa = categoriaEmpresa;
    }
    
    /**
     * Gets nome da empresa.
     * 
     * @return 
     */
    public String getNomeEmpresa() {
        return nomeEmpresa;
    }
    
    /**
     * Sets nome da empresa.
     * 
     * @param nomeEmpresa 
     */
    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }
    
    /**
     * Gets distrito da empresa.
     * 
     * @return 
     */
    public String getDistritoEmpresa() {
        return distritoEmpresa;
    }
    
    /**
     * Sets distrito da empresa.
     * 
     * @param distritoEmpresa 
     */
    public void setDistritoEmpresa(String distritoEmpresa) {
        this.distritoEmpresa = distritoEmpresa;
    }
    
    /**
     * Gets coordenadas GPS.
     * 
     * @return 
     */
    public Gps getCoordenadasGps() {
        return coordenadasGps;
    }
    
    /**
     * Sets coordenadas GPS.
     * 
     * @param coordenadasGps 
     */
    public void setCoordenadasGps(Gps coordenadasGps) {
        this.coordenadasGps = coordenadasGps;
    }
    
    /**
     * Método responsável por determinar o valor da receita anual.
     * 
     * @return 
     */
    public abstract double receitaAnual();
    
    /**
     * Método responsável por determinar o valor da despesa anual.
     * 
     * @return 
     */
    public abstract double despesaAnual();
    
    /**
     * Método responsável por determinar o valor do lucro anual.
     * 
     * @return 
     */
    public double lucroAnual() {   //Método responsável por determinar e retornar o valor do lucro anual de cada empresa. Não é abstrato uma vez que o lucro anual é calculado de igual forma para cada tipo de empresa.
        return receitaAnual() - despesaAnual();
    }
    
    /**
     * Método responsável por imprimir informação da empresa com maior receita anual.
     * 
     */
    public void maiorReceita() {
        System.out.printf("\nNome da empresa: " + getNomeEmpresa()
            + "\nValor da receita anual: " + receitaAnual()
            + "\n");
    }
    
    /**
     * Método responsável por imprimir informação da empresa com menor despesa anual.
     * 
     */
    public void menorDespesa() {
        System.out.printf("\nNome da empresa: " + getNomeEmpresa()
            + "\nValor da despesa anual: " + despesaAnual()
            + "\n");
    }
    
    /**
     * Método responsável por imprimir informação da empresa com maior lucro anual.
     * 
     */
    public void maiorLucro() {
        System.out.printf("\nNome da empresa: " + getNomeEmpresa()
            + "\nValor do lucro anual: " + lucroAnual()
            + "\n");
    }
    
    /**
     * Método responsável por determinar a capacidade máxima de clientes por dia.
     * 
     * @return 
     */
    public abstract int maiorCapacidade();
    
    /**
     * Método responsável por imprimir a informação de uma empresa.
     * 
     */
    public void informacaoEmpresa() {
        System.out.printf("\nNome da Empresa: " + getNomeEmpresa()
            + "\nTipo de Empresa: " + getCategoriaEmpresa()
            + "\nDistrito da Empresa: " + getDistritoEmpresa()
            + "\nCoordenadas GPS: " + coordenadasGps.toString());   //Uma vez que no enunciado é referido para imprimir PELO MENOS a informação lá referida, achei pertinente imprimir também as coordenadas GPS da empresa.
    }
    
    /**
     * Método responsável por imprimir os atributos possíveis de editar de uma empresa.
     * 
     */
    public void informacaoEditarEmpresa() {
        System.out.printf("Introduza o valor a alterar:\n"
                + "1)Categoria da empresa.\n"
                + "2)Nome da empresa.\n"
                + "3)Distrito da empresa.\n"
                + "4)Coordenadas GPS.\n");
    }
    
    /**
     * Método responsável por editar os atributos desta classe.
     * 
     * @param selecionaOpcao 
     */
    public void executarEditarEmpresa(int selecionaOpcao) {
        if (selecionaOpcao == 1) {
            String novaCategoriaEmpresa;
            
            System.out.printf("Introduza a nova categoria da empresa: ");
            Scanner sc = new Scanner(System.in);
            novaCategoriaEmpresa = sc.nextLine();
            
            while (novaCategoriaEmpresa.equals("Restauração") == false && novaCategoriaEmpresa.equals("Mercearia") == false) {   //O input é repetido caso não seja introduzida uma categoria válida.
                System.out.printf("Nova categoria inválida! Por favor tente novamente: ");
                sc = new Scanner(System.in);
                novaCategoriaEmpresa = sc.nextLine();
            }
            
            setCategoriaEmpresa(novaCategoriaEmpresa);
        }
        
        else if (selecionaOpcao == 2) {
            String novoNomeEmpresa;
            
            System.out.printf("Introduza o novo nome da empresa: ");
            Scanner sc = new Scanner(System.in);
            novoNomeEmpresa = sc.nextLine();
            
            setNomeEmpresa(novoNomeEmpresa);
        }
        
        else if (selecionaOpcao == 3) {
            String novoDistritoEmpresa;
            
            System.out.printf("Introduza o novo distrito da empresa: ");
            Scanner sc = new Scanner(System.in);
            novoDistritoEmpresa = sc.nextLine();
            
            setDistritoEmpresa(novoDistritoEmpresa);
        }
        
        else if (selecionaOpcao == 4) {
            double novaCoordenadaLatitude;
            double novaCoordenadaLongitude;
            
            System.out.printf("Introduza o novo valor da latitude: ");
            Scanner sc = new Scanner(System.in);
            
            while (!sc.hasNextDouble()) {
                System.out.printf("Novo valor de latitude inválido! Por favor tente novamente: ");
                sc = new Scanner(System.in);
            }
            
            novaCoordenadaLatitude = sc.nextDouble();
            
            System.out.printf("Introduza o novo valor da longitude: ");
            Scanner sc1 = new Scanner(System.in);
            
            while (!sc1.hasNextDouble()) {
                System.out.printf("Novo valor de longitude inválido! Por favor tente novamente: ");
                sc1 = new Scanner(System.in);
            }
            
            novaCoordenadaLongitude = sc1.nextDouble();
            
            Gps g0 = new Gps(novaCoordenadaLatitude, novaCoordenadaLongitude);
            
            setCoordenadasGps(g0);
        }
    }

    @Override
    public String toString() {
        return 
                 "\nCoordenadas da Empresa: " + coordenadasGps.toString()
                + "\n";
    }
}
