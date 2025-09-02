public class Exercicio1 {
    public static void main(String[] args) {
        String URLs[] = {
            "https://www.dei.uc.pt/poao/exames",
            "http://www.scs.org/index.html",
            "https://www.nato.int/events",
            "https://www.btu.de/",
            "https://www.dei.uc.pt/poao/exames",
            "http://www.eth.ch/index.html",
            "http://www.osu.uk/"
        };
        String paises[][] = {
            { "pt", "Portugal" },
            { "org", "EUA" },
            { "fr", "França" },
            { "uk", "Reino Unido" },
            { "de", "Alemanha" },
            { "edu", "EUA" }
        };

        String[] nomesPaises = new String[paises.length];
        int[] contPaises = new int[paises.length];
        int[] contOutros = { 0 }; // Usando um array para armazenar o valor de contOutros

        contUrls(URLs, paises, nomesPaises, contPaises, contOutros);
        printContUrls(nomesPaises, contPaises, contOutros[0], paises);
    }

    public static String verificardominio(String url) {
        String url1[] = url.split("/", 0);
        String url2[] = url1[2].split("\\.", 0);
        return url2[url2.length - 1];
    }

    public static void contUrls(String[] URLs, String[][] paises, String[] nomesPaises, int[] contPaises, int[] contOutros) {
        for (int i = 0; i < URLs.length; i++) {
            String dominio = verificardominio(URLs[i]);
            boolean outro = false;

            for (int j = 0; j < paises.length; j++) {
                String dominioPais = paises[j][0];
                if (dominio.equals(dominioPais)) {
                    nomesPaises[j] = paises[j][1];
                    contPaises[j]++;
                    outro = true;
                    break;
                }
            }
            if (!outro) {
                contOutros[0]++; // Incrementando o valor de contOutros no array
            }
        }
    }

    public static void printContUrls(String[] nomesPaises, int[] contPaises, int contOutros, String[][] paises) {
        for (int i = 0; i < paises.length; i++) {
            if (contPaises[i] > 0) {
                System.out.println(nomesPaises[i] + ": " + contPaises[i]);
            }
        }
        System.out.println("Outro(s): " + contOutros);
    }
}
