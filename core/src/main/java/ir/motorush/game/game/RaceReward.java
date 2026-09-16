package ir.motorush.game.game;

public class RaceReward {

    private final int position;
    private final int collectedCoins;
    private final int reward;

    public RaceReward(
            int position,
            int collectedCoins
    ) {

        this.position = position;

        this.collectedCoins =
                Math.max(
                        0,
                        collectedCoins
                );

        this.reward =
                calculateReward(
                        position,
                        this.collectedCoins
                );
    }

    private int calculateReward(
            int position,
            int collectedCoins
    ) {

        int baseReward;

        switch (position) {

            case 1:
                baseReward =
                        EconomyConfig
                                .FIRST_PLACE_REWARD;
                break;

            case 2:
                baseReward =
                        EconomyConfig
                                .SECOND_PLACE_REWARD;
                break;

            case 3:
                baseReward =
                        EconomyConfig
                                .THIRD_PLACE_REWARD;
                break;

            default:
                baseReward =
                        EconomyConfig
                                .OTHER_PLACE_REWARD;
                break;
        }

        int coinBonus =
                collectedCoins
                        * EconomyConfig
                                .COIN_REWARD_PER_COLLECTED_COIN;

        return Math.max(
                EconomyConfig.MIN_RACE_REWARD,
                baseReward + coinBonus
        );
    }

    public int getPosition() {
        return position;
    }

    public int getCollectedCoins() {
        return collectedCoins;
    }

    public int getReward() {
        return reward;
    }
}
