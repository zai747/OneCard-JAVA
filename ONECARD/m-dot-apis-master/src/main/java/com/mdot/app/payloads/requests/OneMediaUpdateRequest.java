package com.mdot.app.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OneMediaUpdateRequest {
    private String title;
    private String image;
    private String description;

}
