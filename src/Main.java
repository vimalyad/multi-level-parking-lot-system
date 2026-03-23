package MultiLevelParkingLotSystem.src;


import MultiLevelParkingLotSystem.src.entities.Gate;
import MultiLevelParkingLotSystem.src.entities.Slot;
import MultiLevelParkingLotSystem.src.entities.Ticket;
import MultiLevelParkingLotSystem.src.entities.Vehicle;
import MultiLevelParkingLotSystem.src.enums.SlotType;
import MultiLevelParkingLotSystem.src.enums.VehicleType;
import MultiLevelParkingLotSystem.src.service.ParkingLot;
import MultiLevelParkingLotSystem.src.strategy.HourlyPricingStrategy;
import MultiLevelParkingLotSystem.src.strategy.NearestSlotStrategy;
import MultiLevelParkingLotSystem.src.strategy.PricingStrategy;
import MultiLevelParkingLotSystem.src.strategy.SlotAssignmentStrategy;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INITIALIZING PARKING LOT ===");

        SlotAssignmentStrategy assignmentStrategy = new NearestSlotStrategy();
        PricingStrategy pricingStrategy = new HourlyPricingStrategy();

        ParkingLot parkingLot = new ParkingLot(assignmentStrategy, pricingStrategy);

        System.out.println("Adding Gates...");
        parkingLot.addGate(new Gate("GATE-1", 1, 0));
        parkingLot.addGate(new Gate("GATE-2", 2, 0));

        System.out.println("Adding Slots...");
        parkingLot.addSlot(new Slot("S-101", SlotType.SMALL, 1, 10));
        parkingLot.addSlot(new Slot("M-101", SlotType.MEDIUM, 1, 20));
        parkingLot.addSlot(new Slot("M-102", SlotType.MEDIUM, 1, 30));

        parkingLot.addSlot(new Slot("S-201", SlotType.SMALL, 2, 10));
        parkingLot.addSlot(new Slot("L-201", SlotType.LARGE, 2, 20));

        System.out.println("\nInitial Status: " + parkingLot.status());
        System.out.println("\n=== STARTING SIMULATION ===");

        long currentTime = System.currentTimeMillis();

        try {
            Vehicle bike = new Vehicle("KA-01-BIKE", VehicleType.TWO_WHEELER);
            Ticket bikeTicket = parkingLot.park(bike, currentTime, SlotType.SMALL, "GATE-1");

            Vehicle car = new Vehicle("KA-02-CAR", VehicleType.CAR);
            Ticket carTicket = parkingLot.park(car, currentTime, SlotType.MEDIUM, "GATE-2");

            Vehicle premiumBike = new Vehicle("KA-03-DUCATI", VehicleType.TWO_WHEELER);
            Ticket premiumBikeTicket = parkingLot.park(premiumBike, currentTime, SlotType.MEDIUM, "GATE-1");

            Vehicle bus = new Vehicle("KA-04-BUS", VehicleType.BUS);
            Ticket busTicket = parkingLot.park(bus, currentTime, SlotType.LARGE, "GATE-1");

            System.out.println("\nStatus after parking: " + parkingLot.status());

            System.out.println("\nAttempting invalid parking...");
            try {
                Vehicle trickyCar = new Vehicle("KA-05-TRICKY", VehicleType.CAR);
                parkingLot.park(trickyCar, currentTime, SlotType.SMALL, "GATE-1");
            } catch (IllegalArgumentException e) {
                System.out.println("Caught Expected Error: " + e.getMessage());
            }

            System.out.println("\n=== PROCESSING EXITS ===");

            long exitTime = currentTime + (long)(2.5 * 60 * 60 * 1000);

            parkingLot.exit(bikeTicket.getTicketId(), exitTime);
            parkingLot.exit(carTicket.getTicketId(), exitTime);

            parkingLot.exit(premiumBikeTicket.getTicketId(), exitTime);

            System.out.println("\nFinal Status: " + parkingLot.status());

        } catch (Exception e) {
            System.err.println("System Error: " + e.getMessage());
        }
    }
}