package org.dummy;

import java.io.Serializable;

public class OlympicCountries implements Serializable{
    private final String country;
    private final int medalByYear;
    private final int Year;

    public OlympicCountries(String country, int medalByYear, int Year) {
        this.country = country;
        this.medalByYear = medalByYear;
        this.Year = Year;
    }

    public String getcountry() {
        return country;
    }

    public int getmedalByYear() {
        return medalByYear;
    }

    public int getYear() {
        return Year;
    }

}
