<a href="https://github.com/uinnn/interface-framework">
  <img align="center" src="https://img.shields.io/static/v1?style=for-the-badge&label=author&message=uinnn&color=informational"/>
</a>
<a href="https://github.com/uinnn/interface-framework">
  <img align="center" src="https://img.shields.io/static/v1?style=for-the-badge&label=version&message=1.4.1&color=yellow"/>
</a>

# instructor-framework

### Note:
> This repository is a showcase from WalkMC Network instructor-framework.
> This will not work in your server.

## Showcase:

```kt
commandOf("fly", "voar", justPlayers = true, permission = "fly") {
  player.allowFlight = !player.allowFlight
  log(if (player.allowFlight) "§aModo de voo ativado." else "§cModo de voo desativado.") // sends message
}
```

### With arguments

```kt
commandOf("fly", "voar", justPlayers = true, permission = "fly") {
  val str = string(index = 0) // string in argument 0
  val int = int(index = 1) // int in argument 1
  val double = double(index = 2) // double in argument 2
  val optionalDouble = optionalDouble(index = 3) ?: 0 // double in argument 3 or 0 if not specified
  val player = player(index = 4) // online player in argument 4
  val offlinePlayer = offlinePlayer(index = 5) // offline player in argument 5
  // much more
}
```
