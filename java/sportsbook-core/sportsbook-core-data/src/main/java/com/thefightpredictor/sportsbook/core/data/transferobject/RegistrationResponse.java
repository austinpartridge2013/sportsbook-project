package com.thefightpredictor.sportsbook.core.data.transferobject;

public class RegistrationResponse {

    private final int status;
    private final String message;

    public RegistrationResponse(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return this.message;
    }
}
