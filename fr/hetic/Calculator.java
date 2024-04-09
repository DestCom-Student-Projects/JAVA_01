package fr.hetic;

class Calculator {
    public static void main(String[] args) {
        if(args.length != 3) {
            System.out.println("Usage: java Calculator <number1> <operator> <number2>");
            System.exit(1);
        }

        int number1 = Integer.parseInt(args[0]);
        String operator = args[1];
        int number2 = Integer.parseInt(args[2]);


        switch (operator) {
            case "+":
                add(number1, number2);
                break;
            case "-":
                substract(number1, number2);
                break;
            case "*" :
            case "x":
                multiply(number1, number2);
                break;
            case "/":
                divide(number1, number2);
                break;
            default:
                System.err.println("Invalid operator");
                System.out.println("Usage: java Calculator <number1> <operator> <number2>");
                System.out.println("Supported operators: +, -, * or x, /");
                System.out.println("If using * or /, please add quotes around the operator");
                System.exit(1);
        }
    }

    public static void add(int a, int b) {
        System.out.println(a + b);
    }

    public static void substract(int a, int b) {
        System.out.println(a - b);
    }

    public static void multiply(int a, int b) {
        System.out.println(a * b);
    }

    public static void divide(int a, int b) {
        if(a % b == 0) {
            System.out.println(a / b);
        } else {
            System.out.println((float) a / b);
        }
    }
}