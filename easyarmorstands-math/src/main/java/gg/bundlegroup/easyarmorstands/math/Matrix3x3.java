package gg.bundlegroup.easyarmorstands.math;

import org.bukkit.util.EulerAngle;

import java.util.Objects;

public final class Matrix3x3 {
    private final double m00, m01, m02;
    private final double m10, m11, m12;
    private final double m20, m21, m22;

    public static final Matrix3x3 IDENTITY = new Matrix3x3(
            1, 0, 0,
            0, 1, 0,
            0, 0, 1);

    public Matrix3x3(double m00, double m01, double m02,
                     double m10, double m11, double m12,
                     double m20, double m21, double m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    }

    public Matrix3x3(double[] values) {
        this(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8]);
    }

    public Matrix3x3(Quaternion quaternion) {
        final double x = quaternion.x(), y = quaternion.y(), z = quaternion.z(), w = quaternion.w();
        final double xx = x * x, yy = y * y, zz = z * z;
        final double xy = x * y, xz = x * z, yz = y * z;
        final double xw = x * w, yw = y * w, zw = z * w;
        this.m00 = 1 - 2 * (yy + zz);
        this.m01 = 2 * (xy - zw);
        this.m02 = 2 * (xz + yw);
        this.m10 = 2 * (xy + zw);
        this.m11 = 1 - 2 * (xx + zz);
        this.m12 = 2 * (yz - xw);
        this.m20 = 2 * (xz - yw);
        this.m21 = 2 * (yz + xw);
        this.m22 = 1 - 2 * (xx + yy);
    }

    public Matrix3x3(Vector3 forward, Vector3 up) {
        forward = forward.normalize();
        Vector3 left = up.cross(forward).normalize();
        up = forward.cross(left).normalize();
        this.m00 = left.x();
        this.m01 = up.x();
        this.m02 = forward.x();
        this.m10 = left.y();
        this.m11 = up.y();
        this.m12 = forward.y();
        this.m20 = left.z();
        this.m21 = up.z();
        this.m22 = forward.z();
    }

    public Matrix3x3(double pitch, double yaw, double roll) {
        double cx = Math.cos(pitch);
        double sx = Math.sin(pitch);
        double cy = Math.cos(yaw);
        double sy = Math.sin(yaw);
        double cz = Math.cos(roll);
        double sz = Math.sin(roll);

        //  1   0   0
        //  0   cx -sx
        //  0   sx  cx

        //  cy  0  -sy
        //  0   1   0
        //  sy  0   cy

        //  cz  sz  0
        // -sz  cz  0
        //  0   0   1

        //  cz  sz  0     cy  0  -sy     1   0   0      x
        // -sz  cz  0  *  0   1   0   *  0   cx -sx  *  y
        //  0   0   1     sy  0   cy     0   sx  cx     z

        //  cz  sz  0     cy  0  -sy     x
        // -sz  cz  0  *  0   1   0   *  cx * y - sx * z
        //  0   0   1     sy  0   cy     sx * y + cx * z

        //  cz  sz  0     cy * x - sy * (sx * y + cx * z)
        // -sz  cz  0  *  cx * y - sx * z
        //  0   0   1     sy * x + cy * (sx * y + cx * z)

        //  cz * (cy * x - sy * (sx * y + cx * z)) + sz * (cx * y - sx * z)
        // -sz * (cy * x - sy * (sx * y + cx * z)) + cz * (cx * y - sx * z)
        //  sy * x + cy * (sx * y + cx * z)

        //  cz * cy * x - cz * sy * sx * y - cz * sy * cx * z + sz * cx * y - sz * sx * z
        // -sz * cy * x + sz * sy * sx * y + sz * sy * cx * z + cz * cx * y - cz * sx * z
        //  sy * x + cy * sx * y + cy * cx * z

        this.m00 = cy * cz;
        this.m01 = cx * sz - cz * sx * sy;
        this.m02 = -cx * cz * sy - sx * sz;

        this.m10 = -cy * sz;
        this.m11 = sx * sy * sz + cx * cz;
        this.m12 = cx * sy * sz - cz * sx;

        this.m20 = sy;
        this.m21 = cy * sx;
        this.m22 = cx * cy;
    }

    public Matrix3x3(double pitch, double yaw) {
        double cx = Math.cos(pitch);
        double sx = Math.sin(pitch);
        double cy = Math.cos(yaw);
        double sy = Math.sin(yaw);

        this.m00 = cy;
        this.m01 = -sx * sy;
        this.m02 = -cx * sy;

        this.m10 = 0;
        this.m11 = cx;
        this.m12 = -sx;

        this.m20 = sy;
        this.m21 = cy * sx;
        this.m22 = cx * cy;
    }

    public EulerAngle getEulerAngle() {
        double pitch = Math.atan2(m21, m22);
        double yaw = Math.asin(m20);
        double roll = Math.atan2(-m10, m00);
        return new EulerAngle(pitch, yaw, roll);
    }

    public Matrix3x3(EulerAngle angle) {
        this(angle.getX(), angle.getY(), angle.getZ());
    }

    public static Matrix3x3 rotateX(double angle) {
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        return new Matrix3x3(
                1, 0, 0,
                0, c, -s,
                0, s, c
        );
    }

    public static Matrix3x3 rotateY(double angle) {
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        return new Matrix3x3(
                c, 0, -s,
                0, 1, 0,
                s, 0, c
        );
    }

    public static Matrix3x3 rotateZ(double angle) {
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        return new Matrix3x3(
                c, s, 0,
                -s, c, 0,
                0, 0, 1
        );
    }

    public Matrix3x3 multiply(Matrix3x3 other) {
        return new Matrix3x3(
                m00 * other.m00 + m01 * other.m10 + m02 * other.m20,
                m00 * other.m01 + m01 * other.m11 + m02 * other.m21,
                m00 * other.m02 + m01 * other.m12 + m02 * other.m22,
                m10 * other.m00 + m11 * other.m10 + m12 * other.m20,
                m10 * other.m01 + m11 * other.m11 + m12 * other.m21,
                m10 * other.m02 + m11 * other.m12 + m12 * other.m22,
                m20 * other.m00 + m21 * other.m10 + m22 * other.m20,
                m20 * other.m01 + m21 * other.m11 + m22 * other.m21,
                m20 * other.m02 + m21 * other.m12 + m22 * other.m22
        );
    }

    public Vector3 multiply(Vector3 other) {
        final double x = other.x(), y = other.y(), z = other.z();
        return new Vector3(
                m00 * x + m01 * y + m02 * z,
                m10 * x + m11 * y + m12 * z,
                m20 * x + m21 * y + m22 * z
        );
    }

    public Matrix3x3 transpose() {
        return new Matrix3x3(
                m00, m10, m20,
                m01, m11, m21,
                m02, m12, m22
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix3x3 matrix3x3 = (Matrix3x3) o;
        return Double.compare(matrix3x3.m00, m00) == 0 && Double.compare(matrix3x3.m01, m01) == 0 && Double.compare(matrix3x3.m02, m02) == 0 && Double.compare(matrix3x3.m10, m10) == 0 && Double.compare(matrix3x3.m11, m11) == 0 && Double.compare(matrix3x3.m12, m12) == 0 && Double.compare(matrix3x3.m20, m20) == 0 && Double.compare(matrix3x3.m21, m21) == 0 && Double.compare(matrix3x3.m22, m22) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m00, m01, m02, m10, m11, m12, m20, m21, m22);
    }

    @Override
    public String toString() {
        return "Matrix3x3((" +
                m00 + ", " + m01 + ", " + m02 + "), (" +
                m10 + ", " + m11 + ", " + m12 + "), (" +
                m20 + ", " + m21 + ", " + m22 + "))";
    }

    public double[] getValues() {
        return new double[]{
                m00, m01, m02,
                m10, m11, m12,
                m20, m21, m22
        };
    }
}
