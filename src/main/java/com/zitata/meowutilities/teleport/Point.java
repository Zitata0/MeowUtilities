package com.zitata.meowutilities.teleport;

public abstract class Point {

    private int dimension;
    private double x, y, z;
    private float rotationYawHead, rotationPitch;

    public Point(int dimension, double x, double y, double z, float rotationYawHead, float rotationPitch) {
        this.dimension = dimension;
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotationYawHead = rotationYawHead;
        this.rotationPitch = rotationPitch;
    }

    public void setPoint(int dimension, double x, double y, double z, float rotationYawHead, float rotationPitch) {
        this.dimension = dimension;
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotationYawHead = rotationYawHead;
        this.rotationPitch = rotationPitch;
    }

    public float getRotationYawHead() {
        return rotationYawHead;
    }

    public float getRotationPitch() {
        return rotationPitch;
    }

    public int getDimension() {
        return dimension;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setRotationYawHead(float rotationYawHead) {
        this.rotationYawHead = rotationYawHead;
    }

    public void setRotationPitch(float rotationPitch) {
        this.rotationPitch = rotationPitch;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
