package io.github.uinnn.instructor

import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

/**
 * A sender type will notificate what type of senders
 * thats a instructor can accept.
 */
enum class SenderType(var message: String) {
  PLAYER("§cApenas jogadores podem executar este comando.") {
    override fun validate(sender: CommandSender): Boolean = sender is Player
  },
  CONSOLE("§cApenas o console pode executar este comando.") {
    override fun validate(sender: CommandSender): Boolean = sender is ConsoleCommandSender
  },
  BOTH("") {
    override fun validate(sender: CommandSender): Boolean = true
  };

  abstract fun validate(sender: CommandSender): Boolean
}