import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class Duke {
    private static final String FILE_PATH = "src/main/java/tasks.txt";
    /**
     * Main method to start the Duke application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>(100);
        loadTasksFromFile(tasks);
        String logo = "UUUUUUUU     UUUUUUUURRRRRRRRRRRRRRRRR   BBBBBBBBBBBBBBBBB        OOOOOOOOO     IIIIIIIIII\n"
                +"U::::::U     U::::::UR::::::::::::::::R  B::::::::::::::::B     OO:::::::::OO   I::::::::I\n"
                +"U::::::U     U::::::UR::::::RRRRRR:::::R B::::::BBBBBB:::::B  OO:::::::::::::OO I::::::::I\n"
                +"UU:::::U     U:::::UURR:::::R     R:::::RBB:::::B     B:::::BO:::::::OOO:::::::OII::::::II\n"
                +" U:::::U     U:::::U   R::::R     R:::::R  B::::B     B:::::BO::::::O   O::::::O  I::::I  \n"
                +" U:::::D     D:::::U   R::::R     R:::::R  B::::B     B:::::BO:::::O     O:::::O  I::::I  \n"
                +" U:::::D     D:::::U   R::::RRRRRR:::::R   B::::BBBBBB:::::B O:::::O     O:::::O  I::::I  \n"
                +" U:::::D     D:::::U   R:::::::::::::RR    B:::::::::::::BB  O:::::O     O:::::O  I::::I  \n"
                +" U:::::D     D:::::U   R::::RRRRRR:::::R   B::::BBBBBB:::::B O:::::O     O:::::O  I::::I  \n"
                +" U:::::D     D:::::U   R::::R     R:::::R  B::::B     B:::::BO:::::O     O:::::O  I::::I  \n"
                +" U:::::D     D:::::U   R::::R     R:::::R  B::::B     B:::::BO:::::O     O:::::O  I::::I  \n"
                +" U::::::U   U::::::U   R::::R     R:::::R  B::::B     B:::::BO::::::O   O::::::O  I::::I  \n"
                +" U:::::::UUU:::::::U RR:::::R     R:::::RBB:::::BBBBBB::::::BO:::::::OOO:::::::OII::::::II\n"
                +"  UU:::::::::::::UU  R::::::R     R:::::RB:::::::::::::::::B  OO:::::::::::::OO I::::::::I\n"
                +"    UU:::::::::UU    R::::::R     R:::::RB::::::::::::::::B     OO:::::::::OO   I::::::::I\n"
                +"      UUUUUUUUU      RRRRRRRR     RRRRRRRBBBBBBBBBBBBBBBBB        OOOOOOOOO     IIIIIIIIII\n";

        System.out.println("Wazzup! It's\n" + logo);
        System.out.println("What can I do for you mah man?");
        System.out.println("____________________________________________________________");

        while (true) {
            String command = scanner.nextLine();
            System.out.println("____________________________________________________________");
            try{
                if (command.equalsIgnoreCase("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                } else if (command.equalsIgnoreCase("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                } else if (command.startsWith("todo")) {
                    String description = command.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new DukeException("The description of a todo cannot be empty.");
                    }
                    tasks.add(new Todo(description));
                    System.out.println("Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                } else if (command.startsWith("deadline")) {
                    // Parse the date and time in the format d/M/yyyy HHmm
                    String[] parts = command.split(" /by ");
                    if (parts.length < 2) {
                        throw new DukeException("Deadline command must include a date.");
                    }
                    String description = parts[0].substring(9).trim();
                    LocalDateTime dateTime = LocalDateTime.parse(parts[1], DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));

                    tasks.add(new Deadline(description, dateTime));
                    System.out.println("Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                } else if (command.startsWith("event")) {
                    String description = command.substring(6, command.indexOf("/from")).trim();
                    String from = command.substring(command.indexOf("/from") + 6, command.indexOf("/to")).trim();
                    String to = command.substring(command.indexOf("/to") + 4).trim();
                    tasks.add(new Event(description, from, to));
                    System.out.println("Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                } else if (command.startsWith("mark")) {
                    int index = Integer.parseInt(command.split(" ")[1]) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markDone();
                        System.out.println("Nice! I've marked this task as done:\n  " + tasks.get(index));
                    } else {
                        System.out.println("Invalid task index.");
                    }
                } else if (command.startsWith("unmark")) {
                    int index = Integer.parseInt(command.split(" ")[1]) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markNotDone();
                        System.out.println("OK, I've marked this task as not done yet:\n  " + tasks.get(index));
                    } else {
                        System.out.println("Invalid task index.");
                    }
                }  else if (command.startsWith("delete")) {
                    int index = Integer.parseInt(command.split(" ")[1]) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task removedTask = tasks.remove(index);
                        System.out.println("Noted. I've removed this task:\n  " + removedTask);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    } else {
                        System.out.println("Invalid task index.");
                    }
                } else if (command.startsWith("find")) {
                    String keyword = command.substring(5).trim();
                    findTasks(tasks, keyword);
                } else {
                    throw new DukeException("I'm sorry, but I don't know what that means :-(");
                }
                saveTasksToFile(tasks);
            }
            catch (DukeException e) {
                System.out.println("☹ OOPS!!! " + e.getMessage());
            }

            System.out.println("____________________________________________________________");
        }


        scanner.close();
    }
    private static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }
    /**
     * Loads tasks from a file into the task list.
     *
     * @param tasks The ArrayList to store the loaded tasks.
     */
    private static void loadTasksFromFile(ArrayList<Task> tasks) {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    Task task = createTaskFromLine(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
                fileScanner.close();
            }
        } catch (FileNotFoundException e) {
            // Handle file not found exception
            System.out.println("File not found: " + FILE_PATH);
        }
    }

    /**
     * Creates a Task object from a line of text in the specified format.
     *
     * @param line The line of text containing task details.
     * @return A Task object representing the task described in the line, or null if parsing fails.
     */
    private static Task createTaskFromLine(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        String status = parts[1];
        String description = parts[2];

        Task task = null;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length >= 4) {
                    String by = parts[3];
                    task = new Deadline(description, LocalDateTime.parse(by, DateTimeFormatter.ofPattern("d/M/yyyy HHmm")));
                }
                break;
            case "E":
                if (parts.length >= 5) {
                    String from = parts[3];
                    String to = parts[4];
                    task = new Event(description, from, to);
                }
                break;
        }

        if (task != null) {
            if (status.equals("1")) {
                task.markDone();
            } else {
                task.markNotDone();
            }
        }

        return task;
    }

    private static void findTasks(ArrayList<Task> tasks, String keyword) {
        System.out.println("Here are the matching tasks in your list:");
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getDescription().contains(keyword)) {
                System.out.println((count + 1) + ". " + task);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No matching tasks found.");
        }
    }

    /**
     * Save tasks to a file.
     *
     * @param tasks The ArrayList of tasks to save.
     */
    private static void saveTasksToFile(ArrayList<Task> tasks) {
        try {
            FileWriter fileWriter = new FileWriter(FILE_PATH);
            for (Task task : tasks) {
                fileWriter.write(task.toFileString() + System.lineSeparator());
            }
            fileWriter.close();
        } catch (IOException e) {
            // Handle IO exception
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }
}



