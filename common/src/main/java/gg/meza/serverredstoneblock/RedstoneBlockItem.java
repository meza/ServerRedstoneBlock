package gg.meza.serverredstoneblock;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;

public class RedstoneBlockItem extends BlockItem {

    private static final Settings itemProps = new Settings().arch$tab(ItemGroups.REDSTONE);

    public RedstoneBlockItem(Block block) {
        super(block, itemProps);
    }
}
