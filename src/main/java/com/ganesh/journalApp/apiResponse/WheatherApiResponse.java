package com.ganesh.journalApp.apiResponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WheatherApiResponse{
    public Current current;
    @Data
    public class Current{

        public int temperature;
        @JsonProperty("weather_descriptions")
        public List<String> weatherDescription;
        public int feelslike;

    }
}










