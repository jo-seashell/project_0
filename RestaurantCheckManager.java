
// Imports
import java.util.InputMismatchException;
import java.util.Scanner;

// Start of the Class
public class RestaurantCheckManager {
    
    //Method for user input and exception handling
    public static double getInput(String message, Scanner scnr) {
        double answer = 0;
        double testDigits = 0;
        while (true) {
            System.out.print(message);
            try {
                answer = scnr.nextDouble();
                
                //Error if the user enters 3+ decimal places
                testDigits = Math.round(answer * 100.0) / 100.0;
                if (Double.compare(answer, testDigits) != 0.0) {
                    throw new Exception("Please enter a number with two or less decimal places");
                }

                //Error if the user enters a negative value
                else if (answer < 0) {
                    throw new Exception("Please enter a positive number");
                }
                break;
            }

            //Error if the user enters something weird instead of a number
            catch (InputMismatchException e) {
                System.out.println("Please enter a number");
                scnr.nextLine();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return answer;
    }
    
    // Method for the Tip Distribution of options 1 and 3
    public static double[] breakdownTips(double tipAmount, int breakdownStyle) {
        double[] tipDistribution = new double[6];
        double[] tipPercentage = new double[6];
        
        // breakdownStyle 1 represents the split given in document
        // breakdownStyle 2 represents an even split
        // breakdownStyle 3 represents the split we created
        // anything else will cause breakdownTips to return 0s
        switch (breakdownStyle) {
            case 1:
                tipPercentage[0] = 0.30 * 0.50;
                tipPercentage[1] = 0.30 * 0.30;
                tipPercentage[2] = 0.30 * 0.20;
                tipPercentage[3] = 0.10;
                tipPercentage[4] = 0.10;
                tipPercentage[5] = 0.50;
                break;
            case 2:
                tipPercentage[0] = 1.00/6.00;
                tipPercentage[1] = 1.00/6.00;
                tipPercentage[2] = 1.00/6.00;
                tipPercentage[3] = 1.00/6.00;
                tipPercentage[4] = 1.00/6.00;
                tipPercentage[5] = 1.00/6.00;
                break;
            case 3:
                tipPercentage[0] = 0.36 * 0.50;
                tipPercentage[1] = 0.36 * 0.30;
                tipPercentage[2] = 0.36 * 0.20;
                tipPercentage[3] = 0.12;
                tipPercentage[4] = 0.12;
                tipPercentage[5] = 0.40;
                break;
        }

        // Find the dollar amount given to each position and round it to nearest cent
        for (int i = 0; i < tipDistribution.length; ++i) {
            tipDistribution[i] = Math.round(tipPercentage[i] * tipAmount * 100.0) / 100.0;
        }

        /* Each index in tipDistribution represents the amount of money (in dollars) given to each position (not person)
            0 = Chef
            1 = Sous chef
            2 = Kitchen aid
            3 = Host / hostess
            4 = Busser
            5 = Server
        */
        return tipDistribution;
    }

    public static void testCase()
    {

        // Use this method to perform test cases! 

        // We can have a check in the initial input from the user, so that if a specific value is entered then all test cases are run, and we can check them. 


    }




    //  --------------------------------  MAIN FUNCTION -------------------------------- //

    public static void main(String[] args) throws Exception {
        
        // Variables for Scanner
        Scanner scnr = new Scanner(System.in);

        // Variables for Payment Amounts
        double saleAmount = 0.0, tipAmount = 0.0, totalAmount = 0.0;
        double totalSaleAmount = 0.0, totalTipAmount = 0.0;
        int numChecks = 0;

        // Variable for Loop Continuation
        char loopContinuation = ' '; 


        // Provide an Introduction to the Program
        System.out.println( "\nWelcome! This program will take check amounts ( $##.## ), for a restaraunt. Specifically, " 
                          + "the program will recieve a Sale, Tip, and Total amount from each meal, and provide a " 
                          + "detailed summary of all pooled sales. Finally, the program will take this information and " 
                          + "distribute tips among all of the staff members who worked that day, in one of three ways.\n" );



        // Loop to determine all sales
        while (true)
        {
            // Prompt for Sale Amount
            saleAmount = getInput("Total Sale Amount: ", scnr);
            
            // Prompt for Tip Amount
            tipAmount = getInput("Tip Amount: ", scnr);
            
            // Prompt for Total Amount
            totalAmount = getInput("Total Amount: ", scnr);
            
            // ----- Perform Edge Case Checks ----- //
            
                // Check if Sale and Tip add to Total
            if ( (saleAmount + tipAmount) != totalAmount )
            {

                    // Check for "Blank" Values - i.e. values marked 0
                if ( tipAmount == 0 || totalAmount == 0 )
                {

                    if ( tipAmount == 0 && totalAmount == 0 )   // Tip and Total are left "Blank"
                    {
                        tipAmount = 0.0;
                        totalAmount = saleAmount;
                    }
                    else if ( tipAmount == 0 )  // JUST Tip is left "Blank"
                    {
                        
                        if ( (totalAmount - saleAmount) < 0 )   // Edge Case in the Event that Tip is missing AND Total is marked as less than the Sale value
                        {
                            tipAmount = 0;
                            totalAmount = saleAmount;
                        }
                        else                                  
                        {
                            tipAmount = totalAmount - saleAmount;
                        }

                    }
                    else if ( totalAmount == 0 )    // JUST Total is left "Blank"
                    {
                        totalAmount = saleAmount + tipAmount;
                    }
                    
                }
                else    // All values are filled, but, the sum of the Sale and Tip does not add to the total
                {

                    if ( (totalAmount - saleAmount) < 0 )
                    {
                        tipAmount = 0;
                        totalAmount = saleAmount;
                    }
                    else                                  
                    {
                        tipAmount = totalAmount - saleAmount;
                    }

                }
            }

            // Update Sale and Tip Amounts, and Number of Total Checks
            totalSaleAmount += saleAmount;
            totalTipAmount += tipAmount;
            numChecks++;

            // Print Updated Totals
            System.out.printf("Check Count: %d\n", numChecks);
            System.out.printf("Total Sale so far: %.2f\n", totalSaleAmount);
            System.out.printf("Total Pooled Tips so far: %.2f\n", totalTipAmount);
            
            // Prompt for Loop Continuation
            System.out.print("Do you want to stop (y/n): ");
            loopContinuation = scnr.next().charAt(0);

            System.out.println("------------------------------");
            if (loopContinuation == 'y' || loopContinuation == 'Y')
            {
                break;
            }

        }

        // Print Final Totals 
        System.out.printf("The Total Sale Amount: %.2f\n", totalSaleAmount);
        System.out.printf("The Total Pooled Tip Amount: %.2f\n", totalTipAmount);
        System.out.println("------------------------------\n");

        
        // Get input for which breakdown method and call Method for Breakdowns of Tips
        int breakdownInput = 0;
        System.out.printf("Please input the number that corresponds to the desired tip breakdown: \n" +
            "1 - Document Breakdown\n" +
            "2 - Equal Split to Everyone\n" +
            "3 - Our Method\n");
        
        while (true){ 
            try { /// reads user input for breakdown 1, 2 or 3 and catches exception if input is not an integer
                breakdownInput = scnr.nextInt(); // Read user input
                
                if (breakdownInput == 1 || breakdownInput == 2 || breakdownInput == 3) {
                    break; // If valid input, exit the loop
                } else {
                    System.out.println("Invalid input, please input 1, 2, or 3.");
                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid input, please input an integer: 1, 2, or 3.");
                scnr.nextLine(); // Consume the leftover newline character
            }
        }
        double[] tipDistribution = breakdownTips(totalTipAmount, breakdownInput);
        System.out.println("\nTip Distribution Breakdown:");
        System.out.println("Chefs: $" + tipDistribution[0]);
        System.out.println("Sous Chefs: $" + tipDistribution[1]);
        System.out.println("Kitchen Aids: $" + tipDistribution[2]);
        System.out.println("Hosts/Hostesses: $" + tipDistribution[3]);
        System.out.println("Bussers: $" + tipDistribution[4]);
        System.out.println("Servers: $" + tipDistribution[5]);
        

        // Entering Employees Into the System
        System.out.println("\nEntering Employees into the System");


        // While loop to create Employee objects as long as the user does not exit the loop
        boolean employeeCreation = true; 
        while (employeeCreation) { 
            System.out.print("Please enter the name of the Employee: "); 
            String nameInput = scnr.next(); // Read user input
            // Check if the input is empty or only whitespace
            if (nameInput.trim().isEmpty()) {
                Employee person = new Employee();
            } 
            else {
                System.out.println("Please input the number that corresponds to the employee's job: \n" +
                               "0 = Chef\n" +
                               "1 = Sous chef\n" +
                               "2 = Kitchen aid\n" +
                               "3 = Host / hostess\n" +
                               "4 = Busser\n" +
                               "5 = Server");
                
                try { // Tries to construct an Employee with a job, catches an exception if input is not an int
                    int jobInput = scnr.nextInt(); // Read user input
                    if (jobInput >= 0 && jobInput <= 5) {
                        Employee person = new Employee(nameInput.trim(), jobInput);
                    } 
                    else {
                        Employee person = new Employee(nameInput.trim());
                        System.out.println("Invalid input, employee entered with no assigned job.");
                    }
                    scnr.nextLine(); // Consume the leftover newline character
                }               
                
                catch (InputMismatchException e) {
                    Employee person = new Employee(nameInput.trim());
                    System.out.println("Invalid input, employee entered with no assigned job.");
                    scnr.nextLine(); // Consume the leftover newline character
                }
            }

            System.out.print("Do you want to enter in another employee? (y/n): ");
            loopContinuation = scnr.next().charAt(0);
            System.out.println("------------------------------");
            if (loopContinuation == 'n' || loopContinuation == 'N')
            {
                employeeCreation = false;
                System.out.println("\n------------------------------");
                break;
            }
        }

        // Close Scanner
        scnr.close();

        // Loop to print the information and individual tip amount for all employees created
        for (int i = 0; i < Employee.employeeList.size(); i++) {
            System.out.println("Employee #" + (i+1));
            Employee employee = Employee.employeeList.get(i);
            employee.printInfo();
            
            double individualTip = (tipDistribution[employee.getJob()]) / Employee.getEmployeeCount(employee.getJob());
            System.out.printf("Tip Distribution: $%.2f%n", individualTip);
            System.out.println("------------------------------");

        }
    
        
    }

    /*
    *                  Edge Cases for Program
    * 
        x1. Some customers write the tip amount but not the total amount
        x2. Some customers write the total amount but not the tip amount
        x3. Some customers do not write anything in which case the tip amount is 0
        x4. Some customers write a tip amount and a total amount that does not tally properly
            x▪ In this case the restaurant accepts the total amount and calculates a tip by
            subtracting the marked cost from the total amount
            x▪ If the difference comes to be negative, tip is considered to be 0
        x5. Some customers write a lower total amount than the marked cost
            x▪ In this case the restaurant assumes the tip amount as 0 and considers the
            marked cost as total amount
    * 
    */



    /*                              Process
    * 
        x● Start by creating a class called RestaurantCheckManager
        x● Write a loop that keeps running until the manager asks to terminate
        x● Inside the loop, prompt the manager to enter the sale amount
        x● Then prompt the manager to enter the tip amount
        x● Finally, prompt the manager to enter the total amount
        x● Calculate the total sale amount, the total tip amount, and the number of checks
            xo Pay attention to the edge cases listed above
        x● Ask the manager if they want to stop
            xo If they type ‘y’ or ‘Y’, terminate the loop
            xo Else continue
        x● After that the program should display the total sale amount and the total tip amount
    *
    */



      /*
       *                           Abstract (Description of Program)
       * 
       * This project involves developing an application to manage restaurant checks, focusing 
       * on the calculation of total sales and pooled tips. The challenge is to create a fair 
       * algorithm for distributing tips among workers, reflecting the broader goal of socially 
       * responsible computing. This project serves as a practical example of how computer 
       * science students can engage with ethical considerations in algorithm design, emphasizing 
       * the need for transparency, fairness, and accountability in technology development.
       * 
       */



}