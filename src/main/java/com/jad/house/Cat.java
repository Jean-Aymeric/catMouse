package com.jad.house;

import java.util.Random;

public class Cat extends Observable implements Runnable {
    private static int nextId = 1;
    private final int id;
    private CatState state;

    public Cat(final House house) {
        this.id = Cat.getId();
        this.setState(CatState.SLEEPING);
    }

    private static int getId() {
        return Cat.nextId++;
    }

    public CatState getState() {
        return this.state;
    }

    private void setState(final CatState state) {
        if (this.state != state) {
            this.notifyObservers();
            this.state = state;
            System.out.println(this);
        }
    }

    @Override
    public String toString() {
        return this.id + ((this.state == CatState.SLEEPING) ? "(- -)" : "(° °)");
    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (new Random().nextInt() % 3 == 0) {
                this.setState(CatState.AWAKE);
            } else {
                this.setState(CatState.SLEEPING);
            }
        }
    }

    @Override
    void isObserved(final Observer observer) {
        observer.observe(this);
    }
}
