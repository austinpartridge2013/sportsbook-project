package com.thefightpredictor.sportsbook.core.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "login_account")
public class UserLogin {

    @Id
    @Column(name = "user_id")
    private int userId;

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }
}
