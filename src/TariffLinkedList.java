import java.util.*;

/**
 * Реалiзацiя однозв'язного списку для зберiгання тарифiв мобiльного зв'язку.
 * Клас iмплементує iнтерфейс List та пiдтримує узагальненi типи.
 *
 * @param <T> тип iдентифiкатора тарифу
 */
public class TariffLinkedList<T> implements List<MobileTariff<T>> {
    
    /**
     * Внутрiшнiй клас, що представляє вузол списку.
     */
    private class Node {
        MobileTariff<T> data;
        Node next;

        Node(MobileTariff<T> data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    /**
     * Конструктор за замовчуванням.
     * Створює порожнiй список.
     */
    public TariffLinkedList() {
        head = null;
        size = 0;
    }

    /**
     * Конструктор, що створює список з одного тарифу.
     *
     * @param tariff початковий тариф
     * @throws NullPointerException якщо тариф null
     */
    public TariffLinkedList(MobileTariff<T> tariff) {
        Objects.requireNonNull(tariff, "Тариф не може бути null");
        head = new Node(tariff);
        size = 1;
    }

    /**
     * Конструктор, що створює список з колекцiї тарифiв.
     *
     * @param collection колекцiя тарифiв
     * @throws NullPointerException якщо колекцiя null
     */
    public TariffLinkedList(Collection<? extends MobileTariff<T>> collection) {
        Objects.requireNonNull(collection, "Колекцiя не може бути null");
        for (MobileTariff<T> tariff : collection) {
            add(tariff);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof MobileTariff<?>)) {
            return false;
        }
        
        Node current = head;
        while (current != null) {
            if (Objects.equals(current.data, o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public Iterator<MobileTariff<T>> iterator() {
        return new Iterator<MobileTariff<T>>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public MobileTariff<T> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                MobileTariff<T> data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int index = 0;
        Node current = head;
        while (current != null) {
            array[index++] = current.data;
            current = current.next;
        }
        return array;
    }

    @Override
    public <E> E[] toArray(E[] a) {
        if (a.length < size) {
            @SuppressWarnings("unchecked")
            E[] newArray = (E[]) Arrays.copyOf(toArray(), size, a.getClass());
            return newArray;
        }
        System.arraycopy(toArray(), 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean add(MobileTariff<T> tariff) {
        Objects.requireNonNull(tariff, "Тариф не може бути null");
        
        Node newNode = new Node(tariff);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof MobileTariff<?>)) {
            return false;
        }

        if (head == null) {
            return false;
        }

        if (Objects.equals(head.data, o)) {
            head = head.next;
            size--;
            return true;
        }

        Node current = head;
        while (current.next != null) {
            if (Objects.equals(current.next.data, o)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends MobileTariff<T>> c) {
        boolean modified = false;
        for (MobileTariff<T> tariff : c) {
            if (add(tariff)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean addAll(int index, Collection<? extends MobileTariff<T>> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("iндекс: " + index + ", Розмiр: " + size);
        }

        Object[] array = c.toArray();
        if (array.length == 0) {
            return false;
        }

        for (int i = array.length - 1; i >= 0; i--) {
            add(index, (MobileTariff<T>) array[i]);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object element : c) {
            modified |= remove(element);
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<MobileTariff<T>> iterator = iterator();
        while (iterator.hasNext()) {
            if (!c.contains(iterator.next())) {
                iterator.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public MobileTariff<T> get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("iндекс: " + index + ", Розмiр: " + size);
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    @Override
    public MobileTariff<T> set(int index, MobileTariff<T> element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("iндекс: " + index + ", Розмiр: " + size);
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        MobileTariff<T> oldValue = current.data;
        current.data = element;
        return oldValue;
    }

    @Override
    public void add(int index, MobileTariff<T> element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("iндекс: " + index + ", Розмiр: " + size);
        }

        Node newNode = new Node(element);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    @Override
    public MobileTariff<T> remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("iндекс: " + index + ", Розмiр: " + size);
        }

        MobileTariff<T> removedValue;
        if (index == 0) {
            removedValue = head.data;
            head = head.next;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            removedValue = current.next.data;
            current.next = current.next.next;
        }
        size--;
        return removedValue;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (!(o instanceof MobileTariff<?>)) {
            return -1;
        }

        Node current = head;
        while (current != null) {
            if (Objects.equals(current.data, o)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (!(o instanceof MobileTariff<?>)) {
            return -1;
        }

        int lastIndex = -1;
        int currentIndex = 0;
        Node current = head;
        while (current != null) {
            if (Objects.equals(current.data, o)) {
                lastIndex = currentIndex;
            }
            current = current.next;
            currentIndex++;
        }
        return lastIndex;
    }

    @Override
    public ListIterator<MobileTariff<T>> listIterator() {
        throw new UnsupportedOperationException("Операцiя listIterator не пiдтримується");
    }

    @Override
    public ListIterator<MobileTariff<T>> listIterator(int index) {
        throw new UnsupportedOperationException("Операцiя listIterator не пiдтримується");
    }

    @Override
    public List<MobileTariff<T>> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Операцiя subList не пiдтримується");
    }

    /**
     * Приклад використання TariffLinkedList.
     */
    public static void main(String[] args) {
        try {
            // Створення пустого списку
            TariffLinkedList<String> emptyList = new TariffLinkedList<>();
            System.out.println("Пустий список створено. Розмiр: " + emptyList.size());

            // Створення списку з одного тарифу
            BasicTariff<String> basicTariff = new BasicTariff<>("B1", "Базовий", 100.0, 1000, 100, 5000, 0.5);
            TariffLinkedList<String> singleList = new TariffLinkedList<>(basicTariff);
            System.out.println("Список з одного тарифу створено. Розмiр: " + singleList.size());

            // Створення списку з колекцiї
            List<MobileTariff<String>> tariffs = new ArrayList<>();
            tariffs.add(new BasicTariff<>("B2", "Економ", 75.0, 500, 50, 2000, 0.7));
            tariffs.add(new PremiumTariff<>("P1", "Премiум", 500.0, 200, true, 1));
            TariffLinkedList<String> listFromCollection = new TariffLinkedList<>(tariffs);
            System.out.println("Список з колекцiї створено. Розмiр: " + listFromCollection.size());

            // Демонстрацiя методiв
            System.out.println("\nДемонстрацiя методiв:");
            listFromCollection.add(new FamilyTariff<>("F1", "Сiмейний", 300.0, 150, 4, 50.0));
            System.out.println("Пiсля додавання нового тарифу. Розмiр: " + listFromCollection.size());

            System.out.println("\nВсi тарифи у списку:");
            for (MobileTariff<String> tariff : listFromCollection) {
                System.out.println(tariff);
            }

        } catch (Exception e) {
            System.err.println("Помилка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}