package walkmc.instructor

/**
 * Represents a instructor error, this is, when a required argument
 * is not present, or when a validate is not true, or when a `error` function
 * of the [Argumentable] interface is called.
 */
class InstructorError(message: String) : Exception(message)

/**
 * Throws a [InstructorError] with the specified message.
 */
fun Argumentable.fail(message: String): Nothing = throw InstructorError(message)