package mobile.page;

import mobile.page.base.ElementOf;
import mobile.page.base.ElementOfs;
import mobile.page.base.PageManager;
import mobile.page.base.PageTradeWithSelect;
import mobile.page.module.Alert;
import mobile.page.module.Loader;
import up.light.pagefactory.TestElement;
import up.light.wait.Conditions;
import up.light.wait.WaitUtil;

public class PageGFZR extends PageTradeWithSelect {
	
	@ElementOf(ElementOfs.CODE)
	private TestElement oEditCode;	// 证券代码

	@ElementOf(ElementOfs.NAME)
	private TestElement oTextName;	// 证券名称
	
	private TestElement oEditPrice;	// 价格
	private TestElement oEditCJPrice;  //成交确认买入价格
	@ElementOf(ElementOfs.NUMBER)
	private TestElement oEditNum;	// 数量

	@ElementOf(ElementOfs.BUTTON_OK)
	private TestElement oBtnOK;		// 交易按钮	
	
	private TestElement oOppShareholdCode;    //对方股东代码
	private TestElement oOppSeat;			  //对方席位
	private TestElement oDealNum;			  //成交约定号
	private TestElement oBtnBaoJia;			  //报价
	private TestElement oTableBJItem;		  //报价代码列表第一条数据
	private boolean isInSelectView;
	
	private PageCodeSelect mPageCodeSelect = PageManager.getPage(PageCodeSelect.class);
	private Loader mLoader = PageManager.getPage(Loader.class);
	
	/**
	 * 获取价格
	 * @return
	 */
	public String doGetPrice() {
		return getValue(oEditPrice.e());
	}
	
	/**
	 * 获取价格
	 * @return
	 */
	public String doGetCJPrice() {
		return getText(oEditCJPrice.e());
	}
	
	/**
	 * 输入对方股东代码
	 * @param oppshareholdcode 
	 */
	public void doInputShareholdCode(String oppshareholdcode) {
		getKeyboard().doInput(oOppShareholdCode.e(), oppshareholdcode);
	}
	
	/**
	 * 输入对方席位
	 * @param oppseat 
	 */
	public void doInputOppSeat(String oppseat) {
		getKeyboard().doInput(oOppSeat.e(), oppseat);
	}
	
	/**
	 * 输入成交约定号
	 * @param dealnum 
	 */
	public void doInputDealNum(String dealnum) {
		getKeyboard().doInput(oDealNum.e(), dealnum);
	}
	
	/**
	 * 输入证券代码
	 * @param code 证券代码
	 * @return 证券名称
	 */
	public String doInputBJCode(String code) {
		// 等待元素出现
		WaitUtil.waitFor(driver, oEditCode, WaitUtil.WAIT_MEDIUM).click();
		WaitUtil.sleep(500);

		// 选择代码
		isInSelectView = true;
		mPageCodeSelect.doInputCode(code);
		isInSelectView = false;
		doInputSelectCode();
		
		// 等待加载框消失
		mLoader.waitForLoad();
		
		// 检查对话框
		Alert alert = getAlert();
		if (alert.exists()) {
			throw new RuntimeException(alert.doGetText());
		}

		return stripe(WaitUtil.waitForText(driver, oTextName.e(), WaitUtil.WAIT_MEDIUM, null, Conditions.NOTBLANK));
	}
	
	/**
	 * 输入证券代码（验证反例）
	 * @param code 证券代码
	 * @return 证券名称
	 */
	public void doInputCodeExp(String code) {
		// 等待元素出现
		WaitUtil.waitFor(driver, oEditCode, WaitUtil.WAIT_MEDIUM).click();
		WaitUtil.sleep(500);

		// 选择代码
		isInSelectView = true;
		mPageCodeSelect.doInputCode(code);
		isInSelectView = false;
		
		// 等待加载框消失
		mLoader.waitForLoad();
	}
	
	public void doInputSelectCode() {
		WaitUtil.sleep(1000);
		oBtnBaoJia.e().click();
		WaitUtil.sleep(5000);
		oTableBJItem.e().click();
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
