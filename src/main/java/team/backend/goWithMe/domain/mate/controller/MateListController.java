package team.backend.goWithMe.domain.mate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.backend.goWithMe.domain.mate.dto.MateResponseDto;
import team.backend.goWithMe.domain.mate.dto.MateRequestDto;
import team.backend.goWithMe.domain.mate.service.MateService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mate")
public class MateListController {

    private final MateService mateListService;

    //findById
    @GetMapping("/{mateId}")
    public ResponseEntity<MateResponseDto> getMate (@PathVariable Long id) {
        MateResponseDto mateDTO = mateListService.findById(id);
        return new ResponseEntity<MateResponseDto>(mateDTO, HttpStatus.OK);
    }

    //findAll
    @GetMapping("/mateList")
    public ResponseEntity<List<MateResponseDto>> getMateList() {
        List<MateResponseDto> mate = mateListService.findAll();
        return new ResponseEntity<List<MateResponseDto>>(mate, HttpStatus.OK);
    }

    //save
    @PostMapping
    public ResponseEntity<MateRequestDto> saveMate(@RequestBody MateRequestDto mateSaveRequestDto) {
        mateListService.save(mateSaveRequestDto);
        return new ResponseEntity<MateRequestDto>(mateSaveRequestDto, HttpStatus.OK);
    }

    //update
    @PutMapping("/{mateId}")
    public ResponseEntity<MateRequestDto> updateMate(@PathVariable Long id, @RequestBody MateRequestDto mateSaveRequestDto) {
        mateListService.update(id, mateSaveRequestDto);
        return new ResponseEntity<MateRequestDto>(mateSaveRequestDto, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/")
    public ResponseEntity<MateRequestDto> deleteMate(@PathVariable Long id, @RequestBody MateRequestDto mateSaveRequestDto) {
        mateListService.delete(id);
        return new ResponseEntity<MateRequestDto>(mateSaveRequestDto, HttpStatus.OK);
    }

}
