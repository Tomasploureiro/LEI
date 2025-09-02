/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorstarthrive;

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author filip
 */
public final class Ficheiro {
    private ArrayList<Restauracao> restaurantes = new ArrayList<>();
    
    /**
     * Método responsável por ler o ficheiro de texto.
     * 
     * @param diretoriaFicheiro
     * @return 
     */
    public StarThrive lerFicheiroTexto(String diretoriaFicheiro) {
        ArrayList<Empresa> empresas = new ArrayList<>();
        
        File file = new File(diretoriaFicheiro);
        
        String[] conteudo = new String[10];   //Esta tabela irá guardar cada linha do ficheiro starthrive.txt.
        
        if (file.exists() && file.isFile()) {
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                
                String line;   //Variável que irá conter o conteúdo de cada linha do ficheiro starthrive.txt.
                int i = 0;
                while ((line = br.readLine()) != null) {
                    conteudo[i] = line;
                    i++;
                }
                
                br.close();
            } catch (FileNotFoundException ex) {
                System.out.printf("Ocorreu um erro ao abrir o ficheiro de texto.\n");
            } catch (IOException ex) {
                System.out.printf("Ocorreu um erro ao ler o ficheiro de texto.\n");
            }
        }
        else {
            System.out.printf("O ficheiro de texto não existe.\n");
        }
        
