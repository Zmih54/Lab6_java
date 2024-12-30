# Mobile Tariffs Management System

Система управління тарифами мобільного зв'язку, реалізована на Java з використанням власної імплементації однозв'язного списку.

## Опис проєкту

Проєкт представляє собою систему для управління різними типами тарифів мобільного зв'язку. Реалізовано такі основні компоненти:

- Базовий абстрактний клас `MobileTariff<T>` для представлення тарифів
- Спеціалізовані класи тарифів:
  - `BasicTariff<T>`: базовий тариф з обмеженим трафіком
  - `PremiumTariff<T>`: преміум тариф з необмеженим трафіком
  - `FamilyTariff<T>`: сімейний тариф з можливістю додавання кількох ліній
- Власна імплементація колекції `TariffLinkedList<T>`, що базується на однозв'язному списку

## Особливості реалізації

- Використання узагальнених типів (generics) для гнучкості у роботі з різними типами ідентифікаторів тарифів
- Повна імплементація інтерфейсу `List`
- Три типи конструкторів для створення списку тарифів
- Детальна документація коду з використанням JavaDoc
- Обробка виключних ситуацій
- Відповідність стандартам Java Code Conventions

## Встановлення та запуск

1. Клонуйте репозиторій:
```bash
git clone https://github.com/your-username/mobile-tariffs.git
```

2. Перейдіть до директорії проєкту:
```bash
cd mobile-tariffs
```

3. Скомпілюйте проєкт:
```bash
javac *.java
```

4. Запустіть демонстраційний приклад:
```bash
java TariffManager
```

## Використання

### Створення нового списку тарифів

```java
// Порожній список
TariffLinkedList<String> emptyList = new TariffLinkedList<>();

// Список з одного тарифу
BasicTariff<String> tariff = new BasicTariff<>("B1", "Базовий", 100.0, 1000, 100, 5000, 0.5);
TariffLinkedList<String> singleList = new TariffLinkedList<>(tariff);

// Список з колекції
List<MobileTariff<String>> tariffs = new ArrayList<>();
tariffs.add(new BasicTariff<>("B2", "Економ", 75.0, 500, 50, 2000, 0.7));
tariffs.add(new PremiumTariff<>("P1", "Преміум", 500.0, 200, true, 1));
TariffLinkedList<String> listFromCollection = new TariffLinkedList<>(tariffs);
```

### Основні операції

```java
// Додавання нового тарифу
list.add(new FamilyTariff<>("F1", "Сімейний", 300.0, 150, 4, 50.0));

// Отримання тарифу за індексом
MobileTariff<String> tariff = list.get(0);

// Видалення тарифу
list.remove(0);

// Перевірка наявності тарифу
boolean contains = list.contains(tariff);
```

**Автор Демич Сергій**
