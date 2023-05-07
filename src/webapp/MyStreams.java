package webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyStreams {
    public static void main(String[] args) {

        int[] array = {1, 4, 5, 9, 1, 6, 9, 4, 4, 5};
        System.out.println(minValue(array));


        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 1000002; i++) {
            integers.add(i);
        }
        //System.out.println(oddOrEven(integers));
        List<Integer> test = oddOrEven(integers);
        int even = 0;
        int odd = 0;
        for (int i : test) {
            if (i % 2 == 0) {
                even++;
            } else odd++;
        }
        System.out.println("Сумма элементов " + utilVarForOddOrEven);
        System.out.println("чётных - " + even);
        System.out.println("не чётных - " + odd);

    }

    private static volatile int utilVarForMinValue;

    private static int minValue(int[] values) {
        MyStreams.utilVarForMinValue = 1;
        return Arrays.stream(values)
                .boxed()
                .sorted(Collections.reverseOrder())
                .mapToInt(Integer::intValue)
                .distinct()
                .map((value) -> value * (utilVarForMinValue = utilVarForMinValue * 10))
                .sum() / 10;
    }

    private static volatile int utilVarForOddOrEven;

    private static List<Integer> oddOrEven(List<Integer> integers) {
        MyStreams.utilVarForOddOrEven = 0;
        return integers.stream()
                .peek(integer -> utilVarForOddOrEven = utilVarForOddOrEven + integer)
                .sorted()
                .filter(integer -> {
                    if (utilVarForOddOrEven % 2 != 0)
                        return integer % 2 == 0;
                    else return integer % 2 != 0;
                })
                .toList();
    }
}
