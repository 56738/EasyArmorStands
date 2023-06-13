# EasyArmorStands

Armor stand and display entity editor.

![Screenshot](https://cdn.56738.me/easyarmorstands/banner.png)

**Supported Minecraft versions:** 1.8.8 to 1.20.1

Using [Paper](https://papermc.io/) is highly recommended. Some features like RGB name tags only work on Paper.

# Usage

Use `/eas give` and follow the instructions.

## Demo video

[![Demo video](https://i.ytimg.com/vi/dQZkB3mez-0/0.jpg)](https://youtu.be/dQZkB3mez-0)

## Armor stands

Select an armor stand by right-clicking it with the tool (`/eas give`).
Then, right-click one of the bones to edit it.

* Edit the pose by clicking one of the circles.
* Move the armor stand by clicking a line.

To move the armor stand, select the *Position* bone (below the head).
Then, either click one of the lines or pick it up by clicking the middle.

Once you have selected an operation, you can move the cursor by looking or walking around.
Right-click to confirm the change or left-click to revert it.
Use `/eas set` to set it to a certain value.

## Display entities

Select an item display, a block display or a text display by right-clicking it with the tool.

* Move the entity by clicking one of the colored lines or pick it up by clicking the middle.
* Rotate it by clicking one of the circle.
* Scale it by clicking the aqua point at the end of a colored line.
* Use `/eas width` and `/eas height` to configure the bounding box.
* Use `/eas translation` to move the displayed object without moving the bounding box.

## Menu

<img alt="Menu" src="https://cdn.56738.me/easyarmorstands/menu.png" width="50%" align="right" />

The menu can be accessed by left-clicking while no bone is selected.

On the bottom left, you can edit the entity equipment slots (armor, held items).

The bottom right contains shortcut buttons to quickly select a certain bone.

Armor stand settings such as visibility can be changed using the buttons in the top right.

### Head Database integration

If [Head Database](https://www.spigotmc.org/resources/head-database.14280/) is installed and you have permission to use
it, you can click the button in the top left to open its menu.
After selecting a head, you are returned to the EasyArmorStands menu and can quickly place it into an equipment slot.

Permission: `headdb.open`

### TrainCarts integration

If [TrainCarts](https://www.spigotmc.org/resources/traincarts.39592/) is installed, a button to open the TrainCarts
model browser is added to the top left.

Permission: `easyarmorstands.traincarts.model`

# Commands

This list contains important commands for features which cannot be accessed using the menu.
Use `/eas help` to see the full list of commands and their full usage.

* `/eas give`: Give yourself the editor tool
* `/eas align`: Move the selected entity to the middle of its block
* `/eas clone`: Duplicate the selected entity
* `/eas name`: Modify the name tag
  (Supports [MiniMessage](https://docs.advntr.dev/minimessage/format.html): `<red>Hello World`)
* `/eas namevisible`: Show the name tag
* `/eas snap angle`: Toggle angle snapping (or specify an increment)
* `/eas snap move`: Toggle position snapping (or specify an increment)
* `/eas scale`: Set the scale of a display entity
* `/eas text`: Set the text of a text display entity (Supports MiniMessage)

# Permissions

The full list of permissions is visible in [permissions.yml](src/main/resources/permissions.yml).

There are two permission packs which contain permissions which are recommended for survival or creative mode servers.

| Permission                 | Description                                                  |
|----------------------------|--------------------------------------------------------------|
| `easyarmorstands.survival` | Allow editing armor stands and their basic properties        |
| `easyarmorstands.creative` | Allow spawning and editing armor stands and display entities |

For example, to allow players to use EasyArmorStands on a creative server, simply give them
the `easyarmorstands.creative` permission.

## PlotSquared integration

If [PlotSquared v6](https://www.spigotmc.org/resources/plotsquared-v6.77506/) is installed, players can only edit armor
stands on plots where they are allowed to build.

Players with the `easyarmorstands.plotsquared.bypass` permission bypass this restriction.

## WorldGuard integration

If [WorldGuard](https://enginehub.org/worldguard) is installed, players can only edit armor stands if they are allowed
to build.

Players with the `easyarmorstands.worldguard.bypass` permission bypass this restriction.
