<#include "mcitems.ftl">
<#if data.defining>
String
<#else>
("${StringUtils.camelToSnake(field$name?replace(" ", ""))?lower_case}", <#if field$value??>BuiltInRegistries.ITEM.getKey(${mappedMCItemToItem(generator.map(field$value, "blocksitems"))}).toString()</#if>)
</#if>