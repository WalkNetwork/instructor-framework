package io.github.uinnn.instructor

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

typealias InstructorAction = Instructor.() -> Unit

/**
 * A instructor class is a easily implementation of a command.
 * This allows to choose what senders will be able to execute,
 * the given perform, and sub commands!
 */
class Instructor(name: String) : Command(name.trim()), Instructable {
  override lateinit var performer: Performer
  override var alternates: MutableSet<Instructor> = HashSet()
  override var senderType: SenderType = SenderType.BOTH

  constructor(name: String, action: InstructorAction) : this(name) {
    apply(action)
  }

  constructor(name: String, vararg aliases: String) : this(name) {
    this.aliases = aliases.toList()
  }

  constructor(name: String, vararg aliases: String, action: InstructorAction) : this(name, *aliases) {
    apply(action)
  }

  init {
    permissionMessage = "§cSem permissão."
  }

  override fun execute(sender: CommandSender, name: String, args: Array<out String>): Boolean {
    if (!senderType.validate(sender)) {
      sender.sendMessage(senderType.message)
      return false
    }
    if (alternates.isNotEmpty()) {
      args.getOrNull(0)?.let { argument ->
        alternates.find { it.name.equals(argument, true) || it.aliases.any { it.equals(argument, true) } }
      }?.let {
        it.execute(sender, "$name ${args[0]}", args.sliceArray(1 until args.size))
        return true
      }
    }
    if (!permission.isNullOrBlank() && !sender.hasPermission(permission)) {
      sender.sendMessage(permissionMessage)
      return false
    }
    if (!this::performer.isInitialized) return false
    return attemptPerform(sender, name, args).isSuccess
  }
}

/**
 * Attempts to perform this instructor. A [Result] instance
 * is created to accompany the final result.
 */
fun Instructor.attemptPerform(
  sender: CommandSender,
  name: String,
  args: Array<out String>
): Result<Unit> {
  return runCatching {
    performer(StandardInstructorPerformer(sender, name, args), this)
  }.onFailure { ex ->
    if (ex is InstructorError) sender.sendMessage(ex.message)
    else {
      sender.sendMessage("§cUm erro inesperado ocorreu.")
      ex.printStackTrace()
    }
  }
}

/**
 * Registers this instructor to the bukkit command map.
 */
fun Instructor.register() = commandMap.register(label, this)

/**
 * Unregisters this instructor from the bukkit command map.
 */
fun Instructor.unregister() = knownCommands.remove(name)
