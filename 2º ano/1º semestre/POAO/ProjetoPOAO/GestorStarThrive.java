/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorstarthrive;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author filip
 */
public class GestorStarThrive {
    
    /**
     * Método responsável por criar empresa do tipo café.
     * 
     * @return 
     */
    public static Cafe criarCafe() {   //Método responsável por retornar um novo café.
        String categoriaEmpresa = "Restauração";
        String nomeEmpresa;
        String distritoEmpresa;
        double coordenadasLatitude;
        double coordenadasLongitude;
        double custoEmpregados;
        int numEmpregados;
        double salarioMedioAnual;
        int numMedioClientesDiario;
        int numMedioCafesDiario;
        double valMedioFaturacaoAnual;

        System.out.printf("Introduza o nome da empresa:");
        Scanner sc2 = new Scanner(System.in);

        while(!sc2.hasNextLine()) {
            System.out.printf("Nome inválido! Por favor tente novamente: ");
            sc2 = new Scanner(System.in);
        }

        nomeEmpresa = sc2.nextLine();

        System.out.printf("Introduza o distrito da empresa: ");
        Scanner sc3 = new Scanner(System.in);

        while(!sc3.hasNextLine()) {
            System.out.printf("Distrito inválido! Por favor tente novamente: ");
            sc3 = new Scanner(System.in);
        }

        distritoEmpresa = sc3.nextLine();

        System.out.printf("Introduza o valor da latitude das coordenadas GPS: ");
        Scanner sc4 = new Scanner(System.in);

        while (!sc4.hasNextDouble()) {
            System.out.printf("Valor de latitude inválido! Por favor tente novamente: ");
            sc4 = new Scanner(System.in);
        }

        coordenadasLatitude = sc4.nextDouble();

        System.out.printf("Introduza o valor da longitude das coordenadas GPS: ");
        Scanner sc5 = new Scanner(System.in);

        while (!sc5.hasNextDouble()) {
            System.out.printf("Valor de longitude inválido! Por favor tente novamente: ");
            sc5 = new Scanner(System.in);
        }

        coordenadasLongitude = sc5.nextDouble();

        Gps g0 = new Gps(coordenadasLatitude, coordenadasLongitude);

        System.out.printf("Introduza o valor do custo dos empregados: ");
        Scanner sc6 = new Scanner(System.in);

        while (!sc6.hasNextDouble()) {
            System.out.printf("Valor de custo dos empregados inválido! Por favor tente novamente: ");
            sc6 = new Scanner(System.in);
        }

        custoEmpregados = sc6.nextDouble();

        System.out.printf("Introduza o número de empregados do estabelecimento: ");
        Scanner sc7 = new Scanner(System.in);

        while (!sc7.hasNextInt()) {
            System.out.printf("Valor de número de empregados inválido! Por favor tente novamente: ");
            sc7 = new Scanner(System.in);
        }

        numEmpregados = sc7.nextInt();

        System.out.printf("Introduza o valor do salário médio anual: ");
        Scanner sc8 = new Scanner(System.in);

        while (!sc8.hasNextDouble()) {
            System.out.printf("Valor de salário médio anual inválido! Por favor tente novamente: ");
            sc8 = new Scanner(System.in);
        }

        salarioMedioAnual = sc8.nextDouble();

        System.out.printf("Introduza o número médio de clientes diário: ");
        Scanner sc9 = new Scanner(System.in);

        while (!sc9.hasNextInt()) {
            System.out.printf("Valor de número médio de clientes diário inválido! Por favor tente novamente: ");
            sc9 = new Scanner(System.in);
        }

        numMedioClientesDiario = sc9.nextInt();

        System.out.printf("Introduza o número médio de cafés vendidos diáriamente: ");
        Scanner sc10 = new Scanner(System.in);

        while (!sc10.hasNextInt()) {
            System.out.printf("Valor de número médio de cafés vendidos diariamente inválido! Por favor tente novamente: ");
            sc10 = new Scanner(System.in);
        }

        numMedioCafesDiario = sc10.nextInt();

        System.out.printf("Introduza o valor médio de faturação anual: ");
        Scanner sc11 = new Scanner(System.in);

        while (!sc11.hasNextDouble()) {
            System.out.printf("Valor médio de faturação anual inválido! Por favor tente novamente: ");
            sc11 = new Scanner(System.in);
        }

        valMedioFaturacaoAnual = sc11.nextDouble();

        Cafe c0 = new Cafe(numMedioClientesDiario, numMedioCafesDiario, valMedioFaturacaoAnual, custoEmpregados, numEmpregados, salarioMedioAnual, categoriaEmpresa, nomeEmpresa, distritoEmpresa, g0);

        return c0;
    }
    
