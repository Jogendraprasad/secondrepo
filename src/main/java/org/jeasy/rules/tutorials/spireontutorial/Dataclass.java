package org.jeasy.rules.tutorials.spireontutorial;

public class Dataclass {

    private int speed;
    private int odometer;
    private int tyrepressure;
    public String companyname;

    public Dataclass(int speed,int odometer,int tyrepressure,String companyname) {
        this.speed = speed;
        this.odometer = odometer;
        this.tyrepressure=tyrepressure;
        this.companyname=companyname;
    }

    public int getSpeed() {
        return speed;
    }


    public int getOdometer() {
        return odometer;
    }

    public int getTyrepressure() {
        return tyrepressure;
    }


}
