package io.github.uinnn.instructor

import com.google.common.collect.FluentIterable
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

/**
 * A argumentable object thats holds a [Array] of [String]
 * as arguments.
 */
interface Argumentable {

  /**
   * The name of the command thats holds all arguments.
   */
  val command: String

  /**
   * The arguments of this argumentable object.
   */
  var arguments: Array<out String>

  /**
   * Gets a optional string argument at the specified index
   * or null if the argument not exists.
   */
  fun optionalString(index: Int): String? = arguments.getOrNull(index)

  /**
   * Gets a string argument at the specified index or throws
   * a [InstructorError] if the argument not exists.
   */
  fun string(index: Int, message: String = "§cArgumento de texto não reconhecido no index $index"): String =
    optionalString(index) ?: error(message)
}

/**
 * Returns the size of all arguments of this argumentable.
 */
inline val Argumentable.size get() = arguments.size

/**
 * Returns the last index of all arguments of this argumentable.
 */
inline val Argumentable.lastIndex get() = arguments.lastIndex

/**
 * Returns if this argumentable contains a argument
 * at the specified index.
 */
operator fun Argumentable.contains(index: Int): Boolean = index < size

/**
 * Gets a optional boolean argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalBoolean(index: Int): Boolean? = optionalString(index)?.toBooleanStrictOrNull()

/**
 * Gets a boolean argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.boolean(
  index: Int,
  message: String = "§cArgumento true/false não reconhecido no index $index"
): Boolean = optionalBoolean(index) ?: error(message)

/**
 * Gets a optional byte argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalByte(index: Int): Byte? = optionalString(index)?.toByteOrNull()

/**
 * Gets a byte argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.byte(
  index: Int,
  message: String = "§cArgumento numérico não reconhecido no index $index"
): Byte = optionalByte(index) ?: error(message)

/**
 * Gets a optional short argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalShort(index: Int): Short? = optionalString(index)?.toShortOrNull()

/**
 * Gets a short argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.short(
  index: Int,
  message: String = "§cArgumento numérico não reconhecido no index $index"
): Short = optionalShort(index) ?: error(message)

/**
 * Gets a optional int argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalInt(index: Int): Int? = optionalString(index)?.toIntOrNull()

/**
 * Gets a int argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.int(
  index: Int,
  message: String = "§cArgumento numérico não reconhecido no index $index"
): Int = optionalInt(index) ?: error(message)

/**
 * Gets a optional long argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalLong(index: Int): Long? = optionalString(index)?.toLongOrNull()

/**
 * Gets a long argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.long(
  index: Int,
  message: String = "§cArgumento numérico não reconhecido no index $index"
): Long = optionalLong(index) ?: error(message)

/**
 * Gets a optional float argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalFloat(index: Int): Float? = optionalString(index)?.toFloatOrNull()

/**
 * Gets a float argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.float(
  index: Int,
  message: String = "§cArgumento numérico não reconhecido no index $index"
): Float = optionalFloat(index) ?: error(message)

/**
 * Gets a optional double argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalDouble(index: Int): Double? = optionalString(index)?.toDoubleOrNull()

/**
 * Gets a double argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.double(
  index: Int,
  message: String = "§cArgumento numérico não reconhecido no index $index"
): Double = optionalDouble(index) ?: error(message)

/**
 * Gets a optional online player argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalPlayer(index: Int): Player? {
  val name = optionalString(index) ?: return null
  return Bukkit.getPlayer(name)
}

/**
 * Gets a online player argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.player(
  index: Int,
  message: String = "§cJogador não reconhecido no index $index"
): Player = optionalPlayer(index) ?: error(message)

/**
 * Gets a optional offline player argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalOfflinePlayer(index: Int): OfflinePlayer? {
  val name = optionalString(index) ?: return null
  val player = Bukkit.getOfflinePlayer(name) ?: return null
  return when {
    !player.hasPlayedBefore() -> null
    else -> player
  }
}

/**
 * Gets a offline player argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.offlinePlayer(
  index: Int,
  message: String = "§cJogador não reconhecido no index $index"
): OfflinePlayer = optionalOfflinePlayer(index) ?: error(message)

/**
 * Gets a optional gamemode argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalGamemode(index: Int): GameMode? {
  val string = optionalString(index) ?: return null
  return runCatching {
    GameMode.valueOf(string.uppercase())
  }.recoverCatching {
    GameMode.getByValue(string.toInt())
  }.getOrNull()
}

/**
 * Gets a gamemode argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.gamemode(
  index: Int,
  message: String = "§cModo de jogo não reconhecido no index $index"
): GameMode = optionalGamemode(index) ?: error(message)

/**
 * Gets a optional array argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalArray(index: Int, finalIndex: Int = lastIndex): Array<out String>? {
  val array = runCatching { arguments.sliceArray(index..finalIndex) }.getOrNull()
  return when {
    array.isNullOrEmpty() -> null
    else -> array
  }
}

/**
 * Gets a array argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.array(
  index: Int,
  finalIndex: Int = lastIndex,
  message: String = "§cArray não reconhecida no index $index..$finalIndex"
): Array<out String> = optionalArray(index, finalIndex) ?: error(message)

/**
 * Gets a optional list argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalList(index: Int, finalIndex: Int = lastIndex): List<String>? {
  val list = runCatching { arguments.slice(index..finalIndex) }.getOrNull()
  return when {
    list.isNullOrEmpty() -> null
    else -> list
  }
}

/**
 * Gets a list argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.list(
  index: Int,
  finalIndex: Int = lastIndex,
  message: String = "§cLista não reconhecida no index $index..$finalIndex"
): List<String> = optionalList(index, finalIndex) ?: error(message)

/**
 * Validates thats the [valide] parameter is true, if false, a [error] will be throw.
 */
fun Argumentable.validate(valide: Boolean, message: String = "§cValidação mal-sucedida."): Boolean =
  if (valide) true else error(message)

/**
 * Validates thats the [valide] parameter is false, if true, a [error] will be throw.
 */
fun Argumentable.validateNot(valide: Boolean, message: String = "§cValidação mal-sucedida."): Boolean =
  if (!valide) true else error(message)

/**
 * Joins all arguments of this argumentable to a string.
 */
fun Argumentable.join(): String = arguments.joinToString(" ")

/**
 * Returns a [List] from the arguments of this argumentable.
 */
fun Argumentable.asList(): List<String> = arguments.asList()

/**
 * Returns a [Sequence] from the arguments of this argumentable.
 */
fun Argumentable.asSequence(): Sequence<String> = arguments.asSequence()

/**
 * Returns a [Iterable] from the arguments of this argumentable.
 */
fun Argumentable.asIterable(): Iterable<String> = arguments.asIterable()

/**
 * Returns a [FluentIterable] from the arguments of this argumentable.
 */
fun Argumentable.asFluent(): FluentIterable<String> = FluentIterable.from(asIterable())

/**
 * Maps all arguments of this argumentable to a list of mapped [T].
 */
inline fun <T> Argumentable.map(transform: (String) -> T): List<T> = arguments.map(transform)

/**
 * Maps all arguments of this argumentable to a list of all arguments in lowercase.
 */
fun Argumentable.lowercase(): List<String> = map(String::lowercase)

/**
 * Maps all arguments of this argumentable to a list of all arguments in uppercase.
 */
fun Argumentable.uppercase(): List<String> = map(String::uppercase)