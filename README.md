# Shiba Jump

## Proposal

*Shiba Jump* is a platform game where the player progresses through a vertical screen, 
obtaining power-ups and defeating enemies in their path. The game will include:
- A home screen that allows for character selection
- The option to save the player's progress
- Player controls using the spacebar and left/right arrow keys

This game is made for entertainment purposes. I chose to create this program as I've always wanted to make a game,
and I feel that this project will be challenging and complex enough for me to learn more about Java.

## User Stories

Here are four user stories that describe features that should be added in Phase 1:
- As a user, I want to be able to enter, change, and view my username.
- As a user, I want to be able to select different characters and view the list of playable characters.
- As a user, I want to be able to check my amount of ShibaPoints (User cannot earn ShibaPoints as of now).
- As a user, I want to be able to add enemies to the list of favorite enemies.

Here are two user stories that describe features that should be added in Phase 2:
- As a user, I want to be able to save my account and game data.
- As a user, I want to be able to load my account and game data.

Here are two user stories that describe features that should be added in Phase 3:
- As a user, I want to be able to use a graphical user interface.
- As a user, I want to be able to enter the game.
- As a user, I want to be able to pause the game, and then choose to "resume" or "save and quit".
- As a user, I want to be able to remove enemy from the list of favorite enemies.

### Phase 4: Task 2

Sample log:

Wed Nov 24 23:25:35 PST 2021  
Added Evil Cat from the enemy list.  
Wed Nov 24 23:25:40 PST 2021  
Added Cat from the enemy list.  
Wed Nov 24 23:25:42 PST 2021  
Added Rat from the enemy list.  
Wed Nov 24 23:25:43 PST 2021  
Removed Rat to the enemy list.  
Wed Nov 24 23:25:46 PST 2021  
Removed Evil Cat to the enemy list.  
Wed Nov 24 23:25:51 PST 2021  
Added Evil Cat from the enemy list.  
Wed Nov 24 23:25:53 PST 2021  
Removed Evil Cat to the enemy list.  

### Phase 4: Task 3  

Possible design improvements:

- Make SJGame a field of GameWindow instead of a field of both GameScreen and HomeScreen. Then, GameScreen and 
HomeScreen can access SJGame through GameWindow. This change will ensure that HomeScreen and GameScreen have access to 
the same SJGame.
- Apply observer pattern to Account and HomeScreen, so that every time a field of Account changes, 
HomeScreen is notified and updates the info on the graphical UI.
- Instead of having a RegularEnemy list and BossEnemy list as fields of Stage, use an Enemy list (which can include
both RegularEnemy and BossEnemy).