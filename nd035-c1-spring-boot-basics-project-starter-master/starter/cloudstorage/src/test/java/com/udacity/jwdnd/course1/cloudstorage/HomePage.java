package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private  WebElement noteDescription;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmit;

    @FindBy(id = "nav-notes")
    private WebElement notesTab;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "edit-note-button")
    private WebElement editNoteButton;

    @FindBy(id = "delete-note-button")
    private WebElement deleteNoteButton;

    @FindBy(id = "note-title-display")
    private WebElement noteTitleDisplay;

    @FindBy(id = "note-decription-display")
    private WebElement noteDescriptionDisplay;

    @FindBy(id = "credential-url-display")
    private WebElement credentialURLDisplay;

    @FindBy(id = "credential-username-display")
    private WebElement credentialUsernameDisplay;

    @FindBy(id = "credential-password-display")
    private WebElement credentialPasswordDisplay;

    private WebDriver webDriver;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void logout(){
        this.logoutButton.click();
    }

    public String getNoteTitleDisplay() {
        return noteTitleDisplay.getText();
    }

    public String getNoteDescriptionDisplay() {
        return noteDescriptionDisplay.getText();
    }

    public String getCredentialURLDisplay() {
        return credentialURLDisplay.getText();
    }

    public String getCredentialUsernameDisplay() {
        return credentialUsernameDisplay.getText();
    }

    public String getCredentialPasswordDisplay() {
        return credentialPasswordDisplay.getText();
    }
}
