package me.blueslime.polarwarps.utils;

import java.util.Random;

public class SoundController {

    private static final Random random = new Random();

    public static <T extends Enum<?>> T getRandomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }


    public static <T extends Enum<?>> String getRandomEnumString(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        T type = clazz.getEnumConstants()[x];
        return type.toString().toUpperCase();
    }

}
