package com.example.easy_flow_backend.dto.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair<S, T> implements Serializable {
    S first;
    T second;
}
