import java.util.ArrayList;

public class Employee {
    /*
     * 0 = Chef
       1 = Sous chef
       2 = Kitchen aid
       3 = Host / hostess
       4 = Busser
       5 = Server
     */
    String name;
    private int job;
    public static ArrayList<Employee> employeeList = new ArrayList<Employee>(); // ArrayList created to keep track of all Employees


    public Employee() { //If an Employee is constructed with no identifiers, it is defaulted without a name and set as a N/a
        name = "No Name";
        job = -1; 
        employeeList.add(this);
    }

    public Employee(String inputName) { //If an Employee is constructed with only a name, they are set as a N/a
        name = inputName;
        job = -1;
        employeeList.add(this);
    }

    public Employee(String inputName, int assignedJob) {
        name = inputName;
        job = assignedJob;
        employeeList.add(this);
    }

    public int getJob() {
        return job;
    }

    //method to get the count of employees for each job
    public static int getEmployeeCount(int job) {
        int count = 0;
        for (int i = 0; i < employeeList.size(); i++) { // Use a regular for loop with an index
            Employee employee = employeeList.get(i); // Access employee by index
            if (employee.job == job) {
                count++;
            }
        }
        return count;
    }

    public void printInfo() {
        System.out.println("Name: " + name);
        System.out.print("Job: ");
        switch (job) {
            case 0:
                System.out.println("Chef");
                break;
            case 1:
                System.out.println("Sous chef");
                break;
            case 2:
                System.out.println("Kitchen aid");
                break;
            case 3:
                System.out.println("Host/hostess");
                break;
            case 4:
                System.out.println("Busser");
                break;
            case 5:
                System.out.println("Server");
                break;
            default:
                System.out.println("N/a");
                break;
        }
    }
}
