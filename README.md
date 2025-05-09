# Snake and Ladders Game

A Java implementation of the classic Snake and Ladders board game with additional features like different snake types and health mechanics.

## Features

- Customizable grid size (randomly generated)
- Three types of snakes:
  - **Normal snakes**: Move player to tail position
  - **Kind snakes**: Move player to tail position (special rules)
  - **Wild snakes**: Move player to tail position and reduce health
- Health system (player starts with 3 lives)
- Dice with 9 possible moves:
  - Move left/right/up/down (1 or 2 spaces)
  - Health boost (restores 1 health)
- Colorful console interface
- Win condition: Reach the top-right corner ($)
- Lose condition: Health reaches 0

## How to Play

1. Run the `Main` class
2. The game will generate a random grid size (up to 15x15)
3. Follow the on-screen instructions:
   - Press any key + Enter to roll the dice
   - Enter `0` to exit the game
4. Try to reach the `$` symbol at the top-right corner
5. Avoid losing all your health points

## Game Symbols

| Symbol | Meaning              | Color  |
|--------|----------------------|--------|
| P      | Player               | Purple |
| $      | Winning position     | Blue   |
| *      | Starting position    | Blue   |
| sN     | Normal snake head    | Yellow |
| dN     | Normal snake tail    | Yellow |
| SN     | Wild snake head      | Red    |
| DN     | Wild snake tail      | Red    |
| lN     | Kind snake head      | Green  |
| rN     | Kind snake tail      | Green  |

(N represents the snake number)

## Classes Overview

- `Main`: Entry point
- `Grid`: Manages game state and logic
- `Graphic`: Handles game visualization
- `Player`: Tracks player position and health
- `Snake`/`NormalSnake`/`KindSnake`/`WildSnake`: Snake implementations
- `Dice`: Possible move directions
- `RandomHelper`: Random number generator
