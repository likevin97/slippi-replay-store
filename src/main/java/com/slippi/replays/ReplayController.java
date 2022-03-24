package com.slippi.replays;

import java.util.List;
import java.util.stream.Collectors;

import com.slippi.replays.api.SlippiFileDto;
import com.slippi.replays.entities.ReplayEntity;
import com.slippi.replays.translators.SlippiReplayTranslator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public String index() {
		return "Greetings from Spring Boot!";
	}

	/**
	 * Get a list of all replay meta data
	 * @return List of replay meta data
	 */
	@RequestMapping("/all-replays")
	@ResponseBody
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
	public ResponseEntity<List<ReplayEntity>> getReplaysByConnectCode(@RequestParam String connectCode) {
		List<ReplayEntity> replays = replayService.getReplaysByConnectCode(connectCode);

		return new ResponseEntity<List<ReplayEntity>>(replays, HttpStatus.OK);
	}


	/**
	 * Add replay to database with connect_code, file_name, file_data, and created_at columns
	 * @param connectCode
	 * @param filePath
	 * @return SlippiFileDto signifying a successful insert
	 */
	@PostMapping(value = "/add-replay")
	@ResponseBody
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
	@PostMapping(value = "/delete-older-than")
	@ResponseBody
	public ResponseEntity<Long> deleteReplays(@RequestParam int days) {
		return new ResponseEntity<Long>(replayService.deleteReplays(days), HttpStatus.OK);
	}

}