package me.m56738.easyarmorstands.util.v1_19_4;

import org.bukkit.util.Transformation;
import org.joml.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JOMLMapper {
    private final MethodHandle vectorConstructor;
    private final MethodHandle vectorSetter;
    private final MethodHandle vectorXGetter;
    private final MethodHandle vectorYGetter;
    private final MethodHandle vectorZGetter;
    private final MethodHandle quaternionXGetter;
    private final MethodHandle quaternionYGetter;
    private final MethodHandle quaternionZGetter;
    private final MethodHandle quaternionWGetter;
    private final MethodHandle matrixConstructor;
    private final MethodHandle transformationConstructor;
    private final MethodHandle transformationTranslationGetter;
    private final MethodHandle transformationLeftRotationGetter;
    private final MethodHandle transformationScaleGetter;
    private final MethodHandle transformationRightRotationGetter;
    private final MethodHandle bukkitTransformationConstructor;
    private final MethodHandle bukkitTransformationTranslationGetter;
    private final MethodHandle bukkitTransformationLeftRotationGetter;
    private final MethodHandle bukkitTransformationScaleGetter;
    private final MethodHandle bukkitTransformationRightRotationGetter;

    public JOMLMapper() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.publicLookup();
        String joml = String.join(".", "org", "joml");
        Class<?> vectorClass = Class.forName(joml + ".Vector3f");
        Class<?> vectorConstClass = Class.forName(joml + ".Vector3fc");
        Class<?> quaternionClass = Class.forName(joml + ".Quaternionf");
        Class<?> matrixClass = Class.forName(joml + ".Matrix4f");
        Class<?> bukkitTransformationClass = Class.forName("com.mojang.math.Transformation");
        this.vectorConstructor = lookup.findConstructor(vectorClass,
                MethodType.methodType(void.class, float.class, float.class, float.class));
        this.vectorSetter = lookup.findVirtual(vectorClass, "set", MethodType.methodType(vectorClass, vectorConstClass));
        this.vectorXGetter = lookup.findGetter(vectorClass, "x", float.class);
        this.vectorYGetter = lookup.findGetter(vectorClass, "y", float.class);
        this.vectorZGetter = lookup.findGetter(vectorClass, "z", float.class);
        this.quaternionXGetter = lookup.findGetter(quaternionClass, "x", float.class);
        this.quaternionYGetter = lookup.findGetter(quaternionClass, "y", float.class);
        this.quaternionZGetter = lookup.findGetter(quaternionClass, "z", float.class);
        this.quaternionWGetter = lookup.findGetter(quaternionClass, "w", float.class);
        this.matrixConstructor = lookup.findConstructor(matrixClass,
                MethodType.methodType(void.class,
                        float.class, float.class, float.class, float.class,
                        float.class, float.class, float.class, float.class,
                        float.class, float.class, float.class, float.class,
                        float.class, float.class, float.class, float.class));
        this.transformationConstructor = lookup.findConstructor(Transformation.class,
                MethodType.methodType(void.class, vectorClass, quaternionClass, vectorClass, quaternionClass));
        this.transformationTranslationGetter = lookup.findVirtual(Transformation.class, "getTranslation",
                MethodType.methodType(vectorClass));
        this.transformationLeftRotationGetter = lookup.findVirtual(Transformation.class, "getLeftRotation",
                MethodType.methodType(quaternionClass));
        this.transformationScaleGetter = lookup.findVirtual(Transformation.class, "getScale",
                MethodType.methodType(vectorClass));
        this.transformationRightRotationGetter = lookup.findVirtual(Transformation.class, "getRightRotation",
                MethodType.methodType(quaternionClass));
        this.bukkitTransformationConstructor = lookup.findConstructor(bukkitTransformationClass,
                MethodType.methodType(void.class, matrixClass));
        this.bukkitTransformationTranslationGetter = lookup.unreflect(
                findGetter(bukkitTransformationClass, vectorClass, 0));
        this.bukkitTransformationLeftRotationGetter = lookup.unreflect(
                findGetter(bukkitTransformationClass, quaternionClass, 0));
        this.bukkitTransformationScaleGetter = lookup.unreflect(
                findGetter(bukkitTransformationClass, vectorClass, 1));
        this.bukkitTransformationRightRotationGetter = lookup.unreflect(
                findGetter(bukkitTransformationClass, quaternionClass, 1));
    }

    private static Method findGetter(Class<?> owner, Class<?> type, int index) throws NoSuchMethodException {
        List<Method> methods = new ArrayList<>();
        for (Method method : owner.getDeclaredMethods()) {
            if (method.getReturnType() != type || method.getParameterCount() != 0) {
                continue;
            }
            int modifiers = method.getModifiers();
            if (!Modifier.isPublic(modifiers) || Modifier.isStatic(modifiers)) {
                continue;
            }
            methods.add(method);
        }
        methods.sort(Comparator.comparing(Method::getName));
        if (index >= methods.size()) {
            throw new NoSuchMethodException();
        }
        return methods.get(index);
    }

    public Object convertToNative(Vector3fc vector) {
        try {
            return vectorConstructor.invoke(vector.x(), vector.y(), vector.z());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Vector3f convertFromNativeVector(Object vector) {
        try {
            return new Vector3f(
                    (float) vectorXGetter.invoke(vector),
                    (float) vectorYGetter.invoke(vector),
                    (float) vectorZGetter.invoke(vector)
            );
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Object convertToNative(Matrix4fc m) {
        try {
            return matrixConstructor.invoke(
                    m.m00(), m.m01(), m.m02(), m.m03(),
                    m.m10(), m.m11(), m.m12(), m.m13(),
                    m.m20(), m.m21(), m.m22(), m.m23(),
                    m.m30(), m.m31(), m.m32(), m.m33());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Quaternionf convertFromNativeQuaternion(Object quaternion) {
        try {
            return new Quaternionf(
                    (float) quaternionXGetter.invoke(quaternion),
                    (float) quaternionYGetter.invoke(quaternion),
                    (float) quaternionZGetter.invoke(quaternion),
                    (float) quaternionWGetter.invoke(quaternion)
            );
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Vector3f getTranslation(Transformation transformation) {
        try {
            return convertFromNativeVector(transformationTranslationGetter.invoke(transformation));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void setTranslation(Transformation transformation, Vector3fc translation) {
        try {
            vectorSetter.invoke(transformationTranslationGetter.invoke(transformation), convertToNative(translation));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Quaternionf getLeftRotation(Transformation transformation) {
        try {
            return convertFromNativeQuaternion(transformationLeftRotationGetter.invoke(transformation));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Vector3f getScale(Transformation transformation) {
        try {
            return convertFromNativeVector(transformationScaleGetter.invoke(transformation));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Quaternionf getRightRotation(Transformation transformation) {
        try {
            return convertFromNativeQuaternion(transformationRightRotationGetter.invoke(transformation));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Matrix4f getMatrix(Transformation transformation) {
        Matrix4f matrix = new Matrix4f();
        matrix.translation(getTranslation(transformation));
        matrix.rotate(getLeftRotation(transformation));
        matrix.scale(getScale(transformation));
        matrix.rotate(getRightRotation(transformation));
        return matrix;
    }

    public Transformation getTransformation(Matrix4fc m) {
        try {
            Object bukkitTransformation = bukkitTransformationConstructor.invoke(convertToNative(m));
            return (Transformation) transformationConstructor.invoke(
                    bukkitTransformationTranslationGetter.invoke(bukkitTransformation),
                    bukkitTransformationLeftRotationGetter.invoke(bukkitTransformation),
                    bukkitTransformationScaleGetter.invoke(bukkitTransformation),
                    bukkitTransformationRightRotationGetter.invoke(bukkitTransformation));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
