# EasyArmorStands

Armor stand and display entity editor.

![Screenshot](https://cdn.56738.me/easyarmorstands/banner2.png)

# Installation

**Supported Minecraft versions:** Spigot/Paper 1.8.8 to 1.20.1

Download the `.jar` file from [Spigot](https://www.spigotmc.org/resources/easyarmorstands.108349/)
or [Hangar](https://hangar.papermc.io/56738/EasyArmorStands) and place it in your `plugins` folder.

# Usage

Use `/eas give` and follow the instructions.

If you get stuck, refer to the detailed instructions below.

## Demo video

[![Demo video](https://i.ytimg.com/vi/dQZkB3mez-0/0.jpg)](https://youtu.be/dQZkB3mez-0)

## Controls

* Right-click: Select
* Left-click: Go back
* Left-click: Open menu
* Q: Deselect entity
* Shift: Disable snapping

## Editing entities

Hold the tool and right-click an entity to select it.

<img alt="Tools" src="https://cdn.56738.me/easyarmorstands/screenshots/tools2.png" width="50%" />

Right-click a line to use the **Move** tool.
Move your cursor by walking or looking around, then right-click to confirm your changes.
You can abort your changes and restore the previous position by left-clicking instead.

Right-click a circle to use the **Rotate** tool.
This works just like the Move tool.

Some entities let you switch between local and global mode by right-clicking anywhere else.
Local mode is affected by the rotation, for example, you can use it to move an armor stand along its arms.
Global mode uses global coordinates.

Use `/eas undo` and `/eas redo` to undo/redo your changes.
You can undo any operation performed using EasyArmorStands, as well as manually placing or destroying armor stands.

## Armor stands

Armor stands have 7 bones:

* Head
* Body
* Left arm
* Right arm
* Left leg
* Right leg
* Position

After selecting an armor stand, you will also have to select which bone you want to edit.
The Position bone is the yellow point in the middle (below the head), the other bones are the white lines.

<img alt="Bones" src="https://cdn.56738.me/easyarmorstands/screenshots/bones.png" width="50%" />

You can pick up and carry the armor stand by selecting the position bone and then selecting the **Pick up** tool in the
middle (where the lines meet).

Use `/eas name` to edit the custom name of an armor stand.
This is often used for holograms.
Format the name using [MiniMessage](https://docs.advntr.dev/minimessage/format.html).

**Hint:** If you are on 1.19.4+, you should use text displays for holograms, see below.

## Display entities

Display entities can be edited just like armor stand bones.

Additionally, they can be scaled.
Click one of the blue points in local mode to select the **Scale** tool.
Alternatively, use `/eas scale <value>` to set the scale in all three dimensions at once.

Use the menu to place an item into an item display.

Use `/eas text` to edit the text of a text display.
Format the text using [MiniMessage](https://docs.advntr.dev/minimessage/format.html).
This command also has some subcommands such as `/eas text background`, which can be used to change the background color.

**Hint:** Set the billboard mode to `center` in the menu and reset the rotation
(`/eas reset rotation`, `/eas yaw 0`, `/eas pitch 0`) to create a hologram which always looks at the player.

Use `/eas block` to set the block of a block display.
This command also supports block states like `minecraft:birch_stairs[facing=east]`.

**Hint:** Use `F3+I` to copy the block state of the block you are looking at.

Use `/eas box` to resize or move the bounding box.
Setting up a bounding box is important for client-side rendering performance.
The client will not render display entities if the bounding box is not visible on the screen.
By default, display entities do not have a bounding box, so they are always rendered.

Use `/eas brightness` to set a custom brightness (light level) of a display entity.

## Menu

<img alt="Menu" src="https://cdn.56738.me/easyarmorstands/menu.png" width="50%" align="right" />

The menu can be accessed by left-clicking while no bone or tool is selected.

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

## Commands

This list contains some important commands for features which cannot be accessed using the menu.
Use `/eas help` to see the full list of commands and their full usage.

* `/eas give`: Give yourself the editor tool
* `/eas align`: Move the selected entity to the middle of its block
* `/eas clone`: Duplicate the selected entity
* `/eas snap angle`: Toggle angle snapping (or specify an increment)
* `/eas snap move`: Toggle position snapping (or specify an increment)
* `/eas reset <property>`: Reset a property

# Permissions

There are two permission packs which contain permissions which are recommended for survival or creative mode servers.

| Permission                 | Description                                                  |
|----------------------------|--------------------------------------------------------------|
| `easyarmorstands.survival` | Allow editing armor stands and their basic properties        |
| `easyarmorstands.creative` | Allow spawning and editing armor stands and display entities |

For example, to allow players to use EasyArmorStands on a creative server, simply give them
the `easyarmorstands.creative` permission.

## PlotSquared integration

If [PlotSquared v6](https://www.spigotmc.org/resources/plotsquared-v6.77506/) is installed, players can only edit
entities on plots where they are allowed to build.

Players with the `easyarmorstands.plotsquared.bypass` permission bypass this restriction.

## WorldGuard integration

If [WorldGuard](https://enginehub.org/worldguard) is installed, players can only edit entities if they are allowed to
build.

Players with the `easyarmorstands.worldguard.bypass` permission bypass this restriction.
