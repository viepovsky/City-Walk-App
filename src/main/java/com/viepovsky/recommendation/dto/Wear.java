package com.viepovsky.recommendation.dto;

import lombok.Getter;

@Getter
public class Wear {
    private final WeatherDescription weatherDesc;
    private Head head;
    private UpperBody upperBody;
    private LowerBody lowerBody;
    private Foot foot;
    private Rain rain;

    public Wear(WeatherDescription weatherDesc) {
        this.weatherDesc = weatherDesc;
        this.rain = Rain.NO;
        switch (weatherDesc) {
            case SCORCHING_HOT, HOT -> setWear(Head.SUN_HAT, UpperBody.T_SHIRT, LowerBody.SHORTS, Foot.SANDALS);
            case WARM -> setWear(Head.NONE, UpperBody.T_SHIRT, LowerBody.SHORTS, Foot.SNEAKERS);
            case MODERATE -> setWear(Head.NONE, UpperBody.T_SHIRT, LowerBody.JEANS, Foot.SNEAKERS);
            case COOL -> setWear(Head.NONE, UpperBody.SHIRT, LowerBody.JEANS, Foot.SNEAKERS);
            case CHILLY -> setWear(Head.NONE, UpperBody.SWEATER, LowerBody.JEANS, Foot.SNEAKERS);
            case COLD -> setWear(Head.BEANIE, UpperBody.LIGHT_JACKET, LowerBody.JEANS, Foot.SNEAKERS);
            case FREEZING -> setWear(Head.BEANIE, UpperBody.WINTER_JACKET, LowerBody.WINTER_PANTS, Foot.WINTER_BOOTS);
        }
    }

    public void setRainAndRainClothes() {
        this.rain = Rain.YES;
        head = Head.RAIN_HAT;
        upperBody = UpperBody.RAIN_JACKET;
    }

    public void setPossibleRain() {
        this.rain = Rain.POSSIBLE;
    }

    private void setWear(Head head, UpperBody upperBody, LowerBody lowerBody, Foot foot) {
        this.head = head;
        this.upperBody = upperBody;
        this.lowerBody = lowerBody;
        this.foot = foot;
    }

    public enum WeatherDescription {
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
        RAIN_HAT,
        SUN_HAT,
        NONE
    }

    public enum UpperBody {
        WINTER_JACKET,
        LIGHT_JACKET,
        RAIN_JACKET,
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

    public enum Rain {
        YES,
        POSSIBLE,
        NO
    }
}
