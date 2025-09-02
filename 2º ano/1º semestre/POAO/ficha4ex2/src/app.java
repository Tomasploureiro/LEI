public class app {
    public static void main(String[] args) {
        Angulo a1 = new Angulo(180);
        System.out.println(a1);
        System.out.println(a1.getGraus());
        Angulo a2 = new Angulo(90);
        Angulo res = a1.adicao(a2);
        System.out.println(res);
        Angulo res2 = a1.subtracao(a2);
        System.out.println(res2);

    }
}