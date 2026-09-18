# TypeHere — Single-Line Text Editor

> A console-based single-line text editor implemented in Java, powered by a **doubly linked list** and a **stack-based undo/redo** system.

---

## 📖 Overview

**TypeHere** is a lightweight, command-line text editor that simulates core text editing operations — inserting characters, deleting characters, moving the cursor left and right, and undoing/redoing changes. It uses a **doubly linked list** internally to represent the text buffer, with the cursor (`|`) as a special node tracked within the list.

This project is an excellent demonstration of fundamental data structures:
- **Doubly Linked List** — for efficient character insertion, deletion, and cursor movement.
- **Stack** — for maintaining undo and redo history.

---

## 📁 Project Structure

```
TypeHere/
├── Main.java                  # Entry point; handles user input and command dispatch
├── SingleLineTextEditor.java  # Core editor logic (insert, delete, move, undo, redo)
├── Node.java                  # Doubly linked list node holding a single character
├── .gitignore                 # Ignores compiled .class files
└── README.md                  # Project documentation
```

---

## 🗂️ Class Descriptions

### `Node` — [`Node.java`](./Node.java)

Represents a single character node in the doubly linked list.

| Field  | Type   | Description                          |
|--------|--------|--------------------------------------|
| `data` | `char` | The character stored in this node    |
| `prev` | `Node` | Pointer to the previous node         |
| `next` | `Node` | Pointer to the next node             |

---

### `SingleLineTextEditor` — [`SingleLineTextEditor.java`](./SingleLineTextEditor.java)

The core editor class. Maintains the doubly linked list and provides all text editing operations.

| Field        | Type            | Description                                      |
|--------------|-----------------|--------------------------------------------------|
| `head`       | `Node`          | Head of the doubly linked list                   |
| `cursor`     | `Node`          | Reference to the cursor node (`\|`)              |
| `undoStack`  | `Stack<String>` | Stack storing previous states for undo           |
| `redoStack`  | `Stack<String>` | Stack storing undone states for redo             |

#### Methods

| Method                    | Description                                                                 |
|---------------------------|-----------------------------------------------------------------------------|
| `insert(String text)`     | Inserts a string of characters immediately before the cursor position       |
| `deleteChar()`            | Deletes the character immediately to the left of the cursor (backspace)     |
| `moveLeft()`              | Moves the cursor one position to the left                                   |
| `moveRight()`             | Moves the cursor one position to the right                                  |
| `undo()`                  | Reverts the editor to the previous state                                    |
| `redo()`                  | Re-applies the last undone action                                           |
| `displayText()`           | Prints the current text buffer (with cursor visible) to the console         |
| `saveState()` *(private)* | Saves the current text state to the undo stack and clears the redo stack    |
| `text2String()` *(private)*| Serializes the linked list into a `String` (used for state snapshots)      |
| `string2Text()` *(private)*| Deserializes a `String` back into the linked list (used for undo/redo)     |

---

### `Main` — [`Main.java`](./Main.java)

The application entry point. Runs an interactive command loop reading user input from the console.

---

## ⚙️ How It Works

### Text Buffer as a Doubly Linked List

Each character in the text (including the cursor `|`) is stored as a `Node` in a doubly linked list. This allows **O(1)** insertion and deletion at the cursor position without shifting elements.

```
Example: typing "Hi" with the cursor at the end

  H  <->  i  <->  |
 head            cursor
```

### Cursor Movement

Moving left or right **swaps the cursor's `|` character** with its neighboring node's character and updates the `cursor` reference — effectively shifting the cursor without restructuring the list.

```
Before moveLeft():   H <-> i <-> | <-> !
After  moveLeft():   H <-> | <-> i <-> !   (cursor reference moves left)
```

### Undo / Redo via Stacks

- **Before every mutating operation** (`insert`, `deleteChar`), the current state (serialized as a `String` via `text2String()`) is pushed onto `undoStack`, and `redoStack` is cleared.
- **`undo()`** — Pushes the current state to `redoStack`, then pops and restores the top of `undoStack`.
- **`redo()`** — Pushes the current state to `undoStack`, then pops and restores the top of `redoStack`.

---

## 🚀 Getting Started

### Prerequisites

- **Java JDK 8** or higher installed
- A terminal / command prompt

### Compile

```bash
javac Node.java SingleLineTextEditor.java Main.java
```

### Run

```bash
java Main
```

---

## 🕹️ Usage

Once running, the editor prints a prompt and waits for a single-character command:

```
Enter command (I: Insert, D: Delete, L: Left, R: Right, U: Undo, Y: Redo):
```

| Command | Action                                             |
|---------|----------------------------------------------------|
| `I`     | **Insert** — prompts for text and inserts it at the cursor |
| `D`     | **Delete** — removes the character left of the cursor (backspace) |
| `L`     | **Left** — moves the cursor one character to the left |
| `R`     | **Right** — moves the cursor one character to the right |
| `U`     | **Undo** — reverts the last insert or delete action |
| `Y`     | **Redo** — re-applies the last undone action       |

After each command, the current text buffer is displayed:

```
------------------------ Text Editor Start ------------------------
Hello, World|
------------------------- Text Editor End -------------------------
```

> The `|` character represents the cursor's current position.

---

## 💡 Example Session

```
Enter command (I: Insert, D: Delete, L: Left, R: Right, U: Undo, Y: Redo): I
Enter text to insert: Hello
------------------------ Text Editor Start ------------------------
Hello|
------------------------- Text Editor End -------------------------

Enter command: I
Enter text to insert:  World
------------------------ Text Editor Start ------------------------
Hello World|
------------------------- Text Editor End -------------------------

Enter command: L
------------------------ Text Editor Start ------------------------
Hello Worl|d
------------------------- Text Editor End -------------------------

Enter command: D
------------------------ Text Editor Start ------------------------
Hello Wor|d
------------------------- Text Editor End -------------------------

Enter command: U
------------------------ Text Editor Start ------------------------
Hello Worl|d
------------------------- Text Editor End -------------------------
```

---

## 🧠 Data Structures Used

| Structure            | Purpose                                      |
|----------------------|----------------------------------------------|
| Doubly Linked List   | Text buffer with O(1) cursor-adjacent ops    |
| Stack (x2)           | Undo history (`undoStack`) and redo history (`redoStack`) |

---

## 📌 Limitations

- **Single-line only** — does not support multi-line text or newline characters.
- **Case-sensitive commands** — commands must be entered as uppercase (`I`, `D`, `L`, `R`, `U`, `Y`).
- **No file I/O** — text is not saved to disk; all content is lost when the program exits.
- **Cursor character conflict** — using the `|` character as actual text input may cause undefined behavior since it is reserved for the cursor.

---

## 📜 License

This project is intended for educational purposes. Feel free to use and modify it for learning about data structures in Java.
