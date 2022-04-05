package com.ae.additions.asm;

import appeng.core.sync.GuiBridge;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

public class AEClassTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (name.equals("appeng.client.gui.implementations.GuiPriority")) {
            return transform(basicClass);
        }

        return basicClass;
    }

    public static byte[] transform(byte[] basicClass) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);

        classReader.accept(classNode, 0);
        MethodNode methodNode = null;
        for (MethodNode mn : classNode.methods) {
            if (mn.name.equals("initGui") || mn.name.equals("func_73866_w_")) {
                methodNode = mn;
                break;
            }
        }
        if (methodNode != null) {

            InsnList point = find(methodNode.instructions, ALOAD, GETFIELD, IFNULL, ALOAD, IFNULL);
            if (point != null) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(ALOAD, 6));
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new FieldInsnNode(GETFIELD, "appeng/client/gui/implementations/GuiPriority", "OriginalGui", "Lappeng/core/sync/GuiBridge;"));
                list.add(new MethodInsnNode(INVOKESTATIC, "com/ae/additions/asm/AEHooks", "getGui", "(Ljava/lang/Object;Lappeng/core/sync/GuiBridge;)Lappeng/core/sync/GuiBridge;", false));
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new InsnNode(SWAP));
                list.add(new FieldInsnNode(PUTFIELD, "appeng/client/gui/implementations/GuiPriority", "OriginalGui", "Lappeng/core/sync/GuiBridge;"));
                //
                list.add(new VarInsnNode(ALOAD, 6));
                list.add(new VarInsnNode(ALOAD, 5));

                list.add(new MethodInsnNode(INVOKESTATIC, "com/ae/additions/asm/AEHooks", "getItem", "(Ljava/lang/Object;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;", false));
                list.add(new VarInsnNode(ASTORE, 5));


                methodNode.instructions.insertBefore(point.getFirst(), list);
            }
        }

        ClassWriter writer = new ClassWriter(2);
        classNode.accept(writer);
        return writer.toByteArray();
    }

    private static InsnList find(InsnList list, int...opcodes) {
        start:
        for (AbstractInsnNode node : list.toArray()) {
            AbstractInsnNode start = node;
            for (int i = 0; i < opcodes.length; i++) {
                int opcode = opcodes[i];
                if (start == null) return null;
                if (start.getOpcode() != opcode) {
                    continue start;
                }
                start = start.getNext();
            }
            InsnList l = new InsnList();
            start = node;
            for (int i = 0; i < opcodes.length; i++) {
                l.add(start);
                start = start.getNext();
            }
            return l;
        }
        return null;
    }
}
