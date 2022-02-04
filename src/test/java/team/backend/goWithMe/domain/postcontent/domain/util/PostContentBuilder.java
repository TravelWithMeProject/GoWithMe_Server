package team.backend.goWithMe.domain.postcontent.domain.util;

import team.backend.goWithMe.domain.postcontent.domain.persist.Category;
import team.backend.goWithMe.domain.postcontent.domain.persist.PostContent;
import team.backend.goWithMe.domain.postcontent.domain.vo.Content;
import team.backend.goWithMe.domain.postcontent.domain.vo.Title;

public class PostContentBuilder {

    public static final Title TITLE = Title.from("게시글 테스트 제목");
    public static final Content CONTENT = Content.from("게시글 테스트 내용");
    public static final Category CATEGORY = Category.sample1;

    public static PostContent createPostContent(final Title title, final Content content, final Category category) {

        return PostContent.builder()
                .title(title)
                .content(content)
                .category(category)
                .build();
    }
}
