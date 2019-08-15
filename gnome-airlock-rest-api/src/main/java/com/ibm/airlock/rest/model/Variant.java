package com.ibm.airlock.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Represents an Airlock variant.")
public class Variant {

    @ApiModelProperty(value = "Variant name.")
    private String name;

    @ApiModelProperty(value = "Branch name.")
    private String branchName;

    @ApiModelProperty(value = "Experiment name.")
    private String experimentName;

    @ApiModelProperty(value = "Indicates whether the variant is ON/OFF based on the last calculation." )
    @JsonProperty("isON")
    private boolean isON;

    @ApiModelProperty(value = "Additional information for debugging purposes.")
    private String traceInfo;

    @ApiModelProperty(value = "The rollout percentage set for this variant.")
    private Double rolloutPercentage;

    public String getName() {
        return name;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getExperimentName() {
        return experimentName;
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

    @JsonCreator
    public Variant() {

    }
}
