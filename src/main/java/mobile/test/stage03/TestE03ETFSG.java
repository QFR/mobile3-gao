package mobile.test.stage03;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageETF;
import mobile.page.base.PageManager;
import mobile.page.module.Alert2;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;
import up.light.assertutil.AssertUtil;

/**
 * ETF申购测试
 */
public class TestE03ETFSG extends TestBase {
	private PageETF mPage = PageManager.getPage(PageETF.class);

	@BeforeClass
	public void before() {
		ViewNavigator.navigate("ETF申购", mPage);
	}

	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testETFSG(Map<String, String> param) {
		if(param.get("类别").equals("正例")){
			//选择ETF市场
			mPage.doSelectETFMarket(param.get("ETF市场"));
			
			// 输入代码并校验名称
			String vActualName = mPage.doInputCode(param.get("ETF代码"));
			String vExpectName = param.get("ETF名称");
			AssertUtil.assertEquals(vExpectName, vActualName);
	
			// 输入数量、点击买入
			mPage.doInputNumber(param.get("数量"));
			mPage.doTrade();
			
			// 获取对话框1内容并校验
			Alert2 alert = mPage.getAlert2();
			String vCheckPoint1 = param.get("验证1");
			String vActualCheckPoint1 = alert.doGetConfirmText();
			AssertUtil.assertEquals(vCheckPoint1, vActualCheckPoint1);
			alert.doAcceptConfirm();
	
			// 获取对话框2内容并校验
			String vCheckPoint2 = param.get("验证2");
			String vActualCheckPoint2 = alert.doGetResultLiText();
			AssertUtil.assertContains(vActualCheckPoint2, vCheckPoint2);
			alert.doAcceptResult();
	
			// 参数中加入委托编号
			String vNo = vActualCheckPoint2.substring(vActualCheckPoint2.indexOf("：") + 1, vActualCheckPoint2.length());
			param.put("委托编号", vNo);		
		
		}else{
			if(!param.get("验证2").isEmpty()){
				//选择ETF市场
				mPage.doSelectETFMarket(param.get("ETF市场"));
				
				// 输入代码并校验名称
				String vActualName = mPage.doInputCode(param.get("ETF代码"));
				String vExpectName = param.get("ETF名称");
				AssertUtil.assertEquals(vExpectName, vActualName);
		
				// 输入数量、点击买入
				mPage.doInputNumber(param.get("数量"));
				mPage.doTrade();
				
				// 获取对话框1内容并校验
				Alert2 alert = mPage.getAlert2();
				String vCheckPoint1 = param.get("验证1");
				String vActualCheckPoint1 = alert.doGetConfirmText();
				AssertUtil.assertEquals(vCheckPoint1, vActualCheckPoint1);
				alert.doAcceptConfirm();
		
				// 获取对话框2内容并校验
				String vCheckPoint2 = param.get("验证2");
				String vActualCheckPoint2 = alert.doGetResultLiText();
				AssertUtil.assertContains(vActualCheckPoint2, vCheckPoint2);
				alert.doAcceptConfirmExp();
				
			}else{
				//选择ETF市场
				mPage.doSelectETFMarket(param.get("ETF市场"));
				
				// 输入代码
				mPage.doInputCodeExp(param.get("ETF代码"));

				// 获取对话框1内容并校验
				Alert2 alert = mPage.getAlert2();
				String vCheckPoint1 = param.get("验证1");
				String vActualCheckPoint1 = alert.doGetResultLiText();
				AssertUtil.assertEquals(vCheckPoint1, vActualCheckPoint1);
				alert.doAcceptConfirmExp();
			} 	
		}
	}
}
