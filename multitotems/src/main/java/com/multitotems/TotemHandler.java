package com.multitotems;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MultiTotemMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TotemHandler {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        // Comme le totem vanilla : ne marche pas contre le vide ni /kill
        if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return;
        }

        // Le totem doit être dans la main (principale ou secondaire), comme le totem vanilla
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() instanceof MultiTotemItem) {
                ItemStack used = stack.copy();

                // Retire 1 utilisation (le totem disparaît quand il est épuisé)
                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));

                CriteriaTriggers.USED_TOTEM.trigger(player, used);

                // Annule la mort et applique les mêmes effets que le totem vanilla
                event.setCanceled(true);
                player.setHealth(1.0F);
                player.removeAllEffects();
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                player.level().broadcastEntityEvent(player, (byte) 35);
                return;
            }
        }
    }
}
