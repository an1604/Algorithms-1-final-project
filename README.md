# Algorithms-1-final-project

## Introduction

In this project, we implement and compare three search algorithms for solving "Puzzle 15" and "Puzzle 24": Breadth-First Search (BFS), Dijkstra's algorithm, and the A* algorithm. The goal is to investigate their performance in solving these puzzles and analyze the impact of different heuristic functions on the A* algorithm.

## Puzzle Definitions

### Puzzle 15
"Puzzle 15" is a classic game where the goal is to arrange the numbers from 1 to 15 in ascending order on a 4x4 board by sliding the numbers into an empty space.

### Puzzle 24
"Puzzle 24" is a variation with a 5x5 board, and the goal is to arrange the numbers from 1 to 24 in ascending order.

### Stage A: Graph Implementation
A custom graph is implemented to represent the puzzle boards. Each possible board configuration is a vertex, and two boards that can be reached by one move are connected as neighbors in the graph.

### Stage B: Board Initialization
The project allows two ways to initialize the starting board: manual input or randomly creating a board by making a legal and random number of moves from the solution board.

### Stage C: BFS Algorithm
The Breadth-First Search algorithm is implemented and used to solve both "Puzzle 15" and "Puzzle 24."

### Stage D: A* Algorithm
The A* algorithm is implemented and used to solve both puzzles with three different heuristic functions.

