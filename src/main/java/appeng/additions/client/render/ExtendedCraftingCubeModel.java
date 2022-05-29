package appeng.additions.client.render;

import appeng.additions.AE2Additions;
import appeng.block.crafting.AbstractCraftingUnitBlock;
import appeng.client.render.BasicUnbakedModel;
import appeng.client.render.crafting.CraftingCubeModel;
import appeng.core.AppEng;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.model.IModelConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Stream;

import static appeng.additions.utils.AdvancedCraftingUnitType.*;

public class ExtendedCraftingCubeModel implements BasicUnbakedModel<ExtendedCraftingCubeModel> {
    private final static Material RING_CORNER = textureAE("ring_corner");
    private final static Material RING_SIDE_HOR = textureAE("ring_side_hor");
    private final static Material RING_SIDE_VER = textureAE("ring_side_ver");
    private final static Material UNIT_BASE = textureAE("unit_base");
    private final static Material LIGHT_BASE = textureAE("light_base");
    private static final HashMap<AbstractCraftingUnitBlock.CraftingUnitType,Material> materials = new HashMap<>();
    static {
        for(AbstractCraftingUnitBlock.CraftingUnitType type : getAllTypes()) {
            materials.put(type, texture(type.name().toLowerCase()+"_light"));
        }
    }

    private final AbstractCraftingUnitBlock.CraftingUnitType type;

    public ExtendedCraftingCubeModel(AbstractCraftingUnitBlock.CraftingUnitType type) {
        this.type = type;
    }

    @Override
    public Stream<Material> getAdditionalTextures() {
        return Stream.concat(materials.values().stream(), Stream.of(RING_CORNER, RING_SIDE_HOR, RING_SIDE_VER, UNIT_BASE, LIGHT_BASE));
    }

    @Override
    public BakedModel bake(IModelConfiguration owner, ModelBakery bakery,
                           Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform,
                           ItemOverrides overrides, ResourceLocation modelLocation) {
        // Retrieve our textures and pass them on to the baked model
        TextureAtlasSprite ringCorner = spriteGetter.apply(RING_CORNER);
        TextureAtlasSprite ringSideHor = spriteGetter.apply(RING_SIDE_HOR);
        TextureAtlasSprite ringSideVer = spriteGetter.apply(RING_SIDE_VER);
        if(getAllTypes().contains(this.type)) {
            return new ExtendedLightBakedModel(
                    ringCorner,
                    ringSideHor, ringSideVer, spriteGetter.apply(LIGHT_BASE),
                    getLightTexture(spriteGetter, type));
        }
        throw new IllegalArgumentException("Unsupported crafting unit type: " + this.type);
    }

    private static TextureAtlasSprite getLightTexture(Function<Material, TextureAtlasSprite> textureGetter,
                                                      AbstractCraftingUnitBlock.CraftingUnitType type) {

        return textureGetter.apply(materials.get(type));
    }

    private static Material textureAE(String name) {
        return new Material(InventoryMenu.BLOCK_ATLAS,
                new ResourceLocation(AppEng.MOD_ID, "block/crafting/" + name));
    }

    private static Material texture(String name) {
        return new Material(InventoryMenu.BLOCK_ATLAS,
                new ResourceLocation(AE2Additions.MODID, "block/crafting/" + name));
    }
}
