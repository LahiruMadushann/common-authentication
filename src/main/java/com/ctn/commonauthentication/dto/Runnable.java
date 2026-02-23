package com.ctn.commonauthentication.dto;

public class Runnable {
    private boolean runnable ;
    private boolean accidentNg;
    private String moving;
    private String accident;

    public String getMoving() {
        return moving;
    }

    public void setMoving(String moving) {
        this.moving = moving;
    }

    public String getAccident() {
        return accident;
    }

    public void setAccident(String accident) {
        this.accident = accident;
    }

    public Runnable(boolean runnable, boolean accidentNg, String moving, String accident) {
        this.runnable = runnable;
        this.accidentNg = accidentNg;
        this.moving = moving;
        this.accident = accident;
    }

    public boolean move() {
        return this.runnable;
    }

    public boolean notMove() {
        return !(runnable && accidentNg);
    }

    public boolean nG() {
        if (accident.equals("NO") && moving.equals("YES")){
            return true;
        } else if (accident.equals("NO") && moving.equals("NO")) {
            return false;
        } else if (accident.equals("RESTORED") && moving.equals("YES")){
            return true;
        } else if (accident.equals("RESTORED") && moving.equals("NO")) {
            return false;
        } else if (accident.equals("NOT_RESTORED") && moving.equals("YES")){
            return false;
        } else if (accident.equals("NOT_RESTORED") && moving.equals("NO")) {
            return false;
        } else if (accident.equals("UNKNOWN") && moving.equals("YES")) {
            return true;
        }else {
            return false;
        }
    }

    public boolean oK() {
        if (accident.equals("NO") && moving.equals("YES")){
            return false;
        } else if (accident.equals("NO") && moving.equals("NO")) {
            return true;
        } else if (accident.equals("RESTORED") && moving.equals("YES")){
            return false;
        } else if (accident.equals("RESTORED") && moving.equals("NO")) {
            return true;
        } else if (accident.equals("NOT_RESTORED") && moving.equals("YES")){
            return true;
        } else if (accident.equals("NOT_RESTORED") && moving.equals("NO")) {
            return true;
        } else if (accident.equals("UNKNOWN") && moving.equals("YES")) {
            return false;
        }else {
            return true;
        }
    }


    public String runnable() {
        return this.moving;
    }

    public String accident() {
        return this.accident;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (runnable ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Runnable other = (Runnable) obj;
        if (runnable != other.runnable)
            return false;
        return true;
    }

}
