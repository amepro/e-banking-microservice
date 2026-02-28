package com.senyitte.customerservice.dtos;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {
    private String firstName;
    private String lastName;
    private String email;
}
