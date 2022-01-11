package team.backend.goWithMe.domain.info.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

import static lombok.AccessLevel.*;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class ArticleContents {

    @NotEmpty
    @Column(name = "article_contents")
    private String articleContents;

    public static ArticleContents of(String articleContents){
        return new ArticleContents(articleContents);
    }
}
