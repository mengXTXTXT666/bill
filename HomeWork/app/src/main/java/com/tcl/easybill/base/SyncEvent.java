package com.tcl.easybill.base;

public class SyncEvent {

    /**
     * state : 100
     * message : success
     */

    private int state;
    private String message;

    SyncEvent(){

    }

    public SyncEvent(int state) {
        this.state = state;
    }

    public SyncEvent(int state, String message) {
        this.state = state;
        this.message = message;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
