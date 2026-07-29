# Improvements

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
