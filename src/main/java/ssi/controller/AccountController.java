package ssi.controller;

import ssi.model.Account;
import ssi.model.AccountCreateForm;
import ssi.repository.AccountRepository;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.sun.net.httpserver.Authenticator.Result;

@Controller
@RequestMapping(path="account")
public class AccountController {
	
	@Autowired
	AccountRepository accountRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	String getAccount() {
		return "account/index";
	}
	
	// アカウント作成
	@RequestMapping(path="create", method = RequestMethod.GET)
	String getCreateAccount(Model model) {
		AccountCreateForm accountCreateForm;
		if(model.asMap().get("createAccount") == null) {
			accountCreateForm = new AccountCreateForm();
		} else {
			accountCreateForm = (AccountCreateForm)model.asMap().get("createAccount");
		}
        model.addAttribute("createAccount", accountCreateForm);
		return "account/AccountCreate";
	}
	
	@RequestMapping(path="create", method = RequestMethod.POST)
	String postCreateAccount(@Validated AccountCreateForm accountCreateForm
						    ,BindingResult result
						    ,RedirectAttributes redirect
						    ,Model model) {
		if (result.hasErrors()) {
			model.addAttribute("createAccount", accountCreateForm);
			return "account/AccountCreate";
		}
		redirect.addFlashAttribute("createAccount", accountCreateForm);
		return "redirect:create_confirm";
	}

	// アカウント作成確認
	@RequestMapping(path="create_confirm", method = RequestMethod.GET)
	String getCreateAccountConfirm(Model model) {
		AccountCreateForm accountCreateForm = (AccountCreateForm)model.asMap().get("createAccount");
		model.addAttribute("createAccount", accountCreateForm);
		return "account/AccountCreateConfirm";
	}
	
	@RequestMapping(path="create_confirm", method = RequestMethod.POST)
	String postCreateAccountConfirm(@ModelAttribute AccountCreateForm accountCreateForm
		    					     ,RedirectAttributes redirect
		    					     ,@RequestParam("button") String button
									 ,Model model) {
		if(button.equals("cancel")) {
			// パスワード初期化処理
			accountCreateForm.setPassword("");
			accountCreateForm.setRePassword("");
			
			redirect.addFlashAttribute("createAccount", accountCreateForm);
			return "redirect:create";
		}
		
		// パスワードエンコード
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodePassword = passwordEncoder.encode(accountCreateForm.getPassword());
		
		Account account = new Account();
		account.setAccountId(accountCreateForm.getAccountId());
		account.setPassword(encodePassword);
		accountRepository.save(account);
		
		// パスワード初期化処理
		accountCreateForm.setPassword("");
		accountCreateForm.setRePassword("");

		redirect.addFlashAttribute("createAccount", accountCreateForm);
		return "redirect:create_complete";
	}

	// アカウント作成完了
	@RequestMapping(path="create_complete", method = RequestMethod.GET)
	String getCreateAccountComplete(Model model) {
		AccountCreateForm accountCreateForm = (AccountCreateForm)model.asMap().get("createAccount");
		model.addAttribute("createAccount", accountCreateForm);
		return "account/AccountCreateComplete";
	}
}
