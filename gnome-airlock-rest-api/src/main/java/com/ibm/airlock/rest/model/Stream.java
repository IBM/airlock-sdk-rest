package com.ibm.airlock.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.gson.JsonObject;
import com.ibm.airlock.common.streams.AirlockStream;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@ApiModel(description = "Represents an Airlock stream.")
public class Stream {
    @ApiModelProperty(value = "Stream filter string value.")
    private String filter;

    @ApiModelProperty(value = "Stream processor string value.")
    private String processor;

    @ApiModelProperty(value = "Stream trace messages.")
    private String[] trace;

    @ApiModelProperty(value = "Stream enablement status.")
    private boolean enabled;


    @ApiModelProperty(value = "Stream status.")
    private boolean processingEnabled;

    @ApiModelProperty(value = "Stream processing suspention flag value")
    private boolean processingSuspended;

    @ApiModelProperty(value = "Stream internal groups.")
    private List internalUserGroups;

    @ApiModelProperty(value = "Stream minApp version defined.")
    private String minAppVersion;

    @ApiModelProperty(value = "Stream rollout percentage version defined.")
    private long rolloutPercentage;

    @ApiModelProperty(value = "Stream stage.")
    private String stage;

    @ApiModelProperty(value = "Stream name")
    private String name;
//    private String appVersion;

    @ApiModelProperty(value = "Stream maxCachSize.")
    private int maxCacheSize;
//    private int maxEventsSize;

    @ApiModelProperty(value = "Stream lastProcessedTime.")
    private String lastProcessedTime;

    @ApiModelProperty(value = "Stream id.")
    private String id;

    public Stream(AirlockStream airlockStream) {
        this.filter = airlockStream.getFilter();
        this.processor = airlockStream.getProcessor();

        // add only not null trace values
        List<String> list = new ArrayList<String>(Arrays.asList(airlockStream.getTrace().getTraceArr()));
        list.removeIf(Objects::isNull);
        if (list != null && list.size() > 0){
            this.trace = new String[list.size()];
            this.trace = list.toArray(this.trace);
        }
        
        this.processingSuspended = airlockStream.isProcessingSuspended();
        this.internalUserGroups = new ArrayList();
//        JSONArray groups = airlockStream.();
//        if (groups != null) {
//            for (int i = 0; i < groups.length(); i++) {
//                this.internalUserGroups.add(groups.get(i));
//            }
//        }
        this.minAppVersion = airlockStream.getMinAppVersion();
        this.rolloutPercentage = airlockStream.getRolloutPercentage();
        this.stage = airlockStream.getStage();
        this.name = airlockStream.getName();
//        this.appVersion = airlockStream.get;
        this.maxCacheSize = airlockStream.getMaxCacheSize();
//        this.maxEventsSize = airlockStream.getM;
        this.lastProcessedTime = airlockStream.getLastProcessedTime();
        this.id = airlockStream.getId();
        JSONArray events = airlockStream.getEvents();
        this.events = new ArrayList();
        if (events != null) {
            for (int i = 0; i < events.length(); i++) {
                this.events.add(events.get(i).toString());
            }
        }
        this.enabled = airlockStream.isEnabled();
        this.result = airlockStream.getResult();
        this.cache = airlockStream.getCache();
        this.processingEnabled = airlockStream.isProcessingEnabled();
    }

    @JsonCreator
    public Stream() {

    }

    //Objects to be persisted when processing
    private List events;

    private String cache;

    private String result;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String[] getTrace() {
        return trace;
    }

    public void setTrace(String[] trace) {
        this.trace = trace;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isProcessingSuspended() {
        return processingSuspended;
    }

    public void setProcessingSuspended(boolean processingSuspended) {
        this.processingSuspended = processingSuspended;
    }

    public List getInternalUserGroups() {
        return internalUserGroups;
    }

    public void setInternalUserGroups(List internalUserGroups) {
        this.internalUserGroups = internalUserGroups;
    }

    public String getMinAppVersion() {
        return minAppVersion;
    }

    public void setMinAppVersion(String minAppVersion) {
        this.minAppVersion = minAppVersion;
    }

    public long getRolloutPercentage() {
        return rolloutPercentage;
    }

    public void setRolloutPercentage(long rolloutPercentage) {
        this.rolloutPercentage = rolloutPercentage;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxCacheSize() {
        return maxCacheSize;
    }

    public void setMaxCacheSize(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }

    public String getLastProcessedTime() {
        return lastProcessedTime;
    }

    public void setLastProcessedTime(String lastProcessedTime) {
        this.lastProcessedTime = lastProcessedTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List getEvents() {
        return events;
    }

    public void setEvents(List events) {
        this.events = events;
    }

//    public JSONArray getPendingEvents() {
//        return pendingEvents;
//    }
//
//    public void setPendingEvents(JSONArray pendingEvents) {
//        this.pendingEvents = pendingEvents;
//    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isProcessingEnabled() {
        return processingEnabled;
    }
}