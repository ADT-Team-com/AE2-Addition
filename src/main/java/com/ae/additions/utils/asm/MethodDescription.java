package com.ae.additions.utils.asm;

import org.objectweb.asm.Type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodDescription {
    private List<Type> arguments;
    private Type returnType;

    public MethodDescription(Type returnType, Type... arguments) {
        this.returnType = returnType;
        this.arguments = Arrays.asList(arguments);
    }

    public MethodDescription(String returnType, String... arguments) {
        this.returnType = Type.getType(returnType);
        this.arguments = Arrays.stream(arguments).map(Type::getType).collect(Collectors.toList());
    }

    public String build() {
        StringBuilder desc = new StringBuilder("(");
        for(Type t : arguments) desc.append(t.getDescriptor());
        desc.append(")");
        desc.append(returnType.getDescriptor());
        return desc.toString();
    }

    public int getArgumentsSize() {
        return arguments.size();
    }

    public Type getArgument(int index) {
        return this.arguments.get(index);
    }

    public Type getReturnType() {
        return returnType;
    }
}
