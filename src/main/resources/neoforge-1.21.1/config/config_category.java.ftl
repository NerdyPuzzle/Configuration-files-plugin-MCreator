<#if !data.defining>BUILDER.push("${StringUtils.camelToSnake(field$name?replace(" ", ""))?lower_case}");</#if>
    ${statement$write}
<#if !data.defining>BUILDER.pop();</#if>