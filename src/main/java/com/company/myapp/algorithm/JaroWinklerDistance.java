package com.company.myapp.algorithm;

import com.company.myapp.model.Result;

import java.util.Comparator;
import java.util.Objects;

public class JaroWinklerDistance {

    private static Comparator<Result> comparator;

    public static double compute(final String s1, final String s2) {
        if (Objects.equals(s1, s2))
            return 1.0d;

        int len1 = s1.length(), len2 = s2.length();
        int max_dist = (int) (Math.floor(Math.max(len1, len2) / 2) - 1);
        int match = 0; // mos keladigan belgilar soni

        int[] hash_s1 = new int[s1.length()];
        int[] hash_s2 = new int[s2.length()];

        for (int i = 0; i < len1; i++)
        {
            for (int j = Math.max(0, i - max_dist); j < Math.min(len2, i + max_dist + 1); j++)
                if (s1.charAt(i) == s2.charAt(j) && hash_s2[j] == 0)
                {
                    hash_s1[i] = 1;
                    hash_s2[j] = 1;
                    match++;
                    break;
                }
        }

        if (match == 0) return 0.0;

        double t = 0; // mos kelmaydigan belgilar sonining yarmi (transpozitsiyalar sonining yarmi)

        int point = 0;

        for (int i = 0; i < len1; i++)
            if (hash_s1[i] == 1)
            {
                while (hash_s2[point] == 0)  point++;

                if (s1.charAt(i) != s2.charAt(point++) ) t++;
            }

        t /= 2;

        System.out.println("m = " + match);

        return (((double)match) / ((double)len1)
                + ((double)match) / ((double)len2)
                + ((double)match - t) / ((double)match))
                / 3.0;
    }

}
