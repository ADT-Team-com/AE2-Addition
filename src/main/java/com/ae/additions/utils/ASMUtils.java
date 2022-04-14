package com.ae.additions.utils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;

public class ASMUtils {

    public static AbstractInsnNode findFirstOpCode(final InsnList instructions, final int opcode) {
        for (int index = 0; index < instructions.size(); ++index) {
            if (instructions.get(index).getOpcode() == opcode) {
                return instructions.get(index);
            }
        }
        return null;
    }

    public static AbstractInsnNode findLastOpCode(final InsnList instructions, final int opcode) {
        for (int index = instructions.size() - 1; index > 0; --index) {
            if (instructions.get(index).getOpcode() == opcode) {
                return instructions.get(index);
            }
        }
        return null;
    }

    public static AbstractInsnNode findLastType(final InsnList instructions, final int type, int skip) {
        for (int index = instructions.size() - 1; index > 0; --index) {
            if (instructions.get(index).getType() == type) {
                if (--skip < 0) {
                    return instructions.get(index);
                }
            }
        }
        return null;
    }

    public static AbstractInsnNode findNextOpCode(final AbstractInsnNode fromInstruction, final int opcode) {
        AbstractInsnNode nextInsn = fromInstruction;
        do {
            if (nextInsn.getOpcode() == opcode) {
                return nextInsn;
            }
        }
        while ((nextInsn = nextInsn.getNext()) != null);

        return null;
    }

    public static AbstractInsnNode findSequenceLast(final InsnList instructions, final boolean skipNons, final int... opSequence) {
        int seqIndex = 0;
        AbstractInsnNode insertionPoint = null;
        for (int index = 0; index < instructions.size(); ++index) {
            // Get the instruction
            AbstractInsnNode ins = instructions.get(index);

            if (skipNons && (ins.getOpcode() == -1)) {
                continue;
            }

            // Does it match the sequence?
            if (ins.getOpcode() == opSequence[seqIndex]) {
                // Has the full sequence been found?
                if (++seqIndex == opSequence.length) {
                    // Found the full sequence
                    insertionPoint = ins;
                    break;
                }
            } else if (ins.getOpcode() == opSequence[0]) {
                // Restart sequence
                seqIndex = 1;
            } else {
                // Reset sequence
                seqIndex = 0;
            }
        }

        return insertionPoint;
    }

    public static AbstractInsnNode findSequence(final InsnList instructions, final boolean skipNons, final int... opSequence) {
        int seqIndex = 0;
        AbstractInsnNode insertionPoint = null;
        boolean found = false;
        for (int index = 0; index < instructions.size(); ++index) {
            AbstractInsnNode ins = instructions.get(index);

            if (skipNons && (ins.getOpcode() == -1)) {
                continue;
            }
            if (ins.getOpcode() == opSequence[seqIndex]) {
                if (seqIndex == 0) {
                    insertionPoint = ins;
                }
                if (++seqIndex == opSequence.length) {
                    found = true;
                    break;
                }
            } else if (ins.getOpcode() == opSequence[0]) {
                insertionPoint = ins;
                seqIndex = 1;
            } else {
                seqIndex = 0;
            }
        }

        return found ? insertionPoint : null;
    }

    public static ClassNode readClass(byte[] basicClass, int flags) {
        ClassReader reader = new ClassReader(basicClass);
        ClassNode node = new ClassNode();
        reader.accept(node, flags);
        return node;
    }

    public static ClassNode readClass(byte[] basicClass) {
        return readClass(basicClass, 0);
    }

    public static MethodNode findMethod(ClassNode classNode, String... names) {
        List<String> list = Arrays.asList(names);
        MethodNode methodNode = null;
        for (MethodNode node : classNode.methods) {
            if (list.contains(node.name)) {
                methodNode = node;
                break;
            }
        }
        return methodNode;
    }

    public static byte[] writeClass(ClassNode node, int flags) {
        ClassWriter writer = new ClassWriter(flags);
        node.accept(writer);
        return writer.toByteArray();
    }

    public static byte[] writeClass(ClassNode node) {
        return writeClass(node, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
    }

    public static MethodNode findMethodDesc(ClassNode classNode, String name, String desc) {
        MethodNode methodNode = null;
        for (MethodNode node : classNode.methods) {
            if (name.equals(node.name) && desc.equals(node.desc)) {
                methodNode = node;
                break;
            }
        }
        return methodNode;
    }

    public static void log(ClassNode classNode, PrintWriter writer) {
        TraceClassVisitor visitor = new TraceClassVisitor(writer);
        classNode.accept(visitor);
    }

    public static void log(ClassNode classNode) {
        log(classNode, new PrintWriter(System.out));
    }

    public static List<AbstractInsnNode> findAllOpcodes(InsnList list, int opcode) {
        List<AbstractInsnNode> nodes = new ArrayList<>();
        for (AbstractInsnNode node : list.toArray()) {
            if (node.getOpcode() == opcode) nodes.add(node);
        }
        return nodes;
    }

    public static MethodNode generateGetter(String name, String desc, int opcode, String owner, String fieldName, int retOpcode) {
        MethodNode node = new MethodNode(ASM5, ACC_PUBLIC, name, "()" + desc, null, null);
        InsnList list = node.instructions;
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(new FieldInsnNode(opcode, owner, fieldName, desc));
        list.add(new InsnNode(retOpcode));
        return node;
    }
}
