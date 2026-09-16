package ir.motorush.game.game;

public class RaceManager {

    public enum RaceState {
        READY,
        COUNTDOWN,
        RUNNING,
        PAUSED,
        FINISHED,
        GAME_OVER
    }

    private RaceState state;

    private int currentLap;
    private int totalLaps;

    private int playerPosition;

    private float raceTime;

    public RaceManager(int totalLaps) {
        this.totalLaps = Math.max(1, totalLaps);

        currentLap = 1;
        playerPosition = 1;
        raceTime = 0f;

        state = RaceState.READY;
    }

    public void start() {
        state = RaceState.RUNNING;
    }

    public void pause() {
        if (state == RaceState.RUNNING) {
            state = RaceState.PAUSED;
        }
    }

    public void resume() {
        if (state == RaceState.PAUSED) {
            state = RaceState.RUNNING;
        }
    }

    public void update(float delta) {

        if (state != RaceState.RUNNING) {
            return;
        }

        raceTime += delta;
    }

    public void nextLap() {

        if (currentLap < totalLaps) {
            currentLap++;
        } else {
            state = RaceState.FINISHED;
        }
    }

    public RaceState getState() {
        return state;
    }

    public int getCurrentLap() {
        return currentLap;
    }

    public int getTotalLaps() {
        return totalLaps;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(int position) {
        playerPosition = Math.max(1, position);
    }

    public float getRaceTime() {
        return raceTime;
    }
}
