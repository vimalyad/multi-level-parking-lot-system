package MultiLevelParkingLotSystem.src.strategy;

import MultiLevelParkingLotSystem.src.entities.Gate;
import MultiLevelParkingLotSystem.src.entities.Slot;
import MultiLevelParkingLotSystem.src.entities.Vehicle;
import MultiLevelParkingLotSystem.src.enums.SlotType;
import MultiLevelParkingLotSystem.src.enums.VehicleType;

import java.util.*;

public class NearestSlotStrategy implements SlotAssignmentStrategy {

    private final Map<VehicleType, List<SlotType>> compatibilityMap;

    public NearestSlotStrategy() {
        compatibilityMap = new HashMap<>();
        compatibilityMap.put(VehicleType.TWO_WHEELER, Arrays.asList(SlotType.SMALL, SlotType.MEDIUM, SlotType.LARGE));
        compatibilityMap.put(VehicleType.CAR, Arrays.asList(SlotType.MEDIUM, SlotType.LARGE));
        compatibilityMap.put(VehicleType.BUS, Collections.singletonList(SlotType.LARGE));
    }

    @Override
    public Slot assignSlot(Vehicle vehicle, SlotType requestedType, Gate entryGate,
                           Map<String, Map<SlotType, TreeSet<Slot>>> availableSlotsPerGate) {

        List<SlotType> allowedSlots = compatibilityMap.get(vehicle.getType());
        if (!allowedSlots.contains(requestedType)) {
            throw new IllegalArgumentException(vehicle.getType() + " cannot park in " + requestedType + " slot.");
        }

        int startIndex = allowedSlots.indexOf(requestedType);
        for (int i = startIndex; i < allowedSlots.size(); i++) {
            SlotType targetType = allowedSlots.get(i);
            TreeSet<Slot> availableSlots = availableSlotsPerGate.get(entryGate.getId()).get(targetType);

            if (!availableSlots.isEmpty()) {
                return availableSlots.first();
            }
        }
        throw new RuntimeException("Parking Lot is Full for this vehicle type.");
    }
}
