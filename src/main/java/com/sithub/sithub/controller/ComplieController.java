package com.sithub.sithub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

@RestController
@RequestMapping("/complie")
@RequiredArgsConstructor
public class ComplieController {
    @GetMapping("/runPython")
    public String runPythonScript(@RequestParam(value = "code") String code) {
        StringBuilder output = new StringBuilder();
        try {
            // Python 코드 실행
            Process process = Runtime.getRuntime().exec("python -c \"" + code + "\"");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // 프로세스 종료 및 출력 반환
            process.waitFor();
            return output.toString();
        } catch (Exception e) {
            return "Error executing Python code: " + e.getMessage();
        }
    }
}
