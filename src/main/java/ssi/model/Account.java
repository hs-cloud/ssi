package ssi.model;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "account")
public class Account implements Serializable {
	
	// アカウントID
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int no;
	
	// アカウント名
	private String accountId;
	
	// パスワード
	private String password;

	//　作成日
	private Date createDate;

	//　更新日
	private Date updateDate;

	//　削除日
	private Date deleteDate;

    @PrePersist
    public void onPrePersist() {
        setCreateDate(new Date());
    }

    @PreUpdate
    public void onPreUpdate() {
        setUpdateDate(new Date());
    }
}
