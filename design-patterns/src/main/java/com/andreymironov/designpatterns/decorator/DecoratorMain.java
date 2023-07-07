package com.andreymironov.designpatterns.decorator;

public class DecoratorMain {
    public interface Car {
        void startDrive();
    }

    public static class CarImpl implements Car {
        @Override
        public void startDrive() {
            System.out.println("Start driving...");
        }
    }

    public static class CarDecorator implements Car {
        private final Car car;

        public CarDecorator(Car car) {
            this.car = car;
        }

        @Override
        public void startDrive() {
            System.out.println("Checking for hand brake down...");
            car.startDrive();
        }
    }

    public static void main(String[] args) {
        // using car without decorator
        Car car = new CarImpl();
        car.startDrive();

        // using car with decorator
        car = new CarDecorator(new CarImpl());
        car.startDrive();
    }
}
