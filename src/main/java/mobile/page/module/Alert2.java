package mobile.page.module;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import mobile.page.base.AbstractPage;
import up.light.pagefactory.TestElement;
import up.light.wait.WaitUtil;

public class Alert2 extends AbstractPage {
	// 确认对话框
	private TestElement oTextConfirms;
	private TestElement oBtnConfirmOK;
	// 委托成功对话框
	private TestElement oTextMsgSuccess;
	private TestElement oTextLiMsgSuccess;
	private TestElement oBtnOK;
	private TestElement oBtnOKExp;
	private TestElement oTitleMsgFail;
	// 委托失败对话框
	private TestElement oTextMsgFail;

	public String doGetConfirmText() {
		return getMsg(oTextConfirms);
	}

	public String doGetResultText() {
		if(WaitUtil.exists(driver, oTextMsgFail, WaitUtil.WAIT_SHORT)) {
			return getText(oTextMsgFail.e());
		}

		return getMsg(oTextMsgSuccess);
	}
	
	public String doGetResultLiText() {
		if (WaitUtil.exists(driver, oTitleMsgFail, WaitUtil.WAIT_MEDIUM)) {
			return getText(oTextMsgFail.e()); 
		}

		return getMsg(oTextLiMsgSuccess);
	}

	public void doAcceptConfirm() {
		oBtnConfirmOK.e().click();
	}

	public void doAcceptResult() {
		oBtnOK.e().click();
	}
	
	public void doAcceptConfirmExp(){
		oBtnOKExp.e().click();
	}
	
	
	private String getMsg(TestElement es) {
		WaitUtil.sleep(1000);
		StringBuilder sb = new StringBuilder();
		String content = null;
		int i = 1;

		for (WebElement e : es.es()) {
			content = getText(e);

			if (StringUtils.isEmpty(content))
				continue;

			sb.append(content);

			if (i++ % 2 == 0) {
				sb.append("\n");
			} else {
				sb.append(" ");
			}
		}

		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		} else {
			throw new RuntimeException("text is null, confirm alert exists, current context: " + driver.getContext());
		}

		return sb.toString();
	}
}
