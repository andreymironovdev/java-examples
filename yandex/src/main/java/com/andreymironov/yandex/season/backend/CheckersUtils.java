package com.andreymironov.yandex.season.backend;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Разработчики любят проводить свободное время за настольными играми.
 * Иногда это шахматы, иногда — UNO, а иногда — шашки.
 * Однако, когда неопытные игроки садятся за шашки, они периодически допускают ошибки (прямо как в программировании!) и не рубят шашку соперника, когда такая возможность есть.
 * Чтобы избежать ошибок, разработчики решили написать программу, которая будет по текущей позиции определять,
 * можно ли сходить так, чтобы срубить шашку противника.
 * Но прямо сейчас у них много других важных проектов, поэтому запрограммировать анализатор позиции попросили вас.
 * Для тех, кто давненько не брал в руки шашек, напомним правила: все шашки стоят на полях одного цвета;
 * одна шашка может срубить другую, если та стоит на соседней клетке по диагонали и при этом в следующей
 * диагональной клетке в направлении соперника нет никакой другой шашки.
 * Формат ввода
 * В первой строке даны числа
 * N и M (1≤N,M≤10^3) — размеры доски, на которой разработчики играют в шашки.
 * Каждое поле имеет свой цвет: черный или белый. При этом гарантируется, что поле с координатами
 * (1;1) имеет черный цвет. Гарантируется также, что поле, имеющее общую границу с черным полем,
 * будет иметь белый цвет, а поле, имеющее общую границу с белым полем, — черный цвет.
 * В следующей строке дано число w — количество белых шашек на поле. В следующих w строках задаются два целых числа
 * i и j (1≤i≤N,1≤j≤M) — поля, на которых стоят белые шашки. В следующей строке дано число
 * b — количество черных шашек на поле. В следующих b строках задаются поля с черными шашками,
 * точно так же, как и с белыми. Гарантируется, что количество шашек каждого цвета — целое положительное число, и что
 * 2≤w+b≤(NM+1)/2. Гарантируется, что все шашки стоят на черных полях.
 */
public class CheckersUtils {
    public static void main(String[] args) throws IOException {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            String[] sizes = reader.readLine().split(" ");
            int N = Integer.parseInt(sizes[0]), M = Integer.parseInt(sizes[1]);

            int whiteCount = Integer.parseInt(reader.readLine());
            Coordinates[] whiteCoordinates = new Coordinates[whiteCount];
            for (int i = 0; i < whiteCount; i++) {
                int[] coordinates = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                whiteCoordinates[i] = new Coordinates(coordinates[0], coordinates[1]);
            }

            int blackCount = Integer.parseInt(reader.readLine());
            Coordinates[] blackCoordinates = new Coordinates[blackCount];
            for (int i = 0; i < blackCount; i++) {
                int[] coordinates = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                blackCoordinates[i] = new Coordinates(coordinates[0], coordinates[1]);
            }

            String move = reader.readLine();

            writer.write(canBeat(N, M, whiteCoordinates, blackCoordinates, move.equals("white")) ? "Yes" : "No");
        }
    }

    public static class Coordinates {
        int x;
        int y;

        Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Coordinates another)) {
                return false;
            }

            return this.x == another.x && this.y == another.y;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + x;
            result = 31 * result + y;
            return result;
        }

        public boolean isInBounds(int N, int M) {
            return x >= 1 && x <= N && y >= 1 && y <= M;
        }
    }

    public static boolean canBeat(int N, int M, Coordinates[] whiteCoordinates, Coordinates[] blackCoordinates,
                                  boolean whiteMove) {
        Map<Coordinates, Boolean> coordinatesToWhiteMap = new HashMap<>();
        for (int i = 0; i < whiteCoordinates.length; i++) {
            coordinatesToWhiteMap.put(whiteCoordinates[i], true);
        }
        for (int i = 0; i < blackCoordinates.length; i++) {
            coordinatesToWhiteMap.put(blackCoordinates[i], false);
        }

        if (whiteMove) {
            for (int i = 0; i < blackCoordinates.length; i++) {
                Coordinates black = blackCoordinates[i];

                Coordinates upperRight = new Coordinates(black.x + 1, black.y + 1);
                Coordinates bottomLeft = new Coordinates(black.x - 1, black.y - 1);
                if (Boolean.TRUE.equals(coordinatesToWhiteMap.get(bottomLeft)) &&
                        !coordinatesToWhiteMap.containsKey(upperRight) && upperRight.isInBounds(N, M)) {
                    return true;
                }

                if (Boolean.TRUE.equals(coordinatesToWhiteMap.get(upperRight)) &&
                        !coordinatesToWhiteMap.containsKey(bottomLeft) && bottomLeft.isInBounds(N, M)) {
                    return true;
                }

                Coordinates bottomRight = new Coordinates(black.x + 1, black.y - 1);
                Coordinates upperLeft = new Coordinates(black.x - 1, black.y + 1);
                if (Boolean.TRUE.equals(coordinatesToWhiteMap.get(bottomRight)) &&
                        !coordinatesToWhiteMap.containsKey(upperLeft) && upperLeft.isInBounds(N, M)) {
                    return true;
                }

                if (Boolean.TRUE.equals(coordinatesToWhiteMap.get(upperLeft)) &&
                        !coordinatesToWhiteMap.containsKey(bottomRight) && bottomRight.isInBounds(N, M)) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < whiteCoordinates.length; i++) {
                Coordinates white = whiteCoordinates[i];
                Coordinates bottomLeft = new Coordinates(white.x - 1, white.y - 1);
                Coordinates upperRight = new Coordinates(white.x + 1, white.y + 1);
                if (Boolean.FALSE.equals(coordinatesToWhiteMap.get(bottomLeft)) &&
                        !coordinatesToWhiteMap.containsKey(upperRight) && upperRight.isInBounds(N, M)) {
                    return true;
                }

                if (Boolean.FALSE.equals(coordinatesToWhiteMap.get(upperRight)) &&
                        !coordinatesToWhiteMap.containsKey(bottomLeft) && bottomLeft.isInBounds(N, M)) {
                    return true;
                }

                Coordinates upperLeft = new Coordinates(white.x - 1, white.y + 1);
                Coordinates bottomRight = new Coordinates(white.x + 1, white.y - 1);
                if (Boolean.FALSE.equals(coordinatesToWhiteMap.get(bottomRight)) &&
                        !coordinatesToWhiteMap.containsKey(upperLeft) && upperLeft.isInBounds(N, M)) {
                    return true;
                }

                if (Boolean.FALSE.equals(coordinatesToWhiteMap.get(upperLeft)) &&
                        !coordinatesToWhiteMap.containsKey(bottomRight) && bottomRight.isInBounds(N, M)) {
                    return true;
                }
            }
        }

        return false;
    }
}