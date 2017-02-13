package com.siriporn.dogfindertest.Models;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by siriporn on 13/2/2560.
 */
@Setter
@Getter
public class LostAndFound {
    public static final int LOST = 0;
    public static final int FOUND = 1;
    private int id;
    private Dog dog;
    private User user;
    private int type;
    private String note;
}
