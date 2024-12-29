package gg.meza.serverredstoneblock;

/*? if fabric {*/
import gg.meza.serverredstoneblock.fabric.RegistryHelper;
/*?}*/
/*? if forge {*/
/*import gg.meza.serverredstoneblock.forge.RegistryHelper;
*//*?}*/
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static gg.meza.serverredstoneblock.RedstoneBlock.POWER_STATE;

public class RedstoneBlockEntity extends BlockEntity {

    private int tickCount = 0;

    public RedstoneBlockEntity(BlockPos pos, BlockState state) {
        /*? if forge {*/
        /*super(RegistryHelper.REDSTONE_BLOCK_ENTITY.get(), pos, state);
        *//*?}*/
        /*? if fabric {*/
        super(RegistryHelper.REDSTONE_BLOCK_ENTITY, pos, state);
        /*?}*/
    }

    public void tick(World level, BlockPos blockPos, BlockState blockState) {
        if (level.isClient()) {
            return;
        }

        if (tickCount++ == 20) {
            level.setBlockState(blockPos, blockState.with(POWER_STATE, ServerRedstoneBlock.currentState.getState()), 2);
            level.updateNeighbors(blockPos, level.getBlockState(blockPos).getBlock());
            tickCount = 0;
        }
    }

    public static <T extends BlockEntity> void tick(World level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (blockEntity instanceof RedstoneBlockEntity) {
            ((RedstoneBlockEntity) blockEntity).tick(level, blockPos, blockState);
        }
    }

}
