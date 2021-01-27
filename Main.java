package processor;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Add matrices\n" +
                    "2. Multiply matrix by a constant\n" +
                    "3. Multiply matrices\n" +
                    "4. Transpose matrix\n" +
                    "5. Calculate a determinant\n" +
                    "6. Inverse matrix\n" +
                    "0. Exit");
            System.out.print("Your choice: ");
            var choice = scan.nextInt();

            double[][] res;
            switch (choice) {
                case 1:
                    res = sumMatrices(readMatrix(" first"),
                            readMatrix(" second"));
                    break;
                case 2:
                    var matrix = readMatrix("");
                    System.out.print("Enter constant: ");
                    var c = scan.nextDouble();
                    res = multiplyMatrixByConst(matrix, c);
                    break;
                case 3:
                    res = multiplyMatrices(readMatrix(" first"),
                            readMatrix(" second"));
                    break;
                case 4:
                    System.out.print("\n" +
                            "1. Main diagonal\n" +
                            "2. Side diagonal\n" +
                            "3. Vertical line\n" +
                            "4. Horizontal line\n" +
                            "Your choice: ");
                    var line = scan.nextInt();
                    res = transpose(readMatrix(""), line);
                    break;
                case 5:
                    res = new double[1][1];
                    res[0][0] = determinant(readMatrix(""));
                    break;
                case 6:
                    res = inverse(readMatrix(""));
                    break;
                case 0:
                default:
                    return;
            }

            if (res != null && !Double.isNaN(res[0][0])) {
                System.out.println("The result is:");
                printMatrix(res);
                System.out.println();
            }
        }
    }

    private static double[][] readMatrix(String name) {
        System.out.printf("Enter size of%s matrix: ", name);
        var n = scan.nextInt();
        var m = scan.nextInt();
        System.out.printf("Enter%s matrix:" + System.lineSeparator(), name);
        var matrix = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scan.nextDouble();
            }
        }
        return matrix;
    }

    private static void printMatrix(double[][] matrix) {
            Arrays.stream(matrix).forEach(row ->
                    System.out.println(Arrays.stream(row)
                            .mapToObj(x -> {
                                var y = (int) x;
                                return y == x ? String.valueOf(y)
                                        : String.valueOf(x);
                            })
                            .collect(Collectors.joining(" "))));
    }

    private static double[][] sumMatrices(double[][] a, double[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            System.out.println("ERROR");
            return null;
        }

        var sum = new double[a.length][a[0].length];
        for (int i = 0; i < sum.length; i++) {
            for (int i1 = 0; i1 < sum[i].length; i1++) {
                sum[i][i1] = a[i][i1] + b[i][i1];
            }
        }
        return sum;
    }

    private static double[][] multiplyMatrixByConst(double[][] matrix, double constant) {
        return Arrays.stream(matrix)
                .map(row -> Arrays.stream(row)
                        .map(operand -> operand * constant)
                        .toArray())
                .toArray(double[][]::new);
    }

    private static double[][] multiplyMatrices(double[][] a, double[][] b) {
        if (a[0].length != b.length) {
            System.out.println("ERROR");
            return null;
        }

        var res = new double[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                for (int t = 0; t < b.length; t++) {
                    res[i][j] += a[i][t] * b[t][j];
                }
            }
        }
        return res;
    }

    private static double[][] transpose(double[][] matrix, int line) {
        double[][] res;
        switch (line) {
            default:
            case 1:
                res = new double[matrix[0].length][matrix.length];
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[i].length; j++) {
                        res[j][i] = matrix[i][j];
                    }
                }
                break;
            case 2:
                res = new double[matrix[0].length][matrix.length];
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[i].length; j++) {
                        res[res.length - 1 - j][res[0].length - 1 - i]
                                = matrix[i][j];
                    }
                }
                break;
            case 3:
                res = new double[matrix.length][matrix[0].length];
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[i].length; j++) {
                        res[i][matrix[i].length - 1 - j] = matrix[i][j];
                    }
                }
                break;
            case 4:
                res = new double[matrix.length][matrix[0].length];
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[i].length; j++) {
                        res[matrix.length - 1 - i][j] = matrix[i][j];
                    }
                }
                break;
        }
        return res;
    }

    private static double determinant(double[][] matrix) {
        if (matrix.length != matrix[0].length) {
            System.out.println("ERROR");
            return Double.NaN;
        }

        var res = 0d;

        if (matrix.length == 1) {
            return matrix[0][0];
        }

        for (int i = 0; i < matrix.length; i++) {
            res += matrix[0][i] * Math.pow(-1, i) * determinant(subMatrix(matrix, 0, i));
        }
        return res;
    }

    private static double[][] subMatrix(double[][] matrix, int row, int column) {
        var sub = new double[matrix.length - 1][matrix[0].length - 1];
        for (int i = 0, i1 = 0; i < matrix.length; i++) {
            for (int j = 0, j1 = 0; j < matrix[i].length; j++) {
                if (i != row && j != column) {
                    sub[i1][j1] = matrix[i][j];
                    j1++;
                }
            }
            i1 = i == row ? i1 : i1 + 1;
        }
        return sub;
    }

    private static double[][] inverse(double[][] matrix) {
        return multiplyMatrixByConst(transpose(cofactorsMatrix(matrix), 1),
                1 / determinant(matrix));
    }

    private static double[][] cofactorsMatrix(double[][] matrix) {
        var res = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                res[i][j] = Math.pow(-1, i + j)
                        * determinant(subMatrix(matrix, i, j));
            }
        }
        return res;
    }
}
