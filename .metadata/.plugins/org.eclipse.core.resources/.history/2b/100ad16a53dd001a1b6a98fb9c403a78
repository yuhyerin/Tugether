// 하단 DB 설정 부분은 Sub PJT II에서 데이터베이스를 구성한 이후에 주석을 해제하여 사용.

package com.web.curation.dto.account;


import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import java.sql.Date;
import java.text.SimpleDateFormat;

@Entity(name="user")
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {
    
    @Id // @Column(name="EMAIL")
    private String email;
    
//    @Column(nullable = false)// , name="PASSWORD")
    private String password;

//    @Column(name="signup_date", insertable = false) //insertable = false, updatable = false) //, name="SIGNUP_DATE")
    private Date signup_date;
    //   SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    @Column(nullable = false) //, name="NICKNAME")
    private String nickname;
    
//    @Column(nullable = false, updatable = false) //, name="GENDER")
    private char gender;
    
//    @Column(name="BIRTH_YEAR")//nullable = false, updatable = false) //, name="BIRTH_YEAR")
    private int birth_year;

//    @Column(name="TEMP")@Getter
    private boolean temp;

	public boolean getTemp() {
		return this.temp;
	}

}

