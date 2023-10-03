package gg.meza.serverredstoneblock;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.UUID;

public class WorldInfoSaveData extends SavedData {

    public String worldId = UUID.randomUUID().toString();
    private boolean dirty = false;

    public static WorldInfoSaveData create() {
        WorldInfoSaveData data = new WorldInfoSaveData();
        data.setDirty();
        return data;
    }

    public static WorldInfoSaveData load(CompoundTag tag) {
        WorldInfoSaveData data = create();
        data.worldId = tag.getString("worldId");
        return data;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    public void setDirty() {
        dirty = true;
    }


    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        compoundTag.putString("worldId", worldId);
        return compoundTag;
    }
}
