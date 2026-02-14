package org.example.utils;

import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.tag.Tag;

public interface Tools {

    ItemStack SELECT_TOOL = ItemStack.builder(Material.STICK)
            .maxStackSize(1)
            .customName(Component.text("Select tool"))
            .set(Tag.Boolean("Tool"),true)
            .build();

    ItemStack ASMBL_TOOL = ItemStack.builder(Material.BLAZE_ROD)
            .maxStackSize(1)
            .customName(Component.text("Asmbl tool"))
            .set(Tag.Boolean("Tool"),true)
            .build();

}
