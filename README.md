# Content Table



# Github Repository
https://github.com/swyannnn/CW2024

# Introduction
Welcome to my Developing Maintenance Software coursework project. I am Seow Weng Yann (Student ID: 20618809), and this project focuses on creating an implementation of the classic game **1942**.

## Project Goals

- **User-Friendly Design:** Develop an intuitive and hassle-free gaming experience that is easy to understand and enjoyable to play.
- **Enhanced User Experience:** Focus on improving the overall user interaction and engagement with the game.
- **Maintainable Codebase:** Refactor the existing code to ensure it is clean, well-organized, and prepared for future expansions and enhancements.

## Usage

For the best experience, it is recommended to view this `README.md` file on the [GitHub website](https://github.com/swyannnn/CW2024). Navigation links have been embedded throughout the document to facilitate easy access to different sections.

# Compilation Instructions

# Implemented and Working Properly

This section details the features that have been successfully implemented in both the frontend and backend of the project. Each feature is explained by outlining the original version, the shortcomings of that approach, the new implementation, and the reasons why the modifications enhance the overall project.

## Frontend

**Main Menu**

**Double Player Option**

**Game Instruction**

**Scrolling Backgrounds**

**Automated Fire Projectile without Key Press**

**Player(s) Horizontal Movement**

**Pause Option**

**Audio Integration**

**Visual Effects**

**Creation of Playable Levels**

**Game Over Screen**

**Win Screen**

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







