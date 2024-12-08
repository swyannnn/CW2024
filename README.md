# Content Table

# Github Repository
https://github.com/swyannnn/CW2024

# Introduction
Welcome to my Developing Maintenance Software coursework project. I am Seow Weng Yann (Student ID: 20618809), and this project focuses on creating an implementation of the classic game **1942**.

For the best experience, it is recommended to view this `README.md` file on the [GitHub website](https://github.com/swyannnn/CW2024). Navigation links have been embedded throughout the document to facilitate easy access to different sections.

## Project Goals

- **User-Friendly Design:** Develop an intuitive and hassle-free gaming experience that is easy to understand and enjoyable to play.
- **Enhanced User Experience:** Focus on improving the overall user interaction and engagement with the game.
- **Maintainable Codebase:** Refactor the existing code to ensure it is clean, well-organized, and prepared for future expansions and enhancements.

## Project Structure 

```
📦demo
 ┣ 📂actor
 ┃ ┣ 📂plane
 ┃ ┃ ┣ 📂component
 ┃ ┃ ┃ ┣ 📜HeartDisplay.java
 ┃ ┃ ┃ ┗ 📜Shield.java
 ┃ ┃ ┣ 📜BossPlane.java
 ┃ ┃ ┣ 📜EnemyPlane.java
 ┃ ┃ ┣ 📜FighterPlane.java
 ┃ ┃ ┣ 📜MultiPhaseBossPlane.java
 ┃ ┃ ┣ 📜PlaneConfig.java
 ┃ ┃ ┣ 📜PlaneFactory.java
 ┃ ┃ ┣ 📜PlaneType.java
 ┃ ┃ ┗ 📜UserPlane.java
 ┃ ┣ 📂projectile
 ┃ ┃ ┣ 📜BossProjectile.java
 ┃ ┃ ┣ 📜EnemyProjectile.java
 ┃ ┃ ┣ 📜Projectile.java
 ┃ ┃ ┣ 📜ProjectileConfig.java
 ┃ ┃ ┣ 📜ProjectileFactory.java
 ┃ ┃ ┣ 📜ProjectileType.java
 ┃ ┃ ┗ 📜UserProjectile.java
 ┃ ┣ 📜ActiveActor.java
 ┃ ┗ 📜ActorSpawner.java
 ┣ 📂effect
 ┃ ┣ 📜ExplosionEffect.java
 ┃ ┗ 📜FlickerEffect.java
 ┣ 📂handler
 ┃ ┣ 📜CollisionHandler.java
 ┃ ┣ 📜GameLoopHandler.java
 ┃ ┣ 📜HealthChangeHandler.java
 ┃ ┗ 📜InputHandler.java
 ┣ 📂level
 ┃ ┣ 📜Level001.java
 ┃ ┣ 📜Level002.java
 ┃ ┣ 📜Level003.java
 ┃ ┣ 📜Level004.java
 ┃ ┣ 📜LevelConfig.java
 ┃ ┣ 📜LevelFactory.java
 ┃ ┗ 📜LevelParent.java
 ┣ 📂manager
 ┃ ┣ 📜ActorManager.java
 ┃ ┣ 📜AudioManager.java
 ┃ ┣ 📜ButtonManager.java
 ┃ ┣ 📜CollisionManager.java
 ┃ ┣ 📜GameLoopManager.java
 ┃ ┣ 📜ImageManager.java
 ┃ ┣ 📜InputManager.java
 ┃ ┗ 📜StateManager.java
 ┣ 📂screen
 ┃ ┣ 📜LevelScreen.java
 ┃ ┣ 📜LoseScreen.java
 ┃ ┣ 📜MainMenuScreen.java
 ┃ ┣ 📜PauseScreen.java
 ┃ ┗ 📜WinScreen.java
 ┣ 📂state
 ┃ ┣ 📜GameState.java
 ┃ ┣ 📜GameStateFactory.java
 ┃ ┣ 📜LevelState.java
 ┃ ┣ 📜LoseState.java
 ┃ ┣ 📜MainMenuState.java
 ┃ ┣ 📜StateTransitioner.java
 ┃ ┗ 📜WinState.java
 ┣ 📂strategy
 ┃ ┣ 📂firing
 ┃ ┃ ┣ 📜BossFiringStrategy.java
 ┃ ┃ ┣ 📜EnemyFiringStrategy.java
 ┃ ┃ ┣ 📜FiringStrategy.java
 ┃ ┃ ┣ 📜MultiPhaseBossFiringStrategy.java
 ┃ ┃ ┗ 📜UserFiringStrategy.java
 ┃ ┗ 📂movement
 ┃ ┃ ┣ 📜BossMovementStrategy.java
 ┃ ┃ ┣ 📜EnemyMovementStrategy.java
 ┃ ┃ ┣ 📜MovementStrategy.java
 ┃ ┃ ┣ 📜MovementType.java
 ┃ ┃ ┣ 📜MultiPhaseBossMovementStrategy.java
 ┃ ┃ ┗ 📜UserMovementStrategy.java
 ┣ 📂util
 ┃ ┣ 📜GameConstant.java
 ┃ ┗ 📜PlayerKeyBindings.java
 ┣ 📜Controller.java
 ┗ 📜Main.java
```

# Compilation Instructions

# Implemented and Working Properly

This section details the features that have been successfully implemented in both the frontend and backend of the project. Each feature is explained by outlining the <b>original version</b>, the <b>shortcomings of that approach</b>, the <b>new implementation</b>, and the <b>reasons why the modifications enhance the overall project</b>.

## Frontend

**Main Menu**
<style>
table, th, td {
  border:1px solid black;
}
</style>
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement/Modification</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
    Main menu page doesn't exist
    </td>
    <td>
    No centralized hub for accessing different game functionalities, leading to a disorganized entry point.
    </td>
    <td>
    A comprehensive main menu has been developed, offering players options to start the game or select other functionalities.
    </td>
  </tr>
</table>

**Double Player Option**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
    Only single-player mode
    </td>
    <td>
    Limiting the game to single player mode restricted the potential audience and replayability. Catering to different player preferences increases the game's appeal and accommodates both solo and group gaming sessions.
    </td>
    <td>
    Players can now choose between single player or double player modes before starting the game, allowing for a tailored experience based on player preferences.
    </td>
  </tr>
</table>

**Game Instruction**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
    No instructions provided
    </td>
    <td>
    Players are unaware of game objectives and controls, leading to a confusion and frustration. Offering clear instructions helps players understand game mechanics and objectives, enhancing overall gameplay experience.
    </td>
    <td>
    Instructions are now easily visible on game screen, ensuring they know what to expect when gameplay begins.
    </td>
  </tr>
</table>

**Scrolling Backgrounds**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
    Static background
    </td>
    <td>
    Static backgrounds can make the game feel monotonous and less immersive.ias. Improving visual appeal and immersion is necessary to keep players engaged and provides a more enjoyable and lively gaming environment.
    </td>
    <td>
    A visually appealing scrolling background has been implemented.
    </td>
  </tr>
</table>

**Automated Fire Projectile without Key Press**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
    Players are required to press the spacebar to fire projectiles
    </td>
    <td>
    Requiring repetitive key presses could be cumbersome during intense gameplay. Reducing the need for repetitive key presses allows players to focus more on movement, evasion, and strategy rather than repeatedly pressing a key.
    </td>
    <td> 
    The fire mechanism has been automated to ensure continuous firing without extra input.
    </td>
  </tr>
</table>

**Player(s) Horizontal Movement**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
    Players are only allowed to move vertically
    </td>
    <td> 
    Restricting movement to one axis limits gameplay complexity and player control. Ensuring responsive controls enhances gameplay fluidity and player satisfaction by providing seamless interaction with the game environment.
    </td>
    <td>
    The player characters can now move smoothly and intuitively along the horizontal axis.
    </td>
  </tr>
</table>


**Pause Option**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
    Pausing option doesn't exist
    </td>
    <td>
    Inability to pause can disrupt the gaming experience, especially during unforeseen interruptions. Allowing players to pause the game allow players with more control over their gaming experience and the flexibility.
    </td>
    <td>
    The game can now be paused at any time during play using a keyboard shortcut or by clicking on the settings icon, and then resume gameplay at their own pace.
    </td>
  </tr>
</table>

**Audio Integration**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
    Lack of audio and sound effects.
    </td>
    <td> 
    Absence of audio can make the game feel less immersive and fail to provide essential feedback to player actions.      Incorporating audio enhances the game's atmosphere and immersion, making the gameplay more engaging and enjoyable for players. 
    </td>
    <td>
    Audio elements, such as background music, sound effects, and in-game notifications have been successfully integrated.
    </td>
  </tr>
</table>

**Visual Effects**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
    Poor visual feedback with minimal effects.
    </td>
    <td>
    Lack of visual effects can make the game feel unresponsive and less exciting.Enhancing visual feedback and effects provides satisfying visual cues that enrich the player's experience.
    </td>
    <td>
    Visual effects, including explosions, player actions, and enemy interactions, have been implemented.
    </td>
  </tr>
</table>

**Creation of Playable Levels**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
    Only two playable levels are featured<
    </td>
    <td>
    Limited levels reduce the game's replayability and can lead to player boredom. Offering diverse and progressively challenging levels provides a continuous sense of progression and achievement for players.
    </td>
    <td>
    Two additional playable levels have been created, each with increasing difficulty, distinct environments, and unique challenges.
    </td>
  </tr>
</table>

**Game Over Screen**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
    A game over icon is displayed upon losing but did not offer options to restart or return to the main menu.
    </td>
    <td>
    Without options to retry or navigate away, players have limited control after failing, which can lead to frustration. Providing actionable options helps players understand their progress and decide on their next steps.
    </td>
    <td>
    A Game Over screen has been developed to inform players when they have failed a level. This screen provides options to restart the game, return to the main menu, or exit the game.
    </td>
  </tr>
</table>

**Win Screen**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
    A win icon is displayed upon losing but did not offer options to restart or return to the main menu.
    </td>
    <td>      
    Without options to continue, players may feel the game ends abruptly without clear next steps. Celebrating player achievements reinforces positive gameplay experiences and motivates continued engagement with the game.
    </td>
    <td>
    Upon successfully completing the game, a Win Screen is displayed to congratulate players on their achievement. This screen includes options to replay the game or return to the main menu.
    </td>
  </tr>
</table>

## Backend

**Smoother UI updates**

**Remove out of bound actors**

**Reduce actor bounding box for less sensitive collision detection**

**Clean up everything before game exit**

**Do clean up transition to new scene**

# Implemented but Not Working Properly

**SPACE button issue**

# Features Not Implemented

**Smooth Screen Transition**

**Memento**

# New Java Classes

`/com/example/demo/actor`

`/com/example/demo/actor/projectile`

`/com/example/demo/actor/plane`

`/com/example/demo/actor/plane/component`

`/com/example/demo/controller`

`/com/example/demo/effect`

`/com/example/demo/handler`

`/com/example/demo/level`

`/com/example/demo/manager`

`/com/example/demo/screen`

`/com/example/demo/state`

`/com/example/demo/strategy`

`/com/example/demo/util`

# Modified Java Classes

`/com/example/demo`

`/com/example/demo/actor`

**ActiveActor**

`/com/example/demo/actor/plane`

**BossPlane** (previously known as Boss)

**EnemyPlane**

**FighterPlane**

**UserPlane**

`/com/example/demo/actor/projectile`

**Projectile**

**BossProjectile** 

**EnemyProjectile**

**UserProjectile**

`/com/example/demo/controller`

**controller**

`/com/example/demo/level`

**Level001** (previously known as LevelOne)

**Level002** (previously known as LevelTwo)

**LevelParent**

``/com/example/demo/ui`

**HeartDisplay**

**LevelView**

**Shield** (previously known as ShieldImage)

# Deleted Java Classes

**ActiveActorDestructible**

**Destructible**

**GameOverImage**

**LevelViewLevelTwo**

# Unexpected Problems

1. Tight Coupling between classes

ActorManager and GameStateManager

2. Continous Errors

Time consuming and frustration

**Boss Plane is not destroyed despite zero health**







