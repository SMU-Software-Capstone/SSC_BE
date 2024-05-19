package com.sithub.sithub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/compile")
@RequiredArgsConstructor
public class CompileController {
    @PostMapping("/runPython")
    public String runPythonScript(@RequestPart(value = "code") String code) {
        StringBuilder output = new StringBuilder();
        try {
            // 코드 파일 저장 - /tmp 디렉터리를 사용
            Path tempFile = Paths.get("/tmp/script.py");
            Files.write(tempFile, code.getBytes());

            // Python 코드 실행
            ProcessBuilder processBuilder = new ProcessBuilder("python3", tempFile.toString());
            processBuilder.environment().put("PATH", "/usr/local/bin:/usr/bin:/bin");
            Process process = processBuilder.start();

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