package MultiLevelParkingLotSystem.src.strategy;

import MultiLevelParkingLotSystem.src.entities.Ticket;

public interface PricingStrategy {
    double calculateBill(Ticket ticket, long exitTimeMillis);
}