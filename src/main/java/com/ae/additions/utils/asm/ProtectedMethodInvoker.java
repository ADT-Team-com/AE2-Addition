package com.ae.additions.utils.asm;

import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

public class ProtectedMethodInvoker {
    private final String owner;
    private final String name;
    private final MethodDescription desc;
    private final boolean isStatic;

    public ProtectedMethodInvoker(String owner, String name, MethodDescription desc, boolean isStatic) {
        this.owner = owner;
        this.name = name;
        this.desc = desc;
        this.isStatic = isStatic;
    }

    public ProtectedMethodInvoker(String owner, String name, MethodDescription desc) {
        this(owner, name, desc, false);
    }

    public MethodInsnNode getNode() {
        int invoke = isStatic ? INVOKESTATIC : INVOKEVIRTUAL;
        return new MethodInsnNode(invoke, owner, name, desc.build(), false);
    }

    public MethodNode generate(String name) {
        MethodNode node = new MethodNode(ACC_PUBLIC | (isStatic ? ACC_STATIC : 0), name, desc.build(), null, null);
        InsnList list = node.instructions;
        if(!isStatic) list.add(new VarInsnNode(ALOAD, 0));
        for(int i = 0; i < desc.getArgumentsSize(); i++) {
            list.add(new VarInsnNode(ASMUtils.getLoadOpcodeFromType(desc.getArgument(i)), isStatic ? i : i + 1));
        }
        list.add(getNode());
        list.add(new InsnNode(ASMUtils.getReturnOpcodeFromType(desc.getReturnType())));
        return node;
    }

    public MethodNode generate() {
        return generate("call" + name.substring(0,1).toUpperCase() + name.substring(1));
    }
}