    /**
     * Método responsável por criar uma empresa do tipo pastelaria.
     * 
     * @return 
     */
    public static Pastelaria criarPastelaria() {   //Método responsável por retornar uma nova pastelaria.
        String categoriaEmpresa = "Restauração";
        String nomeEmpresa;
        String distritoEmpresa;
        double coordenadasLatitude;
        double coordenadasLongitude;
        double custoEmpregados;
        int numEmpregados;
        double salarioMedioAnual;
        int numMedioClientesDiario;
        int numMedioBolosDiario;
        double valMedioFaturacaoAnual;

        System.out.printf("Introduza o nome da empresa:");
        Scanner sc2 = new Scanner(System.in);

        while(!sc2.hasNextLine()) {
            System.out.printf("Nome inválido! Por favor tente novamente: ");
            sc2 = new Scanner(System.in);
        }

        nomeEmpresa = sc2.nextLine();

        System.out.printf("Introduza o distrito da empresa: ");
        Scanner sc3 = new Scanner(System.in);

        while(!sc3.hasNextLine()) {
            System.out.printf("Distrito inválido! Por favor tente novamente: ");
            sc3 = new Scanner(System.in);
        }

        distritoEmpresa = sc3.nextLine();

        System.out.printf("Introduza o valor da latitude das coordenadas GPS: ");
        Scanner sc4 = new Scanner(System.in);

        while (!sc4.hasNextDouble()) {
            System.out.printf("Valor de latitude inválido! Por favor tente novamente: ");
            sc4 = new Scanner(System.in);
        }

        coordenadasLatitude = sc4.nextDouble();

        System.out.printf("Introduza o valor da longitude das coordenadas GPS: ");
        Scanner sc5 = new Scanner(System.in);

        while (!sc5.hasNextDouble()) {
            System.out.printf("Valor de longitude inválido! Por favor tente novamente: ");
            sc5 = new Scanner(System.in);
        }

        coordenadasLongitude = sc5.nextDouble();

        Gps g0 = new Gps(coordenadasLatitude, coordenadasLongitude);

        System.out.printf("Introduza o valor do custo dos empregados: ");
        Scanner sc6 = new Scanner(System.in);

        while (!sc6.hasNextDouble()) {
            System.out.printf("Valor de custo dos empregados inválido! Por favor tente novamente: ");
            sc6 = new Scanner(System.in);
        }

        custoEmpregados = sc6.nextDouble();

        System.out.printf("Introduza o número de empregados do estabelecimento: ");
        Scanner sc7 = new Scanner(System.in);

        while (!sc7.hasNextInt()) {
            System.out.printf("Valor de número de empregados inválido! Por favor tente novamente: ");
            sc7 = new Scanner(System.in);
        }

        numEmpregados = sc7.nextInt();

        System.out.printf("Introduza o valor do salário médio anual: ");
        Scanner sc8 = new Scanner(System.in);

        while (!sc8.hasNextDouble()) {
            System.out.printf("Valor de salário médio anual inválido! Por favor tente novamente: ");
            sc8 = new Scanner(System.in);
        }

        salarioMedioAnual = sc8.nextDouble();

        System.out.printf("Introduza o número médio de clientes diário: ");
        Scanner sc9 = new Scanner(System.in);

        while (!sc9.hasNextInt()) {
            System.out.printf("Valor de número médio de clientes diário inválido! Por favor tente novamente: ");
            sc9 = new Scanner(System.in);
        }

        numMedioClientesDiario = sc9.nextInt();

        System.out.printf("Introduza o número médio de bolos vendidos diáriamente: ");
        Scanner sc10 = new Scanner(System.in);

        while (!sc10.hasNextInt()) {
            System.out.printf("Valor de número médio de bolos vendidos diariamente inválido! Por favor tente novamente: ");
            sc10 = new Scanner(System.in);
        }

        numMedioBolosDiario = sc10.nextInt();

        System.out.printf("Introduza o valor médio de faturação anual: ");
        Scanner sc11 = new Scanner(System.in);

        while (!sc11.hasNextDouble()) {
            System.out.printf("Valor médio de faturação anual inválido! Por favor tente novamente: ");
            sc11 = new Scanner(System.in);
        }

        valMedioFaturacaoAnual = sc11.nextDouble();

        Pastelaria p0 = new Pastelaria(numMedioClientesDiario, numMedioBolosDiario, valMedioFaturacaoAnual, custoEmpregados, numEmpregados, salarioMedioAnual, categoriaEmpresa, nomeEmpresa, distritoEmpresa, g0);

        return p0;
    }
    
