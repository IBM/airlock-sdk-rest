package com.ibm.airlock.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ibm.airlock.rest.util.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApiModel(description = "Represents an Airlock feature.")
public class Feature {

    @ApiModelProperty(value = "Feature name.")
    private String name;

    @ApiModelProperty(value = "Indicates whether the feature is ON/OFF based on the last calculation." )
    private boolean on;

    @ApiModelProperty(value = "The configuration object for this feature.")
    private Map<String, Object> configuration;

    @ApiModelProperty(value = "Features children.")
    private List<Feature> children;

    @ApiModelProperty(value = "Feature parent name.")
    private String parentName;

    @ApiModelProperty(value = "Additional information for debugging purposes.")
    private FeatureDebugInfo debugInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public Map<String, Object> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, Object> configuration) {
        this.configuration = configuration;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public FeatureDebugInfo getDebugInfo() {
        return debugInfo;
    }

    public void setFeatureDebugInfo(FeatureDebugInfo featureDebugInfo) {
        this.debugInfo = featureDebugInfo;
    }

    @JsonCreator
    public Feature() {

    }

    public Feature(com.ibm.airlock.common.data.Feature feature) {
        this.name = feature.getName();

        if (feature.getConfiguration() != null) {
            this.configuration = JSON.jsonToMap(feature.getConfiguration());
        }

        this.on = feature.isOn();

        if (feature.getParent() != null) {
            parentName = feature.getParent().getName();
        }

        this.debugInfo = new FeatureDebugInfo();

        this.debugInfo.setAppliedConfigurationRules(new ArrayList<>());

        if (feature.getConfigurationStatuses() != null) {
            for (int i = 0; i < feature.getConfigurationStatuses().length(); i++) {
                JSONObject status = feature.getConfigurationStatuses().getJSONObject(i);
                if (status.optString("name") != null) {
                    this.debugInfo.getAppliedConfigurationRules().add(status.optString("name"));
                }
            }
        }

        this.debugInfo.setAppliedOrderingRules(new ArrayList<>());

        if (feature.getAnalyticsAppliedRules() != null) {
            this.debugInfo.setAppliedOrderingRules(feature.getAnalyticsAppliedRules());
        }

        List<com.ibm.airlock.common.data.Feature> childFeatures = feature.getChildren();
        for (com.ibm.airlock.common.data.Feature child: childFeatures){
            if (this.children == null){
                this.children = new ArrayList<>();
            }
            //Be aware - this is actually a recursive call...
            this.children.add(new Feature(child));
        }

        this.debugInfo.setSource(feature.getSource());
        this.debugInfo.setTrace(feature.getTraceInfo());
        this.debugInfo.setPercentage(feature.getPercentage());
        this.debugInfo.setBranchStatus(feature.getBranchStatus());
        this.debugInfo.setOrderingWeight(feature.getWeight());
    }

    public List<Feature> getChildren() {
        return children;
    }

    public void setChildren(List<Feature> children) {
        this.children = children;
    }
}
