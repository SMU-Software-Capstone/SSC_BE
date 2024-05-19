package com.sithub.sithub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/compile")
@RequiredArgsConstructor
public class ComplieController {
    @PostMapping("/runPython")
    public String runPythonScript(@RequestPart(value = "code") String code) {
//        StringBuilder output = new StringBuilder();
//        try {
//            // 코드 파일 저장 - /tmp 디렉터리를 사용
//            Path tempFile = Paths.get("/tmp/script.py");
//            Files.write(tempFile, code.getBytes());
//
//            // Python 코드 실행
//            Process process = Runtime.getRuntime().exec("python " + tempFile.toString());
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                output.append(line).append("\n");
//            }
//
//            while ((line = errorReader.readLine()) != null) {
//                output.append(line).append("\n");
//            }
//
//            // 프로세스 종료 및 출력 반환
//            process.waitFor();
//            Files.delete(tempFile); // 임시 파일 삭제
//            return output.toString();
//        } catch (Exception e) {
//            return "Error executing Python code: " + e.getMessage();
//        }
        StringBuilder result = new StringBuilder();
        File tempScript = null;
        System.out.println(code);
        try {
            // 임시 파일 생성
            tempScript = File.createTempFile("python_script", ".py");

            // 코드를 임시 파일에 작성
            BufferedWriter out = new BufferedWriter(new FileWriter(tempScript));
            out.write(code);
            out.close();

            // 파이썬 스크립트 실행
            ProcessBuilder processBuilder = new ProcessBuilder("python3", tempScript.getAbsolutePath());
            Process process = processBuilder.start();

            // 파이썬 스크립트의 출력 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }

            // 프로세스 종료 대기
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Python script exited with code " + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to run Python script", e);
        } finally {
            if (tempScript != null) tempScript.delete(); // 임시 파일 삭제
        }

        return result.toString();
    }
}