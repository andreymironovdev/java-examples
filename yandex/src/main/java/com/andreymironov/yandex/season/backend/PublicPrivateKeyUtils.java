package com.andreymironov.yandex.season.backend;

import java.io.*;
import java.util.*;

/**
 * Дима и Егор разрабатывают новый сервис YD (Yandex Dorogi) и в данный момент занимаются аудитом его безопасности.
 * Для шифрования пользовательских данных в YD используется алгоритм шифрования с открытым ключом YS (Yandex Shifrovatel).
 * Схема работы алгоритма YS такова: для каждого сервиса генерируется закрытый ключ (p,q), где
 * p и q — натуральные числа. По закрытому ключу (p,q) генерируется открытый ключ (НОД(p,q), НОК(p,q)),
 * который доступен всем пользователям. Если злоумышленник сможет по открытому ключу получить закрытый ключ,
 * то он получит доступ ко всем данным YD и нанесёт сервису непоправимый вред. Конечно же, Егор и Дима не хотят этого
 * допустить, поэтому они хотят сделать так, чтобы злоумышленнику пришлось перебрать очень много вариантов открытого
 * ключа, прежде чем он сможет его угадать.
 * Дима уже сгенерировал закрытый ключ для YD и получил на его основе открытый ключ (x,y).
 * Егору сразу же стало интересно, сколько вариантов закрытого ключа придётся перебрать злоумышленнику для взлома
 * YD в худшем случае, иными словами, сколько существует закрытых ключей (p,q) таких, что открытым ключом для них
 * является (x,y). К сожалению, у Егора есть много других задач, очень важных для запуска YD,
 * поэтому он просит вас вычислить это количество за него.
 * Формат ввода:
 * <p>
 * В первой строке содержатся два целых числа x и y (1≤x≤y≤10^12) — описание открытого ключа.
 */
public class PublicPrivateKeyUtils {
    public static void main(String[] args) throws IOException {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            long[] numbers = Arrays.stream(reader.readLine().split(" "))
                    .mapToLong(Long::valueOf)
                    .toArray();

            writer.write(String.valueOf(getPrivateKeysCount(numbers[0], numbers[1])));
        }
    }

    public static long getPrivateKeysCount(long nod, long nok) {
        if (nok % nod != 0) {
            return 0;
        } else {
            long result = 1L;

            Map<Long, Integer> nokPrimeDivisors = getPrimeDivisors(nok);

            for (Long nokDivisor: nokPrimeDivisors.keySet()) {
                if (nod % nokDivisor == 0) {
                    Integer maxPower = nokPrimeDivisors.get(nokDivisor);
                    int power = 1;
                    long divisorToCheck = nokDivisor;
                    do {
                        power ++;
                        divisorToCheck *= nokDivisor;
                    } while (nod % divisorToCheck == 0);

                    result *= maxPower - power + 2;
                } else {
                    result *= 2;
                }
            }

            return result;
        }
    }

    public static Map<Long, Integer> getPrimeDivisors(long num) {
        Map<Long, Integer> map = new TreeMap<>();
        Set<Long> consideredDivisors = new TreeSet<>();

        long divisor = 2;
        while (divisor <= num) {
            int power = 0;
            while (num % divisor == 0) {
                num = num / divisor;
                power++;
            }
            if (power > 0) {
                map.put(divisor, power);
            }

            consideredDivisors.add(divisor);
            boolean hasConsidered;
            do {
                divisor++;
                long finalDivisor = divisor;
                hasConsidered = consideredDivisors.stream().anyMatch(d -> finalDivisor % d == 0);
            } while (hasConsidered);
        }

        return map;
    }
}
