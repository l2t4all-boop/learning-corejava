package com.careerit.cj.abs;

import java.util.concurrent.ThreadLocalRandom;

interface Game {
    void start();

    void play();

    void stop();
}


abstract class AbsGame implements Game {

    public void start() {
        System.out.println("Game :" + this.getClass().getSimpleName() + " started");
    }

    public void stop() {
        System.out.println("Game :" + this.getClass().getSimpleName() + " stopped");
    }
}

class Car extends AbsGame implements Game {

    public void start() {
        System.out.println("Game :" + this.getClass().getSimpleName() + " started, it is override method");
    }

    @Override
    public void play() {
        System.out.println("You are playing game :" + this.getClass().getSimpleName() + " please wear seat belt...");
    }

}

class Bike extends AbsGame implements Game {

    @Override
    public void play() {
        System.out.println("You are playing game :" + this.getClass().getSimpleName() + " please wear helmet ...");
    }

}

class Ship extends AbsGame implements Game {

    @Override
    public void play() {
        System.out.println("You are playing game :" + this.getClass().getSimpleName() + " please wear water jacket ...");
    }

}

public class GameManager {

    public void main(String[] args) {
        Game game = selectRandomGame();
        game.start();
        game.play();
        game.stop();
    }

    private static Game selectRandomGame() {
        int num = ThreadLocalRandom.current().nextInt(1, 4);
        return switch (num) {
            case 1 -> new Bike();
            case 2 -> new Car();
            case 3 -> new Ship();
            default -> throw new IllegalArgumentException("Unknown game selected");
        };
    }
}
