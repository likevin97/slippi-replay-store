package com.slippi.replays;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

import com.slippi.replays.entities.ReplayEntity;
import com.slippi.replays.repositories.ReplayRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReplayService {

    private final ReplayRepository replayRepository;

    @Autowired
    public ReplayService(ReplayRepository replayRepository) {
        this.replayRepository = replayRepository;
    }


    /**
     * Save replay data with connect code
     * @param file
     * @param connectCode
     * @throws IOException
     */
    public ReplayEntity save(MultipartFile file, String connectCode) throws IOException {
        ReplayEntity replayEntity = new ReplayEntity();
        replayEntity.setSlpName(StringUtils.cleanPath(file.getOriginalFilename()));
        replayEntity.setSlpData(file.getBytes());
        replayEntity.setConnectCode(connectCode);
        replayEntity.setCreatedAt(OffsetDateTime.now());

        replayRepository.save(replayEntity);
        return replayEntity;
    }


    /**
	 * Get all replays for a single user
	 * @return List of replays by connect code
	 */
    public List<ReplayEntity> getReplaysByConnectCode(String connectCode) {
        return replayRepository.findByConnectCode(connectCode);
    }


    /**
	 * Get a list of all replay data
	 * @return List of replay data
	 */
    public List<ReplayEntity> getAllReplays() {
        return replayRepository.findAll();
    }


    /**
	 * Deletes all replays older than "x" days
	 * @param days
	 * @return the number of replays deleted
	 */
    public Long deleteReplays(int days) {
        return replayRepository.deleteByCreatedAtBefore(OffsetDateTime.now().minusDays(days));
    }

}
