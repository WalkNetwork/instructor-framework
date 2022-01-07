package walkmc.instructor

import walkmc.extensions.*

/**
 * Represents a manager for instructors.
 */
object InstructorManager : HashMap<String, Instructor>() {
	
	/**
	 * Register a instructor in the server.
	 */
	fun register(instructor: Instructor) {
		put(instructor.name, instructor)
		server.commandMap.register(instructor.label, instructor)
	}
	
	/**
	 * Unregister a instructor in the server.
	 */
	fun unregister(name: String) {
		remove(name)
		server.knownCommands.remove(name)
	}
}
