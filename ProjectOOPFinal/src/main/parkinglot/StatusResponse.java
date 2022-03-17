package main.parkinglot;

public class StatusResponse {
    private int slotNumber;
    private String registrationNumber;
    private String color;
    private String abonamentInfo;

    public StatusResponse(int slotNumber, String registrationNumber, String color, String abonamentInfo) {
        this.slotNumber = slotNumber;
        this.registrationNumber = registrationNumber;
        this.color = color;
        this.abonamentInfo=abonamentInfo;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getColor() {
        return color;
    }

    public String getAbonamentInfo() {
        return abonamentInfo;
    }

    @Override
    public String toString() {
        return slotNumber + "         " + registrationNumber + "         " + color+"  "+abonamentInfo;
    }

}