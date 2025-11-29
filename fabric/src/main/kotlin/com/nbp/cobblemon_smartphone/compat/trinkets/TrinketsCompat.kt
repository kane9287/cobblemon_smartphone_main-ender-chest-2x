package com.nbp.cobblemon_smartphone.compat.trinkets

import com.nbp.cobblemon_smartphone.CobblemonSmartphone
import com.nbp.cobblemon_smartphone.item.SmartphoneItem
import com.nbp.cobblemon_smartphone.registry.CobblemonSmartphoneItems
import dev.emi.trinkets.api.Trinket
import dev.emi.trinkets.api.TrinketsApi
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

/**
 * Empty Trinket implementation for smartphones.
 * No special behavior needed - just allows the item to be equipped in trinket slots.
 */
class SmartphoneTrinket : Trinket

/**
 * Trinkets compatibility layer for Cobblemon Smartphone.
 * This class is only loaded when Trinkets is present.
 */
object TrinketsCompat {
    
    private val smartphoneTrinket = SmartphoneTrinket()

    /**
     * Checks if the player has a smartphone equipped in any Trinkets slot.
     * @param player The player to check
     * @return The smartphone ItemStack if found, null otherwise
     */
    fun getEquippedSmartphone(player: Player): ItemStack? {
        return try {
            val component = TrinketsApi.getTrinketComponent(player as LivingEntity)
            if (component.isPresent) {
                val trinketComponent = component.get()
                val equipped = trinketComponent.getEquipped { stack -> stack.item is SmartphoneItem }
                if (equipped.isNotEmpty()) {
                    // Returns Tuple/Pair - use .b for the ItemStack (second element)
                    return equipped[0].b
                }
            }
            null
        } catch (e: Exception) {
            CobblemonSmartphone.LOGGER.debug("Error getting smartphone from Trinkets: ${e.message}")
            null
        }
    }

    /**
     * Checks if the player has a smartphone equipped in any Trinkets slot.
     * @param player The player to check
     * @return true if a smartphone is equipped in a Trinkets slot
     */
    fun hasSmartphoneEquipped(player: Player): Boolean {
        return try {
            val component = TrinketsApi.getTrinketComponent(player as LivingEntity)
            if (component.isPresent) {
                component.get().isEquipped { stack -> stack.item is SmartphoneItem }
            } else {
                false
            }
        } catch (e: Exception) {
            CobblemonSmartphone.LOGGER.debug("Error checking Trinkets: ${e.message}")
            false
        }
    }

    /**
     * Registers all smartphones as trinkets using the Trinket interface implementation
     */
    fun register() {
        // Register all smartphone items as valid trinkets
        TrinketsApi.registerTrinket(CobblemonSmartphoneItems.WHITE_SMARTPHONE, smartphoneTrinket)
        TrinketsApi.registerTrinket(CobblemonSmartphoneItems.ORANGE_SMARTPHONE, smartphoneTrinket)
        TrinketsApi.registerTrinket(CobblemonSmartphoneItems.MAGENTA_SMARTPHONE, smartphoneTrinket)
        TrinketsApi.registerTrinket(CobblemonSmartphoneItems.LIGHT_BLUE_SMARTPHONE, smartphoneTrinket)
        TrinketsApi.registerTrinket(CobblemonSmartphoneItems.YELLOW_SMARTPHONE, smartphoneTrinket)
        TrinketsApi.registerTrinket(CobblemonSmartphoneItems.LIME_SMARTPHONE, smartphoneTrinket)
        TrinketsApi.registerTrinket(CobblemonSmartphoneItems.PINK_SMARTPHONE, smartphoneTrinket)
        TrinketsApi.registerTrinket(CobblemonSmartphoneItems.GRAY_SMARTPHONE, smartphoneTrinket)
        TrinketsApi.registerTrinket(CobblemonSmartphoneItems.LIGHT_GRAY_SMARTPHONE, smartphoneTrinket)
        TrinketsApi.registerTrinket(CobblemonSmartphoneItems.CYAN_SMARTPHONE, smartphoneTrinket)
        TrinketsApi.registerTrinket(CobblemonSmartphoneItems.PURPLE_SMARTPHONE, smartphoneTrinket)
        TrinketsApi.registerTrinket(CobblemonSmartphoneItems.BLUE_SMARTPHONE, smartphoneTrinket)
        TrinketsApi.registerTrinket(CobblemonSmartphoneItems.BROWN_SMARTPHONE, smartphoneTrinket)
        TrinketsApi.registerTrinket(CobblemonSmartphoneItems.GREEN_SMARTPHONE, smartphoneTrinket)
        TrinketsApi.registerTrinket(CobblemonSmartphoneItems.RED_SMARTPHONE, smartphoneTrinket)
        TrinketsApi.registerTrinket(CobblemonSmartphoneItems.BLACK_SMARTPHONE, smartphoneTrinket)
        
        CobblemonSmartphone.LOGGER.info("Trinkets integration enabled - registered ${CobblemonSmartphoneItems.all().size} smartphones")
    }
}