package com.ibm.airlock.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Represents the existing Airlock experiments list.")
public class ExperimentList {

    @ApiModelProperty(value = "List of experiments.")
    private Experiment[] experiments;

    public Experiment[] getExperiments() {
        return experiments;
    }

    @JsonCreator
    public ExperimentList() {

    }
}
