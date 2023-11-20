package com.platbread.githubpackageexammain;

//import com.platbread.githubpackageexamsub.TestSub;

import com.platbread.githubpackageexamsub.TestSub;
import com.platbread.githubpackageexamsub3.TestSub3;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestSub testSub1;
//    private final TestSub2 testSub2;
    private final TestSub3 testSub3;

    @GetMapping("/test1")
    public String test() {
        return testSub1.testPrint();
    }

//    @GetMapping("/test2")
//    public String test2() {
//        return testSub2.test();
//    }
    @GetMapping("/test3")
    public String test3() {
        return testSub3.testPrint();
    }
}
