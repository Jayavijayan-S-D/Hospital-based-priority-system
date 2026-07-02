public class Patient {

    private int id;
    private String name;
    private int age;
    private String condition;
    private int priority;
    private int waitTurns;
    private String assignedDoctor;

    private int heartRate;
    private int spo2;
    private double temperature;

    public Patient(int id, String name, int age, String condition, int priority,
                   String assignedDoctor, int heartRate, int spo2, double temperature) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.condition = condition;
        this.priority = priority;
        this.assignedDoctor = assignedDoctor;
        this.heartRate = heartRate;
        this.spo2 = spo2;
        this.temperature = temperature;
        this.waitTurns = 0;
    }

    public int getId() { return id; }
    public String getName() { 
        return name;
     }
    public int getAge() {
         return age; 
        }
    public String getCondition() {
         return condition;
         }
    public int getPriority() {
         return priority; 
        }
    public void setPriority(int priority) { 
        this.priority = priority; 
    }
    public int getWaitTurns() {
         return waitTurns;
         }
    public void incrementWait() {
         waitTurns++;
         }
    public String getAssignedDoctor() {
         return assignedDoctor;
         }
    public int getHeartRate() {
         return heartRate; 
        }
    public int getSpo2() {
         return spo2; 
        }
    public double getTemperature() {
         return temperature; 
        }

    public String getPriorityLabel() {
        switch (priority) {
            case 1: return "CRITICAL";
            case 2: return "HIGH";
            case 3: return "MEDIUM";
            default: return "LOW";
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Age: " + age
                + " | Condition: " + condition
                + " | Priority: " + getPriorityLabel()
                + " | Waited: " + waitTurns + " turn(s)"
                + " | Doctor: " + assignedDoctor;
    }
}
