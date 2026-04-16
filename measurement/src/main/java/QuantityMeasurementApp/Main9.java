package com.quantity;

import com.quantity.weight.*;

public class Main {
    public static void main(String[] args) {

        QuantityWeight q1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight q2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        // ✅ Equality
        System.out.println(q1.equals(q2)); // true

        // 🔁 Conversion
        System.out.println(q1.convertTo(WeightUnit.GRAM)); // 1000 g

        // ➕ Addition (implicit)
        System.out.println(q1.add(q2)); // 2 kg

        // ➕ Addition (explicit)
        System.out.println(q1.add(q2, WeightUnit.GRAM)); // 2000 g
    }
}