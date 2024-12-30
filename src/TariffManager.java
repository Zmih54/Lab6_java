import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Абстрактний клас, що представляє базовий тариф мобiльного зв'язку.
 *
 * @param <T> тип даних для iдентифiкатора тарифу
 */
abstract class MobileTariff<T> implements Comparable<MobileTariff<T>> {
    private T tariffId;
    private String name;
    private double monthlyFee;
    private int numberOfClients;
    
    /**
     * Конструктор базового тарифу.
     *
     * @param tariffId унiкальний iдентифiкатор тарифу
     * @param name назва тарифу
     * @param monthlyFee щомiсячна плата
     * @param numberOfClients кiлькiсть клiєнтiв
     */
    public MobileTariff(T tariffId, String name, double monthlyFee, int numberOfClients) {
        if (monthlyFee < 0) {
            throw new IllegalArgumentException("Щомiсячна плата не може бути вiд'ємною");
        }
        if (numberOfClients < 0) {
            throw new IllegalArgumentException("Кiлькiсть клiєнтiв не може бути вiд'ємною");
        }
        
        this.tariffId = tariffId;
        this.name = name;
        this.monthlyFee = monthlyFee;
        this.numberOfClients = numberOfClients;
    }

    // Геттери
    public T getTariffId() { return tariffId; }
    public String getName() { return name; }
    public double getMonthlyFee() { return monthlyFee; }
    public int getNumberOfClients() { return numberOfClients; }

    /**
     * Метод для отримання повної вартостi послуг.
     *
     * @return повна вартiсть послуг
     */
    public abstract double getTotalCost();

    @Override
    public int compareTo(MobileTariff<T> other) {
        return Double.compare(this.monthlyFee, other.monthlyFee);
    }

    @Override
    public String toString() {
        return String.format("Тариф '%s' (ID: %s): %.2f грн/мiс, %d клiєнтiв",
                name, tariffId, monthlyFee, numberOfClients);
    }
}

/**
 * Клас, що представляє базовий тариф з обмеженим трафiком.
 */
class BasicTariff<T> extends MobileTariff<T> {


    /**
     * Конструктор базового тарифу.
     *
     * @param tariffId унiкальний iдентифiкатор тарифу
     * @param name назва тарифу
     * @param monthlyFee щомiсячна плата
     * @param numberOfClients кiлькiсть клiєнтiв
     */
    public BasicTariff(T tariffId, String name, double monthlyFee, int numberOfClients,
                      int includedMinutes, int includedMegabytes, double extraMinutesCost) {
        super(tariffId, name, monthlyFee, numberOfClients);

    }

    @Override
    public double getTotalCost() {
        return getMonthlyFee(); // Базова вартiсть без додаткових послуг
    }
}

/**
 * Клас, що представляє премiум тариф з необмеженим трафiком.
 */
class PremiumTariff<T> extends MobileTariff<T> {
    private boolean includesRoaming;


    /**
     * Конструктор премiум тарифу.
     *
     * @param tariffId унiкальний iдентифiкатор тарифу
     * @param name назва тарифу
     * @param monthlyFee щомiсячна плата
     * @param numberOfClients кiлькiсть клiєнтiв
     * @param includesRoaming чи включений роумiнг
     */
    public PremiumTariff(T tariffId, String name, double monthlyFee, int numberOfClients,
                        boolean includesRoaming, int prioritySupport) {
        super(tariffId, name, monthlyFee, numberOfClients);
        this.includesRoaming = includesRoaming;
    }

    @Override
    public double getTotalCost() {
        return getMonthlyFee() + (includesRoaming ? 100 : 0);
    }
}

/**
 * Клас, що представляє сiмейний тариф.
 */
class FamilyTariff<T> extends MobileTariff<T> {
    private int numberOfLines;
    private double perLineCost;

