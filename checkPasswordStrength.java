import java.util.Scanner;

public class PasswordStrengthChecker {

    public static String checkPasswordStrength(String password) {
        // Minimum length
        if (password.length() < 8) {
            return "Weak: Password length should be at least 8 characters";
        }

        // Checking for uppercase, lowercase, digits, and special characters
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isLowerCase(ch)) {
                hasLower = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if ("!@#$%^&*()-_=+[{]}|;:'\",<.>/?`~".indexOf(ch) != -1) {
                hasSpecial = true;
            }
        }

        // Calculating score based on conditions met
        int score = (hasUpper ? 1 : 0) + (hasLower ? 1 : 0) + (hasDigit ? 1 : 0) + (hasSpecial ? 1 : 0);

        // Assigning strength level based on score
        if (score == 4) {
            return "Strong";
        } else if (score >= 2) {
            return "Moderate";
        } else {
            return "Weak: Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character";
        }
    }

    public static String suggestImprovement(String password) {
        boolean needsNumber = !password.matches(".\\d.");
        boolean needsSpecial = !password.matches(".[!@#$%^&()-_=+\\[{\\]}|;:'\",<.>/?`~].*");

        String suggestion = "";
        if (needsNumber && needsSpecial) {
            suggestion = "Consider adding numbers and special characters to increase the password strength.";
        } else if (needsNumber) {
            suggestion = "Consider adding numbers to increase the password strength.";
        } else if (needsSpecial) {
            suggestion = "Consider adding special characters to increase the password strength.";
        }

        return suggestion;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        String strength = checkPasswordStrength(password);
        System.out.println("Password strength: " + strength);
        if (strength.equals("Weak")) {
            String improvementSuggestion = suggestImprovement(password);
            System.out.println(improvementSuggestion);
        }
    }
}
