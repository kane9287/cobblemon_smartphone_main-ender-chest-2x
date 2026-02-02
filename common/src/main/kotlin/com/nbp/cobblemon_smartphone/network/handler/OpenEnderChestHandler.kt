package com.nbp.cobblemon_smartphone.network.handler.server

import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler
import com.nbp.cobblemon_smartphone.CobblemonSmartphone
import com.nbp.cobblemon_smartphone.network.packet.OpenEnderChestPacket
import net.minecraft.network.chat.Component
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ChestMenu
import java.util.UUID

object OpenEnderChestHandler : ServerNetworkPacketHandler<OpenEnderChestPacket> {
    override fun handle(packet: OpenEnderChestPacket, server: MinecraftServer, player: ServerPlayer) {
        server.execute {
            val isEnabled = CobblemonSmartphone.config.features.enableCloud
            if (!isEnabled) {
                player.displayClientMessage(Component.translatable("message.nbp.cloud.disabled").withColor(0xfd0100), true)
                return@execute
            }

            // Verifica cooldown
            val currentTime = System.currentTimeMillis() / 1000 // tempo em segundos
            val lastUse = EnderChestCooldowns.lastEnderChestUse[player.uuid] ?: 0
            val cooldown = CobblemonSmartphone.config.cooldowns.cloudButton
            val timeElapsed = currentTime - lastUse

            if (timeElapsed < cooldown) {
                val remainingSeconds = (cooldown - timeElapsed).toInt()
                player.displayClientMessage(Component.translatable("message.nbp.cloud.cooldown", remainingSeconds).withColor(0xfd0100), true)
                return@execute
            }

            // Atualiza o cooldown
            EnderChestCooldowns.lastEnderChestUse[player.uuid] = currentTime

            // Abre o enderchest para o jogador
            openEnderChestForPlayer(player)
        }
    }

    private fun openEnderChestForPlayer(player: ServerPlayer) {
        val enderChestInventory = player.enderChestInventory
        val rows = enderChestInventory.containerSize / 9  // Auto-detect rows: 27/9=3, 54/9=6
        
        player.openMenu(object : MenuProvider {
            override fun getDisplayName() = Component.translatable("container.enderchest")
            override fun createMenu(containerId: Int, playerInventory: Inventory, playerEntity: Player): AbstractContainerMenu {
                return when(rows) {
                    1 -> ChestMenu.oneRow(containerId, playerInventory, enderChestInventory)
                    2 -> ChestMenu.twoRows(containerId, playerInventory, enderChestInventory)
                    3 -> ChestMenu.threeRows(containerId, playerInventory, enderChestInventory)
                    4 -> ChestMenu.fourRows(containerId, playerInventory, enderChestInventory)
                    5 -> ChestMenu.fiveRows(containerId, playerInventory, enderChestInventory)
                    6 -> ChestMenu.sixRows(containerId, playerInventory, enderChestInventory)
                    else -> ChestMenu.threeRows(containerId, playerInventory, enderChestInventory)  // Fallback to 3 rows
                }
            }
        })
    }
}

// Objeto auxiliar para armazenar o cooldown de cada jogador no enderchest
object EnderChestCooldowns {
    val lastEnderChestUse = mutableMapOf<UUID, Long>()
}

