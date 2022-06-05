package appeng.additions.data;

import appeng.additions.AE2Additions;
import appeng.additions.data.providers.BlockDropProvider;
import appeng.additions.data.providers.CraftingRecipes;
import appeng.additions.data.providers.ModelDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = AE2Additions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AE2AdditionsDataGenerator {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent dataEvent) {
        onGatherData(dataEvent.getGenerator(), dataEvent.getExistingFileHelper());
    }

    public static void onGatherData(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        generator.addProvider(new BlockDropProvider(generator.getOutputFolder()));
        generator.addProvider(new CraftingRecipes(generator));
        generator.addProvider(new ModelDataProvider(generator, AE2Additions.MODID, existingFileHelper));
    }
}
