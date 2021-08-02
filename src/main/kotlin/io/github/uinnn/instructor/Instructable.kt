package io.github.uinnn.instructor

typealias Performer = InstructorPerformer.(Instructor) -> Unit

/**
 * A instructable object thats can instruct a command to execute a [Performer].
 */
interface Instructable {

  /**
   * The current performer action of this instructable object.
   */
  var performer: Performer

  /**
   * Sets the new performer action of this instructable object.
   */
  fun performs(action: Performer) {
    performer = action
  }

  /**
   * Returns if the type of sender thats can perform this instructable.
   */
  var senderType: SenderType

  /**
   * The alternates sub commands of this instructable.
   */
  var alternates: HashSet<Instructor>
}

/**
 * Creates a alternate instructor of this instructable
 */
fun Instructable.instructor(
  name: String,
  vararg aliases: String = emptyArray(),
  action: InstructorAction
): Instructor {
  val instructor = Instructor(name, *aliases, action = action)
  alternates.add(instructor)
  return instructor
}

/**
 * Creates a alternate instructor with the specified
 * [action] as a performer of this instructable
 */
fun Instructable.instructorWith(
  name: String,
  vararg aliases: String = emptyArray(),
  action: Performer
): Instructor {
  val instructor = Instructor(name, *aliases) { performer = action }
  alternates.add(instructor)
  return instructor
}
