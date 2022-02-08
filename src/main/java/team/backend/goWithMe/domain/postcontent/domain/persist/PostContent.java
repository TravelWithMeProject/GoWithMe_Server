package team.backend.goWithMe.domain.postcontent.domain.persist;

import lombok.*;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.postcontent.domain.vo.Content;
import team.backend.goWithMe.domain.postcontent.domain.vo.Count;
import team.backend.goWithMe.domain.postcontent.domain.vo.Title;
import team.backend.goWithMe.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Table(name = "post_content")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PostContent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "content_id")
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @Embedded
    private Count count;

    @Enumerated(EnumType.STRING)
    private Category category;

    private PostContent(Title title, Content content, Count count, Category category) {
        this.title = title;
        this.content = content;
        this.count = count;
        this.category = category;
    }

    public static PostContent createPostContent(final Title title, final Content content, final Count count, final Category category) {
        return new PostContent(title, content, count, category);
    }
}
