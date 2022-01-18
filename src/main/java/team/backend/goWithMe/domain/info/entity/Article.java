package team.backend.goWithMe.domain.info.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.backend.goWithMe.domain.info.vo.ArticleContents;
import team.backend.goWithMe.domain.info.vo.ArticleTitle;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import static lombok.AccessLevel.*;

@Entity
@Getter
@Table(name = "article")
@NoArgsConstructor(access = PROTECTED)
public class Article extends Info{

    @Embedded
    private ArticleTitle articleTitle;

    @Embedded
    private ArticleContents articleContents;

}
