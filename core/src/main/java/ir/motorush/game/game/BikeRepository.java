package ir.motorush.game.game;

import java.util.ArrayList;
import java.util.List;

public final class BikeRepository {

    private BikeRepository() {
    }

    public static List<BikeData> getAllBikes() {

        List<BikeData> bikes =
                new ArrayList<>();

        bikes.add(
                new BikeData(
                        "starter",
                        "Street Rider",
                        160f,
                        30f,
                        0
                )
        );

        bikes.add(
                new BikeData(
                        "speedster",
                        "Speedster",
                        190f,
                        26f,
                        600
                )
        );

        bikes.add(
                new BikeData(
                        "phantom",
                        "Phantom",
                        215f,
                        34f,
                        1200
                )
        );

        bikes.add(
                new BikeData(
                        "racer_x",
                        "Racer X",
                        240f,
                        38f,
                        2200
                )
        );

        return bikes;
    }

    public static BikeData findById(
            String id
    ) {

        for (
                BikeData bike :
                getAllBikes()
        ) {

            if (
                    bike.getId()
                            .equals(id)
            ) {

                return bike;
            }
        }

        return getAllBikes().get(0);
    }
}