        for (int i = 0; i < conteudo.length; i++) {   //Percorre todas as linhas do ficheiro starthrive.txt, realizando o split em cada linha.
            String tipoEmpresa = conteudo[i].split("-")[0];   //Variável que irá guardar a primeira posição do split, que contem o tipo de empresa (Café, Pastelaria, Restaurante Local, Restaurante Fast-Food, Frutaria ou Mercado).
            int count = 1;
            
            for(int j = 0; j < conteudo[i].length(); j++) {
                if (conteudo[i].charAt(j) == '-') {
                    count++;
                }
            }
            
            String[] splitConteudo = new String[count];
            splitConteudo = conteudo[i].split("-");
            
            if (tipoEmpresa.equals("Café")) {

                int numMedioClientesDiario = Integer.parseInt(splitConteudo[1]);
                int numMedioCafesDiario = Integer.parseInt(splitConteudo[2]);
                double valMedioFaturacaoAnual = Double.parseDouble(splitConteudo[3]);
                double custoEmpregados = Double.parseDouble(splitConteudo[4]);
                int numEmpregados = Integer.parseInt(splitConteudo[5]);
                double salarioMedioAnual = Double.parseDouble(splitConteudo[6]);
                String categoriaEmpresa = splitConteudo[7];
                String nomeEmpresa = splitConteudo[8];
                String distritoEmpresa = splitConteudo[9];
                double coordenadasLatitude = Double.parseDouble(splitConteudo[10]);
                double coordenadasLongitude = Double.parseDouble(splitConteudo[11]);
                
                Gps gps0 = new Gps(coordenadasLatitude, coordenadasLongitude);
                
                Cafe c0 = new Cafe(numMedioClientesDiario, numMedioCafesDiario, valMedioFaturacaoAnual, custoEmpregados, numEmpregados, salarioMedioAnual, categoriaEmpresa, nomeEmpresa, distritoEmpresa, gps0);
                
                empresas.add(c0);
                restaurantes.add(c0);
            }
            else if (tipoEmpresa.equals("Pastelaria")) {
                
                int numMedioClientesDiario = Integer.parseInt(splitConteudo[1]);
                int numMedioBolosDiario = Integer.parseInt(splitConteudo[2]);
                double valMedioFaturacaoAnual = Double.parseDouble(splitConteudo[3]);
                double custoEmpregados = Double.parseDouble(splitConteudo[4]);
                int numEmpregados = Integer.parseInt(splitConteudo[5]);
                double salarioMedioAnual = Double.parseDouble(splitConteudo[6]);
                String categoriaEmpresa = splitConteudo[7];
                String nomeEmpresa = splitConteudo[8];
                String distritoEmpresa = splitConteudo[9];
                double coordenadasLatitude = Double.parseDouble(splitConteudo[10]);
                double coordenadasLongitude = Double.parseDouble(splitConteudo[11]);
                
                Gps gps0 = new Gps(coordenadasLatitude, coordenadasLongitude);
                
                Pastelaria p0 = new Pastelaria(numMedioClientesDiario, numMedioBolosDiario, valMedioFaturacaoAnual, custoEmpregados, numEmpregados, salarioMedioAnual, categoriaEmpresa, nomeEmpresa, distritoEmpresa, gps0);
                
                empresas.add(p0);
                restaurantes.add(p0);
            }
            else if (tipoEmpresa.equals("Local")) {

                int numMesasInteriores = Integer.parseInt(splitConteudo[1]);
                int numMesasEsplanada = Integer.parseInt(splitConteudo[2]);
                double valLicencaAnualMesaEsplanada = Double.parseDouble(splitConteudo[3]);
                double valMedioFaturacaoDiario = Double.parseDouble(splitConteudo[4]);
                int numMedioClientesDiario = Integer.parseInt(splitConteudo[5]);
                int numDiasFuncionamentoAnual = Integer.parseInt(splitConteudo[6]);
                double custoEmpregados = Double.parseDouble(splitConteudo[7]);
                int numEmpregados = Integer.parseInt(splitConteudo[8]);
                double salarioMedioAnual = Double.parseDouble(splitConteudo[9]);
                String categoriaEmpresa = splitConteudo[10];
                String nomeEmpresa = splitConteudo[11];
                String distritoEmpresa = splitConteudo[12];
                double coordenadasLatitude = Double.parseDouble(splitConteudo[13]);
                double coordenadasLongitude = Double.parseDouble(splitConteudo[14]);
                
                Gps gps0 = new Gps(coordenadasLatitude, coordenadasLongitude);
                
                Local l0 = new Local(numMesasInteriores, numMesasEsplanada, valLicencaAnualMesaEsplanada, valMedioFaturacaoDiario, numMedioClientesDiario, numDiasFuncionamentoAnual, custoEmpregados, numEmpregados, salarioMedioAnual, categoriaEmpresa, nomeEmpresa, distritoEmpresa, gps0);
                
                empresas.add(l0);
                restaurantes.add(l0);
            }
            else if (tipoEmpresa.equals("FastFood")) {

                int numMesasInteriores = Integer.parseInt(splitConteudo[1]);
                double valMedioFaturacaoDiario = Double.parseDouble(splitConteudo[2]);
                int numMedioClientesDiarioDrive = Integer.parseInt(splitConteudo[3]);
                double valMedioFaturacaoDiarioDrive = Double.parseDouble(splitConteudo[4]);
                int numMedioClientesDiario = Integer.parseInt(splitConteudo[5]);
                int numDiasFuncionamentoAnual = Integer.parseInt(splitConteudo[6]);
                double custoEmpregados = Double.parseDouble(splitConteudo[7]);
                int numEmpregados = Integer.parseInt(splitConteudo[8]);
                double salarioMedioAnual = Double.parseDouble(splitConteudo[9]);
                String categoriaEmpresa = splitConteudo[10];
                String nomeEmpresa = splitConteudo[11];
                String distritoEmpresa = splitConteudo[12];
                double coordenadasLatitude = Double.parseDouble(splitConteudo[13]);
                double coordenadasLongitude = Double.parseDouble(splitConteudo[14]);
                
                Gps gps0 = new Gps(coordenadasLatitude, coordenadasLongitude);
                
                FastFood f0 = new FastFood(numMesasInteriores, valMedioFaturacaoDiario, numMedioClientesDiarioDrive, valMedioFaturacaoDiarioDrive, numMedioClientesDiario, numDiasFuncionamentoAnual, custoEmpregados, numEmpregados, salarioMedioAnual, categoriaEmpresa, nomeEmpresa, distritoEmpresa, gps0);
                
                empresas.add(f0);
                restaurantes.add(f0);
            }
            else if (tipoEmpresa.equals("Frutaria")) {

                int numProdutos = Integer.parseInt(splitConteudo[1]);
                double valMedioFaturacaoAnual = Integer.parseInt(splitConteudo[2]);
                double valAnualLimpezaEstabelecimento = Integer.parseInt(splitConteudo[3]);
                String categoriaEmpresa = splitConteudo[4];
                String nomeEmpresa = splitConteudo[5];
                String distritoEmpresa = splitConteudo[6];
                double coordenadasLatitude = Double.parseDouble(splitConteudo[7]);
                double coordenadasLongitude = Double.parseDouble(splitConteudo[8]);
                
                Gps gps0 = new Gps(coordenadasLatitude, coordenadasLongitude);
                
                Frutaria f0 = new Frutaria(numProdutos, valMedioFaturacaoAnual, valAnualLimpezaEstabelecimento, categoriaEmpresa, nomeEmpresa, distritoEmpresa, gps0);
                
                empresas.add(f0);
            }
            else if (tipoEmpresa.equals("Mercado")) {

                String tipoMercado = splitConteudo[1];
                double areaCorredores = Double.parseDouble(splitConteudo[2]);
                double valMedioFaturacaoAnual = Double.parseDouble(splitConteudo[3]);
                double valAnualLimpezaEstabelecimento = Double.parseDouble(splitConteudo[4]);
                String categoriaEmpresa = splitConteudo[5];
                String nomeEmpresa = splitConteudo[6];
                String distritoEmpresa = splitConteudo[7];
                double coordenadasLatitude = Double.parseDouble(splitConteudo[8]);
                double coordenadasLongitude = Double.parseDouble(splitConteudo[9]);
                
                Gps gps0 = new Gps(coordenadasLatitude, coordenadasLongitude);
                
                Mercado m0 = new Mercado(tipoMercado, areaCorredores, valMedioFaturacaoAnual, valAnualLimpezaEstabelecimento, categoriaEmpresa, nomeEmpresa, distritoEmpresa, gps0);
                
                empresas.add(m0);
            }
        }
        
