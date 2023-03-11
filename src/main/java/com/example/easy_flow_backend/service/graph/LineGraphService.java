package com.example.easy_flow_backend.service.graph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class LineGraphService {
    @Autowired
    protected GraphService graphService;
    @Autowired
    protected GraphEdgeService graphEdgeService;




}
