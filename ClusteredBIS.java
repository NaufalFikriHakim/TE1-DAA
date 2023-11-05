import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class ClusteredBIS {
    public static int binaryLocFinder(int[] arr, int start, int end, int key) {
    if (start == end) {
        if (arr[start] > key) {
            return start;
        } else {
                return start + 1;
            }
        }
        if (start > end) {
            return start;
        } else {
            int middle = (start + end) / 2;
            if (arr[middle] < key) {
                return binaryLocFinder(arr, middle + 1, end, key);
            }
            if (arr[middle] > key) {
                return binaryLocFinder(arr, start, middle - 1, key);
            }
            return middle;
        }
    }

    public static int[] placeInserter(int[] arr, int start, int end) {
        int temp = arr[end];
        for (int k = end; k > start; k--) {
            arr[k] = arr[k - 1];
        }

        arr[start] = temp;
        return arr;
    }

    public static void clusteredBIS(int[] arr) {
        int pop = 0;

        for (int i = 1; i < arr.length; i++) {
            int cop = i;
            int key = arr[cop];
            int place;

            if (key >= arr[pop]) {
                place = binaryLocFinder(arr, pop + 1, cop - 1, key);
            } else {
                place = binaryLocFinder(arr, 0, pop - 1, key);
            }

            pop = place;
            arr = placeInserter(arr, place, cop);
        }
    }

    static void random(int arr[],int low,int high) 
    { 
     
        Random rand= new Random(); 
        int pivot = rand.nextInt(high-low)+low; 
         
        int temp1=arr[pivot];  
        arr[pivot]=arr[high]; 
        arr[high]=temp1; 
    } 
     
    public static int partition(int arr[], int low, int high) 
    { 
        random(arr,low,high);
        int pivot = arr[high]; 
     
 
        int i = (low-1);
        for (int j = low; j < high; j++) 
        { 
            if (arr[j] < pivot) 
            { 
                i++; 
 
                int temp = arr[i]; 
                arr[i] = arr[j]; 
                arr[j] = temp; 
            } 
        } 
 
        int temp = arr[i+1]; 
        arr[i+1] = arr[high]; 
        arr[high] = temp; 
 
        return i+1; 
    } 
 
    public static void quickSort(int arr[], int low, int high) 
    { 
        if (low < high) 
        { 
            int pi = partition(arr, low, high); 
 
            quickSort(arr, low, pi-1); 
            quickSort(arr, pi+1, high); 
        } 
    } 

    public static int[] readFile(String filePath, int size)throws Exception {
        int[] array = new int[size];
        Scanner sc = new Scanner(new BufferedReader(new FileReader(new File(filePath))));
        for(int i=0;sc.hasNextLine();i++) 
        {
        array[i]=Integer.parseInt(sc.nextLine());
        }
        return array;
    }

    public static void calculate(String filePath, String sortFunc, String fileDesc, int size){
        try{
            int[] arr = readFile(filePath, size);
            long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            long startTime = System.currentTimeMillis();
            if(sortFunc.equals("CBIS")){
                clusteredBIS(arr);
            }else if(sortFunc.equals("QS")){
                quickSort(arr, 0, arr.length-1);
            }
            long endTime = System.currentTimeMillis();
            long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            long memoryUsed = memoryAfter - memoryBefore;
            System.out.println("Estimated memory used: " + memoryUsed + " bytes");
            long duration = (endTime - startTime);
            System.out.println("Time taken to sort " + fileDesc + " using " + sortFunc + " is " + duration + " ms");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static void compareSorts(String fileName, String fileDesc, int size){
        calculate(fileName, "CBIS", fileDesc, size);
        calculate(fileName, "QS", fileDesc, size);
    }

    public static void main(String[] args) throws Exception {
        compareSorts("data200random.txt", "small random data", 200);
        compareSorts("data200sorted.txt", "small sorted data", 200);
        compareSorts("data200reverse.txt", "small reverse data", 200);
        compareSorts("data2000random.txt", "medium random data", 2000);
        compareSorts("data2000sorted.txt", "medium sorted data", 2000);
        compareSorts("data2000reverse.txt", "medium reverse data", 2000);
        compareSorts("data20000random.txt", "large random data", 20000);
        compareSorts("data20000sorted.txt", "large sorted data", 20000);
        compareSorts("data20000reverse.txt", "large reverse data", 20000);
    }
}