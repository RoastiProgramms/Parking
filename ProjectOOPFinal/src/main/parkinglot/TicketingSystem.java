package main.parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
class TicketingSystem {
    private static TicketingSystem ticketingSystem;
    private ParkingLot parkingLot;
    private Map<Integer, Ticket> tickets;


    TicketingSystem(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        tickets = new HashMap<Integer, Ticket>();
    }


    static TicketingSystem createInstance(int numberOfSlots) {
        if(numberOfSlots < 1) {
            throw new Exceptions("Number of slots cannot be less than 1");
        }
        if (ticketingSystem == null) {
            ParkingLot parkingLot = ParkingLot.getInstance(numberOfSlots);
            ticketingSystem = new TicketingSystem(parkingLot);
        }
        return ticketingSystem;
    }


    static TicketingSystem getInstance() {
        if(ticketingSystem == null) {
            throw new IllegalStateException("Parking Lot is not initialized");
        }
        return ticketingSystem;
    }

    int issueParkingTicket(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        int assignedSlotNumber = parkingLot.fillAvailableSlot();
        Ticket ticket = new Ticket(assignedSlotNumber, vehicle);
        tickets.put(assignedSlotNumber, ticket);
        return assignedSlotNumber;
    }

    void exitVehicle(int slotNumber) {
        if (tickets.containsKey(slotNumber)) {
            parkingLot.emptySlot(slotNumber);
            tickets.remove(slotNumber);
            return;
        } else {
            throw new Exceptions("No vehicle found at given slot. Incorrect input");
        }
    }

    List<String> getRegistrationNumbersFromColor(String color) {
        if (color == null) {
            throw new IllegalArgumentException("color cannot be null");
        }
        List<String> registrationNumbers = new ArrayList<String>();
        for (Ticket ticket : tickets.values()) {
            if (color.equals(ticket.vehicle.getColor())) {
                registrationNumbers.add(ticket.vehicle.getRegistrationNumber());
            }
        }
        return registrationNumbers;
    }
    List<String> getRegistrationNumbersFromAbonamentInfo(String abonamentInfo){
        List<String> registrationNumbers=new ArrayList<String>();
        for(Ticket ticket: tickets.values())
        {
            if(abonamentInfo.equals(ticket.vehicle.getAbonamentInfo()))
            {
                registrationNumbers.add(ticket.vehicle.getRegistrationNumber());
            }
        }
        return registrationNumbers;
    }

    int getSlotNumberFromRegistrationNumber(String registrationNumber) {
        if (registrationNumber == null) {
            throw new IllegalArgumentException("registrationNumber cannot be null");
        }
        for (Ticket ticket : tickets.values()) {
            if (registrationNumber.equals(ticket.vehicle.getRegistrationNumber())) {
                return ticket.slotNumber;
            }
        }

        throw new Exceptions("Not found");
    }

    List<Integer> getSlotNumbersFromColor(String color) {
        if (color == null) {
            throw new IllegalArgumentException("color cannot be null");
        }
        List<Integer> registrationNumbers = new ArrayList<Integer>();
        for (Ticket ticket : tickets.values()) {
            if (color.equals(ticket.vehicle.getColor())) {
                registrationNumbers.add(ticket.slotNumber);
            }
        }
        return registrationNumbers;
    }

    List<StatusResponse> getStatus() {
        List<StatusResponse> statusResponseList = new ArrayList<StatusResponse>();
        for (Ticket ticket : tickets.values()) {
            statusResponseList.add(new StatusResponse(ticket.slotNumber, ticket.vehicle.getRegistrationNumber(),
                    ticket.vehicle.getColor(),ticket.vehicle.getAbonamentInfo()));
        }
        return statusResponseList;
    }

    private class Ticket {
        int slotNumber;
        Vehicle vehicle;

        Ticket(int slotNumber, Vehicle vehicle) {
            this.slotNumber = slotNumber;
            this.vehicle = vehicle;
        }
    }
}