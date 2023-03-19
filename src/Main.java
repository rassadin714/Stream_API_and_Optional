import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.stream.Stream.*;

public class Main {
    public static void main(String[] args) {
        List<Person> personList = Arrays.asList(
                new Person("Ирина", 50),
                new Person("Мария", 20),
                new Person("Алексей", 35),
                new Person("Андрей", 25),
                new Person("Михаил",40)
        );
        Stream<Person> stream = personList.stream();
        Stream<Person> stream1 = Stream.empty();
        BiConsumer<Person, Person> minMaxConsumer = (min, max) -> System.out.println("Самый молодой человек " + min + ", самый взрослый -  " + max);

        //первый вариант метода findMinMax
        findMinMax(stream,
                (o1, o2) -> (o1.getAge()>o2.getAge()) ? 1 : (o1.getAge()<o2.getAge() ? -1 : 0),
                minMaxConsumer);
        findMinMax(stream1,
                (o1, o2) -> (o1.getAge()>o2.getAge()) ? 1 : (o1.getAge()<o2.getAge() ? -1 : 0),
                minMaxConsumer);

        //второй вариант метода findMinMax

        Stream<Person> stream2 = personList.stream();
        Stream<Person> stream3 = Stream.empty();
        findMinMax1(stream2,
                (o1, o2) -> (o1.getAge()>o2.getAge()) ? 1 : (o1.getAge()<o2.getAge() ? -1 : 0),
                minMaxConsumer);
        findMinMax1(stream3,
                (o1, o2) -> (o1.getAge()>o2.getAge()) ? 1 : (o1.getAge()<o2.getAge() ? -1 : 0),
                minMaxConsumer);

        List<Integer> integerList = Arrays.asList(1, 2, 5, 10, 120, 4, 15, 1988);
        countEvenNumbers(integerList);

    }

    public static<T> void findMinMax(Stream<? extends T> stream,
                                     Comparator<? super T> order,
                                     BiConsumer<? super T, ? super T> minMaxConsumer){
        List<T> list = stream.sorted(order).collect(Collectors.toList());
        if (list.isEmpty()){
            minMaxConsumer.accept(null, null);
        } else {
            T min = list.get(0);
            T max = list.get(list.size()-1);
            minMaxConsumer.accept(min, max);
        }
    }

    public static<T> void findMinMax1(Stream<? extends T> stream,
                                      Comparator<? super T> order,
                                      BiConsumer<? super T, ? super T> minMaxConsumer){
        List<T> list = stream.collect(Collectors.toList());
        if (list.isEmpty()) {
            minMaxConsumer.accept(null, null);
        } else {
            Supplier<Stream<? extends T>> streamSupplier = () -> list.stream();
            T min = streamSupplier.get().min(order).get();
            T max = streamSupplier.get().max(order).get();
            minMaxConsumer.accept(min, max);
        }

    }

    public static void countEvenNumbers (List<Integer> list){
        List<Integer> listNew = list.stream()
                .filter(n -> n % 2==0)
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("Количество четных чисел составляет " + listNew.size() + ": " + listNew);
    }
}