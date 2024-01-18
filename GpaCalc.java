package gpacalc.gpaCal;

import java.util.ArrayList;     // 순서가 있는 컬렉션을 정의하기 위함
import java.util.Arrays;        // 배열값을 출력하기 위해서
import java.util.List;          // 배열에서의 추가 및 제거를 위함
import java.util.Scanner;


public class GpaCalc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Subject> subjects = new ArrayList<>();

        // 전공, 교양과목 입력을 받아서 Subject 리스트에 추가
        System.out.println("전공 과목명과 이수학점, 평점을 입력해주세요(예시: 자바프로그래밍-3-A+,머신러닝-3-B+):");
        addSubjects(scanner, subjects, "전공");

        System.out.println("교양 과목명과 이수학점, 평점을 입력해주세요(예시: 통계학-3-C0):");
        addSubjects(scanner, subjects, "교양");

        //subject 리스트를 통하여서 GradeCalculator 객체 생성
        GradeCalculator calculator = new GradeCalculator(subjects);
        calculator.printSubjectList();
        calculator.printCalculatedGpa();
    }


    // 입력값을 배열에 저장
    private static void addSubjects(Scanner scanner, List<Subject> subjects, String type) {
        String input = scanner.nextLine();
        String[] subjectInputs = splitByComma(input);

        for (String subjectInput : subjectInputs) {
            String[] parts = splitSubjectIntoThreePart(subjectInput);
            validateSubjectParts(parts);
            addSubjectToList(subjects, type, parts);
        }
    }

    // 과목명이 10자를 넘으면 예외처리 메소드
    private static void checkSubjectNameLength(String subjectName) {
        if (subjectName.length() > 10) {
            throw new IllegalArgumentException("과목명은 10자를 초과할 수 없습니다.");
        }
    }

    // 과목명이 공백일시에 예외처리 메소드
    private static void checkIfSubjectNameEmpty(String subjectName) {
        if (subjectName.trim().isEmpty()) {
            throw new IllegalArgumentException("과목명이 비었습니다.");
        }
    }

    // 과목의 성적이 범위 밖일때 예외처리 메소드
    private static void checkRangeGrade(String grade) {
        List<String> validGrades = Arrays.asList("A+", "A0", "B+", "B0", "C+", "C0", "D+", "D0", "F", "P", "NP");
        if (!validGrades.contains(grade)) {
            throw new IllegalArgumentException("유효하지 않은 성적입니다: " + grade);
        }
    }

    // 입력된 문자열을 ',' 기준으로 분류하여 배열에 저장
    private static String[] splitByComma(String input) {
        return input.split(",");
    }

    // 각 과목 문자열을 '-'로 분할하여 과목명, 학점, 성적으로 나눔
    private static String[] splitSubjectIntoThreePart(String subjectInput) {
        return subjectInput.split("-");
    }

    // 분할된 문자열이 정확히 3부분으로 구성이 되어 있지 않으면 예외적용
    private static void validateSubjectParts(String[] parts) {
        if (parts.length != 3) {
            throw new IllegalArgumentException("잘못된 형식입니다.");
        }
    }

    // 입력값을 Subject 객체 리스트에 추가
    private static void addSubjectToList(List<Subject> subjects, String type, String[] parts) {
        String subjectName = parts[0].trim();
        int credit = Integer.parseInt(parts[1].trim());
        String grade = parts[2].trim();

        subjects.add(new Subject(type, subjectName, credit, grade));
    }
}