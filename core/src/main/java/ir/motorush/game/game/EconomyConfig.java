package ir.motorush.game.game;

public final class EconomyConfig {

    private EconomyConfig() {
    }

    public static final int STARTING_COINS = 500;

    public static final int MIN_RACE_REWARD = 120;

    public static final int FIRST_PLACE_REWARD = 300;
    public static final int SECOND_PLACE_REWARD = 240;
    public static final int THIRD_PLACE_REWARD = 190;
    public static final int OTHER_PLACE_REWARD = 150;

    public static final int COIN_REWARD_PER_COLLECTED_COIN = 5;

    public static final int SPEED_UPGRADE_BASE_COST = 100;
    public static final int HANDLING_UPGRADE_BASE_COST = 100;
    public static final int NITRO_UPGRADE_BASE_COST = 120;

    public static final int MAX_UPGRADE_LEVEL = 10;

    public static int getUpgradeCost(
            UpgradeType type,
            int currentLevel
    ) {

        int baseCost;

        switch (type) {

            case SPEED:
                baseCost =
                        SPEED_UPGRADE_BASE_COST;
                break;

            case HANDLING:
                baseCost =
                        HANDLING_UPGRADE_BASE_COST;
                break;

            case NITRO:
                baseCost =
                        NITRO_UPGRADE_BASE_COST;
                break;

            default:
                baseCost = 100;
                break;
        }

        return baseCost
                + Math.max(
                        0,
                        currentLevel - 1
                ) * baseCost;
    }
}
