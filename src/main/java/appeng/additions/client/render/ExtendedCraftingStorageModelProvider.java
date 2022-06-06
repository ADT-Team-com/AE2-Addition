package appeng.additions.client.render;

import appeng.additions.AE2Additions;
import appeng.additions.utils.AdvancedCraftingUnitType;
import appeng.block.crafting.AbstractCraftingUnitBlock;
import appeng.client.render.crafting.AbstractCraftingUnitModelProvider;
import appeng.client.render.crafting.LightBakedModel;
import appeng.core.AppEng;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static appeng.additions.utils.AdvancedCraftingUnitType.getAllTypes;

public class ExtendedCraftingStorageModelProvider extends AbstractCraftingUnitModelProvider<AdvancedCraftingUnitType> {
    private final static Material RING_CORNER = textureAE("ring_corner");
    private final static Material RING_SIDE_HOR = textureAE("ring_side_hor");
    private final static Material RING_SIDE_VER = textureAE("ring_side_ver");
    private final static Material UNIT_BASE = textureAE("unit_base");
    private final static Material LIGHT_BASE = textureAE("light_base");

    private static final HashMap<AdvancedCraftingUnitType,Material> materials = new HashMap<>();
    static {
        for(AdvancedCraftingUnitType type : getAllTypes()) {
            materials.put(type, texture(type.name().toLowerCase()+"_light"));
        }
    }

    public ExtendedCraftingStorageModelProvider(AdvancedCraftingUnitType type) {
        super(type);
    }

    @Override
    public List<Material> getMaterials() {
        return new ArrayList<>(materials.values());
    }

    @Override
    public BakedModel getBakedModel(Function<Material, TextureAtlasSprite> spriteGetter) {
        TextureAtlasSprite ringCorner = spriteGetter.apply(RING_CORNER);
        TextureAtlasSprite ringSideHor = spriteGetter.apply(RING_SIDE_HOR);
        TextureAtlasSprite ringSideVer = spriteGetter.apply(RING_SIDE_VER);
        return new LightBakedModel(ringCorner,
                ringSideHor, ringSideVer, spriteGetter.apply(LIGHT_BASE),
                getLightTexture(spriteGetter, type));
    }

    private static Material textureAE(String name) {
        return new Material(InventoryMenu.BLOCK_ATLAS,
                new ResourceLocation(AppEng.MOD_ID, "block/crafting/" + name));
    }

    private static Material texture(String name) {
        return new Material(InventoryMenu.BLOCK_ATLAS,
                new ResourceLocation(AE2Additions.MODID, "block/crafting/" + name));
    }

    private static TextureAtlasSprite getLightTexture(Function<Material, TextureAtlasSprite> textureGetter,
                                                      AdvancedCraftingUnitType type) {

        return textureGetter.apply(materials.get(type));
    }
}
