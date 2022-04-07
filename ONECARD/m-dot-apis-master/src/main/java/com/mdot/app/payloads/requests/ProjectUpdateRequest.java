package com.mdot.app.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectUpdateRequest {
    private String title;
    private String image;
    private String description;

}
