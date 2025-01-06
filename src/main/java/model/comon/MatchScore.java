package model.comon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchScore {

    private Long firstPlayerId;
    private Long secondPlayerId;

    private Score scoreFirstPlayer;
    private Score scoreSecondPlayer;

}
