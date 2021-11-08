package bai11;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="user")
@Data
public class User implements Serializable {

//    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hp")
    private int hp;
    @Column(name = "stamina")
    private int stamina;

    // Nếu không đánh dấu @Column thì sẽ mapping tự động theo tên biến
    private int atk;
    private int def;
    private int agi;
}
