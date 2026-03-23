package MultiLevelParkingLotSystem.src.entities;

public class Gate {
    private final String id;
    private final int floor;
    private final int position;

    public Gate(String id, int floor, int position) {
        this.id = id;
        this.floor = floor;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public int getFloor() {
        return floor;
    }

    public int getPosition() {
        return position;
    }
}
