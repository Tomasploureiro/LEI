public class Exercicio1 {
    public static void main(String[] args) {
        String URLs[] = {
                "https://www.dei.uc.pt/poao/exames",
                "http://www.scs.org/index.html",
                "https://www.nato.int/events",
                "https://www.btu.de/",
                "https://www.dei.uc.pt/poao/exames",
                "http://www.eth.ch/index.html",
                "http://www.osu.edu/"
        };
        String paises[][] = {
                { "pt", "Portugal" },
                { "org", "EUA" },
                { "fr", "França" },
                { "uk", "Reino Unido" },
                { "de", "Alemanha" },
                { "edu", "Portugal" }
        };

        String[] nomesPaises = new String[paises.length]; //Aqui é iniciado o arry que guarda o nome dos paises que têm o dominio nos URLs
        int[] contPaises = new int[paises.length]; //Aqui é iniciado o array que guarda a cntagem de URLs com os respetivos dominios
        int[] contOutros = { 0 }; // Aqui é iniciado o array que guarda a contagem dos URLs que não têm pais associado ao dominio e é um array para poder ser retornado da função

        contUrls(paises, URLs, nomesPaises, contPaises, contOutros);
        verificaPrefixos(nomesPaises, contPaises);
        printContUrls(paises, nomesPaises, contPaises, contOutros[0]);
    }

    public static String verificarDominio(String url) { 
        //Ésta função retorna o dominio do URLs
        String url1[] = url.split("/", 0);
        String url2[] = url1[2].split("\\.", 0);
        return url2[url2.length - 1];
    }

    public static void contUrls(String[][] paises, String[] URLs, String[] nomesPaises, int[] contPaises, int[] contOutros) { 
        // Função que organiza os paises no array nomesPaises e o respetivo numero de urls no array contPaises e os que não têm paises associados no array contOutros
        for (int i = 0; i < URLs.length; i++) {
            String dominio = verificarDominio(URLs[i]); 
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
                contOutros[0]++;
            }
        }
    }

    public static void verificaPrefixos(String[] nomesPaises, int[] contPaises) {
        //Função que verifica se existem Paises repetidos na lista nomesPaises e caso existam adiciona os contadores do Array contPaises
        for (int i = 0; i < nomesPaises.length; i++) {
            for (int j = 0; j < nomesPaises.length; j++) {
                if (j > i && nomesPaises[i] != null && nomesPaises[j] != null
                        && nomesPaises[i].equals(nomesPaises[j])) {
                    contPaises[i] += contPaises[j];
                    contPaises[j] = 0;
                }
            }
        }

    }

    public static void printContUrls(String[][] paises, String[] nomesPaises, int[] contPaises, int contOutros) {
        //Função que da print aos Paises e às respetivas contagens
        for (int i = 0; i < nomesPaises.length; i++) {
            if (contPaises[i] > 0) {
                System.out.println(nomesPaises[i] + ": " + contPaises[i]);
            }
        }
        System.out.println("Outro(s): " + contOutros);
    }
}
