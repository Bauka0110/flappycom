package kz.flappy.flappycom.flappycom.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_friends")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Timestamp addedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "me_id")
    private Users me;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "friend_id")
    private Users friend;
}
