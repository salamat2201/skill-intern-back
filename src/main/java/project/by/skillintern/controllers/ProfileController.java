package project.by.skillintern.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.by.skillintern.dto.responses.AuthDTO;
import project.by.skillintern.dto.responses.ProfileDTO;
import project.by.skillintern.entities.User;
import project.by.skillintern.exceptions.UserAlreadyExistsException;
import project.by.skillintern.jwt.JwtService;
import project.by.skillintern.services.ProfileService;
import project.by.skillintern.services.UserService;
import project.by.skillintern.services.impl.S3Service;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = "*")
@Tag(name="Profile", description="Взаймодействие с профильем")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final ProfileService profileService;
    private final JwtService jwtService;
    private final S3Service s3Service;

    @GetMapping("/get")
    @Operation(summary = "Get user profile. Authenticated Users(Токен керек)")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<ProfileDTO> getProfile() {
        User currentUser = profileService.getProfile(userService.getCurrentUser().getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ProfileDTO profile = new ProfileDTO();
        profile.setUsername(currentUser.getUsername());
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/edit")
    @Operation(summary = "Edit user profile. Authenticated Users(Токен керек)")
    public ResponseEntity<?> editProfile(@RequestBody ProfileDTO profileDTO) throws UserAlreadyExistsException {
        User currentUser = profileService.getProfile(userService.getCurrentUser().getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(currentUser.getUsername().equals(profileDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Итак та осындай username");
        }
        currentUser.setUsername(profileDTO.getUsername());

        profileService.updateProfile(currentUser);
        Map<String, String> tokens = jwtService.generateToken(profileDTO.getUsername(), currentUser.getRole().name());

        AuthDTO authDTO = new AuthDTO();
        authDTO.setToken(tokens.get("accessToken"));
        return ResponseEntity.ok(authDTO);
    }
    @PostMapping("/add-resume")
    @Operation(summary = "Add resume. Authenticated Users(Токен керек)")
    public ResponseEntity<String> addResume(@RequestParam MultipartFile resume) {
        if (resume.isEmpty()) {
            return ResponseEntity.badRequest().body("No file provided");
        }
        try {
            String fileUrl = s3Service.uploadFile(resume);
            profileService.uploadResume(fileUrl);

            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file: " + e.getMessage());
        }
    }
}
