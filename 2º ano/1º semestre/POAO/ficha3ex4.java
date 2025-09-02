public class ficha3ex4 {
    public static void main(String[] args) {
        char[] vogais = { 'a', 'e', 'i', 'o', 'u' };
        String text = "andreia";
        String textfinal = new String();
        for (int i = 0; i < text.length(); i++) {
            boolean v1 = false;
            boolean v2 = false;
            textfinal += text.charAt(i);
            for (int j : vogais) {
                if (v1 == false && text.charAt(i) == j) {
                    v1 = true;
                }
                if (i + 1 < text.length()) {
                    if (v2 == false && text.charAt(i + 1) == j) {
                        v2 = true;
                    }
                }
            }
            if (v1 == true && v2 == true)
                textfinal += 'p';
        }
        System.out.print(textfinal);
    }
}
