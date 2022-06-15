package kz.flappy.flappycom.flappycom.services;

import kz.flappy.flappycom.flappycom.entities.Posts;
import kz.flappy.flappycom.flappycom.entities.Users;

import java.util.List;

public interface PostService {

    void deletePost(Long id);
    List<Posts> getAllPosts();
    Posts getPosts(Long id);
    Posts savePosts(Posts post);
    Posts addPosts(Posts post);
    List<Posts> getAllPostsByUserId(Long id);


}
