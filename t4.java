import java.util.*;

class HotelSK
{
    List<Room> rooms = new ArrayList<>();
    List<Reservation> reservations = new ArrayList<>();
    int reservationCounter = 1;

    public HotelSK()
    {
        rooms.add(new Room(1, 2000.0, true));
        rooms.add(new Room(2, 2000.0, true));
        rooms.add(new Room(3, 2000.0, true));
        rooms.add(new Room(4, 2000.0, true));
        rooms.add(new Room(5, 2000.0, true));
    }

    public List<Room> searchRooms()
    {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms)
        {
            if (room.isAvailable())
            {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Reservation bookRoom(int roomId, String customerName, String EntryDate, String ExitDate)
    {
        Optional<Room> roomOpt = rooms.stream().filter(room -> room.id == roomId && room.isAvailable()).findFirst();
        if (roomOpt.isPresent())
        {
            Room room = roomOpt.get();
            room.setAvailable(false);
            Reservation reservation = new Reservation(reservationCounter++, roomId, customerName, EntryDate, ExitDate, room.getPrice());
            reservations.add(reservation);
            return reservation;
        }
        return null;
    }

    public List<Reservation> viewReservations(String customerName)
    {
        List<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.customerName.equalsIgnoreCase(customerName))
            {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }
}

class t4 {
    public static void main(String[] args) {
        HotelSK hotel = new HotelSK();
        PaymentProcessor paymentProcessor = new PaymentProcessor();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Search for available rooms");
            System.out.println("2. Book a room");
            System.out.println("3. Check your bookings");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    List<Room> availableRooms = hotel.searchRooms();
                    if (availableRooms.isEmpty()) {
                        System.out.println("No available rooms found.");
                    } else {
                        for (Room room : availableRooms) {
                            System.out.println("Room ID: " + room.id + ", Price: $" + room.getPrice());
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter room ID: ");
                    int roomId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter check-in date (DD-MM-YYYY): ");
                    String EntryDate = scanner.nextLine();
                    System.out.print("Enter check-out date (DD-MM-YYYY): ");
                    String ExitDate = scanner.nextLine();
                    Reservation reservation = hotel.bookRoom(roomId, name, EntryDate, ExitDate);
                    if (reservation != null) {
                        System.out.print("Enter credit card number: ");
                        String creditCardNumber = scanner.nextLine();
                        if (paymentProcessor.processPayment(creditCardNumber, reservation.getPrice())) {
                            System.out.println("Reservation successful! Reservation ID: " + reservation.id);
                        } else {
                            System.out.println("Payment failed. Reservation not confirmed.");
                        }
                    } else {
                        System.out.println("Room not available.");
                    }
                    break;
                case 3:
                    System.out.print("Enter your name: ");
                    String customerName = scanner.nextLine();
                    List<Reservation> customerReservations = hotel.viewReservations(customerName);
                    if (customerReservations.isEmpty()) {
                        System.out.println("No Booking found for " + customerName);
                    } else {
                        for (Reservation res : customerReservations) {
                            System.out.println("Booking ID: " + res.id + ", Room ID: " + res.roomId + ", Check-in: " + res.EntryDate + ", Check-out: " + res.ExitDate);
                        }
                    }
                    break;
                case 4:
                    System.out.println("Exiting the system.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

class Room
{
    int id;
    double price;
    boolean isAvailable;

    public Room(int id, double price, boolean isAvailable)
    {
        this.id = id;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public double getPrice()
    {
        return price;
    }
    public boolean isAvailable()
    {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable)
    {
        this.isAvailable = isAvailable;
    }
}

class Reservation
{
    int id;
    int roomId;
    String customerName;
    String EntryDate;
    String ExitDate;
    double price;

    public Reservation(int id, int roomId, String customerName, String EntryDate, String ExitDate, double price)
    {
        this.id = id;
        this.roomId = roomId;
        this.customerName = customerName;
        this.EntryDate = EntryDate;
        this.ExitDate = ExitDate;
        this.price = price;
    }

    public double getPrice()
    {
        return price;
    }
}


class PaymentProcessor
{
    public boolean processPayment(String creditCardNumber, double amount)
    {
        if (creditCardNumber != null && !creditCardNumber.isEmpty() && amount > 0)
        {
            System.out.println("Total amount of $" + amount + " processed successfully.");
            return true;
        }
        return false;
    }
}
