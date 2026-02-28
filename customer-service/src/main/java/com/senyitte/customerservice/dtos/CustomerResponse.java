package com.senyitte.customerservice.dtos;
import java.time.Instant;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Instant createdAt;
    private Instant updatedAt;

}
