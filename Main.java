package processor;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        var an = scan.nextInt();
        var am = scan.nextInt();
        var a = new int[an][am];
        for (int i = 0; i < an; i++) {
            for (int j = 0; j < am; j++) {
                a[i][j] = scan.nextInt();
            }
        }
        var bn = scan.nextInt();
        var bm = scan.nextInt();
        if (an != bn || am != bm) {
            System.out.println("ERROR");
            return;
        }
        var b = new int[bn][bm];
        for (int i = 0; i < bn; i++) {
            for (int j = 0; j < bm; j++) {
                b[i][j] = scan.nextInt();
            }
        }

        for (int i = 0; i < a.length; i++) {
            for (int i1 = 0; i1 < a[i].length; i1++) {
                a[i][i1] += b[i][i1];
            }
        }

        Arrays.stream(a).forEach(row ->
                System.out.println(Arrays.stream(row)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "))));
    }
}
