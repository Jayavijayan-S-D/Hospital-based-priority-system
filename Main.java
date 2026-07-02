import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static HospitalSystem system = new HospitalSystem();

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("  HOSPITAL PATIENT PRIORITY SYSTEM  ");
        System.out.println("====================================");

        system.loadFromFile();

        int choice;

        do {
            showMenu();
            choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    system.attendNext();
                    break;
                case 3:
                    system.viewAll();
                    break;
                case 4:
                    system.totalWaiting();
                    break;
                case 5:
                    system.undoLastAttend();
                    break;
                case 6:
                    system.showAnalytics();
                    break;
                case 7:
                    system.saveToFile();
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 7);

        sc.close();
    }

    static void showMenu() {
        System.out.println("\n1. Add Patient");
        System.out.println("2. Attend Next Patient");
        System.out.println("3. View All Patients");
        System.out.println("4. Total Patients Waiting");
        System.out.println("5. Undo Last Attend");
        System.out.println("6. View Analytics");
        System.out.println("7. Save & Exit");
    }

    static void addPatient() {
        System.out.print("Enter patient name: ");
        String name = sc.nextLine();

        int age = readInt("Enter age: ");

        System.out.print("Enter condition: ");
        String condition = sc.nextLine();

        System.out.println("Priority Mode: 1-Manual  2-Auto (based on vitals)");
        int mode = readInt("Enter choice: ");

        int priority;
        int heartRate = 0, spo2 = 0;
        double temperature = 0;

        if (mode == 2) {
            heartRate = readInt("Enter heart rate (bpm): ");
            spo2 = readInt("Enter SpO2 (%): ");
            temperature = readDouble("Enter temperature (F): ");
            priority = PriorityCalculator.calculatePriority(heartRate, spo2, temperature);
            System.out.println("Auto-calculated priority: " + priority + " (" + priorityLabel(priority) + ")");
        } else {
            System.out.println("Priority: 1-Critical  2-High  3-Medium  4-Low");
            priority = readInt("Enter priority: ");
            if (priority < 1 || priority > 4) {
                System.out.println("Invalid priority, defaulting to Medium.");
                priority = 3;
            }
        }

        system.addPatient(name, age, condition, priority, heartRate, spo2, temperature);
    }

    static String priorityLabel(int priority) {
        switch (priority) {
            case 1: return "CRITICAL";
            case 2: return "HIGH";
            case 3: return "MEDIUM";
            default: return "LOW";
        }
    }

    // Reads an integer safely, re-prompting on invalid input instead of crashing
    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
