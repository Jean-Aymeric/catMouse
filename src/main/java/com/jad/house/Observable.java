package com.jad.house;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private final List<Observer> observers = new ArrayList<>();

    void subscribe(Observer observer) {
        this.observers.add(observer);
    }

    void unsubscribe(Observer observer) {
        this.observers.remove(observer);
    }

    abstract void isObserved(Observer observer);

    void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.notifyChange(this);
        }
    }
}
