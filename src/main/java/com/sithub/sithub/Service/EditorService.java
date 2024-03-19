package com.sithub.sithub.Service;

import com.sithub.sithub.domain.Editor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EditorService {

    public void create(String title){
        String id = "asdf";
        Editor editor = new Editor(title, UUID.randomUUID().toString(), id);

    }
}
