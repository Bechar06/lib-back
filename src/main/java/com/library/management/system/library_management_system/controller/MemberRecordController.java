package com.library.management.system.library_management_system.controller;

import com.google.zxing.WriterException;
import com.library.management.system.library_management_system.dto.MemberRecordDto;
import com.library.management.system.library_management_system.model.LMSException;
import com.library.management.system.library_management_system.service.MemberRecordService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/memberRecord")
@CrossOrigin(origins = "*", maxAge = 3600)
@SuppressWarnings("unchecked")
public class MemberRecordController {


    @Autowired
    private MemberRecordService memberRecordService;

    @GetMapping(path = "/first", produces = MediaType.APPLICATION_JSON_VALUE)
    
    private MemberRecordDto findFirst() {
        MemberRecordDto memberRecordDto = memberRecordService.findFirst();
        if (memberRecordDto != null)
            return memberRecordDto;
        else
            return new MemberRecordDto();
    }

    @GetMapping(path = "/{codeMemberRecord}", produces = MediaType.APPLICATION_JSON_VALUE)
    private MemberRecordDto findMemberRecordByCode(@PathVariable String codeMemberRecord) {
        return memberRecordService.findByCodeMemberRecord(codeMemberRecord);
    }

    @ApiOperation(value = "Add a Member")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private MemberRecordDto add(@RequestBody MemberRecordDto memberRecordDto) throws LMSException, IOException, WriterException {
        return memberRecordService.add(memberRecordDto);
    }

    @ApiOperation(value = "Delete a Member")
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private void delete(@PathVariable Integer id) throws LMSException, IOException {
        memberRecordService.delete(id);
    }

    @ApiOperation(value = "Update a Member")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private MemberRecordDto update(@RequestBody MemberRecordDto memberRecordDto) throws IOException, LMSException, WriterException {
        return memberRecordService.update(memberRecordDto);
    }
    @ApiOperation(value = "View a list of available Members",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private List<MemberRecordDto> findByAll() {
        return memberRecordService.findAll();
    }


}
