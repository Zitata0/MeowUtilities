package com.Zitata.MeowUtilities.Teleport.Patterns;

public abstract class Point {

    private int dimension;
    private double x, y, z;

    public Point(int dimension, double x, double y, double z){
        this.dimension = dimension;
        this.x = x;
        this.y = y;
        this.z = z;
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
