package team.backend.goWithMe.domain.mate.controller;

import io.swagger.annotations.ApiOperation;
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
    @GetMapping("/{id}")
    public ResponseEntity<MateResponseDto> getMate (@PathVariable Long id) {
        MateResponseDto mateDTO = mateListService.findById(id);
        return new ResponseEntity<MateResponseDto>(mateDTO, HttpStatus.OK);
    }

    //findAll
    @GetMapping("/mateList")
    public ResponseEntity<List<MateResponseDto>> getMateAll() {
        List<MateResponseDto> mate = mateListService.findAll();
        return new ResponseEntity<List<MateResponseDto>>(mate, HttpStatus.OK);
    }

    //save
    @PostMapping("/save")
    @ApiOperation(value = "친구 저장", notes = "친구 정보를 저장한다.")
    public ResponseEntity<MateRequestDto> saveMate(@RequestBody MateRequestDto mateSaveRequestDto) {
        mateListService.save(mateSaveRequestDto);
        return new ResponseEntity<MateRequestDto>(mateSaveRequestDto, HttpStatus.OK);
    }

    //update
    @PutMapping("/{id}")
    @ApiOperation(value = "친구 수정", notes = "친구 정보를 수정한다.")
    public ResponseEntity<Void> updateMate(@PathVariable Long id, @RequestBody MateRequestDto mateSaveRequestDto) {
        mateListService.update(id, mateSaveRequestDto);
        return ResponseEntity.ok().build();
    }

    //delete
    @DeleteMapping("/{id}")
    @ApiOperation(value = "친구 삭제", notes = "친구 정보를 삭제한다.")
    public ResponseEntity<Void> deleteMate(@PathVariable Long id) {
        mateListService.delete(id);
        return ResponseEntity.ok().build();
    }

}