    /**
     * Método responsável por criar uma empresa do tipo restaurante local.
     * 
     * @return 
     */
    public static Local criarRestauranteLocal() {   //Método responsável por retornar um novo restaurante local.
        String categoriaEmpresa = "Restauração";
        String nomeEmpresa;
        String distritoEmpresa;
        double coordenadasLatitude;
        double coordenadasLongitude;
        double custoEmpregados;
        int numEmpregados;
        double salarioMedioAnual;
        int numMedioClientesDiario;
        int numDiasFuncionamentoAnual;
        int numMesasInteriores;
        int numMesasEsplanada;
        double valLicencaAnualMesaEsplanada;
        double valMedioFaturacaoDiario;

        System.out.printf("Introduza o nome da empresa:");
        Scanner sc2 = new Scanner(System.in);

        while(!sc2.hasNextLine()) {
            System.out.printf("Nome inválido! Por favor tente novamente: ");
            sc2 = new Scanner(System.in);
        }

        nomeEmpresa = sc2.nextLine();

        System.out.printf("Introduza o distrito da empresa: ");
        Scanner sc3 = new Scanner(System.in);

        while(!sc3.hasNextLine()) {
            System.out.printf("Distrito inválido! Por favor tente novamente: ");
            sc3 = new Scanner(System.in);
        }

        distritoEmpresa = sc3.nextLine();

        System.out.printf("Introduza o valor da latitude das coordenadas GPS: ");
        Scanner sc4 = new Scanner(System.in);

        while (!sc4.hasNextDouble()) {
            System.out.printf("Valor de latitude inválido! Por favor tente novamente: ");
            sc4 = new Scanner(System.in);
        }

        coordenadasLatitude = sc4.nextDouble();

        System.out.printf("Introduza o valor da longitude das coordenadas GPS: ");
        Scanner sc5 = new Scanner(System.in);

        while (!sc5.hasNextDouble()) {
            System.out.printf("Valor de longitude inválido! Por favor tente novamente: ");
            sc5 = new Scanner(System.in);
        }

        coordenadasLongitude = sc5.nextDouble();

        Gps g0 = new Gps(coordenadasLatitude, coordenadasLongitude);

        System.out.printf("Introduza o valor do custo dos empregados: ");
        Scanner sc6 = new Scanner(System.in);

        while (!sc6.hasNextDouble()) {
            System.out.printf("Valor de custo dos empregados inválido! Por favor tente novamente: ");
            sc6 = new Scanner(System.in);
        }

        custoEmpregados = sc6.nextDouble();

        System.out.printf("Introduza o número de empregados do estabelecimento: ");
        Scanner sc7 = new Scanner(System.in);

        while (!sc7.hasNextInt()) {
            System.out.printf("Valor de número de empregados inválido! Por favor tente novamente: ");
            sc7 = new Scanner(System.in);
        }

        numEmpregados = sc7.nextInt();

        System.out.printf("Introduza o valor do salário médio anual: ");
        Scanner sc8 = new Scanner(System.in);

        while (!sc8.hasNextDouble()) {
            System.out.printf("Valor de salário médio anual inválido! Por favor tente novamente: ");
            sc8 = new Scanner(System.in);
        }

        salarioMedioAnual = sc8.nextDouble();

        System.out.printf("Introduza o número médio de clientes diário: ");
        Scanner sc9 = new Scanner(System.in);

        while (!sc9.hasNextInt()) {
            System.out.printf("Valor de número médio de clientes diário inválido! Por favor tente novamente: ");
            sc9 = new Scanner(System.in);
        }

        numMedioClientesDiario = sc9.nextInt();

        System.out.printf("Introduza o número de dias de funcionamento anual: ");
        Scanner sc10 = new Scanner(System.in);

        while (!sc10.hasNextInt()) {
            System.out.printf("Valor de número de dias de funcionamento anual inválido! Por favor tente novamente: ");
            sc10 = new Scanner(System.in);
        }

        numDiasFuncionamentoAnual = sc10.nextInt();

        while (numDiasFuncionamentoAnual < 0 || numDiasFuncionamentoAnual > 365) {
            System.out.printf("Valor de número de dias de funcionamento anual inválido! Por favor tente novamente: ");
            sc10 = new Scanner(System.in);
            numDiasFuncionamentoAnual = sc10.nextInt();
        }

        System.out.printf("Introduza o número de mesas interiores do estabelecimento");
        Scanner sc11 = new Scanner(System.in);

        while (!sc11.hasNextInt()) {
            System.out.printf("Valor de número de mesas interiores inválido! Por favor tente novamente: ");
            sc11 = new Scanner(System.in);
        }

        numMesasInteriores = sc11.nextInt();

        System.out.printf("Introduza o número de mesas de esplanada do estabelecimento");
        Scanner sc12 = new Scanner(System.in);

        while (!sc12.hasNextInt()) {
            System.out.printf("Valor de número de mesas de esplanada inválido! Por favor tente novamente: ");
            sc12 = new Scanner(System.in);
        }

        numMesasEsplanada = sc12.nextInt();

        System.out.printf("Introduza o valor de licença anual por mesa de esplanada");
        Scanner sc13 = new Scanner(System.in);

        while(!sc13.hasNextDouble()) {
            System.out.printf("Valor de licença anual por mesa de esplanada inválido! Por favor tente novamente: ");
            sc13 = new Scanner(System.in);
        }

        valLicencaAnualMesaEsplanada = sc13.nextDouble();

        System.out.printf("Introduza o valor medio de faturação diário: ");
        Scanner sc14 = new Scanner(System.in);

        while(!sc14.hasNextDouble()) {
            System.out.printf("Valor medio de faturação diário inválido! Por favor tente novamente: ");
            sc14 = new Scanner(System.in);
        }

        valMedioFaturacaoDiario = sc14.nextDouble();

        Local l0 = new Local(numMesasInteriores, numMesasEsplanada, valLicencaAnualMesaEsplanada, valMedioFaturacaoDiario, numMedioClientesDiario, numDiasFuncionamentoAnual, custoEmpregados, numEmpregados, salarioMedioAnual, categoriaEmpresa, nomeEmpresa, distritoEmpresa, g0);

        return l0;
    }
    
