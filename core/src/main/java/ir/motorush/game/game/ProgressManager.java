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

    private static final String KEY_SPEED_PREFIX =
            "speed_";

    private static final String KEY_HANDLING_PREFIX =
            "handling_";

    private static final String KEY_NITRO_PREFIX =
            "nitro_";

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

        int savedCoins =
                preferences.getInteger(
                        KEY_COINS,
                        EconomyConfig.STARTING_COINS
                );

        /*
         * PlayerProgress خودش 500 سکه اولیه دارد.
         * مقدار ذخیره‌شده فقط وقتی وجود داشته باشد
         * جایگزین می‌شود.
         */
        if (
                preferences.contains(
                        KEY_COINS
                )
        ) {

            progress =
                    createProgressWithCoins(
                            savedCoins
                    );
        }

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

                if (
                        !id.trim().isEmpty()
                ) {

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

                if (
                        !stage.trim().isEmpty()
                ) {

                    progress.completeStage(
                            stage.trim()
                    );
                }
            }
        }

        return progress;
    }

    private PlayerProgress createProgressWithCoins(
            int coins
    ) {

        PlayerProgress progress =
                new PlayerProgress();

        int difference =
                coins
                        - EconomyConfig.STARTING_COINS;

        if (difference > 0) {

            progress.addCoins(
                    difference
            );

        } else if (difference < 0) {

            progress.spendCoins(
                    -difference
            );
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

    public int getUpgradeLevel(
            String bikeId,
            UpgradeType type
    ) {

        String key =
                getUpgradeKey(
                        bikeId,
                        type
                );

        return preferences.getInteger(
                key,
                1
        );
    }

    public void saveUpgradeLevel(
            String bikeId,
            UpgradeType type,
            int level
    ) {

        int safeLevel =
                Math.max(
                        1,
                        Math.min(
                                EconomyConfig.MAX_UPGRADE_LEVEL,
                                level
                        )
                );

        preferences.putInteger(
                getUpgradeKey(
                        bikeId,
                        type
                ),
                safeLevel
        );

        preferences.flush();
    }

    private String getUpgradeKey(
            String bikeId,
            UpgradeType type
    ) {

        String prefix;

        switch (type) {

            case SPEED:
                prefix =
                        KEY_SPEED_PREFIX;
                break;

            case HANDLING:
                prefix =
                        KEY_HANDLING_PREFIX;
                break;

            case NITRO:
                prefix =
                        KEY_NITRO_PREFIX;
                break;

            default:
                prefix = "upgrade_";
                break;
        }

        return prefix + bikeId;
    }

    private String join(
            Set<String> values
    ) {

        StringBuilder builder =
                new StringBuilder();

        for (String value : values) {

            if (
                    builder.length() > 0
            ) {

                builder.append(",");
            }

            builder.append(value);
        }

        return builder.toString();
    }
}
