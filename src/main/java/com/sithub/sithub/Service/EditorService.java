package com.sithub.sithub.Service;

import com.sithub.sithub.Repository.EditorRepository;
import com.sithub.sithub.domain.Editor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EditorService {
    private final EditorRepository editorRepository;
    @Transactional
    public void create(String title, Long id){
//        Editor editor = new Editor(title, UUID.randomUUID().toString(), id);
//        editorRepository.save(editor);
    }
}
