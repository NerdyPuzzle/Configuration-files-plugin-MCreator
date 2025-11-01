package ${package}.init;

<#assign commonConfigs = configs?filter(e -> e.configType == 0 || e.configType == 1)>
<#assign clientConfigs = configs?filter(e -> e.configType == 2)>

public class ${JavaModName}Configs {

    <#if commonConfigs?has_content>
    @EventBusSubscriber
    public static class CommonRegistry {

    	@SubscribeEvent
    	public static void register(FMLConstructModEvent event) {
    		event.enqueueWork(() -> {
    		    ModContainer container = ModList.get().getModContainerById("${modid}").get();
    		    <#list commonConfigs as config>
    			    container.registerConfig(ModConfig.Type.
    		    <#if config.configType == 0>
    			    COMMON,
    		    <#else>
    			    SERVER,
    		    </#if>
    			    ${config.getModElement().getName()}Configuration.SPEC,
                    	"${config.file}.toml");
    		    </#list>
    		});
    	}

    }
    </#if>

    @EventBusSubscriber(value = Dist.CLIENT)
    public static class ClientRegistry {

    	@SubscribeEvent
    	public static void register(FMLConstructModEvent event) {
    		event.enqueueWork(() -> {
    		    ModContainer container = ModList.get().getModContainerById("${modid}").get();
    		    <#if clientConfigs?has_content>
    		        <#list clientConfigs as config>
    			        container.registerConfig(ModConfig.Type.CLIENT, ${config.getModElement().getName()}Configuration.SPEC, "${config.file}.toml");
    		        </#list>
    		    </#if>
    		    container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    		});
    	}

    }

}