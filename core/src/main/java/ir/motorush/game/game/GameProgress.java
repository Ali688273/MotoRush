package ir.motorush.game.game;

public class GameProgress {

    private int coins;
    private int totalRaces;
    private int completedRaces;
    private int bestPosition;

    public GameProgress() {
        coins = 0;
        totalRaces = 0;
        completedRaces = 0;
        bestPosition = 0;
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(int amount) {
        if (amount > 0) {
            coins += amount;
        }
    }

    public boolean spendCoins(int amount) {

        if (amount <= 0 || coins < amount) {
            return false;
        }

        coins -= amount;
        return true;
    }

    public int getTotalRaces() {
        return totalRaces;
    }

    public void registerRace() {
        totalRaces++;
    }

    public int getCompletedRaces() {
        return completedRaces;
    }

    public void registerCompletedRace(int position) {

        completedRaces++;

        if (bestPosition == 0 ||
                position < bestPosition) {

            bestPosition = position;
        }
    }

    public int getBestPosition() {
        return bestPosition;
    }
}
