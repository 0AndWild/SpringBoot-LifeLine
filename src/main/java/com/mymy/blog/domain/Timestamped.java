package com.mymy.blog.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter // 이게 없으면 api를 이용하여 주고받을 때 html에서 get으로 createdAt,modifiedAt 리스트를 조회해도 작동이 안함!!!(중요*9999) 밑에 두개의 어노테이션과 함꼐 묶여 다님!!!
@MappedSuperclass // Entity가 자동으로 컬럼으로 인식합니다.
//@MappedSuperclass 이거는 Timestamped를 상속한 녀석이 자동으로 생성시간과 수정시간을 컬럼으로 잡도록 도와주는 녀석
@EntityListeners(AuditingEntityListener.class) // 생성/변경 시간을 자동으로 업데이트합니다.
public abstract class Timestamped {
    // abstract를 사용한 추상 클래스는 일반 클래스와 다 똑같지만 \
    // new Timestamped 이런식으로 사용못하고(빵을 못 만드는 빵틀), 다른데서 상속되어야만 빵을 만드는 빵틀이 된다.

    @CreatedDate
    private LocalDateTime createdAt; //LocalDateTime은 시간을 나타내는 자료형 //생성날짜

    @LastModifiedDate
    private LocalDateTime modifiedAt; //수정날짜
}