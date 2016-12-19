package mobile.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import mobile.page.base.ElementOf;
import mobile.page.base.ElementOfs;
import mobile.page.base.PageManager;
import mobile.page.base.PageTradeWithSelect;
import mobile.page.module.Alert;
import mobile.page.module.Loader;
import up.light.pagefactory.TestElement;
import up.light.utils.ArgumentUtil;
import up.light.wait.Conditions;
import up.light.wait.WaitUtil;

public class PageETF extends PageTradeWithSelect {
	
	@ElementOf(ElementOfs.CODE)
	private TestElement oEditETFDM;	// ETF代码
	
	@ElementOf(ElementOfs.NAME)
	private TestElement oTextETFMC;	// ETF名称
	
	@ElementOf(ElementOfs.NUMBER)
	private TestElement oEditSL;	// 份额
	
	@ElementOf(ElementOfs.BUTTON_OK)  
	private TestElement oBtnTrade;
	
	private TestElement oEditCFGDM;	// 成分股代码
	private TestElement oEditCFGMC;	// 成分股名称
	
	private TestElement oLableETFMkt;  //ETF市场	
	
	private boolean isInSelectView;
	
	private PageCodeSelect mPageCodeSelect = PageManager.getPage(PageCodeSelect.class);
	private Loader mLoader = PageManager.getPage(Loader.class);
	/**
	 *选择ETF市场
	 * @param etfmtk ETF市场
	 */
	public void doSelectETFMarket(String etfmkt){
		
		oLableETFMkt.e().click();
	
		String xpath = "//div[@class='kmc-downMenu']//li[text()='" + etfmkt + "']";
		List<WebElement> es = driver.findElements(By.xpath(xpath));
		ArgumentUtil.notEmpty(es, "can't find this ETFMarket: " + etfmkt);
		
		es.get(0).click();
		
	}
	
	/**
	 * 输入成分股证券代码
	 * @param code 成分股证券代码
	 * @return 成分股证券名称
	 */
	public String doInputCFGCode(String code) {
		// 等待元素出现
		WaitUtil.waitFor(driver, oEditCFGDM, WaitUtil.WAIT_MEDIUM).click();
		WaitUtil.sleep(500);

		mPageCodeSelect.doInputCode(code);
		
		// 等待加载框消失
		mLoader.waitForLoad();
		
		// 检查对话框
		Alert alert = getAlert();
		if (alert.exists()) {
			throw new RuntimeException(alert.doGetText());
		}

		return stripe(WaitUtil.waitForText(driver, oEditCFGMC.e(), WaitUtil.WAIT_MEDIUM, null, Conditions.NOTBLANK));
	}
	
	/**
	 * 输入证券代码(验证反例)
	 * @param code 证券代码
	 * @return 证券名称
	 */
	public void doInputCodeExp(String code) {
		// 等待元素出现
		WaitUtil.waitFor(driver, oEditETFDM, WaitUtil.WAIT_MEDIUM).click();
		WaitUtil.sleep(500);

		// 选择代码
		isInSelectView = true;
		mPageCodeSelect.doInputCode(code);
		isInSelectView = false;

		// 等待加载框消失
		mLoader.waitForLoad();
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
