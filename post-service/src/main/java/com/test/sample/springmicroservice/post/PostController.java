package com.test.sample.springmicroservice.post;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/posts")
@Slf4j
public class PostController {

    private PostService postService;

    private PostRepository postRepository;

    private CommentRepository commentRepository;

    public PostController(PostService postService, PostRepository postRepository, CommentRepository commentRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping()
//    @ApiOperation(nickname = "get-all-posts", value = "Get all posts")
//    @ApiResponses(
//        value = {
//            @ApiResponse(code = 200, message = "return all posts by page")
//        }
//    )
    @JsonView(View.Summary.class)
    public ResponseEntity<Page<Post>> getAllPosts(
        @RequestParam(value = "q", required = false) String keyword, //
        @RequestParam(value = "status", required = false) Post.Status status, //
        @PageableDefault(page = 0, size = 10, sort = "createdDate", direction = Direction.DESC) Pageable page) {

        log.debug("get all posts of q@" + keyword + ", status @" + status + ", page@" + page);

        Page<Post> posts = this.postRepository.findAll(PostSpecifications.filterByKeywordAndStatus(keyword, status), page);

        return ok(posts);
    }

    @GetMapping(value = "/{slug}")
    @JsonView(View.Public.class)
    public ResponseEntity<Post> getPost(@PathVariable("slug") String slug) {

        log.debug("get postsinfo by slug @" + slug);

        Post post = this.postRepository.findBySlug(slug).orElseThrow(
            () -> {
                return new PostNotFoundException(slug);
            }
        );

        log.debug("get post @" + post);

        return ok(post);
    }

    @PostMapping()
    public ResponseEntity<Void> createPost(@RequestBody @Valid PostForm post, HttpServletRequest request) {

        log.debug("create a new post@" + post);

        Post saved = this.postService.createPost(post);

        log.debug("saved post id is @" + saved.getId());
        URI createdUri = ServletUriComponentsBuilder
            .fromContextPath(request)
            .path("/posts/{slug}")
            .buildAndExpand(saved.getSlug())
            .toUri();

        return created(createdUri).build();
    }

    @PutMapping(value = "/{slug}")
    public ResponseEntity<Void> updatePost(@PathVariable("slug") String slug, @RequestBody @Valid PostForm form) {

        log.debug("update post by id @" + slug + ", form content@" + form);

        this.postService.updatePost(slug, form);

        return noContent().build();
    }

    @DeleteMapping(value = "/{slug}")
    public ResponseEntity<Void> deletePostById(@PathVariable("slug") String slug) {

        log.debug("delete post by id @" + slug);

        this.postService.deletePost(slug);

        return noContent().build();
    }

    @GetMapping(value = "/{slug}/comments")
    public ResponseEntity<Page<Comment>> getCommentsOfPost(
        @PathVariable("slug") String slug,
        @PageableDefault(page = 0, size = 10, sort = "createdDate", direction = Direction.DESC) Pageable page) {

        log.debug("get comments of post@" + slug + ", page@" + page);

        Page<Comment> commentsOfPost = this.commentRepository.findByPost(new Slug(slug), page);

        log.debug("get post comment size @" + commentsOfPost.getTotalElements());

        return ok(commentsOfPost);
    }

    @PostMapping(value = "/{slug}/comments")
    public ResponseEntity<Void> createComment(
        @PathVariable("slug") @NotNull String slug, @RequestBody CommentForm comment, HttpServletRequest request) {

        log.debug("new comment of post@" + slug + ", comment" + comment);

        Comment _comment = Comment.builder()
            .post(new Slug(slug))
            .content(comment.getContent())
            .build();

        Comment saved = this.commentRepository.save(_comment);

        log.debug("saved comment @" + saved.getId());

        URI location = ServletUriComponentsBuilder
            .fromContextPath(request)
            .path("/posts/{slug}/comments/{id}")
            .buildAndExpand(slug, saved.getId())
            .toUri();

         return created(location).build();
    }

}
