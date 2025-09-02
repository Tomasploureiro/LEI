public class Guerreiro extends Personagem {
    
    private boolean armadura;
    private String armaCurta;

    public Guerreiro() {
    }

    public Guerreiro(String nome, boolean armadura, String armaCurta) {
        super(nome, Math.random() * (25-1), 10, 5, 3);
        this.armadura = armadura;
        this.armaCurta = armaCurta;
    }

    public boolean isArmadura() {
        return this.armadura;
    }

    public boolean getArmadura() {
        return this.armadura;
    }

    public void setArmadura(boolean armadura) {
        this.armadura = armadura;
    }


    public String getArmaCurta() {
        return this.armaCurta;
    }

    public void setArmaCurta(String armaCurta) {
        this.armaCurta = armaCurta;
    }

    public String toString() {
        return super.toString() + " " + "Armadura: " + (isArmadura() ? "Sim" : "Nao") + " " + "ArmaCurta: " + getArmaCurta();
    }
    public void imprimirEquipamento(String armaCurta, boolean armadura, String mochila, String armaLonga, int armaduracheck){
        if(getArmaCurta().equals((armaCurta)) && isArmadura() == (armadura) && armaduracheck == 1){
            System.out.println(toString());
        }else if(getArmaCurta().equals((armaCurta))){
            System.out.println(toString());
        }else if(isArmadura() == armadura && armaduracheck == 1){
            System.out.println(toString());
        }
    }
    public void aumentarExperiencia(){
        System.out.println(toString());
        setExperiencia(getExperiencia()+1);
        setForca(getForca()*0.2+getForca());
        setAgilidade(getAgilidade()*0.1+getAgilidade());
        setInteligencia(getInteligencia()*0.05+getInteligencia());
        System.out.println(toString());
    }

}
