package study.mvc.basic;

import lombok.Getter;
import lombok.Setter;

//json 라이브러리가 접근할 수 있는 getter, setter 생성 (자바 프로퍼티 접근법)
@Getter
@Setter
public class HelloData {
    private String username;
    private int age;
}
