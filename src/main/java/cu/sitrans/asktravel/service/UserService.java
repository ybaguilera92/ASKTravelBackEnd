package cu.sitrans.asktravel.service;

import cu.sitrans.asktravel.models.Catalog;
import cu.sitrans.asktravel.models.User;
import cu.sitrans.asktravel.payload.request.UserChangePasswordDTO;
import cu.sitrans.asktravel.payload.request.UserDTO;
import cu.sitrans.asktravel.payload.request.UserUpdateImageDTO;
import cu.sitrans.asktravel.payload.response.JwtResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;


public interface UserService {
    // Tag save(String tagName, String tagColor);
    Map<String, Object> getUsers(Pageable pageable);
    Optional<User> getUserById(String id);
    User save(UserDTO user);
    User update(UserDTO userDTO);
    User updatePassword(UserChangePasswordDTO userDTO);

    User updateImage(UserUpdateImageDTO userDTO) throws IOException;
    Map<String, Object> countALl();
    Map<String, Object> getProfile(String id);
}
