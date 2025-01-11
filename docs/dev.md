# DEV MANUAL

Welcome to the dev manual for Cascadia, here you will find more details about the structures and specifities of our Cascadia version.

After the beta meeting we were told the game wasn't separated into differents objects enought, and a few things were too heavily hard coded and not easy enought to modify or upgrade in the future, and lastly we were told our interface with the player was extremely ugly.

Knowing this, we took the harsh decision to entirly revisit our code, we remade the whole data structure with everything being divided in many more differents objects, the game data was entirely cut from the graphics and the interactions with the players. this means that we have 2 differents package, one for the game data divided into numerous objects, which are easy to maintain and upgrade, and another one for the interaction with the player, itself divided in differents objects for each different view and ways to interact with the player.

Respectfully,
the dev team.