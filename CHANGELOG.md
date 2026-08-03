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

* Fixed no longer being able to use `/eas block` with groups
* Fixed the "Into Fire" advancement being awarded when obtaining the tool
