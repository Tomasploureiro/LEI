/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorstarthrive;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author filip
 */
public class Gui extends javax.swing.JFrame {   
    private ArrayList<Empresa> lista;
    private ArrayList<StarThrive> lista2;
    private ArrayList<Restauracao> restaurantes;
    private String header[] = new String[]{"Nome", "Categoria", "Distrito","Despesa","Receita","Lucro"};
    private DefaultTableModel dtm;
    private int row;
    private String lucro;

    public Gui() {
    }

    public Gui(StarThrive starthrive, ArrayList<Restauracao> restaurante) {
        int cdiario = 0;
        int cdiario2 = 0;
        int auxcdiario1 = 0;
        int auxcdiario2 = 0;
        restaurantes = restaurante; 
        lista = starthrive.getEmpresas();
        initComponents();
        dtm = new DefaultTableModel(header,0);
        jTable2.setModel(dtm);
        this.setLocationRelativeTo(null);
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).lucroAnual() > 0){
                 lucro = "sim";
            }else{lucro = "não";}
            Object[] objs = {lista.get(i).getNomeEmpresa(),lista.get(i).getCategoriaEmpresa(),lista.get(i).getDistritoEmpresa(),lista.get(i).despesaAnual(),lista.get(i).receitaAnual(),lucro};
            if(lista.get(i).lucroAnual() > 0){
                 lucro = "Sim";
            }else{lucro = "Não";}
            
            dtm.addRow(objs);
        }
        double auxval = 0;
        int numempresa  = 0;
        
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).getCategoriaEmpresa().equals("Restauração")){
            if(auxval < lista.get(i).receitaAnual()){
                auxval = lista.get(i).receitaAnual();
                numempresa = i;
            }
            }
                }
        
        jRMaiorReceita.setText("Nome: " + lista.get(numempresa).getNomeEmpresa() + "   Receita: " + lista.get(numempresa).receitaAnual());
        numempresa  = 0;
        auxval = 0;
        
        for (int i = 0; i < lista.size(); i++) {
             if(lista.get(i).getCategoriaEmpresa().equals("Mercearia")){
                if(auxval < lista.get(i).receitaAnual()){
                    auxval = lista.get(i).receitaAnual();
                    numempresa = i;
            }
            }
                }
        
        jMMaiorReceita.setText("Nome: " + lista.get(numempresa).getNomeEmpresa() + "   Receita: " + lista.get(numempresa).receitaAnual());
        numempresa  = 0;
        auxval = 0;
        
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).getCategoriaEmpresa().equals("Restauração")){
             if(i == 0){auxval = lista.get(i).despesaAnual();}
                if(auxval > lista.get(i).despesaAnual()){
                    auxval = lista.get(i).despesaAnual();
                    numempresa = i;

            }
            }
            }
      
        jRMenorDespesa.setText("Nome: " + lista.get(numempresa).getNomeEmpresa() + "   Despesa " + lista.get(numempresa).despesaAnual());
        numempresa  = 0;
        auxval = 0;
        
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).getCategoriaEmpresa().equals("Mercearia")){
             if(auxval == 0){auxval = lista.get(i).despesaAnual();}
                if(auxval >= lista.get(i).despesaAnual()){
                    auxval = lista.get(i).despesaAnual();
                    numempresa = i;

            }
            }
            }
        
        jMDespesamenor.setText("Nome: " + lista.get(numempresa).getNomeEmpresa() + "   Despesa " + lista.get(numempresa).despesaAnual());
        numempresa  = 0;
        auxval = 0;
        
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).getCategoriaEmpresa().equals("Restauração")){
             if(i == 0){auxval = lista.get(i).lucroAnual();}
                if(auxval < lista.get(i).lucroAnual()){
                    auxval = lista.get(i).lucroAnual();
                    numempresa = i;

            }
            }
            }
      
        jRLucroAnual.setText("Nome: " + lista.get(numempresa).getNomeEmpresa() + "   Lucro " + lista.get(numempresa).lucroAnual());
        numempresa  = 0;
        auxval = 0;
        
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).getCategoriaEmpresa().equals("Mercearia")){
             if(i == 0){auxval = lista.get(i).lucroAnual();}
                if(auxval < lista.get(i).lucroAnual()){
                    auxval = lista.get(i).lucroAnual();
                    numempresa = i;

            }
            }
            }
      
        jMLucroAnual.setText("Nome: " + lista.get(numempresa).getNomeEmpresa() + "   Lucro " + lista.get(numempresa).lucroAnual());
        numempresa  = 0;
        auxval = 0;
        for(int i = 0; i < restaurante.size();i++){
            System.out.println("OI");
            if(i == 0){cdiario = restaurante.get(i).getNumMedioClientesDiario();}
            if(cdiario < restaurante.get(i).getNumMedioClientesDiario()){
                cdiario = restaurante.get(i).getNumMedioClientesDiario();
                auxcdiario1 = i;
            }
        }
        for(int i = 0; i < restaurante.size();i++){
            if(i == 0){cdiario2 = restaurante.get(i).getNumMedioClientesDiario();}
            if(cdiario2 < restaurante.get(i).getNumMedioClientesDiario() && cdiario2 < restaurante.get(auxcdiario1).getNumMedioClientesDiario()){
                cdiario2 = restaurante.get(i).getNumMedioClientesDiario();
                System.out.println(i);
                auxcdiario2 = i;
            }}
        
        
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextNomeCafe = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextDistritoCafe = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextGPSCafe = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextSalarioAnualCafe = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextNumEmpregadosCafe = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextClientesDiarioCafe = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextCafeDiarioCafe = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextCustoEmpregadosCafe = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextFaturacaoAnualCafe = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTextNomeRestaurante1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextDistritoRestaurante1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jTextGpsRestaurante1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jTextSalarioAnualRestaurante1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTextNumEmpregadosRestaurante1 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jTextClientesDiarioRestaurante1 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jTextDiasFuncAnualRestaurante1 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jTextCustoEmpregadosRestaurante1 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jTextFaturacaoDiariaRestaurante1 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jTextNumMesasIntRestaurante1 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jTextNumMesasEsplanadaRestaurante1 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jTextLicencaAnualEsplanadaRestaurante1 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jTextNomePastelaria = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextDistritoPastelaria = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTextBolosDiariosPastelaria = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextGPSPastelaria = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextSalarioAnualPastelaria = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextCustoEmpregadosPastelaria = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextNumEmpregadosPastelaria = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTextClientesDiarioPastelaria = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jTextFaturacaoAnualPastelaria = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextNomeFrutaria = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextDistritoFrutaria = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jTextvalMedioFaturacaoFrutaria = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jTextnumProdutosFrutaria = new javax.swing.JTextField();
        jTextvalAnualLimpezaFrutaria = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextGpsFrutaria = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jTextNomeRestaurante2 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jTextDistritoRestaurante2 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jTextGpsRestaurante2 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jTextSalarioAnualRestaurante2 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jTextNumEmpregadosRestaurante2 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jTextClientesDiarioRestaurante2 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jTextDiasFuncAnualRestaurante2 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jTextCustoEmpregadosRestaurante2 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jTextFaturacaoDiariaRestaurante2 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jTextNumMesasIntRestaurante2 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jTextClientesDiarioDriveRestaurante2 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jTextFaturacaoDiariaDriveRestaurante2 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jButton17 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jTextNomeMercado = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jTextDistritoMercado = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jTextGpsMercado = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jTextLimpezaAnualMercado = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jTextAreaCorredorMercado = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jTextTipoMercado = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jTextFaturacaoAnualMercado = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        jButton21 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jTextNomeup = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jTextCategoriaup = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        jTextDistritoup = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel71 = new javax.swing.JLabel();
        jTextGpsup = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        jRMaiorReceita = new javax.swing.JTextField();
        jMMaiorReceita = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jRMenorDespesa = new javax.swing.JTextField();
        jMDespesamenor = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        jRLucroAnual = new javax.swing.JTextField();
        jMLucroAnual = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel34.setText("Cafe");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("StarThrive");
        setResizable(false);

        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Exit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setText("Nome");

        jLabel6.setText("Distrito");

        jTextDistritoCafe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDistritoCafeActionPerformed(evt);
            }
        });

        jLabel11.setText("Gps");

        jLabel12.setText("SalarioAnual");

        jLabel13.setText("NumEmpregados");

        jLabel7.setText("ClientesDiario");

        jLabel14.setText("CafeDiario");

        jLabel15.setText("CustoEmpregados");

        jTextCustoEmpregadosCafe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCustoEmpregadosCafeActionPerformed(evt);
            }
        });

        jLabel16.setText("FaturacaoAnual");

        jButton5.setText("Add");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel8.setText("Cafe");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)))
                .addGap(49, 49, 49)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextNomeCafe, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                    .addComponent(jTextDistritoCafe)
                    .addComponent(jTextCafeDiarioCafe))
                .addGap(141, 141, 141)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jTextCustoEmpregadosCafe, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(159, 159, 159))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextGPSCafe, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextSalarioAnualCafe, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextClientesDiarioCafe, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextNumEmpregadosCafe, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFaturacaoAnualCafe, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextNomeCafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jTextGPSCafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jTextNumEmpregadosCafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextDistritoCafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jTextSalarioAnualCafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jTextClientesDiarioCafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextCafeDiarioCafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jTextCustoEmpregadosCafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jTextFaturacaoAnualCafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(7, 7, 7))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setText("Nome");

        jLabel10.setText("Distrito");

        jTextDistritoRestaurante1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDistritoRestaurante1ActionPerformed(evt);
            }
        });

        jLabel26.setText("Gps");

        jLabel27.setText("SalarioAnual");

        jLabel28.setText("NumEmpregados");

        jLabel29.setText("ClientesDiario");

        jLabel30.setText("DiasFuncAnual");

        jLabel31.setText("CustoEmpregados");

        jTextCustoEmpregadosRestaurante1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCustoEmpregadosRestaurante1ActionPerformed(evt);
            }
        });

        jLabel32.setText("FaturacaoDiaria");

        jLabel36.setText("NumMesasInt");

        jLabel37.setText("NumMesasEsplanada");

        jLabel38.setText("LicencaAnualEsplanada");

        jButton9.setText("Add");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel33.setText("Restaurante Local");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextNomeRestaurante1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextDistritoRestaurante1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextDiasFuncAnualRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jTextNumMesasIntRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(166, 166, 166)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextGpsRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextSalarioAnualRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextCustoEmpregadosRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextNumMesasEsplanadaRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(144, 144, 144))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextNumEmpregadosRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextLicencaAnualEsplanadaRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextClientesDiarioRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFaturacaoDiariaRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(399, 399, 399))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextNomeRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(jTextGpsRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(jTextNumEmpregadosRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextDistritoRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(jTextSalarioAnualRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(jTextClientesDiarioRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jTextDiasFuncAnualRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(jTextCustoEmpregadosRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(jTextFaturacaoDiariaRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jTextNumMesasIntRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)
                    .addComponent(jTextNumMesasEsplanadaRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38)
                    .addComponent(jTextLicencaAnualEsplanadaRestaurante1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel35.setText("Pastelaria");

        jButton13.setText("Add");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel17.setText("Nome");

        jLabel18.setText("Distrito");

        jTextDistritoPastelaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDistritoPastelariaActionPerformed(evt);
            }
        });

        jLabel23.setText("BolosDiarios");

        jTextBolosDiariosPastelaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextBolosDiariosPastelariaActionPerformed(evt);
            }
        });

        jLabel19.setText("Gps");

        jTextGPSPastelaria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextGPSPastelariaFocusLost(evt);
            }
        });

        jLabel20.setText("SalarioAnual");

        jLabel24.setText("CustoEmpregados");

        jTextCustoEmpregadosPastelaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCustoEmpregadosPastelariaActionPerformed(evt);
            }
        });

        jLabel21.setText("NumEmpregados");

        jLabel22.setText("ClientesDiario");

        jLabel25.setText("FaturacaoAnual");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextNomePastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextDistritoPastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextBolosDiariosPastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(142, 142, 142)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(92, 92, 92))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(39, 39, 39)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextSalarioAnualPastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextGPSPastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jTextCustoEmpregadosPastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 275, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(330, 330, 330)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextClientesDiarioPastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFaturacaoAnualPastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextNumEmpregadosPastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel35)
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jTextNomePastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jTextGPSPastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jTextNumEmpregadosPastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jTextDistritoPastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jTextSalarioAnualPastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(jTextClientesDiarioPastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jTextBolosDiariosPastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jTextCustoEmpregadosPastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(jTextFaturacaoAnualPastelaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton13)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Nome");

        jLabel3.setText("Distrito");

        jLabel59.setText("valMedioFaturacao");

        jTextvalMedioFaturacaoFrutaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextvalMedioFaturacaoFrutariaActionPerformed(evt);
            }
        });

        jLabel60.setText("numProdutos");

        jTextnumProdutosFrutaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextnumProdutosFrutariaActionPerformed(evt);
            }
        });

        jTextvalAnualLimpezaFrutaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextvalAnualLimpezaFrutariaActionPerformed(evt);
            }
        });

        jLabel61.setText("valAnualLimpeza");

        jLabel4.setText("Gps");

        jLabel64.setText("Frutaria");

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextDistritoFrutaria, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextNomeFrutaria, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 223, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(96, 96, 96)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextvalMedioFaturacaoFrutaria, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                .addComponent(jTextnumProdutosFrutaria)))))
                .addGap(110, 110, 110)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextvalAnualLimpezaFrutaria, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextGpsFrutaria, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel64)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextNomeFrutaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59)
                    .addComponent(jTextvalMedioFaturacaoFrutaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61)
                    .addComponent(jTextvalAnualLimpezaFrutaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jTextDistritoFrutaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jTextGpsFrutaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextnumProdutosFrutaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel60)))
                .addGap(22, 22, 22)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel40.setText("Nome");

        jLabel41.setText("Distrito");

        jTextDistritoRestaurante2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDistritoRestaurante2ActionPerformed(evt);
            }
        });

        jLabel42.setText("Gps");

        jLabel43.setText("SalarioAnual");

        jLabel44.setText("NumEmpregados");

        jLabel45.setText("ClientesDiario");

        jLabel46.setText("DiasFuncAnual");

        jLabel47.setText("CustoEmpregados");

        jTextCustoEmpregadosRestaurante2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCustoEmpregadosRestaurante2ActionPerformed(evt);
            }
        });

        jLabel48.setText("FaturacaoDiaria");

        jLabel49.setText("NumMesasInt");

        jLabel50.setText("ClientesDiarioDrive");

        jLabel51.setText("FaturacaoDiariaDrive");

        jTextFaturacaoDiariaDriveRestaurante2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFaturacaoDiariaDriveRestaurante2ActionPerformed(evt);
            }
        });

        jLabel39.setText("Restaurante Fast Food");

        jButton17.setText("Add");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addGap(67, 67, 67)
                                        .addComponent(jTextNumMesasIntRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(79, 79, 79))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(43, 43, 43)
                                        .addComponent(jTextDiasFuncAnualRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextNomeRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextDistritoRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextSalarioAnualRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextGpsRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextCustoEmpregadosRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextClientesDiarioDriveRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22)))
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextNumEmpregadosRestaurante2, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .addComponent(jTextClientesDiarioRestaurante2)
                            .addComponent(jTextFaturacaoDiariaRestaurante2)
                            .addComponent(jTextFaturacaoDiariaDriveRestaurante2)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(372, 372, 372))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(362, 362, 362)))))
                .addGap(58, 58, 58))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addComponent(jLabel39)
                            .addGap(12, 12, 12)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel40)
                                .addComponent(jLabel42)
                                .addComponent(jTextGpsRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextDistritoRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel43)
                                    .addComponent(jTextSalarioAnualRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(22, 22, 22)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel46)
                                    .addComponent(jTextDiasFuncAnualRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextCustoEmpregadosRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel47)
                                    .addComponent(jLabel48)
                                    .addComponent(jTextFaturacaoDiariaRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(jTextNomeRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(82, 82, 82)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextNumEmpregadosRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel44))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(jLabel45)
                            .addComponent(jTextClientesDiarioRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel49)
                        .addComponent(jLabel51)
                        .addComponent(jTextFaturacaoDiariaDriveRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextClientesDiarioDriveRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50)
                    .addComponent(jTextNumMesasIntRestaurante2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jButton17)
                .addContainerGap())
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel52.setText("Nome");

        jLabel53.setText("Distrito");

        jTextDistritoMercado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDistritoMercadoActionPerformed(evt);
            }
        });

        jLabel54.setText("Gps");

        jLabel55.setText("LimpezaAnual");

        jLabel56.setText("AreaCorredor");

        jLabel57.setText("TipoMercado");

        jTextTipoMercado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextTipoMercadoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextTipoMercadoFocusLost(evt);
            }
        });
        jTextTipoMercado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextTipoMercadoActionPerformed(evt);
            }
        });

        jLabel58.setText("FaturacaoAnual");

        jLabel65.setText("Mercado");

        jButton21.setText("Add");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel65)
                        .addGap(474, 474, 474))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(385, 385, 385))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(73, 73, 73)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFaturacaoAnualMercado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextNomeMercado, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextDistritoMercado, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(141, 141, 141)
                                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextLimpezaAnualMercado, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                                    .addComponent(jTextGpsMercado))
                                .addGap(136, 136, 136)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                    .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextTipoMercado, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                                    .addComponent(jTextAreaCorredorMercado))))
                        .addGap(50, 50, 50))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextAreaCorredorMercado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextNomeMercado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel54)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextDistritoMercado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel57)
                            .addComponent(jTextTipoMercado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel55)
                            .addComponent(jTextLimpezaAnualMercado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel65)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel56)
                                .addGap(5, 5, 5))
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel52)
                                .addComponent(jTextGpsMercado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jLabel53)))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(jTextFaturacaoAnualMercado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jButton21)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jScrollPane5.setViewportView(jPanel5);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane3MouseClicked(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel62.setText("Update");

        jTextNomeup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNomeupActionPerformed(evt);
            }
        });

        jLabel63.setText("Nome");

        jLabel66.setText("Categoria");

        jTextCategoriaup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCategoriaupActionPerformed(evt);
            }
        });

        jLabel67.setText("Distrito");

        jTextDistritoup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDistritoupActionPerformed(evt);
            }
        });

        jButton4.setText("Update");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel71.setText("Gps");

        jTextGpsup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextGpsupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton4)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextNomeup, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextCategoriaup, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextDistritoup, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67)
                                .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextGpsup, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel62)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextNomeup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel63)
                    .addComponent(jLabel66)
                    .addComponent(jTextCategoriaup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67)
                    .addComponent(jTextDistritoup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71)
                    .addComponent(jTextGpsup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addContainerGap())
        );

        jLabel70.setText("Selecione uma linha para mais informações de uma empresa!");

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel68.setText("Empresa com maior receita anual:");

        jRMaiorReceita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRMaiorReceitaActionPerformed(evt);
            }
        });

        jLabel69.setText("Restauração:");

        jLabel72.setText("Merciaria:");

        jLabel73.setText("Empresa com menor despesa anual:");

        jLabel74.setText("Empresa com maior lucro anual:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                            .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jRMaiorReceita, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                            .addComponent(jMMaiorReceita))))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jRMenorDespesa, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                            .addComponent(jMDespesamenor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jRLucroAnual, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                            .addComponent(jMLucroAnual))
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(jLabel73)
                    .addComponent(jLabel74))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRMaiorReceita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel69)
                    .addComponent(jRMenorDespesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRLucroAnual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jMMaiorReceita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel72)
                    .addComponent(jMDespesamenor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jMLucroAnual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jLabel76.setText("!!!!!!!Preencher Gps no seguinte formato: Latitude-Longitude!!!!!!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1218, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel76)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 36, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel70)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    //** Frutaria */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String frutariaNome = jTextNomeFrutaria.getText();
        String frutariaDistrito = jTextDistritoFrutaria.getText();
        String frutariaValorMedioFaturacao = jTextvalMedioFaturacaoFrutaria.getText();
        String frutariaNumProdutos = jTextnumProdutosFrutaria.getText();
        String frutariavalAnualLimpeza = jTextvalAnualLimpezaFrutaria.getText();
        String frutariaGps = jTextGpsFrutaria.getText();
        String[] coords = frutariaGps.split("-");
        Gps auxiliar = new Gps(Double. parseDouble(coords[0]),Double. parseDouble(coords[1]));
        
        Frutaria aux = new Frutaria(Integer.parseInt(frutariaNumProdutos),Double. parseDouble(frutariaValorMedioFaturacao),Double. parseDouble(frutariavalAnualLimpeza),"Mercearia",frutariaNome,frutariaDistrito,auxiliar);
        lista.add(aux);
        dtm.setRowCount(0);//reset data model
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).lucroAnual() > 0){
                 lucro = "sim";
            }else{lucro = "não";}
            Object[] objs = {lista.get(i).getNomeEmpresa(),lista.get(i).getCategoriaEmpresa(),lista.get(i).getDistritoEmpresa(),lista.get(i).despesaAnual(),lista.get(i).receitaAnual(),lucro};
            dtm.addRow(objs);}
        
    }                                        

    //*Delete*/
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        
        if(jTable2.getSelectionModel().isSelectionEmpty()){JOptionPane.showMessageDialog(null,"Selecione uma empresa");}else{
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Delete this data", "Delete", dialogButton);
        row = jTable2.getSelectedRow();
        if(dialogResult == 0) {
            dtm.removeRow(row);
            lista.remove(row);
            dtm.setRowCount(0);
            for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).lucroAnual() > 0){
                 lucro = "sim";
            }else{lucro = "não";}
            Object[] objs = {lista.get(i).getNomeEmpresa(),lista.get(i).getCategoriaEmpresa(),lista.get(i).getDistritoEmpresa(),lista.get(i).despesaAnual(),lista.get(i).receitaAnual(),lucro};
            dtm.addRow(objs);}
            
        } 
        }
    }                                        

    //** CAFE */
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {                                         

        String cafeNome = jTextNomeCafe.getText();
        String cafeDistrito = jTextDistritoCafe.getText();
        String cafeDiario = jTextCafeDiarioCafe.getText();
        String cafeGps = jTextGPSCafe.getText();
        String[] coords = cafeGps.split("-");
        Gps auxiliar = new Gps(Double. parseDouble(coords[0]),Double. parseDouble(coords[1]));
        String cafeCustoEmpregados = jTextCustoEmpregadosCafe.getText();
        String cafeNumEmpregados = jTextNumEmpregadosCafe.getText();
        String cafeClientesDiario = jTextClientesDiarioCafe.getText();
        String cafeFaturacaoAnual = jTextFaturacaoAnualCafe.getText();
        String cafeSalarioAnual = jTextSalarioAnualCafe.getText();

        Cafe aux = new Cafe(Integer.parseInt(cafeClientesDiario),Integer.parseInt(cafeDiario),Double. parseDouble(cafeFaturacaoAnual),Double. parseDouble(cafeCustoEmpregados),Integer.parseInt(cafeNumEmpregados),Double. parseDouble(cafeSalarioAnual),"Restaurante-Cafe",cafeNome,cafeDistrito,auxiliar);
        
        lista.add(aux);
        dtm.setRowCount(0);//reset data model
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).lucroAnual() > 0){
                 lucro = "sim";
            }else{lucro = "não";}
            Object[] objs = {lista.get(i).getNomeEmpresa(),lista.get(i).getCategoriaEmpresa(),lista.get(i).getDistritoEmpresa(),lista.get(i).despesaAnual(),lista.get(i).receitaAnual(),lucro};
            dtm.addRow(objs);}
        
      
    }                                        

    //** Pastelaria */
    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {                                          
       
        String pastelariaNome = jTextNomePastelaria.getText();
        String pastelariaDistrito = jTextDistritoPastelaria.getText();
        String pastelariaDiario = jTextBolosDiariosPastelaria.getText();
        String pastelariaGps = jTextGPSPastelaria.getText();
        String[] coords = pastelariaGps.split("-");
        Gps auxiliar = new Gps(Double. parseDouble(coords[0]),Double. parseDouble(coords[1]));
        String pastelariaCustoEmpregados = jTextCustoEmpregadosPastelaria.getText();
        String pastelariaNumEmpregados = jTextNumEmpregadosPastelaria.getText();
        String pastelariaClientesDiario = jTextClientesDiarioPastelaria.getText();
        String pastelariaFaturacaoAnual = jTextFaturacaoAnualPastelaria.getText();
        String pastelariaSalarioAnual = jTextSalarioAnualPastelaria.getText();
        
        Pastelaria aux = new Pastelaria(Integer.parseInt(pastelariaClientesDiario),Integer.parseInt(pastelariaDiario),Double. parseDouble(pastelariaFaturacaoAnual),Double. parseDouble(pastelariaCustoEmpregados),Integer.parseInt(pastelariaNumEmpregados),Double. parseDouble(pastelariaSalarioAnual),"Restauração",pastelariaNome,pastelariaDistrito,auxiliar);
        
        lista.add(aux);
        dtm.setRowCount(0);//reset data model
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).lucroAnual() > 0){
                 lucro = "sim";
            }else{lucro = "não";}
            Object[] objs = {lista.get(i).getNomeEmpresa(),lista.get(i).getCategoriaEmpresa(),lista.get(i).getDistritoEmpresa(),lista.get(i).despesaAnual(),lista.get(i).receitaAnual(),lucro};
            dtm.addRow(objs);}
        
    }                                         

    private void jTextDistritoCafeActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        
    }                                                 

    private void jTextCustoEmpregadosCafeActionPerformed(java.awt.event.ActionEvent evt) {                                                         

    }                                                        

    private void jTextDistritoPastelariaActionPerformed(java.awt.event.ActionEvent evt) {                                                        
        
    }                                                       

    private void jTextCustoEmpregadosPastelariaActionPerformed(java.awt.event.ActionEvent evt) {                                                               
        
    }                                                              

    private void jTextDistritoRestaurante1ActionPerformed(java.awt.event.ActionEvent evt) {                                                          
      
    }                                                         

    private void jTextCustoEmpregadosRestaurante1ActionPerformed(java.awt.event.ActionEvent evt) {                                                                 
        
    }                                                                

    //** Restaurante Local */
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        
        String restaurante1Nome = jTextNomeRestaurante1.getText();
        String restaurante1Distrito = jTextDistritoRestaurante1.getText();
        String restaurante1DiasFuncAnual = jTextDiasFuncAnualRestaurante1.getText();
        String restaurante1Gps = jTextGpsRestaurante1.getText();
        String[] coords = restaurante1Gps.split("-");
        Gps auxiliar = new Gps(Double. parseDouble(coords[0]),Double. parseDouble(coords[1]));
        String restaurante1SalarioAnual = jTextSalarioAnualRestaurante1.getText();
        String restaurante1CustoEmpregados = jTextCustoEmpregadosRestaurante1.getText();
        String restaurante1NumeroEsplanadas = jTextNumMesasEsplanadaRestaurante1.getText();
        String restaurante1NumEmpregados = jTextNumEmpregadosRestaurante1.getText();
        String resaurante1ClientesDiario = jTextClientesDiarioRestaurante1.getText();
        String resaurante1FaturacaoDiaria = jTextFaturacaoDiariaRestaurante1.getText();
        String resaurante1LicencaAnualEsplanada = jTextLicencaAnualEsplanadaRestaurante1.getText();
        String resaurante1NumMesasInt = jTextNumMesasIntRestaurante1.getText();

        Local aux = new Local(Integer.parseInt(resaurante1NumMesasInt),Integer.parseInt(restaurante1NumeroEsplanadas),Double. parseDouble(resaurante1LicencaAnualEsplanada),Double. parseDouble(resaurante1FaturacaoDiaria),Integer.parseInt(resaurante1ClientesDiario),Integer.parseInt(restaurante1DiasFuncAnual),Double. parseDouble(restaurante1CustoEmpregados),Integer.parseInt(restaurante1NumEmpregados),Double. parseDouble(restaurante1SalarioAnual),"Restauração",restaurante1Nome,restaurante1Distrito,auxiliar);
        
        lista.add(aux);
        dtm.setRowCount(0);//reset data model
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).lucroAnual() > 0){
                 lucro = "sim";
            }else{lucro = "não";}
            Object[] objs = {lista.get(i).getNomeEmpresa(),lista.get(i).getCategoriaEmpresa(),lista.get(i).getDistritoEmpresa(),lista.get(i).despesaAnual(),lista.get(i).receitaAnual(),lucro};
            dtm.addRow(objs);}
        
    }                                        

    private void jTextBolosDiariosPastelariaActionPerformed(java.awt.event.ActionEvent evt) {                                                            
        
    }                                                           

    //** Mercado */
    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        
        String mercadoNome = jTextNomeMercado.getText();
        String mercadoDistrito = jTextDistritoMercado.getText();
        String mercadoFaturacaoAnual = jTextFaturacaoAnualMercado.getText();
        String mercadoGps = jTextGpsMercado.getText();
        String[] coords = mercadoGps.split("-");
        Gps auxiliar = new Gps(Double. parseDouble(coords[0]),Double. parseDouble(coords[1]));
        String mercadoLimpezaAnual = jTextLimpezaAnualMercado.getText();
        String mercadoAreaCorredor = jTextAreaCorredorMercado.getText();
        String mercadoTipo = jTextTipoMercado.getText();
        Mercado aux = new Mercado(mercadoTipo,Double. parseDouble(mercadoAreaCorredor),Double. parseDouble(mercadoFaturacaoAnual),Double. parseDouble(mercadoLimpezaAnual),"Merciaria",mercadoNome,mercadoDistrito,auxiliar);
        
        lista.add(aux);
        dtm.setRowCount(0);//reset data model
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).lucroAnual() > 0){
                 lucro = "sim";
            }else{lucro = "não";}
            Object[] objs = {lista.get(i).getNomeEmpresa(),lista.get(i).getCategoriaEmpresa(),lista.get(i).getDistritoEmpresa(),lista.get(i).despesaAnual(),lista.get(i).receitaAnual(),lucro};
            dtm.addRow(objs);}
        
    }                                         

    private void jTextDistritoMercadoActionPerformed(java.awt.event.ActionEvent evt) {                                                     
       
    }                                                    

    private void jTextvalMedioFaturacaoFrutariaActionPerformed(java.awt.event.ActionEvent evt) {                                                               
        
    }                                                              

    private void jTextnumProdutosFrutariaActionPerformed(java.awt.event.ActionEvent evt) {                                                         
        
    }                                                        

    private void jTextvalAnualLimpezaFrutariaActionPerformed(java.awt.event.ActionEvent evt) {                                                             
       
    }                                                            
       //** Botao Exit */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        Ficheiro f = new Ficheiro();
        StarThrive a = new StarThrive();
        a.setEmpresas(lista);
        f.escreverFicheiroObjetos(a);
        System.exit(0);
    }                                        

    //** Fast Food */
    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        
        String restaurante2Nome = jTextNomeRestaurante2.getText();
        String restaurante2Distrito = jTextDistritoRestaurante2.getText();
        String restaurante2DiasFuncAnual = jTextDiasFuncAnualRestaurante2.getText();
        String restaurante2Gps = jTextGpsRestaurante2.getText();
        String[] coords = restaurante2Gps.split("-");
        Gps auxiliar = new Gps(Double. parseDouble(coords[0]),Double. parseDouble(coords[1]));
        String restaurante2SalarioAnual = jTextSalarioAnualRestaurante2.getText();
        String restaurante2CustoEmpregados = jTextCustoEmpregadosRestaurante2.getText();
        String restaurante2ClientesDiarioDrive = jTextClientesDiarioDriveRestaurante2.getText();
        String restaurante2NumEmpregados = jTextNumEmpregadosRestaurante2.getText();
        String resaurante2ClientesDiario = jTextClientesDiarioRestaurante2.getText();
        String resaurante2FaturacaoDiaria = jTextFaturacaoDiariaRestaurante2.getText();
        String resaurante2FaturacaoDiariaDrive = jTextFaturacaoDiariaDriveRestaurante2.getText();
        String resaurante2NumMesasInt = jTextNumMesasIntRestaurante2.getText();

        FastFood aux = new FastFood(Integer.parseInt(resaurante2NumMesasInt),Double. parseDouble(resaurante2FaturacaoDiaria),Integer.parseInt(restaurante2ClientesDiarioDrive),Double. parseDouble(resaurante2FaturacaoDiariaDrive),Integer.parseInt(resaurante2ClientesDiario),Integer.parseInt(restaurante2DiasFuncAnual),Double. parseDouble(restaurante2CustoEmpregados),Integer.parseInt(restaurante2NumEmpregados),Double. parseDouble(restaurante2SalarioAnual),"Restauração",restaurante2Nome,restaurante2Distrito,auxiliar);
        lista.add(aux);
        dtm.setRowCount(0);//reset data model
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).lucroAnual() > 0){
                 lucro = "sim";
            }else{lucro = "não";}
            Object[] objs = {lista.get(i).getNomeEmpresa(),lista.get(i).getCategoriaEmpresa(),lista.get(i).getDistritoEmpresa(),lista.get(i).despesaAnual(),lista.get(i).receitaAnual(),lucro};
            dtm.addRow(objs);
        }
        
        
    }                                         

    private void jTextFaturacaoDiariaDriveRestaurante2ActionPerformed(java.awt.event.ActionEvent evt) {                                                                      
        
    }                                                                     

    private void jTextCustoEmpregadosRestaurante2ActionPerformed(java.awt.event.ActionEvent evt) {                                                                 
        
    }                                                                

    private void jTextDistritoRestaurante2ActionPerformed(java.awt.event.ActionEvent evt) {                                                          
        
    }                                                         

    private void jTextNomeupActionPerformed(java.awt.event.ActionEvent evt) {                                            
       
    }                                           

    private void jTextCategoriaupActionPerformed(java.awt.event.ActionEvent evt) {                                                 
      
    }                                                

    private void jTextDistritoupActionPerformed(java.awt.event.ActionEvent evt) {                                                
       
    }                                               

    //** Botao Update */
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        
        row = jTable2.getSelectedRow(); 
        String nomeup = jTextNomeup.getText();
        String categoriaup = jTextCategoriaup.getText();
        String distritoup = jTextDistritoup.getText();
        String Gpsup = jTextGpsup.getText();
        String[] coords;
        Gps auxiliar = new Gps(); 
        if(!jTextGpsup.getText().isBlank()){
            coords = Gpsup.split("-");
            auxiliar = new Gps(Double. parseDouble(coords[0]),Double. parseDouble(coords[1]));}
        if(jTextNomeup.getText().isBlank()){}else{lista.get(row).setNomeEmpresa(nomeup);}
        if(jTextCategoriaup.getText().isBlank()){}else{lista.get(row).setCategoriaEmpresa(categoriaup);}
        if(jTextDistritoup.getText().isBlank()){}else{lista.get(row).setDistritoEmpresa(distritoup);}
        if(jTextGpsup.getText().isBlank()){}else{lista.get(row).setCoordenadasGps(auxiliar);}
        if(jTable2.getSelectionModel().isSelectionEmpty()){JOptionPane.showMessageDialog(null,"Selecione uma empresa");}
        dtm.setRowCount(0);//reset data model
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).lucroAnual() > 0){
                 lucro = "sim";
            }else{lucro = "não";}
            Object[] objs = {lista.get(i).getNomeEmpresa(),lista.get(i).getCategoriaEmpresa(),lista.get(i).getDistritoEmpresa(),lista.get(i).despesaAnual(),lista.get(i).receitaAnual(),lucro};
            dtm.addRow(objs);
        }
        
        
        
        
        
             
    }                                        

    private void jScrollPane3MouseClicked(java.awt.event.MouseEvent evt) {                                          
        
    }                                         

    
       //** Mais Informacoes das Empresas */
    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {                                     
        
        int index = jTable2.getSelectedRow();
        JOptionPane.showMessageDialog(null,lista.get(index).toString());
        
    }                                    

    private void jTextTipoMercadoActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        
    }                                                

    private void jTextTipoMercadoFocusGained(java.awt.event.FocusEvent evt) {                                             
        
    }                                            

    
       //** Protecao da String do tipo de mercado **/
    private void jTextTipoMercadoFocusLost(java.awt.event.FocusEvent evt) {                                           
        String mercadoTipo = jTextTipoMercado.getText();
        if(mercadoTipo.equals("min") || mercadoTipo.equals("super") || mercadoTipo.equals("hiper")){
           }else{ 
        JOptionPane.showMessageDialog(null,"Tipo de Mercado tem de ser min, super ou hiper!");
        mercadoTipo = null;
        jTextTipoMercado.setText("");
      }
        System.out.println(mercadoTipo);
        
    }                                          

    private void jTextGPSPastelariaFocusLost(java.awt.event.FocusEvent evt) {                                             
      
        
    }                                            

    private void jTextGpsupActionPerformed(java.awt.event.ActionEvent evt) {                                           
        
    }                                          

    private void jRMaiorReceitaActionPerformed(java.awt.event.ActionEvent evt) {                                               
        
    }                                              

    public ArrayList<Empresa> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Empresa> lista) {
        this.lista = lista;
    }



    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jMDespesamenor;
    private javax.swing.JTextField jMLucroAnual;
    private javax.swing.JTextField jMMaiorReceita;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTextField jRLucroAnual;
    private javax.swing.JTextField jRMaiorReceita;
    private javax.swing.JTextField jRMenorDespesa;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextAreaCorredorMercado;
    private javax.swing.JTextField jTextBolosDiariosPastelaria;
    private javax.swing.JTextField jTextCafeDiarioCafe;
    private javax.swing.JTextField jTextCategoriaup;
    private javax.swing.JTextField jTextClientesDiarioCafe;
    private javax.swing.JTextField jTextClientesDiarioDriveRestaurante2;
    private javax.swing.JTextField jTextClientesDiarioPastelaria;
    private javax.swing.JTextField jTextClientesDiarioRestaurante1;
    private javax.swing.JTextField jTextClientesDiarioRestaurante2;
    private javax.swing.JTextField jTextCustoEmpregadosCafe;
    private javax.swing.JTextField jTextCustoEmpregadosPastelaria;
    private javax.swing.JTextField jTextCustoEmpregadosRestaurante1;
    private javax.swing.JTextField jTextCustoEmpregadosRestaurante2;
    private javax.swing.JTextField jTextDiasFuncAnualRestaurante1;
    private javax.swing.JTextField jTextDiasFuncAnualRestaurante2;
    private javax.swing.JTextField jTextDistritoCafe;
    private javax.swing.JTextField jTextDistritoFrutaria;
    private javax.swing.JTextField jTextDistritoMercado;
    private javax.swing.JTextField jTextDistritoPastelaria;
    private javax.swing.JTextField jTextDistritoRestaurante1;
    private javax.swing.JTextField jTextDistritoRestaurante2;
    private javax.swing.JTextField jTextDistritoup;
    private javax.swing.JTextField jTextFaturacaoAnualCafe;
    private javax.swing.JTextField jTextFaturacaoAnualMercado;
    private javax.swing.JTextField jTextFaturacaoAnualPastelaria;
    private javax.swing.JTextField jTextFaturacaoDiariaDriveRestaurante2;
    private javax.swing.JTextField jTextFaturacaoDiariaRestaurante1;
    private javax.swing.JTextField jTextFaturacaoDiariaRestaurante2;
    private javax.swing.JTextField jTextGPSCafe;
    private javax.swing.JTextField jTextGPSPastelaria;
    private javax.swing.JTextField jTextGpsFrutaria;
    private javax.swing.JTextField jTextGpsMercado;
    private javax.swing.JTextField jTextGpsRestaurante1;
    private javax.swing.JTextField jTextGpsRestaurante2;
    private javax.swing.JTextField jTextGpsup;
    private javax.swing.JTextField jTextLicencaAnualEsplanadaRestaurante1;
    private javax.swing.JTextField jTextLimpezaAnualMercado;
    private javax.swing.JTextField jTextNomeCafe;
    private javax.swing.JTextField jTextNomeFrutaria;
    private javax.swing.JTextField jTextNomeMercado;
    private javax.swing.JTextField jTextNomePastelaria;
    private javax.swing.JTextField jTextNomeRestaurante1;
    private javax.swing.JTextField jTextNomeRestaurante2;
    private javax.swing.JTextField jTextNomeup;
    private javax.swing.JTextField jTextNumEmpregadosCafe;
    private javax.swing.JTextField jTextNumEmpregadosPastelaria;
    private javax.swing.JTextField jTextNumEmpregadosRestaurante1;
    private javax.swing.JTextField jTextNumEmpregadosRestaurante2;
    private javax.swing.JTextField jTextNumMesasEsplanadaRestaurante1;
    private javax.swing.JTextField jTextNumMesasIntRestaurante1;
    private javax.swing.JTextField jTextNumMesasIntRestaurante2;
    private javax.swing.JTextField jTextSalarioAnualCafe;
    private javax.swing.JTextField jTextSalarioAnualPastelaria;
    private javax.swing.JTextField jTextSalarioAnualRestaurante1;
    private javax.swing.JTextField jTextSalarioAnualRestaurante2;
    private javax.swing.JTextField jTextTipoMercado;
    private javax.swing.JTextField jTextnumProdutosFrutaria;
    private javax.swing.JTextField jTextvalAnualLimpezaFrutaria;
    private javax.swing.JTextField jTextvalMedioFaturacaoFrutaria;
    // End of variables declaration                   
}
