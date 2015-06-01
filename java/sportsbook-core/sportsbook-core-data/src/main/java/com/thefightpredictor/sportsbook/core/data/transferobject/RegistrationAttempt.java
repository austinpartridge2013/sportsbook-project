package com.thefightpredictor.sportsbook.core.data.transferobject;

public class RegistrationAttempt {
    String parkingCode;
    int streetNumber;
    String streetName;
    String unit;
    int days;
    String licensePlate;
    String carMake;
    String carModel;

    public String getParkingCode() {
        return parkingCode;
    }
    public void setParkingCode(final String parkingCode) {
        this.parkingCode = parkingCode;
    }
    public int getStreetNumber() {
        return streetNumber;
    }
    public void setStreetNumber(final int streetNumber) {
        this.streetNumber = streetNumber;
    }
    public String getStreetName() {
        return streetName;
    }
    public void setStreetName(final String streetName) {
        this.streetName = streetName;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(final String unit) {
        this.unit = unit;
    }
    public int getDays() {
        return days;
    }
    public void setDays(final int days) {
        this.days = days;
    }
    public String getLicensePlate() {
        return licensePlate;
    }
    public void setLicensePlate(final String licensePlate) {
        this.licensePlate = licensePlate;
    }
    public String getCarMake() {
        return carMake;
    }
    public void setCarMake(final String carMake) {
        this.carMake = carMake;
    }
    public String getCarModel() {
        return carModel;
    }
    public void setCarModel(final String carModel) {
        this.carModel = carModel;
    }
}
