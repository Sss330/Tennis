package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentMatchDto {
    private Long firstPlayerId;
    private Long secondPlayerId;
    private List<Integer> scoreFirstPlayer;
    private List<Integer> scoreSecondPlayer;

}
