package com.ae.additions.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.*;

import java.util.List;

import static com.ae.additions.utils.ASMUtils.*;
import static org.objectweb.asm.Opcodes.*;

public class AEClassTransformer implements IClassTransformer {

    private static byte[] transformGuiPriority(byte[] basicClass) {
        ClassNode classNode = readClass(basicClass);
        MethodNode methodNode = findMethod(classNode, "initGui", "func_73866_w_");
        if (methodNode != null) {
            AbstractInsnNode point = findSequence(methodNode.instructions, true, ALOAD, GETFIELD, IFNULL, ALOAD, IFNULL);
            if (point != null) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(ALOAD, 6));
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new FieldInsnNode(GETFIELD, "appeng/client/gui/implementations/GuiPriority", "OriginalGui", "Lappeng/core/sync/GuiBridge;"));
                list.add(new MethodInsnNode(INVOKESTATIC, "com/ae/additions/asm/AEHooks", "getGui", "(Ljava/lang/Object;Lappeng/core/sync/GuiBridge;)Lappeng/core/sync/GuiBridge;", false));
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new InsnNode(SWAP));
                list.add(new FieldInsnNode(PUTFIELD, "appeng/client/gui/implementations/GuiPriority", "OriginalGui", "Lappeng/core/sync/GuiBridge;"));

                list.add(new VarInsnNode(ALOAD, 6));
                list.add(new VarInsnNode(ALOAD, 5));
                list.add(new MethodInsnNode(INVOKESTATIC, "com/ae/additions/asm/AEHooks", "getItem", "(Ljava/lang/Object;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;", false));
                list.add(new VarInsnNode(ASTORE, 5));

                methodNode.instructions.insertBefore(point, list);
            }
        }

        return writeClass(classNode);
    }

    private static byte[] transformContainerInterfaceTerminal(byte[] basicClass) {
        ClassNode classNode = readClass(basicClass);
        MethodNode methodNode = findMethod(classNode, "addItems");
        if (methodNode == null) return basicClass;
        AbstractInsnNode point = findSequence(methodNode.instructions, true, ALOAD, LDC, ALOAD, INVOKESTATIC, INVOKEVIRTUAL);
        if (point == null) return null;
        InsnList list = new InsnList();
        list.add(new VarInsnNode(ALOAD, 6));
        list.add(new LdcInsnNode("invSize"));
        list.add(new VarInsnNode(ALOAD, 2));
        list.add(new MethodInsnNode(INVOKESTATIC, "appeng/container/implementations/ContainerInterfaceTerminal$InvTracker", "access$100", "(Lappeng/container/implementations/ContainerInterfaceTerminal$InvTracker;)Lnet/minecraft/inventory/IInventory;", false));
        list.add(new MethodInsnNode(INVOKEINTERFACE, "net/minecraft/inventory/IInventory", "getSizeInventory", "()I", true));
        list.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/nbt/NBTTagCompound", "setInteger", "(Ljava/lang/String;I)V", false));
        methodNode.instructions.insertBefore(point, list);
        return writeClass(classNode);
    }

    private static byte[] transformGrid(byte[] basicClass) {
        ClassNode classNode = readClass(basicClass);
        MethodNode methodNode = findMethod(classNode, "getMachines");
        if (methodNode == null) return basicClass;
        List<AbstractInsnNode> points = findAllOpcodes(methodNode.instructions, ARETURN);
        if (points.isEmpty()) return basicClass;
        InsnList list = new InsnList();
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(new MethodInsnNode(INVOKESTATIC, "com/ae/additions/asm/AEHooks", "getMachines", "(Lappeng/me/MachineSet;Lappeng/me/Grid;)Lappeng/api/networking/IMachineSet;", false));
        points.forEach(point -> methodNode.instructions.insertBefore(point, list));
        return writeClass(classNode);
    }

    private static byte[] transformCraftingCPUCluster(byte[] basicClass) {
        ClassNode classNode = readClass(basicClass);
        MethodNode methodNode = findMethod(classNode, "addTile");
        if (methodNode == null) return basicClass;
        AbstractInsnNode point = findSequenceLast(methodNode.instructions, true, ALOAD, DUP, GETFIELD, ICONST_1, IADD, PUTFIELD);
        if (point == null) return null;
        InsnList list = new InsnList();
        LabelNode label = new LabelNode();
        list.add(new VarInsnNode(ALOAD, 1));
        list.add(new TypeInsnNode(INSTANCEOF, "com/ae/additions/utils/IAdvancedAccelerator"));
        list.add(new JumpInsnNode(IFEQ, label));
        list.add(new VarInsnNode(ALOAD, 1));
        list.add(new TypeInsnNode(CHECKCAST, "com/ae/additions/utils/IAdvancedAccelerator"));
        list.add(new MethodInsnNode(INVOKEINTERFACE, "com/ae/additions/utils/IAdvancedAccelerator", "getAcceleratorCount", "()I", true));
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(new FieldInsnNode(GETFIELD, "appeng/me/cluster/implementations/CraftingCPUCluster", "accelerator", "I"));
        list.add(new InsnNode(IADD));
        list.add(new InsnNode(ICONST_M1));
        list.add(new InsnNode(IADD));
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(new InsnNode(SWAP));
        list.add(new FieldInsnNode(PUTFIELD, "appeng/me/cluster/implementations/CraftingCPUCluster", "accelerator", "I"));
        list.add(label);
        methodNode.instructions.insert(point, list);
        return writeClass(classNode);
    }

    private static byte[] transformDualityInterface(byte[] basicClass) {
        ClassNode classNode = readClass(basicClass);
        MethodNode methodNode = findMethod(classNode, "updateCraftingList");
        if (methodNode == null) return basicClass;
        classNode.interfaces.add("com/ae/additions/utils/IDualityInterface");

        InsnList list = new InsnList();
        LabelNode label = new LabelNode();
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(new TypeInsnNode(CHECKCAST, "com/ae/additions/utils/IDualityInterface"));
        list.add(new MethodInsnNode(INVOKEINTERFACE, "com/ae/additions/utils/IDualityInterface", "isOverrideDefault", "()Z", true));
        list.add(new JumpInsnNode(IFEQ, label));
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(new TypeInsnNode(CHECKCAST, "com/ae/additions/utils/IDualityInterface"));
        list.add(new MethodInsnNode(INVOKEINTERFACE, "com/ae/additions/utils/IDualityInterface", "updateCraftingListProxy", "()V", true));
        list.add(new InsnNode(RETURN));
        list.add(label);

        methodNode.instructions.insert(list);

        classNode.methods.add(generateGetter("getProxy", "Lappeng/me/helpers/AENetworkProxy;", GETFIELD, "appeng/helpers/DualityInterface", "gridProxy", ARETURN));
        classNode.methods.add(generateGetter("getCraftingList", "Ljava/util/List;", GETFIELD, "appeng/helpers/DualityInterface", "craftingList", ARETURN));
        classNode.methods.add(generateGetter("getIsWorking", "Z", GETFIELD, "appeng/helpers/DualityInterface", "isWorking", IRETURN));

        MethodNode generated = new MethodNode(ASM5, ACC_PUBLIC, "callAddToCraftingList", "(Lnet/minecraft/item/ItemStack;)V", null, null);
        list = generated.instructions;
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(new VarInsnNode(ALOAD, 1));
        list.add(new MethodInsnNode(INVOKESPECIAL, "appeng/helpers/DualityInterface", "addToCraftingList", "(Lnet/minecraft/item/ItemStack;)V", false));
        list.add(new InsnNode(RETURN));
        classNode.methods.add(generated);

        generated = new MethodNode(ASM5, ACC_PUBLIC, "callReadConfig", "()V", null, null);
        list = generated.instructions;
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(new MethodInsnNode(INVOKESPECIAL, "appeng/helpers/DualityInterface", "readConfig", "()V", false));
        list.add(new InsnNode(RETURN));
        classNode.methods.add(generated);

        generated = new MethodNode(ASM5, ACC_PUBLIC, "callUpdateCraftingList", "()V", null, null);
        list = generated.instructions;
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(new MethodInsnNode(INVOKESPECIAL, "appeng/helpers/DualityInterface", "updateCraftingList", "()V", false));
        list.add(new InsnNode(RETURN));
        classNode.methods.add(generated);

        generated = new MethodNode(ASM5, ACC_PUBLIC, "callUpdatePlan", "(I)V", null, null);
        list = generated.instructions;
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(new VarInsnNode(ILOAD, 1));
        list.add(new MethodInsnNode(INVOKESPECIAL, "appeng/helpers/DualityInterface", "updatePlan", "(I)V", false));
        list.add(new InsnNode(RETURN));
        classNode.methods.add(generated);

        generated = new MethodNode(ASM5, ACC_PUBLIC, "callHasWorkToDo", "()Z", null, null);
        list = generated.instructions;
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(new MethodInsnNode(INVOKESPECIAL, "appeng/helpers/DualityInterface", "hasWorkToDo", "()Z", false));
        list.add(new InsnNode(IRETURN));
        classNode.methods.add(generated);
        return writeClass(classNode);
    }

    private static byte[] transformTileInterface(byte[] basicClass) {
        ClassNode classNode = readClass(basicClass);
        classNode.interfaces.add("com/ae/additions/utils/ITileInterface");
        MethodNode generated = new MethodNode(ASM5, ACC_PUBLIC, "setDuality", "(Lappeng/helpers/DualityInterface;)V", null, null);
        InsnList list = generated.instructions;
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(new VarInsnNode(ALOAD, 1));
        list.add(new FieldInsnNode(PUTFIELD, "appeng/tile/misc/TileInterface", "duality", "Lappeng/helpers/DualityInterface;"));
        list.add(new InsnNode(RETURN));
        classNode.methods.add(generated);
        return writeClass(classNode);
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (name.equals("appeng.client.gui.implementations.GuiPriority")) {
            return transformGuiPriority(basicClass);
        } else if (name.equals("appeng.container.implementations.ContainerInterfaceTerminal")) {
            return transformContainerInterfaceTerminal(basicClass);
        } else if (name.equals("appeng.me.Grid")) {
            return transformGrid(basicClass);
        } else if (name.equals("appeng.me.cluster.implementations.CraftingCPUCluster")) {
            return transformCraftingCPUCluster(basicClass);
        } else if (name.equals("appeng.helpers.DualityInterface")) {
            return transformDualityInterface(basicClass);
        } else if (name.equals("appeng.tile.misc.TileInterface")) {
            return transformTileInterface(basicClass);
        }
        return basicClass;
    }
}
