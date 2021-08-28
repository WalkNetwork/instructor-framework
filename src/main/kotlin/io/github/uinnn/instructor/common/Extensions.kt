package io.github.uinnn.instructor.common

import io.github.uinnn.instructor.Instructor
import org.bukkit.command.Command

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
 * Returns this or the default value if this is null.
 * Equals to:
 * `this ?: value`
 */
infix fun <T> T?.or(value: T): T = this ?: value