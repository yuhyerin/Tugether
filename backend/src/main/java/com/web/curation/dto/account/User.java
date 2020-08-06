// 하단 DB 설정 부분은 Sub PJT II에서 데이터베이스를 구성한 이후에 주석을 해제하여 사용.

package com.web.curation.dto.account;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="user")
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {
    
    @Id
    private String email;
    
    private String password;
    
    @Column(name="signup_date", insertable=false)
    private LocalDateTime signup_date;
    
    private String nickname;
    
    private char gender;
    
    private int birth_year;

    private boolean temp;

	public boolean getTemp() {
		return this.temp;
	}

}

