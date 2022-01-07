package walkmc.instructor

internal fun createInstructor(
	name: String,
	vararg aliases: String = emptyArray(),
	justPlayers: Boolean = false,
	permission: String? = null,
	action: Instructor.() -> Unit
): Instructor {
	return Instructor(name, *aliases) {
		sender = if (justPlayers) Sender.PLAYER else Sender.ALL
		
		if (permission != null)
			this.permission = permission
		
		action(this)
	}
}

internal fun createInstructorChildren(
	parent: Instructor,
	name: String,
	vararg aliases: String = emptyArray(),
	justPlayers: Boolean = false,
	permission: String? = null,
	action: Instructor.() -> Unit
): ChildrenInstructor {
	return ChildrenInstructor(parent, name, *aliases) {
		sender = if (justPlayers) Sender.PLAYER else Sender.ALL
		
		if (permission != null)
			this.permission = permission
		
		action(this)
	}
}

/**
 * Creates a new main instructor with specified name, aliases and builder action.
 */
fun command(
	name: String,
	vararg aliases: String = emptyArray(),
	justPlayers: Boolean = false,
	permission: String? = null,
	action: Instructor.() -> Unit
): Instructor {
	val command = createInstructor(
		name, *aliases, justPlayers = justPlayers, permission = permission, action = action
	)
	
	command.register()
	return command
}

/**
 * Creates a new main instructor with specified
 * name, aliases and action as performer of this instructor.
 */
fun commandOf(
	name: String,
	vararg aliases: String = emptyArray(),
	justPlayers: Boolean = false,
	permission: String? = null,
	action: Performer.(Instructor) -> Unit
): Instructor {
	val command = createInstructor(name, *aliases, justPlayers = justPlayers, permission = permission) {
		performs(action)
	}
	
	command.register()
	return command
}
