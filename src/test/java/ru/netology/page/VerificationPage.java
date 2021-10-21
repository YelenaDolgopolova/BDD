package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;


public class VerificationPage {
    private SelenideElement verificationField = $("input[name=\"code\"]");
    private SelenideElement verificationButton = $("[data-test-id=action-verify]");

    public DashboardPage verify(DataHelper.VerificationCode code) {
        verificationField.setValue(code.getCode());
        verificationButton.click();
        return new DashboardPage();
    }

}
