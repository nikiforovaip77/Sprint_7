package ru.praktikum.qa_scooter.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

// Класс для создания заказа
@Data
@AllArgsConstructor
public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private Integer rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

}