package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999/", LoginPage.class);
    }

    @Test
    void shouldTransferFromFirstToSecond() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(authInfo);
        var dashBoardPage = verificationPage.verify(verifyInfo);
        int firstBalance = dashBoardPage.getCurrentBalanceOfFirstCard();
        int secondBalance = dashBoardPage.getCurrentBalanceOfSecondCard();
        var transferMoney = dashBoardPage.firstCard();
        String sum = "2000";
        var card = DataHelper.getSecondCard();
        transferMoney.validTransfer(sum, card);
        assertEquals(firstBalance + Integer.parseInt(sum), dashBoardPage.getCurrentBalanceOfFirstCard());
        assertEquals(secondBalance - Integer.parseInt(sum), dashBoardPage.getCurrentBalanceOfSecondCard());
    }

    @Test
    void shouldTransferFromSecondToFirst() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(authInfo);
        var dashBoardPage = verificationPage.verify(verifyInfo);
        int firstBalance = dashBoardPage.getCurrentBalanceOfFirstCard();
        int secondBalance = dashBoardPage.getCurrentBalanceOfSecondCard();
        var transferMoney = dashBoardPage.secondCard();
        String sum = "5000";
        var card = DataHelper.getFirstCard();
        transferMoney.validTransfer(sum, card);
        assertEquals(secondBalance + Integer.parseInt(sum), dashBoardPage.getCurrentBalanceOfSecondCard());
        assertEquals(firstBalance - Integer.parseInt(sum), dashBoardPage.getCurrentBalanceOfFirstCard());
    }

    @Test
    void shouldGetError() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(authInfo);
        var dashBoardPage = verificationPage.verify(verifyInfo);
        var transferMoney = dashBoardPage.secondCard();
        String sum = "20000";
        var card = DataHelper.getFirstCard();
        transferMoney.validTransfer(sum, card);
        transferMoney.errorMessage();
    }

}
