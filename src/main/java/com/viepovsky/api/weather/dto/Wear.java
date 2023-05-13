package com.viepovsky.api.weather.dto;

import lombok.Getter;

@Getter
public class Wear {
    private final TemperatureScale scale;
    private Head head;
    private UpperBody upperBody;
    private LowerBody lowerBody;
    private Foot foot;

    public Wear(TemperatureScale scale) {
        this.scale = scale;
        switch (scale) {
            case SCORCHING_HOT, HOT -> setWear(Head.SUN_HAT, UpperBody.T_SHIRT, LowerBody.SHORTS, Foot.SANDALS);
            case WARM -> setWear(Head.NONE, UpperBody.T_SHIRT, LowerBody.SHORTS, Foot.SNEAKERS);
            case MODERATE -> setWear(Head.NONE, UpperBody.T_SHIRT, LowerBody.JEANS, Foot.SNEAKERS);
            case COOL -> setWear(Head.NONE, UpperBody.SHIRT, LowerBody.JEANS, Foot.SNEAKERS);
            case CHILLY -> setWear(Head.NONE, UpperBody.SWEATER, LowerBody.JEANS, Foot.SNEAKERS);
            case COLD -> setWear(Head.BEANIE, UpperBody.LIGHT_JACKET, LowerBody.JEANS, Foot.SNEAKERS);
            case FREEZING -> setWear(Head.BEANIE, UpperBody.WINTER_JACKET, LowerBody.WINTER_PANTS, Foot.WINTER_BOOTS);
        }
    }

    private void setWear(Head head, UpperBody upperBody, LowerBody lowerBody, Foot foot) {
        this.head = head;
        this.upperBody = upperBody;
        this.lowerBody = lowerBody;
        this.foot = foot;
    }

    public enum TemperatureScale {
        SCORCHING_HOT,
        HOT,
        WARM,
        MODERATE,
        COOL,
        CHILLY,
        COLD,
        FREEZING
    }

    public enum Head {
        BEANIE,
        NONE,
        SUN_HAT
    }

    public enum UpperBody {
        HEAVY_COAT,
        WINTER_JACKET,
        LIGHT_JACKET,
        SWEATER,
        SHIRT,
        T_SHIRT
    }

    public enum LowerBody {
        WINTER_PANTS,
        JEANS,
        SHORTS
    }

    public enum Foot {
        WINTER_BOOTS,
        SNEAKERS,
        SANDALS
    }
}
