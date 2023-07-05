package com.example.easy_flow_backend.dto.Models;

import com.example.easy_flow_backend.entity.Station;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GetGraphModel {
    List<StationModel> stations;
    List<Number> weights;

    public GetGraphModel(List<Station> stations, List<Number> weights) {
        this.stations = new ArrayList<>();
        for (Station s : stations) {
            this.stations.add(new StationModel(s.getStationName(), s.getLatitude(), s.getLongitude()));
        }

        this.weights = weights;
    }
}
