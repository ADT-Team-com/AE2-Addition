package appeng.additions.blockentities;

import appeng.additions.blocks.ExtendedPatternProviderBlock;
import appeng.additions.item.menu.PatternProviderMenuFifthGen;
import appeng.additions.item.menu.PatternProviderMenuFourthGen;
import appeng.additions.item.menu.PatternProviderMenuSecondGen;
import appeng.additions.item.menu.PatternProviderMenuThirdGen;
import appeng.additions.mixins.IPatternProviderLogicAccessor;
import appeng.additions.registry.BlockEntitiesRegistry;
import appeng.additions.registry.BlocksRegistry;
import appeng.blockentity.crafting.PatternProviderBlockEntity;
import appeng.menu.MenuOpener;
import appeng.menu.locator.MenuLocator;
import appeng.util.inv.AppEngInternalInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ExtendedPatternProviderBlockEntity extends PatternProviderBlockEntity {

    public ExtendedPatternProviderBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntitiesRegistry.EXTENDED_PATTERN_PROVIDER, pos, blockState);
    }

    public ExtendedPatternProviderBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState blockState) {
        super(blockEntityType, pos, blockState);
        if(blockState.getBlock() instanceof ExtendedPatternProviderBlock) {
            int size = ((ExtendedPatternProviderBlock) blockState.getBlock()).getSize();
            ((IPatternProviderLogicAccessor) this.getLogic()).setPatternInventory(new AppEngInternalInventory(this.getLogic(), size));
        }
    }

    @Override
    protected Item getItemFromBlockEntity() {
        if(this.getLogic() != null) {
            var b = BlocksRegistry.getPatternProviderFromSize(this.getLogic().getPatternInv().size());
            if(b != null) return b.asItem();
        }
        return super.getItemFromBlockEntity();
    }

    @Override
    public void openMenu(Player player, MenuLocator locator) {
        switch (this.getLogic().getPatternInv().size()) {
            case 18 -> MenuOpener.open(PatternProviderMenuSecondGen.TYPE, player, locator);
            case 27 -> MenuOpener.open(PatternProviderMenuThirdGen.TYPE, player, locator);
            case 36 -> MenuOpener.open(PatternProviderMenuFourthGen.TYPE, player, locator);
            case 45 -> MenuOpener.open(PatternProviderMenuFifthGen.TYPE, player, locator);
            default -> super.openMenu(player, locator);
        }
    }

    @Override
    public void onReady() {
        super.onReady();
        this.getMainNode().setVisualRepresentation(getItemFromBlockEntity());
    }
}
