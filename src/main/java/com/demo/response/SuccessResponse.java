package com.demo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class SuccessResponse {

    @ApiModelProperty
    private String response;

    public SuccessResponse() {
    }

    public SuccessResponse(String message) {
        this.response = message;
    }
}
