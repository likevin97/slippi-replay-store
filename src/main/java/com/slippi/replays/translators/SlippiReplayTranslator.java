package com.slippi.replays.translators;

import com.slippi.replays.api.SlippiFileDto;
import com.slippi.replays.entities.ReplayEntity;

public class SlippiReplayTranslator {

    public static SlippiFileDto entityToObject(ReplayEntity replayEntity) {
        SlippiFileDto slippiFileDto = new SlippiFileDto();


        slippiFileDto.setConnectCode(replayEntity.getConnectCode());
        slippiFileDto.setFileName(replayEntity.getSlpName());
        slippiFileDto.setId(replayEntity.getId());
        slippiFileDto.setCreatedAt(replayEntity.getCreatedAt());

        return slippiFileDto;
    }

}
