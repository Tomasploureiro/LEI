public class Mercenario extends Personagem {
    private String armaLonga;
    private double municoes;

    public Mercenario() {
    }

    public Mercenario(String nome, String armaLonga, double municoes) {
        super(nome, Math.random() * (25-1), 4, 10, 4);
        this.armaLonga = armaLonga;
        this.municoes = municoes;
    }

    public String getArmaLonga() {
        return this.armaLonga;
    }

    public void setArmaLonga(String armaLonga) {
        this.armaLonga = armaLonga;
    }

    public double getMunicoes() {
        return this.municoes;
    }

    public void setMunicoes(double municoes) {
        this.municoes = municoes;
    }

    public String toString() {
        String municoesPrint = String.format("%.0f", getMunicoes());
        return super.toString() +  "ArmaLonga: " + getArmaLonga() + " " + "Municoes: " + municoesPrint;
    }
    public void imprimirEquipamento(String armaCurta, boolean armadura, String mochila, String armaLonga, int armaduracheck){
        if(getArmaLonga().equals(armaLonga)){
            System.out.println(toString());
        }
    }
    public void aumentarExperiencia(){
        System.out.println(toString());
        setExperiencia(getExperiencia()+1);
        setForca(getForca()*0.08+getForca());
        setAgilidade(getAgilidade()*0.2+getAgilidade());
        setInteligencia(getInteligencia()*0.08+getInteligencia());
        System.out.println(toString());
    }
    

}
