package team.backend.goWithMe.domain.postcontent.domain.persist;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import team.backend.goWithMe.domain.postcontent.domain.vo.Content;
import team.backend.goWithMe.domain.postcontent.domain.vo.Count;
import team.backend.goWithMe.domain.postcontent.domain.vo.Title;

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
        Category givenCategory = Category.sample;

        //then
        assertThrows(NullPointerException.class, () ->
                PostContent.createPostContent(null, null, null, null));
        assertThrows(NullPointerException.class, () ->
                PostContent.createPostContent(givenTitle, null, null, null));
        assertThrows(NullPointerException.class, () ->
                PostContent.createPostContent(null, givenContent, null, null));
        assertThrows(NullPointerException.class, () ->
                PostContent.createPostContent(null, null, givenCount, null));
        assertThrows(NullPointerException.class, () ->
                PostContent.createPostContent(null, null, null, givenCategory));
        assertThrows(NullPointerException.class, () ->
                PostContent.createPostContent(givenTitle, givenContent, null, null));
        assertThrows(NullPointerException.class, () ->
                PostContent.createPostContent(givenTitle, null, givenCount, null));
        assertThrows(NullPointerException.class, () ->
                PostContent.createPostContent(givenTitle, null, null, givenCategory));
        assertThrows(NullPointerException.class, () ->
                PostContent.createPostContent(null, givenContent, givenCount, null));
        assertThrows(NullPointerException.class, () ->
                PostContent.createPostContent(null, givenContent, null, givenCategory));
        assertThrows(NullPointerException.class, () ->
                PostContent.createPostContent(null, null, givenCount, givenCategory));
        assertThrows(NullPointerException.class, () ->
                PostContent.createPostContent(null, givenContent, givenCount, givenCategory));
        assertThrows(NullPointerException.class, () ->
                PostContent.createPostContent(givenTitle, null, givenCount, givenCategory));
        assertThrows(NullPointerException.class, () ->
                PostContent.createPostContent(givenTitle, givenContent, null, givenCategory));
        assertThrows(NullPointerException.class, () ->
                PostContent.createPostContent(givenTitle, givenContent, givenCount, null));
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

        PostContent postContentA = PostContent.createPostContent(titleA, contentA, countA, Category.sample);
        PostContent postContentB = PostContent.createPostContent(titleB, contentB, countB, Category.sample);

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

        PostContent postContentA = PostContent.createPostContent(titleA, contentA, countA, Category.sample);
        PostContent postContentB = PostContent.createPostContent(titleB, contentB, countB, Category.sample);

        //then
        assertNotEquals(titleA, titleB);
        assertNotEquals(contentA, contentB);
        assertNotEquals(countA, countB);;
        assertNotEquals(postContentA.getTitle(), postContentB.getTitle());
        assertNotEquals(postContentA.getContent(), postContentB.getContent());
        assertNotEquals(postContentA.getCount(), postContentB.getCount());

    }

//    @Test
//    @DisplayName("게시글 빌더 성공 테스트 : 성공")
//    public void builderPostContentSuccessTest() {
//
//        PostContent postContent = PostContent.createPostContent(TITLE, CONTENT, CATEGORY);
//
//        //then
//        assertThat(postContent.getTitle().title()).isEqualTo("게시글 테스트 제목");
//        assertThat(postContent.getContent().content()).isEqualTo("게시글 테스트 내용");
//        assertThat(postContent.getCategory()).isEqualTo(Category.sample);
//    }
//
//    @Test
//    @DisplayName("게시글 빌더 실패 테스트 : 실패")
//    public void builderPostContentFailTest() {
//
//        //given
//        Title givenTitle = Title.from("게시글 테스트 제목");
//        Content givenContent = Content.from("게시글 테스트 내용");
//
//        //then
//        assertThrows(NullPointerException.class, () -> {
//           PostContent.builder()
//                   .title(givenTitle)
//                   .content(givenContent)
//                   .build();
//        });
//    }
}