    /**
     * Método responsável por criar empresa do tipo restaurante Fast-Food.
     * 
     * @return 
     */
    public static FastFood criarRestauranteFastFood() {   //Método responsável por retornar um restaurante Fast-Food.
        String categoriaEmpresa = "Restauração";
        String nomeEmpresa;
        String distritoEmpresa;
        double coordenadasLatitude;
        double coordenadasLongitude;
        double custoEmpregados;
        int numEmpregados;
        double salarioMedioAnual;
        int numMedioClientesDiario;
        int numDiasFuncionamentoAnual;
        int numMesasInteriores;
        double valMedioFaturacaoDiario;
        int numMedioClientesDiarioDrive;
        double valMedioFaturacaoDiarioDrive;

        System.out.printf("Introduza o nome da empresa:");
        Scanner sc2 = new Scanner(System.in);

        while(!sc2.hasNextLine()) {
            System.out.printf("Nome inválido! Por favor tente novamente: ");
            sc2 = new Scanner(System.in);
        }

        nomeEmpresa = sc2.nextLine();

        System.out.printf("Introduza o distrito da empresa: ");
        Scanner sc3 = new Scanner(System.in);

        while(!sc3.hasNextLine()) {
            System.out.printf("Distrito inválido! Por favor tente novamente: ");
            sc3 = new Scanner(System.in);
        }

        distritoEmpresa = sc3.nextLine();

        System.out.printf("Introduza o valor da latitude das coordenadas GPS: ");
        Scanner sc4 = new Scanner(System.in);

        while (!sc4.hasNextDouble()) {
            System.out.printf("Valor de latitude inválido! Por favor tente novamente: ");
            sc4 = new Scanner(System.in);
        }

        coordenadasLatitude = sc4.nextDouble();

        System.out.printf("Introduza o valor da longitude das coordenadas GPS: ");
        Scanner sc5 = new Scanner(System.in);

        while (!sc5.hasNextDouble()) {
            System.out.printf("Valor de longitude inválido! Por favor tente novamente: ");
            sc5 = new Scanner(System.in);
        }

        coordenadasLongitude = sc5.nextDouble();

        Gps g0 = new Gps(coordenadasLatitude, coordenadasLongitude);

        System.out.printf("Introduza o valor do custo dos empregados: ");
        Scanner sc6 = new Scanner(System.in);

        while (!sc6.hasNextDouble()) {
            System.out.printf("Valor de custo dos empregados inválido! Por favor tente novamente: ");
            sc6 = new Scanner(System.in);
        }

        custoEmpregados = sc6.nextDouble();

        System.out.printf("Introduza o número de empregados do estabelecimento: ");
        Scanner sc7 = new Scanner(System.in);

        while (!sc7.hasNextInt()) {
            System.out.printf("Valor de número de empregados inválido! Por favor tente novamente: ");
            sc7 = new Scanner(System.in);
        }

        numEmpregados = sc7.nextInt();

        System.out.printf("Introduza o valor do salário médio anual: ");
        Scanner sc8 = new Scanner(System.in);

        while (!sc8.hasNextDouble()) {
            System.out.printf("Valor de salário médio anual inválido! Por favor tente novamente: ");
            sc8 = new Scanner(System.in);
        }

        salarioMedioAnual = sc8.nextDouble();

        System.out.printf("Introduza o número médio de clientes diário: ");
        Scanner sc9 = new Scanner(System.in);

        while (!sc9.hasNextInt()) {
            System.out.printf("Valor de número médio de clientes diário inválido! Por favor tente novamente: ");
            sc9 = new Scanner(System.in);
        }

        numMedioClientesDiario = sc9.nextInt();

        System.out.printf("Introduza o número de dias de funcionamento anual: ");
        Scanner sc10 = new Scanner(System.in);

        while (!sc10.hasNextInt()) {
            System.out.printf("Valor de número de dias de funcionamento anual inválido! Por favor tente novamente: ");
            sc10 = new Scanner(System.in);
        }

        numDiasFuncionamentoAnual = sc10.nextInt();

        while (numDiasFuncionamentoAnual < 0 || numDiasFuncionamentoAnual > 365) {
            System.out.printf("Valor de número de dias de funcionamento anual inválido! Por favor tente novamente: ");
            sc10 = new Scanner(System.in);
            numDiasFuncionamentoAnual = sc10.nextInt();
        }

        System.out.printf("Introduza o número de mesas interiores do estabelecimento: ");
        Scanner sc11 = new Scanner(System.in);

        while (!sc11.hasNextInt()) {
            System.out.printf("Valor de número de mesas interiores inválido! Por favor tente novamente: ");
            sc11 = new Scanner(System.in);
        }

        numMesasInteriores = sc11.nextInt();

        System.out.printf("Introduza o valor medio de faturação diário: ");
        Scanner sc12 = new Scanner(System.in);

        while(!sc12.hasNextDouble()) {
            System.out.printf("Valor medio de faturação diário inválido! Por favor tente novamente: ");
            sc12 = new Scanner(System.in);
        }

        valMedioFaturacaoDiario = sc12.nextDouble();

        System.out.printf("Introduza o número médio de clientes diário no Drive-Thru: ");
        Scanner sc13 = new Scanner(System.in);

        while(!sc13.hasNextInt()) {
            System.out.printf("Valor médio de clientes diário no Drive-Thru inválido! Por favor tente novamente: ");
            sc13 = new Scanner(System.in);
        }

        numMedioClientesDiarioDrive = sc13.nextInt();

        System.out.printf("Introduza o valor médio de faturação diário no Drive-Thru: ");
        Scanner sc14 = new Scanner(System.in);

        while(!sc14.hasNextDouble()) {
            System.out.printf("Valor médio de faturação diário no Drive-Thru inválido! Por favor tente novamente: ");
            sc14 = new Scanner(System.in);
        }

        valMedioFaturacaoDiarioDrive = sc14.nextDouble();

        FastFood f0 = new FastFood(numMesasInteriores, valMedioFaturacaoDiario, numMedioClientesDiarioDrive, valMedioFaturacaoDiarioDrive , numMedioClientesDiario , numDiasFuncionamentoAnual, custoEmpregados, numEmpregados, salarioMedioAnual, categoriaEmpresa, nomeEmpresa, distritoEmpresa, g0);

        return f0;
    }
    
