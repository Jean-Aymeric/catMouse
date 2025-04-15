package com.jad.house;

public class Mouse implements Runnable, Observer {
    private static int nextId = 1;
    private final int id;
    private MouseState state;

    public Mouse(final House house) {
        this.id = Mouse.getId();
        this.state = MouseState.HIDING;
        house.subscribe(this);
    }

    private static int getId() {
        return Mouse.nextId++;
    }

    public MouseState getState() {
        return this.state;
    }

    public void setState(final MouseState state) {
        this.state = state;
        System.out.println(this);
    }

    @Override
    public String toString() {
        return this.id + " <:)))" + ((this.state == MouseState.DANCING) ? "~~" : "--");
    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void notifyChange(Observable observable) {
        observable.isObserved(this);
    }

    @Override
    public void observe(final Observable observable) {
        observable.isObserved(this);
    }

    @Override
    public void observe(final House house) {
        if (house.isACatAwake()) {
            this.setState(MouseState.HIDING);
        } else {
            this.setState(MouseState.DANCING);
        }
    }
}
