package main.parkinglot;

import java.util.HashMap;
import java.util.Map;
class ParkingLot {
    private static ParkingLot parkingLot;
    private Map<Integer, Slot> slots;


    protected ParkingLot(int numberOfSlots) {
        slots = new HashMap<Integer, Slot>();
        for (int i = 1; i <= numberOfSlots; i++) {
            slots.put(i, new Slot(i));
        }
    }


    static ParkingLot getInstance(int numberOfSlots) {
        if (parkingLot == null) {
            parkingLot = new ParkingLot(numberOfSlots);
        }
        return parkingLot;
    }


    int fillAvailableSlot() {
        int nextAvailableSlotNumber = -1;
        for (int i = 1; i <= slots.size(); i++) {
            Slot s = slots.get(i);
            if (s.status) {
                nextAvailableSlotNumber = s.slotNumber;
                s.status = false;
                break;
            }
        }
        if (nextAvailableSlotNumber != -1) {
            return nextAvailableSlotNumber;
        } else {
            throw new Exceptions("Sorry, parking lot is full");
        }
    }


    void emptySlot(int slotNumber) {
        if (slots.containsKey(slotNumber)) {
            if (slots.get(slotNumber).status) {
                throw new IllegalStateException("The slot is already empty");
            } else {
                slots.get(slotNumber).status = true;
            }
        } else {
            throw new IllegalStateException("The slot number is invalid");
        }
    }

    private class Slot {
        // unique slot identifier
        private int slotNumber;
        // boolean status to maintain isAvailable => true=available, false=not available
        private boolean status;

        Slot(int slotNumber) {
            this.slotNumber = slotNumber;
            this.status = true;
        }
    }
}
