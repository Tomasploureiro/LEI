package POOTrivia;

public class RegistoJogo {
	/**
	 * Pontos do jogo
	 */
	public int pontos;
	/**
	 * Data e hora do jogo
	 */
	public String dataHora;
	/**
	 * Nome do Jogador
	 */
	public String nomeJogador;
	
	/**
	 * Construtor por omissão
	 */
	public RegistoJogo(){}

	/**
	 * Construtor para criar os registos a cada jogo
	 * @param pontos pontos
	 * @param dataHora dataHora
	 * @param nomeJogador nomeJogador
	 */
	public RegistoJogo(int pontos, String dataHora, String nomeJogador) {
		this.pontos = pontos;
		this.dataHora = dataHora;
		this.nomeJogador = nomeJogador;
	}
	
	
	/** 
	 * @return pontos
	 */
	public int getPontos() {
		return pontos;
	}

	/** 
	 * @param pontos Atribui o valor a pontos
	 */
	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	/** 
	 * @return dataHora
	 */
	 
	public String getDataHora() {
		return dataHora;
	}

	/** 
	 * @param dataHora Atribui o valor a dataHora
	 */
	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}
	
	/**
	 * 
	 * @return nomeJogador
	 */
	public String getNomeJogador() {
		return nomeJogador;
	}
	/**
	 * 
	 * @param nomeJogador Atribui o valor a nomeJogador
	 */
	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}	
}
