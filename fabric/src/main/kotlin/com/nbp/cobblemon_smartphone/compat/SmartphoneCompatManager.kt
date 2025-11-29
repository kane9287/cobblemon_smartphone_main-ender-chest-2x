package com.nbp.cobblemon_smartphone.compat

import com.nbp.cobblemon_smartphone.CobblemonSmartphone
import com.nbp.cobblemon_smartphone.compat.trinkets.TrinketsCompat
import com.nbp.cobblemon_smartphone.item.SmartphoneItem
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

/**
 * Manages optional mod compatibility for Cobblemon Smartphone.
 */
object SmartphoneCompatManager {
    
    private var trinketsLoaded: Boolean = false

    /**
     * Initializes all optional mod compatibilities.
     * Should be called during mod initialization.
     */
    fun init() {
        trinketsLoaded = FabricLoader.getInstance().isModLoaded("trinkets")
        
        if (trinketsLoaded) {
            try {
                TrinketsCompat.register()
                CobblemonSmartphone.LOGGER.info("Trinkets compatibility initialized successfully")
            } catch (e: Exception) {
                CobblemonSmartphone.LOGGER.error("Failed to initialize Trinkets compatibility", e)
                trinketsLoaded = false
            }
        }
    }

    /**
     * Checks if Trinkets mod is loaded and available.
     */
    fun isTrinketsLoaded(): Boolean = trinketsLoaded

    /**
     * Gets a smartphone from Trinkets slots if available.
     * @param player The player to check
     * @return The smartphone ItemStack if found in Trinkets slots, null otherwise
     */
    fun getSmartphoneFromTrinkets(player: Player): ItemStack? {
        if (!trinketsLoaded) return null
        return try {
            TrinketsCompat.getEquippedSmartphone(player)
        } catch (e: Exception) {
            CobblemonSmartphone.LOGGER.error("Error checking Trinkets slots", e)
            null
        }
    }

    /**
     * Gets a smartphone from the player's inventory or Trinkets slots.
     * Trinkets slots are checked first if available.
     * @param player The player to check
     * @return The smartphone ItemStack if found, null otherwise
     */
    fun getSmartphone(player: Player): ItemStack? {
        // First check Trinkets slots if available
        if (trinketsLoaded) {
            val trinketsSmartphone = getSmartphoneFromTrinkets(player)
            if (trinketsSmartphone != null) {
                return trinketsSmartphone
            }
        }
        
        // Fallback to regular inventory check
        return player.inventory.items.firstOrNull { it.item is SmartphoneItem }
    }

    /**
     * Gets the SmartphoneItem from the found smartphone, if any.
     * @param player The player to check
     * @return The SmartphoneItem if a smartphone was found, null otherwise
     */
    fun getSmartphoneItem(player: Player): SmartphoneItem? {
        return getSmartphone(player)?.item as? SmartphoneItem
    }
}
