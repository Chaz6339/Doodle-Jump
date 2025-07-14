package org.ACTW;

/**
 * Enum to represent the difficulty levels in the game, each with a specific platform spacing,
 * bounce platform spawn chance, and monster spawn chance.
 *
 * @author Trevor
 * @author Alexa
 */
public enum DifficultyState {
    //Easy difficulty: closest platforms, highest bounce chance, no monsters.
    EASY(50, 0.05, 0.0),
    //Medium difficulty: moderate platform spacing, moderate bounce chance, some monsters.
    MEDIUM(70, 0.04, 0.15),
    //Hard difficulty: widest platform spacing, lowest bounce chance, frequent monsters.
    HARD(110, 0.03, 0.3);

    private final int platformSpacing;
    private final double bouncePlatformChance;
    private final double monsterSpawnChance;

    /**
     * Constructs a difficulty state with the specified parameters.
     *
     * @param platformSpacing the vertical distance between platforms
     * @param bouncePlatformChance the chance of spawning a bounce platform
     * @param monsterSpawnChance the chance of spawning a monster
     * @author Trevor
     */
    DifficultyState(int platformSpacing, double bouncePlatformChance, double monsterSpawnChance) {
        this.platformSpacing = platformSpacing;
        this.bouncePlatformChance = bouncePlatformChance;
        this.monsterSpawnChance = monsterSpawnChance;
    }

    /**
     * Returns the vertical spacing between platforms for this difficulty.
     *
     * @return the platform spacing
     * @author Trevor
     */
    public int getPlatformSpacing() {
        return platformSpacing;
    }

    /**
     * Returns the chance of a bounce platform spawning at this difficulty.
     *
     * @return the bounce platform chance (0.0 to 1.0)
     * @author Trevor
     */
    public double getBouncePlatformChance() {
        return bouncePlatformChance;
    }

    /**
     * Returns the chance of a monster spawning at this difficulty.
     *
     * @return the monster spawn chance (0.0 to 1.0)
     * @author Trevor
     */
    public double getMonsterSpawnChance() {
        return monsterSpawnChance;
    }

    /**
     * Determines the difficulty level based on the current score.
     *
     * @param score the player's current score
     * @return the corresponding DifficultyState
     * @author Trevor
     */
    public static DifficultyState fromScore(int score) {
        if (score < 5000) {
            return EASY;
        } else if (score < 10000) {
            return MEDIUM;
        } else {
            return HARD;
        }
    }
}