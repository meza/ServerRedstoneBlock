package gg.meza.serverredstoneblock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class RedstoneBlockItem extends BlockItem {

    private static final Item.Properties itemProps = new Item.Properties().arch$tab(CreativeModeTabs.REDSTONE_BLOCKS);

    public RedstoneBlockItem(Block block) {
        super(block, itemProps);
    }
}
