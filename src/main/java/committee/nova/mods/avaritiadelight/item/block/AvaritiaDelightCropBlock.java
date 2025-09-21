package committee.nova.mods.avaritiadelight.item.block;

import java.util.function.Supplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import org.jetbrains.annotations.NotNull;

public class AvaritiaDelightCropBlock extends CropBlock {
    private final Supplier<Item> seed;

    public AvaritiaDelightCropBlock() {
        this(null);
    }

    public AvaritiaDelightCropBlock(Supplier<Item> seed) {
        super(Properties.of().noOcclusion().noCollission().randomTicks().instabreak().sound(SoundType.CROP));
        this.seed = seed;
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return this.seed == null ? this : this.seed.get();
    }
}