    /**
     * Конструктор сiмейного тарифу.
     *
     * @param tariffId унiкальний iдентифiкатор тарифу
     * @param name назва тарифу
     * @param monthlyFee щомiсячна плата
     * @param numberOfClients кiлькiсть клiєнтiв
     * @param numberOfLines кiлькiсть лiнiй
     * @param perLineCost вартiсть кожної додаткової лiнiї
     */
    public FamilyTariff(T tariffId, String name, double monthlyFee, int numberOfClients,
                       int numberOfLines, double perLineCost) {
        super(tariffId, name, monthlyFee, numberOfClients);
        this.numberOfLines = numberOfLines;
        this.perLineCost = perLineCost;
    }

    @Override
    public double getTotalCost() {
        return getMonthlyFee() + (numberOfLines - 1) * perLineCost;
    }
}

/**
 * Клас для управлiння тарифами мобiльної компанiї.
 */
public class TariffManager {
    private List<MobileTariff<String>> tariffs;

    /**
     * Конструктор менеджера тарифiв.
     */
    public TariffManager() {
        tariffs = new ArrayList<>();
    }

    /**
     * Додає новий тариф до списку.
     *
     * @param tariff тариф для додавання
     */
    public void addTariff(MobileTariff<String> tariff) {
        tariffs.add(tariff);
    }

    /**
     * Пiдраховує загальну кiлькiсть клiєнтiв.
     *
     * @return загальна кiлькiсть клiєнтiв
     */
    public int getTotalClients() {
        return tariffs.stream()
                .mapToInt(MobileTariff::getNumberOfClients)
                .sum();
    }

    /**
     * Сортує тарифи за розмiром абонплати.
     */
    public void sortByMonthlyFee() {
        Collections.sort(tariffs);
    }

    /**
     * Шукає тарифи у заданому цiновому дiапазонi.
     *
     * @param minCost мiнiмальна вартiсть
     * @param maxCost максимальна вартiсть
     * @return список тарифiв у заданому дiапазонi
     */
    public List<MobileTariff<String>> findTariffsByPriceRange(double minCost, double maxCost) {
        if (minCost < 0 || maxCost < 0) {
            throw new IllegalArgumentException("Цiновий дiапазон не може бути вiд'ємним");
        }
        if (minCost > maxCost) {
            throw new IllegalArgumentException("Мiнiмальна цiна не може бути бiльшою за максимальну");
        }
        
        return tariffs.stream()
                .filter(t -> t.getMonthlyFee() >= minCost && t.getMonthlyFee() <= maxCost)
                .collect(Collectors.toList());
    }

    /**
     * Виводить список тарифiв.
     */
    public void printTariffs() {
        tariffs.forEach(System.out::println);
    }

    /**
     * Виконавчий метод програми.
     *
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        try {
            TariffManager manager = new TariffManager();

            // Додавання тарифiв
            manager.addTariff(new BasicTariff<>("B1", "Базовий", 100.0, 1000, 
                100, 5000, 0.5));
            manager.addTariff(new PremiumTariff<>("P1", "Премiум", 500.0, 200, 
                true, 1));
            manager.addTariff(new FamilyTariff<>("F1", "Сiмейний", 300.0, 150, 
                4, 50.0));

            // Виведення початкового списку
            System.out.println("Список тарифiв:");
            manager.printTariffs();

            // Пiдрахунок клiєнтiв
            System.out.println("\nЗагальна кiлькiсть клiєнтiв: " + 
                manager.getTotalClients());

            // Сортування за абонплатою
            manager.sortByMonthlyFee();
            System.out.println("\nВiдсортованi тарифи за абонплатою:");
            manager.printTariffs();

            // Пошук тарифiв у цiновому дiапазонi
            double minCost = 200.0;
            double maxCost = 400.0;
            System.out.printf("\nТарифи з абонплатою у дiапазонi %.2f - %.2f грн:\n", minCost, maxCost);
            List<MobileTariff<String>> foundTariffs = 
                manager.findTariffsByPriceRange(minCost, maxCost);
            if (foundTariffs.isEmpty()) {
                System.out.println("Тарифiв у заданому дiапазонi не знайдено");
            } else {
                foundTariffs.forEach(System.out::println);
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Помилка введення даних: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Виникла непередбачена помилка: " + e.getMessage());
        }
    }
}