package committee.nova.mods.avaritiadelight.util;

import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class InventoryHelper {
    public static CompoundTag writeNbt(CompoundTag nbt, NonNullList<ItemStack> stacks) {
        return writeNbt(nbt, stacks, true);
    }

    public static CompoundTag writeNbt(CompoundTag nbt, NonNullList<ItemStack> stacks, boolean setIfEmpty) {
        ListTag nbtList = new ListTag();
        for (int i = 0; i < stacks.size(); ++i) {
            ItemStack itemStack = stacks.get(i);
            if (!itemStack.isEmpty()) {
                CompoundTag nbtCompound = new CompoundTag();
                nbtCompound.putByte("Slot", (byte) i);
                writeStackNbt(itemStack, nbtCompound);
                nbtList.add(nbtCompound);
            }
        }
        if (!nbtList.isEmpty() || setIfEmpty) nbt.put("Items", nbtList);
        return nbt;
    }

    public static void readNbt(CompoundTag nbt, NonNullList<ItemStack> stacks) {
        ListTag nbtList = nbt.getList("Items", 10);
        for (int i = 0; i < nbtList.size(); ++i) {
            CompoundTag nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 255;
            if (j < stacks.size()) stacks.set(j, readStackNbt(nbtCompound));
        }
    }

    private static ItemStack readStackNbt(CompoundTag nbt) {
        ItemStack stack = new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(nbt.getString("id"))), nbt.getInt("Count"));
        if (nbt.contains("tag", 10)) {
            stack.setTag(nbt.getCompound("tag"));
            stack.getItem().verifyTagAfterLoad(stack.getTag());
        }
        return stack;
    }

    private static void writeStackNbt(ItemStack stack, CompoundTag nbt) {
        ResourceLocation identifier = BuiltInRegistries.ITEM.getKey(stack.getItem());
        nbt.putString("id", identifier.toString());
        nbt.putInt("Count", stack.getCount());
        if (stack.getTag() != null)
            nbt.put("tag", stack.getTag().copy());
    }

    public static boolean canInsertItems(int start, Container inventory, List<ItemStack> insert) {
        return insertItems(start, copy(inventory), insert);
    }

    public static boolean insertItems(int start, Container inventory, List<ItemStack> insert) {
        for (ItemStack insertStack : insert)
            if (insertStack != null && !tryAddItemToInventory(start, inventory, insertStack.copy()))
                return false;
        return true;
    }

    private static boolean tryAddItemToInventory(int start, Container inventory, ItemStack stack) {
        if (stack.isEmpty()) return true;
        for (int i = start; i < inventory.getContainerSize(); i++) {
            ItemStack inventoryStack = inventory.getItem(i);
            if (inventoryStack == null || ItemStack.isSameItemSameTags(inventoryStack, stack)) {
                if (stack.getMaxStackSize() - (inventoryStack != null ? inventoryStack.getCount() : 0) > 0) {
                    int countToAdd = Math.min(stack.getCount(), stack.getMaxStackSize() - (inventoryStack != null ? inventoryStack.getCount() : 0));
                    if (inventoryStack == null) inventory.setItem(i, stack.copy());
                    else inventoryStack.grow(countToAdd);
                    stack.shrink(countToAdd);
                    if (stack.getCount() == 0) return true;
                }
            }
        }
        if (inventory instanceof Inventory playerInventory) {
            playerInventory.placeItemBackInInventory(stack);
            return true;
        } else for (int i = start; i < inventory.getContainerSize(); i++)
            if (inventory.getItem(i).isEmpty()) {
                inventory.setItem(i, stack);
                return true;
            }
        return false;
    }

    public static Container copy(Container another) {
        Container inventory = new SimpleContainer(another.getContainerSize());
        for (int i = 0; i < another.getContainerSize(); i++)
            inventory.setItem(i, another.getItem(i).copy());
        return inventory;
    }
}
