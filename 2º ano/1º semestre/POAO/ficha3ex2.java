public class ficha3ex2 {
    public static void main(String[] args) {
        char[] vogais = { 'a', 'e', 'i', 'o', 'u' };
        String text = "ola brou";
        int cont = 0;
        for (int i = 0; i < text.length(); i++) {
            for (int j : vogais) {
                if (text.charAt(i) == j) {
                    cont++;
                    break;
                }
            }
        }
        System.out.print(cont);
    }
}
