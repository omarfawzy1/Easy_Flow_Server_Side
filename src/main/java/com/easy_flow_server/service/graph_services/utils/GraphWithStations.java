package com.easy_flow_server.service.graph_services.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("GraphWithStations")
public class GraphWithStations implements Serializable {
    private double[][] graph;
    private List<String> stations;
}
