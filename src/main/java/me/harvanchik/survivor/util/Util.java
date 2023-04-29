package me.harvanchik.survivor.util;

import java.sql.Timestamp;

/**
 * Class full of generic utility methods to be used throughout the plugin.
 * @author harvanchik
 * @since 04-28-2023
 */
public class Util {

    /**
     * Get the current {@link Timestamp}.
     * @return the current timestamp
     */
    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }
}
