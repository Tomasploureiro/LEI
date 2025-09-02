import java.util.ArrayList;

public class Personagem {
    private String nome;
    private double experiencia;
    private double forca;
    private double agilidade;
    private double inteligencia;

    public Personagem(){
    }

    public Personagem(String nome, double experiencia, double forca, double agilidade, double inteligencia){
        this.nome = nome;
        this.experiencia = experiencia;
        this.forca = forca;
        this.agilidade = agilidade;
        this.inteligencia = inteligencia;
    }
    
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setExperiencia(double experiencia) {
        this.experiencia = experiencia;
    }

    public double getExperiencia() {
        return experiencia;
    }

    public void setForca(double forca) {
        this.forca = forca;
    }

    public double getForca() {
        return forca;
    }

    public void setAgilidade(double agilidade) {
        this.agilidade = agilidade;
    }

    public double getAgilidade() {
        return agilidade;
    }

    public void setInteligencia(double inteligencia) {
        this.inteligencia = inteligencia;
    }
 
    public double getInteligencia() {
        return inteligencia;
    }

    public String toString(){
        String experienciaPrint = String.format("%.2f", getExperiencia());
        String forcaPrint = String.format("%.2f", getForca());
        String agilidadePrint = String.format("%.2f", getAgilidade());
        String inteligenciaPrint = String.format("%.2f", getInteligencia());
        return "Nome: " + getNome() + " " + "Experiencia: " + experienciaPrint + " " + "Forca: " + forcaPrint + " "
        + "Agilidade: " + agilidadePrint + " " + "Inteligencia: " + inteligenciaPrint + " ";
    }
    
    public void aumentarExperiencia(){}

    public void imprimirEquipamento(String armaCurta, boolean armadura, String mochila, String armaLonga, int armaduracheck){}

    public static void imprimirTodos(ArrayList<Personagem> personagens){
        for(Personagem p : personagens){
            System.out.println(p.toString());
        }
    }

    public static void imprimirExperiencia(ArrayList<Personagem>personagens, double n){
        for(Personagem p : personagens){
            if(p.getExperiencia() > n)
                System.out.println(p.toString());
        }
    }
}
