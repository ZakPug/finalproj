//Zakwan Berlin
//Final Project
//Find a treasure in a large home
//south, west, down, east, search is the winning path (from living room)

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Class representing the Player
class Player {
    private String name;
    private int health;
    private boolean hasTreasure;

    // Constructor for Player class
    public Player(String name, int health) {
        this.name = name;
        this.health = health;
        this.hasTreasure = false;
    }

    // Getter for the player's name
    public String getName() {
        return name;
    }

    // Getter for the player's health
    public int getHealth() {
        return health;
    }

    // Setter for the player's health
    public void setHealth(int newHealth) {
        health = newHealth;
    }

    // Check if the player has found treasure
    public boolean hasFoundTreasure() {
        return hasTreasure;
    }

    // Set that the player has found treasure
    public void foundTreasure() {
        hasTreasure = true;
    }
}

// Class representing a Room
class Room {
    private String name;
    private String description;
    private boolean hasTreasure;
    private Map<String, Room> connections;

    // Constructor for Room class
    public Room(String name, String description, boolean hasTreasure) {
        this.name = name;
        this.description = description;
        this.hasTreasure = hasTreasure;
        this.connections = new HashMap<>();
    }

    // Getter for the room's name
    public String getName() {
        return name;
    }

    // Getter for the room's description
    public String getDescription() {
        return description;
    }

    // Check if the room has treasure
    public boolean hasTreasureHere() {
        return hasTreasure;
    }

    // Add a connection to another room
    public void addConnection(String direction, Room room) {
        connections.put(direction, room);
    }

    // Get the room connected in a specific direction
    public Room getConnectedRoom(String direction) {
        return connections.get(direction);
    }
}

// Class representing the Game
class Game {
    private Player player;
    private Room currentRoom;

    // Constructor for Game class
    public Game() {
        player = new Player("Player", 100);
        createRooms();
        currentRoom = livingRoom;
    }

    // Define rooms
    private Room livingRoom, kitchen, bedroom, bathroom, attic, basement, garden, library, study, diningRoom, secretPassage;

    // Initialize rooms and their connections
    private void createRooms() {
        livingRoom = new Room("Living Room", "A cozy living room with a fireplace.", false);
        kitchen = new Room("Kitchen", "A messy kitchen with pots and pans all around.", false);
        bedroom = new Room("Bedroom", "A tidy bedroom with a large bed.", false);
        bathroom = new Room("Bathroom", "A clean bathroom with a bathtub and a mirror.", false);
        attic = new Room("Attic", "An old attic filled with dust and spiderwebs.", false);
        basement = new Room("Basement", "A dark and damp basement with storage boxes.", false);
        garden = new Room("Garden", "A beautiful garden with colorful flowers.", false);
        library = new Room("Library", "A quiet library with rows of bookshelves. There appears to be an attic above the shelves, and an entrance to a basement in the distance.", false);
        study = new Room("Study", "A study room with a desk and a comfortable chair.", false);
        diningRoom = new Room("Dining Room", "A grand dining room with an elegant chandelier.", false);
        secretPassage = new Room("Secret Passage", "A mysterious passage hidden behind a bookshelf.", true); // This room has the treasure

        // Set room connections
        livingRoom.addConnection("east", kitchen);
        livingRoom.addConnection("west", diningRoom);
        livingRoom.addConnection("south", garden);
        livingRoom.addConnection("north", bedroom);

        kitchen.addConnection("west", livingRoom);

        bedroom.addConnection("south", livingRoom);
        bedroom.addConnection("west", bathroom);

        bathroom.addConnection("east", bedroom);

        attic.addConnection("down", library);

        basement.addConnection("up", library);
        basement.addConnection("east", secretPassage);

        garden.addConnection("north", livingRoom);
        garden.addConnection("west", study);
        garden.addConnection("east", library);

        library.addConnection("west", garden);
        library.addConnection("down", basement);
        library.addConnection("up", attic);

        study.addConnection("west", library);
        study.addConnection("north", diningRoom);

        diningRoom.addConnection("east", livingRoom);
        diningRoom.addConnection("south", study);

        secretPassage.addConnection("west", basement);

        
    }

    // Start the game
    public void start() {
        System.out.println("Welcome to the Treasure Hunt Text Adventure Game!");
        System.out.println("You find yourself in a cozy living room.");
        System.out.println("Type 'help' to see available commands.\n");
        handleInput();
    }

    // Handle player input and game commands
    private void handleInput() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("help")) {
                showHelp();
            } else if (input.equals("look")) {
                showCurrentRoom();
            } else if (input.equals("search")) {
                searchForTreasure();
            } else if (input.equals("quit")) {
                System.out.println("Goodbye!");
                break;
            } else {
                move(input);
            }
        }
    }

    // Display available commands to the player
    private void showHelp() {
        System.out.println("Available commands:");
        System.out.println("look    - Look around the current room.");
        System.out.println("search  - Search for treasure in the current room.");
        System.out.println("quit    - Quit the game.");
        System.out.println("Directions: north, south, east, west, up, down\n");
    }

    // Display information about the current room
    private void showCurrentRoom() {
        System.out.println("You are in the " + currentRoom.getName() + ".");
        System.out.println(currentRoom.getDescription());

        if (currentRoom.hasTreasureHere() && !player.hasFoundTreasure()) {
            System.out.println("There seems to be a valuable treasure here!\n");
        }
    }

    // Move the player to a connected room in a specific direction
    private void move(String direction) {
        Room nextRoom = currentRoom.getConnectedRoom(direction);
        if (nextRoom != null) {
            currentRoom = nextRoom;
            showCurrentRoom();
        } else {
            System.out.println("You can't move in that direction.\n");
        }
    }

    // Search for treasure in the current room
    private void searchForTreasure() {
        if (currentRoom.hasTreasureHere() && !player.hasFoundTreasure()) {
            player.foundTreasure();
            System.out.println("Congratulations! You found the treasure!");
            System.out.println("You win the game!\n");
        } else {
            System.out.println("You search the room but find nothing of value.\n");
        }
    }
}

// Main class to run the game
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
