package gg.meza.serverredstoneblock;

/*? if >=1.21 {*/
import com.mojang.serialization.MapCodec;
/*?}*/

/*? if fabric {*/
/*import gg.meza.serverredstoneblock.fabric.RegistryHelper;
*//*?}*/
/*? if forge {*/
import gg.meza.serverredstoneblock.forge.RegistryHelper;
/*?}*/
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.currentState;
import static gg.meza.serverredstoneblock.ServerRedstoneBlock.telemetry;

public class RedstoneBlock extends Block {
    /*? if >=1.21 {*/
    public static final MapCodec<RedstoneBlock> CODEC = createCodec(RedstoneBlock::new);
    /*?}*/
    public static final EnumProperty<ServerPowerState> POWER_STATE = EnumProperty.of("power_state", ServerPowerState.class);
    public static ServerPowerState powerState;

    public RedstoneBlock(Settings settings) {
        super(settings);
        powerState = ServerPowerState.ON;
        setDefaultState(getDefaultState().with(POWER_STATE, powerState));
    }

    /*? if >=1.21 {*/
    public MapCodec<RedstoneBlock> getCodec() {
        return CODEC;
    }
    /*?}*/

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
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

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        ServerRedstoneBlock.LOGGER.info("Scheduled tick" + pos);
        world.setBlockState(pos, state.with(POWER_STATE, currentState.getState()), 3);
        world.updateNeighbors(pos, world.getBlockState(pos).getBlock());
        if(state.getBlock() == this) {
            world.scheduleBlockTick(pos, this, 20);
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        ServerRedstoneBlock.LOGGER.info("Redstone block placed on the client");
        if(!world.isClient()) {
            world.scheduleBlockTick(pos, this, 20);
            ServerRedstoneBlock.LOGGER.info("Redstone block placed on the server");
            telemetry.redstoneBlockPlaced();
        }
    }
}
