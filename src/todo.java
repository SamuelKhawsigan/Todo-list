import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class todo {
    private static ArrayList<String> tasks = new ArrayList<>();
    private static final String FILE_PATH = "tasks.txt"; // Saves in project folder
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadTasks(); // Load saved tasks on startup

        while (true) {
            System.out.println("\n--- To-Do List Menu ---");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Delete Task");
            System.out.println("4. Clear All Tasks");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addTask();
                        break;
                    case 2:
                        viewTasks();
                        break;
                    case 3:
                        deleteTask();
                        break;
                    case 4:
                        clearAllTasks();
                        break;
                    case 5:
                        saveTasks();
                        System.out.println("Tasks saved. Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option! Please choose 1-5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number (1-5)!");
            }
        }
    }

    // Add a new task
    private static void addTask() {
        System.out.print("Enter task to add: ");
        String task = scanner.nextLine().trim();
        if (!task.isEmpty()) {
            tasks.add(task);
            System.out.println("Task added: '" + task + "'");
        } else {
            System.out.println("Task cannot be empty!");
        }
    }

    // View all tasks
    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found. Your list is empty!");
        } else {
            System.out.println("\n--- Your Tasks ---");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    // Delete a task by number
    private static void deleteTask() {
        viewTasks();
        if (!tasks.isEmpty()) {
            try {
                System.out.print("Enter task number to delete: ");
                int index = Integer.parseInt(scanner.nextLine()) - 1;
                if (index >= 0 && index < tasks.size()) {
                    String removedTask = tasks.remove(index);
                    System.out.println("Deleted task: '" + removedTask + "'");
                } else {
                    System.out.println("Invalid task number!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    // Clear all tasks
    private static void clearAllTasks() {
        if (!tasks.isEmpty()) {
            System.out.print("Are you sure? This cannot be undone. (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (confirm.equals("y")) {
                tasks.clear();
                System.out.println("All tasks deleted.");
            } else {
                System.out.println("Operation canceled.");
            }
        } else {
            System.out.println("Task list is already empty!");
        }
    }

    // Save tasks to file
    private static void saveTasks() {
        try (PrintWriter writer = new PrintWriter(FILE_PATH)) {
            for (String task : tasks) {
                writer.println(task);
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    // Load tasks from file
    private static void loadTasks() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    tasks.add(line);
                }
                System.out.println("Loaded " + tasks.size() + " saved task(s).");
            } catch (IOException e) {
                System.err.println("Error loading tasks: " + e.getMessage());
            }
        }
    }
}

