package io.github.uinnn.instructor

import org.bukkit.Bukkit
import org.bukkit.command.SimpleCommandMap

/**
 * Returns the command map of this server.
 */
val commandMap: SimpleCommandMap by lazy {
  val server = Bukkit.getServer()
  server::class.java.getDeclaredField("commandMap").apply {
    isAccessible = true
  }.get(server) as SimpleCommandMap
}

/**
 * Returns a only known commands field.
 */
val knownCommands: MutableMap<*, *> by lazy {
  SimpleCommandMap::class.java.getDeclaredField("knownCommands").apply {
    isAccessible = true
  }.get(commandMap) as MutableMap<*, *>
}