# BrickThrower v1.2.4 Source

BrickThrower is a simple plugin for Minecraft Servers where you can throw bricks with right click. 

## Contributing
This GitHub place is where you can create issues and make pull requests. Feel free to do that, though before making major changes, please create a suggestion in our discord server here: https://discord.gg/TyZM6ePB65 and go into the BrickThrower suggestions channel and make a suggestion there. With enough upvotes we will consider it as an addition.


## Planned Updates 

### 1.3.0:
- Add damage to players when bricks are thrown at them.
- Allow older versions to use items of their choice
- Don't allow players to eat food that has our NBT data and do not allow players to place the blocks/items that has our NBT data.
- Add ability to allow console to reload config even if the option is set to false in the config.
- Improve performance of the plugin

## Note
1.3.0 will be the final content update of BrickThrower. Things will not be added after this. Any updates will be for: 
- Support for newer versions of Minecraft
- Bug patches
- Documentation

This is not due to me not having interest in the plugin, more that the plugin is complete. There would be nothing else that I would have interest into adding to the plugin as anything more would push away from the point of the plugin. I also want to work on other plugins. This plugin has been a lot of fun to work on. :)


After 1.3.0 there will be a new branch called "wip". This branch will be for any kind of update (bug patches, supporting new versions of minecraft, and documentation). Any change I make to the code will go to that branch. Any pull requests made that are accepted will also go to this branch. Once there is enough changes (which is under my discretion as to what constitutes as "enough") the wip branch will be merged into the main branch and I will release an update onto spigot (after testing of course). 

## Finished Updates

### 1.2.2:
- Added ability to also get nether_bricks instead of normal bricks by typing: /brickthrower get nether 
- Started using NBT tags to store no_craft data instead of lore.

### 1.2.3:
- Added the ability to change what the default item you get from /brickthrower get is (you can change this in the config).
- Added the ability to have a list of items you can use (you can add and remove items in the config)
- Added the ability to change the name of the item /brickthrower get gives you (you can change this in the config).
- Added new alias for the brickthrower command, brth instead of brickthrower
- Performance Improvements

### 1.2.4:
- Refactored the code for performance improvements and readability.
- Fixed many bugs. One in particular that would delete an item in your offhand when trying to throw bricks.
- Added a new config option to change how long an item stays appearing for.
- Added Support for more versions of Minecraft:
  - 1.12
  - 1.11
  - 1.10
  - 1.9
  - 1.8
