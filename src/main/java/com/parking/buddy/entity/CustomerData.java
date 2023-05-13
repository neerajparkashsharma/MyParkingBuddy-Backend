package com.parking.buddy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerData {

    public String name;
    public String email;

    public String customerId;
}
