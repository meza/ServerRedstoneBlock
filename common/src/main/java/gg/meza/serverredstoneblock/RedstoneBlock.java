package gg.meza.serverredstoneblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.analytics;
import static gg.meza.serverredstoneblock.ServerRedstoneBlock.redstoneBlockEntityType;

public class RedstoneBlock extends BaseEntityBlock {
    public static final EnumProperty<ServerPowerState> POWER_STATE = EnumProperty.create("power_state", ServerPowerState.class);
    public static ServerPowerState powerState = ServerPowerState.ON;

    public RedstoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return blockEntityType == redstoneBlockEntityType ? RedstoneBlockEntity::tick : null;
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RedstoneBlockEntity(blockPos, blockState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWER_STATE);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
        return powerState.getValue();
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity, ItemStack itemStack) {
        super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
        analytics.redstoneBlockRemoved();
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);
        analytics.redstoneBlockPlacedEvent();
    }

    private void switchTo(ServerPowerState state) {
        powerState = state;
        analytics.redstoneToggled(powerState);
    }

    public void warning() {
        switchTo(ServerPowerState.WARNING);
    }

    public void off() {
        switchTo(ServerPowerState.OFF);
    }

    public void on() {
        switchTo(ServerPowerState.ON);
    }

}
