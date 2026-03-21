package com.ganesh.journalapp.apiResponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WheatherApiResponse{
    private Current current;
    @Data
    public class Current{

        private int temperature;
        @JsonProperty("weather_descriptions")
        private List<String> weatherDescription;
        private int feelslike;

    }
}










