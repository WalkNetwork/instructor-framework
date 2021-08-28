<a href="https://github.com/uinnn/instructor-framework">
  <img align="center" src="https://img.shields.io/static/v1?style=for-the-badge&label=author&message=uinnn&color=informational"/>
</a>
<a href="https://github.com/uinnn/instructor-framework">
  <img align="center" src="https://img.shields.io/github/v/release/uinnn/instructor-framework?color=yellow&label=instructor-framework&style=for-the-badge"/>
</a>
<a href="https://github.com/uinnn/instructor-framework">
  <img align="center" src="https://img.shields.io/github/v/release/uinnn/instructor-framework?color=ff69b4&label=maven-central&style=for-the-badge"/>
</a>
<a href="https://github.com/uinnn/instructor-framework">
  <img align="center" src="https://img.shields.io/static/v1?style=for-the-badge&label=license&message=MIT License&color=success"/>
</a>

# instructor-framework
A useful very simple way to create instructors (commands) in spigot with kotlin.

### Objective 📝
There are numerous command libraries for Bukkit/Spigot, but they all have something in common!
Use methods like executors and annotations like records/metadata.
The `instructor-framework` eliminates all this verbosity.
With just one simple function, you already have several very useful features that make handling the command easier!

### How To Use
#### You can see the dokka documentation [here](https://uinnn.github.io/instructor-framework/)

### Creating a instructor.
To create a instructor is very easy, you just call the `instructor()` function:

```kt
command(name = "mycommand", aliases = "mycmd", "cmd") {
  // adds a performer action to execute:
  performs {
    // sends a message to the console or player depending of the sender.
    send("Hello!")
  }
}
```

Well, this instructor thats we have created is very simple, this just performs a action, so we can shorten even more!
```kt
commandWith(name = "mycommand", aliases = "mycmd", "cmd") {
  // in this scope, everything will be performed.
  send("Hello!")
}
```

You can modify the permission, permission message and others:
```kt
command(name = "mycommand", aliases = "mycmd", "cmd") {
  senderType = SenderType.PLAYER // only players can execute.
  permission = "admin" // require admin permission to execute.
  permissionMessage = "§cWithout permission!" // sets the permission message.
  performs {
    send("Hello!")
  }
}
```

### Adding alternates instructors (sub commands)
To add alternates instructors, just add a new `instructor()` or `instructorWith()` function inside of a `instructor()`:
```kt
command(name = "mycommand", aliases = "mycmd", "cmd") {
  performs {
    send("Hello!")
  }
  
  // will be executable with: /mycommand sub
  with("sub") {
    send("I am a sub command!")
  }
  
  // will be executable with: /mycommand another
  sub("another") {
    performs {
      send("Please, use: /mycommand another sub")
    }
    
    // will be executable with: /mycommand another sub
    with("sub") {
      send("I am a sub command of another sub command!")
    }
  }
}
```

### Argumentable
Argumentable is a interface thats holds the arguments thats the command sender gived! This contains a lot of useful functions:
```kt
commandWith(name = "mycommand", aliases = "mycmd", "cmd") {
  val double = double(0) // gets a double number in the argument 0 (/mycommand 5)
  // gets a optional double number in the argument 1 (/mycommand 5 2)
  // if the argument is not a double or is not specified, we set the variable as 0
  val optional = optionalDouble(1) or 0
}
```
The argumentable interface is very extensible and has a lot of predefined arguments converters!

### Supported Arguments converters:
* `optionalString` and `string` argument.
* `optionalInt` and `int` argument. 
* `optionalLong` and `long` argument.
* `optionalByte` and `byte` argument. 
* `optionalShort` and `short` argument.
* `optionalFloat` and `float` argument. 
* `optionalDouble` and `double` argument.
* `optionalBoolean` and `boolean` argument. 
* `optionalList` and `list` argument.
* `optionalArray` and `array` argument. 
* `optionalPlayer` and `player` argument.
* `optionalOfflinePlayer` and `offlineplayer` argument. 
* `optionalGamemode` and `gamemode` argument. 
* `optionalEnchantment` and `enchantment` argument. 
* `optionalWorld` and `world` argument. 
* `optionalMaterial` and `material` argument. 
* `optionalMaterialData` and `materialData` argument. 
* `optionalLocation` and `location` argument. 
* `optionalBlock` and `block` argument. 

