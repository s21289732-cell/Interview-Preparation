import java.util.*;

// Vehicle class
class Vehicle {
    String vehicleNumber;
    String type;
    long entryTime;

    Vehicle(String vehicleNumber, String type) {
        this.vehicleNumber = vehicleNumber;
        this.type = type;
        this.entryTime = System.currentTimeMillis();
    }
}

// Parking Slot class
class ParkingSlot {
    int slotId;
    boolean isOccupied;
    Vehicle vehicle;

    ParkingSlot(int slotId) {
        this.slotId = slotId;
        this.isOccupied = false;
        this.vehicle = null;
    }

    void parkVehicle(Vehicle v) {
        this.vehicle = v;
        this.isOccupied = true;
    }

    void removeVehicle() {
        this.vehicle = null;
        this.isOccupied = false;
    }
}

// Parking Lot class
class ParkingLot {
    List<ParkingSlot> slots;

    ParkingLot(int totalSlots) {
        slots = new ArrayList<>();
        for (int i = 1; i <= totalSlots; i++) {
            slots.add(new ParkingSlot(i));
        }
    }

    // Park vehicle
    void parkVehicle(String number, String type) {
        for (ParkingSlot slot : slots) {
            if (!slot.isOccupied) {
                Vehicle v = new Vehicle(number, type);
                slot.parkVehicle(v);
                System.out.println("Vehicle parked at slot: " + slot.slotId);
                return;
            }
        }
        System.out.println("Parking Full!");
    }

    // Remove vehicle
    void removeVehicle(String number) {
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied && slot.vehicle.vehicleNumber.equals(number)) {
                long exitTime = System.currentTimeMillis();
                long duration = (exitTime - slot.vehicle.entryTime) / 1000; // seconds

                double fee = calculateFee(duration, slot.vehicle.type);

                System.out.println("Vehicle Found at slot: " + slot.slotId);
                System.out.println("Parking Duration: " + duration + " seconds");
                System.out.println("Total Fee: ₹" + fee);

                slot.removeVehicle();
                return;
            }
        }
        System.out.println("Vehicle not found!");
    }

    // Display slots
    void displayStatus() {
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied) {
                System.out.println("Slot " + slot.slotId + ": Occupied (" + slot.vehicle.vehicleNumber + ")");
            } else {
                System.out.println("Slot " + slot.slotId + ": Free");
            }
        }
    }

    // Fee calculation
    double calculateFee(long duration, String type) {
        double rate;

        switch (type.toLowerCase()) {
            case "bike":
                rate = 5;
                break;
            case "car":
                rate = 10;
                break;
            case "truck":
                rate = 20;
                break;
            default:
                rate = 8;
        }

        return rate * duration;
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of parking slots: ");
        int n = sc.nextInt();

        ParkingLot lot = new ParkingLot(n);

        while (true) {
            System.out.println("\n--- Parking System Menu ---");
            System.out.println("1. Park Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. Display Status");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Vehicle Number: ");
                    String num = sc.next();

                    System.out.print("Enter Vehicle Type (bike/car/truck): ");
                    String type = sc.next();

                    lot.parkVehicle(num, type);
                    break;

                case 2:
                    System.out.print("Enter Vehicle Number to remove: ");
                    String removeNum = sc.next();

                    lot.removeVehicle(removeNum);
                    break;

                case 3:
                    lot.displayStatus();
                    break;

                case 4:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}