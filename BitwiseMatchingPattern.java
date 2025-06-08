import java.util.Scanner;

public class BitwiseMatchingPattern {

    public int nextLargerWithSameBits(int n) {
        int c = n;
        int c0 = 0, c1 = 0;

        // Count trailing zeros
        while (((c & 1) == 0) && (c != 0)) {
            c0++;
            c >>= 1;
        }

        // Count ones after trailing zeros
        while ((c & 1) == 1) {
            c1++;
            c >>= 1;
        }

        // If there is no bigger number with the same number of 1s
        if (c0 + c1 == 31 || c0 + c1 == 0)
            return -1;

        int p = c0 + c1;

        // Flip the rightmost non-trailing 0
        n |= (1 << p);

        // Clear all bits to the right of p
        n &= ~((1 << p) - 1);

        // Insert (c1-1) ones on the right
        n |= (1 << (c1 - 1)) - 1;

        return n;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BitwiseMatchingPattern bmp = new BitwiseMatchingPattern();

        System.out.print("Enter an integer: ");
        int input = scanner.nextInt();

        int result = bmp.nextLargerWithSameBits(input);

        if (result == -1) {
            System.out.println("No larger number with the same number of 1 bits exists.");
        } else {
            System.out.println("Next larger number with same number of 1 bits: " + result);
            System.out.println("Binary form: " + Integer.toBinaryString(result));
        }
    }
}
