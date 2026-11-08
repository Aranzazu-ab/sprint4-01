package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.HealthStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public HealthStatus testOk (){
        return new HealthStatus("Ok!");
    }
}
