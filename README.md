# EasyArmorStands

Easy but advanced armor stand editor for Bukkit.

[Demo video](https://youtu.be/dQZkB3mez-0)

![Screenshot](https://cdn.56738.me/easyarmorstands/banner.png)

**Supported Minecraft versions:** 1.8.8 to 1.19.4

Using [Paper](https://papermc.io/) is highly recommended. Some features like RGB name tags only work on Paper.

# Usage

Use `/eas give` to get the tool.

Right-click an armor stand to start editing it.

## Bones

<img alt="Bones" src="https://cdn.56738.me/easyarmorstands/bones.png" width="25%" align="right"/>

Right-click one of the particles to start editing a bone.
The particles are located at the end of each bone.
For example, the arms can be edited by right-clicking the hands.
You can always left-click to deselect the current bone.

The position bone is a special bone which can be used to move the whole armor stand.

## Tools

Once a bone was selected, it can be modified using one of many tools.
For example, the "rotate" tool can be activated by right-clicking one of the colored circles.
Move the cursor to edit the bone and right-click again to confirm (or left-click to abort).

![Rotation tool](https://cdn.56738.me/easyarmorstands/tool.png)

## Menu

<img alt="Menu" src="https://cdn.56738.me/easyarmorstands/menu.png" width="50%" align="right" />

The menu can be accessed by left-clicking while no bone is selected (or `/eas open`).

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
* `/eas align`: Move an armor stand to the middle of its block
* `/eas clone`: Duplicate the armor stand
* `/eas name`: Modify the name tag
  ([MiniMessage format](https://docs.advntr.dev/minimessage/format.html): `<red>Hello World`)
* `/eas lname`: Modify the name tag
  ([legacy color codes](https://minecraft.fandom.com/wiki/Formatting_codes): `&cHello World`)
* `/eas snap angle`: Toggle angle snapping (or specify an increment)
* `/eas snap move`: Toggle position snapping (or specify an increment)

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
