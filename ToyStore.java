import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ToyStore {
    private List<Toy> toys;

    public ToyStore() {
        toys = new ArrayList<>();
    }

    // метод добавления новых игрушек и изменения веса
    public void addOrUpdateToy(Toy toy) {
        // если игрушка уже существует, обновляем ее
        for (Toy t : toys) {
            if (t.getId() == toy.getId()) {
                t.setQuantity(toy.getQuantity());
                t.setWeight(toy.getWeight());
                return;
            }
        }
        // иначе добавляем новую игрушку
        toys.add(toy);
    }

    // метод для выбора призовой игрушки
    public Toy chooseToy() {
        // удаляем игрушки с количеством 0
        removeEmptyToys();

        double totalWeight = 0;
        for (Toy toy : toys) {
            totalWeight += toy.getWeight();
        }

        Random random = new Random();
        double randomWeight = random.nextDouble() * totalWeight;

        double cumulativeWeight = 0;
        for (Toy toy : toys) {
            cumulativeWeight += toy.getWeight();
            if (randomWeight < cumulativeWeight) {
                toy.setQuantity(toy.getQuantity() - 1);
                return toy;
            }
        }

        return null; // если список игрушек пуст
    }

    // метод для записи призовой игрушки в файл
    public void writeToFile(Toy toy) {
        try {
            FileWriter writer = new FileWriter("prize.txt", true);
            writer.write("Приз: " + toy.getName() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // метод для отображения списка игрушек и их количества в консоли
    public void displayToys() {
        System.out.println("Ты можешь выиграть одну из этих игрушек:");
        for (Toy toy : toys) {
            if (toy.getQuantity() > 0) {
                System.out.println(toy.getName() + " (Количество: " + toy.getQuantity() + ")");
            }
        }
        System.out.println("Нажми любую кнопку, чтобы поучаствовать.");
    }

    // метод для взаимодействия с пользователем
    public void playGame() {
        try (Scanner scanner = new Scanner(System.in)) {
            displayToys();
            scanner.nextLine(); // ожидаем ввода пользователя

            Toy prizeToy = chooseToy();
            if (prizeToy != null) {
                System.out.println("Вы выиграли: " + prizeToy.getName());
                writeToFile(prizeToy);
            } else if (toys.isEmpty()) {
                System.out.println("Все игрушки розыграны. Спасибо за участие!");
                return;
            } else System.out.println("Ошибка при розыгрыше");

            System.out.println("Попробовать еще? (Y/N)");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("N")) {
                System.out.println("Спасибо за участие!");
            } else {
                playGame(); // рекурсивно вызываем метод для продолжения игры
            }
        }
    }

    // метод для удаления игрушек с количеством 0
    private void removeEmptyToys() {
        Iterator<Toy> iterator = toys.iterator();
        while (iterator.hasNext()) {
            Toy toy = iterator.next();
            if (toy.getQuantity() == 0) {
                iterator.remove();
            }
        }
    }
}
