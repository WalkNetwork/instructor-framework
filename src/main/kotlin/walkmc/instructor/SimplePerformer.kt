package walkmc.instructor

import org.bukkit.*
import org.bukkit.command.*
import org.bukkit.entity.*

/**
 * A standard implementation of [Performer].
 */
class SimplePerformer(
	val sender: CommandSender,
	override val command: String,
	override var arguments: Array<out String>
) : Performer {
	override val isConsole: Boolean = sender is ConsoleCommandSender
	override val isPlayer: Boolean = sender is Player
	override val console: ConsoleCommandSender = Bukkit.getConsoleSender()
	override val player: Player get() = sender as Player
}
