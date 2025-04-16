package com.jad.house;

import java.util.ArrayList;
import java.util.List;

public class House extends Observable implements Observer {
    private final List<Mouse> mouses = new ArrayList<>();
    private final List<Cat> cats = new ArrayList<>();
    private final Cheese cheese = new Cheese();
    private boolean isACatAwake = false;

    public House(int nbMouses, int nbCat) {
        for (int i = 0; i < nbMouses; i++) {
            this.mouses.add(new Mouse(this));
        }
        for (int i = 0; i < nbCat; i++) {
            final Cat cat = new Cat(this);
            this.cats.add(cat);
            cat.subscribe(this);
        }
        for (Mouse mouse : this.mouses) {
            new Thread(mouse).start();
        }
        for (Cat cat : this.cats) {
            new Thread(cat).start();
        }
    }

    public boolean isACatAwake() {
        return this.isACatAwake;
    }

    private void setACatAwake(final boolean ACatAwake) {
        if (this.isACatAwake != ACatAwake) {
            this.isACatAwake = ACatAwake;
            this.notifyObservers();
        }
    }

    @Override
    public void notifyChange(final Observable observable) {
        this.observe(observable);
    }

    @Override
    public void observe(final Observable observable) {
        observable.isObserved(this);
    }

    public void observe(final Cat cat) {
        if (cat.getState() == CatState.AWAKE) {
            this.setACatAwake(true);
        } else if (!this.isAtLeastOneCatAwake()) {
            this.setACatAwake(false);
        }
    }

    private boolean isAtLeastOneCatAwake() {
        for (Cat cat : this.cats) {
            if (cat.getState() == CatState.AWAKE) {
                return true;
            }
        }
        return false;
    }

    @Override
    void isObserved(final Observer observer) {
        observer.observe(this);
    }

    public Cheese getCheese() {
        return this.cheese;
    }
}
