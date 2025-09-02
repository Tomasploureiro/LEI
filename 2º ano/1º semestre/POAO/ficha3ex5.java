public class ficha3ex5 {
    public static void main(String[] args) {
        String frase = "o gato comeu o gato biscoito";
        String palavra = "gato";
        int cont = 0;
        for(int i = 0; i<frase.length(); i++){
            boolean v = false;
            if(frase.charAt(i) == palavra.charAt(0)){
                for(int j = 1; j<palavra.length(); j++){
                    if(frase.charAt(i+j) == palavra.charAt(j)){
                        v = true;
                    }
                    else{
                        v = false;
                        break;
                    }
                }
                if (v == true)
                    cont++;
            }
        }
        System.out.print(cont);
    }
}
