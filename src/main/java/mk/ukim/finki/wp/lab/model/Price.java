package mk.ukim.finki.wp.lab.model;

public class Price {
    private final String value;

    public Price(String value) {
        this.value = (value == null || value.trim().isEmpty()) ? "N/A" : value.trim();
    }

    public String getValue() {
        return value;
    }

    public boolean isNumeric() {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

//    public Double getAsDouble() {
//        if (isNumeric()) {
//            return Double.parseDouble(value);
//        }
//        throw new IllegalArgumentException("The price is not numeric");
//    }


    @Override
    public String toString() {
        return value;
    }
}
