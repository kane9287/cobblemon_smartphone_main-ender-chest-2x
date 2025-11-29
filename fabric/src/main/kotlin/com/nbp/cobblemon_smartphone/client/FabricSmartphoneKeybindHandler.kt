package com.nbp.cobblemon_smartphone.client

import com.cobblemon.mod.common.CobblemonSounds
import com.nbp.cobblemon_smartphone.client.gui.SmartphoneScreen
import com.nbp.cobblemon_smartphone.compat.SmartphoneCompatManager
import com.nbp.cobblemon_smartphone.item.SmartphoneItem
import net.minecraft.client.Minecraft

/**
 * Fabric-specific keybind handler that supports Trinkets integration.
 */
object FabricSmartphoneKeybindHandler {

    /**
     * Handles the smartphone keybind press.
     * Checks both Trinkets slots (if available) and regular inventory.
     */
    fun handleKeybind() {
        val player = Minecraft.getInstance().player ?: return
        
        // Use the compat manager to get smartphone from Trinkets or inventory
        val smartphoneItem = SmartphoneCompatManager.getSmartphoneItem(player)
        
        if (smartphoneItem != null) {
            Minecraft.getInstance().setScreen(SmartphoneScreen(smartphoneItem.getColor()))
            player.playSound(CobblemonSounds.POKEDEX_OPEN, 0.5f, 1f)
        }
    }
}
