package kz.flappy.flappycom.flappycom.services.impl;

import kz.flappy.flappycom.flappycom.entities.Posts;
import kz.flappy.flappycom.flappycom.entities.Users;
import kz.flappy.flappycom.flappycom.repositories.PostRepository;
import kz.flappy.flappycom.flappycom.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Posts> getAllPosts() {
        return postRepository.findAllByAddedDateIsNotNullOrderByAddedDateDesc();
    }

    @Override
    public List<Posts> getAllPostsByUserId(Long id) {
        return postRepository.findAllByUsersId(id);
    }

    @Override
    public Posts getPosts(Long id) {
        return postRepository.findById(id).orElse(null) ;
    }

    @Override
    public Posts savePosts(Posts post) {
        return postRepository.save(post);
    }

    @Override
    public Posts addPosts(Posts post) {
        return postRepository.save(post);
    }
}
