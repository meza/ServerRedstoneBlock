package gg.meza.serverredstoneblock;

import net.minecraft.block.BlockState;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.telemetry;

public class CurrentState {
    private ServerPowerState state;

    public CurrentState() {
        this.state = ServerPowerState.ON;
    }

    public ServerPowerState getState() {
        return state;
    }

    private void setState(ServerPowerState state) {
        this.state = state;
        telemetry.redstoneToggled(this.state);
    }

    public void on() {
        setState(ServerPowerState.ON);
    }

    public void off() {
        setState(ServerPowerState.OFF);
    }

    public void warning() {
        setState(ServerPowerState.WARNING);
    }
}
