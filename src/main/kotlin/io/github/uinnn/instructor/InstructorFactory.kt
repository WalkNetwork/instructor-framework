package io.github.uinnn.instructor

/**
 * Creates a new main instructor with specified name, aliases and builder action.
 */
fun command(
  name: String,
  vararg aliases: String = emptyArray(),
  action: InstructorAction
): Instructor = Instructor(name, *aliases) {
  action(this)
  register()
}

/**
 * Creates a new main instructor with specified
 * name, aliases and action as performer of this instructor.
 */
fun commandWith(
  name: String,
  vararg aliases: String = emptyArray(),
  action: Performer
): Instructor = Instructor(name, *aliases) {
  performer = action
  register()
}