package com.multitotems;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class MultiTotemItem extends Item {
    public MultiTotemItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        int max = stack.getMaxDamage();
        int left = max - stack.getDamageValue();
        tooltip.add(Component.translatable("tooltip.multitotems.uses", left, max)
                .withStyle(ChatFormatting.GRAY));
    }
}
