package gg.meza.serverredstoneblock;

import net.minecraft.util.StringRepresentable;

public enum ServerPowerState implements StringRepresentable {
    OFF(0),
    ON(15),
    WARNING(7);

    private final int value;

    ServerPowerState(int value) {
        this.value = value;
    }

    @Override
    public String getSerializedName() {
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
