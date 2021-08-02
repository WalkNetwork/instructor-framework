package io.github.uinnn.instructor

import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

/**
 * A standard implementation of [InstructorPerformer].
 */
class StandardInstructorPerformer(
  val sender: CommandSender,
  override val command: String,
  override var arguments: Array<out String>
) : InstructorPerformer {
  override val isConsole: Boolean = sender is ConsoleCommandSender
  override val isPlayer: Boolean = sender is Player
  override val console: ConsoleCommandSender get() = sender as ConsoleCommandSender
  override val player: Player get() = sender as Player
}