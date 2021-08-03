<a href="https://github.com/uinnn/instructor-framework">
  <img align="center" src="https://img.shields.io/static/v1?style=for-the-badge&label=author&message=uinnn&color=informational"/>
</a>
<a href="https://github.com/uinnn/instructor-framework">
  <img align="center" src="https://img.shields.io/static/v1?style=for-the-badge&label=version&message=1.0.3v&color=ff69b4"/>
</a>
<a href="https://github.com/uinnn/instructor-framework">
  <img align="center" src="https://img.shields.io/static/v1?style=for-the-badge&label=maven-central&message=1.0.3&color=orange"/>
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
instructor(name = "mycommand", aliases = "mycmd", "cmd") {
  // adds a performer action to execute:
  performs {
    // sends a message to the console or player depending of the sender.
    send("Hello!")
  }
}
```

Well, this instructor thats we have created is very simple, this just performs a action, so we can shorten even more!
```kt
instructorWith(name = "mycommand", aliases = "mycmd", "cmd") {
  // in this scope, everything will be performed.
  send("Hello!")
}
```

You can modify the permission, permission message and others:
```kt
instructor(name = "mycommand", aliases = "mycmd", "cmd") {
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
instructor(name = "mycommand", aliases = "mycmd", "cmd") {
  performs {
    send("Hello!")
  }
  
  // will be executable with: /mycommand sub
  instructorWith("sub") {
    send("I am a sub command!")
  }
  
  // will be executable with: /mycommand another
  instructor("another") {
    performs {
      send("Please, use: /mycommand another sub")
    }
    
    // will be executable with: /mycommand another sub
    instructorWith("sub") {
      send("I am a sub command of another sub command!")
    }
  }
}
```

### Argumentable
Argumentable is a interface thats holds the arguments thats the command sender gived! This contains a lot of useful functions:
```kt
instructorWith(name = "mycommand", aliases = "mycmd", "cmd") {
  val double = double(0) // gets a double number in the argument 0 (/mycommand 5)
  // gets a optional double number in the argument 1 (/mycommand 5 2)
  // if the argument is not a double or is not specified, we set the variable as 0
  val optional = optionalDouble(1) ?: 0
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

### Others useful functions:
* `join` joins all arguments to a string.
* `asList` returns all arguments as list.
* `asIterable` returns all arguments as iterable.
* `asSequence` returns all arguments as sequence.
* `asFluent` returns all arguments as Fluent Iterable.
* `validate` verify if a specified boolean is true, if false, will throw a error.
* `validateNot` verify if a specified boolean is false, if true, will throw a error.
* `map` maps all arguments to the specified transformer.
* `lowercase` maps all arguments to lowercase.
* `uppercase` maps all arguments to uppercase.

### Argumentable examples:
Some examples using arguments:

```kt
instructor(name = "fly") {
  senderType = SenderType.PLAYER
  permission = "admin"
  performs {
    // gets the argument 0 as online player or yourself.
    val target = optionalPlayer(0) ?: player
    target.allowFlight = true
    send("You activated the fly of the player: ${target.name}")
  }
}
```

```kt
instructorWith(name = "example") {
  val joined = join()
  send("Gived arguments: $joined")
}
```

```kt
instructorWith(name = "list") {
  // will gets the argument 0, 1 and 2 as list.
  val rangeList = list(index = 0, finalIndex = 2)
  send("Gived arguments: $rangeList")
}
```

```kt
instructorWith(name = "up") {
  val target = optionalOfflinePlayer(0) ?: player
  // if the target is not in the data manager, the command will be stopped.
  val data = DataManager.get(target.uniqueId) ?: error("The target is not in the data manager.")
  // if the level of the target in data is not greater than 5, the command will be stopped.
  validate(data.level <= 5, "The target level is not greater than 5.")
  data.level++
}
```

```kt
instructorWith(name = "uppercase") {
  send("Gived arguments: ${uppercase()}")
}
```

```kt
instructorWith(name = "map") {
  // maps all arguments to players.
  val mapped = map { argument -> Bukkit.getPlayer(argument) }
  mapped.forEach { player ->
    player.sendMessage("Hello!")
  }
}
```

```kt
instructorWith(name = "as") {
  // will transforms all arguments to fluent iterable,
  // will limit the arguments in 5, and skips 2 arguments.
  val fluent = asFluent().limit(5).skip(2)
}
```

