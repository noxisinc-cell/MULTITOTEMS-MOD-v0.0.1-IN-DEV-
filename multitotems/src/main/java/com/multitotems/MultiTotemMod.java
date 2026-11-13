package com.multitotems;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(MultiTotemMod.MODID)
public class MultiTotemMod {
    public static final String MODID = "multitotems";

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Le nombre dans durability(...) = nombre de fois que le totem te sauve de la mort.
    public static final RegistryObject<Item> GOLD_TOTEM = ITEMS.register("gold_totem",
            () -> new MultiTotemItem(new Item.Properties().durability(3).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> EMERALD_TOTEM = ITEMS.register("emerald_totem",
            () -> new MultiTotemItem(new Item.Properties().durability(5).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> DIAMOND_TOTEM = ITEMS.register("diamond_totem",
            () -> new MultiTotemItem(new Item.Properties().durability(10).rarity(Rarity.RARE)));

    public static final RegistryObject<Item> NETHERITE_TOTEM = ITEMS.register("netherite_totem",
            () -> new MultiTotemItem(new Item.Properties().durability(25).rarity(Rarity.EPIC).fireResistant()));

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("totems",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.multitotems"))
                    .icon(() -> new ItemStack(NETHERITE_TOTEM.get()))
                    .displayItems((params, output) -> {
                        output.accept(GOLD_TOTEM.get());
                        output.accept(EMERALD_TOTEM.get());
                        output.accept(DIAMOND_TOTEM.get());
                        output.accept(NETHERITE_TOTEM.get());
                    })
                    .build());

    public MultiTotemMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        TABS.register(bus);
    }
}
