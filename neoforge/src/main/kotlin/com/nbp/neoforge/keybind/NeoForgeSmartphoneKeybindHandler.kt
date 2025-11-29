package com.nbp.neoforge.keybind

import com.cobblemon.mod.common.CobblemonSounds
import com.nbp.cobblemon_smartphone.client.gui.SmartphoneScreen
import com.nbp.cobblemon_smartphone.client.keybind.SmartphoneKeybinds
import com.nbp.cobblemon_smartphone.item.SmartphoneItem
import com.nbp.neoforge.compat.SmartphoneCompatManager
import net.minecraft.client.Minecraft

/**
 * NeoForge-specific keybind handler that supports Curios integration.
 */
object NeoForgeSmartphoneKeybindHandler {
    
    /**
     * Handles the smartphone keybind press.
     * Checks Curios slots first (if available), then regular inventory.
     */
    fun handleKeybind() {
        val player = Minecraft.getInstance().player ?: return
        
        // Use the compat manager to find a smartphone (checks Curios first if available)
        val smartphone = SmartphoneCompatManager.getSmartphone(player)
        
        if (smartphone != null) {
            val smartphoneItem = smartphone.item as SmartphoneItem
            Minecraft.getInstance().setScreen(SmartphoneScreen(smartphoneItem.getColor()))
            player.playSound(CobblemonSounds.POKEDEX_OPEN, 0.5f, 1f)
        }
    }
}
