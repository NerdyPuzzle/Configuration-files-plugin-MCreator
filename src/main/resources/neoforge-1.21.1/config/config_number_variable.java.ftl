<#if data.defining>
Double
<#else>
("${StringUtils.camelToSnake(field$name?replace(" ", ""))?lower_case}", (double) ${field$value})
</#if>