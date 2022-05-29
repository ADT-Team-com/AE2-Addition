package appeng.additions.client;

import appeng.additions.AE2Additions;
import appeng.additions.blocks.ExtendedCraftingStorageBlock;
import appeng.additions.client.render.ExtendedCraftingCubeModel;
import appeng.additions.registry.BlockEntitiesRegistry;
import appeng.additions.registry.BlocksRegistry;
import appeng.block.crafting.AbstractCraftingUnitBlock;
import appeng.client.render.SimpleModelLoader;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static appeng.additions.utils.AdvancedCraftingUnitType.getAllTypes;

public class InitBuiltInModels {
    public static void init() {
        for(AbstractCraftingUnitBlock.CraftingUnitType type : getAllTypes()) {
            addBuiltInModel("block/crafting/"+type.name().toLowerCase()+"_formed", ()->new ExtendedCraftingCubeModel(type));
        }
        for(RegistryObject<Block> ros : BlocksRegistry.BLOCKS.getEntries()) {
            Block b = ros.get();
            if(b instanceof ExtendedCraftingStorageBlock) {
                ItemBlockRenderTypes.setRenderLayer(b, RenderType.cutout());
            }
        }
    }

    private static <T extends IModelGeometry<T>> void addBuiltInModel(String id, Supplier<T> modelFactory) {
        ModelLoaderRegistry.registerLoader(new ResourceLocation(AE2Additions.MODID, id),
                new SimpleModelLoader<>(modelFactory));
    }
}
