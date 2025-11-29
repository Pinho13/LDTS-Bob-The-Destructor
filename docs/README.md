# LDTS_T04_G04 - BOB, THE DESTRUCTOR

## com.ldtsfeup2526.bobTheDestructor.controller.Game Description

  In this project, our main focus is to develop a 2D mining game inspired by games like Minecraft and Terraria. The main persona is a miner named Bob, as the game’s name suggests. This persona starts his adventure in the upperground area, where the core progression system (upgrades and shop) are located. 
The main objective of this game is to go lower and lower in the underground to discover the terrain blocks with the most valuable resources, collect them and return to the upperground area to invest them in rewards. The game’s dynamics is strictly on exploration, resource collection and tool upgrades. As the distance from the upperground area increases, the stiffness of the subterranean layers also increases.

This project was developed by Aléxis Ramos, Pedro Tomás Teixeira, Rafael Pinho e Silva for LDTS 2024/25.

### Total features (to be split in the two below)

- **Collision System**
- **Physics System**
- **Player Movement**
- **Sprite Loader**
- **Terrain Generator**
- **Destruction System**
- **Main Menu**
- Screen Resizer
- Animation Manager
- Collectible System
- Gadget System
- Upgrade System
- Shop System
- Particle System
- Ore System
- GUI
- Top score
- Tile Visibility System

### Implemented features

### Planned features

### Design

#### SCREENS AND FLOW CONTROL SHOULD BE SWITCHABLE WITHOUT SCATTERED IF/SWITCH LOGIC

**Problem in Context**

We needed to support different application screens (e.g., gameplay, menus) and switch between them at runtime. Early drafts risked centralizing control via `if/switch` statements in `Game`, mixing rendering coordination with screen-specific logic, which violates the Single Responsibility Principle and makes transitions brittle.

Relevant prior code (pre-pattern idea): manual calls from the main loop to different drawing routines coupled in `Game` would have led to conditionals deciding which screen to update/draw. We avoided committing that version, and instead structured screens from the start via a dedicated abstraction.

**The Pattern**

We applied the State pattern. Each screen is a distinct state encapsulating its own model, viewer, and controller. `Game` holds a reference to the current `State<?>` and simply delegates to it. Switching screens becomes replacing the state instance.

This fits because screens exhibit stateful behavior with uniform operations (`update(gui)`), and transitions are well expressed as state replacements without conditionals.

**Implementation**

