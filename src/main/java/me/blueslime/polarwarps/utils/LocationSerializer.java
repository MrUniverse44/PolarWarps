package me.blueslime.polarwarps.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.text.DecimalFormat;

public class LocationSerializer {

    private static final DecimalFormat format = new DecimalFormat("0.00");

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.0");

    public static String toDecimal(double value) {
        return decimalFormat.format(value);
    }

    public static Location fromString(String location) {
        if (location == null) {
            return null;
        }

        String[] text = location.replace(" ", "").split(",");

        if (text.length != 6) {
            return null;
        }

        double x = Double.parseDouble(text[1]);
        double y = Double.parseDouble(text[2]);
        double z = Double.parseDouble(text[3]);

        Location loc = new Location(
                Bukkit.getWorld(text[0]),
                x,
                y,
                z
        );

        loc.setYaw(
                Float.parseFloat(text[4])
        );

        loc.setPitch(
                Float.parseFloat(text[5])
        );

        return loc;
    }

    public static String toString(Location location) {
        return location.getWorld() + ", " +
                format.format(location.getX()) + ", " +
                format.format(location.getY()) + ", " +
                format.format(location.getZ()) + ", " +
                location.getYaw() + ", " +
                location.getPitch();
    }

}

