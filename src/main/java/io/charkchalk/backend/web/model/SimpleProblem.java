package io.charkchalk.backend.web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SimpleProblem extends Problem {
    private String detail;

    public SimpleProblem(String title, int status, String detail) {
        super(title, status);
        this.detail = detail;
    }
}
