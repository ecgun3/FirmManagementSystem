import java.util.Scanner; // To specify dataset size
import java.util.Random; // To generate random array
import java.util.Arrays; 
import java.util.*; // For Collection.sort()

public class Algorithm {

    public static void radixSort(int[] arr) {
        if (arr == null || arr.length == 0) 
        {
            return; // If size is 0 don't do anything
        }
    
        // Put negative and pozitive values into different arrays
        int negativeCount = 0;
        for (int value : arr) 
        {
            if (value < 0) 
            {
                negativeCount++;
            }
        }
    
        int[] negatives = new int[negativeCount]; // Negative values as an array
        int[] positives = new int[arr.length - negativeCount]; // Positive values as an array
    
        int negIndex = 0, posIndex = 0;
        for (int value : arr) 
        {
            if (value < 0) 
            {
                negatives[negIndex++] = -value; // Turn negative numbers to positive
            } 
            
            else 
            {
                positives[posIndex++] = value; // If positive put it into positive array
            }
        }
    
        // Sort negative and positive arrays
        if (negatives.length > 0) 
        {
            radixSortPositive(negatives);
        }

        if (positives.length > 0) 
        {
            radixSortPositive(positives);
        }
    
        // Reverse the negative array convert the values to negative and place them in the array
        for (int i = 0; i < negatives.length; i++) 
        {
            arr[i] = -negatives[negatives.length - 1 - i];
        }
    
        // Add  the positive array
        for (int i = 0; i < positives.length; i++) 
        {
            arr[negatives.length + i] = positives[i];
        }
    }
    
    private static void radixSortPositive(int[] arr) 
    {
        int max = maxValue(arr);
    
        int exp = 1;
        while (max / exp > 0) 
        {
            sortCount(arr, exp);
            exp *= 10;
        }
    }
    
    private static int maxValue(int[] arr) 
    {
        int max = arr[0];
        for (int value : arr) 
        {
            if (value > max) 
            {
                max = value;
            }
        }
        return max;
    }
    
    private static void sortCount(int[] arr, int exp) 
    {
        int n = arr.length;
        int[] sorted = new int[n];
        int[] count = new int[10]; // Counts numbers from 0 to 9
    
        // Count the digits
        for (int i = 0; i < n; i++) {
            int digit = (arr[i] / exp) % 10;
            count[digit]++;
        }
    
        // Kümülatif toplama
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
    
        // Sort numbers
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            sorted[count[digit] - 1] = arr[i];
            count[digit]--;
        }
    
        // Copy sorted array to original array
        System.arraycopy(sorted, 0, arr, 0, n);
    }
        


    public static void shellSort(int[] arr)
    {
        int n = arr.length;

        for(int gap = n / 2; gap > 0; gap /= 2)
        {
            for(int i = gap; i < n; i++)
            {
                int key = arr[i];
                int j = i;

                while(j >= gap && arr[j - gap] > key)
                {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = key;
            }
        }
    }



    public static void heapSort(int[] arr)
    {
        int n = arr.length;

        //turns array into Max-Heap
        for(int i = n / 2 - 1; i >= 0; i--)
        {
            heapify(arr, n, i);
        }

        for(int i = n - 1; i >= 0; i--)
        {
            //move largest element to end of the array
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0); //heapify remaining part
        }

    }

    private static void heapify(int[] arr, int n, int i)
    {
        int large = i; //parent
        int left = 2 * i + 1; //left child
        int right = 2 * i + 2; //right child

        //if left child is bigger than parent
        if(left < n && arr[left] > arr[large])
        {
            large = left;
        }

        //if right child is bigger than parent
        if(right < n && arr[right] > arr[large])
        {
            large = right;
        }

        //if largest isn't parent
        if(large != i)
        {
            int temp = arr[i];
            arr[i] = arr[large];
            arr[large] = temp;

            heapify(arr, n, large);
        }
    }




    public static void insertionSort(int[] arr)
    {
        int n = arr.length;
        for(int i = 1; i < n; i++)
        {
            int current = arr[i];
            int j = i - 1;

            while(j >= 0 && arr[j] > current)
            {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = current;
        }
    }



    public static int[] randomArray(int size)
    {
        Random rNum = new Random();
        int arr[] = new int[size];
        for(int i = 0; i < size; i++)
        {
            arr[i] = rNum.nextInt(20001) - 10000; // -10000 <= arr[i] < 10001 (-10000+20001)
        }
        return arr;
    }



    //Without usage of Runnable interface executionTime of each sort type should be a different method
    public static long executionTime(Runnable algorithmType) //Runnable is a interface it represents code blocks that can be runned
    {
        long timeStart = System.nanoTime(); //starting time
        algorithmType.run(); //Sorting algorithm is executed
        long timeEnd = System.nanoTime(); //ending time

        return timeEnd - timeStart; //calculating the time of the execution
    }



    public static boolean sortCheck(int[] sortedArr, int[] originalArr)
    {
        int[] javaSortArr = originalArr.clone();

        Arrays.sort(javaSortArr);

        return Arrays.equals(sortedArr, javaSortArr);
    }

    public static void runAlgorithms()
    {
        int size = 0;
        Scanner input = new Scanner(System.in);

        boolean flg = false;
        while(!flg)
        {   
            System.out.printf("Enter size between 1,000 to 10,000: ");

            if(input.hasNextInt())
            {
                size = input.nextInt();
                
                if (size >= 1000 && size <= 10000) 
                {
                    if(size == 0)
				    {
					    System.out.println("Size is zero.");
				    }

                    flg = true; 
                }

                else 
                {
                    System.out.println("Size should be in the [1,000 - 10,000] range.");
			    }
            }

            else
		    {
			    System.out.println("Invalid input. Please enter an integer.");
			    input.next(); //Skip invalid input
		    }       
        }
    
        int[] array = randomArray(size);
    
        int[] radixArray = array.clone();
        int[] shellArray = array.clone();
        int[] heapArray = array.clone();
        int[] insertionArray = array.clone();

        long radixTime = executionTime(() -> radixSort(radixArray));
        long shellTime = executionTime(() -> shellSort(shellArray));
        long heapTime = executionTime(() -> heapSort(heapArray));
        long insertionTime = executionTime(() -> insertionSort(insertionArray));

        System.out.println();

        if(sortCheck(radixArray, array) && sortCheck(shellArray, array) && sortCheck(heapArray, array) && sortCheck(insertionArray, array))
        {
            System.out.println("Algorithms produced correct results.");
        }

        else
        {
            System.out.println("Some algorithms produced incorrect results.");
        }

        System.out.println();

        if(sortCheck(radixArray, array))
            System.out.println("Radix true");
        else
        System.out.println("Radix wrong");
        
        if(sortCheck(shellArray, array))
            System.out.println("Shell true");

        if(sortCheck(heapArray, array))
            System.out.println("Heap true");
            
        if(sortCheck(insertionArray, array))
            System.out.println("Insertion true");
        
        System.out.println();
            
        System.out.println("Execution Times:");
        System.out.println("Radix Sort: " + radixTime + " ns");
        System.out.println("Shell Sort: " + shellTime + " ns");
        System.out.println("Heap Sort: " + heapTime + " ns");
        System.out.println("Insertion Sort: " + insertionTime + " ns");

        //return menu
    }
    

    public static void main(String[] args)
    {
        runAlgorithms();
    }
        
}