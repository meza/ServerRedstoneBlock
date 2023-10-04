package gg.meza.serverredstoneblock;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static gg.meza.serverredstoneblock.RedstoneBlock.POWER_STATE;
import static gg.meza.serverredstoneblock.RedstoneBlock.powerState;
import static gg.meza.serverredstoneblock.ServerRedstoneBlock.redstoneBlockEntityType;

public class RedstoneBlockEntity extends BlockEntity {

    private int tickCount = 0;

    public RedstoneBlockEntity(BlockPos pos, BlockState state) {
        super(redstoneBlockEntityType, pos, state);
    }

    public void tick(World level, BlockPos blockPos, BlockState blockState) {
        if (tickCount++ == 20) {
            level.setBlockState(blockPos, blockState.with(POWER_STATE, powerState), 3);
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