/**
 * Represents a task that can be added to the task list.
 */
class Task {
    protected String description;
    protected boolean isDone;

    protected LocalDateTime date;

    public String toFileString() {
        return "";
    }
    public String formatDate() {
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }
    public Task(String description, LocalDateTime date) {
        this.description = description;
        this.isDone = false;
        this.date = date;
    }
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // Return a tick or cross symbol cuz im lazy like that, or its easier. idk
    }
    /**
     * Get the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }
    public boolean isDone() {
        return isDone;
    }
    public void markDone() {
        isDone = true;
    }
    public void markNotDone() {
        isDone = false;
    }
    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + description;
    }
}

/**
 * Represents a Todo task.
 */
class Todo extends Task {
    public Todo(String description) {
        super(description,null);
    }
    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

/**
 * Represents a Deadline task.
 */
class Deadline extends Task {
    protected String by;

    public Deadline(String description, LocalDateTime date) {
        super(description, date);
    }
    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + date;
    }
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + date + ")";
    }
}

/**
 * Represents an Event task.
 */
class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description,null);
        this.from = from;
        this.to = to;
    }
    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + to;
    }
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}

/**
 * Custom exception class for Duke-specific exceptions.
 */
class DukeException extends Exception {
    public DukeException(String message) {
        super(message);
    }
}