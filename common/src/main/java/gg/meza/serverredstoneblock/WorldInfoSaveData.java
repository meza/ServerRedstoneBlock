package gg.meza.serverredstoneblock;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.UUID;

public class WorldInfoSaveData extends PersistentState {
    public String worldId = UUID.randomUUID().toString();
    private boolean dirty = false;

    public static WorldInfoSaveData create() {
        WorldInfoSaveData data = new WorldInfoSaveData();
        data.setDirty();
        return data;
    }

    public static WorldInfoSaveData load(NbtCompound tag) {
        WorldInfoSaveData data = create();
        data.worldId = tag.getString("worldId");
        return data;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putString("worldId", worldId);
        return nbt;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    public void setDirty() {
        dirty = true;
    }

    public static String getWorldId(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        WorldInfoSaveData state = persistentStateManager.getOrCreate(WorldInfoSaveData::load, WorldInfoSaveData::create, "serverredstoneblock");
        state.markDirty();

        return state.worldId;
    }
}
