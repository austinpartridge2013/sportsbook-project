package com.thefightpredictor.sportsbook.core.service;

import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import com.thefightpredictor.sportsbook.core.data.AuthorizedUser;
import com.thefightpredictor.sportsbook.core.data.VehicleRegistration;
import com.thefightpredictor.sportsbook.core.data.transferobject.RegistrationAttempt;
import com.thefightpredictor.sportsbook.core.data.transferobject.RegistrationResponse;
import com.thefightpredictor.sportsbook.core.service.interfaces.VehicleRegistrationService;

@Stateless
public class VehicleRegistrationServiceImpl implements VehicleRegistrationService
{
    private static final int HOURS_AFTER_MIDNIGHT_CHECKIN_ALLOWED = 5;

    @PersistenceContext
    private EntityManager em;

    public RegistrationResponse registerVehicle(final RegistrationAttempt registrationAttempt)
    {
        final AuthorizedUser authorizedUser = getAuthorizedUserForCheckinAttempt(registrationAttempt);
        if (authorizedUser == null) {
            return new RegistrationResponse(HttpURLConnection.HTTP_FORBIDDEN, "No matching user found");
        }

        final LocalDateTime currentDateTime = LocalDateTime.now();

        final LocalDateTime tonightsParkingDateTime = getCurrentRegistrationDate(currentDateTime);

        for (int i = 0; i < registrationAttempt.getDays(); i++) {
            final VehicleRegistration vehicleRegistration = new VehicleRegistration();
            vehicleRegistration.setAuthorizedDate(tonightsParkingDateTime.plusDays(i));
            vehicleRegistration.setAuthorizedUser(authorizedUser);
            vehicleRegistration.setLicensePlate(registrationAttempt.getLicensePlate());
            vehicleRegistration.setCarMake(registrationAttempt.getCarMake());
            vehicleRegistration.setCarModel(registrationAttempt.getCarModel());
            vehicleRegistration.setCreated(currentDateTime);
            em.persist(vehicleRegistration);
        }

        return new RegistrationResponse(HttpURLConnection.HTTP_OK, "Successfully registered vehicle");
    }

    private AuthorizedUser getAuthorizedUserForCheckinAttempt(final RegistrationAttempt registrationAttempt) {
        try {
            return em.createNamedQuery("AuthorizedUser.getMatchingUser", AuthorizedUser.class)
                    .setParameter("parkingCode", registrationAttempt.getParkingCode())
                    .setParameter("streetNumber", registrationAttempt.getStreetNumber())
                    .setParameter("unit", registrationAttempt.getUnit())
                    .getSingleResult();
        } catch (final NoResultException e) {
            return null;
        } catch (final NonUniqueResultException e) {
            throw new AssertionError("Multiple results match criteria");
        }
    }

    private LocalDateTime getCurrentRegistrationDate(final LocalDateTime currentDateTime) {
        final LocalDateTime tonightsParkingDateTime;
        if (currentDateTime.getHour() < HOURS_AFTER_MIDNIGHT_CHECKIN_ALLOWED) {
            tonightsParkingDateTime = currentDateTime.truncatedTo(ChronoUnit.DAYS);
        } else {
            tonightsParkingDateTime = currentDateTime.plusDays(1L).truncatedTo(ChronoUnit.DAYS);
        }
        return tonightsParkingDateTime;
    }
}
