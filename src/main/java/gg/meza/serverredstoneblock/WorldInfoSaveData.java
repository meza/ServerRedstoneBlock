package gg.meza.serverredstoneblock;

import net.minecraft.nbt.NbtCompound;
/*? if >= 1.21 {*/
import net.minecraft.registry.RegistryWrapper;
/*?}*/
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.UUID;

public class WorldInfoSaveData extends PersistentState {
    public String worldId = UUID.randomUUID().toString();
    private boolean dirty = false;

    /*? if < 1.21 {*/
    /*private static Type<WorldInfoSaveData> type = new Type<>(
            WorldInfoSaveData::create,
            WorldInfoSaveData::load,
            null
    );
    *//*?} else {*/
    private static Type<WorldInfoSaveData> type = new Type<>(
            WorldInfoSaveData::new,
            WorldInfoSaveData::load,
            null
    );
    /*?}*/


    public static WorldInfoSaveData create() {
        WorldInfoSaveData data = new WorldInfoSaveData();
        data.setDirty();
        return data;
    }


    public static WorldInfoSaveData load(NbtCompound tag/*? if >=1.21 {*/, RegistryWrapper.WrapperLookup registryLookup/*?}*/) {
        WorldInfoSaveData data = create();
        data.worldId = tag.getString("worldId");
        return data;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt/*? if >=1.21 {*/, RegistryWrapper.WrapperLookup registryLookup/*?}*/) {
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
        WorldInfoSaveData state = persistentStateManager.getOrCreate(type, "serverredstoneblock");
        state.markDirty();

        return state.worldId;
    }
}
