package com.ae.additions.utils.asm;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

public class FieldSetter {
    private final String owner;
    private final String name;
    private final Type type;
    private final boolean isStatic;

    public FieldSetter(String owner, String name, Type type, boolean isStatic) {
        this.owner = owner;
        this.name = name;
        this.type = type;
        this.isStatic = isStatic;
    }

    public FieldSetter(String owner, String name, Type type) {
        this(owner, name, type, false);
    }

    public FieldInsnNode getNode() {
        return new FieldInsnNode(isStatic ? PUTSTATIC : PUTFIELD, owner, name, type.getDescriptor());
    }

    public MethodNode generateSetterMethod(String name) {
        MethodNode node = new MethodNode(ACC_PUBLIC | (isStatic ? ACC_STATIC : 0), name, Type.getMethodDescriptor(Type.VOID_TYPE, type), null, null);
        InsnList list = node.instructions;
        if(!this.isStatic) list.add(new VarInsnNode(ALOAD, 0));
        list.add(new VarInsnNode(ASMUtils.getLoadOpcodeFromType(type), isStatic ? 0 : 1));
        list.add(getNode());
        list.add(new InsnNode(RETURN));
        return node;
    }

    public MethodNode generateSetterMethod() {
        return generateSetterMethod("set" + this.name.substring(0,1).toUpperCase() + this.name.substring(1));
    }
}
