package org.ACTW.screens;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.ACTW.*;
import org.ACTW.charactersandmobs.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents and pertains to everything related to the actual gameplay itself. Movement, placing characters,
 * obstacles, platforms, etc. onto the scene.
 *
 * @author Trevor
 * @author Alexa
 * @author William
 * @author Charlie
 */
public class GameScreen {
    private Direction curDirection;
    private boolean moving;
    private final java.util.Set<KeyCode> keysHeld = new java.util.HashSet<>();

    //Physics and movement constants
    private double velocityY = 0;
    private final double MAX_FALL_SPEED = 11.5;  // Cap for downward velocity
    private final double GRAVITY = 0.325;
    private final double SCENE_WIDTH = 400; // width of the screen

    //Scrolling, score, and chunk related data
    private int currentScore;
    private Text scoreText;
    private int chunksLoaded;

    //Scene management
    private final Pane stackPane;
    private final SceneManager sceneManager;
    private final String STAT_SCREEN_LABEL = "StatScreen";

    private final double SCROLL_THRESHOLD = 300;
    private final ArrayList<ChunkLoader> chunkLoaders = new ArrayList<>();
    private final ArrayList<ImageView> obstacleViews = new ArrayList<>();
    private final Random random = new Random();
    private final ArrayList<Obstacle> obstacles = new ArrayList<>();

    // Game state tracking
    private GameState gameState;
    private AnimationTimer timer;

    private final DoodleChar doodleChar;

    /**
     * Constructor for the GameScreen class.
     *
     * @author Trevor
     * @author Alexa
     * @author William
     * @author Charlie
     */
    public GameScreen(SceneManager sceneManager) {
        // Initialize stack pane
        this.stackPane = new Pane();

        // Set current score to 0
        this.currentScore = 0;
        this.chunksLoaded = 1;
        this.sceneManager = sceneManager;
        this.doodleChar = new DoodleChar();
        this.curDirection = Direction.RIGHT;
        this.moving = false;
    }

