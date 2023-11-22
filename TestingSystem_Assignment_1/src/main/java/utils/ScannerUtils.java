package utils;

import java.util.Scanner;

public class ScannerUtils {
    private static final Scanner scanner = new Scanner(System.in);
    public static int inputNumber()
    {
        return scanner.nextInt();
    }
    public static String inputString()
    {
        return scanner.nextLine();
    }
}
