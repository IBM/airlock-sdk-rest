package com.ibm.airlock.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Represents an Airlock attribute. Can be either a configuration attribute or a context attribute.")
public class Attribute {

    @ApiModelProperty(required = true, value = "The key of the attribute.")
    private String key;

    @ApiModelProperty(required = true, value = "The value of the attribute.")
    private Object value;

    @JsonCreator
    public Attribute(@JsonProperty("key")String key,@JsonProperty("value") Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
