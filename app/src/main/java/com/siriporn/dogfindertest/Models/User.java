package com.siriporn.dogfindertest.Models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Moobi on 10-Jan-17.
 */
@Getter
@Setter
public class User {
    private int id;
    private String fb_id;
    private String fb_name;
    private String fb_token;
    private Date fb_token_exp;
    private String email;
    private Date birth_date;
}
