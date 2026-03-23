package MultiLevelParkingLotSystem.src.entities;

public class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final Slot allocatedSlot;
    private final long entryTime;

    public Ticket(String ticketId, Vehicle vehicle, Slot allocatedSlot, long entryTime) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.allocatedSlot = allocatedSlot;
        this.entryTime = entryTime;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Slot getAllocatedSlot() {
        return allocatedSlot;
    }

    public long getEntryTime() {
        return entryTime;
    }
}
