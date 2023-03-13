# EasyArmorStands

Easy but advanced armor stand editor for Bukkit.

![Screenshot](https://cdn.56738.me/easyarmorstands/banner.png)

**Supported Minecraft versions:** 1.8.8 to 1.19.3

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

These permissions are provided for convenience to quickly grant all permissions in the corresponding categories.

| Permission                 | Description                                                           |
|----------------------------|-----------------------------------------------------------------------|
| `easyarmorstands.creative` | Contains all permissions in the "Creative" and "Survival" categories. |
| `easyarmorstands.survival` | Contains all permissions in the "Survival" category.                  |

For example, to allow players to use EasyArmorStands on a creative server, simply give them
the `easyarmorstands.creative` permission.

## PlotSquared integration

If [PlotSquared v6](https://www.spigotmc.org/resources/plotsquared-v6.77506/) is installed, players can only edit armor
stands on plots where they are allowed to build.

## WorldGuard integration

If [WorldGuard](https://enginehub.org/worldguard) is installed, players can only edit armor stands if they are allowed
to build.

## All permissions

To fine-tune access, you can grant or deny individual permissions.

| Permission                              | Description                                                                  | Category |
|-----------------------------------------|------------------------------------------------------------------------------|----------|
| `easyarmorstands.align`                 | Allows using `/eas align` to move an armor stand to the center of its block. | Survival |
| `easyarmorstands.edit`                  | Allows editing armor stands. Required to use this plugin.                    | Survival |
| `easyarmorstands.help`                  | Allows viewing the help menu.                                                | Survival |
| `easyarmorstands.open`                  | Allows opening the EasyArmorStands menu.                                     | Survival |
| `easyarmorstands.property.arms`         | Allows toggling armor stand arm visibility.                                  | Survival |
| `easyarmorstands.property.baseplate`    | Allows toggling armor stand base plate visibility.                           | Survival |
| `easyarmorstands.property.cantick`      | Allows toggling whether armor stand ticking is disabled (Paper only).        | Survival |
| `easyarmorstands.property.equipment`    | Allows modifying armor stand equipment.                                      | Survival |
| `easyarmorstands.property.glow`         | Allows toggling glowing armor stand outlines.                                | Survival |
| `easyarmorstands.property.gravity`      | Allows toggling gravity for an armor stand.                                  | Survival |
| `easyarmorstands.property.invulnerable` | Allows toggling armor stand invulnerability.                                 | Survival |
| `easyarmorstands.property.lock`         | Allows toggling armor stand equipment lock.                                  | Survival |
| `easyarmorstands.property.name`         | Allows editing armor stan name tags.                                         | Survival |
| `easyarmorstands.property.size`         | Allows toggling the size of an armor stand.                                  | Survival |
| `easyarmorstands.property.visible`      | Allows toggling armor stand visibility.                                      | Survival |
| `easyarmorstands.snap`                  | Allows toggling position and angle snapping and configuring the increment.   | Survival |
| `easyarmorstands.version`               | Allows viewing version information.                                          | Survival |
| `easyarmorstands.clone`                 | Allows cloning armor stands.                                                 | Creative |
| `easyarmorstands.color`                 | Allows using the color picker.                                               | Creative |
| `easyarmorstands.destroy`               | Allows destroying selected armor stands.                                     | Creative |
| `easyarmorstands.give`                  | Allows giving yourself the EasyArmorStand tool.                              | Creative |
| `easyarmorstands.history`               | Allows undoing and redoing changes.                                          | Creative |
| `easyarmorstands.spawn`                 | Allows spawning armor stands.                                                | Creative |
| `easyarmorstands.traincarts.model`      | Allows opening the TrainCarts model browser.                                 | Creative |
| `easyarmorstands.debug`                 | Allows viewing debug information.                                            |          |
| `easyarmorstands.plotsquared.bypass`    | Allows bypassing PlotSquared restrictions.                                   |          |
| `easyarmorstands.worldguard.bypass`     | Allows bypassing WorldGuard restrictions.                                    |          |
| `headdb.open`                           | Allows opening the Head Database menu.                                       |          |
