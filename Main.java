public class Main {
    public static void main(String[] args) {
        ToyStore toyStore = new ToyStore();

        toyStore.addOrUpdateToy(new Toy(1, "Кукла", 2, 10));
        toyStore.addOrUpdateToy(new Toy(2, "Машинка", 5, 30));
        toyStore.addOrUpdateToy(new Toy(3, "Мяч", 10, 70));

        System.out.println("Розыгрыш в нашем магазине!");
        toyStore.playGame();
    }
}
