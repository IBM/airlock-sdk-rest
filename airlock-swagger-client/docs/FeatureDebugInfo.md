
# FeatureDebugInfo

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**appliedConfigurationRules** | **List&lt;String&gt;** | The names list of applied configuration rules |  [optional]
**appliedOrderingRules** | **List&lt;String&gt;** | The names list of applied ordering rules |  [optional]
**source** | [**SourceEnum**](#SourceEnum) | The source feature value is loaded from |  [optional]
**trace** | **String** | Debug information about feature&#39;s rules execution |  [optional]
**percentage** | **Double** | The percentage of the user the feature is available |  [optional]
**orderingWeight** | **Double** | The weight value defines the feature ordering place relatively to siblings |  [optional]
**branchStatus** | [**BranchStatusEnum**](#BranchStatusEnum) | The feature branch status indicates whether a feature has been overridden in the another branch |  [optional]


<a name="SourceEnum"></a>
## Enum: SourceEnum
Name | Value
---- | -----
SERVER | &quot;SERVER&quot;
DEFAULT | &quot;DEFAULT&quot;
MISSING | &quot;MISSING&quot;
CACHE | &quot;CACHE&quot;
UNKNOWN | &quot;UNKNOWN&quot;


<a name="BranchStatusEnum"></a>
## Enum: BranchStatusEnum
Name | Value
---- | -----
CHECKED_OUT | &quot;CHECKED_OUT&quot;
NEW | &quot;NEW&quot;
NONE | &quot;NONE&quot;
TEMPORARY | &quot;TEMPORARY&quot;



