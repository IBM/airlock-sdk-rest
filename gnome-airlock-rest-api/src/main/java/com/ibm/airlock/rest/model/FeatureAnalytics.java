package com.ibm.airlock.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "An object containing all analytics related data for a specific feature.")
public class FeatureAnalytics {

    @ApiModelProperty(value = "Indicating if the feature should be reported to analytics.")
    private boolean sendOn;

    @ApiModelProperty(value = "The feature name.")
    private String featureName;

    @ApiModelProperty(value = "The configuration attributes (key/value pairs) that were set to be reported to analytics.")
    private Attribute[] attributes;

    @ApiModelProperty(value = "An array containing the configuration rules that were both applied and marked to be reported to analytics.")
    private String[] appliedConfigurationRules;

    @ApiModelProperty(value = "An array containing the ordering rules that were both applied and marked to be reported to analytics.")
    private String[] appliedOrderingRules;

    @ApiModelProperty(value = "An array containing the names of the children according their order based on the last calculation.")
    private String[] orderedChildren;

    @JsonCreator
    public FeatureAnalytics(@JsonProperty("featureName")String featureName) {
        this.featureName = featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public void setAttributes(Attribute[] attributes) {
        this.attributes = attributes;
    }

    public void setAppliedConfigurationRules(String[] appliedConfigurationRules) {
        this.appliedConfigurationRules = appliedConfigurationRules;
    }

    public void setAppliedOrderingRules(String[] appliedOrderingRules) {
        this.appliedOrderingRules = appliedOrderingRules;
    }

    public void setOrderedChildren(String[] orderedChildren) {
        this.orderedChildren = orderedChildren;
    }

    public void setSendOn(boolean sendOn) {
        this.sendOn = sendOn;
    }

    public boolean isSendOn() {
        return sendOn;
    }

    public String getFeatureName() {
        return featureName;
    }

    public Attribute[] getAttributes() {
        return attributes;
    }

    public String[] getAppliedConfigurationRules() {
        return appliedConfigurationRules;
    }

    public String[] getAppliedOrderingRules() {
        return appliedOrderingRules;
    }

    public String[] getOrderedChildren() {
        return orderedChildren;
    }
}
