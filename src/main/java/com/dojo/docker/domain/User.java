package com.dojo.docker.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {
    private String name;
    private int age;

    // Those fields below should be masked in the logs
    private String ssn;
    private String password;
    private Address address;

}
