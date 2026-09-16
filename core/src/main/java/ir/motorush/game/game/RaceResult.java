package ir.motorush.game.game;

public class RaceResult {

    private boolean finished;

    private int position;
    private int totalRacers;

    private int score;
    private int coins;

    private float raceTime;

    public RaceResult() {
        finished = false;

        position = 1;
        totalRacers = 1;

        score = 0;
        coins = 0;

        raceTime = 0f;
    }

    public void finish(
            int position,
            int totalRacers,
            int score,
            int coins,
            float raceTime
    ) {
        this.finished = true;

        this.position = position;
        this.totalRacers = totalRacers;

        this.score = score;
        this.coins = coins;

        this.raceTime = raceTime;
    }

    public boolean isFinished() {
        return finished;
    }

    public int getPosition() {
        return position;
    }

    public int getTotalRacers() {
        return totalRacers;
    }

    public int getScore() {
        return score;
    }

    public int getCoins() {
        return coins;
    }

    public float getRaceTime() {
        return raceTime;
    }
}
