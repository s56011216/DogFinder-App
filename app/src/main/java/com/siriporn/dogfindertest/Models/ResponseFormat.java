package com.siriporn.dogfindertest.Models;

import java.util.Map;

import lombok.Getter;

/**
 * Created by siriporn on 30/1/2560.
 */
@Getter
public class ResponseFormat {
    private boolean success;
    private Map<String, Object> payload;
    private ErrorBody error;

    @Getter
    private class ErrorBody {
        private String code;
        private String message;
    }
}
