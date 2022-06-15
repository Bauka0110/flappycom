package kz.flappy.flappycom.flappycom.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "t_posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "pictureURL")
    private String pictureURL;

    @Column(name = "addedDate")
    private Timestamp addedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Users users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_like_id")
    private Set<Users> likes;

}
