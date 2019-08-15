package com.ibm.airlock.rest.model;

import com.ibm.airlock.common.data.Feature;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "Represents Airlock feature trace and additional information for debugging purpose")
public class FeatureDebugInfo {

    @ApiModelProperty(name = "appliedConfigurationRules", value = "The names list of applied configuration rules")
    private List<String> appliedConfigurationRules;


    @ApiModelProperty(name = "appliedOrderingRules", value = "The names list of applied ordering rules")
    private List<String> appliedOrderingRules;

    @ApiModelProperty(name = "source", value = "The source feature value is loaded from")
    private Feature.Source source;

    @ApiModelProperty(name = "trace",  value = "Debug information about feature's rules execution")
    private String trace = "";

    @ApiModelProperty(name = "percentage",  value = "The percentage of the user the feature is available")
    private double percentage ;

    @ApiModelProperty(name = "orderingWeight",  value = "The weight value defines the feature ordering place relatively to siblings")
    private double orderingWeight;

    @ApiModelProperty(name = "branchStatus",  value = "The feature branch status indicates whether a feature has been overridden in the another branch")
    private  Feature.BranchStatus branchStatus;

    public double getPercentage() {
        return percentage;
    }

    public double getOrderingWeight() {
        return orderingWeight;
    }

    public void setOrderingWeight(double orderingWeight) {
        this.orderingWeight = orderingWeight;
    }

    public Feature.BranchStatus getBranchStatus() {
        return branchStatus;
    }

    public void setBranchStatus(Feature.BranchStatus branchStatus) {
        this.branchStatus = branchStatus;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public List<String> getAppliedConfigurationRules() {
        return appliedConfigurationRules;
    }

    public void setAppliedConfigurationRules(List<String> appliedConfigurationRules) {
        this.appliedConfigurationRules = appliedConfigurationRules;
    }

    public List<String> getAppliedOrderingRules() {
        return appliedOrderingRules;
    }

    public void setAppliedOrderingRules(List<String> appliedOrderingRules) {
        this.appliedOrderingRules = appliedOrderingRules;
    }

    public Feature.Source getSource() {
        return source;
    }

    public void setSource(Feature.Source source) {
        this.source = source;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }
}
