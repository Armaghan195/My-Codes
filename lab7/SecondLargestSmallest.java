import java.util.Arrays;
import java.util.Scanner;

public class SecondLargestSmallest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        
        System.out.print("Enter number of elements: ");
        int n = sc.nextInt();

        int[] arr = new int[n];

        // Get array elements
        System.out.println("Enter " + n + " numbers:");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        // Sort the array in ascending order
        Arrays.sort(arr);

        // Find second smallest and second largest
        int secondSmallest = arr[1];
        int secondLargest = arr[n - 2];

        System.out.println("Second Smallest = " + secondSmallest);
        System.out.println("Second Largest = " + secondLargest);

        sc.close();
    }
}
