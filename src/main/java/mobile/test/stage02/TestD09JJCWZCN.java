package mobile.test.stage02;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mobile.navigator.ViewNavigator;
import mobile.page.PageJJCWZCN;
import mobile.page.base.PageManager;
import mobile.page.module.Alert;
import mobile.test.base.TestBase;
import mobile.test.base.TestDataProvider;
import up.light.assertutil.AssertUtil;

public class TestD09JJCWZCN extends TestBase {
	private PageJJCWZCN mPage = PageManager.getPage(PageJJCWZCN.class);

	@BeforeClass
	public void before() {
		ViewNavigator.navigate("基金场外转场内", mPage);
		mPage.doApprove();
	}

	@Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
	public void testJJCWZCN(Map<String, String> param) {

		// 输入代码并校验名称
		String vActualName = mPage.doInputCode(param.get("代码"));
		String vExpectName = param.get("名称");
		AssertUtil.assertEquals(vExpectName, vActualName);

		// 输入数量、点击买入
		mPage.doInputNumber(param.get("数量"));
		mPage.doTrade();

		// 获取对话框1内容并校验
		String vCheckPoint1 = param.get("验证1");
		Alert alert = mPage.getAlert();
		String vActualCheckPoint1 = alert.doGetText();
		AssertUtil.assertEquals(vCheckPoint1, vActualCheckPoint1);
		alert.doAccept();

		// 获取对话框2内容并校验
		String vCheckPoint2 = param.get("验证2");
		String vActualCheckPoint2 = alert.doGetText();

		AssertUtil.assertContains(vActualCheckPoint2, vCheckPoint2);
		alert.doAccept();

	}
	
}
