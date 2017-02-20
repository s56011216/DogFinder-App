package com.siriporn.dogfindertest.Models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Moobi on 12-Jan-17.
 */
@Parcel
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
    String images[];
    Date created_at;
    Date updated_at;
}
