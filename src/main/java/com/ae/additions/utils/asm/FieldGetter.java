package com.ae.additions.utils.asm;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

public class FieldGetter {
    private final String owner;
    private final String name;
    private final Type type;
    private final boolean isStatic;

    public FieldGetter(String owner, String name, Type type, boolean isStatic) {
        this.owner = owner;
        this.name = name;
        this.type = type;
        this.isStatic = isStatic;
    }

    public FieldGetter(String owner, String name, Type type) {
        this(owner, name, type, false);
    }

    public FieldInsnNode getNode() {
        return new FieldInsnNode(isStatic ? GETSTATIC : GETFIELD, owner, name, type.getDescriptor());
    }

    public MethodNode generateAccessorMethod(String name) {
        MethodNode node = new MethodNode(ACC_PUBLIC | (isStatic ? ACC_STATIC : 0), name, Type.getMethodDescriptor(type), null, null);
        InsnList list = node.instructions;
        if(!this.isStatic) list.add(new VarInsnNode(ALOAD, 0));
        list.add(getNode());
        list.add(new InsnNode(ASMUtils.getReturnOpcodeFromType(type)));
        return node;
    }

    public MethodNode generateAccessorMethod() {
        return generateAccessorMethod("get" + this.name.substring(0,1).toUpperCase() + this.name.substring(1));
    }
}
