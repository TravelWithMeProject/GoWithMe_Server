package team.backend.goWithMe.global.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TokenDTO {

    private Long userId;
    private String email;
    private String name;
    private String password;
    private String imgUrl;

    @Builder
    public TokenDTO(Long userId, String email, String name, String password, String imgUrl) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.imgUrl = imgUrl;
    }

    // 정적 팩토리 메서드
//    public static TokenProvider from(Member member) {}
}
