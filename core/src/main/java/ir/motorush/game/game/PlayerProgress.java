package ir.motorush.game.game;

import java.util.HashSet;
import java.util.Set;

public class PlayerProgress {

    private int coins;

    private String selectedBikeId;

    private final Set<String> unlockedBikes;

    private final Set<String> completedStages;

    public PlayerProgress() {

        coins = 0;

        selectedBikeId =
                "starter";

        unlockedBikes =
                new HashSet<>();

        completedStages =
                new HashSet<>();

        unlockedBikes.add(
                "starter"
        );
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(
            int amount
    ) {

        coins =
                Math.max(
                        0,
                        coins + amount
                );
    }

    public boolean spendCoins(
            int amount
    ) {

        if (
                amount <= 0
        ) {

            return true;
        }

        if (
                coins < amount
        ) {

            return false;
        }

        coins -= amount;

        return true;
    }

    public String getSelectedBikeId() {
        return selectedBikeId;
    }

    public void setSelectedBikeId(
            String id
    ) {

        selectedBikeId = id;
    }

    public boolean isBikeUnlocked(
            String id
    ) {

        return unlockedBikes.contains(id);
    }

    public void unlockBike(
            String id
    ) {

        unlockedBikes.add(id);
    }

    public boolean isStageCompleted(
            String id
    ) {

        return completedStages.contains(id);
    }

    public void completeStage(
            String id
    ) {

        completedStages.add(id);
    }

    public Set<String> getUnlockedBikes() {
        return unlockedBikes;
    }

    public Set<String> getCompletedStages() {
        return completedStages;
    }
}
