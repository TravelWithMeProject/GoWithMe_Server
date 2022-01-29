package team.backend.goWithMe.domain.auth.details;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.persist.RoleType;
import team.backend.goWithMe.domain.member.domain.vo.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDetails implements UserDetails {

    private UserEmail email;
    private UserPassword password;
    private RoleType role;
    private UserName name;
    private UserNickName nickName;
    private LocalDate birth;
    private UserProfileImage profileImg;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> "ROLE_" + role);

        return collectors;
    }

    @Override
    public String getPassword() {
        return password.userPassword();
    }

    @Override
    public String getUsername() {
        return email.userEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private MemberDetails(UserEmail email, UserPassword password, UserName name, UserNickName nickName,
                         LocalDate birth, RoleType role, UserProfileImage profileImg) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.role = role;
        this.birth = birth;
        this.profileImg = profileImg;
    }

    /**
     * 비즈니스 로직
     */
    public static MemberDetails from(final Member member) {
        return new MemberDetails(member.getEmail(), member.getPassword(), member.getRoleType(),
                member.getName(), member.getNickname(), member.getBirth(), member.getProfileImage());
    }

    public static MemberDetails createDetails(final UserEmail email, final UserPassword password, final UserName name,
                                              final UserNickName nickName, final LocalDate birth, RoleType role,
                                              final UserProfileImage profileImage) {

        log.debug("createEmail : {}", email.userEmail());

        return new MemberDetails(email, password, name, nickName, birth, role, profileImage);
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .roleType(RoleType.USER)
                .nickname(nickName)
                .birth(birth)
                .profileImage(profileImg)
                .build();
    }
}
