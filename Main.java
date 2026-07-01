import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    private static PriorityQueue<Patient> queue = new PriorityQueue<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        System.out.println("====================================");
        System.out.println("  HOSPITAL PATIENT PRIORITY SYSTEM  ");
        System.out.println("====================================");

        do {
            showMenu();
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    attendNext();
                    break;
                case 3:
                    viewAll();
                    break;
                case 4:
                    totalWaiting();
                    break;
                case 5:
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 5);

        sc.close();
    }

    static void showMenu() {
        System.out.println("\n1. Add Patient");
        System.out.println("2. Attend Next Patient");
        System.out.println("3. View All Patients");
        System.out.println("4. Total Patients Waiting");
        System.out.println("5. Exit");
        System.out.print("Enter choice: ");
    }

    static void addPatient() {
        System.out.print("Enter patient name: ");
        String name = sc.nextLine();

        System.out.print("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter condition: ");
        String condition = sc.nextLine();

        System.out.println("Priority: 1-Critical  2-High  3-Medium  4-Low");
        System.out.print("Enter priority: ");
        int priority = sc.nextInt();
        sc.nextLine();

        Patient patient = new Patient(name, age, condition, priority);
        queue.add(patient);

        System.out.println("Patient " + patient.getName() + " added successfully!");
    }

    static void attendNext() {
        if (queue.isEmpty()) {
            System.out.println("No patients waiting.");
            return;
        }

        Patient next = queue.poll();

        System.out.println("\n--- Attending Next Patient ---");
        System.out.println("Name      : " + next.getName());
        System.out.println("Age       : " + next.getAge());
        System.out.println("Condition : " + next.getCondition());
        System.out.println("Priority  : " + next.getPriorityLabel());
        System.out.println("Patient " + next.getName() + " sent to doctor.");
    }

    static void viewAll() {
        if (queue.isEmpty()) {
            System.out.println("No patients in queue.");
            return;
        }

        System.out.println("\n--- Waiting Patients ---");
        for (Patient p : queue) {
            System.out.println(p);
        }
    }

    static void totalWaiting() {
        System.out.println("Total patients waiting: " + queue.size());
    }
}
