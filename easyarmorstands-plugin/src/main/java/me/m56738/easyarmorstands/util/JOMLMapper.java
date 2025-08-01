package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.lib.joml.Quaternionf;
import me.m56738.easyarmorstands.lib.joml.Quaternionfc;
import me.m56738.easyarmorstands.lib.joml.Vector3f;
import me.m56738.easyarmorstands.lib.joml.Vector3fc;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class JOMLMapper {
    private final Class<?> vectorClass;
    private final MethodHandle vectorConstructor;
    private final MethodHandle vectorXGetter;
    private final MethodHandle vectorYGetter;
    private final MethodHandle vectorZGetter;
    private final MethodHandle quaternionConstructor;
    private final MethodHandle quaternionXGetter;
    private final MethodHandle quaternionYGetter;
    private final MethodHandle quaternionZGetter;
    private final MethodHandle quaternionWGetter;
    private final MethodHandle transformationConstructor;
    private final MethodHandle transformationTranslationGetter;
    private final MethodHandle transformationLeftRotationGetter;
    private final MethodHandle transformationScaleGetter;
    private final MethodHandle transformationRightRotationGetter;

    public JOMLMapper() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.publicLookup();
        String joml = String.join(".", "org", "joml");
        Class<?> transformationClass = Class.forName("org.bukkit.util.Transformation");
        this.vectorClass = Class.forName(joml + ".Vector3f");
        Class<?> quaternionClass = Class.forName(joml + ".Quaternionf");
        this.vectorConstructor = lookup.findConstructor(vectorClass,
                MethodType.methodType(void.class, float.class, float.class, float.class));
        this.vectorXGetter = lookup.findGetter(vectorClass, "x", float.class);
        this.vectorYGetter = lookup.findGetter(vectorClass, "y", float.class);
        this.vectorZGetter = lookup.findGetter(vectorClass, "z", float.class);
        this.quaternionConstructor = lookup.findConstructor(quaternionClass,
                MethodType.methodType(void.class, float.class, float.class, float.class, float.class));
        this.quaternionXGetter = lookup.findGetter(quaternionClass, "x", float.class);
        this.quaternionYGetter = lookup.findGetter(quaternionClass, "y", float.class);
        this.quaternionZGetter = lookup.findGetter(quaternionClass, "z", float.class);
        this.quaternionWGetter = lookup.findGetter(quaternionClass, "w", float.class);
        this.transformationConstructor = lookup.findConstructor(transformationClass,
                MethodType.methodType(void.class, vectorClass, quaternionClass, vectorClass, quaternionClass));
        this.transformationTranslationGetter = lookup.findVirtual(transformationClass, "getTranslation",
                MethodType.methodType(vectorClass));
        this.transformationLeftRotationGetter = lookup.findVirtual(transformationClass, "getLeftRotation",
                MethodType.methodType(quaternionClass));
        this.transformationScaleGetter = lookup.findVirtual(transformationClass, "getScale",
                MethodType.methodType(vectorClass));
        this.transformationRightRotationGetter = lookup.findVirtual(transformationClass, "getRightRotation",
                MethodType.methodType(quaternionClass));
    }

    public Class<?> getNativeVectorClass() {
        return vectorClass;
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

    public Object convertToNative(Quaternionfc quaternion) {
        try {
            return quaternionConstructor.invoke(quaternion.x(), quaternion.y(), quaternion.z(), quaternion.w());
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

    public Vector3f getTranslation(Object transformation) {
        try {
            return convertFromNativeVector(transformationTranslationGetter.invoke(transformation));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Quaternionf getLeftRotation(Object transformation) {
        try {
            return convertFromNativeQuaternion(transformationLeftRotationGetter.invoke(transformation));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Vector3f getScale(Object transformation) {
        try {
            return convertFromNativeVector(transformationScaleGetter.invoke(transformation));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Quaternionf getRightRotation(Object transformation) {
        try {
            return convertFromNativeQuaternion(transformationRightRotationGetter.invoke(transformation));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Object getTransformation(
            Vector3fc translation,
            Quaternionfc leftRotation,
            Vector3fc scale,
            Quaternionfc rightRotation
    ) {
        try {
            return transformationConstructor.invoke(
                    convertToNative(translation),
                    convertToNative(leftRotation),
                    convertToNative(scale),
                    convertToNative(rightRotation));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
