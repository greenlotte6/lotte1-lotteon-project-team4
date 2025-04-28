package kr.co.lotteon.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserListDTO {

        private List<UsersDTO> users = new ArrayList<>();

        public List<UsersDTO> getUsers() {
            return users;
    }
}
