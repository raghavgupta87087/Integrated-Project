public class Main {
    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        System.out.println(q1.add(q2, LengthUnit.FEET));   // 2 FEET
        System.out.println(q1.add(q2, LengthUnit.INCH));   // 24 INCH
        System.out.println(q1.add(q2, LengthUnit.YARD));   // ~0.667 YARD
    }
}