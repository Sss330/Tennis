package model.comon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    int points;
    int games;
    int sets;
    boolean advantage;
    boolean tieBreak;
}
