
package mortgage_calculator;

import java.text.NumberFormat;
import java.util.Scanner;


public class Mortgage_Calculator {

    final static byte MONTH_IN_YEARS = 12;
    final static byte PERCENT = 100;
    
    public static void main(String[] args) {


        //Amount of mortgage money
        int principal = (int) readNumber("Principal: ", 1000, 1_000_000);


        //Annual Interest Rate
        float annualInterest = (float) readNumber("Annual Interest Rate: ", 1, 30);


        //Length of payment
        byte period = (byte) readNumber("Period (years): ", 1, 30);

        ///////////////////////////////////////////

        printMortgage(principal, annualInterest, period);


        printPaymentSchedule(principal, annualInterest, period);

    }

    public static void printMortgage(int principal, float annualInterest, byte period) {

        double mortgage = calculateMortgage(principal, annualInterest, period);

        String mortgageFormatted = NumberFormat.getCurrencyInstance().format(mortgage);
        System.out.println("MORTGAGE");
        System.out.println("--------");
        System.out.println();
        System.out.println("Monthly Payments: " + mortgageFormatted);
    }

    public static void printPaymentSchedule(int principal, float annualInterest, byte period) {
        System.out.println();
        System.out.println("PAYMENT SCHEDULE");
        System.out.println("----------------");
        System.out.println();
        
        for(short month = 1; month <= period * MONTH_IN_YEARS; month++){

            double balance = calculateBalance(principal, annualInterest, period, month);
            System.out.println(NumberFormat.getCurrencyInstance().format(balance));
        }
    }
    //END OF MAIN

        ////REFACTORED READING---------------------------------------------------------
    public static double readNumber(String prompt, double min, double max){
        Scanner scan = new Scanner(System.in);
        double value;
        while(true){
            System.out.println(prompt);
            value = scan.nextDouble();
            if(value >= min && value <= max)
                break;
            System.out.println("Please enter a value between "+ min + " and "+ max);
        }

        return value;
    }
        ////REFACTORED BALANCE---------------------------------------------------------

    public static double calculateBalance(int principal, float annualInterest, byte period,
        short numberOfPaymentsMade){

            float monthlyInterest = annualInterest / PERCENT / MONTH_IN_YEARS;  //c
            short numberOfPayments = (short) (period * MONTH_IN_YEARS);     //n

            double balance = principal *
                    ( (Math.pow(1 + monthlyInterest, numberOfPayments) - Math.pow(1 + monthlyInterest, numberOfPaymentsMade))
                            /
                            ((Math.pow(1 + monthlyInterest, numberOfPayments) - 1)) );
            return balance;
    }

        //REFACTORED MORTGAGE----------------------------------------------------------
     public static double calculateMortgage(int principal, float annualInterest, byte period){

            float annualRate = annualInterest / PERCENT;
            float monthlyInterest = annualRate / PERCENT / MONTH_IN_YEARS;
            short numberOfPayments = (short) (period * MONTH_IN_YEARS);
            double mortgage = principal * ((monthlyInterest * (Math.pow(1 + monthlyInterest, numberOfPayments))) /
                    (Math.pow(1 + monthlyInterest, numberOfPayments) - 1));

            return mortgage;
     }
}