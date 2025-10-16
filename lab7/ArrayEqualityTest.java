import java.util.Scanner;

public class ArrayEqualityTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input size of first array
        System.out.print("Enter size of first array: ");
        int n1 = sc.nextInt();

        // Input first array
        int[] arr1 = new int[n1];
        System.out.println("Enter elements of first array:");
        for (int i = 0; i < n1; i++) {
            arr1[i] = sc.nextInt();
        }

        // Input size of second array
        System.out.print("\nEnter size of second array: ");
        int n2 = sc.nextInt();

        // Input second array
        int[] arr2 = new int[n2];
        System.out.println("Enter elements of second array:");
        for (int i = 0; i < n2; i++) {
            arr2[i] = sc.nextInt();
        }

        // Check if arrays are equal
        boolean isEqual = true;

        // Step 1: Compare lengths
        if (n1 != n2) {
            isEqual = false;
        } else {
            // Step 2: Compare element by element
            for (int i = 0; i < n1; i++) {
                if (arr1[i] != arr2[i]) {
                    isEqual = false;
                    break;
                }
            }
        }

        // Output result
        if (isEqual)
            System.out.println("\n The arrays are equal.");
        else
            System.out.println("\n The arrays are NOT equal.");

        sc.close();
    }
}
