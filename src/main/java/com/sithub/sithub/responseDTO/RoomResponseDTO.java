package com.sithub.sithub.responseDTO;

import com.sithub.sithub.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomResponseDTO {
    private List<User> userList;

    public RoomResponseDTO(List<User> userList) {
        this.userList = userList;
    }
}
