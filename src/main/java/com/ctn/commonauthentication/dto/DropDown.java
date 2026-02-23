package com.ctn.commonauthentication.dto;

public class DropDown {
    public int id;
    public int value;
    public String label;
    // add assessedEx
    public String assessedEx;
    public String name;
    public boolean stopped;

    public DropDown(int id, int value, String label) {
        this.id = id;
        this.value = value;
        this.label = label;
    }
    public DropDown(int id, String assessedEx, String name) {
        this.id = id;
        this.assessedEx = assessedEx;
        this.name = name;
    }

    public DropDown(int id, int value, String label,String assessedEx) {
        this.id = id;
        this.value = value;
        this.label = label;
        this.assessedEx = assessedEx;
    }

    public DropDown(int id, int value, String label, boolean stopped) {
        this.id = id;
        this.value = value;
        this.label = label;
        this.stopped = stopped;
    }
    public DropDown(int id, String assessedEx, String name, boolean stopped) {
        this.id = id;
        this.assessedEx = assessedEx;
        this.name = name;
        this.stopped = stopped;
    }

    public DropDown(int id, int value, String label,String assessedEx, boolean stopped) {
        this.id = id;
        this.value = value;
        this.label = label;
        this.assessedEx = assessedEx;
        this.stopped = stopped;
    }

    public DropDown(int id, int value, String label, String assessedEx, String name) {
        this.id = id;
        this.value = value;
        this.label = label;
        this.assessedEx = assessedEx;
        this.name = name;
    }

    public DropDown(int id, int value, String label, String assessedEx, String name, boolean stopped) {
        this.id = id;
        this.value = value;
        this.label = label;
        this.assessedEx = assessedEx;
        this.name = name;
        this.stopped = stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public boolean isStopped() {
        return this.stopped;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssessedEx() {
        return assessedEx;
    }

    public void setAssessedEx(String assessedEx) {
        this.assessedEx = assessedEx;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
