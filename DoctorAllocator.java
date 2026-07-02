
public class DoctorAllocator {

    public static String assignDoctor(String condition) {
        String c = condition.toLowerCase();

        if (c.contains("chest") || c.contains("heart") || c.contains("cardiac")) {
            return "Dr. Arjun (Cardiology)";
        } else if (c.contains("bone") || c.contains("fracture") || c.contains("joint")) {
            return "Dr. Meera (Orthopedic)";
        } else if (c.contains("skin") || c.contains("rash") || c.contains("allergy")) {
            return "Dr. Kavya (Dermatology)";
        } else if (c.contains("child") || c.contains("infant")) {
            return "Dr. Ravi (Pediatrics)";
        } else if (c.contains("brain") || c.contains("head") || c.contains("seizure")) {
            return "Dr. Sanjay (Neurology)";
        } else {
            return "Dr. Priya (General Physician)";
        }
    }
}
