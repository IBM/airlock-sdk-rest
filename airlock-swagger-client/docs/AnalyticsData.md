
# AnalyticsData

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**experimentName** | **String** | The current experiment name. |  [optional]
**branchName** | **String** | The current branch name. |  [optional]
**variantName** | **String** | The current variant name. |  [optional]
**dateJoinedVariant** | **Long** | The date in which the client joined the current variant (milliseconds from epoch). |  [optional]
**features** | [**List&lt;FeatureAnalytics&gt;**](FeatureAnalytics.md) | An array of feature analytics data. |  [optional]
**context** | [**List&lt;Attribute&gt;**](Attribute.md) | An array of context attributes that were marked to be reported to analytics. |  [optional]



