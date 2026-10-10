# API changes

* Introduced an abstraction layer to enable adding support for other platforms.
* Plugins should use the `easyarmorstands-paper-api` dependency instead of `easyarmorstands-api`.
* Most objects can be converted from and to Paper objects using e.g. `PaperPlayer.fromNative(player)` and
  `PaperPlayer.toNative(player)`.
    * Other objects can be converted using `PaperAdapter`.
* The `EasyArmorStands` object can be obtained using `EasyArmorStandsPaper.getInstance()`.
* Moved the API to a new Maven repository: `https://repo.56738.me/repository/maven-public/`
