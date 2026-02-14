package org.example.handlers;

public abstract class AbstractHandler implements Handler {

    protected boolean isOn;

    public AbstractHandler(){
        isOn = false;
    }

    @Override
    public void turnOff() {
        isOn = false;
    }

    @Override
    public void turnOn() {
        isOn = true;
    }

    @Override
    public boolean isOn() {
        return isOn;
    }
}
