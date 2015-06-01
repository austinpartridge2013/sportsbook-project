package com.thefightpredictor.sportsbook.core.service.interfaces;

import com.thefightpredictor.sportsbook.core.data.transferobject.RegistrationAttempt;
import com.thefightpredictor.sportsbook.core.data.transferobject.RegistrationResponse;

public interface VehicleRegistrationService {
    RegistrationResponse registerVehicle(RegistrationAttempt registrationAttempt);
}
