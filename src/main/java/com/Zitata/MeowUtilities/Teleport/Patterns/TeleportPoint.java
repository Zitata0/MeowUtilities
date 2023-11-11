package com.Zitata.MeowUtilities.Teleport.Patterns;

public class TeleportPoint extends Point {

    private String name;
    private boolean publicPoint;

    public TeleportPoint(String name, int dimension, double x, double y, double z, boolean publicPoint) {
        super(dimension, x, y, z);
        this.name = name;
        this.publicPoint = publicPoint;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public boolean isPublicPoint() {
        return publicPoint;
    }

    public void setPublicPoint(boolean publicPoint) {
        this.publicPoint = publicPoint;
    }

}
