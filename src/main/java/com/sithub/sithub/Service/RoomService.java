package com.sithub.sithub.Service;

import com.sithub.sithub.Repository.RoomRepository;
import com.sithub.sithub.Repository.UserRepository;
import com.sithub.sithub.domain.Room;
import com.sithub.sithub.domain.User;
import com.sithub.sithub.responseDTO.RoomResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    @Transactional
    public void create(Long id){
//        Editor editor = new Editor(title, UUID.randomUUID().toString(), id);
//        editorRepository.save(editor);
    }

    @Transactional
    public void getRoomAndSaveUser(String id, String roomId){
        Room room = roomRepository.findByRandomId(roomId).get();
        User user = userRepository.findByUserStringId(id).get();
        user.setRoom(room);
    }

    public RoomResponseDTO getRoomResponseDTO(String roomId){
        Room room = roomRepository.findByRandomId(roomId).get();
        RoomResponseDTO roomResponseDTO = new RoomResponseDTO(room.getUserList());
        return roomResponseDTO;
    }

    public void removeUser(String id, String roomid){
        Room room = roomRepository.findByRandomId(roomid).get();
        User user = userRepository.findByUserStringId(id).get();
        user.removeUser(room);
    }
}
