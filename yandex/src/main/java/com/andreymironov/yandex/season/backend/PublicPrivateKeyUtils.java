package com.andreymironov.yandex.season.backend;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;

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

            writer.write(String.valueOf(
                    getPrivateKeysCount(BigInteger.valueOf(numbers[0]), BigInteger.valueOf(numbers[1]))));
        }
    }


    /**
     * @param nod
     * @param nok
     * @return 2 ^ number of prime divisors of nok / nod
     */
    public static BigInteger getPrivateKeysCount(BigInteger nod, BigInteger nok) {
        if (!nok.remainder(nod).equals(BigInteger.ZERO) || nod.compareTo(nok) > 0) {
            return BigInteger.ZERO;
        } else if (nod.equals(nok)) {
            return BigInteger.ONE;
        } else if (nod.equals(BigInteger.ONE)) {
            BigInteger divisor = getSmallestPrimeDivisor(nok);
            int power = getPowerOfDivisor(divisor, nok);
            return BigInteger.TWO.multiply(getPrivateKeysCount(nod, nok.divide(divisor.pow(power))));
        } else {
            return getPrivateKeysCount(BigInteger.ONE, nok.divide(nod));
        }
    }

    public static BigInteger getSmallestPrimeDivisor(BigInteger number) {
        for (
                BigInteger i = BigInteger.TWO;
                i.compareTo(number.sqrt()) <= 0;
                i = i.add(BigInteger.ONE)
        ) {
            if (number.remainder(i).equals(BigInteger.ZERO)) {
                return i;
            }
        }

        return number;
    }

    public static int getPowerOfDivisor(BigInteger divisor, BigInteger number) {
        int result = 0;

        while (number.remainder(divisor).equals(BigInteger.ZERO)) {
            result++;
            number = number.divide(divisor);
        }

        return result;
    }
}
