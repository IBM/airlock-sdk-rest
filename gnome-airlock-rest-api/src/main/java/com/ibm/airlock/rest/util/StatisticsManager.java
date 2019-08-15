package com.ibm.airlock.rest.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticsManager {

    private static Hashtable<String, String> handlerPatternToName = new Hashtable<>();
    private static Hashtable<String, Integer> requestsCounters = new Hashtable<>();
    private static Hashtable<String, Long> totalProcessingTime = new Hashtable<>();

    public static synchronized void incrementHandlerRequestCounter(String handlerName) {
        Optional<Map.Entry<String, Integer>> handlerCounter = requestsCounters.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(handlerName)).findFirst();
        if (handlerCounter.isPresent()) {
            handlerCounter.get().setValue(handlerCounter.get().getValue() + 1);
        } else {
            requestsCounters.put(handlerName, 1);
        }
    }

    public static synchronized void addHandlerName(String handlerPattern, String name) {
        handlerPatternToName.put(handlerPattern, name);
    }

    public static synchronized int getRequestsCounters(String handlerName) {
        return requestsCounters.get(handlerName);
    }

    public static synchronized void updateHandlerRequestProcessingTime(String handlerName, Long processingTime) {
        Optional<Map.Entry<String, Long>> handlerCounter = totalProcessingTime.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(handlerName)).findFirst();
        if (handlerCounter.isPresent()) {
            handlerCounter.get().setValue(handlerCounter.get().getValue() + processingTime);
        } else {
            totalProcessingTime.put(handlerName, processingTime);
        }
    }

    public static String getHandlerNameByPattern(String handlerPattern) {
        return handlerPatternToName.containsKey(handlerPattern) ?
                handlerPatternToName.get(handlerPattern) : handlerPattern;
    }

    public static synchronized JSONArray getHandlerStatistics(String handlerName) {

        JSONArray handlerStatistics = new JSONArray();

        if (handlerName.equals("*")) {

            //LinkedHashMap preserve the ordering of elements in which they are inserted
            LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();

            //Use Comparator.reverseOrder() for reverse ordering
            requestsCounters.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

            for (String name : reverseSortedMap.keySet()) {
                handlerStatistics.put(getStatictieByHandler(name));
            }
        } else {
            handlerStatistics.put(getStatictieByHandler(handlerName));
        }

        return handlerStatistics;
    }

    private static JSONObject getStatictieByHandler(String handlerName) {
        JSONObject handlerStatistic = new JSONObject();
        handlerStatistic.put("handlerName", getHandlerNameByPattern(handlerName));
        handlerStatistic.put("requests", 0);
        handlerStatistic.put("avargeTimePerRequest", 0);
        if (requestsCounters.get(handlerName) != null && requestsCounters.get(handlerName) != 0) {
            handlerStatistic.put("requests", requestsCounters.get(handlerName));
            handlerStatistic.put("avargeTimePerRequest", (totalProcessingTime.get(handlerName) / requestsCounters.get(handlerName)));
        }

        return handlerStatistic;
    }
}
