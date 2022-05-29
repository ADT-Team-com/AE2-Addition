package appeng.additions.client.render;

import appeng.block.crafting.AbstractCraftingUnitBlock;
import appeng.client.render.cablebus.CubeBuilder;
import appeng.util.Platform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IModelData;

public class ExtendedLightBakedModel extends ACraftingCubeBakedModel {

    private final TextureAtlasSprite baseTexture;

    private final TextureAtlasSprite lightTexture;

    ExtendedLightBakedModel(TextureAtlasSprite ringCorner, TextureAtlasSprite ringHor, TextureAtlasSprite ringVer,
                    TextureAtlasSprite baseTexture, TextureAtlasSprite lightTexture) {
        super(ringCorner, ringHor, ringVer);
        this.baseTexture = baseTexture;
        this.lightTexture = lightTexture;
    }

    @Override
    protected void addInnerCube(Direction facing, BlockState state, IModelData modelData, CubeBuilder builder, float x1,
                                float y1, float z1, float x2, float y2, float z2) {
        builder.setTexture(this.baseTexture);
        builder.addCube(x1, y1, z1, x2, y2, z2);
        boolean powered = state.getValue(AbstractCraftingUnitBlock.POWERED);
        builder.setEmissiveMaterial(powered);
        builder.setTexture(this.lightTexture);
        builder.addCube(x1, y1, z1, x2, y2, z2);
        // Reset back to default
        builder.setEmissiveMaterial(false);
    }
}