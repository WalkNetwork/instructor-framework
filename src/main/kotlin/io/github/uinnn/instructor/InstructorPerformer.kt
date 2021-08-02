package io.github.uinnn.instructor

import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

/**
 * A instructor performer is a executor for a instructor.
 */
interface InstructorPerformer : Argumentable {

  /**
   * Verifies if this sender is a console.
   */
  val isConsole: Boolean

  /**
   * Verifies if this sender is a player.
   */
  val isPlayer: Boolean

  /**
   * Returns this sender as console command sender.
   * If the sender is not a console will be throw a error.
   */
  val console: ConsoleCommandSender

  /**
   * Returns this sender as player.
   * If the sender is not a player will be throw a error.
   */
  val player: Player
}

/**
 * Sends a message to the sender of this instructor performer
 */
fun InstructorPerformer.send(message: Any) {
  if (isPlayer) player.sendMessage("$message") else console.sendMessage("$message")
}