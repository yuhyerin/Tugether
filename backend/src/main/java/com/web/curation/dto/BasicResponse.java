package com.web.curation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

@ToString
public class BasicResponse {
    @ApiModelProperty(value = "status", position = 1)
    public boolean status;
    @ApiModelProperty(value = "data", position = 2)
    public String data;
    @ApiModelProperty(value = "object", position = 3)
    public Object object;
}
