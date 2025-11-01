<#if data.defining>
Boolean
<#else>
("${StringUtils.camelToSnake(field$name?replace(" ", ""))?lower_case}", ${field$value})
</#if>