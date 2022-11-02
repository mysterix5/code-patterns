package com.github.mysterix5.template.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "userstatistics")
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsEntity {
    @Id
    private String name;
    private int amount;

    public void increase() {
        amount++;
    }
}
