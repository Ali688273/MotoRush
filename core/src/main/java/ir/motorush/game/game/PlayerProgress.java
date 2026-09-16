package ir.motorush.game.game;

import java.util.HashSet;
import java.util.Set;

public class PlayerProgress {

    private int coins;

    private String selectedBikeId;

    private final Set<String> unlockedBikes;
    private final Set<String> completedStages;

    public PlayerProgress() {

        coins = 500;

        selectedBikeId = "starter";

        unlockedBikes =
                new HashSet<>();

        completedStages =
                new HashSet<>();

        unlockedBikes.add("starter");
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(
            int amount
    ) {

        if (amount <= 0) {
            return;
        }

        coins += amount;
    }

    public boolean spendCoins(
            int amount
    ) {

        if (amount <= 0) {
            return true;
        }

        if (coins < amount) {
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

        if (
                id == null ||
                id.trim().isEmpty()
        ) {
            return;
        }

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

        if (
                id != null &&
                !id.trim().isEmpty()
        ) {

            unlockedBikes.add(id);
        }
    }

    public boolean isStageCompleted(
            String id
    ) {

        return completedStages.contains(id);
    }

    public void completeStage(
            String id
    ) {

        if (
                id != null &&
                !id.trim().isEmpty()
        ) {

            completedStages.add(id);
        }
    }

    public Set<String> getUnlockedBikes() {
        return unlockedBikes;
    }

    public Set<String> getCompletedStages() {
        return completedStages;
    }
}