- Core abstraction: [`states/State.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/states/State.java#L11-L33). It holds the screen model, a `Controller<T>`, and a `ScreenViewer<T>`, created via factory methods.
- Game loop delegation: [`Game.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/Game.java#L43-L61) calls `state.update(gui)` every frame and exposes `setState(...)` for transitions ([`Game.java#setState`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/Game.java#L65-L67)).
- Concrete states (example placeholder in package `states.game`): a `GameState` links the `Scene` model with its screen viewer and controller.

**Consequences**

Benefits:
- Clean separation of concerns between the main loop and screen-specific logic.
- Adding a new screen is localized: implement a new `State<T>` subclass and its viewer/controller.
- Transitions are explicit and testable via `Game.setState(...)`.

Liabilities:
- More types and indirection; initial boilerplate for each new screen.

#### RENDERING BEHAVIOR FOR DIFFERENT ELEMENTS SHOULD BE EXTENSIBLE WITHOUT MODIFYING A CENTRAL RENDERER

**Problem in Context**

As we introduced multiple renderable elements (player, tiles, decor, etc.), a single monolithic renderer would accumulate `if/switch` chains to handle each type, making it hard to add new visuals and violating Open/Closed Principle.

**The Pattern**

We applied the Strategy pattern for drawing. Each drawable element has its own viewer strategy implementing a common interface. Screens compose these strategies to render the model.

**Implementation**

- Strategy interface: [`view/elements/ElementViewer.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/view/elements/ElementViewer.java#L6-L8).
- Strategy composition/provider: [`view/ViewerProvider.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/view/ViewerProvider.java#L7-L16) instantiates and exposes concrete viewers (e.g., `PlayerViewer`).
- Example concrete strategy: [`view/elements/PlayerViewer.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/view/elements/PlayerViewer.java).
- Screen-level composition: `ScreenViewer<T>` (see `view/screens`) aggregates the strategies to draw a complete screen.

**Consequences**

Benefits:
- New renderable types can be added by creating a new `ElementViewer` implementation; existing code stays closed to modification.
- Testability improves by isolating drawing logic per element.

Liabilities:
- Slight increase in the number of classes and indirection through the provider.

#### WE MUST DECOUPLE THE GAME VIEW FROM THE LANTERNA BACKEND AND CENTRALIZE SCREEN CREATION

**Problem in Context**

Directly using Lanterna APIs in game code tightly couples rendering to a specific terminal library and spreads configuration details (resolution, font, title) across the codebase. This hampers portability and makes switching or configuring the backend harder.

**The Pattern**

We combined Adapter and Factory patterns:
- Adapter: define a minimal `GUI` interface for drawing operations, with `GUILanterna` adapting Lanterna’s `Screen` to that interface.
- Factory: centralize creation/configuration of the `Screen` via a `ScreenCreator` (implemented by `LanternaScreenCreator`).

**Implementation**

- Adapter (`GUILanterna`): wraps Lanterna and implements drawing methods; see [`gui/GUILanterna.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/gui/GUILanterna.java#L19-L81). Notable operations include `drawPixel`, `clear`, `refresh`, and `close`.
- Factory (`ScreenCreator`): interface to build a configured Lanterna `Screen`; see [`gui/ScreenCreator.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/gui/ScreenCreator.java#L11-L13). `GUILanterna` delegates actual creation to this factory in `createScreen(...)`.
- Integration in boot: `Game` builds `GUILanterna` with resolution, pixel size, and title; see [`Game.java` constructor](../src/main/java/com/ldtsfeup2526/bobTheDestructor/Game.java#L26-L31).

**Consequences**

Benefits:
- The view layer uses a stable `GUI` API and is decoupled from Lanterna specifics.
- Screen creation details (resolution, font size, window title, KeyListener wiring) are centralized, easing configuration and testing.
- Future backends can be introduced by implementing `GUI` and a matching `ScreenCreator`.

Liabilities:
- Additional abstraction layers; must ensure adapters stay thin and efficient.

#### INPUT HANDLING SHOULD MAP KEY EVENTS TO HIGH-LEVEL ACTIONS AND SUPPORT SINGLE-SHOT SEMANTICS

**Problem in Context**

Raw key events (press/release) are noisy and platform-dependent. Game logic should operate on semantic actions (e.g., `UP`, `JUMP`, `SELECT`) and avoid repeated triggers for single-shot actions while a key is held. Early designs risked scattering key-code checks and debouncing across controllers.

**The Pattern**

We leveraged the Observer style provided by Java AWT (`KeyListener`) to receive events, and applied a small parsing layer that behaves like a Command/Interpreter for inputs: `ActionParser` translates key codes into domain actions and coordinates one-shot behavior using an `InputReader` buffer.

This combination cleanly separates event capture from action semantics.

**Implementation**

- Event capture buffer: [`controller/input/InputReader.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/controller/input/InputReader.java#L9-L56) implements `KeyListener`, maintaining `inputPressed` and `inputFinished` lists; `updateInputPressed()` prunes consumed inputs each frame.
- Parsing to actions: [`controller/input/ActionParser.java`](../src/main/java/com/ldtsfeup2526/bobTheDestructor/controller/input/ActionParser.java#L8-L59) maps key codes to `Action` values and marks single-shot actions (`SPACE`, `ENTER`, `ESCAPE`) as finished.
- Wiring in boot: `Game` creates the parser and passes its `InputReader` into the GUI, so the same listener receives events; see [`Game.java` boot](../src/main/java/com/ldtsfeup2526/bobTheDestructor/Game.java#L26-L31).

**Consequences**

Benefits:
- Clear separation between event capture and semantic actions.
- One-shot actions are handled uniformly; held keys don’t cause repeated triggers unless intended.
- Controllers can depend on `Action` lists without caring about AWT details.

Liabilities:
- Requires disciplined use of `inputFinished` to avoid starving inputs; controllers must mark single-shot actions appropriately (the parser handles main cases already).

#### Singletons...


### Known code smells

### Testing

### Self-Evaluation