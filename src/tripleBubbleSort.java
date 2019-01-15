
public class tripleBubbleSort {
	import java.io.InputStream;
	import java.io.PrintStream;
	import java.util.Arrays;
	import java.util.Scanner;

	class Solution {
	    private static Scanner sc;
	    private static PrintStream out;

	    public static void main(String[] args) {
	        main(System.in, System.out);
	    }

	    public static void main(InputStream in, PrintStream out) {
	        // Create a new Scanner object for reading the input
	        sc = new Scanner(in);
	        Solution.out = out;

	        // Read the number of testcases to follow
	        int t = sc.nextInt();

	        // Iterate over the testcases and solve the problem
	        for (int i = 1; i <= t; i++) {
	            System.out.print("Case #" + i + ": ");
	            testCase();
	        }
	    }

	    /*public static void testCase() {
	        int N = sc.nextInt();
	        int[] arr = readIntArray(N);

	        for (int i = 0; i < N - 2; i++) {
	             if (arr[i] > arr[i+2]) {
	                 int temp = arr[i];
	                 arr[i] = arr[i+2];
	                 arr[i+2] = temp;
	                 i = Math.max(-1, i-3);
	             }
	        }

	        for (int i = 1; i < N; i++) {
	            if (arr[i-1] > arr[i]) {
	                System.out.println(i-1);
	                return;
	            }
	        }
	        System.out.println("OK");
	    }*/

	    public static void testCase() {
	        int N = sc.nextInt();
	        int[] a1 = new int[(N+1)/2];
	        int[] a2 = new int[N/2];

	        for (int i = 0; i < N; i++) {
	            if ((i & 1) == 0) {
	                a1[i/2] = sc.nextInt();
	            } else {
	                a2[i/2] = sc.nextInt();
	            }
	        }

	        Arrays.sort(a1);
	        Arrays.sort(a2);

	        for (int i = 0; i < a1.length; i++) {
	            if (i < a2.length && a1[i] > a2[i]) {
	                System.out.println(i * 2);
	                return;
	            }
	            if (i > 0 && a1[i] < a2[i-1]) {
	                System.out.println(i * 2 - 1);
	                return;
	            }
	        }
	        System.out.println("OK");
	    }


	    public static int[] readIntArray(int length) {
	        int[] result = new int[length];
	        for (int i = 0; i < length; i++) {
	            result[i] = sc.nextInt();
	        }
	        return result;
	    }

	    public static String[] readStringArray(int length) {
	        String[] array = new String[length];
	        readArray(array, new Supplier<String>() {
	            public String get() {
	                return sc.next();
	            }
	        });
	        return array;
	    }

	    public static <T> void readArray(T[] array, Supplier<T> supplier) {
	        for (int i = 0; i < array.length; i++) {
	            array[i] = supplier.get();
	        }
	    }

	    private static interface Supplier<T> {
	        public T get();
	    }
	}

}
