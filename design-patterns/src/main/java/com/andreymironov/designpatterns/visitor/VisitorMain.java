package com.andreymironov.designpatterns.visitor;

import java.util.List;

public class VisitorMain {
    public interface Vehicle {
        default boolean accept(VehicleAuditVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class Car implements Vehicle {
        private final int age;

        public Car(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        @Override
        public boolean accept(VehicleAuditVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class Bicycle implements Vehicle {
        private final boolean canRide;

        public Bicycle(boolean canRide) {
            this.canRide = canRide;
        }

        public boolean canRide() {
            return this.canRide;
        }
    }

    public static class VehicleAuditVisitor {
        public boolean visit(Vehicle vehicle) {
            if (vehicle instanceof Car car) {
                return car.getAge() < 10;
            } else if (vehicle instanceof Bicycle bicycle) {
                return bicycle.canRide();
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    public static void main(String[] args) {
        List<Vehicle> vehicles = List.of(
                new Car(11), new Bicycle(false)
        );

        VehicleAuditVisitor visitor = new VehicleAuditVisitor();
    }
}
