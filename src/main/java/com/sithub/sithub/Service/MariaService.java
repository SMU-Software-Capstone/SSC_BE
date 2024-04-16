package com.sithub.sithub.Service;

import com.sithub.sithub.Repository.MariaRepository;
import com.sithub.sithub.domain.Gyumin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MariaService {

    private final MariaRepository mariaRepository;

    public void updateCodes(String roomId, String code){
        Gyumin maria = mariaRepository.findByRoomId(roomId);
        if(maria != null) {
            maria.updateCode(code);
        }
    }

    public void saveSnapshot(String roomId, String code){
        Gyumin maria = new Gyumin(roomId, "asdf.java", code);
        mariaRepository.save(maria);
    }
}
