package com.ibm.airlock.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Represents Airlock analytics data for a specific product according to the last calculation.")
public class AnalyticsData {

    @ApiModelProperty(value = "The current experiment name.")
    private String experimentName;

    @ApiModelProperty(value = "The current branch name.")
    private String branchName;

    @ApiModelProperty(value = "The current variant name.")
    private String variantName;

    @ApiModelProperty(value = "The date in which the client joined the current variant (milliseconds from epoch).")
    private Long dateJoinedVariant;

    @ApiModelProperty(value = "An array of feature analytics data.")
    private FeatureAnalytics[] features;

    @ApiModelProperty(value = "An array of context attributes that were marked to be reported to analytics.")
    private Attribute[] context;


    @JsonCreator
    public AnalyticsData(@JsonProperty("experimentName")String experimentName,@JsonProperty("branchName") String branchName,@JsonProperty("variantName") String variantName,@JsonProperty("dateJoinedVariant") Long dateJoinedVariant) {
        this.experimentName = experimentName;
        this.branchName = branchName;
        this.variantName = variantName;
        this.dateJoinedVariant = dateJoinedVariant;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public Long getDateJoinedVariant() {
        return dateJoinedVariant;
    }

    public void setDateJoinedVariant(Long dateJoinedVariant) {
        this.dateJoinedVariant = dateJoinedVariant;
    }

    public FeatureAnalytics[] getFeatures() {
        return features;
    }

    public Attribute[] getContext() {
        return context;
    }


    public void setFeatures(FeatureAnalytics[] features) {
        this.features = features;
    }

    public void setContext(Attribute[] context) {
        this.context = context;
    }


}
