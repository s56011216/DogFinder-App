package com.siriporn.dogfindertest.Models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by siriporn on 15/2/2560.
 */
@Getter
@Setter
public class Coordinate {
    private double latitude;
    private double longitude;
    private String name;
    private Date created_at;
}
