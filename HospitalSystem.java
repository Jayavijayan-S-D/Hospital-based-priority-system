import java.util.*;
import java.io.*;

public class HospitalSystem {

    private List<Patient> waitingList = new ArrayList<>();
    private List<Patient> history = new ArrayList<>();
    private Patient lastAttended = null;
    private int nextId = 1;

    private static final String FILE_NAME = "patients_data.txt";
    private static final int ESCALATION_THRESHOLD = 3;
    public void addPatient(String name, int age, String condition, int priority,
                            int heartRate, int spo2, double temperature) {

        String doctor = DoctorAllocator.assignDoctor(condition);
        Patient p = new Patient(nextId++, name, age, condition, priority, doctor, heartRate, spo2, temperature);
        waitingList.add(p);
        sortQueue();

        System.out.println("Patient " + p.getName() + " (ID: " + p.getId() + ") added. Assigned to " + doctor);
    }

    private void sortQueue() {
        waitingList.sort((a, b) -> {
            if (a.getPriority() != b.getPriority()) {
                return a.getPriority() - b.getPriority(); 
            }
            return b.getWaitTurns() - a.getWaitTurns();
    }

    public void attendNext() {
        if (waitingList.isEmpty()) {
            System.out.println("No patients waiting.");
            return;
        }

        sortQueue();
        Patient next = waitingList.remove(0);
        lastAttended = next;
        history.add(next);

        System.out.println("\n--- Attending Next Patient ---");
        System.out.println(next);
        System.out.println("Patient " + next.getName() + " sent to " + next.getAssignedDoctor());

        escalateWaitTimes();
    }
    private void escalateWaitTimes() {
        for (Patient p : waitingList) {
            p.incrementWait();
            if (p.getWaitTurns() % ESCALATION_THRESHOLD == 0 && p.getPriority() > 1) {
                p.setPriority(p.getPriority() - 1);
                System.out.println("Note: " + p.getName() + " waited too long, priority escalated to " + p.getPriorityLabel());
            }
        }
        sortQueue();
    }

    public void viewAll() {
        if (waitingList.isEmpty()) {
            System.out.println("No patients in queue.");
            return;
        }

        sortQueue();
        System.out.println("\n--- Waiting Patients ---");
        for (Patient p : waitingList) {
            System.out.println(p);
        }
    }

    public void totalWaiting() {
        System.out.println("Total patients waiting: " + waitingList.size());
    }

    public void undoLastAttend() {
        if (lastAttended == null) {
            System.out.println("No recent attend to undo.");
            return;
        }

        history.remove(lastAttended);
        waitingList.add(lastAttended);
        sortQueue();

        System.out.println("Recalled patient " + lastAttended.getName() + " back to queue.");
        lastAttended = null;
    }

    public void showAnalytics() {
        if (history.isEmpty()) {
            System.out.println("No patients attended yet.");
            return;
        }

        int totalAttended = history.size();
        double avgWait = 0;
        int[] priorityCount = new int[5];
        Map<String, Integer> conditionCount = new HashMap<>();

        for (Patient p : history) {
            avgWait += p.getWaitTurns();
            priorityCount[p.getPriority()]++;
            conditionCount.merge(p.getCondition(), 1, Integer::sum);
        }
        avgWait /= totalAttended;

        String mostCommonCondition = "";
        int max = 0;
        for (Map.Entry<String, Integer> entry : conditionCount.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                mostCommonCondition = entry.getKey();
            }
        }

        System.out.println("\n===== Analytics Dashboard =====");
        System.out.println("Total Patients Attended : " + totalAttended);
        System.out.printf("Average Wait Turns      : %.2f%n", avgWait);
        System.out.println("Priority Distribution    :");
        System.out.println("  Critical : " + priorityCount[1]);
        System.out.println("  High     : " + priorityCount[2]);
        System.out.println("  Medium   : " + priorityCount[3]);
        System.out.println("  Low      : " + priorityCount[4]);
        System.out.println("Most Common Condition    : " + mostCommonCondition);
        System.out.println("================================");
    }

    public void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Patient p : waitingList) {
                pw.println(p.getId() + "|" + p.getName() + "|" + p.getAge() + "|" + p.getCondition() + "|"
                        + p.getPriority() + "|" + p.getWaitTurns() + "|" + p.getAssignedDoctor() + "|"
                        + p.getHeartRate() + "|" + p.getSpo2() + "|" + p.getTemperature());
            }
            System.out.println("Queue saved to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int maxId = 0;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                int age = Integer.parseInt(parts[2]);
                String condition = parts[3];
                int priority = Integer.parseInt(parts[4]);
                int waitTurns = Integer.parseInt(parts[5]);
                String doctor = parts[6];
                int heartRate = Integer.parseInt(parts[7]);
                int spo2 = Integer.parseInt(parts[8]);
                double temperature = Double.parseDouble(parts[9]);

                Patient p = new Patient(id, name, age, condition, priority, doctor, heartRate, spo2, temperature);
                for (int i = 0; i < waitTurns; i++) p.incrementWait();

                waitingList.add(p);
                if (id > maxId) maxId = id;
            }

            nextId = maxId + 1;
            sortQueue();
            System.out.println("Loaded " + waitingList.size() + " patient(s) from previous session.");
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}
