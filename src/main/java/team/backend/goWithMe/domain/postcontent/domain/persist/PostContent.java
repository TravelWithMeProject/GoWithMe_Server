package team.backend.goWithMe.domain.postcontent.domain.persist;

import com.mysema.commons.lang.Assert;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.postcontent.domain.vo.Content;
import team.backend.goWithMe.domain.postcontent.domain.vo.Title;
import team.backend.goWithMe.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Table(name = "post_content")
@Getter
@NoArgsConstructor
public class PostContent extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "content_id")
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    public PostContent(Title title, Content content, Category category){

        Assert.hasText(title.title(), "게시글 제목이 없습니다.");
        Assert.hasText(content.content(), "게시글 내용이 없습니다.");
        Assert.hasText(category.toString(), "게시글 카테고리가 없습니다.");

        this.title = title;
        this.content = content;
        this.category = category;
    }
}
