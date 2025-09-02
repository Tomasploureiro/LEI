import java.util.Random;
import java.util.Arrays;


public class projeto3{
    public static void insertionSort(int[] values){
        // O(n^2)
        for(int i = 0; i<values.length; i++){

            int j = i;

            while(j > 0 && values[j] < values[j-1]){
                int aux = values[j];
                values[j] = values[j - 1];
                values[j - 1] = aux;
                j-=1;                 
            }
        }
    }


    public static void heapSort(int[] values){
        // O(nlog(n))
        int n = values.length;
        
        for(int i = n / 2 - 1; i >= 0; i--){
            heap(values, n , i);
        }

        for(int i = n - 1; i >= 0; i--){
            int aux = values[0];
            values[0] = values[i]; 
            values[i] = aux;

            heap(values, i , 0);
        }
    }

    public static void heap(int[] values, int n, int i){
        int big = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && values[l] > values[big])
        big = l;
  
        if (r < n && values[r] > values[big])
        big = r;
  
      if (big != i) {
        int swap = values[i];
        values[i] = values[big];
        values[big] = swap;
  
        heap(values, n, big);
        }
    }
    
    private static void quickSort(int[] values) {
        quicksort(values, 0, values.length - 1);
    }

    private static void quicksort(int[] values, int lowIndex, int highIndex) {

        if (lowIndex >= highIndex) {
            return;
        }

        int pivotIndex = new Random().nextInt(highIndex - lowIndex) + lowIndex;
        int pivot = values[pivotIndex];
        swap(values, pivotIndex, highIndex);

        int leftPointer = partition(values, lowIndex, highIndex, pivot);

        quicksort(values, lowIndex, leftPointer - 1);
        quicksort(values, leftPointer + 1, highIndex);

    }

    private static int partition(int[] values, int lowIndex, int highIndex, int pivot) {
        int leftPointer = lowIndex;
        int rightPointer = highIndex - 1;

        while (leftPointer < rightPointer) {

            while (values[leftPointer] <= pivot && leftPointer < rightPointer) {
                leftPointer++;
            }

            while (values[rightPointer] >= pivot && leftPointer < rightPointer) {
                rightPointer--;
            }

            swap(values, leftPointer, rightPointer);
        }

        if(values[leftPointer] > values[highIndex]) {
            swap(values, leftPointer, highIndex);
        }
        else {
            leftPointer = highIndex;
        }

        return leftPointer;
    }

    private static void swap(int[] values, int index1, int index2) {
        int temp = values[index1];
        values[index1] = values[index2];
        values[index2] = temp;
    }


    public static void printArray(int values[]) {
        int n = values.length;
        for (int i = 0; i < n; ++i)
        System.out.print(values[i] + " ");
        System.out.println();
      }

    public static int[] createValues(int size){
        int values[] = new int[size];
        Random rand = new Random();
        for(int i = 0; i<size; i++){
            values[i] = rand.nextInt();
        }
        return values;
    }

    public static void main(String[] args) {
    for (int j = 1; j<6; j++){
        int[] values = createValues(j*200000);
        
        long startTime = 0;
        long endTime = 0;
        long finalTime = 0;
        startTime = System.nanoTime();
        insertionSort(values);
        endTime = System.nanoTime();
        finalTime = endTime - startTime;
        System.out.println(finalTime  + " quickSort aleatorio" + j*200000);

        Arrays.sort(values);
        startTime = System.nanoTime();
        insertionSort(values);
        endTime = System.nanoTime();
        finalTime = endTime - startTime;
        System.out.println(finalTime  + " quickSort crescente" + j*200000);

        for (int i = 0; i < values.length / 2; i++) {
            int temp = values[i];
            values[i] = values[values.length - i - 1];
            values[values.length - i - 1] = temp;
        }
        startTime = System.nanoTime();
        insertionSort(values);
        endTime = System.nanoTime();
        finalTime = endTime - startTime;
        System.out.println(finalTime  + " quickSort decrescente" + j*200000);
    }
    }
}