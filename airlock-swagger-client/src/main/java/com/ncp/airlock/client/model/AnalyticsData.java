/*
 * 
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.ncp.airlock.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.ncp.airlock.client.model.Attribute;
import com.ncp.airlock.client.model.FeatureAnalytics;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents Airlock analytics data for a specific product according to the last calculation.
 */
@ApiModel(description = "Represents Airlock analytics data for a specific product according to the last calculation.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2018-11-27T09:43:16.328Z[Etc/UTC]")
public class AnalyticsData {
  public static final String SERIALIZED_NAME_EXPERIMENT_NAME = "experimentName";
  @SerializedName(SERIALIZED_NAME_EXPERIMENT_NAME)
  private String experimentName = null;

  public static final String SERIALIZED_NAME_BRANCH_NAME = "branchName";
  @SerializedName(SERIALIZED_NAME_BRANCH_NAME)
  private String branchName = null;

  public static final String SERIALIZED_NAME_VARIANT_NAME = "variantName";
  @SerializedName(SERIALIZED_NAME_VARIANT_NAME)
  private String variantName = null;

  public static final String SERIALIZED_NAME_DATE_JOINED_VARIANT = "dateJoinedVariant";
  @SerializedName(SERIALIZED_NAME_DATE_JOINED_VARIANT)
  private Long dateJoinedVariant = null;

  public static final String SERIALIZED_NAME_FEATURES = "features";
  @SerializedName(SERIALIZED_NAME_FEATURES)
  private List<FeatureAnalytics> features = null;

  public static final String SERIALIZED_NAME_CONTEXT = "context";
  @SerializedName(SERIALIZED_NAME_CONTEXT)
  private List<Attribute> context = null;

  public AnalyticsData experimentName(String experimentName) {
    this.experimentName = experimentName;
    return this;
  }

   /**
   * The current experiment name.
   * @return experimentName
  **/
  @ApiModelProperty(value = "The current experiment name.")
  public String getExperimentName() {
    return experimentName;
  }

  public void setExperimentName(String experimentName) {
    this.experimentName = experimentName;
  }

  public AnalyticsData branchName(String branchName) {
    this.branchName = branchName;
    return this;
  }

   /**
   * The current branch name.
   * @return branchName
  **/
  @ApiModelProperty(value = "The current branch name.")
  public String getBranchName() {
    return branchName;
  }

  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }

  public AnalyticsData variantName(String variantName) {
    this.variantName = variantName;
    return this;
  }

   /**
   * The current variant name.
   * @return variantName
  **/
  @ApiModelProperty(value = "The current variant name.")
  public String getVariantName() {
    return variantName;
  }

  public void setVariantName(String variantName) {
    this.variantName = variantName;
  }

  public AnalyticsData dateJoinedVariant(Long dateJoinedVariant) {
    this.dateJoinedVariant = dateJoinedVariant;
    return this;
  }

   /**
   * The date in which the client joined the current variant (milliseconds from epoch).
   * @return dateJoinedVariant
  **/
  @ApiModelProperty(value = "The date in which the client joined the current variant (milliseconds from epoch).")
  public Long getDateJoinedVariant() {
    return dateJoinedVariant;
  }

  public void setDateJoinedVariant(Long dateJoinedVariant) {
    this.dateJoinedVariant = dateJoinedVariant;
  }

  public AnalyticsData features(List<FeatureAnalytics> features) {
    this.features = features;
    return this;
  }

  public AnalyticsData addFeaturesItem(FeatureAnalytics featuresItem) {
    if (this.features == null) {
      this.features = new ArrayList<>();
    }
    this.features.add(featuresItem);
    return this;
  }

   /**
   * An array of feature analytics data.
   * @return features
  **/
  @ApiModelProperty(value = "An array of feature analytics data.")
  public List<FeatureAnalytics> getFeatures() {
    return features;
  }

  public void setFeatures(List<FeatureAnalytics> features) {
    this.features = features;
  }

  public AnalyticsData context(List<Attribute> context) {
    this.context = context;
    return this;
  }

  public AnalyticsData addContextItem(Attribute contextItem) {
    if (this.context == null) {
      this.context = new ArrayList<>();
    }
    this.context.add(contextItem);
    return this;
  }

   /**
   * An array of context attributes that were marked to be reported to analytics.
   * @return context
  **/
  @ApiModelProperty(value = "An array of context attributes that were marked to be reported to analytics.")
  public List<Attribute> getContext() {
    return context;
  }

  public void setContext(List<Attribute> context) {
    this.context = context;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AnalyticsData analyticsData = (AnalyticsData) o;
    return Objects.equals(this.experimentName, analyticsData.experimentName) &&
        Objects.equals(this.branchName, analyticsData.branchName) &&
        Objects.equals(this.variantName, analyticsData.variantName) &&
        Objects.equals(this.dateJoinedVariant, analyticsData.dateJoinedVariant) &&
        Objects.equals(this.features, analyticsData.features) &&
        Objects.equals(this.context, analyticsData.context);
  }

  @Override
  public int hashCode() {
    return Objects.hash(experimentName, branchName, variantName, dateJoinedVariant, features, context);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AnalyticsData {\n");
    
    sb.append("    experimentName: ").append(toIndentedString(experimentName)).append("\n");
    sb.append("    branchName: ").append(toIndentedString(branchName)).append("\n");
    sb.append("    variantName: ").append(toIndentedString(variantName)).append("\n");
    sb.append("    dateJoinedVariant: ").append(toIndentedString(dateJoinedVariant)).append("\n");
    sb.append("    features: ").append(toIndentedString(features)).append("\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

