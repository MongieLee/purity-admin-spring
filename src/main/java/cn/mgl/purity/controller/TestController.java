package cn.mgl.purity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/v1")
public class TestController {
    @GetMapping("/one")
    public void one() {
    }

    @GetMapping("/two")
    public void two() {
    }

    @GetMapping("/three")
    public void three() {
    }

    @GetMapping("/four")
    public void four() {
    }

    @GetMapping("/five")
    public void five() {
    }

    @GetMapping("/six")
    public void six() {
    }

    @GetMapping("/seven")
    public void seven() {
    }
}
