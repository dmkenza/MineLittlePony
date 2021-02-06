package com.minelittlepony.api.model.armour;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import com.minelittlepony.hdskins.util.Registries;

public final class ArmourRegistry {
    private ArmourRegistry() {}
    static final Registry<IArmour<?>> REGISTRY = Registries.createDefaulted(new Identifier("minelittlepony", "armour"), "");

    @SuppressWarnings("unchecked")
    public static <T extends IArmourModel> IArmour<T> getArmour(ItemStack stack, IArmour<T> fallback) {
        return (IArmour<T>)REGISTRY.getOrEmpty(Registry.ITEM.getId(stack.getItem())).orElse(fallback);
    }
}
