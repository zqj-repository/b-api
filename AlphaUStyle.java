package stock.analysis;

import stock.kdj.KDJLine;
import sun.font.GlyphLayout;

import java.util.ArrayList;
import java.util.List;

public class AlphaUStyle {

    public static void main(String[] args) {
//        System.out.println(nearClose(new Float[]{ -0.12f, -0.16f,-0.18f, -0.16f, -0.08f, -0.04f}));
        boolean[] b = kdjAnalysis(
                new Float[]{ 45F, 37F, 27F, 23F, 34F },
                new Float[]{ 39F, 38F, 34F, 30F, 32F },
                new Float[]{ 58F, 34F, 11F, 7F, 38F }
        );
        System.out.println(b[0]+","+ b[1] +","+ b[2] +","+ b[3]);
    }


    public Float[] pureArray(Float[] a) {
        List<Float> pure = new ArrayList<>();

        boolean occurTop = false;
        Float currentTop = 0F;
        for (int i = a.length - 1; i >= 0; i--) {
            int left = i - 1;
            int right = i + 1;
            Float leftF;
            Float rightF;
            if (left < 0) {
                leftF = Float.NaN;
            } else {
                leftF = a[left];
            }
            if (right >= a.length) {
                rightF = Float.NaN;
            } else {
                rightF = a[right];
            }
            if (!leftF.equals(Float.NaN) && !rightF.equals(Float.NaN) && a[i] > leftF && a[i] > rightF) {
                occurTop = true;
            }
            if (!occurTop) {
                pure.add(a[i]);
            } else {
                if (a[i] > 30) {
                    pure.add(a[i]);
                }
            }
        }
    }

    /**
     * MACD
     *
     * 3
     *
     * if(U style KDJ) {
     *  MinJ < 10 && MaxJ >70 && last < 40
     * }
     * if (LASTk - LastD > 0 && (last - 2)K - (last - 2)D < 0F) {
     *     GOLDx = TRUE;
     * }
     *
     * uStyle
     * reverseJ
     * Goldx
     *
     */

    public static boolean[] wkdjAnalysis(Float[] k, Float[] d, Float[] j) {

    }

    public static boolean[] kdjAnalysis(Float[] k, Float[] d, Float[] j) {
        boolean ustyle = uTrend(j);
        boolean reverseJ = false;
        boolean goldX = false;
        int N = j.length;
        Float[] last3 = new Float[]{j[N - 1], j[N-2], j[N-3]};
        boolean lowStandardDeviation = standardDeviation(last3) < 2F;
        if (ustyle) {
            Float maxJ = j[0], minJ = j[0];
            for (int i = 1; i < j.length; i++) {
                if (j[i] > maxJ) {
                    maxJ = j[i];
                }
                if (j[i] < minJ) {
                    minJ = j[i];
                }
            }
            if (maxJ > 50f && minJ < 15f && j[j.length - 1] < 40F) {
                reverseJ = true;
            }
        }
        if (k[k.length - 1] - d[d.length - 1] > 0f && k[k.length - 3] - d[d.length - 3] < 0f) {
            goldX = true;
        }
        return new boolean[] { ustyle, reverseJ, goldX, lowStandardDeviation };
    }

    public static boolean uTrend(Float[] a) {
        int min = 0;
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[min]) {
                min = i;
            }
        }

        boolean downT = true;
        boolean upT = true;
        if (min == a.length - 1) {
            downT = true;
            upT = false;
        } else if (min == 0) {
            upT = true;
            downT = false;
        } else {
            for (int i = 1; i < a.length; i++) {
                if (i < min && a[i - 1] < a[i]) {
                    downT = false;
                }
                if (i > min && a[i - 1] > a[i]) {
                    upT = false;
                }
            }
        }
        return downT && upT;
    }


    public static boolean nearClose(Float[] macd) {
        int N = macd.length;
        float sumDiff = 0F;
        float sumMacd = macd[0];
        for (int i = 1; i < macd.length; i ++) {
            sumMacd += macd[i];
            sumDiff += macd[i] - macd[i-1];
        }

        float speed = sumDiff / N;
        return sumMacd < 0.3F && (macd[N-1] + speed * 5F) > 0F;
    }


    public static double standardDeviation(Float[] array) {
        int sum = 0;
        for(int i=0;i<array.length;i++){
            sum += array[i];
        }
        double average = sum/array.length;
        int total=0;
        for(int i=0;i<array.length;i++){
            total += (array[i]-average)*(array[i]-average);
        }
        return Math.sqrt(total/array.length);
    }

}
