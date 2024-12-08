# Table of Contents

- [Github Repository](#github-repository)
- [Introduction](#introduction)
    - [Project Goals](#project-goals)
    - [Project Structure](#project-structure)
- [Compilation Instructions](#compilation-instructions)
- [Implemented and Working Properly](#implemented-and-working-properly)
    - [Frontend](#frontend)
    - [Backend](#backend)
- [Implemented but Not Working Properly](#implemented-but-not-working-properly)
- [Features Not Implemented](#features-not-implemented)
- [New Java Classes](#new-java-classes)
- [Modified Java Classes](#modified-java-classes)
- [Deleted Java Classes](#deleted-java-classes)
- [Unexpected Problems](#unexpected-problems)

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

This section details the features that have been successfully implemented in both the frontend and backend of the project. Each feature is explained by outlining the <b>original version</b>, the <b>shortcomings of that approach</b>, <b>reasons why the modifications enhance the overall project</b> and the <b>new implementation</b>.

## Frontend
<style>
table, th, td {
  border:1px solid black;
}
</style>

### **1. Main Menu**
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

### **2. Double Player Option**
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
    - Limiting the game to single player mode restricted the potential audience and replayability. <br> 
    - Catering to different player preferences accommodates both solo and group gaming sessions.
    </td>
    <td>
    Players can now choose between single player or double player modes before starting the game.
    </td>
  </tr>
</table>

### **3. Game Instruction**
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
    - Players are unaware of game objectives and controls, leading to a confusion and         frustration.<br> 
    - Offering clear instructions ensures they know what to expect when gameplay begins.
    </td>
    <td>
    Instructions are now easily visible on game screen.
    </td>
  </tr>
</table>

### **4. Scrolling Backgrounds**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
    Static backgrounds
    </td>
    <td>
    - Static backgrounds can make the game feel monotonous and less immersive. <br>
    - Improving visual appeal and immersion is necessary to keep players engaged and provides a more lively gaming environment.
    </td>
    <td>
    A visually appealing scrolling background has been implemented.
    </td>
  </tr>
</table>

### **5. Automated Fire Projectile without Key Press**
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
    - Requiring repetitive key presses could be cumbersome during intense gameplay. <br>
    - Reducing the need for repetitive key presses allows players to focus more on movement and strategy rather than repeatedly pressing a key.
    </td>
    <td> 
    The fire mechanism has been automated to ensure continuous firing without extra input.
    </td>
  </tr>
</table>

### **6. Player(s) Horizontal Movement**
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
    - Restricting movement to one axis limits gameplay complexity and player control. <br>
    - Ensuring responsive controls enhances gameplay fluidity and player satisfaction by providing seamless interaction with the game environment.
    </td>
    <td>
    The player characters can now move smoothly and intuitively along the horizontal axis.
    </td>
  </tr>
</table>


### **7. Pause Option**
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
    - Inability to pause can disrupt the gaming experience, especially during unforeseen interruptions. <br>
    - Allowing players to pause the game allow players with more control over their gaming experience and the flexibility.
    </td>
    <td>
    The game can now be paused at any time during play using a keyboard shortcut or by clicking on the settings icon, then resume gameplay at their own pace.
    </td>
  </tr>
</table>

### **9. Audio Integration**
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
    - Absence of audio fail to provide essential feedback to player actions.<br>
    - Incorporating audio makes the gameplay more engaging and enjoyable for players. 
    </td>
    <td>
    - Audio elements, such as background music, sound effects, and in-game notifications have been successfully integrated. <br>
    - All the logics are handled by <a href="src/main/java/com/example/demo/manager/AudioManager.java">AudioManager</a>.
    </td>
  </tr>
</table>

### **10.Visual Effects**
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
    - Lack of visual effects can make the game feel unresponsive and less exciting. <br>
    - Enhancing visual feedback and effects provides satisfying visual cues that enrich the player's experience.
    </td>
    <td>
    Visual effects, including explosions, player actions, and enemy interactions, have been implemented.
    </td>
  </tr>
</table>

### **11. Creation of Playable Levels**
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
    - Limited levels reduce the game's replayability and can lead to player boredom. <br>
    - Offering diverse and progressively challenging levels provides a continuous sense of progression and achievement for players.
    </td>
    <td>
    - Two additional playable levels have been created <br>
    - Each with increasing difficulty, distinct environments, and unique challenges.
    </td>
  </tr>
</table>

### **12. Game Over Screen**
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
    - Without options to retry or navigate away, players have limited control after failing, which can lead to frustration. <br>
    - Providing actionable options helps players understand their progress and decide on their next steps.
    </td>
    <td>
    A Game Over screen has been developed to inform players when they have failed a level. 
    </td>
  </tr>
</table>

### **13. Win Screen**
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
    - Without options to continue, players may feel the game ends abruptly without clear next steps. Celebrating player achievements reinforces positive gameplay experiences and motivates continued engagement with the game.
    </td>
    <td>
    A Win screen has been developed to inform players when they have won the game.  
    </td>
  </tr>
</table>

## Backend

### **1. Improved Animation Handling**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
    UI updates were managed using <code>Timeline</code>, where keyframes were scheduled at fixed intervals.
    </td>
    <td>      
    - Fixed intervals can lead to frame drops or uneven animation timing, especially in fast-paced games. <br>
    - A more dynamic approach is needed to ensure smoother updates, regardless of the system's frame rate.
    </td>
    <td>
    Switched to <code>AnimationTimer</code>, which provides a continuous game loop that updates based on system time. All these logics are handled by <a href="src/main/java/com/example/demo/manager/GameLoopManager.java">GameLoopManager</a>
    </td>
  </tr>
</table>

### **2. Remove Out-of-Bound Actors**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
      Actors such as projectiles or enemies continued to exist even after moving off-screen.
    </td>
    <td>      
    - Allowing out-of-bound actors to persist unnecessarily increases memory usage and computational overhead.<br>
    - Removing them improves game performance and ensures efficient resource utilization. <br>
    - It also prevents potential logic errors involving out-of-bound actors.
    </td>
    <td>
    Introduced logic to remove actors once they move outside the visible screen area.
    </td>
  </tr>
</table>

### **3. Reduce actor bounding box for less sensitive collision detection**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
      Collision detection used the full bounding box of actors, which often resulted in overly sensitive collision responses.
    </td>
    <td>      
      - Using the full bounding box for collision detection does not account for the actual shape or movement of actors, leading to false-positive collisions. <br>
      - Reducing the bounding box size allows for more accurate and fair collision detection.
    </td>
    <td>
      Reduced the collision bounding box size by applying a shrink factor to actor dimensions.
    </td>
  </tr>
</table>

### **4. Centralized State Management**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
      Each level (e.g., LevelOne, LevelTwo) independently managed its logic, including level transitions, enemy spawning, and UI initialization. 
    </td>
    <td>      
      - Duplicated logic across levels made it challenging to scale or modify game behavior consistently. <br>
      - A centralized system simplifies transitions, improves code reusability, and enables scalable design.
    </td>
    <td>
      A `StateManager` class was introduced, leveraging the <strong>State Pattern</strong> to centralize transitions and lifecycle management. More information please kindly visit <a href="#comexampledemostate">this section</a>.
    </td>
  </tr>
</table>

### **5. Improved Actor and Level Creation with Factory Pattern**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
      Actors and levels were created manually in each class with hardcoded configurations.
    </td>
    <td>
      - Hardcoding actor and level creation increased redundancy and reduced scalability.<br>
      - Introducing a centralized factory allows dynamic creation of actors and levels with consistent configurations, enabling easy customization and future extensibility.
    </td>
    <td>
      - Leveraged the <strong>Factory Pattern</strong> to create actors and levels dynamically. <br>
      - See <a href="src/main/java/com/example/demo/actor/plane/PlaneFactory.java">PlaneFactory</a>, <a href="src/main/java/com/example/demo/actor/projectile/ProjectileFactory.java">ProjectileFactory</a> and <a href="src/main/java/com/example/demo/level/LevelFactory.java">LevelFactory</a> for more details.
    </td>
  </tr>
</table>

### **6. Flexible Behavior Management with Strategy Pattern**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
      Movement and firing behaviors were hardcoded directly into specific actor classes.
    </td>
    <td>
      - Hardcoding behaviors tightly couples logic with the actor classes, reducing flexibility.<br>
      - The Strategy Pattern enables dynamic assignment and modification of behaviors at runtime, making it easier to implement diverse movement and firing styles.
    </td>
    <td>
      Leveraged the <strong>Strategy Pattern</strong> to decouple behaviors from actor classes:
      <ul>
        - <a href="#comexampledemostrategymovement">MovementStrategy</a>:  Each actor can dynamically switch between movement styles.<br>
        - <a href="#comexampledemostrategyfiring">FiringStrategy</a>:  Flexible firing patterns are assigned to actors at runtime.
      </ul>
    </td>
  </tr>
</table>


### **7. Efficient Resource Management with Cleanup Process**
<table style="width:100%">
  <tr>
    <th>Original Version</th>
    <th>Reason for Improvement</th>
    <th>New Implementation</th>
  </tr>
  <tr>
    <td>
      No explicit cleanup process was implemented when switching game states or exiting the game.
    </td>
    <td>      
      - Failing to release resources when switching states or quitting the game may result in memory leaks and degraded performance.<br>
      - A robust cleanup process ensures proper resource deallocation, improves game stability, and prevents memory leaks.
    </td>
    <td>
      Introduced a comprehensive <code>cleanup()</code> process to ensure efficient resource management. 
    </td>
  </tr>
</table>



# Implemented but Not Working Properly

**SPACE button issue**

# Features Not Implemented

**Smooth Screen Transition**

**Memento**

# New Java Classes

#### `/com/example/demo/actor`

`/com/example/demo/actor/projectile`

`/com/example/demo/actor/plane`

`/com/example/demo/actor/plane/component`

`/com/example/demo/controller`

`/com/example/demo/effect`

`/com/example/demo/handler`

`/com/example/demo/level`

`/com/example/demo/manager`

`/com/example/demo/screen`

#### `/com/example/demo/state`

#### `/com/example/demo/strategy/movement`

#### `/com/example/demo/strategy/firing`

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







