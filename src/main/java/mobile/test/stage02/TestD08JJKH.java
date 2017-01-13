package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageJJKH;
import mobile.page.base.PageManager;
import mobile.page.module.Alert;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;
import up.light.assertutil.AssertUtil;

public class TestD08JJKH extends TestBase {
	private PageJJKH mPage = PageManager.getPage(PageJJKH.class);

	@BeforeClass
	public void before() {
		ViewNavigator.navigate("基金开户", mPage);
	}

	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testJJKH(Map<String, String> param) {
		//选择基金公司
		mPage.doSelectJJGS(param.get("基金公司"));

		mPage.doTrade();

		// 校验对话框1
		String vCheckPoint1 = param.get("验证1");
		Alert alert = mPage.getAlert();
		String vActualCheckPoint1 = alert.doGetText();
		AssertUtil.assertEquals(vCheckPoint1, vActualCheckPoint1);
		alert.doAccept();

	}
	
}
