package gg.meza.serverredstoneblock;

import net.minecraft.util.StringIdentifiable;

public enum ServerPowerState implements StringIdentifiable {
    OFF(0),
    ON(15),
    WARNING(7);

    private final int value;

    ServerPowerState(int value) {
        this.value = value;
    }

    @Override
    public String asString() {
        return switch (this) {
            case OFF -> "off";
            case ON -> "on";
            case WARNING -> "warning";
        };
    }

    public int getValue() {
        return value;
    }
}
