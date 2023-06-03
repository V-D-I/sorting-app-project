package org.example;


import java.util.ArrayList;
import java.util.List;

public class App {
    static int argumentsLimit = 10;

    /**
     * This small application takes up to 10 integers as arguments, sort them and prints into standard output
     *
     * @param args Integers
     * @throws IllegalArgumentException if pass invalid integer
     */
    public static void main(String[] args) {
        List<Integer> userIntegers = new ArrayList<>();

        int numberOfArgument = 0;
        for (String arg : args) {
            numberOfArgument++;

            if (arg.equals("") || numberOfArgument > argumentsLimit)
                break;


            try {
                userIntegers.add(Integer.parseInt(arg));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("All arguments must be valid integers");
            }
        }

        userIntegers.sort((a, b) -> {
            if (a > b) {
                return 1;
            } else if (a < b) {
                return -1;
            }
            return 0;
        });

        System.out.println(userIntegers);
    }
}
