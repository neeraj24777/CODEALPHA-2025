// HOTEL RESERVATION SYSTEM 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Room {
    int roomId;
    String category;
    boolean isAvailable;
    double price;

    public Room(int roomId, String category, boolean isAvailable, double price) {
        this.roomId = roomId;
        this.category = category;
        this.isAvailable = isAvailable;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room ID: " + roomId + ", Category: " + category + ", Price: $" + price + ", Available: " + (isAvailable ? "Yes" : "No");
    }
}

class Booking {
    int bookingId;
    String customerName;
    Room room;
    Date checkInDate;
    Date checkOutDate;

    public Booking(int bookingId, String customerName, Room room, Date checkInDate, Date checkOutDate) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId + ", Customer: " + customerName + ", Room: " + room.roomId +
                ", Check-In: " + checkInDate + ", Check-Out: " + checkOutDate;
    }
}

public class Task_2 { // Java does not allow spaces in class names
    private List<Room> rooms = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private int bookingCounter = 1;

    public void addRoom(int roomId, String category, double price) {
        rooms.add(new Room(roomId, category, true, price));
    }

    public List<Room> searchAvailableRooms(String category) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable && room.category.equalsIgnoreCase(category)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Booking makeReservation(String customerName, int roomId, Date checkIn, Date checkOut) {
        for (Room room : rooms) {
            if (room.roomId == roomId && room.isAvailable) {
                room.isAvailable = false;
                Booking booking = new Booking(bookingCounter++, customerName, room, checkIn, checkOut);
                bookings.add(booking);
                return booking;
            }
        }
        return null; // Room not available
    }

    public Booking viewBookingDetails(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.bookingId == bookingId) {
                return booking;
            }
        }
        return null; // Booking not found
    }

    public void processPayment(Booking booking, double amount) {
        if (booking != null) {
            System.out.println("Processing payment of $" + amount + " for Booking ID: " + booking.bookingId);
        } else {
            System.out.println("Invalid booking for payment.");
        }
    }

    public static void main(String[] args) {
        Task_2 system = new Task_2(); // Using Task_2 since spaces are not allowed in class names

        system.addRoom(101, "Single", 50.0);
        system.addRoom(102, "Double", 75.0);
        system.addRoom(103, "Suite", 120.0);

        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("Welcome to Hotel Reservation System!");
        while (true) {
            System.out.println("\n1. Search Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View Booking Details");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter room category (Single/Double/Suite): ");
                    String category = scanner.nextLine();
                    List<Room> availableRooms = system.searchAvailableRooms(category);
                    if (availableRooms.isEmpty()) {
                        System.out.println("No available rooms in this category.");
                    } else {
                        System.out.println("Available Rooms:");
                        for (Room room : availableRooms) {
                            System.out.println(room);
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Room ID to book: ");
                    int roomId = scanner.nextInt();
                    scanner.nextLine(); 

                    System.out.print("Enter check-in date (YYYY-MM-DD): ");
                    String checkInStr = scanner.nextLine();
                    System.out.print("Enter check-out date (YYYY-MM-DD): ");
                    String checkOutStr = scanner.nextLine();

                    try {
                        Date checkIn = dateFormat.parse(checkInStr);
                        Date checkOut = dateFormat.parse(checkOutStr);

                        if (checkOut.before(checkIn)) {
                            System.out.println("Check-out date must be after check-in date.");
                            break;
                        }

                        Booking booking = system.makeReservation(name, roomId, checkIn, checkOut);
                        if (booking != null) {
                            System.out.println("Booking successful: " + booking);
                            system.processPayment(booking, booking.room.price);
                        } else {
                            System.out.println("Room is not available or invalid Room ID.");
                        }
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Booking ID: ");
                    int bookingId = scanner.nextInt();
                    Booking booking = system.viewBookingDetails(bookingId);
                    if (booking != null) {
                        System.out.println("Booking Details: " + booking);
                    } else {
                        System.out.println("Booking not found.");
                    }
                    break;

                case 4:
                    System.out.println("Thank you for using the Hotel Reservation System!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

