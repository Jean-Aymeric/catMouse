package com.jad.house;

public class Mouse implements Runnable, Observer {
    private static int nextId = 1;
    private final int id;
    private final Cheese cheese;
    private final Observable observable;
    private MouseState state;
    private int lifePoints = 5;

    public Mouse(final House house) {
        this.id = Mouse.getId();
        this.state = MouseState.DANCING;
        house.subscribe(this);
        this.cheese = house.getCheese();
        this.observable = house;
    }

    private static int getId() {
        return Mouse.nextId++;
    }

    public MouseState getState() {
        return this.state;
    }

    public void setState(final MouseState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.id + " <:)))" + ((this.state == MouseState.DANCING) ? "~~ " : "-- ") + this.lifePoints;
    }

    @Override
    public void run() {
        while (this.isDead()) {
            try {
                if (this.state == MouseState.DANCING) {
                    this.cheese.consume();
                    this.lifePoints = 5;
                } else {
                    this.lifePoints--;
                    if (this.lifePoints <= 0) {
                        System.out.println(this + " is dead");
                        this.observable.unsubscribe(this);
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    private boolean isDead() {
        return this.lifePoints > 0;
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
        System.out.println(this);
    }
}
