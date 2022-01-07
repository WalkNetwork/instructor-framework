package walkmc.instructor

import org.bukkit.command.*
import org.bukkit.entity.*

/**
 * A sender type will notificate what type of senders
 * that's an instructor can accept.
 */
open class Sender(val message: String, val checker: (CommandSender) -> Boolean) {
	companion object {
		val PLAYER = Sender("§cApenas jogadores podem executar este comando.") { it is Player }
		val CONSOLE = Sender("§cApenas jogadores podem executar este comando.") { it is ConsoleCommandSender }
		val ALL = Sender("") { true }
	}
}
