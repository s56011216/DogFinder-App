package com.siriporn.dogfindertest.Models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Moobi on 10-Jan-17.
 */
@Parcel
@Getter
@Setter
public class User {
    int id;
    String fb_id;
    String fb_name;
    String fb_token;
    Date fb_token_exp;
    String email;
    Date birth_date;
    String fb_profile_image;
    String telephone;

    public User() {}

    public User(int id, String fb_id, String fb_name, String fb_token, Date fb_token_exp, String email, Date birth_date, String fb_profile_image) {
        this.id = id;
        this.fb_id = fb_id;
        this.fb_name = fb_name;
        this.fb_token = fb_token;
        this.fb_token_exp = fb_token_exp;
        this.email = email;
        this.birth_date = birth_date;
        this.fb_profile_image = fb_profile_image;
    }
}
