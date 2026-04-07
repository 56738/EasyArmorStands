# Changes

EasyArmorStands v3 requires Paper 26.1.1.

Bukkit and Spigot servers, as well as older server versions, are no longer supported.
This significantly reduces the maintenance effort and makes it easier to use features introduced in new versions.

EasyArmorStands v2 can still be used on Bukkit/Spigot/Paper 1.8.8 to 1.21.11.
However, most new features will only be added to EasyArmorStands v3.

# Improvements

* Added Mannequin pose property
* Added `editor.allow-entities` to config
    * If true (default), any entity can be edited (as long as you have permission to edit the entity type)
    * If false, only entities created using EasyArmorStands can be edited
    * Entities created before EasyArmorStands v3 must be registered using `/eas register ...`
* Added `/eas register ...` and `/eas unregister ...`
    * Allows marking (or unmarking) entities as created by EasyArmorStands
* Allow opening menu while an armor stand bone is selected

# Fixes

* Fixed `/eas text` not working with text which contains newlines
