
# Scrabble Book Game Project (Server Side)

## Introduction
Welcome to the Scrabble Book Game Project! This project is designed to showcase various programming concepts, including server-client architecture, data structures, algorithms, and parallel programming. By building the Scrabble Book game from scratch, we aim to deepen our understanding of these concepts and create a fully functional game system.

## General Description
The Scrabble Book Game is a variation of the classic Scrabble game, where players form words on a game board to earn points. However, in this version, only words found in selected books are considered legal. The game involves drawing tiles, forming words, and strategically placing them on the board to maximize points.

## Components
- **Tile Class**: Represents a letter tile with a score. Immutable objects ensure consistency.
- **Bag Class**: Manages a collection of tiles with quantities for each letter. Implements the Singleton pattern.
- **Word Class**: Represents a word placement on the game board, including tiles, position, and orientation.
- **Board Class**: Manages the game board state and tile placements. Implements methods for checking word legality and calculating scores.

## Gameplay Rules
1. Players draw tiles randomly from the bag to start the game.
2. Each player takes turns forming words on the game board.
3. Words must be legal and found in selected books.
4. Bonus squares on the board can double or triple the word or letter score.
5. The game ends after a set number of rounds.

## Project Structure
The project is structured into different components, each responsible for specific functionalities:
- **Tile**: Represents letter tiles with scores.
- **Bag**: Manages the collection of tiles and ensures fair distribution.
- **Word**: Handles word placements on the game board.
- **Board**: Manages the game board state and gameplay mechanics.
![image](https://github.com/Shariv23/Book-Scrabble/assets/128408255/8b623612-bda8-4116-a38e-2597652c1eac)


## Credits
- Developed by Omer Sharivker
- Project supervised by Dr. Eliyahu Halaschi



