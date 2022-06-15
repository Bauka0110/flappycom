package kz.flappy.flappycom.flappycom.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_friends_request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendsRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Timestamp addedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Users from;

    @ManyToOne(fetch = FetchType.EAGER)
    private Users to;

}
