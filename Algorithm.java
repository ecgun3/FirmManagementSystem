import java.util.Scanner; //to specify dataset size
import java.util.Random; //to generate random array
import java.util.Arrays; 
import java.util.*; //for Collection.sort()

public class Algorithms {


    public static void radixSort(int[] arr)
    {
        if(arr.length == 0 || arr == null)
        {
            return; //if size is 0
        }

        int max = maxValue(arr);
        int min = minValue(arr);

        //if there are negative numbers 
        int offset;
        if (min < 0) 
        {
            offset = -min;
        } 
        else 
        {
            offset = 0;
        }

        for (int i = 0; i < arr.length; i++) 
        {
            arr[i] += offset;
        }

        int exp = 1;

        while(max / exp > 0)
        {
            sortCount(arr, exp);
            exp *= 10;
        }

        for (int i = 0; i < arr.length; i++) 
        {
            arr[i] -= offset;
        }
    }
    private static int minValue(int[] arr) 
    {
        int min = arr[0];
        for (int value : arr) 
        {
            if (value < min) 
            {
                min = value;
            }
        }
        return min;
    }
    //finds max value to initialize most significant bit
    private static int maxValue(int[] arr)
    {
        int max = arr[0];
        for(int value : arr)
        {
            if(value > max)
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
        int[] count = new int[19]; //counts numbers from -9 to 9

        for(int i = 0; i < n; i++)
        {
            int digit = (arr[i] / exp) % 10 + 9;
            count[digit]++;
        }

        for(int i = 1; i < 19; i++)
        {
            count[i] += count[i - 1];
        }

        for(int i = n - 1; i >= 0; i--)
        {
            int digit = (arr[i] / exp) % 10 + 9;
            sorted[count[digit] - 1] = arr[i];
            count[digit]--;
        }

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



    public static void main(String[] args)
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

        /*if(sortCheck(radixArray, array) && sortCheck(shellArray, array) && sortCheck(heapArray, array) && sortCheck(insertionArray, array))
        {
            System.out.println("Algorithms produced correct results.");
        }

        else
        {
            System.out.println("Some algorithms produced incorrect results.");
        }*/
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
            
        System.out.println("Execution Times:");
        System.out.println("Radix Sort: " + radixTime);
        System.out.println("Shell Sort: " + shellTime);
        System.out.println("Heap Sort: " + heapTime);
        System.out.println("Insertion Sort: " + insertionTime);

        //return menu
    }
}