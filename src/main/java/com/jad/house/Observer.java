package com.jad.house;

public interface Observer {
    void notifyChange(Observable observable);

    void observe(Observable observable);

    default void observe(Cat cat) {
    }

    default void observe(House house) {
    }
}