    /**
     * Método responsável por criar uma empresa do tipo Frutaria.
     * 
     * @return 
     */
    public static Frutaria criarFrutaria() {
        String categoriaEmpresa = "Mercearia";
        String nomeEmpresa;
        String distritoEmpresa;
        double coordenadasLatitude;
        double coordenadasLongitude;
        int numProdutos;
        double valMedioFaturacaoAnual;
        double valAnualLimpezaEstabelecimento;

        System.out.printf("Introduza o nome da empresa:");
        Scanner sc2 = new Scanner(System.in);

        while(!sc2.hasNextLine()) {
            System.out.printf("Nome inválido! Por favor tente novamente: ");
            sc2 = new Scanner(System.in);
        }

        nomeEmpresa = sc2.nextLine();

        System.out.printf("Introduza o distrito da empresa: ");
        Scanner sc3 = new Scanner(System.in);

        while(!sc3.hasNextLine()) {
            System.out.printf("Distrito inválido! Por favor tente novamente: ");
            sc3 = new Scanner(System.in);
        }

        distritoEmpresa = sc3.nextLine();

        System.out.printf("Introduza o valor da latitude das coordenadas GPS: ");
        Scanner sc4 = new Scanner(System.in);

        while (!sc4.hasNextDouble()) {
            System.out.printf("Valor de latitude inválido! Por favor tente novamente: ");
            sc4 = new Scanner(System.in);
        }

        coordenadasLatitude = sc4.nextDouble();

        System.out.printf("Introduza o valor da longitude das coordenadas GPS: ");
        Scanner sc5 = new Scanner(System.in);

        while (!sc5.hasNextDouble()) {
            System.out.printf("Valor de longitude inválido! Por favor tente novamente: ");
            sc5 = new Scanner(System.in);
        }

        coordenadasLongitude = sc5.nextDouble();

        Gps g0 = new Gps(coordenadasLatitude, coordenadasLongitude);

        System.out.printf("Introduza o número total de produtos:");
        Scanner sc6 = new Scanner(System.in);

        while(!sc6.hasNextInt()) {
            System.out.printf("Valor do número de produtos inválido! Por favor tente novamente: ");
            sc6 = new Scanner(System.in);
        }

        numProdutos = sc6.nextInt();

        System.out.printf("Introduza o valor médio de faturação anual: ");
        Scanner sc7 = new Scanner(System.in);

        while (!sc7.hasNextDouble()) {
            System.out.printf("Valor médio de faturação anual inválido! Por favor tente novamente: ");
            sc7 = new Scanner(System.in);
        }

        valMedioFaturacaoAnual = sc7.nextDouble();

        System.out.printf("Introduza o valor médio anual de limpeza do estabelecimento: ");
        Scanner sc8 = new Scanner(System.in);

        while (!sc8.hasNextDouble()) {
            System.out.printf("Valor médio de limpeza do estabelecimento inválido! Por favor tente novamente: ");
            sc8 = new Scanner(System.in);
        }

        valAnualLimpezaEstabelecimento = sc8.nextDouble();

        Frutaria f0 = new Frutaria(numProdutos, valMedioFaturacaoAnual, valAnualLimpezaEstabelecimento, categoriaEmpresa, nomeEmpresa, distritoEmpresa, g0);

        return f0;
    }
    
