package mobile.page;

import mobile.page.base.AbstractPage;
import mobile.page.base.PageManager;
import mobile.page.module.Alert;
import mobile.page.module.Loader;
import up.light.pagefactory.TestElement;
import up.light.wait.WaitUtil;

public class PageJJCWZCN extends AbstractPage {
	private TestElement oBtnTY; //同意
	private TestElement oEditCode;	//基金代码
	private TestElement oTextName;	//基金名称
	private TestElement oTextWTSL;	//基金数量
	private TestElement oBtnTrade;	//交易按钮
	
	private PageCodeSelect mPageCodeSelect = PageManager.getPage(PageCodeSelect.class);
	private Loader mLoader = PageManager.getPage(Loader.class);
	private boolean isInSelectView;
	/**
	 * 点击同意按钮
	 */
	public void doApprove(){
		oBtnTY.e().click();	
	}
	
	/**
	 * 输入证券代码
	 * @param code 证券代码
	 * @return 证券名称
	 */
	public String doInputCode(String code) {
		// 等待元素出现
		WaitUtil.waitFor(driver, oEditCode, WaitUtil.WAIT_MEDIUM).click();
		WaitUtil.sleep(500);

		// 选择代码
		isInSelectView = true;
		mPageCodeSelect.doInputCode(code);
		isInSelectView = false;

		// 等待加载框消失
		mLoader.waitForLoad();

		// 检查对话框
		Alert alert = getAlert();
		if (alert.exists()) {
			throw new RuntimeException(alert.doGetText());
		}

		return getText(oTextName.e());
	}
	
	/**
	 * 输入数量
	 * @param number 
	 * @param number 数量
	 */
	public void doInputNumber(String number) {
		getKeyboard().doInput(oTextWTSL.e(), number);
	}
	
	/**
	 * 点击认购，申购或赎回按钮
	 */
	public void doTrade() {
		oBtnTrade.e().click();
	}
	
	@Override
	public void reset() {
		if (isInSelectView) {
			mPageCodeSelect.reset();
			isInSelectView = false;
		}

		super.reset();
	}
}
