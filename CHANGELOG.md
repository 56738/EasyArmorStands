# Changes

EasyArmorStands v3 requires Paper 1.21.11 or later.

Bukkit and Spigot servers, as well as older server versions, are no longer supported.
This significantly reduces the maintenance effort and makes it easier to use features introduced in new versions.

EasyArmorStands v2 can still be used on Bukkit/Spigot/Paper 1.8.8 to 1.21.11.
It will still receive bug fixes and some new features.
However, most new features will only be added to EasyArmorStands v3.

# Improvements

* Added Mannequin pose property
* Added `editor.allow-entities` to config
    * If true (default), any entity can be edited (as long as you have permission to edit the entity type)
    * If false, only entities created using EasyArmorStands can be edited
    * Entities created before EasyArmorStands v3 must be registered using `/eas register ...`
* Added `/eas register ...` and `/eas unregister ...`
    * Allows marking (or unmarking) entities as created by EasyArmorStands
* Using `/eas clone` or `/eas undo`/`redo` uses full entity data
    * Requires the `easyarmorstands.copy.entity` permission
    * The whole entity is cloned, including properties not supported by EasyArmorStands
* Restructured menu
* Added names to equipment slots in the menu
* Group selection uses a different color for selected entities
* Text inputs use dialogs instead of suggesting commands
* MiniMessage tags such as `<rainbow>` or `<gradient>` are preserved when saved in an entity

# Changes

* Removed menus from configuration
    * Menus can no longer be customized
    * Menus are built dynamically instead
    * This allows other plugins to add menu buttons without requiring config changes
* Armor stand parts can be selected immediately
    * Previously, the armor stand had to be selected before a part could be selected.
    * Now, buttons for all parts are added to the element selection.
    * The old behavior can be restored by disabling `editor.flatten-armor-stands` in the config.
    * If you use armor stands to display items/models, consider converting them to item displays using `/eas convert`
      instead of disabling this.

# API changes

* Adventure and JOML are no longer relocated.
    * They are already part of Paper.
* Renamed `Node` to `Layer`.
    * The editor consists of a stack of layers.
    * Only the top layer is considered active.
* Introduced a new concept called `Node`.
    * Nodes can be added to layers.
    * Nodes can contain buttons.
    * For example, a `SelectableElement` provides a node which is added to the element selection layer.
