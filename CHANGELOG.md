# Improvements

* Make `/eas` give the tool
    * Replaces `/eas give`
    * `/eas give` still works, but additionally shows a hint to use `/eas` instead
    * `/eas give <player>` still works like before
* Disallow editing non-persistent entities
    * Non-persistent entities are usually temporary entities created by other plugins
    * Editing them can be allowed using `editor.allow-non-persistent` in `config.yml`
* Allow using `/eas position/yaw/pitch` and `/eas scale x/y/z` with groups

# Fixes

* Fixes no longer being able to use `/eas block` with groups
* Restrict `/eas shear` to local position/rotation

# API changes

* Introduced an abstraction layer to enable adding support for other platforms.
* Plugins should use the `easyarmorstands-paper-api` dependency instead of `easyarmorstands-api`.
* Most objects can be converted from and to Paper objects using e.g. `PaperPlayer.fromNative(player)` and
  `PaperPlayer.toNative(player)`.
    * Other objects can be converted using `PaperAdapter`.
* The `EasyArmorStands` object is no longer a singleton.
    * It can be obtained by casting the EasyArmorStands plugin to `EasyArmorStandsPaperProvider`.
    * Example:
      `((EasyArmorStandsPaperProvider) Bukkit.getPluginManager().getPlugin("EasyArmorStands")).getEasyArmorStands()`
* Moved the API to a new Maven repository: `https://repo.56738.me/repository/maven-public/`
