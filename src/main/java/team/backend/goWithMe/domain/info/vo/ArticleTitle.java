package team.backend.goWithMe.domain.info.vo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class ArticleTitle {

    @NotEmpty
    @Column(name = "article_title", length = 100)
    private String articleTitle;

    public static ArticleTitle of(String articleTitle){
        return new ArticleTitle(articleTitle);
    }

}
