package team.backend.goWithMe.domain.postcontent.domain.persist;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import team.backend.goWithMe.domain.postcontent.domain.vo.Content;
import team.backend.goWithMe.domain.postcontent.domain.vo.Title;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static team.backend.goWithMe.domain.postcontent.domain.util.PostContentBuilder.*;

public class PostContentTest {

    @Test
    @DisplayName("게시글 빌더 성공 테스트 : 성공")
    public void builderPostContentSuccessTest() {
        
        PostContent postContent = createPostContent(TITLE, CONTENT, CATEGORY);
        
        //then
        assertThat(postContent.getTitle().title()).isEqualTo("게시글 테스트 제목");
        assertThat(postContent.getContent().content()).isEqualTo("게시글 테스트 내용");
        assertThat(postContent.getCategory()).isEqualTo(Category.sample1);
    }

    @Test
    @DisplayName("게시글 빌더 실패 테스트 : 실패")
    public void builderPostContentFailTest() {

        //given
        Title givenTitle = Title.from("게시글 테스트 제목");
        Content givenContent = Content.from("게시글 테스트 내용");

        //then
        assertThrows(NullPointerException.class, () -> {
           PostContent.builder()
                   .title(givenTitle)
                   .content(givenContent)
                   .build();
        });
    }
}
