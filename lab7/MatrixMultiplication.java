import java.util.Scanner;

public class MatrixMultiplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Get dimensions of first matrix
        System.out.print("Enter number of rows in Matrix A: ");
        int r1 = sc.nextInt();
        System.out.print("Enter number of columns in Matrix A: ");
        int c1 = sc.nextInt();

        // Get dimensions of second matrix
        System.out.print("Enter number of rows in Matrix B: ");
        int r2 = sc.nextInt();
        System.out.print("Enter number of columns in Matrix B: ");
        int c2 = sc.nextInt();

        // Validate matrix multiplication rule
        // (Columns of A must equal Rows of B)
        if (c1 != r2) {
            System.out.println(" Matrix multiplication not possible!");
            System.out.println("Columns of A must be equal to Rows of B.");
            return;
        }

        // Declare matrices
        int[][] A = new int[r1][c1];
        int[][] B = new int[r2][c2];
        int[][] C = new int[r1][c2]; // result matrix

        // Input matrix A
        System.out.println("\nEnter elements of Matrix A:");
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c1; j++) {
                A[i][j] = sc.nextInt();
            }
        }

        // Input matrix B
        System.out.println("\nEnter elements of Matrix B:");
        for (int i = 0; i < r2; i++) {
            for (int j = 0; j < c2; j++) {
                B[i][j] = sc.nextInt();
            }
        }

        // Multiply matrices: C = A × B
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                C[i][j] = 0; // initialize
                for (int k = 0; k < c1; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        // Display result
        System.out.println("\nResultant Matrix (A × B):");
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }

        sc.close();
    }
}
