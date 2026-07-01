public class Patient implements Comparable<Patient> {

    private String name;
    private int age;
    private String condition;
    private int priority; // 1-Critical, 2-High, 3-Medium, 4-Low

    public Patient(String name, int age, String condition, int priority) {
        this.name = name;
        this.age = age;
        this.condition = condition;
        this.priority = priority;
    }

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

    public String getPriorityLabel() {
        switch (priority) {
            case 1: return "CRITICAL";
            case 2: return "HIGH";
            case 3: return "MEDIUM";
            default: return "LOW";
        }
    }

    // Enables PriorityQueue to automatically sort patients by priority
    @Override
    public int compareTo(Patient other) {
        return this.priority - other.priority;
    }

    @Override
    public String toString() {
        return "Name: " + name + " | Age: " + age
                + " | Condition: " + condition
                + " | Priority: " + getPriorityLabel();
    }
}
