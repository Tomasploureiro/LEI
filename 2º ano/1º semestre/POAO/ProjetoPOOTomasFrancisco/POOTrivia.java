package POOTrivia;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("serial")
public class POOTrivia extends JFrame {
	/**
     * Jogo
     */
	private Jogo jogo;
    /**
     * Botão para escolher a pergunta
     */
    private JButton botaoEscolherPergunta;
        /**
     * Botão para a proxima pergunta
     */
    private JButton botaoProximaPergunta;
    /**
     * Label para a pergunta
     */
    private JLabel labelPergunta;
    /**
     * Painel das opções
     */
    private JPanel panelOpcoes;
    /**
     * label para a pontuação
     */
    private JLabel labelPontuacao;
    /**
     * boolean para a primeira escolha
     */
    private boolean primeiraEscolha = true;
    /*
     * boolean para ver se escolheu a opção
     */
    private boolean escolheuOpcao = false;
    /**
     * Pergunta atual
     */
    private Pergunta perguntaAtual;
    /**
     * Registos de jogos
     */
    private List<RegistoJogo> registros;
    
    /**
     * Função do menu de interface grafica
     */
    public POOTrivia() {
        

        setTitle("POOTrivia");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel painel = new JPanel();
        
      
        botaoEscolherPergunta = new JButton("Come�ar Jogo");
        botaoEscolherPergunta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	jogo = new Jogo();
                jogo.carregarPerguntas("C:\\Users\\User\\Desktop\\uni\\POAO\\POOTrivia\\perguntas.txt");
                
                exibirPerguntaAleatoria();
            }
        });

        painel.add(botaoEscolherPergunta);
        getContentPane().add(painel);

        setLocationRelativeTo(null);
        setVisible(true);
    }


    
    /** 
     * @return Jogo
     */
    public Jogo getJogo() {
        return this.jogo;
    }

    
    /** 
     * @param jogo Atribui o valor a jogo
     */
    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    
    /** 
     * @return JButton
     */
    public JButton getBotaoEscolherPergunta() {
        return this.botaoEscolherPergunta;
    }

    /**
     * @param botaoEscolherPergunta Atribui o valor a botaoEscolherPergunta 
     * */
    public void setBotaoEscolherPergunta(JButton botaoEscolherPergunta) {
        this.botaoEscolherPergunta = botaoEscolherPergunta;
    }
    
    /** 
     * @return JButton
     */
    public JButton getBotaoProximaPergunta() {
        return this.botaoProximaPergunta;
    }

    
    /** 
     * @param botaoProximaPergunta Atribui o valor a botaoProximaPregunta
     */
    public void setBotaoProximaPergunta(JButton botaoProximaPergunta) {
        this.botaoProximaPergunta = botaoProximaPergunta;
    }

    /** 
     * @return JLabel
     */
    public JLabel getLabelPergunta() {
        return this.labelPergunta;
    }

    /** 
     * @param labelPergunta Atribui o valor a labelPergunta
     */
    public void setLabelPergunta(JLabel labelPergunta) {
        this.labelPergunta = labelPergunta;
    }
    
    /** 
     * @return JPanel
     */
    public JPanel getPanelOpcoes() {
        return this.panelOpcoes;
    }

    
    /** 
     * @param panelOpcoes Atribui o valor a painelOpcoes
     */
    public void setPanelOpcoes(JPanel panelOpcoes) {
        this.panelOpcoes = panelOpcoes;
    }
    
    /** 
     * @return JLabel
     */
    public JLabel getLabelPontuacao() {
        return this.labelPontuacao;
    }

    /** 
     * @param labelPontuacao Atribui o valor a labelPontuacao
     */
    public void setLabelPontuacao(JLabel labelPontuacao) {
        this.labelPontuacao = labelPontuacao;
    }

    /** 
     * @return boolean
     */
    public boolean isPrimeiraEscolha() {
        return this.primeiraEscolha;
    }

    /**
     * @return boolean
     */
    public boolean getPrimeiraEscolha() {
        return this.primeiraEscolha;
    }

    /**
     * @param primeiraEscolha Atribui o valor a primeiraEscolha
     */
    public void setPrimeiraEscolha(boolean primeiraEscolha) {
        this.primeiraEscolha = primeiraEscolha;
    }

    /** 
     * @return boolean
     */
    public boolean isEscolheuOpcao() {
        return this.escolheuOpcao;
    }

    /** 
     * @return boolean
     */
    public boolean getEscolheuOpcao() {
        return this.escolheuOpcao;
    }
    
    /** 
     * @param escolheuOpcao Atribui o valor a escolheuOpcao
     */
    public void setEscolheuOpcao(boolean escolheuOpcao) {
        this.escolheuOpcao = escolheuOpcao;
    }

    /** 
     * @return Pergunta
     */
    public Pergunta getPerguntaAtual() {
        return this.perguntaAtual;
    }

    /**
     * @param perguntaAtual Atribui o valor a perguntaAtual
     */
    public void setPerguntaAtual(Pergunta perguntaAtual) {
        this.perguntaAtual = perguntaAtual;
    }

    /** 
     * @return registros
     */
    public List<RegistoJogo> getRegistros() {
        return this.registros;
    }

    /**
     * @param registros Atribui o valor a registros
     */
    public void setRegistros(List<RegistoJogo> registros) {
        this.registros = registros;
    }

    /**
     * Função para exibir a perguta escolhida
     */
    private void exibirPerguntaAleatoria() {
        if (primeiraEscolha) {
        	getContentPane().removeAll();
            revalidate();
            repaint();
            primeiraEscolha = false;
        }

        if(jogo.obterNumeroPerguntasGeradas() < 5) {
        	perguntaAtual = jogo.escolherPerguntaAleatoria();

            labelPergunta = new JLabel();
            labelPergunta.setHorizontalAlignment(SwingConstants.CENTER);
            labelPergunta.setText(perguntaAtual.getTextoPergunta());
            Font fonte = labelPergunta.getFont();
            labelPergunta.setFont(new Font(fonte.getName(), Font.PLAIN, 20));

            panelOpcoes = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 5, 5, 5);

            ArrayList<String> opcoes = jogo.getOpcoesPergunta(perguntaAtual);
            
            for (int i = 0; i < opcoes.size(); i++) {
                JButton botaoOpcao = new JButton();
                botaoOpcao.setText((i + 1) + ") " + opcoes.get(i));
                botaoOpcao.addActionListener(new OpcaoActionListener(i, perguntaAtual.getResposta(), botaoOpcao));
                gbc.gridy = i;
                panelOpcoes.add(botaoOpcao, gbc);
            }

            botaoProximaPergunta = new JButton("Pr�xima Pergunta");
            botaoProximaPergunta.setEnabled(false);
            botaoProximaPergunta.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    removerPerguntaAtual();
                    exibirPerguntaAleatoria();
                }
            });

            labelPontuacao = new JLabel("Pontua��o: " + jogo.obterPontos());

            JPanel panelPrincipal = new JPanel(new GridBagLayout());
            gbc.gridx = 0;
            gbc.gridy = 0;
            panelPrincipal.add(labelPergunta, gbc);

            gbc.gridy = 1;
            panelPrincipal.add(panelOpcoes, gbc);

            gbc.gridy = 2;
            panelPrincipal.add(botaoProximaPergunta, gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridheight = 3;
            panelPrincipal.add(labelPontuacao, gbc);

            getContentPane().add(panelPrincipal);

            revalidate();
            repaint();
        }
        else {
        	 getContentPane().removeAll();
        	 
        	 setLayout(new BorderLayout());
        	 
        	 JLabel labelFimJogo = new JLabel("Fim do Jogo! Pontua��o final: " + jogo.obterPontos());
        	 labelFimJogo.setHorizontalAlignment(SwingConstants.CENTER);
        	 getContentPane().add(labelFimJogo, BorderLayout.NORTH);
        	 
        	 JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        	 JTextField textFieldNome = new JTextField("Digite seu nome", 15);
             centerPanel.add(textFieldNome);
             getContentPane().add(centerPanel, BorderLayout.CENTER);
             
             JButton buttonSubmit = new JButton("Submit");
             buttonSubmit.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     String nomeJogador = textFieldNome.getText();
                     System.out.println("Nome do jogador: " + nomeJogador);
                     jogo.acabarJogo(nomeJogador);
                     leaderBoard();
                 }
             });
             getContentPane().add(buttonSubmit, BorderLayout.SOUTH);

             revalidate();
             repaint();
        }
        
    }

    /**
     * Função para remover a pergunta atual da interface
     */
    private void removerPerguntaAtual() {
        getContentPane().removeAll();           
        revalidate();
        repaint();
        escolheuOpcao = false;
    }
    
    /**
     * Função para controlar o leaderboard
     */
    private void leaderBoard() {
    	
    	String pastaPath = "Jogos";
    	
    	File pasta = new File(pastaPath);
        File[] arquivos = pasta.listFiles();
        
        registros = new ArrayList<>();
        
        if (arquivos != null) {
        	for (File arquivo : arquivos) {
        		List<String> linhas = new ArrayList<>();
        		try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        linhas.add(linha);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        		
        		int pontos = 0;
        		String dataHora = null, nomeJogador = null;
        		
        		for (String linha : linhas) {
        			if (linha.startsWith("Data e hora:")) {
        				dataHora = linha.substring("Data e hora: ".length());
        			}
        			else if (linha.startsWith("Nome do jogador:")) {
        				nomeJogador = linha.substring("Nome do jogador: ".length());
        			}
        			else if (linha.startsWith("Pontuacao:")) {
        				pontos = Integer.parseInt(linha.substring("Pontuacao: ".length()));
        			}
        		}
        		
        		registros.add(new RegistoJogo(pontos, dataHora, nomeJogador));
        	}
        }
        

        Collections.sort(registros, (r1, r2) -> {
            int pointsComparison = Integer.compare(r2.getPontos(), r1.getPontos());
            if (pointsComparison != 0) {
                return pointsComparison;
            }

            return r1.getDataHora().compareTo(r2.getDataHora());
        });
            
            	
    	getContentPane().removeAll();
    	
    	setLayout(new GridLayout(registros.size() + 1, 1));
        JLabel headerLabel = new JLabel("Leaderboard");
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(headerLabel);
        
        for (int i = 0; i < 3; i++) {
        	RegistoJogo registo = registros.get(i);
            JLabel entryLabel = new JLabel("Nome Jogador: " + registo.getNomeJogador() +
                    "       Pontos: " + registo.getPontos() +
                    "       DataHora: " + registo.getDataHora());
            entryLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(entryLabel);
        }
        
        JButton returnButton = new JButton("Comecar novo jogo");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	getContentPane().removeAll();
            	
                jogo = new Jogo();
                jogo.carregarPerguntas("C:\\Users\\User\\Desktop\\uni\\POAO\\POOTrivia\\perguntas.txt");
                primeiraEscolha = true;
                setLayout(new GridBagLayout());
                revalidate();
                repaint();
                exibirPerguntaAleatoria();
            }
        });
        add(returnButton);
    	
        revalidate();
        repaint();
    }

    /**
     * Função para receber os inputs do utilizador
     */
    public class OpcaoActionListener implements ActionListener {
        private int opcaoIndex;
        private String respostaCorreta;
        private JButton botaoOpcao;

        public OpcaoActionListener(int index, String respostaCorreta, JButton botaoOpcao) {
            this.opcaoIndex = index;
            this.respostaCorreta = respostaCorreta;
            this.botaoOpcao = botaoOpcao;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!escolheuOpcao) {
            	System.out.println(jogo.getOpcoesPergunta(perguntaAtual).get(opcaoIndex));
                if (jogo.verificarResposta(perguntaAtual, jogo.getOpcoesPergunta(perguntaAtual).get(opcaoIndex))) {
                    botaoOpcao.setBackground(Color.GREEN);
                } else {
                    botaoOpcao.setBackground(Color.RED);
                }

                for (Component component : panelOpcoes.getComponents()) {
                    if (component instanceof JButton) {
                        JButton button = (JButton) component;
                        button.setEnabled(false);
                    }
                }

                botaoProximaPergunta.setEnabled(true);
                escolheuOpcao = true;
            }

            labelPontuacao.setText("Pontua��o: " + jogo.obterPontos());
        }	
    }
    
    public static void main(String[] args) {
		POOTrivia pootrivia = new POOTrivia();

	}
}


