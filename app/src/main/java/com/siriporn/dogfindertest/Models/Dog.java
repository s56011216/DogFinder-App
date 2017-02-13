package com.siriporn.dogfindertest.Models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

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
    String name;
    String bleed;
    String note;
    String images[];
    int age;

    public Dog() {}

    public Dog(int id, String name, String bleed, String note, String[] images, int age) {
        this.id = id;
        this.name = name;
        this.bleed = bleed;
        this.note = note;
        this.images = images;
        this.age = age;
    }
}
