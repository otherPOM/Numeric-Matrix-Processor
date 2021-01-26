package processor;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        var a = readMatrix();
        var b = readMatrix();

        var sum = sumMatrices(a, b);

        printMatrix(sum);
    }

    private static int[][] readMatrix() {
        var n = scan.nextInt();
        var m = scan.nextInt();
        var matrix = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scan.nextInt();
            }
        }
        return matrix;
    }

    private static void printMatrix(int[][] matrix) {
        if (matrix != null) {
            Arrays.stream(matrix).forEach(row ->
                    System.out.println(Arrays.stream(row)
                            .mapToObj(String::valueOf)
                            .collect(Collectors.joining(" "))));
        }
    }

    private static int[][] sumMatrices(int[][] a, int[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            System.out.println("ERROR");
            return null;
        }
        var sum = new int[a.length][a[0].length];
        for (int i = 0; i < sum.length; i++) {
            for (int i1 = 0; i1 < sum[i].length; i1++) {
                sum[i][i1] = a[i][i1] + b[i][i1];
            }
        }
        return sum;
    }
}
