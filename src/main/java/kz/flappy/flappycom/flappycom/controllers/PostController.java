package kz.flappy.flappycom.flappycom.controllers;

import kz.flappy.flappycom.flappycom.entities.*;
import kz.flappy.flappycom.flappycom.repositories.FriendRepository;
import kz.flappy.flappycom.flappycom.repositories.FriendRequestRepository;
import kz.flappy.flappycom.flappycom.repositories.ImageRepository;
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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class PostController {


    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private FriendService friendService;

    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/news")
    public String news(Model model) {

        List<Users> users = userService.getAllUsers();
        List<Posts> posts = postService.getAllPosts();
        model.addAttribute("users", users);
        model.addAttribute("posts", posts);
        model.addAttribute("CURRENT_USER", getUser());
        return "news";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addpost")
    public String addPost(@RequestParam(name = "content") String content,
                          @RequestParam(name = "pictureURL") String pictureURL) {
        Users currentUser = getUser();

        Posts posts = new Posts();
        posts.setContent(content);
        posts.setPictureURL(pictureURL);
        posts.setAddedDate(new Timestamp(System.currentTimeMillis()));
        posts.setUsers(currentUser);
        postService.savePosts(posts);

        return "redirect:/news";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/savepost")
    public String savePost(@RequestParam(name = "id") Long id,
                           @RequestParam(name = "content") String content,
                           @RequestParam(name = "pictureURL") String pictureURL) {
        Posts posts = postService.getPosts(id);

        if(posts!=null) {
            posts.setContent(content);
            posts.setPictureURL(pictureURL);
            posts.setAddedDate(new Timestamp(System.currentTimeMillis()));
            postService.savePosts(posts);
            return "redirect:/news?success";
        }
        return "redirect:/news?error";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("deletepost")
    public String deletePost(@RequestParam(name = "id") Long id) {
        postService.deletePost(id);
        return "redirect:/news";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/details/{postId}")
    public String details(Model model, @PathVariable(name = "postId") Long id) {
        model.addAttribute("CURRENT_USER", getUser());
        List<Users> users = userService.getAllUsers();
        model.addAttribute("users", users);
        Posts posts = postService.getPosts(id);
        model.addAttribute("posts", posts);
        return "details";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/unlike")
    public String unlike(@RequestParam(name = "id") Long id) {
        Posts post = postService.getPosts(id);
        post.getLikes().remove(getUser());
        postService.savePosts(post);
        return "redirect:/news";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/like")
    public String like(@RequestParam(name = "id") Long id) {
        Posts post = postService.getPosts(id);
        post.getLikes().add(getUser());
        postService.savePosts(post);
        return "redirect:/news";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/images")
    public String images(Model model) {

        List<Images> images = imageService.getAllImages();
        model.addAttribute("images", images);
        model.addAttribute("CURRENT_USER", getUser());
        return "images";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/addimage")
    public String addImage(@RequestParam(name = "pictureURL") String pictureURL) {
        Users currentUser = getUser();

        Images images = new Images();
        images.setPictureURL(pictureURL);
        images.setAddedDate(new Timestamp(System.currentTimeMillis()));
        images.setUsers(currentUser);

        imageRepository.save(images);

        return "redirect:/images";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/saveimage")
    public String savePost(@RequestParam(name = "id") Long id,
                           @RequestParam(name = "pictureURL") String pictureURL) {
        Users current = getUser();
        Images images = imageService.getImages(id);

        if(images!=null) {
            images.setPictureURL(pictureURL);
            images.setAddedDate(new Timestamp(System.currentTimeMillis()));
            imageService.saveImages(images);

            return "redirect:/images?success";
        }
        return "redirect:/images?error";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("deleteimage")
    public String deleteImage(@RequestParam(name = "id") Long id) {
        imageService.deleteImage(id);
        return "redirect:/images";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/imagedetails/{imageId}")
    public String imageDetails(Model model, @PathVariable(name = "imageId") Long id) {
        model.addAttribute("CURRENT_USER", getUser());
        List<Users> users = userService.getAllUsers();
        model.addAttribute("users", users);
        Images images = imageService.getImages(id);
        model.addAttribute("images", images);
        return "imagedetails";
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/searchusers")
    public String searchUsers(Model model, @RequestParam(name = "userfullname") String userfullname) {
        List<Users> users = userService.getAllByFullName(userfullname);
        model.addAttribute("users", users);
        model.addAttribute("CURRENT_USER", getUser());
        return "searchusers";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/friends")
    public String friends(Model model) {
        List<Friends> friends = friendService.getAllFriends(getUser().getId());
        List<FriendsRequest> friendsRequest = friendRequestService.myRequest(getUser().getId());
        List<FriendsRequest> friendsResponse = friendRequestService.myResponse(getUser().getId());

        model.addAttribute("friendsRequest", friendsRequest);
        model.addAttribute("friendsResponse", friendsResponse);
        model.addAttribute("friends", friends);
        model.addAttribute("CURRENT_USER", getUser());
        return "friends";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("addfriend")
    public String addFriend(@RequestParam(name = "id") Long id) {
        Users currenUser = getUser();
        Users user = userService.getUserById(id);
        Friends friend = new Friends();
        friend.setAddedDate(new Timestamp(System.currentTimeMillis()));
        friend.setMe(currenUser);
        friend.setFriend(user);
        friendService.addFriend(friend);
        return "redirect:/friends";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("addfriendrequest")
    public String addFriendRequest(Model model, @RequestParam(name = "id") Long id) {
        Users currenUser = getUser();
        Users user = userService.getUserById(id);

        FriendsRequest friendd = new FriendsRequest();
        friendd.setAddedDate(new Timestamp(System.currentTimeMillis()));
        friendd.setFrom(currenUser);
        friendd.setTo(user);

        Long idd = friendd.getId();
        model.addAttribute("idd", idd);

        friendRequestService.addFriendRequest(friendd);
        return "redirect:/friends";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("addfriendresponse")
    public String addFriendResponse(@RequestParam(name = "id") Long id,
                                    @RequestParam(name = "idd") Long idd) {
        Users currenUser = getUser();
        Users user = userService.getUserById(id);

        Friends friend = new Friends();
        friend.setAddedDate(new Timestamp(System.currentTimeMillis()));
        friend.setMe(currenUser);
        friend.setFriend(user);
        friendService.addFriend(friend);

        Friends friend2 = new Friends();
        friend2.setAddedDate(new Timestamp(System.currentTimeMillis()));
        friend2.setMe(user);
        friend2.setFriend(currenUser);
        friendService.addFriend(friend2);

        friendRequestService.deleteFriendRequest(idd);

        return "redirect:/friends";
    }


    private Users getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            return (Users) authentication.getPrincipal();
        }
        return null;
    }

}
