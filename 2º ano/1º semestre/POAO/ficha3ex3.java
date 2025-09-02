public class ficha3ex3 {
    public static void main(String[] args) {
        String text = "aaaammaaa";
        double l = Math.floor(text.length() / 2);
        boolean verificador = true;
        for (int i = 0; i < l; i++) {
            if (text.charAt(i) != text.charAt(text.length() - i - 1)) {
                verificador = false;
                break;
            }
        }
        System.out.print(verificador);
    }
}
