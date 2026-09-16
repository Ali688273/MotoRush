package ir.motorush.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Set;

public class ProgressManager {

    private static final String PREF_NAME =
            "motorush_progress";

    private static final String KEY_COINS =
            "coins";

    private static final String KEY_SELECTED_BIKE =
            "selected_bike";

    private static final String KEY_UNLOCKED =
            "unlocked_bikes";

    private static final String KEY_COMPLETED =
            "completed_stages";

    private final Preferences preferences;

    public ProgressManager() {

        preferences =
                Gdx.app.getPreferences(
                        PREF_NAME
                );
    }

    public PlayerProgress load() {

        PlayerProgress progress =
                new PlayerProgress();

        progress.addCoins(
                preferences.getInteger(
                        KEY_COINS,
                        0
                )
        );

        String selected =
                preferences.getString(
                        KEY_SELECTED_BIKE,
                        "starter"
                );

        progress.setSelectedBikeId(
                selected
        );

        String unlocked =
                preferences.getString(
                        KEY_UNLOCKED,
                        "starter"
                );

        if (!unlocked.isEmpty()) {

            String[] ids =
                    unlocked.split(",");

            for (String id : ids) {

                if (!id.trim().isEmpty()) {

                    progress.unlockBike(
                            id.trim()
                    );
                }
            }
        }

        String completed =
                preferences.getString(
                        KEY_COMPLETED,
                        ""
                );

        if (!completed.isEmpty()) {

            String[] stages =
                    completed.split(",");

            for (String stage : stages) {

                if (!stage.trim().isEmpty()) {

                    progress.completeStage(
                            stage.trim()
                    );
                }
            }
        }

        return progress;
    }

    public void save(
            PlayerProgress progress
    ) {

        preferences.putInteger(
                KEY_COINS,
                progress.getCoins()
        );

        preferences.putString(
                KEY_SELECTED_BIKE,
                progress.getSelectedBikeId()
        );

        preferences.putString(
                KEY_UNLOCKED,
                join(
                        progress.getUnlockedBikes()
                )
        );

        preferences.putString(
                KEY_COMPLETED,
                join(
                        progress.getCompletedStages()
                )
        );

        preferences.flush();
    }

    private String join(
            Set<String> values
    ) {

        StringBuilder builder =
                new StringBuilder();

        for (String value : values) {

            if (builder.length() > 0) {
                builder.append(",");
            }

            builder.append(value);
        }

        return builder.toString();
    }
}
