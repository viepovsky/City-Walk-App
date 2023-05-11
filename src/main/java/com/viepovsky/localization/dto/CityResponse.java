package com.viepovsky.localization.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CityResponse {
    private String countryCode;

    private String name;

    private String latitude;

    private String longitude;
}
