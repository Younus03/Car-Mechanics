import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

class Mechanic {
    private int id;
    private String name;
    private String specialization;
    private boolean available;

    public Mechanic(int id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.available = true;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}

class Booking {
    private int bookingId;
    private String customerName;
    private String carModel;
    private int mechanicId;
    private LocalDateTime bookingTime;
    private String status;

    public Booking(int bookingId, String customerName, String carModel, int mechanicId, LocalDateTime bookingTime) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.carModel = carModel;
        this.mechanicId = mechanicId;
        this.bookingTime = bookingTime;
        this.status = "Confirmed";
    }

    public void displayDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("\n--- Booking Confirmation ---");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Customer: " + customerName);
        System.out.println("Car Model: " + carModel);
        System.out.println("Mechanic ID: " + mechanicId);
        System.out.println("Booking Time: " + bookingTime.format(formatter));
        System.out.println("Status: " + status);
    }
}

public class CarMechanicBookingSystem {
    private static ArrayList<Mechanic> mechanics = new ArrayList<>();
    private static ArrayList<Booking> bookings = new ArrayList<>();
    private static int bookingCounter = 1001;

    public static void main(String[] args) {
        initializeMechanics();
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("Welcome to Car Mechanic Booking System!");

        while (true) {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAvailableMechanics();
                    break;
                case 2:
                    bookMechanic(scanner);
                    break;
                case 3:
                    viewBookings();
                    break;
                case 4:
                    System.out.println("Thank you for using Car Mechanic Booking System!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeMechanics() {
        mechanics.add(new Mechanic(1, "John Smith", "Engine Repair"));
        mechanics.add(new Mechanic(2, "Sarah Johnson", "Brake Service"));
        mechanics.add(new Mechanic(3, "Mike Davis", "Electrical"));
        mechanics.add(new Mechanic(4, "Emma Wilson", "General Maintenance"));
    }

    private static void displayMenu() {
        System.out.println("\n===== Menu =====");
        System.out.println("1. View Available Mechanics");
        System.out.println("2. Book a Mechanic");
        System.out.println("3. View My Bookings");
        System.out.println("4. Exit");
    }

    private static void viewAvailableMechanics() {
        System.out.println("\n===== Available Mechanics =====");
        boolean found = false;
        for (Mechanic m : mechanics) {
            if (m.isAvailable()) {
                System.out.println("ID: " + m.getId() + " | Name: " + m.getName() + 
                                 " | Specialization: " + m.getSpecialization());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No mechanics available at the moment.");
        }
    }

    private static void bookMechanic(Scanner scanner) {
        System.out.print("\nEnter your name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter your car model: ");
        String carModel = scanner.nextLine();

        viewAvailableMechanics();

        System.out.print("Enter Mechanic ID: ");
        int mechanicId = scanner.nextInt();
        scanner.nextLine();

        Mechanic selectedMechanic = null;
        for (Mechanic m : mechanics) {
            if (m.getId() == mechanicId && m.isAvailable()) {
                selectedMechanic = m;
                break;
            }
        }

        if (selectedMechanic == null) {
            System.out.println("Mechanic not available or invalid ID.");
            return;
        }

        System.out.print("Enter booking date (yyyy-MM-dd HH:mm): ");
        String dateInput = scanner.nextLine();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime bookingTime = LocalDateTime.parse(dateInput, formatter);

            int bookingId = bookingCounter++;
            Booking booking = new Booking(bookingId, customerName, carModel, mechanicId, bookingTime);
            bookings.add(booking);
            selectedMechanic.setAvailable(false);

            booking.displayDetails();
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm");
        }
    }

    private static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("\nNo bookings found.");
        } else {
            System.out.println("\n===== Your Bookings =====");
            for (Booking b : bookings) {
                b.displayDetails();
            }
        }
    }
}
