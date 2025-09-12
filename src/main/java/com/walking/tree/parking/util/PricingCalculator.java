package com.walking.tree.parking.util;

import com.walking.tree.parking.entity.enums.VehicleType;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class PricingCalculator {

    private PricingCalculator() {}

    // Example: simple pricing rules; replace with configurable rules in prod
    public static BigDecimal calculate(VehicleType type, long minutes) {
        if (minutes <= 0) minutes = 1;
        BigDecimal hrs = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.CEILING);
        BigDecimal ratePerHour;
        switch (type) {
            case BIKE: ratePerHour = BigDecimal.valueOf(10); break;
            case CAR: ratePerHour = BigDecimal.valueOf(40); break;
            case TRUCK: ratePerHour = BigDecimal.valueOf(80); break;
            default: ratePerHour = BigDecimal.valueOf(40);
        }
        return hrs.multiply(ratePerHour).setScale(2, RoundingMode.HALF_UP);
    }
}
