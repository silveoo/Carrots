package com.silveo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CarrotCollecting {

    private static final int MAX_CARRY = 5;
    private static final int MAX_TRIPS = 10;

    public static void main(String[] args) {
        // Создание полянок
        List<Field> fields = generateFields(5);

        // Информация о полянках
        System.out.println("Полянки:");
        for (int i = 0; i < fields.size(); i++) {
            System.out.println("Полянка " + (i + 1) + ": " + fields.get(i)); // +toString
        }

        // Алгоритм и результаты
        System.out.println("\nВсего собрано моркови(шт): " + collectMaxCarrots(fields));
    }

    // Метод для генерации полянок с морковками
    private static List<Field> generateFields(int numFields) {
        List<Field> fields = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= numFields; i++) {
            // Рандомайзер морковок
            int numCarrots = random.nextInt(10) + 1;
            fields.add(new Field(i, numCarrots));
        }
        return fields;
    }

    // Основной метод
    private static int collectMaxCarrots(List<Field> fields) {
        int totalCarrots = 0;
        int currentTrip = 1;

        while (currentTrip <= MAX_TRIPS && !areFieldsEmpty(fields)) {
            int currentCarry = 0;
            System.out.println("\nХодка " + currentTrip + ":");

            Collections.sort(fields); // Сортировка по возрастанию веса

            for (Field field : fields) {
                // Проверяем, есть ли на полянке морковь
                while (field.hasCarrots()) {
                    // Проверяем, можем ли взять еще одну морковь
                    if (currentCarry + field.getCarrotWeight() <= MAX_CARRY) {
                        currentCarry += field.getCarrotWeight();
                        totalCarrots++;
                        field.removeCarrot();
                        System.out.println("Забрали морковь с полянки " + field.getId() +
                                ". Текущий вес: " + currentCarry);
                    } else {
                        // Если не можем взять больше моркови с этой полянки, переходим к следующей
                        break;
                    }
                }
            }

            currentTrip++;
        }

        return totalCarrots;
    }

    // Метод для проверки, все ли полянки пусты
    private static boolean areFieldsEmpty(List<Field> fields) {
        for (Field field : fields) {
            if (field.hasCarrots()) {
                return false;
            }
        }
        return true;
    }

    // Класс полянки с Comparable для правильной сортировки
    static class Field implements Comparable<Field> {
        private final int id;
        private int numCarrots;
        private final int carrotWeight;

        public Field(int id, int numCarrots) {
            this.id = id;
            this.numCarrots = numCarrots;
            this.carrotWeight = id;
        }

        public int getId() {
            return id;
        }

        public boolean hasCarrots() {
            return numCarrots > 0;
        }

        public void removeCarrot() {
            numCarrots--;
        }

        public int getCarrotWeight() {
            return carrotWeight;
        }

        @Override
        public String toString() {
            return "вес моркови: " + carrotWeight + "кг, количество: " + numCarrots;
        }

        @Override
        public int compareTo(Field other) {
            return Integer.compare(this.carrotWeight, other.carrotWeight);
        }
    }
}