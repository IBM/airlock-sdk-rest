package com.ibm.airlock.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Represents an Airlock feature.")
public class Experiment {

    @ApiModelProperty(value = "The Experiment name.")
    private String name;

    @ApiModelProperty(value = "Indicates whether the experiment is ON/OFF based on the last calculation." )
    @JsonProperty("isON")
    private boolean isON;

    @ApiModelProperty(value = "Additional information for debugging purposes.")
    private String traceInfo;

    @ApiModelProperty(value = "The rollout percentage set for this experiment.")
    private Double rolloutPercentage;

    @ApiModelProperty(value = "The application version this experiment is enabled.")
    private String appVersions;

    @ApiModelProperty(value = "The variants related to this experiment.")
    private Variant[] variants;

    public String getName() {
        return name;
    }

    public boolean isON() {
        return isON;
    }

    public String getTraceInfo() {
        return traceInfo;
    }

    public Double getRolloutPercentage() {
        return rolloutPercentage;
    }

    public String getAppVersions() {
        return appVersions;
    }

    public Variant[] getVariants() {
        return variants;
    }

    @JsonCreator
    public Experiment() {

    }
}
