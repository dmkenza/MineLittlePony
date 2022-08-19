package com.kenza

import com.kenza.KenzaInjector.entityHasElytraFromTrinkets
import com.minelittlepony.api.pony.meta.Race
import com.minelittlepony.common.event.ScreenInitCallback
import dev.emi.trinkets.api.TrinketsApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.MappingResolver
import net.irisshaders.iris.api.v0.IrisApi
//import net.irisshaders.iris.api.v0.IrisApi
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager
import java.util.*


//summon minecraft:villager ~ ~ ~ {VillagerData:{level:1,profession:"minecraft:nitwit",type:"minecraft:desert"}}
object KenzaInjector {

    val LOGGER = LogManager.getLogger()

    val resData = HashMap<String, Identifier>()
    val pathHashMap = HashMap<Int, String>()


    private var initTitleCounter = 0

    fun init() {


        ScreenInitCallback.EVENT.register(ScreenInitCallback { screen: Screen?, buttons: ScreenInitCallback.ButtonList? ->

            GlobalScope.launch {
                delay(5000)
                test1()
            }

//            this.onScreenInit(
//                screen,
//                buttons
//            )
        })

        onEntityLoaded()
    }


    private fun onScreenInit(screen: Screen?, buttons: ScreenInitCallback.ButtonList?) {
//        if (screen is CreateWorldScreen) {
//
//        }
//        if (screen is OpenToLanScreen) {
////            buttons.addButton(Button(screen.width / 2 - 155, 130, 150, 20))
////                .onClick { b: Button? -> MinecraftClient.getInstance().setScreen(LanSettingsScreen(screen)) }
////                .style.text = TranslatableText("unicopia.options.title")
//        }
//        if (screen is TitleScreen) {
//            //open world after start minecraft
//            initTitleCounter++
//            if (initTitleCounter == 2) {
//                val client = MinecraftClient.getInstance()
//                client.levelStorage.levelList.firstOrNull()?.let { level ->
//                    client.soundManager.play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0f))
//                    if (client.levelStorage.levelExists(level.name)) {
//                        client.setScreenAndRender(SaveLevelScreen(TranslatableText("selectWorld.data_read")))
//                        client.startIntegratedServer(level.name)
//                    }
//                }
//            }
//        }
    }

    private fun onEntityLoaded() {

        ServerEntityEvents.ENTITY_LOAD.register(ServerEntityEvents.Load { entity: Entity?, serverWorld: ServerWorld? ->
            entity?.toVillagerEntityExtension()?.setCustomName(false)
//            entity?.toVillagerEntityExtension()?.setCustomName(true)
        })
//
//        ServerEntityEvents.ENTITY_UNLOAD.register(ServerEntityEvents.Unload { entity: Entity?, serverWorld: ServerWorld? ->
//            val x1 = 2
//            entity?.toVillagerSkinContainer()?.initSkinID()
//        })
//
        ClientEntityEvents.ENTITY_LOAD.register(ClientEntityEvents.Load(object : (Entity, ClientWorld) -> Unit {
            override fun invoke(entity: Entity, p2: ClientWorld) {
//                entity.toVillagerSkinContainer()?.test2()
            }
        }))
//
//        ServerPlayConnectionEvents.JOIN.register(ServerPlayConnectionEvents.Join { handler: ServerPlayNetworkHandler, sender: PacketSender, server: MinecraftServer? ->
//            val x1 = handler.player
//            x1.cameraEntity
//        })
    }

    fun findTexturePath(entity: Entity): String {
        val skidID = entity.toVillagerEntityExtension()?.ponySkinID ?: ""
        return "textures/entity/villager/all/$skidID.png"
    }

    fun getOrCreateIdentifier(path: String): Identifier {
        val identifier = resData.get(path)
        identifier?.let {
            return identifier
        }
        Identifier("minelittlepony", path).let {
            resData.put(path, it)
            return it
        }
    }


    fun findTexture(entity: Entity): Identifier {
        findTexturePath(entity).let { path ->
            return getOrCreateIdentifier(path)
        }
    }

    fun findTexture(category: String, identifier: Identifier, entityType: String): Identifier {
        val path = "textures/entity/" + entityType + "/" + category + "/" + identifier.path + ".png"
        return getOrCreateIdentifier(path)
    }


    fun getOverridePonyRaceOfEntity(entity: Entity): Race? {

        return if (entity.canLoadDynamicPonySkin()) {
            entity.toVillagerEntityExtension()?.ponyRace
        } else {
            null
        }
    }


    fun LivingEntity.entityHasElytraFromTrinkets(): Boolean {
        return try {
            TrinketsApi.getTrinketComponent(this).value?.getEquipped { stack: ItemStack ->
                stack.isOf(
                    Items.ELYTRA
                )
            }?.isNotEmpty() ?: false
        } catch (e: NoClassDefFoundError) {
            false
        }
    }


    fun isIrisShadersEnabled(): Boolean {
        return try {
            IrisApi.getInstance().config.areShadersEnabled()
        } catch (e: NoClassDefFoundError) {
            false
        }
    }

}

fun Entity.canLoadDynamicPonySkin(): Boolean {
    return (this.toVillagerEntityExtension()?.ponySkinID ?: -1) >= 0
}

fun test1(){

//    val resolver: MappingResolver = FabricLoader.getInstance().mappingResolver
////    val cls = Class.forName(resolver.mapClassName("iris", "net.irisshaders.iris.api.v0.IrisApi"))
//    val cls = Class.forName( "net/irisshaders/iris/api/v0/IrisApi")
    val cls = Class.forName( "net.irisshaders.iris.api.v0.IrisApi")
////    cls.newInstance()
////    val method: Method = cls.getDeclaredMethod("getInstance(")
////    method.invoke(cls.newInstance(), "TESTING")
//
//
    val x1 = IrisApi.getInstance().config.areShadersEnabled()
    cls
}

val <T> Optional<T>.value: T?
    get() = orElse(null)