        return new StarThrive(empresas);
    }
    
    /**
     * Método responsável por ler o ficheiro de objetos.
     * 
     * @param diretoriaFicheiro
     * @return 
     */
    public StarThrive lerFicheiroObjetos(String diretoriaFicheiro) {
        StarThrive starthrive = null;   //Inicializamos o objeto starthrive a null.
        
        File f = new File("starthrive.obj");
        
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            starthrive = (StarThrive)ois.readObject();   //Leitura do ficheiro de objetos.
            
            ois.close();
        } catch (FileNotFoundException ex) {
            System.out.printf("Ocorreu um erro ao abrir o ficheiro de objetos.\n");
        } catch (IOException ex) {
            System.out.printf("Ocorreu um erro ao ler o ficheiro de objetos.\n");
        } catch (ClassNotFoundException ex) {
            System.out.printf("Ocorreu um erro ao converter o ficheiro de objetos.\n");
        }
        
        return starthrive;
    }
    
    /**
     * Método responsável por escrever no ficheiro de objetos.
     * 
     * @param starthrive 
     */
    public void escreverFicheiroObjetos(StarThrive starthrive) {
        File f = new File("starthrive.obj");
        
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            oos.writeObject(starthrive);
            
            oos.close();
        } catch (FileNotFoundException ex) {
            System.out.printf("Ocorreu um erro ao criar o ficheiro de objetos.\n");
        } catch (IOException ex) {
            System.out.printf("Ocorreu um erro ao escrever para o ficheiro de objetos.\n");
        }
    }
    
    /**
     * Método responsável por retornar um ArrayList com os restaurantes.
     * 
     * @return 
     */
    public ArrayList<Restauracao> retornarestaurantes(){
            return restaurantes;
    }
}
