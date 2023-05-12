package webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MyStreams {
    public static void main(String[] args) {

        int[] array = {1, 4, 5, 9, 1, 6, 9, 4, 4, 5};
        System.out.println(minValue(array));

        int sumValue = 0;
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 1000002; i++) {
            integers.add(i);
            sumValue = sumValue + i;
        }

        List<Integer> test = oddOrEven(integers);
        int even = 0;
        int odd = 0;
        for (int i : test) {
            if (i % 2 == 0) {
                even++;
            } else odd++;
        }
        System.out.println("Сумма элементов   " + sumValue);
        System.out.println("чётных - " + even);
        System.out.println("не чётных - " + odd);

    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (sum, b) -> sum * 10 + b);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Supplier<Stream<Integer>> streamSupplier = integers::stream;

        final boolean evenSum = streamSupplier.get()
                .reduce(0, Integer::sum) % 2 == 0;


        return streamSupplier.get()
                .filter(integer -> evenSum == (integer % 2 != 0))
                .toList();
    }
}
