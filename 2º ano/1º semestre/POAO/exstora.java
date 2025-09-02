public class exstora{
    public static void main(String[] argv){
        int size = 4;
        float notaex1 = 1, notaex2 = 0, notaproj = 6, notateste = 9;
        float[] tabela = new float[size];
        tabela[0] = notaex1;
        tabela[1] = notaex2;
        tabela[2] = notaproj;
        tabela[3] = notateste;
        for(int i = 0; i<tabela.length; i++)
            System.out.println(tabela[i]);
        for(float n : tabela)
            System.out.println(n);
    }
}