### Others useful functions:
* `join` joins all arguments to a string.
* `joinInRange` joins all arguments in a range to a string.
* `limit` limits all arguments in a size and returns a `FluentIterable`.
* `zipped` zips all adjacents arguments in list of pairs.
* `asList` returns all arguments as list.
* `asIterable` returns all arguments as iterable.
* `asSequence` returns all arguments as sequence.
* `asFluent` returns all arguments as `FluentIterable`.
* `validate` verify if a specified boolean is true, if false, will throw a `error`.
* `validateNot` verify if a specified boolean is false, if true, will throw a `error`.
* `map` maps all arguments to the specified transformer.
* `lowercase` maps all arguments to lowercase.
* `uppercase` maps all arguments to uppercase.
* `or` returns the optional argument if present or another default value.
* `fail` will throw a `InstructorException` thats will send a message to the sender and stoping the execution of the command.

### Argumentable examples:
Some examples using arguments:

```kt
command(name = "fly") {
  senderType = SenderType.PLAYER
  permission = "admin"
  performs {
    // gets the argument 0 as online player or yourself.
    val target = optionalPlayer(0) or player
    target.allowFlight = true
    send("You activated the fly of the player: ${target.name}")
  }
}
```

```kt
commandWith(name = "example") {
  val joined = join()
  send("Gived arguments: $joined")
}
```

```kt
commandWith(name = "list") {
  // will gets the argument 0, 1 and 2 as list.
  val rangeList = list(index = 0, finalIndex = 2)
  send("Gived arguments: $rangeList")
}
```

```kt
commandWith(name = "up") {
  val target = optionalOfflinePlayer(0) or player
  // if the target is not in the data manager, the command will be stopped.
  val data = DataManager.get(target.uniqueId) ?: error("The target is not in the data manager.")
  // if the level of the target in data is not greater than 5, the command will be stopped.
  validate(data.level <= 5, "The target level is not greater than 5.")
  data.level++
}
```

```kt
commandWith(name = "uppercase") {
  send("Gived arguments: ${uppercase()}")
}
```

```kt
commandWith(name = "map") {
  // maps all arguments to players.
  val mapped = map { argument -> Bukkit.getPlayer(argument) }
  mapped.forEach { player ->
    player.sendMessage("Hello!")
  }
}
```

```kt
commandWith(name = "as") {
  // will transforms all arguments to fluent iterable,
  // will limit the arguments in 5, and skips 2 arguments.
  val fluent = asFluent().limit(5).skip(2)
  // or
  val limited = limit(5).skip(2)
}
```

```kt
commandWith(name = "joinInRange") {
  // will joins the arguments 0, 1, 2 and 3 in a string.
  val joined = joinInRange(0, 3)
}
```

```kt
commandWith(name = "new") {
  // a optional world in argument 0 or the sender's world
  val world = optionalWorld(0) or player.world
  // a required material in argument 1, can be by name, like 'grass' (will be uppercased), or by id, like '2' 
  val material = material(1)
  // a required material data in argument 2, same to the material, but this can do: 'stone:2', or '1:2'
  val data = materialData(2)
  // a required enchantment in argument 3, can be by name or id
  val enchantment = enchantment(3)
}
```

```kt
commandWith(name = "location") {
  // a required location in argument 0, this will be performed like as multi argument, for example:
  // /location world 0.5 100 50.12
  // with yaw and pitch:
  // /location world 0.5 100 50.12 40.4 3
  // is not recommended use this with another arguments.
  val location = location(0)
  
  // the block is the same thing than the location.
  val block = block(0)
}
```

## Setup for development
The `instructor-framework` is in the central maven repository. Thus making things very easy!

### Gradle Kotlin DSL
```gradle
implementation("io.github.uinnn:instructor-framework:1.2")
```

### Gradle
```gradle
implementation 'io.github.uinnn:instructor-framework:1.2'
```

### Maven
```xml
<dependency>
  <groupId>io.github.uinnn</groupId>
  <artifactId>instructor-framework</artifactId>
  <version>1.2</version>
</dependency>
```

### Final notes
The `instructor-framework` **NOT** contains the kotlin runtime needed to run this framework,
so you should implement them directly in your project.
To make your life easier, here is all the implementation of the libraries needed to run the framework:

```gradle
plugins {
  kotlin("jvm") version "1.5.30"
}

dependencies {
  implementation(kotlin("stdlib-jdk8")) // the kotlin std lib with jdk8
}
```




