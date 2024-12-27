package service;

import dto.CurrentMatchDto;

public class MatchScoreCalculationService {

    ScorePointsService scorePointsService = ScorePointsService.ONE;
    CurrentMatchDto currentMatchDto = new CurrentMatchDto();


    public boolean isMatchOver (CurrentMatchDto currentMatchDto){

        if (currentMatchDto.getScoreFirstPlayer().add(1)){

        }


        return false;
    }

    public void asd (){


    }




}
