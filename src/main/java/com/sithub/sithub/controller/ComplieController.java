package com.sithub.sithub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/compile")
@RequiredArgsConstructor
public class ComplieController {
    @PostMapping("/runPython")
    public String runPythonScript(@RequestPart(value = "code") String code) {
        StringBuilder output = new StringBuilder();
        try {
            // 코드 파일 저장
            Path tempFile = Files.createTempFile("script", ".py");
            Files.write(tempFile, code.getBytes());

            // Python 코드 실행
            Process process = Runtime.getRuntime().exec("python " + tempFile.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            while ((line = errorReader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // 프로세스 종료 및 출력 반환
            process.waitFor();
            Files.delete(tempFile); // 임시 파일 삭제
            return output.toString();
        } catch (Exception e) {
            return "Error executing Python code: " + e.getMessage();
        }
    }
}