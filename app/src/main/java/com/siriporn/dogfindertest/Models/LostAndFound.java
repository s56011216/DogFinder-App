package com.siriporn.dogfindertest.Models;

import android.os.Parcelable;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by siriporn on 13/2/2560.
 */
@Parcel
@Setter
@Getter
public class LostAndFound implements Serializable {
    public static final int LOST = 0;
    public static final int FOUND = 1;
    int id;
    Dog dog;
    User user;
    int type;
    String note;

    public LostAndFound() {}

    public LostAndFound(int id, Dog dog, User user, int type, String note) {
        this.id = id;
        this.dog = dog;
        this.user = user;
        this.type = type;
        this.note = note;
    }
}
