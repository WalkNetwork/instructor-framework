package walkmc.instructor

/**
 * @author uin
 */
class ChildrenInstructor(var parent: Instructor, name: String) : Instructor(name) {
	constructor(
		parent: Instructor,
		name: String,
		action: Instructor.() -> Unit
	) : this(parent, name) {
		apply(action)
	}
	
	constructor(
		parent: Instructor,
		name: String,
		vararg aliases: String
	) : this(parent, name) {
		this.aliases = aliases.toList()
	}
	
	constructor(
		parent: Instructor,
		name: String,
		vararg aliases: String,
		action: Instructor.() -> Unit
	) : this(parent, name, *aliases) {
		apply(action)
	}
}
