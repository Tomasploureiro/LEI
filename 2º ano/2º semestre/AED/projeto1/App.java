   import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class App {
    public static void main(String[] args) {
        main(20000);
        main(40000);
        main(60000);
        main(80000);
        main(100000);
    }

    public static List<Integer> criarArray(List<Integer> list, int size, Random rand){

        for(int i = 0; i<size; i++){
            list.add(rand.nextInt(2*size) + 1);
        }
        return list;
    }

    public static boolean exercicio1(List<Integer> list, int k){
        boolean resposta = false;
        for(int i = 0; i<list.size();i++){
            for(int j = i+1; j<list.size(); j++){
                if (list.get(i) + list.get(j) == k && list.get(i) != list.get(j)){
                    resposta = true;
                }    
            }
        }   
        return resposta;   
    }

    public static boolean exercicio2(List<Integer> list, int k){
        int i = 0;
        int j = list.size()-1;
        Collections.sort(list);
        while (i < j){
            if ((list.get(i) + list.get(j) == k) && (list.get(i) != list.get(j))){
                return true;
            }
            else if(list.get(i) + list.get(j) < k && (list.get(i) != list.get(j))){
                i++;
            }
            else if(list.get(i) + list.get(j) > k && (list.get(i) != list.get(j))){
                j--;
            }
        }
        return false;
    }

    public static boolean exercicio3(List<Integer> list, int k){
        List<Integer> list0 = new ArrayList<>(Collections.nCopies(Collections.max(list), 0));
        for(int i = 0; i<list.size(); i++){
            if(list.get(i)<k ){
                if(list0.get(k-list.get(i)) != 0 && k != (list.get(i)*2)){
                    return true;
                }
                else{
                    list0.set(list.get(i), 1);
                }
            }
        }
        return false;
    }

    public static void main(int size){
        Random rand = new Random();
        List<Integer> list = new ArrayList<Integer>();
        int k;
        long startTime;
        long endTime;
        long time = 0;
        long startTime2;
        long endTime2;
        long time2 = 0;
        long startTime3;
        long endTime3;
        long time3 = 0;
    
        
        for(int i = 0; i<10000; i++){
            list = new ArrayList<Integer>();
            list = criarArray(list, size, rand);
            k = rand.nextInt(2*size) + 1;
            startTime = System.nanoTime();
            exercicio1(list, k);
            endTime = System.nanoTime();
            time = time + endTime - startTime;
            startTime2 = System.nanoTime();
            exercicio2(list, k);
            endTime2 = System.nanoTime();
            time2 = time2 + endTime2 - startTime2;
            startTime3 = System.nanoTime();
            exercicio3(list, k);
            endTime3 = System.nanoTime();
            time3 = time3 + endTime3 - startTime3;
            System.out.println(i);
        }
        time = time/100;
        time2 = time2/10000;
        time3 = time3/10000;
        System.out.println("Size " + size + ", Exercicio 1: " + time + ", Exercicio2: " + time2 + ", Exercicio 3: " + time3);
    }
}