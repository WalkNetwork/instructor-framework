package walkmc.instructor

/**
 * A instructable object thats can instruct a command to execute.
 */
interface Instructable {
	
	/**
	 * The current performer action of this instructable object.
	 */
	var performer: Performer.(Instructor) -> Unit
	
	/**
	 * Sets the new performer action of this instructable object.
	 */
	fun performs(action: Performer.(Instructor) -> Unit) {
		performer = action
	}
	
	/**
	 * Returns if the type of sender thats can perform this instructable.
	 */
	var sender: Sender
	
	/**
	 * The alternates sub commands of this instructable.
	 */
	var childrens: MutableSet<ChildrenInstructor>
}

/**
 * Creates a alternate instructor of this instructable
 */
fun Instructor.sub(
	name: String,
	vararg aliases: String = emptyArray(),
	justPlayers: Boolean = false,
	permission: String? = null,
	action: Instructor.() -> Unit
): ChildrenInstructor = createChildren(
	name,
	*aliases,
	justPlayers = justPlayers,
	permission = permission,
	action = action
)

/**
 * Creates a alternate instructor with the specified
 * [action] as a performer of this instructable
 */
fun Instructor.of(
	name: String,
	vararg aliases: String = emptyArray(),
	justPlayers: Boolean = false,
	permission: String? = null,
	action: Performer.(Instructor) -> Unit
): ChildrenInstructor = createChildren(name, *aliases, justPlayers = justPlayers, permission = permission) {
	performs(action)
}