    /**
     * Método responsável por criar uma empresa do tipo mercado.
     * 
     * @return 
     */
    public static Mercado criarMercado() {
        String categoriaEmpresa = "Mercearia";
        String nomeEmpresa;
        String distritoEmpresa;
        double coordenadasLatitude;
        double coordenadasLongitude;
        String tipoMercado;
        double areaCorredores;
        double valMedioFaturacaoAnual;
        double valAnualLimpezaEstabelecimento;

        System.out.printf("Introduza o nome da empresa:");
        Scanner sc2 = new Scanner(System.in);

        while(!sc2.hasNextLine()) {
            System.out.printf("Nome inválido! Por favor tente novamente: ");
            sc2 = new Scanner(System.in);
        }

        nomeEmpresa = sc2.nextLine();

        System.out.printf("Introduza o distrito da empresa: ");
        Scanner sc3 = new Scanner(System.in);

        while(!sc3.hasNextLine()) {
            System.out.printf("Distrito inválido! Por favor tente novamente: ");
            sc3 = new Scanner(System.in);
        }

        distritoEmpresa = sc3.nextLine();

        System.out.printf("Introduza o valor da latitude das coordenadas GPS: ");
        Scanner sc4 = new Scanner(System.in);

        while (!sc4.hasNextDouble()) {
            System.out.printf("Valor de latitude inválido! Por favor tente novamente: ");
            sc4 = new Scanner(System.in);
        }

        coordenadasLatitude = sc4.nextDouble();

        System.out.printf("Introduza o valor da longitude das coordenadas GPS: ");
        Scanner sc5 = new Scanner(System.in);

        while (!sc5.hasNextDouble()) {
            System.out.printf("Valor de longitude inválido! Por favor tente novamente: ");
            sc5 = new Scanner(System.in);
        }

        coordenadasLongitude = sc5.nextDouble();

        Gps g0 = new Gps(coordenadasLatitude, coordenadasLongitude);

        System.out.printf("Introduza o tipo de mercado (Mini/Super/Hiper): ");
        Scanner sc6 = new Scanner(System.in);

        while (!sc6.hasNextLine()) {
            System.out.printf("Tipo de mercado inválido! Por favor tente novamente (Mini/Super/Hiper: ");
            sc6 = new Scanner(System.in);
        }

        tipoMercado = sc6.nextLine();

        while (tipoMercado.equalsIgnoreCase("Mini") == false && tipoMercado.equalsIgnoreCase("Super") && tipoMercado.equalsIgnoreCase("Hiper")) {   //O input é repetido caso o tipo de mercado introduzido não corresponda a nenhuma das 3 opções. A função equalsIgnoreCase permite fazer a comparação ignorando as letras maiúsculas.
            System.out.printf("Tipo de mercado inválido! Por favor tente novamente (Mini/Super/Hiper: ");
            sc6 = new Scanner(System.in);
            tipoMercado = sc6.nextLine();
        }

        System.out.printf("Introduza o valor da área dos corredores: ");
        Scanner sc7 = new Scanner(System.in);

        while (!sc7.hasNextDouble()) {
            System.out.printf("Valor da área dos corredores inválido! Por favor tente novamente: ");
            sc7 = new Scanner(System.in);
        }

        areaCorredores = sc7.nextDouble();

        System.out.printf("Introduza o valor médio de faturação anual: ");
        Scanner sc8 = new Scanner(System.in);

        while (!sc8.hasNextDouble()) {
            System.out.printf("Valor médio de faturação anual inválido! Por favor tente novamente: ");
            sc8 = new Scanner(System.in);
        }

        valMedioFaturacaoAnual = sc8.nextDouble();

        System.out.printf("Introduza o valor médio anual de limpeza do estabelecimento: ");
        Scanner sc9 = new Scanner(System.in);

        while (!sc9.hasNextDouble()) {
            System.out.printf("Valor médio de limpeza do estabelecimento inválido! Por favor tente novamente: ");
            sc9 = new Scanner(System.in);
        }

        valAnualLimpezaEstabelecimento = sc9.nextDouble();

        Mercado m0 = new Mercado(tipoMercado, areaCorredores, valMedioFaturacaoAnual, valAnualLimpezaEstabelecimento, categoriaEmpresa, nomeEmpresa, distritoEmpresa, g0);

        return m0;
    }
    
