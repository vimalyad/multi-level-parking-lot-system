package MultiLevelParkingLotSystem.src.strategy;

import MultiLevelParkingLotSystem.src.entities.Ticket;
import MultiLevelParkingLotSystem.src.enums.SlotType;

import java.util.HashMap;
import java.util.Map;

public class HourlyPricingStrategy implements PricingStrategy {

    private final Map<SlotType, Double> hourlyRates;

    public HourlyPricingStrategy() {
        hourlyRates = new HashMap<>();
        hourlyRates.put(SlotType.SMALL, 20.0);
        hourlyRates.put(SlotType.MEDIUM, 50.0);
        hourlyRates.put(SlotType.LARGE, 100.0);
    }

    @Override
    public double calculateBill(Ticket ticket, long exitTimeMillis) {
        long durationMillis = exitTimeMillis - ticket.getEntryTime();
        double hours = Math.ceil(durationMillis / (1000.0 * 60 * 60));
        if (hours == 0) hours = 1;

        return hours * hourlyRates.get(ticket.getAllocatedSlot().getType());
    }
}