package com.demo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.joda.time.DateTime;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class RestErrorResponse {

    @ApiModelProperty
    private String timestamp;

    @ApiModelProperty(position = 1)
    private int httpStatus;

    @ApiModelProperty(position = 2)
    private String message;

    @ApiModelProperty(position = 3)
    private String error;

    public RestErrorResponse(int httpStatus, String message, String error) {
        this.timestamp = DateTime.now().toString();
        this.httpStatus = httpStatus;
        this.message = message;
        this.error = error;
    }
}
