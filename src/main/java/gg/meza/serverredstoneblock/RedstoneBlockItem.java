package gg.meza.serverredstoneblock;

import net.minecraft.block.Block;
/*? if < 1.21 {*/
/*import net.minecraft.entity.player.PlayerEntity;
*//*?}*/
/*? if < 1.21.4 {*/
/*import net.minecraft.text.Text;
*//*?}*/
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.telemetry;

public class RedstoneBlockItem extends BlockItem {

    public RedstoneBlockItem(Block block, Settings itemProps) {
        super(block, itemProps);
    }

    @Override
    public void onCraft(ItemStack stack, World world/*? if < 1.21 {*//*, PlayerEntity player*//*?}*/) {
        if (!world.isClient()) {
            telemetry.redstoneBlockCrafted();
        }
    }


    /*? if <1.21.4 {*/
    /*@Override
    public Text getName() {
        return Text.translatable("block.serverredstoneblock.server_redstone_block");
    }
    *//*?}*/


}
