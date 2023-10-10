package com.example.tily.comment;

import com.example.tily._core.errors.exception.Exception400;
import com.example.tily.roadmap.Roadmap;
import com.example.tily.roadmap.RoadmapRepository;
import com.example.tily.step.Step;
import com.example.tily.step.StepRepository;
import com.example.tily.til.Til;
import com.example.tily.til.TilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {
    private final RoadmapRepository roadmapRepository;
    private final StepRepository stepRepository;
    private final TilRepository tilRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponse.CreateCommentDTO createComment(CommentRequest.CreateCommentDTO requestDTO, Long roadmapId, Long stepId, Long tilId) {

        Roadmap roadmap = roadmapRepository.findById(roadmapId).orElseThrow(
                () -> new Exception400("해당 로드맵을 찾을 수 없습니다")
        );

        Step step = stepRepository.findById(stepId).orElseThrow(
                () -> new Exception400("해당 스텝을 찾을 수 없습니다")
        );

        Til til = tilRepository.findById(tilId).orElseThrow(
                () -> new Exception400("해당 til을 찾을 수 없습니다")
        );

        String content = requestDTO.getContent();

        Comment comment = Comment.builder().roadmap(roadmap).step(step).til(til).content(content).build();
        commentRepository.save(comment);

        return new CommentResponse.CreateCommentDTO(comment);
    }
}