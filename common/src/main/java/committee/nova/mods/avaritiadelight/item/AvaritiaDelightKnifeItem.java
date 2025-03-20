package committee.nova.mods.avaritiadelight.item;

import committee.nova.mods.avaritiadelight.registry.ADItemGroups;
import net.minecraft.item.ToolMaterial;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class AvaritiaDelightKnifeItem extends KnifeItem {
    public AvaritiaDelightKnifeItem(ToolMaterial tier, Settings settings) {
        super(tier, 3, -2.4f, settings.arch$tab(ADItemGroups.MAIN));
    }
}
