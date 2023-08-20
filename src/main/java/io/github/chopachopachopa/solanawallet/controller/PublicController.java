package io.github.chopachopachopa.solanawallet.controller;

import io.github.chopachopachopa.solanawallet.dto.RegUserDto;
import io.github.chopachopachopa.solanawallet.dto.RestResponse;
import io.github.chopachopachopa.solanawallet.service.UserService;
import io.github.chopachopachopa.solanawallet.util.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/public")
public class PublicController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/users")
    public RestResponse<Void> registerUser(@Valid @RequestBody final RegUserDto dto) {
        userService.saveUser(userMapper.toReqModel(dto));
        return RestResponse.ok();
    }
}
