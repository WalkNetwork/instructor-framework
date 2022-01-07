package walkmc.instructor

import org.bukkit.command.*

/**
 * A instructor class is a easily implementation of a command.
 * This allows to choose what senders will be able to execute,
 * the given perform, and sub commands!
 */
open class Instructor(name: String) : Command(name.trim().lowercase()), Instructable {
	override lateinit var performer: Performer.(Instructor) -> Unit
	override var childrens: MutableSet<ChildrenInstructor> = HashSet()
	override var sender = Sender.ALL
	
	constructor(name: String, action: Instructor.() -> Unit) :
		this(name) {
		apply(action)
	}
	
	constructor(name: String, vararg aliases: String) :
		this(name) {
		this.aliases = aliases.toList()
	}
	
	constructor(name: String, vararg aliases: String, action: Instructor.() -> Unit) :
		this(name, *aliases) {
		apply(action)
	}
	
	init {
		permissionMessage = "§cSem permissão."
	}
	
	override fun execute(sender: CommandSender, name: String, args: Array<out String>): Boolean {
		if (!this.sender.checker(sender)) {
			sender.sendMessage(this.sender.message)
			return false
		}
		
		if (!permission.isNullOrBlank() && !sender.hasPermission(permission)) {
			sender.sendMessage(permissionMessage)
			return false
		}
		
		if (childrens.isNotEmpty()) {
			args.getOrNull(0)
				?.let { argument -> findChildren(argument) }
				?.let {
					it.execute(sender, "$name ${args[0]}", args.sliceArray(1 until args.size))
					return true
				}
		}
		
		if (!this::performer.isInitialized)
			return false
		
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
	args: Array<out String>,
): Result<Unit> {
	return runCatching {
		performer(SimplePerformer(sender, name, args), this)
	}.onFailure { ex ->
		if (ex is InstructorError) {
			sender.sendMessage(ex.message)
		} else {
			sender.sendMessage("§cUm erro inesperado ocorreu.")
			ex.printStackTrace()
		}
	}
}

/**
 * Registers this instructor to the bukkit command map.
 */
fun Instructor.register() = InstructorManager.register(this)

/**
 * Unregisters this instructor from the bukkit command map.
 */
fun Instructor.unregister() = InstructorManager.unregister(name)

/**
 * Finds a sub command of this main command by the specified command [name]
 */
fun Instructor.findChildren(name: String): ChildrenInstructor? {
	return childrens.find { sub ->
		sub.name.equals(name, true) || sub.aliases.any { it.equals(name, true) }
	}
}

fun Instructor.createChildren(
	name: String,
	vararg aliases: String = emptyArray(),
	justPlayers: Boolean = false,
	permission: String? = null,
	action: Instructor.() -> Unit
): ChildrenInstructor {
	val children = createInstructorChildren(
		this,
		name,
		*aliases,
		justPlayers = justPlayers,
		permission = permission,
		action = action
	)
	
	childrens.add(children)
	return children
}
