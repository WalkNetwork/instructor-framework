package walkmc.instructor

import net.md_5.bungee.api.chat.*
import org.bukkit.command.*
import org.bukkit.entity.*
import walkmc.extensions.*

/**
 * A instructor performer is a executor for a instructor.
 */
interface Performer : Argumentable {
	
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
fun Performer.log(message: Any) {
	if (isPlayer) player.sendMessage("$message") else console.sendMessage("$message")
}

/**
 * Sends a component message to the sender of this instructor performer
 */
fun Performer.log(message: TextComponent) {
	if (isPlayer) player.spigot().sendMessage(message)
}

/**
 * Sends a action bar message to the sender of this instructor performer
 */
fun Performer.action(message: Any) {
	if (isPlayer) player.action(message)
}

/**
 * Sends a title message to the sender of this instructor performer
 */
fun Performer.title(
	title: String = "",
	subtitle: String = "",
	fadeIn: Int = 20,
	stay: Int = 40,
	fadeOut: Int = 20,
) {
	if (isPlayer) player.title(title, subtitle, fadeIn, stay, fadeOut)
}

/**
 * Validates if the player send of this command is a admin or higher.
 */
fun Performer.checkIsAdmin() =
	validate(isConsole || player.isAdmin, "§cComando reservado para administradores.")

/**
 * Validates if the player send of this command is a manager or higher.
 */
fun Performer.checkIsManager() =
	validate(isConsole || player.isManager, "§cComando reservado para managers.")

/**
 * Validates if the player send of this command is a coordenator or higher.
 */
fun Performer.checkIsCoordenator() =
	validate(isConsole || player.isCoordenator, "§cComando reservado para coordenadores.")

/**
 * Validates if the player send of this command is a moderator or higher.
 */
fun Performer.checkIsModerator() =
	validate(isConsole || player.isModerator, "§cComando reservado para moderadores.")

/**
 * Validates if the player send of this command is a helper or higher.
 */
fun Performer.checkIsHelper() =
	validate(isConsole || player.isHelper, "§cComando reservado para ajudantes.")

/**
 * Validates if the player send of this command is a walk or higher.
 */
fun Performer.checkIsWalk() =
	validate(isConsole || player.isWalk, "§cComando reservado para vips Walk.")

/**
 * Validates if the player send of this command is a prince or higher.
 */
fun Performer.checkIsPrince() =
	validate(isConsole || player.isPrince, "§cComando reservado para vips Prince.")

/**
 * Validates if the player send of this command is a duke or higher.
 */
fun Performer.checkIsDuke() =
	validate(isConsole || player.isDuke, "§cComando reservado para vips Duke.")

/**
 * Validates if the player send of this command is a baron or higher.
 */
fun Performer.checkIsBaron() =
	validate(isConsole || player.isBaron, "§cComando reservado para vips Baron.")
