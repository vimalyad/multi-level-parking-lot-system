package MultiLevelParkingLotSystem.src.entities;

import MultiLevelParkingLotSystem.src.enums.SlotType;

public class Slot {
    private final String id;
    private final SlotType type;
    private final int floor;
    private final int position;

    public Slot(String id, SlotType type, int floor, int position) {
        this.id = id;
        this.type = type;
        this.floor = floor;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public SlotType getType() {
        return type;
    }

    public int getFloor() {
        return floor;
    }

    public int getPosition() {
        return position;
    }
}