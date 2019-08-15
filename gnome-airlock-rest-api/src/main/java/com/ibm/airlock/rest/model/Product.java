package com.ibm.airlock.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.airlock.sdk.AbstractMultiProductManager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.json.JSONObject;

@ApiModel(description = "Represents an Airlock product registered on the SDK.")
public class Product {

    @ApiModelProperty(value = "Product name.")
    private String name;

    @ApiModelProperty(value = "Product id.")
    private String productId;

    @ApiModelProperty(value = "The season of the application associated with this product as it was provided during initialization.")
    private String seasonId;

    @ApiModelProperty(value = "The version of the application associated with this product as it was provided during initialization.")
    private String appVersion;

    @ApiModelProperty(value = "The instanceId of this product as it was provided during initialization.")
    private String instanceId;

    @JsonCreator
    public Product(){

    }

    @JsonCreator
    public Product(@JsonProperty("name") String name,
                   @JsonProperty("productId") String productId,
                   @JsonProperty("seasonId") String seasonId,
                   @JsonProperty("appVersion") String appVersion,
                   @JsonProperty("instanceId") String instanceId) {
        this.name = name;
        this.productId = productId;
        this.appVersion = appVersion;
        this.seasonId = seasonId;
        this.instanceId = instanceId;
    }

    public Product(AbstractMultiProductManager.ProductMetaData product) {
        this.name = product.getProductName();
        this.productId = product.getProductId();
        this.appVersion = product.getAppVersion();
        this.seasonId = product.getSeasonId();
        this.instanceId = product.getInstanceId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String toString(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("productId",productId);
        jsonObject.put("instanceId",instanceId);
        jsonObject.put("appVersion",appVersion);
        jsonObject.put("seasonId",seasonId);
        return jsonObject.toString();
    }
}
