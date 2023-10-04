package gg.meza.serverredstoneblock;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

import static gg.meza.serverredstoneblock.ServerRedstoneBlock.analytics;

public class RedstoneBlockItem extends BlockItem {

    private static final Settings itemProps = new Item.Settings().group(ItemGroup.REDSTONE);

    public RedstoneBlockItem(Block block) {
        super(block, itemProps);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isClient()) {
            analytics.redstoneBlockCrafted();
        }
    }

    @Override
    public Text getName() {
        return new TranslatableText("block.serverredstoneblock.server_redstone_block");
    }
}
