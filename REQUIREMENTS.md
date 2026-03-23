# Multilevel Parking Lot Design Problem Statement

Design a multilevel parking lot system with the following requirements:

## Functional Requirements

1. The parking lot should support three types of parking slots:
    * Small: for 2-wheelers
    * Medium: for cars
    * Large: for buses

2. The system should maintain different hourly parking charges for each slot type.

3. When a vehicle enters the parking lot, the system should:
    * assign a parking slot,
    * generate a parking ticket,
    * and store the following information in the ticket:
        * vehicle details
        * allocated slot number
        * allocated slot type
        * entry time

4. When a vehicle exits the parking lot, the system should:
    * calculate the parking duration using the entry and exit times,
    * generate the bill,
    * and return the total amount to be paid.

5. The parking lot has multiple entry gates. The system should always assign the nearest available compatible slot based
   on the vehicle's entry gate.

6. A smaller vehicle should be allowed to park in a larger slot if needed:
    * a 2-wheeler can park in a small, medium, or large slot
    * a car can park in a medium or large slot
    * a bus can park only in a large slot

7. Billing should be based on the allocated slot type, not the vehicle type. For example, if a bike is parked in a
   medium slot, the charge should be calculated using the medium slot rate.

## APIs to be Supported

The system should provide the following main functions:

`park(vehicleDetails, entryTime, requestedSlotType, entryGateID)`
Parks a vehicle and returns the generated parking ticket.

`status()`
Returns the current availability of parking slots by slot type.

`exit(parkingTicket, exitTime)`
Processes vehicle exit and returns the generated bill amount.

## Expected Deliverables

The solution should include:

* Class Diagram
* Code
* Explanation of the design and approach