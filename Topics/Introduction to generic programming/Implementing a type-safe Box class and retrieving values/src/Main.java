import java.util.*;

public class Main {
    static class Box<T> {
        // your code here
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        if(sc.hasNextInt()) {
            int num = sc.nextInt();
            // your code here
        } else if (sc.hasNextFloat()) {
            float num = sc.nextFloat();
            // your code here
        } else {
            String str = sc.next();
            // your code here
        }
    }
}