    /**
     * Método responsável por adicionar uma nova empresa.
     * 
     * @param empresas 
     */
    public void adicionarEmpresa(ArrayList<Empresa> empresas){
        int tipoEmpresa;

        System.out.printf("Introduza o tipo de empresa a adicionar: \n"
                + "1)Restauração.\n"
                + "2)Mercearia\n");
        Scanner sc = new Scanner(System.in);

        while (!sc.hasNextInt()) {   //O input é repetido caso não seja introduzido um número.
            System.out.printf("Tipo inválido! Por favor tente novamente: ");
            sc = new Scanner(System.in);
        }

        tipoEmpresa = sc.nextInt();

        while (tipoEmpresa < 1 || tipoEmpresa > 2) {   //O input é repetido caso não tenha sido introduzido um tipo de empresa existente.
            System.out.printf("Tipo inválido! Por favor tente novamente: ");
            sc = new Scanner(System.in);
            tipoEmpresa = sc.nextInt();
        }

        if (tipoEmpresa == 1) {   //Empresa do tipo restauração.
            int tipoRestauracao;

            System.out.printf("Introduza o tipo de empresa da área de restauração:\n"
                    + "1)Café.\n"
                    + "2)Pastelaria.\n"
                    + "3)Restaurante Local.\n"
                    + "4)Restaurante Fast-Food.\n");
            Scanner sc1 = new Scanner(System.in);

            while (!sc1.hasNextInt()) {   //O input é repetido caso não seja introduzido um número.
                System.out.printf("Tipo inválido! Por favor tente novamente: ");
                sc1 = new Scanner(System.in);
            }

            tipoRestauracao = sc1.nextInt();

            while (tipoRestauracao < 1 || tipoRestauracao > 4) {   //O input é repetido caso não tenha sido introduzido um tipo de empresa existente.
                System.out.printf("Tipo inválido! Por favor tente novamente: ");
                sc1 = new Scanner("System.in");
                tipoRestauracao = sc1.nextInt();
            }

            if (tipoRestauracao == 1) {   //Empresa do tipo café.
                Cafe c0 = criarCafe();

                empresas.add(c0);
            }

            else if (tipoRestauracao == 2) {   //Empresa do tipo pastelaria.
                Pastelaria p0 = criarPastelaria();

                empresas.add(p0);
            }

            else if (tipoRestauracao == 3) {   //Empresa do tipo restaurante local.
                Local l0 = criarRestauranteLocal();

                empresas.add(l0);
            }

            else if (tipoRestauracao == 4) {   //Empresa do tipo restaurante fast-food.
                FastFood f0 = criarRestauranteFastFood();

                empresas.add(f0);
            }
        }

        else if (tipoEmpresa == 2) {
            int tipoMercearia;

            System.out.printf("Introduza o tipo de empresa da área de Mercearia:\n"
                    + "1)Frutaria.\n"
                    + "2)Mercado.\n");
            Scanner sc1 = new Scanner(System.in);

            while (!sc1.hasNextInt()) {   //O input é repetido caso não seja introduzido um número.
                System.out.printf("Tipo inválido! Por favor tente novamente: ");
                sc1 = new Scanner(System.in);
            }

            tipoMercearia = sc1.nextInt();

            while (tipoMercearia < 1 || tipoMercearia > 2) {   //O input é repetido caso não tenha sido introduzido um tipo de empresa existente.
                System.out.printf("Tipo inválido! Por favor tente novamente: ");
                sc1 = new Scanner("System.in");
                tipoMercearia = sc1.nextInt();
            }

            if (tipoMercearia == 1) {
                Frutaria f0 = criarFrutaria();

                empresas.add(f0);
            }

            else if (tipoMercearia == 2) {
                Mercado m0 = criarMercado();

                empresas.add(m0);
            }
        }
    }
    
