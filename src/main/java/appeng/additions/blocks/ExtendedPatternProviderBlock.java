package appeng.additions.blocks;

import appeng.additions.blockentities.ExtendedPatternProviderBlockEntity;
import appeng.api.util.IOrientable;
import appeng.block.AEBaseEntityBlock;
import appeng.blockentity.crafting.PatternProviderBlockEntity;
import appeng.menu.locator.MenuLocators;
import appeng.util.InteractionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class ExtendedPatternProviderBlock extends AEBaseEntityBlock<ExtendedPatternProviderBlockEntity> {
    private final int size;

    public static final BooleanProperty OMNIDIRECTIONAL = BooleanProperty.create("omnidirectional");

    public ExtendedPatternProviderBlock(int size) {
        super(defaultProps(Material.METAL));
        registerDefaultState(defaultBlockState().setValue(OMNIDIRECTIONAL, true));
        this.size = size;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OMNIDIRECTIONAL);
    }


    @Override
    protected BlockState updateBlockStateFromBlockEntity(BlockState currentState, ExtendedPatternProviderBlockEntity be) {
        return currentState.setValue(OMNIDIRECTIONAL, be.isOmniDirectional());
    }

    @Override
    public InteractionResult onActivated(Level level, BlockPos pos, Player p,
                                         InteractionHand hand,
                                         @javax.annotation.Nullable ItemStack heldItem, BlockHitResult hit) {
        if (InteractionUtil.isInAlternateUseMode(p)) {
            return InteractionResult.PASS;
        }

        var be = this.getBlockEntity(level, pos);
        if (be != null) {
            if (!level.isClientSide()) {
                be.openMenu(p, MenuLocators.forBlockEntity(be));
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return InteractionResult.PASS;
    }

    @Override
    protected boolean hasCustomRotation() {
        return true;
    }

    @Override
    protected void customRotateBlock(IOrientable rotatable, Direction axis) {
        if (rotatable instanceof PatternProviderBlockEntity patternProvider) {
            patternProvider.setSide(axis);
        }
    }

    public int getSize() {
        return size;
    }
}
