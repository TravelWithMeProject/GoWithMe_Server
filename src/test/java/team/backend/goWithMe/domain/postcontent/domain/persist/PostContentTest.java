package team.backend.goWithMe.domain.postcontent.domain.persist;

import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.backend.goWithMe.domain.postcontent.domain.vo.Content;
import team.backend.goWithMe.domain.postcontent.domain.vo.Count;
import team.backend.goWithMe.domain.postcontent.domain.vo.Title;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class PostContentTest {

    @Test
    @DisplayName("게시글 생성 테스트")
    public void createPostContentTest() {

        //given
        Title givenTitle = Title.from("게시글 제목 테스트");
        Content givenContent = Content.from("게시글 내용 테스트");
        Count givenCount = Count.from(100L);
        Category givenCategory = null;


        //then
        assertDoesNotThrow(() ->
                PostContent.createPostContent(givenTitle, givenContent, givenCount, givenCategory));
    }

    @Test
    @DisplayName("VO 객체 동일 테스트")
    public void voEqualsTest(){

        //given
        Title titleA = Title.from("게시글 제목 테스트");
        Title titleB = Title.from("게시글 제목 테스트");

        Content contentA = Content.from("게시글 내용 테스트");
        Content contentB = Content.from("게시글 내용 테스트");

        Count countA = Count.from(100L);
        Count countB = Count.from(100L);

        PostContent postContentA = PostContent.createPostContent(titleA, contentA, countA, null);
        PostContent postContentB = PostContent.createPostContent(titleB, contentB, countB, null);

        //then
        assertEquals(titleA, titleB);
        assertEquals(contentA, contentB);
        assertEquals(countA, countB);
        assertEquals(postContentA.getTitle(), postContentB.getTitle());
        assertEquals(postContentA.getContent(), postContentB.getContent());
        assertEquals(postContentA.getCount(), postContentB.getCount());
    }

    @Test
    @DisplayName("VO 객체 불일치 테스트")
    public void voNotEqualsTest(){

        //given
        Title titleA = Title.from("게시글 제목 테스트A");
        Title titleB = Title.from("게시글 제목 테스트B");

        Content contentA = Content.from("게시글 내용 테스트A");
        Content contentB = Content.from("게시글 내용 테스트B");

        Count countA = Count.from(100L);
        Count countB = Count.from(1000L);

        PostContent postContentA = PostContent.createPostContent(titleA, contentA, countA, null);
        PostContent postContentB = PostContent.createPostContent(titleB, contentB, countB, null);

        //then
        assertNotEquals(titleA, titleB);
        assertNotEquals(contentA, contentB);
        assertNotEquals(countA, countB);;
        assertNotEquals(postContentA.getTitle(), postContentB.getTitle());
        assertNotEquals(postContentA.getContent(), postContentB.getContent());
        assertNotEquals(postContentA.getCount(), postContentB.getCount());

    }

}
