package com.nbp.neoforge.compat.curios

import com.nbp.cobblemon_smartphone.CobblemonSmartphone
import com.nbp.cobblemon_smartphone.item.SmartphoneItem
import com.nbp.cobblemon_smartphone.registry.CobblemonSmartphoneItems
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import top.theillusivec4.curios.api.CuriosApi
import top.theillusivec4.curios.api.type.capability.ICurioItem

/**
 * Empty ICurioItem implementation for smartphones.
 * No special behavior needed - just allows the item to be equipped in curio slots.
 */
class SmartphoneCurio : ICurioItem

/**
 * Curios compatibility layer for Cobblemon Smartphone.
 * This class is only loaded when Curios is present.
 */
object CuriosCompat {
    
    private val smartphoneCurio = SmartphoneCurio()

    /**
     * Checks if the player has a smartphone equipped in any Curios slot.
     * @param player The player to check
     * @return The smartphone ItemStack if found, null otherwise
     */
    fun getEquippedSmartphone(player: Player): ItemStack? {
        return try {
            val curiosOpt = CuriosApi.getCuriosInventory(player)
            if (curiosOpt.isPresent) {
                val curios = curiosOpt.get()
                // Search in our custom smartphone slot first
                val smartphoneSlot = curios.findFirstCurio { stack -> stack.item is SmartphoneItem }
                if (smartphoneSlot.isPresent) {
                    return smartphoneSlot.get().stack()
                }
            }
            null
        } catch (e: Exception) {
            CobblemonSmartphone.LOGGER.debug("Error getting smartphone from Curios: ${e.message}")
            null
        }
    }

    /**
     * Checks if the player has a smartphone equipped in any Curios slot.
     * @param player The player to check
     * @return true if a smartphone is equipped in a Curios slot
     */
    fun hasSmartphoneEquipped(player: Player): Boolean {
        return try {
            val curiosOpt = CuriosApi.getCuriosInventory(player)
            if (curiosOpt.isPresent) {
                val curios = curiosOpt.get()
                curios.findFirstCurio { stack -> stack.item is SmartphoneItem }.isPresent
            } else {
                false
            }
        } catch (e: Exception) {
            CobblemonSmartphone.LOGGER.debug("Error checking Curios: ${e.message}")
            false
        }
    }

    /**
     * Registers all smartphones as curios using the ICurioItem interface implementation
     */
    fun register() {
        // Register all smartphone items as valid curios
        CobblemonSmartphoneItems.all().forEach { item ->
            CuriosApi.registerCurio(item, smartphoneCurio)
        }
        
        CobblemonSmartphone.LOGGER.info("Curios integration enabled - registered ${CobblemonSmartphoneItems.all().size} smartphones")
    }
}
