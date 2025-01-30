// STUDENT GRADE TRACKER

import java.util.*;

public class Task_1 { 

    public static void main(String[] args) {
        List<Double> grades = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Student Grade Tracker!");

        while (true) {
            System.out.print("Enter a student's grade: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("done")) {
                break;
            }

            try {
                double grade = Double.parseDouble(input);
                if (grade < 0 || grade > 100) {
                    System.out.println("Please enter a grade between 0 and 100.");
                    continue;
                }
                grades.add(grade);
            } catch (NumberFormatException e) {
                System.out.println(" Please enter a numeric grade or 'done'.");
            }
        }

        if (grades.isEmpty()) {
            System.out.println("No grades were entered.");
        } else {
            double average = calculateAverage(grades);
            double highest = Collections.max(grades);
            double lowest = Collections.min(grades);

            System.out.println("\n Grade Summary:");
            System.out.println("--------------------");
            System.out.printf(" Average Grade: %.2f\n", average);
            System.out.printf(" Highest Grade: %.2f\n", highest);
            System.out.printf(" Lowest Grade: %.2f\n", lowest);
        }

        scanner.close();
    }

    public static double calculateAverage(List<Double> grades) {
        return grades.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }
}
