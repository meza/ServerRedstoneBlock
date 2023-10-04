package gg.meza.serverredstoneblock;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.analytics;

public class RedstoneBlock extends BlockWithEntity {
    public static final EnumProperty<ServerPowerState> POWER_STATE = EnumProperty.of("power_state", ServerPowerState.class);
    public static ServerPowerState powerState = ServerPowerState.ON;

    public static final Settings blockProps = Settings.of(Material.METAL)
            .mapColor(MapColor.RED)
            .requiresTool()
            .strength(5.0F, 6.0F)
            .sounds(BlockSoundGroup.METAL);

    public RedstoneBlock() {
        super(blockProps);
        setDefaultState(getDefaultState().with(POWER_STATE, ServerPowerState.ON));
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RedstoneBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (type == ServerRedstoneBlock.redstoneBlockEntityType) {
            return (BlockEntityTicker<T>) RedstoneBlockEntity::tick;
        }

        return null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWER_STATE);
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return powerState.getValue();
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if(!world.isClient()) {
            analytics.redstoneBlockBroken();
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if(!world.isClient()) {
            analytics.redstoneBlockPlaced();
        }
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
