public class ficha3ex1 {
    public static void main(String[] args) {
        String text = "O meu nome é Tomás";
        char retirar = ' ';
        String newtext = new String();

        for (int i = 0; i < text.length(); i++)
            if (text.charAt(i) != retirar)
                newtext += text.charAt(i);

        System.out.println(newtext);
    }

}
