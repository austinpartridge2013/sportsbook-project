package com.thefightpredictor.sportsbook.core.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "authorized_user")
@NamedQueries({
    @NamedQuery(name  = "AuthorizedUser.getMatchingUser",
            query = "SELECT ar FROM AuthorizedUser ar "
                    + "WHERE ar.parkingCode = :parkingCode AND "
                    + "      ar.streetNumber = :streetNumber AND "
                    + "      ar.streetName = :streetName AND "
                    + "      ar.unit = :unit")
})
public class AuthorizedUser {

    @Id
    @Column(name = "authorized_user_id")
    private int id;

    @Column(name = "parking_code")
    private String parkingCode;

    @Column(name = "street_number")
    private int streetNumber;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "unit")
    private String unit;

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getParkingCode() {
        return this.parkingCode;
    }

    public void setParkingCode(final String parkingCode) {
        this.parkingCode = parkingCode;
    }

    public int getStreetNumber() {
        return this.streetNumber;
    }

    public void setStreetNumber(final int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return this.streetName;
    }

    public void setStreetName(final String streetName) {
        this.streetName = streetName;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(final String unit) {
        this.unit = unit;
    }
}
