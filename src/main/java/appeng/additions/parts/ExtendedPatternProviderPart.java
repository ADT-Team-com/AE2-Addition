package appeng.additions.parts;

import appeng.additions.AE2Additions;
import appeng.additions.menu.*;
import appeng.additions.mixins.IPatternProviderLogicAccessor;
import appeng.api.parts.IPartItem;
import appeng.api.parts.IPartModel;
import appeng.core.AppEng;
import appeng.items.parts.PartModels;
import appeng.menu.MenuOpener;
import appeng.menu.locator.MenuLocator;
import appeng.parts.PartModel;
import appeng.parts.crafting.PatternProviderPart;
import appeng.util.inv.AppEngInternalInventory;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public class ExtendedPatternProviderPart extends PatternProviderPart {
    private static final ResourceLocation RESOURCE_OFF = new ResourceLocation(AppEng.MOD_ID, "part/interface_off");
    private static final ResourceLocation RESOURCE_ON = new ResourceLocation(AppEng.MOD_ID, "part/interface_on");
    private static final ResourceLocation RESOURCE_HAS_CHANNEL = new ResourceLocation(AppEng.MOD_ID, "part/interface_has_channel");
    public static final Int2IntMap LEVELS = new Int2IntArrayMap();
    public static final Int2ObjectArrayMap<ResourceLocation> MODEL_BASE_MAP = new Int2ObjectArrayMap<>();
    public static final Int2ObjectArrayMap<PartModel> MODELS_OFF_MAP = new Int2ObjectArrayMap<>();
    public static final Int2ObjectArrayMap<PartModel> MODELS_ON_MAP = new Int2ObjectArrayMap<>();
    public static final Int2ObjectArrayMap<PartModel> MODELS_HAS_CHANNEL_MAP = new Int2ObjectArrayMap<>();

    public static void computeBaseModels() {
        if (LEVELS.size() != MODEL_BASE_MAP.size()) {
            LEVELS.values().forEach(i -> {
                if (!MODEL_BASE_MAP.containsKey(i)) {
                    MODEL_BASE_MAP.put(i, new ResourceLocation(AE2Additions.MODID, "part/pattern_provider_" + i +"th_base"));
                }
            });
        }
    }

    @PartModels
    public static Collection<PartModel> modelsOff() {
        if (MODELS_OFF_MAP.size() != LEVELS.size()) {
            computeBaseModels();
            MODELS_OFF_MAP.clear();
            MODEL_BASE_MAP.forEach((k, v) -> {
                MODELS_OFF_MAP.put(k.intValue(), new PartModel(v, RESOURCE_OFF));
            });
        }
        return MODELS_OFF_MAP.values();
    }

    @PartModels
    public static Collection<PartModel> modelsOn() {
        if (MODELS_ON_MAP.size() != LEVELS.size()) {
            computeBaseModels();
            MODELS_ON_MAP.clear();
            MODEL_BASE_MAP.forEach((k, v) -> {
                MODELS_ON_MAP.put(k.intValue(), new PartModel(v, RESOURCE_ON));
            });
        }
        return MODELS_ON_MAP.values();
    }

    @PartModels
    public static Collection<PartModel> modelsHasChannel() {
        if (MODELS_HAS_CHANNEL_MAP.size() != LEVELS.size()) {
            computeBaseModels();
            MODELS_HAS_CHANNEL_MAP.clear();
            MODEL_BASE_MAP.forEach((k, v) -> {
                MODELS_HAS_CHANNEL_MAP.put(k.intValue(), new PartModel(v, RESOURCE_HAS_CHANNEL));
            });
        }
        return MODELS_HAS_CHANNEL_MAP.values();
    }

    private final int size;
    public ExtendedPatternProviderPart(IPartItem<?> partItem) {
        this(partItem, 18);
    }

    public ExtendedPatternProviderPart(IPartItem<?> partItem, int size) {
        super(partItem);
        this.size = size;
        ((IPatternProviderLogicAccessor) this.getLogic()).setPatternInventory(new AppEngInternalInventory(this.getLogic(), size));
    }

    @Override
    public void openMenu(Player player, MenuLocator locator) {
        switch (this.getLogic().getPatternInv().size()) {
            case 18 -> MenuOpener.open(PatternProviderMenuSecondGen.TYPE, player, locator);
            case 27 -> MenuOpener.open(PatternProviderMenuThirdGen.TYPE, player, locator);
            case 36 -> MenuOpener.open(PatternProviderMenuFourthGen.TYPE, player, locator);
            case 45 -> MenuOpener.open(PatternProviderMenuFifthGen.TYPE, player, locator);
            case 54 -> MenuOpener.open(PatternProviderMenuSixthGen.TYPE, player, locator);
            default -> super.openMenu(player, locator);
        }
    }

    @Override
    public ItemStack getMainMenuIcon() {
        return new ItemStack(getPartItem().asItem());
    }

    @Override
    public IPartModel getStaticModels() {
        if (LEVELS.containsKey(size)) {
            if (this.isActive() && this.isPowered()) {
                return MODELS_HAS_CHANNEL_MAP.get(LEVELS.get(size));
            } else if (this.isPowered()) {
                return MODELS_ON_MAP.get(LEVELS.get(size));
            } else {
                return MODELS_OFF_MAP.get(LEVELS.get(size));
            }
        }
        return super.getStaticModels();
    }
}
