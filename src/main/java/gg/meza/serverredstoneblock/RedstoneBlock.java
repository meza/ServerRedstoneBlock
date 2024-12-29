package gg.meza.serverredstoneblock;

/*? if fabric {*/
import gg.meza.serverredstoneblock.fabric.RegistryHelper;
/*?}*/
/*? if forge {*/
/*import gg.meza.serverredstoneblock.forge.RegistryHelper;
*//*?}*/
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.currentState;
import static gg.meza.serverredstoneblock.ServerRedstoneBlock.telemetry;

public class RedstoneBlock extends BlockWithEntity {
    public static final EnumProperty<ServerPowerState> POWER_STATE = EnumProperty.of("power_state", ServerPowerState.class);
    public static ServerPowerState powerState;

    public RedstoneBlock(Settings settings) {
        super(settings);
        powerState = ServerPowerState.ON;
        setDefaultState(getDefaultState().with(POWER_STATE, powerState));
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RedstoneBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        /*? if forge {*/
        /*if (type == RegistryHelper.REDSTONE_BLOCK_ENTITY.get()) {
        *//*?}*/
        /*? if fabric {*/
        if (type == RegistryHelper.REDSTONE_BLOCK_ENTITY) {
        /*?}*/
            return (BlockEntityTicker<T>) RedstoneBlockEntity::tick;
        }

        return null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWER_STATE);
    }

    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return currentState.getState().getValue();
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if(!world.isClient()) {
            telemetry.redstoneBlockBroken();
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if(!world.isClient()) {
            telemetry.redstoneBlockPlaced();
        }
    }

}
