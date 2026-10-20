/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_243
 */
package com.medievalmachinery.mod.client.render;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_243;

@Environment(value=EnvType.CLIENT)
public final class GeoBoneAnchorResolver {
    private GeoBoneAnchorResolver() {
    }

    public static class_243 resolveWorldPosition(Object bone) {
        if (bone == null) {
            return null;
        }
        Object vector = GeoBoneAnchorResolver.invokeNoArgs(bone, "getWorldPosition");
        if (vector == null) {
            vector = GeoBoneAnchorResolver.invokeNoArgs(bone, "getModelPosition");
        }
        if (vector == null) {
            return null;
        }
        Double x = GeoBoneAnchorResolver.component(vector, "x", "x");
        Double y = GeoBoneAnchorResolver.component(vector, "y", "y");
        Double z = GeoBoneAnchorResolver.component(vector, "z", "z");
        if (x == null || y == null || z == null) {
            return null;
        }
        return new class_243(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }

    private static Object invokeNoArgs(Object target, String name) {
        try {
            Method method = target.getClass().getMethod(name, new Class[0]);
            method.setAccessible(true);
            return method.invoke(target, new Object[0]);
        }
        catch (ReflectiveOperationException ignored) {
            return null;
        }
    }

    private static Double component(Object vector, String methodName, String fieldName) {
        Object byMethod = GeoBoneAnchorResolver.invokeNoArgs(vector, methodName);
        if (byMethod instanceof Number) {
            Number number = (Number)byMethod;
            return number.doubleValue();
        }
        try {
            Double d;
            Field field = vector.getClass().getField(fieldName);
            field.setAccessible(true);
            Object value = field.get(vector);
            if (value instanceof Number) {
                Number number = (Number)value;
                d = number.doubleValue();
            } else {
                d = null;
            }
            return d;
        }
        catch (ReflectiveOperationException ignored) {
            return null;
        }
    }
}

