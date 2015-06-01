package com.thefightpredictor.sportsbook.core.data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle_registration")
public class VehicleRegistration {

    @Id
    @Column(name = "vehicle_registration_id")
    private int id;

    @Column(name = "authorized_date")
    private LocalDateTime authorizedDate;

    @ManyToOne
    @JoinColumn(name="authorized_user_id")
    private AuthorizedUser authorizedUser;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "car_make")
    private String carMake;

    @Column(name = "car_model")
    private String carModel;

    @Column(name = "created")
    private LocalDateTime created;

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public LocalDateTime getAuthorizedDate() {
        return this.authorizedDate;
    }

    public void setAuthorizedDate(final LocalDateTime authorizedDate) {
        this.authorizedDate = authorizedDate;
    }

    public AuthorizedUser getAuthorizedUser() {
        return this.authorizedUser;
    }

    public void setAuthorizedUser(final AuthorizedUser authorizedUser) {
        this.authorizedUser = authorizedUser;
    }

    public String getLicensePlate() {
        return this.licensePlate;
    }

    public void setLicensePlate(final String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getCarMake() {
        return this.carMake;
    }

    public void setCarMake(final String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return this.carModel;
    }

    public void setCarModel(final String carModel) {
        this.carModel = carModel;
    }

    public LocalDateTime getCreated() {
        return this.created;
    }

    public void setCreated(final LocalDateTime created) {
        this.created = created;
    }
}
