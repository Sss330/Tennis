package model;

import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "matches",schema = "public")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Player player1;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Player player2;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Player winner;

}
