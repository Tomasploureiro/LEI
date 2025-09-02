public class Mago extends Personagem {
    private String mochila;


    public Mago() {
    }

    public Mago(String nome, String mochila) {
        super(nome, Math.random() * (25-1), 2, 4, 9);
        this.mochila = mochila;
    }

    public String getMochila() {
        return this.mochila;
    }

    public void setMochila(String mochila) {
        this.mochila = mochila;
    }

    public String toString() {
        return super.toString() +  "Mochila: " + getMochila() + " ";
    }
    public void imprimirEquipamento(String armaCurta, boolean armadura, String mochila, String armaLonga, int armaduracheck){
        if(getMochila().equals(mochila)){
            System.out.println(toString());
        }
    }
    public void aumentarExperiencia(){
        System.out.println(toString());
        setExperiencia(getExperiencia()+1);
        setForca(getForca()*0.05+getForca());
        setAgilidade(getAgilidade()*0.1+getAgilidade());
        setInteligencia(getInteligencia()*0.2+getInteligencia());
        System.out.println(toString());
    }

}
