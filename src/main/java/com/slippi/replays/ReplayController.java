package com.slippi.replays;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.slippi.replays.api.SlippiFileDto;
import com.slippi.replays.entities.ReplayEntityProjection;
import com.slippi.replays.entities.ReplayEntity;
import com.slippi.replays.translators.SlippiReplayTranslator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ReplayController {

	@Autowired
	ReplayService replayService;

	@GetMapping("/")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	/**
	 * Get a list of all replay meta data
	 * @return List of replay meta data
	 */
	@RequestMapping("/all-replays")
	@ResponseBody
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<List<SlippiFileDto>> getAllReplays() {
		List<SlippiFileDto> replays = replayService.getAllReplays()
		.stream().map(
			replay -> SlippiReplayTranslator.entityToObject(replay)
			).collect(Collectors.toList());

		return new ResponseEntity<List<SlippiFileDto>>(replays, HttpStatus.OK);
	}


	/**
	 * Get all replays for a single user
	 * @return List of replays by connect code
	 */
	@RequestMapping("/replays-by-connect-code")
	@ResponseBody
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<List<ReplayEntity>> getReplaysByConnectCode(@RequestParam String connectCode) {
		List<ReplayEntity> replays = replayService.getReplaysByConnectCode(connectCode);

		return new ResponseEntity<List<ReplayEntity>>(replays, HttpStatus.OK);
	}

	/**
	 * Delete all replays by connect code except for the "num" latest replays
	 * @param connectCode
	 * @param num
	 */
	@DeleteMapping("/delete-replays-by-connect-code-excluding-latest")
	@ResponseBody
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<Void> getLatestReplaysByConnectCode(@RequestParam String connectCode, @RequestParam int num) {
		List<UUID> exclusionList = replayService.getXLatestReplaysByConnectCode(connectCode, num);
		replayService.deleteReplaysExcludingLatest(connectCode, exclusionList);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}


	/**
	 * Add replay to database with connect_code, file_name, file_data, and created_at columns
	 * @param connectCode
	 * @param filePath
	 * @return SlippiFileDto signifying a successful insert
	 */
	@PostMapping(value = "/add-replay")
	@ResponseBody
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<String> addReplay(@RequestParam MultipartFile file, @RequestParam String connectCode) {
		try {
			replayService.save(file, connectCode);
			return ResponseEntity.status(HttpStatus.OK).body(String.format("Slippi replay uploaded successfully: %s", file.getOriginalFilename()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(String.format("Error uploading slippi replay: %s", file.getOriginalFilename()));
		}
	}


	/**
	 * Deletes all replays older than "x" days
	 * @param days
	 * @return the number of replays deleted
	 */
	@DeleteMapping(value = "/delete-older-than")
	@ResponseBody
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<Long> deleteReplays(@RequestParam int days) {
		return new ResponseEntity<Long>(replayService.deleteReplays(days), HttpStatus.OK);
	}

}