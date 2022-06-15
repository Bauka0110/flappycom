package kz.flappy.flappycom.flappycom.controllers;

import kz.flappy.flappycom.flappycom.entities.*;
import kz.flappy.flappycom.flappycom.services.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value ={"/","/index", "/home"})
    public String index(Model model) {
        model.addAttribute("CURRENT_USER", getUser());
        return "index";
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("CURRENT_USER", getUser());
        return "login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/profile")
    public String profile(Model model) {

        Long id = getUser().getId();

        List<Friends> userFriends = friendService.getAllFriends(id);
        List<Images> images = imageService.getAllImages();
        List<Posts> posts = postService.getAllPosts();
        List<Images> userImages = imageService.getAllImagesByUserId(id);
        List<Posts> userPosts = postService.getAllPostsByUserId(id);
        List<FriendsRequest> friendsRequests = friendRequestService.getAllFollowersByUserId(id);

        model.addAttribute("userFriends", userFriends);
        model.addAttribute("userPosts", userPosts);
        model.addAttribute("userImages", userImages);
        model.addAttribute("friendsRequests", friendsRequests);
        model.addAttribute("images", images);
        model.addAttribute("posts", posts);
        model.addAttribute("CURRENT_USER", getUser());
        return "profile";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/adminpage")
    public String adminPage(Model model) {
        List<Users> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("CURRENT_USER", getUser());
        return "adminpage";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping(value = "/moderatorpage")
    public String moderatorPage(Model model) {
        model.addAttribute("CURRENT_USER", getUser());
        return "moderatorpage";
    }

    @GetMapping(value = "/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String registration(@RequestParam(name = "fullName") String fullName,
                                 @RequestParam(name = "new_password") String newPassword,
                                 @RequestParam(name = "confirm_password") String confirmPassword,
                                 @RequestParam(name = "email") String email,
                                 @RequestParam(name = "country") String country,
                                 @RequestParam(name = "gender") String gender,
                                 @RequestParam(name = "avatar") String avatar) {
        Users user = new Users();

        if(newPassword.equals(confirmPassword)) {

            user.setPassword(passwordEncoder.encode(newPassword));
            user.setFullName(fullName);
            user.setEmail(email);
            user.setCountry(country);
            user.setGender(gender);
            user.setAvatar(avatar);
            userService.addUser(user);
            return "redirect:/login";
        }
        return "redirect:/registration?error";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/settings")
    public String updateProfile(Model model) {
        model.addAttribute("CURRENT_USER", getUser());
        return "settings";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/updateprofile")
    public String updateProfile(@RequestParam(name = "old_password") String oldPassword,
                                @RequestParam(name = "new_password") String newPassword,
                                @RequestParam(name = "confirm_new_password") String confirmNewPassword,
                                @RequestParam(name = "country") String country) {
        Users currentUser = getUser();
        currentUser.setCountry(country);
        if(passwordEncoder.matches(oldPassword, currentUser.getPassword()) && newPassword.equals(confirmNewPassword)) {
            currentUser.setPassword(passwordEncoder.encode(newPassword));
            userService.updateUser(currentUser);
            return "redirect:/settings?success";
        }
        return "redirect:/settings?error";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/addavatar")
    public String updateProfile(@RequestParam(name = "new_avatar") String newAvatar) {
        Users currentUser = getUser();
        currentUser.setAvatar(newAvatar);
        userService.updateUser(currentUser);
        return "redirect:/profile?success";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping(value = "/userdetails/{userId}")
    public String details(Model model, @PathVariable(name = "userId") Long id) {
        List<Users> users = userService.getAllUsers();
        Users user = userService.getUserById(id);
        Posts posts = postService.getPosts(id);

        model.addAttribute("CURRENT_USER", getUser());
        model.addAttribute("users", users);
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        return "userdetails";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @PostMapping("deleteuser")
    public String deleteuser(@RequestParam(name = "id") Long id) {
        Users user = userService.getUserById(id);
        return "redirect:/adminpage";
    }

    private Users getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            return (Users) authentication.getPrincipal();
        }
        return null;
    }
}
