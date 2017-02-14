package com.siriporn.dogfindertest.Models;

import android.os.Parcelable;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by siriporn on 13/2/2560.
 */
@Setter
@Getter
public class LostAndFound implements Serializable {
    public static final int LOST = 0;
    public static final int FOUND = 1;
    int id;
    Dog dog;
    int type;
    String note;
    Date created_at;
}
