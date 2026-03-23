package MultiLevelParkingLotSystem.src.service;

import MultiLevelParkingLotSystem.src.entities.Gate;
import MultiLevelParkingLotSystem.src.entities.Slot;
import MultiLevelParkingLotSystem.src.entities.Ticket;
import MultiLevelParkingLotSystem.src.entities.Vehicle;
import MultiLevelParkingLotSystem.src.enums.SlotType;
import MultiLevelParkingLotSystem.src.strategy.PricingStrategy;
import MultiLevelParkingLotSystem.src.strategy.SlotAssignmentStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;

public class ParkingLot {
    private final Map<String, Gate> gates;
    private final Map<String, Map<SlotType, TreeSet<Slot>>> availableSlotsPerGate;
    private final Map<String, Ticket> activeTickets;

    private final SlotAssignmentStrategy assignmentStrategy;
    private final PricingStrategy pricingStrategy;

    public ParkingLot(SlotAssignmentStrategy assignmentStrategy, PricingStrategy pricingStrategy) {
        this.gates = new HashMap<>();
        this.availableSlotsPerGate = new HashMap<>();
        this.activeTickets = new HashMap<>();
        this.assignmentStrategy = assignmentStrategy;
        this.pricingStrategy = pricingStrategy;
    }

    public void addGate(Gate gate) {
        gates.put(gate.getId(), gate);
        availableSlotsPerGate.put(gate.getId(), new HashMap<>());
        for (SlotType type : SlotType.values()) {
            availableSlotsPerGate.get(gate.getId()).put(type, new TreeSet<>((s1, s2) -> {
                int dist1 = calculateDistance(gate, s1);
                int dist2 = calculateDistance(gate, s2);
                if (dist1 != dist2) return Integer.compare(dist1, dist2);
                return s1.getId().compareTo(s2.getId());
            }));
        }
    }

    private int calculateDistance(Gate gate, Slot slot) {
        return Math.abs(gate.getFloor() - slot.getFloor()) * 100 + Math.abs(gate.getPosition() - slot.getPosition());
    }

    public void addSlot(Slot slot) {
        for (Gate gate : gates.values()) {
            availableSlotsPerGate.get(gate.getId()).get(slot.getType()).add(slot);
        }
    }

    public Ticket park(Vehicle vehicle, long entryTimeMillis, SlotType requestedSlotType, String entryGateID) {
        Gate entryGate = gates.get(entryGateID);
        if (entryGate == null) throw new IllegalArgumentException("Invalid Gate ID");

        Slot allocatedSlot = assignmentStrategy.assignSlot(vehicle, requestedSlotType, entryGate, availableSlotsPerGate);

        for (String gateId : gates.keySet()) {
            availableSlotsPerGate.get(gateId).get(allocatedSlot.getType()).remove(allocatedSlot);
        }

        Ticket ticket = new Ticket(UUID.randomUUID().toString(), vehicle, allocatedSlot, entryTimeMillis);
        activeTickets.put(ticket.getTicketId(), ticket);

        return ticket;
    }

    public double exit(String ticketId, long exitTimeMillis) {
        Ticket ticket = activeTickets.remove(ticketId);
        if (ticket == null) throw new IllegalArgumentException("Invalid Ticket ID");

        Slot slot = ticket.getAllocatedSlot();

        double billAmount = pricingStrategy.calculateBill(ticket, exitTimeMillis);

        for (String gateId : gates.keySet()) {
            availableSlotsPerGate.get(gateId).get(slot.getType()).add(slot);
        }
        return billAmount;
    }

    public Map<SlotType, Integer> status() {
        Map<SlotType, Integer> statusMap = new HashMap<>();
        if (gates.isEmpty()) return statusMap;

        String anyGateId = gates.keySet().iterator().next();

        for (SlotType type : SlotType.values()) {
            statusMap.put(type, availableSlotsPerGate.get(anyGateId).get(type).size());
        }
        return statusMap;
    }
}