    /**
     * Gets the GameScreen scene.
     *
     * @return GameScreen scene
     * @author William
     */
    public Scene getScene() {
        Scene scene = new Scene(stackPane, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
        setup(scene);
        stackPane.requestFocus();
        return scene;
    }

    /**
     * Encompassing setup function, calls all helper methods.
     *
     * @param scene scene object
     * @author Trevor
     */
    private void setup(Scene scene) {
        setupBackground();
        setupInitialChunk();
        setupDoodleChar();
        setupScoreDisplay();
        setupFocus(scene);
        setupKeyHandlers(scene);
        setupAnimationTimer();
    }

    /**
     * Sets up the background image.
     *
     * @author Alexa
     * @author Trevor
     */
    private void setupBackground() {
        Image backgroundImage = new Image("newbackground.png");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitHeight(Global.SCREEN_HEIGHT);
        backgroundView.setFitWidth(Global.SCREEN_WIDTH);
        stackPane.getChildren().addFirst(backgroundView);
    }

    /**
     * Sets up the initial chunk.
     *
     * @author Trevor
     */
    private void setupInitialChunk() {
        //Chunk and obstacle structures
        ChunkLoader chunkLoader = new ChunkLoader(550);
        chunkLoader.generatePlatforms(600, DifficultyState.EASY);
        chunkLoaders.add(chunkLoader);

        for (Platform platform : chunkLoader.getPlatforms()) {
            stackPane.getChildren().add(platform.getNode());
        }
    }

    /**
     * Sets up the doodle character.
     *
     * @author Charlie
     * @author Trevor
     */
    private void setupDoodleChar() {
        addChar(stackPane);
        double x = (Global.SCREEN_WIDTH - doodleChar.getDoodleWidth()) / 2;
        double y = (Global.SCREEN_HEIGHT - doodleChar.getDoodleHeight()) / 2;
        doodleChar.setDoodlePosition(x, y);
        velocityY = -15;
        doodleChar.getDoodleView().toFront();
    }

    /**
     * Sets up the score display
     *
     * @author Trevor
     */
    private void setupScoreDisplay() {
        addScore(getCurrentScore(), stackPane);
    }

    /**
     * Sets up the focus (refocuses the screen)
     *
     * @param scene scene object
     * @author Charlie
     * @author Trevor
     */
    private void setupFocus(Scene scene) {
        scene.setOnMouseClicked(e -> stackPane.requestFocus());
        javafx.application.Platform.runLater(stackPane::requestFocus);
    }

    /**
     * Sets up key press event handlers.
     *
     * @author Trevor
     * @author Charlie
     * @author William
     * CITE: https://stackoverflow.com/questions/29962395/how-to-write-a-keylistener-for-javafx
     */
    private void setupKeyHandlers(Scene scene) {
        // key press events
        scene.setOnKeyPressed(event -> {
            keysHeld.add(event.getCode());

            // Always respond immediately to the latest directional key
            switch (event.getCode()) {
                case LEFT -> {
                    curDirection = Direction.LEFT;
                    moving = true;
                }
                case RIGHT -> {
                    curDirection = Direction.RIGHT;
                    moving = true;
                }
                case UP -> {
                    curDirection = Direction.UP;
                    moving = true;
                }
            }
        });

        // key release events
        scene.setOnKeyReleased(event -> {
            keysHeld.remove(event.getCode());

            // If no keys are still held, stop movement
            if (keysHeld.isEmpty()) {
                moving = false;
            } else {
                // Maintain direction based on remaining key presses
                if (keysHeld.contains(KeyCode.LEFT)) {
                    curDirection = Direction.LEFT;
                    moving = true;
                } else if (keysHeld.contains(KeyCode.RIGHT)) {
                    curDirection = Direction.RIGHT;
                    moving = true;
                } else if (keysHeld.contains(KeyCode.UP)) {
                    curDirection = Direction.UP;
                    moving = true;
                }
            }
        });
    }

    /**
     * Sets up the animation timer, which is the main game loop.
     *
     * @author Trevor
     * @author Alexa
     * @author William
     * @author Charlie
     * CITE: Utilized ChatGPT to understand/write certain chunks of code
     * CITE: https://www.youtube.com/watch?v=NN_JxHhJvQ0
     */
    private void setupAnimationTimer() {
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Horizontal movement
                doodleChar.updateDoodleChar(curDirection, moving);

                // Screen wrap-around
                double doodleX = doodleChar.getX();
                double doodleWidth = doodleChar.getDoodleWidth();

                if (doodleX + doodleWidth < 0) {
                    doodleChar.getDoodleView().setLayoutX(SCENE_WIDTH);
                } else if (doodleX > SCENE_WIDTH) {
                    doodleChar.getDoodleView().setLayoutX(-doodleWidth);
                }

                // Apply gravity
                velocityY += GRAVITY;
                if (velocityY > MAX_FALL_SPEED) {
                    velocityY = MAX_FALL_SPEED;
                }

                // Update game state
                if (velocityY < 0 && gameState != GameState.JUMPING) {
                    gameState = GameState.JUMPING;
                } else if (velocityY > 0 && gameState != GameState.FALLING) {
                    gameState = GameState.FALLING;
                }

                // Move doodle
                doodleChar.getDoodleView().setLayoutY(doodleChar.getDoodleView().getLayoutY() + velocityY);

                // Scroll screen if needed
                if (doodleChar.getY() < SCROLL_THRESHOLD) {
                    double scrollAmount = SCROLL_THRESHOLD - doodleChar.getY();
                    doodleChar.getDoodleView().setLayoutY(SCROLL_THRESHOLD);

                    for (ChunkLoader loader : chunkLoaders) {
                        loader.update(scrollAmount);
                    }

                    for (ImageView obstacleView : obstacleViews) {
                        obstacleView.setLayoutY(obstacleView.getLayoutY() + scrollAmount);
                    }

                    generateNewChunksIfNeeded();
                    chunksLoaded++;
                } else if (doodleChar.getY() > Global.SCREEN_HEIGHT) {
                    // Death logic
                    GameData.setCurrentScore(getCurrentScore());
                    gameState = GameState.GAME_OVER;
                    timer.stop();

                    sceneManager.removeScene(Global.STAT_SCREEN_LABEL);
                    sceneManager.addScene(Global.STAT_SCREEN_LABEL, new StatScreen(sceneManager).getScene());
                    sceneManager.activateScene(STAT_SCREEN_LABEL);
                }

                // Collect all platforms from all chunks
                ArrayList<Platform> allPlatforms = new ArrayList<>();
                for (ChunkLoader loader : chunkLoaders) {
                    allPlatforms.addAll(loader.getPlatforms());
                }

                // Check platform collisions
                velocityY = doodleChar.checkPlatformCollision(allPlatforms, velocityY);

                // Check monster collisions
                Double monsterCollisionResult = doodleChar.checkMonsterCollisionBounce(obstacleViews, obstacles, velocityY, sceneManager, STAT_SCREEN_LABEL);
                if (monsterCollisionResult == null) {
                    gameState = GameState.GAME_OVER;
                    timer.stop();
                    return;
                } else {
                    velocityY = monsterCollisionResult;
                }

                // Score and obstacle updates
                scoreText.setText("Score: " + getCurrentScore());

                for (Obstacle obstacle : obstacles) {
                    if (obstacle instanceof MovingMonster) {
                        ((MovingMonster) obstacle).updatePosition();
                    }
                }
            }
        };
        timer.start();
    }

    /**
     * Adds the doodle character to the screen.
     *
     * @param mainRoot the root pane to add the doodle to
     * @author Charlie
     */
    public void addChar(Pane mainRoot) {
        mainRoot.getChildren().add(doodleChar.getDoodleView());
    }

    /**
     * Adds and initializes the score display on the screen.
     *
     * @param score    the starting score value
     * @param mainRoot the root pane to add the score text to
     * @author Charlie
     */
    public void addScore(int score, Pane mainRoot) {
        scoreText = new Text("Score" + score);
        scoreText.setLayoutX(10);
        scoreText.setLayoutY(20);
        scoreText.setFill(Color.BLACK);
        scoreText.setFont(Font.font("Courier New", 15));
        mainRoot.getChildren().add(scoreText);
    }

    /**
     * Calculated and returns the current score based on chunks loaded
     *
     * @return the current score
     * @author Charlie
     */
    public int getCurrentScore() {
        currentScore = chunksLoaded * 3;
        return currentScore;
    }

    /**
     * Generates new chunks above the player as needed and continue to populate them with platforms and obstacles.
     *
     * @author Trevor
     * @author Alexa
     * @author Charlie
     */
    // move to ChunkLoader
    private void generateNewChunksIfNeeded() {
        int chunkHeight = 400; // Height of one vertical chunk
        int numChunksAhead = 3; // Number of chunks to generate ahead of the character

        // Determine the y-position of the topmost loaded chunk
        double currentTopY = chunkLoaders.stream()
                .mapToDouble(loader -> loader.StartY)
                .min()
                .orElse(600); // Fallback if no chunks are loaded yet

        // Calculate how high we want to generate new chunks
        double targetTopY = doodleChar.getDoodleView().getLayoutY() - (chunkHeight * numChunksAhead);


        // Keep generating chunks upward until we've filled up to the target height
        while (currentTopY > targetTopY) {
            int newStartY = (int) (currentTopY - chunkHeight - 50); // 10px gap between chunks

            // Create a new chunk and generate platforms for it
            ChunkLoader newChunk = new ChunkLoader(newStartY);
            DifficultyState difficulty = DifficultyState.fromScore(getCurrentScore());
            newChunk.generatePlatforms(chunkHeight, difficulty);
            chunkLoaders.add(newChunk);

            // Add the new platforms' visual nodes to the scene
            for (Platform p : newChunk.getPlatforms()) {
                stackPane.getChildren().add(p.getNode());
            }

            if (getCurrentScore() >= 70) {
                if (random.nextDouble() < difficulty.getMonsterSpawnChance()) {
                    int numObstacles = 1;
                    for (int i = 0; i < numObstacles; i++) {
                        Obstacle obstacle = createRandomObstacle();
                        ImageView obstacleView = new ImageView(obstacle.getCharacterImagePath());
                        obstacleView.setFitHeight(50);
                        obstacleView.setFitWidth(50);

                        if (obstacle instanceof MovingMonster) {
                            ((MovingMonster) obstacle).setImageView(obstacleView);
                        }

                        boolean placed = false;
                        while (!placed) {
                            double xPos = random.nextDouble() * (Global.SCREEN_WIDTH - 50);
                            double yPos = newStartY + random.nextDouble() * (chunkHeight - 100);

                            javafx.geometry.Bounds simulatedObstacleBounds = new javafx.geometry.BoundingBox(xPos, yPos, 50, 50);

                            boolean overlapsPlatform = false;
                            for (Platform platform : newChunk.getPlatforms()) {
                                javafx.geometry.Bounds platformBounds = platform.getNode().getBoundsInParent();

                                double platformX = platformBounds.getMinX();
                                double platformY = platformBounds.getMinY();
                                double platformW = platformBounds.getWidth();
                                double platformH = platformBounds.getHeight();

                                double obsX = simulatedObstacleBounds.getMinX();
                                double obsY = simulatedObstacleBounds.getMinY();
                                double obsW = simulatedObstacleBounds.getWidth();
                                double obsH = simulatedObstacleBounds.getHeight();

                                double buffer = 10; // buffer to allow "near but not touching"

                                boolean horizontallyClose = obsX + obsW - buffer > platformX && obsX + buffer < platformX + platformW;
                                boolean verticallyClose = obsY + obsH - buffer > platformY && obsY + buffer < platformY + platformH;

                                if (horizontallyClose && verticallyClose) {
                                    overlapsPlatform = true;
                                    break;
                                }
                            }

                            if (!overlapsPlatform) {
                                obstacleView.setLayoutX(xPos);
                                obstacleView.setLayoutY(yPos);
                                placed = true;
                            }
                        }
                        stackPane.getChildren().add(obstacleView);
                        obstacleViews.add(obstacleView);
                        obstacles.add(obstacle);
                    }
                }
            }

            // Update the reference for the next chunk's position
            currentTopY = newStartY;
        }
        doodleChar.getDoodleView().toFront();
        scoreText.toFront();
    }

    /**
     * Randomly creates a new obstacle instance.
     *
     * @return a randomly selected Obstacle object
     * @author Alexa
     * @author Charlie
     */
    private Obstacle createRandomObstacle() {
        int choice = random.nextInt(3);
        return switch (choice) {
            case 1 -> new MovingMonster();
            case 2 -> new BlackHole();
            default -> new StationaryMonster();
        };
    }
}
