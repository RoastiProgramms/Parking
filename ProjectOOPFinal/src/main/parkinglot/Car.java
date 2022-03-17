package main.parkinglot;

import java.util.Locale;

public class Car implements Vehicle {

    private String registrationNumber;
    private String color;
    private String abonamentInfo;


    public Car(String registrationNumber, String color, String abonamentInfo) {
        if(registrationNumber == null || color == null) {
            throw new IllegalArgumentException("Both registrationNumber & Color should not be null");
        }
        this.registrationNumber = registrationNumber;
        this.color = color;
        this.abonamentInfo=abonamentInfo.toLowerCase(Locale.ROOT);
        if(this.abonamentInfo==null)
        {
            this.abonamentInfo="no";
        }
        if(!this.abonamentInfo.equals("yes")&&!this.abonamentInfo.equals("no"))
        {
            throw new IllegalArgumentException("Invalid abonament information.");
        }

    }

    public String getColor() {
        return this.color;
    }

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }

    public String getAbonamentInfo() {
        return this.abonamentInfo;
    }
}