    /**
     * Método responsável por editar uma empresa.
     * 
     * @param starthrive 
     */
    public void editarEmpresa(StarThrive starthrive) {   //Método responsável por editar os valores de uma empresa.
        String nomeEmpresa;
        Empresa empresaEditar;
        int selecionaOpcao;

        System.out.printf("Introduza o nome da empresa a editar: ");
        Scanner sc = new Scanner(System.in);
        nomeEmpresa = sc.nextLine();

        empresaEditar = starthrive.procurarEmpresa(nomeEmpresa);

        while (empresaEditar == null) {
            System.out.printf("Empresa não encontrada! Por favor, tente novamente: ");
            sc = new Scanner(System.in);
            nomeEmpresa = sc.nextLine();

            empresaEditar = starthrive.procurarEmpresa(nomeEmpresa);
        }

        empresaEditar.informacaoEditarEmpresa();

        System.out.printf("Introduza a opção a selecionar: ");
        Scanner sc1 = new Scanner(System.in);
        selecionaOpcao = sc1.nextInt();

        empresaEditar.executarEditarEmpresa(selecionaOpcao);
    }
    
    /**
     * Método responsável por apagar uma empresa.
     * 
     * @param starthrive 
     */
    public void apagarEmpresa(StarThrive starthrive) {   //Método responsável por eliminar uma empresa.
        String nomeEmpresa;
        Empresa empresaApagar;

        System.out.printf("Introduza o nome da empresa a remover: ");
        Scanner sc = new Scanner(System.in);
        nomeEmpresa = sc.nextLine();

        empresaApagar = starthrive.procurarEmpresa(nomeEmpresa);

        while (empresaApagar == null) {
            System.out.printf("Empresa não encontrada! Por favor, tente novamente: ");
            sc = new Scanner(System.in);
            nomeEmpresa = sc.nextLine();

            empresaApagar = starthrive.procurarEmpresa(nomeEmpresa);
        }

        starthrive.getEmpresas().remove(empresaApagar);
    }
    
    /**
     * Método responsável por executar o programa.
     * 
     */
    public void executarPrograma() {
        StarThrive starthrive;
        Ficheiro f = new Ficheiro();
        ArrayList<Restauracao> restaurantesr = f.retornarestaurantes();
        File fObjetos = new File("starthrive.obj");
        
        if (!fObjetos.exists()) {   //Se o ficheiro de objetos não existir, é lido o ficheiro de texto starthrive.txt.
            starthrive = f.lerFicheiroTexto("starthrive.txt");   //Inicializamos o objeto starthrive da classe StarThrive através da leitura do conteúdo presente no ficheior de texto starthrive.txt.
        }
        else {   //Caso contrário, é lido o ficheiro de objetos starthrive.obj.

            starthrive = f.lerFicheiroObjetos("starthrive.obj");
        }
        
        starthrive.imprimirEmpresas();
        
        starthrive.imprimirEmpresasCondicoes();
        
        starthrive.imprimirEmpresasMaiorCapacidade();
        
        Gui gui = new Gui(starthrive, restaurantesr);
        
        gui.setVisible(true);

       /* java.awt.EventQueue.invokeLater(() -> {
            new MainInterface(starthrive).setVisible(true);
        });*/
        
        starthrive.setEmpresas(gui.getLista());
        
        f.escreverFicheiroObjetos(starthrive);
    }
    
    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GestorStarThrive gst = new GestorStarThrive();
        
        gst.executarPrograma();
    }
}
