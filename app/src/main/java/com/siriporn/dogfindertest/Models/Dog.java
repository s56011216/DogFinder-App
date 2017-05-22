package com.siriporn.dogfindertest.Models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Moobi on 12-Jan-17.
 */

@Setter
@Getter
public class Dog {
    int id;
    int age;
    double latitude;
    double longitude;
    User user;
    String name;
    String breed;
    String note;
    String[] images;
    Date created_at;
    Date updated_at;

    public Dog(){

    }

    public Dog(int id, int age, double latitude, double longitude, User user, String name,
                   String breed, String note, String[] images, Date created_at, Date updated_at ) {
        this.id = id;
        this.age = age;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
        this.name = name;
        this.breed = breed;
        this.note = note;
        this.images = images;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
