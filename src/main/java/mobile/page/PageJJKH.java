package mobile.page;

import mobile.page.base.AbstractPage;
import up.light.pagefactory.TestElement;

public class PageJJKH extends AbstractPage {
	private TestElement oImgJJGSLIST;
	private TestElement oLiJJGSCUR;
	private TestElement oBtnTJ;
	
	public void doSelectJJGS(String curjjgs){
		oImgJJGSLIST.e().click();
		oLiJJGSCUR.e().click();
	}
	/**
	 * 基金开户点击提交
	 */
	public void doTrade() {
		oBtnTJ.e().click();
	}
	
}
