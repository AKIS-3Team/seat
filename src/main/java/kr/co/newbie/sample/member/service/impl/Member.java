package kr.co.newbie.sample.member.service.impl;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Member {
    @Id
    private long id;
    private String name;

}
