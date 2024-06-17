package ${package}.configuration;
<#include "../mcelements.ftl">
<#include "../mcitems.ftl">

public class ${name}Configuration {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;

	<#list data.pools as pool>
	  <#list pool.entries as var>
	    <#if var.silkTouchMode = 0>
	      public static final ForgeConfigSpec.ConfigValue<Boolean> ${var.varname?upper_case};
	    <#elseif var.silkTouchMode = 1>
	      public static final ForgeConfigSpec.ConfigValue<Double> ${var.varname?upper_case};
	    <#elseif var.silkTouchMode = 5>
	      public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ${var.varname?upper_case};
	    <#else>
	      public static final ForgeConfigSpec.ConfigValue<String> ${var.varname?upper_case};
	    </#if>
	  </#list>
	</#list>

	static {
		<#list data.pools as pool>
		  BUILDER.push("${pool.category}");

		  <#list pool.entries as var>
		    ${var.varname?upper_case} = BUILDER.<#if var.enablecomment>comment("${var.comment}").</#if>define<#if var.silkTouchMode = 5>List</#if>("${var.vardisplay}",
		    <#if var.silkTouchMode = 0>
			  <#if var.logicField = 0>
		        true);
			  <#else>
		        false);
			  </#if>
		    <#elseif var.silkTouchMode = 1>
		      (double) ${var.numberField});
		    <#elseif var.silkTouchMode = 2>
		      "${var.textDefault}");
		    <#elseif var.silkTouchMode = 3>
		      ForgeRegistries.ITEMS.getKey(${mappedMCItemToItem(generator.map(var.block, "blocksitems"))}).toString());
		    <#elseif var.silkTouchMode = 4>
		      ForgeRegistries.ITEMS.getKey(${mappedMCItemToItem(generator.map(var.item, "blocksitems"))}).toString());
		    <#elseif var.silkTouchMode = 5>
		      List.of(
		        <#list var.stringlist as string>
		            "${string}"<#sep>,
		        </#list>
		      ), entry -> true);
		    </#if>
		</#list>

		  BUILDER.pop();
		</#list>
		
		SPEC = BUILDER.build();
 	}
  
}