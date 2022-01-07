package walkmc.instructor.common

import org.bukkit.command.*
import walkmc.extensions.*
import walkmc.instructor.*

/**
 * Converts this command to a instructor.
 */
fun Command.toInstructor() = Instructor(name) {
	this.aliases = aliases
	this.description = description
	this.label = label
	this.permission = permission
	this.permissionMessage = permissionMessage
}

/**
 * Finds a registered command by [name].
 */
fun findCommand(name: String): Command? {
	return server.knownCommands[name]
}

/**
 * Finds a instructor command that's is created from [Instructor] by [name].
 */
fun findInstructor(name: String): Instructor? {
	return InstructorManager[name]
}
