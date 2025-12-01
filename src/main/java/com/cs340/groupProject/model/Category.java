package com.cs340.groupProject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    public int id;
    public String name;
    public String description;
}
