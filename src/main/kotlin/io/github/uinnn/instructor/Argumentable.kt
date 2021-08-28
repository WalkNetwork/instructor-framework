package io.github.uinnn.instructor

import com.google.common.collect.FluentIterable
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.material.MaterialData

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
    optionalString(index) ?: fail(message)
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
): Boolean = optionalBoolean(index) ?: fail(message)

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
): Byte = optionalByte(index) ?: fail(message)

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
): Short = optionalShort(index) ?: fail(message)

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
): Int = optionalInt(index) ?: fail(message)

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
): Long = optionalLong(index) ?: fail(message)

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
): Float = optionalFloat(index) ?: fail(message)

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
): Double = optionalDouble(index) ?: fail(message)

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
): Player = optionalPlayer(index) ?: fail(message)

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
): OfflinePlayer = optionalOfflinePlayer(index) ?: fail(message)

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
): GameMode = optionalGamemode(index) ?: fail(message)

/**
 * Gets a optional enchantment argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalEnchantment(index: Int): Enchantment? {
  val string = optionalString(index) ?: return null
  return runCatching {
    Enchantment.getByName(string.uppercase())
  }.recoverCatching {
    Enchantment.getById(string.toInt())
  }.getOrNull()
}

/**
 * Gets a enchantment argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.enchantment(
  index: Int,
  message: String = "§cEncantamento não reconhecido no index $index"
): Enchantment = optionalEnchantment(index) ?: fail(message)

/**
 * Gets a optional world argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalWorld(index: Int): World? {
  val string = optionalString(index) ?: return null
  return Bukkit.getWorld(string)
}

/**
 * Gets a world argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.world(
  index: Int,
  message: String = "§cMundo não reconhecido no index $index"
): World = optionalWorld(index) ?: fail(message)

/**
 * Internal api to get a material by string.
 */
internal fun getMaterialOrNull(string: String): Material? {
  return runCatching {
    Material.valueOf(string.uppercase())
  }.recoverCatching {
    Material.getMaterial(string.toInt())
  }.getOrNull()
}

/**
 * Gets a optional material argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalMaterial(index: Int): Material? {
  val string = optionalString(index) ?: return null
  return getMaterialOrNull(string)
}

/**
 * Gets a material argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.material(
  index: Int,
  message: String = "§cMaterial não reconhecido no index $index"
): Material = optionalMaterial(index) ?: fail(message)

/**
 * Gets a optional material data argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalMaterialData(index: Int): MaterialData? {
  val string = optionalString(index) ?: return null
  val split = string.split(':', limit = 2)
  val material = getMaterialOrNull(split[0]) ?: return null
  return MaterialData(material, split[1].toByte())
}

/**
 * Gets a material data argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.materialData(
  index: Int,
  message: String = "§cMaterial data não reconhecido no index $index"
): MaterialData = optionalMaterialData(index) ?: fail(message)

/**
 * Gets a optional location argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalLocation(index: Int): Location? {
  val world = optionalWorld(index) ?: return null
  val x = optionalDouble(index + 1) ?: return null
  val y = optionalDouble(index + 2) ?: return null
  val z = optionalDouble(index + 3) ?: return null
  return Location(world, x, y, z, optionalFloat(index + 4) ?: 0F, optionalFloat(index + 5) ?: 0F)
}

/**
 * Gets a location argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.location(
  index: Int,
  message: String = "§cLocalização não reconhecida no index $index"
): Location = optionalLocation(index) ?: fail(message)

/**
 * Gets a optional block argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalBlock(index: Int): Block? {
  return optionalLocation(index)?.block
}

/**
 * Gets a block argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.block(
  index: Int,
  message: String = "§cBloco não reconhecida no index $index"
): Block = optionalBlock(index) ?: fail(message)

/**
 * Gets a optional entity type argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalEntityType(index: Int): EntityType? {
  val string = optionalString(index) ?: return null
  return runCatching {
    EntityType.valueOf(string.uppercase())
  }.recoverCatching {
    EntityType.fromId(string.toInt())
  }.getOrNull()
}

/**
 * Gets a entity type argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.entityType(
  index: Int,
  message: String = "§cTipo de entidade não reconhecida no index $index"
): EntityType = optionalEntityType(index) ?: fail(message)

/**
 * Gets a optional sound argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalSound(index: Int): Sound? {
  val string = optionalString(index) ?: return null
  return runCatching {
    Sound.valueOf(string.uppercase())
  }.getOrNull()
}

/**
 * Gets a sound argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.sound(
  index: Int,
  message: String = "§cTipo de som não reconhecido no index $index"
): Sound = optionalSound(index) ?: fail(message)

/**
 * Gets a optional particle argument at the specified index
 * or null if the argument not exists.
 */
fun Argumentable.optionalParticle(index: Int): Particle? {
  val string = optionalString(index) ?: return null
  return runCatching {
    Particle.valueOf(string.uppercase())
  }.recoverCatching {
    Particle.values()[string.toInt()]
  }.getOrNull()
}

/**
 * Gets a particle argument at the specified index or throws
 * a [InstructorError] if the argument not exists.
 */
fun Argumentable.particle(
  index: Int,
  message: String = "§cTipo de partícula não reconhecido no index $index"
): Particle = optionalParticle(index) ?: fail(message)

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
): Array<out String> = optionalArray(index, finalIndex) ?: fail(message)

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
): List<String> = optionalList(index, finalIndex) ?: fail(message)

/**
 * Validates thats the [valide] parameter is true, if false, a [fail] will be throw.
 */
fun Argumentable.validate(valide: Boolean, message: String = "§cValidação mal-sucedida."): Boolean =
  if (valide) true else fail(message)

/**
 * Validates thats the [valide] parameter is false, if true, a [fail] will be throw.
 */
fun Argumentable.validateNot(valide: Boolean, message: String = "§cValidação mal-sucedida."): Boolean =
  if (!valide) true else fail(message)

/**
 * Joins all arguments of this argumentable to a string.
 */
fun Argumentable.join(): String = arguments.joinToString(" ")

/**
 * Joins all arguments in range of this argumentable to a string.
 */
fun Argumentable.joinInRange(index: Int, finalIndex: Int = lastIndex): String {
  val list = optionalList(index, finalIndex) ?: return ""
  return list.joinToString(" ")
}

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
 * Returns a [FluentIterable] limited by the size from the arguments of this argumentable.
 */
fun Argumentable.limit(size: Int): FluentIterable<String> = asFluent().limit(size)

/**
 * Returns all arguments zipped to another next argument.
 * @see zipWithNext
 */
fun Argumentable.zipped() = asList().zipWithNext()

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