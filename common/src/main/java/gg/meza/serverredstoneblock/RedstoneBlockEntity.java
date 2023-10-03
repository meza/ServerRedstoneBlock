package gg.meza.serverredstoneblock;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static gg.meza.serverredstoneblock.RedstoneBlock.POWER_STATE;
import static gg.meza.serverredstoneblock.RedstoneBlock.powerState;
import static gg.meza.serverredstoneblock.ServerRedstoneBlock.*;

public class RedstoneBlockEntity extends BlockEntity implements EntityBlock, Nameable {

    private int tickCount = 0;

    public RedstoneBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(redstoneBlockEntityType.get(), blockPos, blockState);
    }

    @Override
    public Component getName() {
        return Component.translatable("block.serverredstoneblock.server_redstone_block");
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RedstoneBlockEntity(blockPos, blockState);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (tickCount++ == 20) {
            level.setBlock(blockPos, blockState.setValue(POWER_STATE, powerState), 3);
            level.updateNeighborsAt(blockPos, this.getBlockState().getBlock());
            tickCount = 0;
        }
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (blockEntity instanceof RedstoneBlockEntity) {
            ((RedstoneBlockEntity) blockEntity).tick(level, blockPos, blockState);
        }
    }

}
