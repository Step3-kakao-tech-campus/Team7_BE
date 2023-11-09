package com.example.tily.step.reference;

import java.time.LocalDateTime;

public class ReferenceRequest {
    public record CreateReferenceDTO(
            Long stepId,
            String category,
            String link
    ) { }
}
