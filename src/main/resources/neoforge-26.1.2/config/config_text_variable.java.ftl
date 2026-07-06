<#if data.defining>
String
<#else>
("${StringUtils.camelToSnake(field$name?replace(" ", ""))?lower_case}", "<#if field$value??>${field$value}</#if>")
</#if>