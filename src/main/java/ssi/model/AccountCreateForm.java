package ssi.model;

import ssi.common.validation.*;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.*;

@Getter @Setter
@EqualsPropertyValues(property = "password",comparingProperty = "rePassword")
public class AccountCreateForm implements Serializable {
	// アカウント名
	@NotEmpty
    @Size(max = 20)
	private String accountId;
	
	// パスワード
	@NotEmpty
    @Size(max = 50)
	private String password;

	// パスワード
	private String rePassword;
}
