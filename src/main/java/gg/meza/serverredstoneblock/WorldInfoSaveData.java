package gg.meza.serverredstoneblock;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
/*? if >=1.21.5 {*/
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.PersistentStateType;
/*?}*/
import net.minecraft.world.World;

import java.util.UUID;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.MOD_ID;

public class WorldInfoSaveData extends PersistentState {
    public String worldId = UUID.randomUUID().toString();
    private boolean dirty = false;

    /*? if < 1.21 {*/
    /*private static Type<WorldInfoSaveData> type = new Type<>(
            WorldInfoSaveData::create,
            WorldInfoSaveData::load,
            null
    );
    *//*?} elif < 1.21.5 {*/
    /*private static Type<WorldInfoSaveData> type = new Type<>(
            WorldInfoSaveData::new,
            WorldInfoSaveData::load,
            null
    );
    *//*?} else {*/
    public static PersistentStateType<WorldInfoSaveData> getType() {
        return new PersistentStateType<>(
                MOD_ID,
                (PersistentState.Context ctx) -> WorldInfoSaveData.create(),
                (PersistentState.Context ctx) -> Codecs.fromOps(NbtOps.INSTANCE).xmap(
                        nbtElement -> load((NbtCompound) nbtElement, ctx.getWorldOrThrow().getRegistryManager()),
                        manager -> manager.writeNbt(new NbtCompound(), ctx.getWorldOrThrow().getRegistryManager())
                ),
                null
        );
    }
    /*?}*/


    public static WorldInfoSaveData create() {
        WorldInfoSaveData data = new WorldInfoSaveData();
        data.setDirty();
        return data;
    }


    public static WorldInfoSaveData load(NbtCompound tag/*? if >=1.21 {*/, RegistryWrapper.WrapperLookup registryLookup/*?}*/) {
        WorldInfoSaveData data = create();
        /*? if < 1.21.5 {*/
        /*data.worldId = tag.getString("worldId");
        *//*?} else {*/
        data.worldId = tag.getString("worldId", UUID.randomUUID().toString());
        /*?}*/
        return data;
    }

    /*? if < 1.21.5 {*//*@Override*//*?}*/
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
        /*? if < 1.21.5 {*/
        /*WorldInfoSaveData state = persistentStateManager.getOrCreate(type, "serverredstoneblock");
        state.markDirty();

        return state.worldId;
        *//*?} else {*/
        WorldInfoSaveData state = persistentStateManager.getOrCreate(getType());
        state.markDirty();

        return state.worldId;
        /*?}*/
    }
}
