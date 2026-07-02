public class PriorityCalculator {

    public static int calculatePriority(int heartRate, int spo2, double temperature) {

        if (spo2 < 90 || heartRate > 130 || heartRate < 40 || temperature > 104) {
            return 1; 
        } else if (spo2 < 95 || heartRate > 110 || temperature > 102) {
            return 2; 
        } else if (spo2 < 98 || heartRate > 100 || temperature > 100) {
            return 3; 
        } else {
            return 4;
        }
    }
}

