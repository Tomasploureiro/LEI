public class ficha3ex6 {
    public static void main(String[] args) {
        String text = "A Maria compra uma camisa amarela";
        String[] palavras = text.split(" ");
        String newtext = new String();
        for (int i = 0; i < palavras.length; i++) {
            int cont = 0;
            for (int j = 0; j < palavras[i].length(); j++) {
                if (palavras[i].charAt(j) == 'a' || palavras[i].charAt(j) == 'A')
                    cont++;
            }
            if (cont >= 2) {
                newtext += palavras[i];
                newtext += " ";
            }
        }
        System.out.print(newtext);
    }
}
