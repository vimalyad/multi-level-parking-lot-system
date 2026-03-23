package MultiLevelParkingLotSystem.src.strategy;

import MultiLevelParkingLotSystem.src.entities.Gate;
import MultiLevelParkingLotSystem.src.entities.Slot;
import MultiLevelParkingLotSystem.src.entities.Vehicle;
import MultiLevelParkingLotSystem.src.enums.SlotType;

import java.util.Map;
import java.util.TreeSet;

public interface SlotAssignmentStrategy {
    Slot assignSlot(Vehicle vehicle, SlotType requestedType, Gate entryGate,
                    Map<String, Map<SlotType, TreeSet<Slot>>> availableSlotsPerGate);
}